package com.exmpale.mimi.Activitys

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityEmailCheckBinding

class EmailCheckActivity : AppCompatActivity() {

    val binding by lazy { ActivityEmailCheckBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        setContentView(binding.root)


        val spannableString = SpannableString(binding.emailTextView.text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:") // Use email client
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {

                ds.isUnderlineText = true
                ds.color = resources.getColor(R.color.blue)
            }
        }
        val startIndex = binding.emailTextView.text.indexOf("try another email address")
        val endIndex = startIndex + "try another email address".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.emailTextView.text = spannableString
        binding.emailTextView.movementMethod = LinkMovementMethod.getInstance()

    }
}