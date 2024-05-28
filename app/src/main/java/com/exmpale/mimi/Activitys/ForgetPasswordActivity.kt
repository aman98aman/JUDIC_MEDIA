package com.exmpale.mimi.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {

    val binding by lazy { ActivityForgetPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setcolor1()
        setcolor2()
        setcolor3()

    }

    private fun setcolor1() {

        val spannableString = SpannableString(binding.timer.text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {}
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = resources.getColor(R.color.blue)
            } }
        val startIndex = binding.timer.text.indexOf("00:30")
        val endIndex = startIndex + "00:30".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.timer.text = spannableString
        binding.timer.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun setcolor2() {

        val spannableString = SpannableString(binding.textView12.text)
        val clickableSpan = object : ClickableSpan() {

            override fun onClick(widget: View) {}

            override fun updateDrawState(ds: TextPaint) {
                // Customize appearance for the clickable text (optional)
                ds.isUnderlineText = false // Show underline for the link
                ds.color = resources.getColor(R.color.blue) // Set text color
            }
        }

        val startIndex = binding.textView12.text.indexOf("Resend OTP")
        val endIndex = startIndex + "Resend OTP".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textView12.text = spannableString
        binding.textView12.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun setcolor3() {

        val spannableString = SpannableString(binding.textView11.text)
        val clickableSpan = object : ClickableSpan() {

            override fun onClick(widget: View) {}

            override fun updateDrawState(ds: TextPaint) {
                // Customize appearance for the clickable text (optional)
                ds.isUnderlineText = false // Show underline for the link
                ds.color = resources.getColor(R.color.blue) // Set text color
            }
        }

        val startIndex = binding.textView11.text.indexOf("ex***@gmail.com")
        val endIndex = startIndex + "ex***@gmail.com".length
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textView11.text = spannableString
        binding.textView11.movementMethod = LinkMovementMethod.getInstance()

    }
}