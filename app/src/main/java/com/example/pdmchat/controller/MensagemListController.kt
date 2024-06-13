package com.example.pdmchat.controller

import android.os.Message
import android.util.Log
import com.example.pdmchat.dao.MensagemDao
import com.example.pdmchat.dao.MensagemDaoImp
import com.example.pdmchat.models.Mensagem
import com.example.pdmchat.ui.MainActivity

class MensagemListController(private val mainActivity: MainActivity){

    private val messageDaoImpl: MensagemDao = MensagemDaoImp()

    fun getMessages() {
        val chatList = messageDaoImpl.retrieveMessages()
        if (chatList.isNotEmpty()) {
            mainActivity.runOnUiThread {
                mainActivity.updateMessagesList(chatList)
            }
        }
    }
}