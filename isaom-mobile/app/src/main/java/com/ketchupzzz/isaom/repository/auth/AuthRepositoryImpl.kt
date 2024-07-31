package com.ketchupzzz.isaom.repository.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Gender
import com.ketchupzzz.isaom.models.UserType
import com.ketchupzzz.isaom.models.Users


const val USERS_COLLECTION = "users"
class AuthRepositoryImpl(val auth : FirebaseAuth, private val firestore: FirebaseFirestore, val storage: FirebaseStorage) : AuthRepository {
    private var _users: MutableState<Users?> = mutableStateOf(null)

    override fun getUsers(): Users? {
       return  _users.value
    }

    override fun setUser(users: Users?) {
        _users.value = users
    }

    override fun login(email: String, password: String, result: (UiState<Users>) -> Unit) {
        result.invoke(UiState.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    val userId = authResult.result?.user?.uid
                    if (userId != null) {
                        firestore.collection(USERS_COLLECTION).document(userId)
                            .get()
                            .addOnCompleteListener { documentResult ->
                                if (documentResult.isSuccessful) {
                                    val user = documentResult.result?.toObject(Users::class.java)
                                    if (user != null) {
                                        result.invoke(UiState.Success(user))
                                    } else {
                                        result.invoke(UiState.Error("User data is null"))
                                    }
                                } else {
                                    result.invoke(UiState.Error("Failed to fetch user data"))
                                }
                            }
                    } else {
                        auth.signOut()
                        result.invoke(UiState.Error("User ID is null"))
                    }
                } else {
                    result.invoke(UiState.Error(authResult.exception?.message ?: "Authentication failed"))
                }
            }
    }


    override fun register(
        name: String,
        sectionID: String,
        type: UserType,
        gender: Gender,
        email: String,
        password: String,
        result: (UiState<Users>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                val results = it.result.user
                val newUser = Users(
                    id = results?.uid,
                    name = name,
                    email = email,
                    sectionID = sectionID, gender = gender,
                    type = type,

                )
                firestore.collection(USERS_COLLECTION)
                    .document(newUser.id ?: "")
                    .set(newUser)
                    .addOnCompleteListener {
                        result.invoke(UiState.Success(newUser))
                    }.addOnFailureListener {
                        result.invoke(UiState.Error(it.message.toString()))
                    }
            } else {
                result.invoke(UiState.Error("Unknown error"))
            }
        }.addOnFailureListener {
            result.invoke(UiState.Error(it.message.toString()))
        }
    }

    override fun logout(result: (UiState<String>) -> Unit) {
        result.invoke(UiState.Loading)
        auth.signOut()
        result.invoke(UiState.Success("Successfully Logged Out!"))
    }
}