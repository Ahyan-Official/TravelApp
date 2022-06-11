package com.travelingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class TextToSpeechActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            val btSpeech = findViewById<Button>(R.id.btSpeech)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                showMessage("This Language is not supported")
            } else {
                btSpeech.isEnabled = true
                speakOut()
            }
        } else {
            showMessage("Initialization Failed!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        tts = TextToSpeech(this, this)
    }

    fun speakOutView(view: View) {
        speakOut()
    }

    private fun speakOut() {

        val editText = findViewById<EditText>(R.id.editText)

        val str = editText.text.toString()
        if (str.isEmpty() || str.isNullOrBlank()) {
            showMessage(getString(R.string.empty_msg))
            return
        }
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}