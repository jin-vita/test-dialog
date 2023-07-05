package com.jinvita.testdialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.FlexboxLayout
import com.jinvita.testdialog.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val dummy1 = listOf(
        "철이랑 수철이랑 수철이랑 수철이",
        "영이랑 희영이랑 희영이랑 희영이",
        "연이랑 수연이랑 수연이랑 수연이",
        "강이랑 혁강이랑 혁강이랑 혁강이",
        "용이랑 찬용이랑 찬용이랑 찬용이",
    )

    private val dummy2 = listOf(
        "철이랑", "수철이랑 수철이랑 수철이",
        "영이랑 희영이랑", "희영이랑 희영이",
        "연이랑 수연이랑 수연이랑", "수연이",
        "강이랑", "혁강이랑", "혁강이랑", "혁강이",
        "용이랑 찬용이랑 찬용이랑 찬용이",
        "철이랑", "수철이랑 수철이랑 수철이",
        "영이랑 희영이랑", "희영이랑 희영이",
        "연이랑 수연이랑 수연이랑", "수연이",
        "강이랑", "혁강이랑", "혁강이랑", "혁강이",
        "철이랑", "수철이랑 수철이랑 수철이",
        "영이랑 희영이랑", "희영이랑 희영이",
        "연이랑 수연이랑 수연이랑", "수연이",
        "강이랑", "혁강이랑", "혁강이랑", "혁강이",
        "용이랑 찬용이랑 찬용이랑 찬용이",
        "철이랑", "수철이랑 수철이랑 수철이",
        "영이랑 희영이랑", "희영이랑 희영이",
        "연이랑 수연이랑 수연이랑", "수연이",
        "강이랑", "혁강이랑", "혁강이랑", "혁강이",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnDialogBasic.setOnClickListener { showBasicDialog() }
            btnDialogSelect.setOnClickListener { showSelectDialog() }
            btnDialogInput.setOnClickListener { showInputDialog() }
            btnDialogList.setOnClickListener { showListDialog() }
            btnDialogFlex.setOnClickListener { showFlexDialog() }
        }
    }

    private fun showBasicDialog() = with(Dialog(this)) {
        setDialogOption(this, R.layout.dialog_basic, 0.6)
    }

    private fun showSelectDialog() = with(Dialog(this)) {
        setDialogOption(this, R.layout.dialog_select, 0.7)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnSubmit.setOnClickListener {
            binding.textTitle.text = "선택 다이얼로그 선택"
            dismiss()
        }
        btnCancel.setOnClickListener { dismiss() }
    }

    private fun showInputDialog() = with(Dialog(this)) {
        setDialogOption(this, R.layout.dialog_input)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val input = findViewById<EditText>(R.id.input)
        btnSubmit.setOnClickListener {
            println("선택된 라디오버튼 아이디 : ${radioGroup.checkedRadioButtonId}")
            if (radioGroup.checkedRadioButtonId == -1) {
                binding.textTitle.text = "라디오버튼을 선택하세요"
                return@setOnClickListener
            }
            val selected = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text
            binding.textTitle.text = "${selected}, ${input.text.trim()}"
            dismiss()
        }
        input.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) btnSubmit.performClick()
            false
        }
    }

    private fun showListDialog() = with(Dialog(this)) {
        setDialogOption(this, R.layout.dialog_list, 0.7)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val listView = findViewById<ListView>(R.id.listView)
        val adapter =
            ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, dummy1)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position)
            binding.textTitle.text = selected.toString()
            dismiss()
        }
        btnCancel.setOnClickListener { dismiss() }
    }

    private fun showFlexDialog() = with(Dialog(this)) {
        setDialogOption(this, R.layout.dialog_flex)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val flexbox = findViewById<FlexboxLayout>(R.id.flexbox)
        dummy2.forEach { value ->
            val button =
                Button(this@MainActivity).apply {
                    maxWidth = (150 * resources.displayMetrics.density).roundToInt()
                    isSingleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    text = value
                    setOnClickListener {
                        binding.textTitle.text = value
                        dismiss()
                    }
                }
            flexbox.addView(button)
        }
        btnCancel.setOnClickListener { dismiss() }
    }

    private fun setDialogOption(dialog: Dialog, layout: Int, ratio: Double = 0.9) = with(dialog) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        findViewById<LinearLayout>(R.id.container).layoutParams = FrameLayout.LayoutParams(
            (ratio * resources.displayMetrics.widthPixels).roundToInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        show()
    }
}