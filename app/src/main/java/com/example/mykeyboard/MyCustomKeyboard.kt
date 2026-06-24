package com.example.mykeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button

class MyCustomKeyboard : InputMethodService() {

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_layout, null)

        // Sahi se click mapping karne ke liye IDs aur unke text ki list
        val buttonMap = mapOf(
            R.id.btn_q to "q", R.id.btn_w to "w", R.id.btn_e to "e", R.id.btn_r to "r",
            R.id.btn_t to "t", R.id.btn_y to "y", R.id.btn_u to "u", R.id.btn_i to "i",
            R.id.btn_o to "o", R.id.btn_p to "p", R.id.btn_a to "a", R.id.btn_s to "s",
            R.id.btn_d to "d", R.id.btn_f to "f", R.id.btn_g to "g", R.id.btn_h to "h",
            R.id.btn_j to "j", R.id.btn_k to "k", R.id.btn_l to "l", R.id.btn_z to "z",
            R.id.btn_x to "x", R.id.btn_c to "c", R.id.btn_v to "v", R.id.btn_b to "b",
            R.id.btn_n to "n", R.id.btn_m to "m", R.id.btn_space to " "
        )

        // Saare text buttons par click setup
        for ((id, text) in buttonMap) {
            view.findViewById<Button>(id)?.setOnClickListener {
                currentInputConnection?.commitText(text, 1)
            }
        }

        // Delete button ka logic
        view.findViewById<Button>(R.id.btn_delete)?.setOnClickListener {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        return view
    }
}
