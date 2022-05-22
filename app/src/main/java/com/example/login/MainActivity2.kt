package com.example.login

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.login.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class MainActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth


        binding.btnSiSignin.setOnClickListener {
            val Name = binding.edtSiNombre.text.toString()
            val Email = binding.edtSiCorreo.text.toString()
            val Password = binding.edtSiContra.text.toString()
            val RPassword = binding.edtSiRepeat.text.toString()

            val passwordRegex = Pattern.compile("^" +
                        "(?=.*[-@#$%^&+=])" +
                        ".{8,}" +
                        "$")

            if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                Toast.makeText(
                    baseContext, "Ingrese un correo electrónico válido.",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (Password.isEmpty() || !passwordRegex.matcher(Password).matches()) {
                Toast.makeText(
                    baseContext, "La contraseña es débil.",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (Password != RPassword) {
                Toast.makeText(
                    baseContext, "Confirme su contraseña.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (Name.isEmpty()) {
                Toast.makeText(
                    baseContext, "Ingrese su nombre.",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                createAccount(Email, Password)
            }
        }

        binding.btnSiBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.txtSiRegistro.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun createAccount(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "¡Cuenta creada correctamente!",
                        Toast.LENGTH_SHORT).show()
                    reaload()
                } else {

                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
        }
    private fun reaload() {
        val intent = Intent( this, MainActivity::class.java)
        this.startActivity(intent)
    }
}