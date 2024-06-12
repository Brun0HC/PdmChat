package com.example.pdmchat.models

interface MensagemDao {
    fun createMensagem(mensagem: Mensagem): Int
    fun retrieveMensagens(): MutableList<Mensagem>
}
