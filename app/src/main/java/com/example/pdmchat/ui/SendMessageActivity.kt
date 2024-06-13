package com.example.pdmchat.ui

import android.os.Bundle
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmchat.controller.MensagemInsertController
import com.example.pdmchat.databinding.ActivitySendMessageBinding
import com.example.pdmchat.models.Mensagem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SendMessageActivity:AppCompatActivity() {
    private val amsgb: ActivitySendMessageBinding by lazy {
        ActivitySendMessageBinding.inflate(layoutInflater)
    }
    private lateinit var receiverET: AutoCompleteTextView
    private lateinit var receiverAdapter: ArrayAdapter<String>
    private lateinit var destinatariosList: MutableList<String>
    // Controller
    private val messageController: MensagemInsertController by lazy {
        MensagemInsertController()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amsgb.root)

        destinatariosList = mutableListOf()
        val sharedPreferences = getSharedPreferences("Destinatarios", Context.MODE_PRIVATE)
        val destinatariosSet = sharedPreferences.getStringSet("destinatarios", emptySet())
        destinatariosList.addAll(destinatariosSet ?: emptySet())

        receiverET = amsgb.receiverEditText
        receiverAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            destinatariosList
        )
        receiverET.setAdapter(receiverAdapter)

        val enviar = amsgb.sendButton
        val msg = amsgb.messageEditText.text
        val receivermsg = amsgb.receiverEditText.text.toString()

        enviar.setOnClickListener {
            val sendermsg = intent.getStringExtra("SENDER")

            newMsg(sendermsg ?: "")
            if (!destinatariosList.contains(receivermsg)) {
                destinatariosList.add(receivermsg)
                receiverAdapter.notifyDataSetChanged()

                val editor = sharedPreferences.edit()
                editor.putStringSet("destinatarios", destinatariosList.toSet())
                editor.apply()
            } else {
                Toast.makeText(this, "Digite o destinatário", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun newMsg(sendermsg: String){
        val receivermsg = amsgb.receiverEditText.text.toString()
        val msg = amsgb.messageEditText.text.toString()

        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val data = dateFormat.format(cal.time)
        val hora = timeFormat.format(cal.time)
        val new = Mensagem(
            sender = sendermsg,
            receiver = receivermsg,
            date = data,
            time = hora,
            message = msg
        )
        messageController.insertMessage(new)
        Toast.makeText(this, "Mensagem enviada", Toast.LENGTH_SHORT).show()

        finish()
    }

}


