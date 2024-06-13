package com.example.pdmchat.dao

import com.example.pdmchat.models.Mensagem
interface MensagemDao{
    fun createMessage(message: Mensagem): Int
    fun retrieveMessages(): MutableList<Mensagem>
}