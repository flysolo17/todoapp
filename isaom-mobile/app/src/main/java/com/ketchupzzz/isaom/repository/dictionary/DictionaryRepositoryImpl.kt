package com.ketchupzzz.isaom.repository.dictionary

import com.google.firebase.firestore.FirebaseFirestore
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.dictionary.Dictionary
import com.ketchupzzz.isaom.models.dictionary.Favorites

const val DICTIONARY_COLLECTION  = "dictionary";
const val  FAVORITE_REPOSITORY = "favorites";
class DictionaryRepositoryImpl(private val firestore: FirebaseFirestore) : DictionaryRepository {

    override suspend fun getAllDictionaries(
        result: (UiState<List<Dictionary>>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(DICTIONARY_COLLECTION)
            .limit(100)
            .addSnapshotListener { value, error ->
                error?.let {
                    result.invoke(UiState.Error(it.message.toString()))
                }
                value?.let {
                    result.invoke(UiState.Success(it.toObjects(Dictionary::class.java)))
                }
            }
    }

    override suspend fun addToFavorites(
        favorites: Favorites,
        result: (UiState<String>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(FAVORITE_REPOSITORY)
            .document(favorites.id)
            .set(favorites)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result(UiState.Success("Successfully Added"))
                } else {
                    result.invoke(UiState.Error("Unknown error"))
                }
            }.addOnFailureListener {
                result(UiState.Error(it.message.toString()))
            }

    }

    override suspend fun getAllFavorites(
        userID: String,
        result: (UiState<List<Favorites>>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        firestore.collection(FAVORITE_REPOSITORY)
            .whereEqualTo(
                "userID",
                userID
            ).addSnapshotListener { value, error ->
                value?.let {
                    result.invoke(UiState.Success(it.toObjects(Favorites::class.java)))
                }
                error?.let {
                    result.invoke(UiState.Error(it.message.toString()))
                }
            }
    }

    override suspend fun removeToFavorites(id: String, result: (UiState<String>) -> Unit) {
        firestore.collection(FAVORITE_REPOSITORY)
            .document(id)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result(UiState.Success("Successfully Removed"))
                } else {
                    result.invoke(UiState.Error("Unknown error"))
                }
            }.addOnFailureListener {
                result(UiState.Error(it.message.toString()))
            }
    }


}