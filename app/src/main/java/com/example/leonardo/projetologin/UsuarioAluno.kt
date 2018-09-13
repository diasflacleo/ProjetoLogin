package com.example.leonardo.projetologin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class UsuarioAluno {
    var nome: String? = null
    var email: String? = null
    var telefone: String? = null
    var endereco: String? = null
    var matricula: String? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(nome: String?, email: String?, telefone: String?,endereco: String?,matricula: String?) {
        this.nome = nome
        this.email = email
        this.telefone = telefone
        this.endereco = endereco
        this.matricula = matricula
    }
}