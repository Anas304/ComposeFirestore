package com.example.firestorecomposedemo.model

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class storedData() {

    val db = FirebaseFirestore.getInstance()

    // Create a collection in firestore
    fun CreateNewCollection(){

        val db = FirebaseFirestore.getInstance()

        // Pass data onto the firestore db, it must be of type hashmap
        val user = hashMapOf(
            "first" to "Michael",
            "last" to "C",
            "Opsparing" to 10000
        )

        // add the user data to the collection
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("FB", "onCreate: ${it.id}")
            }

            .addOnFailureListener {
                Log.d("FB", "onCreate: ${it}")
            }
    }

    // Update fields in document or create if they don't exist
    fun updateData(name: String, savings: Int){
        val updater = db.collection("users").document("holidaySavings")
        updater.update("name", name)
        updater.update("savings", savings)

    }

    // Read data from Firestore
    fun readFirestoreData(){
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("read", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("read", "Error getting documents.", exception)
            }
    }

    // READ DATA TEST
    fun readDataTest(){
        val docRef = db.collection("users").document("holidaySavings")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Rtest", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("Rtest", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Rfail", "get failed with ", exception)
            }
    }

    fun readDataTestFinal(): String{
        val docRef = db.collection("users").document("holidaySavings")
        var returnTest = ""
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Rtest", "DocumentSnapshot data: ${document.data}")

                    // I want to return this so i can use it in a composable text view
                    returnTest = document.get("name").toString()
                } else {
                    Log.d("Rtest", "No such document")
                }
            }
            .addOnFailureListener {  exception ->
                Log.d("Rfail", "get failed with ", exception)
            }
        return returnTest
    }

    // Answer from stackoverflow
    /*
    The Firebase call is async, which means it will not return the data immediately.
    This is why the API uses a callback. Therefore, your function readDataTestFinal is always returning an empty string.
    One solution you can use is transform your function in a suspend function and call it using a coroutine scope.
    */
    suspend fun readDataFromFireStoreFieldName(): String {
        val docRef = db.collection("users").document("holidaySavings")
        return suspendCoroutine { continuation ->
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        continuation.resume(document.get("name").toString())
                    } else {
                        continuation.resume("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}