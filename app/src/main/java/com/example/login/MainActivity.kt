package com.example.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.login.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.Result.Companion.success

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnInLogin.setOnClickListener{
            val Email = binding.edtInCorreo.text.toString()
            val Password = binding.edtInContra.text.toString()

            when {
                Email.isEmpty() || Password.isEmpty() -> {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Los campos de correo o contraseña están vacíos. Intente nuevamente.",
                        Toast.LENGTH_SHORT).show()
                } else -> {
                SignIn(Email,Password)
                }
            }
        }
        binding.txtInRegistro.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }

    private fun SignIn (email: String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Su ingreso ha sido exitoso.",
                        Toast.LENGTH_SHORT).show()
                        reaload()

                } else {
                    Toast.makeText(baseContext, "Su ingreso ha sido erróneo. Intente nuevamente.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun reaload() {
        val intent = Intent( this, MainActivity3::class.java)
        this.startActivity(intent)
    }
}
