package com.example.pdmchat.dao

import android.util.Log
import com.example.pdmchat.models.Mensagem
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class MensagemDaoImp : MensagemDao {
    companion object {
        private const val MESSAGE_LIST_ROOT_NODE = "messages"
    }

    private val messageRtDbFbReference = FirebaseDatabase.getInstance().getReference(MESSAGE_LIST_ROOT_NODE)

    // Simula uma consulta no banco de dados
    private val messageList = mutableListOf<Mensagem>()
    private var isFirstValueEvent = true

    init {
        messageRtDbFbReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Mensagem::class.java)
                if (message != null) {
                    messageList.add(message)
                    Log.d("MensagemDaoImp", "Mensagem adicionada: $message")
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Mensagem::class.java)
                if (message != null) {
                    val index = messageList.indexOfFirst { it.id == message.id }
                    if (index != -1) {
                        messageList[index] = message
                        Log.d("MensagemDaoImp", "Mensagem atualizada: $message")
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val message = snapshot.getValue(Mensagem::class.java)
                if (message != null) {
                    val index = messageList.indexOfFirst { it.id == message.id }
                    if (index != -1) {
                        messageList.removeAt(index)
                        Log.d("MensagemDaoImp", "Mensagem removida: $message")
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não necessário neste caso
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })

        // Chamado uma única vez sempre que o aplicativo for executado
        messageRtDbFbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isFirstValueEvent) {
                    isFirstValueEvent = false
                    val chats = snapshot.children.mapNotNull { it.getValue(Mensagem::class.java) }
                    if (chats.isNotEmpty()) {
                        messageList.addAll(chats.filterNot { messageList.contains(it) })
                        for (chat in chats) {
                            println(chat)
                        }
                    } else {
                        println("Nenhuma mensagem encontrada.")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }

    override fun createMessage(message: Mensagem): Int {
        val messageRf = messageRtDbFbReference.push()
        val id = messageRf.key ?: return -1
        message.id = id
        messageRf.setValue(message)
        Log.d("Firebase", "Enviando mensagem para o Firebase: $message")
        return 1
    }

    override fun retrieveMessages(): MutableList<Mensagem> {
        return messageList
    }
}