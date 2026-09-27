package com.example.smartkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class MyKeyboardService : InputMethodService() {

    private var isArabic = false
    private lateinit var keyboardLayout: LinearLayout

    override fun onCreateInputView(): View {
        keyboardLayout = LinearLayout(this)
        keyboardLayout.orientation = LinearLayout.VERTICAL
        keyboardLayout.setPadding(8, 8, 8, 8)
        keyboardLayout.setBackgroundColor(Color.rgb(220, 220, 220))

        createKeyboard()
        return keyboardLayout
    }

    private fun createKeyboard() {
        keyboardLayout.removeAllViews()

        val topRow = LinearLayout(this)
        topRow.orientation = LinearLayout.HORIZONTAL
        topRow.gravity = Gravity.CENTER

        addKey(topRow, "📋", 1f) {
            Toast.makeText(this, "الحافظة ستتم إضافتها لاحقًا", Toast.LENGTH_SHORT).show()
        }

        addKey(topRow, "🌐", 1f) {
            isArabic = !isArabic
            createKeyboard()
        }

        addKey(topRow, "⌫", 1f) {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        keyboardLayout.addView(topRow)

        val rows = if (isArabic) {
            listOf(
                "ض ص ث ق ف غ ع ه خ ح ج د",
                "ش س ي ب ل ا ت ن م ك ة",
                "ئ ء ؤ ر لا ى ة و ز ظ"
            )
        } else {
            listOf(
                "Q W E R T Y U I O P",
                "A S D F G H J K L",
                "Z X C V B N M"
            )
        }

        for (rowText in rows) {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL
            row.gravity = Gravity.CENTER

            for (key in rowText.split(" ")) {
                addKey(row, key, 1f) {
                    currentInputConnection?.commitText(key, 1)
                }
            }

            keyboardLayout.addView(row)
        }

        val bottomRow = LinearLayout(this)
        bottomRow.orientation = LinearLayout.HORIZONTAL
        bottomRow.gravity = Gravity.CENTER

        addKey(bottomRow, "مسافة", 3f) {
            currentInputConnection?.commitText(" ", 1)
        }

        addKey(bottomRow, "↵", 1f) {
            currentInputConnection?.sendKeyEvent(
                android.view.KeyEvent(
                    android.view.KeyEvent.ACTION_DOWN,
                    android.view.KeyEvent.KEYCODE_ENTER
                )
            )
        }

        keyboardLayout.addView(bottomRow)
    }

    private fun addKey(
        row: LinearLayout,
        text: String,
        weight: Float,
        action: () -> Unit
    ) {
        val button = Button(this)
        button.text = text
        button.textSize = 16f
        button.setTextColor(Color.BLACK)
        button.setAllCaps(false)
        button.setPadding(2, 2, 2, 2)

        val background = GradientDrawable()
        background.setColor(Color.WHITE)
        background.cornerRadius = 18f
        button.background = background

        button.setOnClickListener {
            action()
        }

        val params = LinearLayout.LayoutParams(
            0,
            55.dp(),
            weight
        )

        params.setMargins(3, 3, 3, 3)
        row.addView(button, params)
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
