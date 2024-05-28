package com.exmpale.mimi.Activitys

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.exmpale.mimi.R
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
//    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        mAuth = FirebaseAuth.getInstance()

        val editor = getSharedPreferences("MyUser", MODE_PRIVATE)
        binding.emailInput.setText(editor.getString("email", null))



        binding.dontHaveACBtn.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        binding.signUpBtn.setOnClickListener {


            val email = binding.emailInput.text.toString()
            val password = binding.passInput.text.toString()

            if (email.isEmpty()) {
                binding.emailInput.error = "Please enter email!"

            } else if (password.isEmpty()) {

                binding.passInput.error = "Please enter password!"

            } else{
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.loginForUser(email, password)
                }
            }

        }


        viewModel.loginSuccess.observe(this) { isSuccess ->
            if (isSuccess) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT)
                    .show()

            } else {

                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT)
                    .show()

            }
        }


        binding.forgetPasswords.paintFlags = binding.forgetPasswords.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    }
}