package com.example.mykeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button

class MyCustomKeyboard : InputMethodService() {

    override fun onCreateInputView(): View {
        val keyboardView = layoutInflater.inflate(R.layout.keyboard_layout, null)

        val btnA = keyboardView.findViewById<Button>(R.id.btn_a)
        val btnB = keyboardView.findViewById<Button>(R.id.btn_b)
        val btnC = keyboardView.findViewById<Button>(R.id.btn_c)
        val btnDelete = keyboardView.findViewById<Button>(R.id.btn_delete)

        btnA.setOnClickListener { sendText("A") }
        btnB.setOnClickListener { sendText("B") }
        btnC.setOnClickListener { sendText("C") }
        
        btnDelete.setOnClickListener {
            val ic = currentInputConnection
            ic?.deleteSurroundingText(1, 0)
        }

        return keyboardView
    }

    private fun sendText(text: String) {
        val ic = currentInputConnection
        ic?.commitText(text, 1)
    }
}

