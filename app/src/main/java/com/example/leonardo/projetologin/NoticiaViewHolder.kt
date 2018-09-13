package com.example.leonardo.projetologin

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView


class NoticiaViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    var txtTitulo: TextView
    var txtDescricao: TextView
    var txtData: TextView

    init {
        txtTitulo = itemView.findViewById(R.id.txtTitulo)
        txtDescricao = itemView.findViewById(R.id.txtDescricao)
        txtData = itemView.findViewById(R.id.txtData)
    }
}