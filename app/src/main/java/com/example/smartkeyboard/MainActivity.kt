package com.example.smartkeyboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(32, 32, 32, 32)

        val title = TextView(this)
        title.text = "Smart Clipboard Keyboard"
        title.textSize = 24f
        title.gravity = Gravity.CENTER

        val description = TextView(this)
        description.text =
            "كيبورد عربي وإنجليزي بشكل بسيط\nفعّل الكيبورد من الزر التالي"
        description.textSize = 16f
        description.gravity = Gravity.CENTER
        description.setPadding(0, 24, 0, 24)

        val enableButton = Button(this)
        enableButton.text = "فعّل الكيبورد"

        enableButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        val chooseButton = Button(this)
        chooseButton.text = "اختيار الكيبورد"

        chooseButton.setOnClickListener {
            val intent = getSystemService(INPUT_METHOD_SERVICE)
                    as android.view.inputmethod.InputMethodManager

            intent.showInputMethodPicker()
        }

        layout.addView(title)
        layout.addView(description)
        layout.addView(enableButton)
        layout.addView(chooseButton)

        setContentView(layout)
    }
}
