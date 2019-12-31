package com.cristhianbonilla.com.chilapp.ui.activities.feature.register

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.cristhianbonilla.com.chilapp.R
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class RegisterActivity : AppCompatActivity() {

    lateinit var editUsername : EditText
    lateinit var editLastName : EditText
    lateinit var editEmail : EditText
    lateinit var editAge : EditText

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initViews()

        editAge.setOnClickListener(View.OnClickListener {

            showdatePicker()
        })
    }

    private fun initViews(){

        editUsername = findViewById(R.id.edit_username)
        editLastName = findViewById(R.id.edit_last_name)
        editEmail = findViewById(R.id.edit_email)
        editAge = findViewById(R.id.edit_age)

    }

    private fun showdatePicker() {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editAge.setText("$dayOfMonth /$monthOfYear/$year")
        }, year, month, day)

        dpd.show()
    }
}
