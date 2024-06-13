package com.example.pdmchat.controller

import com.example.pdmchat.dao.MensagemDao
import com.example.pdmchat.dao.MensagemDaoImp
import com.example.pdmchat.models.Mensagem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MensagemInsertController(private val mensagemDaoImpl: MensagemDao = MensagemDaoImp()){
    fun insertMessage(msg: Mensagem) {
        GlobalScope.launch {
            mensagemDaoImpl.createMessage(msg)
        }
    }
    //fun insertMessage(mensagem: Mensagem) = mensagemDaoImpl.createMessage(mensagem)

}