package com.example.leonardo.projetologin

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar?.title = "Login"

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "room-database"
        ).allowMainThreadQueries().build()


        button_cadastrar.setOnClickListener{
            val intent = Intent(this@Login,CadastroUsuario::class.java)
            Toast.makeText(this,"Cadastrar novo usuário.", Toast.LENGTH_LONG)
            startActivity(intent)



        }

        button_esqueci.setOnClickListener{
            val intent = Intent(this@Login,EsqueciSenha::class.java)
            Toast.makeText(this,"Esqueci a senha.", Toast.LENGTH_LONG)
            startActivity(intent)



        }

        button_entrar.setOnClickListener{
            if(editText_email.text.toString().trim().isNullOrEmpty()){
                editText_email?.error = "Email obrigatório."
                editText_email.requestFocus()

            }else{
                if(editText_senha.text.toString().trim().isNullOrEmpty()){
                    editText_senha?.error = "Senha obrigatória."
                    editText_senha.requestFocus()
                }else{
                    /* var usuario: Array<Usuario> = db.romDao().login(editText_email.text.toString(),editText_senha.text.toString())

                     if(usuario.isNotEmpty()){
                         Toast.makeText(this@Login, "Usuário Autenticado!", Toast.LENGTH_LONG).show()
                         val intent = Intent(this@Login,Principal::class.java)
                         startActivity(intent)
                         finish()
                     }else{
                         Toast.makeText(this@Login, "Usuário e/ou senha não conferem.", Toast.LENGTH_LONG).show()
                         editText_senha.requestFocus()
                     }*/

                    // When a user signs in to your app, pass the user's email address and password to signInWithEmailAndPassword
                    mAuth.signInWithEmailAndPassword(editText_email.text.toString(), editText_senha.text.toString())
                            .addOnCompleteListener(this) { task ->


                                // tentativa de login
                                if (task.isSuccessful) {
                                    Log.w("TAG", "signInWithEmail - OK ")
                                    Toast.makeText(this@Login, "Autenticado!.",
                                            Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@Login,Principal::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    Log.w("TAG", "signInWithEmail - fail ", task.exception)
                                    Toast.makeText(this@Login, "Erro na autenticação: " + task.exception, Toast.LENGTH_LONG).show()
                                }
                            }

                }
            }




        }


    }


}
