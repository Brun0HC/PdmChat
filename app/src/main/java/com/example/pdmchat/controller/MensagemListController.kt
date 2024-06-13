package com.example.pdmchat.controller

import com.example.pdmchat.dao.MensagemDao
import com.example.pdmchat.dao.MensagemDaoImp

class MensagemListController(private val mensagemDaoImpl: MensagemDao = MensagemDaoImp()){

    fun getMessages() = mensagemDaoImpl.retrieveMessages()
}