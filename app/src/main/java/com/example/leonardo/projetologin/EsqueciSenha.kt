package com.example.leonardo.projetologin

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.content.Intent

import kotlinx.android.synthetic.main.esqueci_senha.*

class EsqueciSenha : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.esqueci_senha)
        supportActionBar?.title = "Esqueci Senha"

        button_enviarEmail.setOnClickListener {
            Toast.makeText(this@EsqueciSenha,"Um email com a nova senha será enviado para o endereço informado!", Toast.LENGTH_LONG).show()
            val intent = Intent(this@EsqueciSenha,Login::class.java)
            startActivity(intent)
        }
    }
}