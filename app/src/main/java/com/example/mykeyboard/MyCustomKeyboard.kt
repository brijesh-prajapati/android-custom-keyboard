package com.example.mykeyboard

import android.content.ClipboardManager
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast

class MyCustomKeyboard : InputMethodService() {

    private fun triggerVibration() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(15)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_layout, null)

        // Text aur Number Buttons Mapping
        val buttonMap = mapOf(
            R.id.btn_1 to "1", R.id.btn_2 to "2", R.id.btn_3 to "3", R.id.btn_4 to "4",
            R.id.btn_5 to "5", R.id.btn_6 to "6", R.id.btn_7 to "7", R.id.btn_8 to "8",
            R.id.btn_9 to "9", R.id.btn_0 to "0",
            R.id.btn_q to "q", R.id.btn_w to "w", R.id.btn_e to "e", R.id.btn_r to "r",
            R.id.btn_t to "t", R.id.btn_y to "y", R.id.btn_u to "u", R.id.btn_i to "i",
            R.id.btn_o to "o", R.id.btn_p to "p", R.id.btn_a to "a", R.id.btn_s to "s",
            R.id.btn_d to "d", R.id.btn_f to "f", R.id.btn_g to "g", R.id.btn_h to "h",
            R.id.btn_j to "j", R.id.btn_k to "k", R.id.btn_l to "l", R.id.btn_z to "z",
            R.id.btn_x to "x", R.id.btn_c to "c", R.id.btn_v to "v", R.id.btn_b to "b",
            R.id.btn_n to "n", R.id.btn_m to "m", R.id.btn_space to " "
        )

        for ((id, text) in buttonMap) {
            view.findViewById<Button>(id)?.setOnClickListener {
                triggerVibration()
                currentInputConnection?.commitText(text, 1)
            }
        }

        // Delete Button
        view.findViewById<Button>(R.id.btn_delete)?.setOnClickListener {
            triggerVibration()
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        // --- Utility Buttons Actions ---

        // 1. Clipboard (Paste Feature)
        view.findViewById<Button>(R.id.btn_clipboard)?.setOnClickListener {
            triggerVibration()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (clipboard.hasPrimaryClip()) {
                val item = clipboard.primaryClip?.getItemAt(0)
                val pasteData = item?.text?.toString() ?: ""
                if (pasteData.isNotEmpty()) {
                    currentInputConnection?.commitText(pasteData, 1)
                }
            } else {
                Toast.makeText(applicationContext, "Clipboard Khali Hai!", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Left Cursor Movement
        view.findViewById<Button>(R.id.btn_left)?.setOnClickListener {
            triggerVibration()
            val ic = currentInputConnection
            ic?.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_DPAD_LEFT))
        }

        // 3. Right Cursor Movement
        view.findViewById<Button>(R.id.btn_right)?.setOnClickListener {
            triggerVibration()
            val ic = currentInputConnection
            ic?.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_DPAD_RIGHT))
        }

        // 4. Voice, Theme, Settings Buttons (Just Toast for now)
        val placeholders = mapOf(
            R.id.btn_voice to "Voice Type Jald Hi Aayega!",
            R.id.btn_theme to "Themes Jald Hi Aayega!",
            R.id.btn_settings to "Settings Jald Hi Aayega!"
        )
        for ((id, msg) in placeholders) {
            view.findViewById<Button>(id)?.setOnClickListener {
                triggerVibration()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
