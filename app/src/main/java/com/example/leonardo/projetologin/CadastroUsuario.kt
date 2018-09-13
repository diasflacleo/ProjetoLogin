package com.example.leonardo.projetologin

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase;
import kotlinx.android.synthetic.main.cadastro_usuario.*
import java.io.File
import java.util.jar.Manifest
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.io.ByteArrayOutputStream


class CadastroUsuario : AppCompatActivity() {

    private var PERMISSION_REQUEST_CODE = 200
    private var TAKE_PHOTO_REQUEST = 101
    var mCurrentPhotoPath : String =  ""

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_usuario)
        supportActionBar?.title = "Cadastro de Usuário"


        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "room-database"
        ).allowMainThreadQueries().build()



            button_cadastrar_usuario.setOnClickListener {


           if (editText_email_cadastro.text.toString().trim().isNullOrEmpty()) {
                editText_email_cadastro?.error = "email obrigatório."
            } else {
               if(editText_senha_cadastro.text.toString().trim().isNullOrEmpty()){
                   editText_senha_cadastro?.error = "senha obrigatória."
               } else{
                   if(editText_confirmacao_cadastro.text.toString().trim().isNullOrEmpty()) {
                       editText_confirmacao_cadastro?.error = "confirmacao obrigatória."
                   }else{

                       if(!editText_senha_cadastro.text.toString().equals(editText_confirmacao_cadastro.text.toString())){
                           Toast.makeText(this@CadastroUsuario, "Senhas diferentes. Redigite.",
                                   Toast.LENGTH_LONG).show()
                           editText_senha_cadastro.requestFocus();
                       }else{
                           val usuario = Usuario(email = editText_email_cadastro.text.toString(),
                                   senha = editText_senha_cadastro.text.toString())

                           // val produto = Produto(nome = "produto test1", preco = "123", descricao = "test description", imagem = "")

                          /* db.romDao().insertUsuario(usuario)
                           Toast.makeText(this@CadastroUsuario, "Usuario cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                           val intent = Intent(this@CadastroUsuario,Login::class.java)
                           startActivity(intent)
                           finish()*/

                           //---------------cria o usuario no firebase ---------------

                           mAuth.createUserWithEmailAndPassword(editText_email_cadastro.text.toString(),
                                   editText_senha_cadastro.text.toString())
                                   .addOnCompleteListener(this){task ->
                                       if (task.isSuccessful){
                                           val user = mAuth.getCurrentUser()
                                           Toast.makeText(this@CadastroUsuario, "Usuário cadastrado com sucesso!",Toast.LENGTH_LONG)
                                           writeNewUser(user!!.getUid(), editText_email_cadastro.text.toString())


                                           val intent = Intent(this@CadastroUsuario, Login::class.java)
                                           startActivity(intent)
                                           finish()
                                       }
                                   }

                       }

                   }
               }
            }






        }



    }

    fun writeNewUser(userId: String,  email: String) {
        var usuarioAluno = UsuarioAluno()
        usuarioAluno.nome = "a definir";
        usuarioAluno.email = email
        usuarioAluno.telefone = "(00)00000-0000"
        usuarioAluno.endereco = "a definir"
        usuarioAluno.matricula = "00000000"

        FirebaseDatabase.getInstance().getReference().child("aluno").child(userId).setValue(usuarioAluno)
    }



}

