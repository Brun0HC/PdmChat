package com.example.pdmchat.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.pdmchat.R
import com.example.pdmchat.databinding.ActivityMainBinding
import com.example.pdmchat.controller.MensagemListController
import com.example.pdmchat.models.Mensagem
import com.example.pdmchat.ui.SendMessageActivity
import com.example.pdmmensagem.adapter.MensagemAdapter

class MainActivity : AppCompatActivity() {
    companion object {
        const val GET_MESSAGES = 1 // Buscar no banco
        const val GET_MESSAGES_INTERVAL = 2000L // Intervalo de busca
    }

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val messageList: MutableList<Mensagem> = mutableListOf()

    // Controller
    private val messageController: MensagemListController by lazy {
        MensagemListController(this)
    }

    // Adapter
    private val messageAdapter: MensagemAdapter by lazy {
        MensagemAdapter(this, messageList)
    }

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        username = intent.getStringExtra("USERNAME")!!

        amb.SendBt.setOnClickListener {
            val intent = Intent(this, SendMessageActivity::class.java)
            intent.putExtra("SENDER", username)
            startActivity(intent)
        }

        // Configurar ListView
        amb.messageLv.adapter = messageAdapter

        // Configurar Handler para atualizar a UI
        uiUpdaterHandler.apply {
            sendMessageDelayed(
                Message.obtain().apply {
                    what = GET_MESSAGES
                },
                GET_MESSAGES_INTERVAL
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitApp -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateMessagesList(messages: MutableList<Mensagem>) {
        messageList.clear()
        messageList.addAll(messages)
        messageAdapter.notifyDataSetChanged()
    }

    val uiUpdaterHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            if (msg.what == GET_MESSAGES) {
                // Busca os contatos
                //val messages = messageController.getMessages()
                //updateMessagesList(messages)
                messageController.getMessages()
                sendMessageDelayed(
                    Message.obtain().apply {
                        what = GET_MESSAGES
                    },
                    GET_MESSAGES_INTERVAL
                )
            }else {
                msg.data.getParcelableArrayList<Mensagem>("MESSAGE_ARRAY")?.let { _chatArray ->
                    Log.d("MainActivity", "Chats recebidos: $_chatArray")

                    updateChatsList(_chatArray.toMutableList(), username)
                }
            }
        }
    }

    fun updateChatsList(chats: MutableList<Mensagem>, username: String) {
        Log.e("CHATS BRUNAO", chats.toString())
        val filteredChats = chats.filter { it.receiver == username }
        Log.e("CHATS BRUNAO filteredChats", filteredChats.toString())
        messageList.clear()
        messageList.addAll(filteredChats)
        messageAdapter.notifyDataSetChanged()
    }
}
