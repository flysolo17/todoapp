package com.ketchupzzz.isaom.repository.modules

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.subject.module.Content
import com.ketchupzzz.isaom.models.subject.module.ModuleWithContents
import com.ketchupzzz.isaom.models.subject.module.Modules
import com.ketchupzzz.isaom.utils.generateRandomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


const val MODULE_COLLECTION ="modules";
const val CONTENT_COLLECTION ="contents";
class ModuleRepositoryImpl(private val firestore: FirebaseFirestore,private  val storage : FirebaseStorage): ModuleRepository {

    override suspend fun getAllModules(subjectID: String, result: (UiState<List<Modules>>) -> Unit) {
        result.invoke(UiState.Loading)
        firestore.collection(MODULE_COLLECTION)
            .whereEqualTo("subjectID",subjectID)
            .orderBy("createdAt",Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                error?.let {
                    Log.e(MODULE_COLLECTION,it.message,it)
                    result.invoke(UiState.Error(it.message.toString()))
                }
                value?.let {
                    result.invoke(UiState.Success(it.toObjects(Modules::class.java)))
                }
            }
    }

    override suspend fun createModule(
        modules: Modules,
        result: (UiState<String>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(MODULE_COLLECTION)
            .document(modules.id ?: generateRandomString())
            .set(modules)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Successfully Created"))
                } else {
                    result.invoke(UiState.Error("Unknown error!"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Error(it.message.toString()))
            }
    }

    override suspend fun deleteModule(moduleID: String, result: (UiState<String>) -> Unit) {
        try {
            result.invoke(UiState.Loading)
            val batch = firestore.batch()
            val mainRef = firestore.collection(MODULE_COLLECTION).document(moduleID)

            // Delete the module document
            batch.delete(mainRef)

            // Delete all contents within the module
            val contentSnapshot = mainRef.collection(CONTENT_COLLECTION).get().await()
            for (document in contentSnapshot.documents) {
                batch.delete(document.reference)
            }

            // Commit the batch
            batch.commit().await()

            // Delete the whole folder in storage
            storage.getReference(MODULE_COLLECTION).child(moduleID).delete().await()

            // Invoke success result
            result.invoke(UiState.Success("Module deleted successfully"))
        } catch (e: Exception) {
            // Invoke error result
            result.invoke(UiState.Error(e.message ?: "Unknown error"))
        }
    }


    override suspend fun createContent(
        moduleID: String,
        content: Content,
        uri: Uri?,
        result: (UiState<String>) -> Unit
    ) {
        try {
            result.invoke(UiState.Loading)
            uri?.let {
                val name = "${generateRandomString(10)}.${MimeTypeMap.getFileExtensionFromUrl(uri.toString())}"
                val storageRef = storage.reference.child(MODULE_COLLECTION).child(moduleID).child(name)
                val downloadUri = withContext(Dispatchers.IO) {
                    storageRef.putFile(uri).await()
                    storageRef.downloadUrl.await()
                }
                content.image = downloadUri.toString()
            }

            firestore.collection(MODULE_COLLECTION)
                .document(moduleID)
                .collection(CONTENT_COLLECTION)
                .document(content.id ?: "")
                .set(content)
                .await()

            result.invoke(UiState.Success("Successfully Added"))
        } catch (e: Exception) {
            Log.e(CONTENT_COLLECTION,e.localizedMessage.toString(),e)
            result.invoke(UiState.Error(e.message ?: "Unknown Error"))
        }
    }

    override suspend fun deleteContent(
        moduleID: String,
        content: Content,
        result: (UiState<String>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(MODULE_COLLECTION)
            .document(moduleID)
            .collection(CONTENT_COLLECTION)
            .document(content.id!!)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Successfully Deleted"))
                } else {
                    result.invoke(UiState.Error("Unknown Error"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Error(it.message ?: "Unknown error"))
            }
    }

    override suspend fun getModuleWithContent(
        moduleID: String,
        result: (UiState<ModuleWithContents>) -> Unit
    ) {
        result.invoke(UiState.Loading)

        val moduleRef = firestore.collection(MODULE_COLLECTION).document(moduleID)
        val module = moduleRef.get().await()
        Log.d(CONTENT_COLLECTION,"Module Reloaded")
        if (!module.exists()) {
            result.invoke(UiState.Error("Module not found!"))
            return
        }
        delay(1000L)
        moduleRef
            .collection(CONTENT_COLLECTION)
            .orderBy("createdAt",Query.Direction.ASCENDING)
            .addSnapshotListener { contentSnapshot, contentError ->
                contentError?.let {
                    result.invoke(UiState.Error(it.message ?: "Unknown Error"))
                    return@addSnapshotListener
                }

                if (contentSnapshot != null) {
                    val moduleWithContent = ModuleWithContents(
                        modules = module.toObject(Modules::class.java)!!,
                        content = contentSnapshot.toObjects(Content::class.java)
                    )
                    Log.d(CONTENT_COLLECTION,"Content Reloaded")

                    result.invoke(UiState.Success(moduleWithContent))
                }
            }
    }

    override fun updateLock(moduleID: String, lock: Boolean, result: (UiState<String>) -> Unit) {
        result.invoke(UiState.Loading)
        firestore.collection(MODULE_COLLECTION)
            .document(moduleID)
            .update("open",!lock)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Successfully Updated!"))
                } else {
                    result.invoke(UiState.Error("Unknown Error"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Error(it.message.toString()))
            }
    }

    override suspend fun getAllContents(
        moduleID: String,
        result: (UiState<List<Content>>) -> Unit
    ) {
        val moduleRef = firestore.collection(MODULE_COLLECTION).document(moduleID)
        moduleRef
            .collection(CONTENT_COLLECTION)
            .orderBy("createdAt",Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                result.invoke(UiState.Loading)
                value?.let {
                    result.invoke(UiState.Success(it.toObjects(Content::class.java)))
                }
                error?.let {
                    result.invoke(UiState.Error(it.message.toString()))
                }
            }
    }

}