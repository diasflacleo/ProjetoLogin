package com.example.leonardo.projetologin

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.alteracao_usuario.*
import java.io.File
import com.google.firebase.auth.FirebaseUser
import android.R.attr.name
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.leonardo.projetologin.UsuarioAluno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


class AlteracaoUsuario : AppCompatActivity() {

    private var PERMISSION_REQUEST_CODE = 200
    private var TAKE_PHOTO_REQUEST = 101
    private var WRITE_REQUEST_CODE = 20

    var mCurrentPhotoPath: String = ""

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mUsuarioAluno: DatabaseReference =  FirebaseDatabase.getInstance().getReference("aluno");

    private var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.alteracao_usuario)
        supportActionBar?.title = "Alteração de Usuário"

        //----------------
        // Get a reference to our posts
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("aluno")

        val username = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        var mAlunoReference = FirebaseDatabase.getInstance().getReference("aluno").child(username)

// Attach a listener to read the data at our posts reference

        val alunoObjectListener = object : ValueEventListener {
            override  fun onDataChange(dataSnapshot: DataSnapshot) {
                val aluno = dataSnapshot.getValue(UsuarioAluno::class.java)
                // if (user != null){
                editText_nome.setText(aluno!!.nome.toString());
                editText_email_alteracao.setText( user!!.getEmail().toString());
                editText_telefone.setText(aluno!!.telefone.toString());
                editText_endereco.setText(aluno!!.endereco.toString());
                editText_matricula.setText(aluno!!.matricula.toString());
                // }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                System.out.println("The read failed: " + databaseError.getCode())
            }
        }
        mAlunoReference!!.addValueEventListener(alunoObjectListener)


        imageView_imagemUsuario.setOnClickListener{

            Toast.makeText(this@AlteracaoUsuario,"Teste Foto", Toast.LENGTH_LONG).show()
            validarPermissoes()



        }

        button_alterar_usuario.setOnClickListener {


            val usuarioAluno = UsuarioAluno()
            usuarioAluno.nome = editText_nome.text.toString()
            usuarioAluno.telefone = editText_telefone.text.toString()
            usuarioAluno.endereco= editText_endereco.text.toString()
            usuarioAluno.matricula =editText_matricula.text.toString()
            mAlunoReference.setValue(usuarioAluno)
            Toast.makeText(this@AlteracaoUsuario,"Dados alterados com sucesso!", Toast.LENGTH_LONG).show()
            //mDatabase.child("aluno").child("a1").setValue(usuarioAluno)
            //FirebaseDatabase.getInstance().getReference().child("aluno").child(user!!.getUid()).setValue("kkkk");

        }

    }

    fun validarPermissoes(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this@AlteracaoUsuario, "Permissão não concedida para 'Camera'", Toast.LENGTH_LONG).show()

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),PERMISSION_REQUEST_CODE )


        }else{
            Toast.makeText(this@AlteracaoUsuario, "Permissão concedida para 'Camera'", Toast.LENGTH_LONG).show()
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this@AlteracaoUsuario, "Permissão não concedida \n para 'Write External Storage ", Toast.LENGTH_LONG).show()

                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),20 )
                Toast.makeText(this@AlteracaoUsuario, "Permissão concedida \n para 'Write External Storage ", Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this@AlteracaoUsuario, "Permissão concedida \n para 'Write External Storage ", Toast.LENGTH_LONG).show()
                abrirCamera()
            }

        }
        //abrirCamera()
    }

    fun abrirCamera(){
        Toast.makeText(this@AlteracaoUsuario, "Abrindo Camera...", Toast.LENGTH_LONG).show()

        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_REQUEST){
            processaFotoCapturada()
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun processaFotoCapturada(){
        val cursor =  contentResolver.query(Uri.parse(mCurrentPhotoPath),
                Array(1){android.provider.MediaStore.Images.ImageColumns.DATA},
                null,null,null)
        cursor.moveToFirst()
        val photoPath = cursor.getString(0)
        cursor.close()
        val file = File(photoPath)
        val uri = Uri.fromFile(file)

        imageView_imagemUsuario.setImageBitmap(decodificaBitmatDeArquivo(file.absolutePath,250,250))

    }

    fun decodificaBitmatDeArquivo(path: String, reqWidth: Int, reqHeight: Int): Bitmap {

        var options : BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        var height:Int = options.outHeight
        var width: Int = options.outWidth

        options.inPreferredConfig = Bitmap.Config.RGB_565
        var inSampleSize = 1

        if(height > reqHeight){
            inSampleSize = (Math.round(height as Float)/(reqHeight as Float)) as Int
        }

        var expectedWidth : Int = width /inSampleSize

        if(expectedWidth > reqWidth){
            inSampleSize = (Math.round(width as Float)/(reqWidth as Float)) as Int
        }

        options.inSampleSize = inSampleSize

        options.inJustDecodeBounds = false

        return BitmapFactory.decodeFile(path,options)
    }



}