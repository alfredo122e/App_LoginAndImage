package com.example.login

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.ActivityMain2Binding
import com.example.login.databinding.ActivityMain3Binding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class MainActivity3 : AppCompatActivity() {

  lateinit var binding: ActivityMain3Binding
  lateinit var ImageUri : Uri
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImageView.setOnClickListener{
            selectImage()
        }

        binding.btnImage.setOnClickListener{
            uploadImage()
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Espere mientras subimos su imagen...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")

        storageReference.putFile(ImageUri).
        addOnSuccessListener {
            binding.imageView.setImageURI(null)
            Toast.makeText(this@MainActivity3, "¡La imagen se subió correctamente!", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing)progressDialog.dismiss()

        }.addOnFailureListener{
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@MainActivity3, "¡Ha ocurrido un error al subir la imagen!", Toast.LENGTH_SHORT).show()

        }
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
            binding.imageView.setImageURI(ImageUri)
        }
    }
}