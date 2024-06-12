package com.example.pdmchat.dao

import com.example.pdmchat.models.Mensagem
import com.google.firebase.database.*

class MessageDao {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("messages")

    fun sendMessage(message: Mensagem, onSuccess: () -> Unit) {
        val messageId = database.push().key ?: return
        database.child(messageId).setValue(message)
            .addOnSuccessListener { onSuccess() }
    }

    fun getMessages(receiverId: String, onSuccess: (List<Mensagem>) -> Unit, onFailure: (DatabaseError) -> Unit) {
        database.orderByChild("receiver").equalTo(receiverId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Mensagem>()
                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(Mensagem::class.java)
                        message?.let { messages.add(it) }
                    }
                    onSuccess(messages)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error)
                }
            })
    }
}