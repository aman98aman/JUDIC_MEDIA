package com.exmpale.mimi.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityCongratulationBinding

class CongratulationActivity : AppCompatActivity() {

    val binding by lazy { ActivityCongratulationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        setContentView(binding.root)

        binding.backHomeBtn.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))

        }

    }
}