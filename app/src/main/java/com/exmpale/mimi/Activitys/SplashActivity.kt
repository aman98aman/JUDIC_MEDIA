package com.exmpale.mimi.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivitySplashBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private val bindng by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindng.root)


        Handler(Looper.getMainLooper()).postDelayed({

            if (Firebase.auth.currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        },3000)
    }
}