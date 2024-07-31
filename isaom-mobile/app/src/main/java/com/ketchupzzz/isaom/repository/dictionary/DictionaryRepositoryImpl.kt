package com.ketchupzzz.isaom.repository.dictionary

import android.util.JsonReader
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Dictionary
import com.ketchupzzz.isaom.utils.generateRandomString
import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
const val DICTIONARY_COLLECTION  = "dictionary";
class DictionaryRepositoryImpl(private val firestore: FirebaseFirestore) : DictionaryRepository {

    override suspend fun getAllDictionaries(
        result: (UiState<List<Dictionary>>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(DICTIONARY_COLLECTION)
            .limit(1000)
            .addSnapshotListener { value, error ->
                error?.let {
                    result.invoke(UiState.Error(it.message.toString()))
                }
                value?.let {
                    result.invoke(UiState.Success(it.toObjects(Dictionary::class.java)))
                }
            }
    }

    override suspend fun addToFavorites(id: String,current  :Boolean , result: (UiState<String>) -> Unit) {
        result.invoke(UiState.Loading)
        firestore.collection(DICTIONARY_COLLECTION)
            .document(id)
            .update("favorite",!current)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    result.invoke(UiState.Success("Successfully added!"))
                } else {
                    result.invoke(UiState.Error("Unknown Error"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Error(it.message.toString()))
            }
    }
}