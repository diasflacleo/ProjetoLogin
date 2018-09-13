package com.example.leonardo.projetologin

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaNoticias:AppCompatActivity(){

    var dadosFirebase = mutableListOf<NoticiaIesb>()
    lateinit var recyclerView: RecyclerView
    lateinit var adaptador: NoticiaAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_noticias)
        supportActionBar?.title = "Lista Noticias"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        adaptador = NoticiaAdaptador(this)
        recyclerView.adapter = adaptador

        recuperarNoticias()
    }

    private fun recuperarNoticias() {
        val db = FirebaseDatabase.getInstance()
        val dbRef = db.getReference("/noticia")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dadosFirebase.clear()
                snapshot.children.forEach { snap ->
                    val noticia = snap.getValue(NoticiaIesb::class.java)
                    dadosFirebase.add(noticia!!)
                    Log.d("FIREBASE", noticia.toString())
                    adaptador.setData(dadosFirebase)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}