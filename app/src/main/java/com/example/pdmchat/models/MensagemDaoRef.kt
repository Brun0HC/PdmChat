package com.example.pdmchat.models

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MensagemDaoRef : MensagemDao {
    companion object {
        private const val MENSAGEM_LIST_ROOT_NODE = "mensagens"
    }

    // Referência ao nó principal do Firebase Realtime Database
    private val mensagemReference = FirebaseDatabase.getInstance().getReference(MENSAGEM_LIST_ROOT_NODE)

    // Lista local de mensagens
    private val mensagemList = mutableListOf<Mensagem>()

    init {
        // Adiciona um listener para mudanças nas mensagens no Firebase
        mensagemReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val mensagem = snapshot.getValue(Mensagem::class.java)
                // Adiciona a nova mensagem à lista local, se ainda não estiver presente
                if (mensagem != null && !mensagemList.contains(mensagem)) {
                    mensagemList.add(mensagem)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val mensagem = snapshot.getValue(Mensagem::class.java)
                // Atualiza a mensagem na lista local, se já estiver presente
                if (mensagem != null) {
                    val index = mensagemList.indexOfFirst { it.id == mensagem.id }
                    if (index != -1) {
                        mensagemList[index] = mensagem
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val mensagem = snapshot.getValue(Mensagem::class.java)
                // Remove a mensagem da lista local
                if (mensagem != null) {
                    mensagemList.remove(mensagem)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica neste caso
            }

            override fun onCancelled(error: DatabaseError) {
                // Tratamento de erros
                Log.e("Firebase", "Erro ao acessar o Firebase: ${error.message}")
            }
        })

        // Carrega mensagens iniciais do Firebase
        mensagemReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Carrega as mensagens uma única vez ao inicializar
                val mensagens = snapshot.children.mapNotNull { it.getValue(Mensagem::class.java) }
                if (mensagens.isNotEmpty()) {
                    mensagemList.addAll(mensagens.filterNot { mensagemList.contains(it) })
                    for (mensagem in mensagens) {
                        println(mensagem)
                    }
                } else {
                    println("Nenhuma mensagem encontrada.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Tratamento de erros
                Log.e("Firebase", "Erro ao carregar mensagens iniciais: ${error.message}")
            }
        })
    }

    // Cria uma nova mensagem no Firebase
    override fun createMensagem(mensagem: Mensagem): Int {
        val newMensagemRef = mensagemReference.push()
        val id = newMensagemRef.key ?: return -1
        mensagem.id = id.toInt()
        newMensagemRef.setValue(mensagem)
        Log.d("Firebase", "Enviando mensagem para o Firebase: $mensagem")
        return 1
    }

    // Retorna a lista de mensagens locais
    override fun retrieveMensagens(): MutableList<Mensagem> {
        return mensagemList
    }
}

