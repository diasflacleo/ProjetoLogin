package com.example.leonardo.projetologin

import android.arch.persistence.room.Room
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import kotlinx.android.synthetic.main.principal.*
import java.text.NumberFormat
import java.util.*

class Principal : AppCompatActivity() {

    private var listNoticias = ArrayList<Noticia>()

    var noticiasAdapter = NoticiasAdapter(this, listNoticias)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)
        supportActionBar?.title = "Tela Principal"

        /*var tb_main : android.support.v7.widget.Toolbar = findViewById(R.id.toolbar_app)
        tb_main.setTitleTextColor(Color.WHITE)
        setSupportActionBar(tb_main)*/


        button_alterar_usuario.setOnClickListener{

                Toast.makeText(this,"Perfil do usuário", Toast.LENGTH_LONG)
                val intent = Intent(this@Principal,AlteracaoUsuario::class.java)
                startActivity(intent)


        }

        button_ver_noticia.setOnClickListener{

            Toast.makeText(this,"Perfil do usuário", Toast.LENGTH_LONG)
            val intent = Intent(this@Principal,ListaNoticias::class.java)
            startActivity(intent)


        }


        //-----------------------
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "room-database"
        ).allowMainThreadQueries().build()

        //--------------------------------------------
        // dummy data pra ser apagado depois
       /*
        db.romDao().insertNoticia(Noticia(0,"Apps Android são fáceis", "10/11/2017","Apps android são fácies de implementar segundo um estudo feito pelo IESB"))
        db.romDao().insertNoticia(Noticia(1,"Windows phone em decadência", "01/01/2018","A Microsoft anunciou oficialmente o fim do suporte do Windows phone"))
        db.romDao().insertNoticia(Noticia(2,"titulo3", "10/11/2017","Apps android são fácies de implementar segundo um estudo feito pelo IESB"))
        db.romDao().insertNoticia(Noticia(3,"titulo4", "01/01/2018","A Microsoft anunciou oficialmente o fim do suporte do Windows phone"))
        db.romDao().insertNoticia(Noticia(4,"titulo5", "10/11/2017","Apps android são fácies de implementar segundo um estudo feito pelo IESB"))
        db.romDao().insertNoticia(Noticia(5,"titulo6", "01/01/2018","A Microsoft anunciou oficialmente o fim do suporte do Windows phone"))
        db.romDao().insertNoticia(Noticia(6,"titulo7", "10/11/2017","Apps android são fácies de implementar segundo um estudo feito pelo IESB"))
        db.romDao().insertNoticia(Noticia(7,"titulo8", "01/01/2018","A Microsoft anunciou oficialmente o fim do suporte do Windows phone"))
*/


        //---------------------------------------------------

        //busca os a lista de Noticias do banco de dados e carrega na lista de Noticias.
        var noticias_news: Array<Noticia> = db.romDao().listaNoticias()

        for (news in noticias_news){
            listNoticias.add(Noticia(news.uid,news.titulo,news.data ,news.texto, news.imagem))
        }



        //-------------------------------
        noticiasAdapter = NoticiasAdapter(this, listNoticias)
        listView_lista.adapter = noticiasAdapter
        listView_lista.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Clicou em ' " + listNoticias[position].titulo+ " '", Toast.LENGTH_SHORT).show()
        }



        listView_lista.onItemLongClickListener = AdapterView.OnItemLongClickListener{ adapterView, view, position, id ->

            Toast.makeText(this, "excluir o item ' " + listNoticias[position].titulo+ " '", Toast.LENGTH_SHORT).show()

            var alertBuilder = AlertDialog.Builder(this)

            with (alertBuilder){
                setTitle("Excluir Item")
                setMessage("Deseja excluir a '"+listNoticias[position].titulo+ "' ?")

                setPositiveButton("Sim"){
                    dialog, which ->

                    //-----------------------
                    removerItemLista(view, position)
                    db.romDao().deleteNoticia(listNoticias[position])
                    Toast.makeText(this@Principal, "Noticia excluído com sucesso!", Toast.LENGTH_LONG).show()

                    //-------------------------
                    //listNoticias.removeAt(position)
                    // depois de removido, a lista é atualizada
                    //NoticiasAdapter.notifyDataSetChanged()
                    //   dialog.dismiss()

                }

                setNegativeButton("Não"){
                    dialog, which ->

                    dialog.dismiss()
                }

            }


            val alert = alertBuilder.create()
            //alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
            alert.show()

            var buttonYes: Button = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            buttonYes.setBackgroundColor(Color.parseColor("#339933"))
            buttonYes.setTextColor(Color.WHITE)

            var buttonNo: Button = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            buttonNo.setBackgroundColor(Color.parseColor("#990000"))
            buttonNo.setTextColor(Color.WHITE)

            true

        }



    }

    //-------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id: Int = item!!.itemId
        if (id == R.id.item_perfilUsuario){
            Toast.makeText(this,"Perfil do usuário", Toast.LENGTH_LONG)
            val intent = Intent(this@Principal,AlteracaoUsuario::class.java)
            startActivity(intent)

            return true
        }
        if (id == R.id.item_verNoticias){
            Toast.makeText(this,"Ver Notícias", Toast.LENGTH_LONG)
            val intent = Intent(this@Principal,ListaNoticias::class.java)
            startActivity(intent)

            return true
        }
        if (id == R.id.item_incluirNoticias){
            Toast.makeText(this,"Perfil do usuário", Toast.LENGTH_LONG)
            //  val intent = Intent(this@MainActivity,CadastroNoticia::class.java)
            //startActivity(intent)

            return true
        }
        if (id == R.id.item_irChat){
            Toast.makeText(this,"Perfil do usuário", Toast.LENGTH_LONG)
            //  val intent = Intent(this@MainActivity,CadastroNoticia::class.java)
            //startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //-------------------------------




    //----------
    fun removerItemLista(view: View, position: Int ){
        var animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.splash_fade_out)
        view.startAnimation(animation)
        var handle: Handler = Handler()
        handle.postDelayed( {

            listNoticias.removeAt(position)
            // depois de removido, a lista é atualizada
            noticiasAdapter.notifyDataSetChanged()
            animation.cancel()


        },1000)
    }

    //---------


    inner class NoticiasAdapter : BaseAdapter {

        private var listaNoticia = ArrayList<Noticia>()
        private var context: Context? = null

        constructor(context: Context, listaNoticia: ArrayList<Noticia>) : super() {
            this.listaNoticia = listaNoticia
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.item_lista, parent, false)
                vh = ViewHolder(view)
                view.tag = vh

            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.textView_tituloNoticia.text = listaNoticia[position].titulo
            vh.textView_dataNoticia.text = listaNoticia[position].data
            vh.textView_textoNoticia.text = listaNoticia[position].texto

            return view
        }

        override fun getItem(position: Int): Any {
            return listaNoticia[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listaNoticia.size
        }
    }

    private class ViewHolder(view: View?) {
        val textView_tituloNoticia: TextView
        val textView_dataNoticia: TextView
        val textView_textoNoticia: TextView

        init {
            this.textView_tituloNoticia = view?.findViewById(R.id.textView_tituloNoticia) as TextView
            this.textView_dataNoticia = view?.findViewById(R.id.textView_dataNoticia) as TextView
            this.textView_textoNoticia = view?.findViewById(R.id.textView_textoNoticia) as TextView

        }
    }


    override fun onResume() {
        super.onResume()
        noticiasAdapter.notifyDataSetChanged()
    }

}
