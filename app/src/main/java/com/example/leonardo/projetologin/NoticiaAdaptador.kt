package com.example.leonardo.projetologin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


class NoticiaAdaptador (private val cxt: Context) : RecyclerView.Adapter<NoticiaViewHolder>() {

    private var dadosFirebase = mutableListOf<NoticiaIesb>()

    fun setData(dados: MutableList<NoticiaIesb>) {
        dadosFirebase = dados
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val v = LayoutInflater.from(cxt).inflate(R.layout.celula, parent, false)
        return NoticiaViewHolder(v)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = dadosFirebase[position]
        holder.txtTitulo.text = noticia.titulo
        //holder.txtDescricao.text = noticia.descricao
        holder.txtData.text = noticia.data
    }

    override fun getItemCount(): Int {
        return dadosFirebase.size
    }
}