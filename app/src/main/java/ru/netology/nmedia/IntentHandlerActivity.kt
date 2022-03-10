package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_handler)

        intent?.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}