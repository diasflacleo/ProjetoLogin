package com.example.leonardo.projetologin

import android.arch.persistence.room.*


@Entity(tableName = "usuario")
data class Usuario (

        @PrimaryKey(autoGenerate = true) var uid: Int = 0,
        @ColumnInfo(name = "usuario_email") var email: String = "",
        @ColumnInfo(name = "usuario_senha") var senha: String = ""
        )


@Entity(tableName = "noticia")
data class Noticia (

        @PrimaryKey(autoGenerate = true) var uid: Int = 0,
        @ColumnInfo(name = "noticia_titulo") var titulo: String = "",
        @ColumnInfo(name = "noticia_data") var data: String = "",
        @ColumnInfo(name = "noticia_texto") var texto: String = "",
        @ColumnInfo(name = "noticia_imagem") var imagem: String = ""
)


@Dao
interface RoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usu: Usuario)

    @Update
    fun updateUsuario(usu: Usuario)

    @Delete
    fun deleteUsuario(usu: Usuario)

    @Query("SELECT * FROM usuario")
    fun usuarios(): Array<Usuario>

    @Query("SELECT * FROM usuario WHERE usuario_email = :usuario_email AND usuario_senha = :usuario_senha")
    fun login(usuario_email: String, usuario_senha: String): Array<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoticia(news: Noticia)

    @Update
    fun updateNoticia(news: Noticia)

    @Delete
    fun deleteNoticia(news: Noticia)

    @Query("SELECT * FROM noticia")
    fun listaNoticias(): Array<Noticia>



}




@Database(entities = [(Usuario::class),(Noticia::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun romDao(): RoomDAO
}
