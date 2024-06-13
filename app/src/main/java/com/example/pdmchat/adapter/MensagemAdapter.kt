package com.example.pdmmensagem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pdmchat.R
import com.example.pdmchat.models.Mensagem

class MensagemAdapter(context: Context, private val msgList: MutableList<Mensagem>) :
    ArrayAdapter<Mensagem>(context, R.layout.message_tile, msgList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mensagem = msgList[position]
        val mensagemTileView: View
        val holder: TileContactHolder

        if (convertView == null) {
            mensagemTileView = LayoutInflater.from(context).inflate(R.layout.message_tile, parent, false)
            holder = TileContactHolder(
                mensagemTileView.findViewById(R.id.remetenteTv),
                mensagemTileView.findViewById(R.id.mensagemTv),
                mensagemTileView.findViewById(R.id.created_atTv)
            )
            mensagemTileView.tag = holder
        } else {
            mensagemTileView = convertView
            holder = mensagemTileView.tag as TileContactHolder
        }

        holder.apply {
            remetenteTv.text = mensagem.sender
            mensagemTv.text = mensagem.message
            dateTimeTv.text = "${mensagem.date} ${mensagem.time}"
        }

        return mensagemTileView
    }

    private data class TileContactHolder(
        val remetenteTv: TextView,
        val mensagemTv: TextView,
        val dateTimeTv: TextView
    )
}
