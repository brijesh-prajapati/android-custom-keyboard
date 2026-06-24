package com.example.mykeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button

class MyCustomKeyboard : InputMethodService() {

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_layout, null)

        val btnA = view.findViewById<Button>(R.id.btn_a)
        val btnB = view.findViewById<Button>(R.id.btn_b)
        val btnC = view.findViewById<Button>(R.id.btn_c)
        val btnDelete = view.findViewById<Button>(R.id.btn_delete)

        btnA.setOnClickListener { currentInputConnection?.commitText("A", 1) }
        btnB.setOnClickListener { currentInputConnection?.commitText("B", 1) }
        btnC.setOnClickListener { currentInputConnection?.commitText("C", 1) }
        
        btnDelete.setOnClickListener {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        return view
    }
}
