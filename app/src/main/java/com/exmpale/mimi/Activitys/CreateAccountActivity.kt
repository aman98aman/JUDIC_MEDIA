package com.exmpale.mimi.Activitys

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {

    val binding by lazy { ActivityCreateAccountBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    val auth = FirebaseAuth.getInstance()
    var userId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.signUpBtn.setOnClickListener {

            registerNewUser()

        }

        binding.alreadyHaveACBtn.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
        }

        viewModel.registerSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration successful
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            } else {
                // Registration failed
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }

       binding.forgetPasswords.paintFlags = binding.forgetPasswords.paintFlags or Paint.UNDERLINE_TEXT_FLAG

//        val spannableString = SpannableString(binding.alreadyHaveACBtn.text)
//        val clickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                // Perform your action here (e.g., open an email client)
//                // For example:
//                val intent = Intent(Intent.ACTION_SENDTO)
//                intent.data = Uri.parse("mailto:") // Use email client
//                startActivity(intent)
//            }
//
//            override fun updateDrawState(ds: TextPaint) {
//                // Customize appearance for the clickable text (optional)
//                ds.isUnderlineText = true // Show underline for the link
//                ds.color = resources.getColor(R.color.blue) // Set text color
//            }
//        }
//
//        val startIndex = binding.alreadyHaveACBtn.text.indexOf("Sign In")
//        val endIndex = startIndex + "Sign In".length
//        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        binding.alreadyHaveACBtn.text = spannableString
//        binding.alreadyHaveACBtn.movementMethod = LinkMovementMethod.getInstance()

    }



    private fun registerNewUser() {
        val name = binding.fullnameInput.text.toString().trim()
        val username = binding.usernameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passInput.text.toString().trim()
        val compassword = binding.comPassword.text.toString().trim()

        validateUserInput(name, username, email, password, compassword)
    }

    private fun validateUserInput(
        name: String,
        username: String,
        email: String,
        password: String,
        compassword: String
    ) {
        if (name.isEmpty()) {
            binding.fullnameInput.requestFocus()
            binding.fullnameInput.error = "Empty"
        } else if (username.isEmpty()) {
            binding.usernameInput.requestFocus()
            binding.usernameInput.error = "Empty"
        } else if (email.isEmpty()) {
            binding.emailInput.requestFocus()
            binding.emailInput.error = "Empty"
        } else if (password.isEmpty()) {
            binding.passInput.requestFocus()
            binding.passInput.error = "Empty"
        } else if (compassword.isEmpty()) {
            binding.comPassword.requestFocus()
            binding.comPassword.error = "Empty"
        } else if (password != compassword) {
            Toast.makeText(
                applicationContext,
                "Please enter  same password!",
                Toast.LENGTH_LONG
            )
                .show()
        } else {
          var bio = "Hello, my name is ${name}."

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this) { task ->

                    userId = task.user!!.uid

                }.addOnCompleteListener {task ->
                    if (task.isSuccessful) {

                        sendEmailVerification()
                        viewModel.registerUser(userId, name, username, email, password, bio,"", "","",0, 0, 0)

                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            val editor = getSharedPreferences("MyUser", MODE_PRIVATE).edit()
            editor.putString("userName", username.toString())
            editor.putString("email", email.toString())
            editor.clear()
            editor.apply()

        }
    }


    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Need email verification check your gmail",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}