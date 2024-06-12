package com.example.pdmchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pdmchat.R

import com.example.pdmchat.models.Mensagem

class MensagemAdapter(context: Context, private val messageList: MutableList<Mensagem>):
    ArrayAdapter<Mensagem>(context, R.layout.message_tile, messageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mensagem = messageList[position]
        val messageTileView: View
        val holder: TileContactHolder

        if (convertView == null) {
            messageTileView = LayoutInflater.from(context).inflate(R.layout.message_tile, parent, false)
            holder = TileContactHolder(
                messageTileView.findViewById(R.id.remetenteTv),
                messageTileView.findViewById(R.id.mensagemTv),
                messageTileView.findViewById(R.id.created_atTv)
            )
            messageTileView.tag = holder
        } else {
            messageTileView = convertView
            holder = messageTileView.tag as TileContactHolder
        }

        holder.apply {
            remetenteTv.text = mensagem.sender
            mensagemTv.text = mensagem.message
            createdTv.text = "${mensagem.date} ${mensagem.time}"
        }

        return messageTileView
    }

    private data class TileContactHolder(
        val remetenteTv: TextView,
        val mensagemTv: TextView,
        val createdTv: TextView
    )
}