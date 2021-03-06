package com.cristhianbonilla.com.chilapp.ui.activities.register

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.afollestad.vvalidator.form
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.UserPreferenceValidator
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    lateinit var editUsername : EditText
    lateinit var editLastName : EditText
    lateinit var editEmail : EditText
    lateinit var editAge : EditText

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    lateinit var bitdDate:String

    lateinit var validateUserPreferen : UserPreferenceValidator

    @Inject
    lateinit var loginDomain : LoginDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (application as App).getComponent().inject(this)

        initViews()

        editAge.setOnClickListener(View.OnClickListener {

            showdatePicker()
        })

        validateUserPreferen =
            UserPreferenceValidator()

        validateUserRegistered()

        btnRegister.setOnClickListener(View.OnClickListener {
          formValidation()
        })
    }

    private fun validateUserRegistered() {
        loginDomain.getUserNamePreference("userName", this)?.let {
            if (validateUserPreferen.validateIfUserPrefereceIsSaved(
                    it
                )
            ) {
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            }
        }
    }


    private fun initViews(){

        editUsername = findViewById(R.id.edit_username)
        editLastName = findViewById(R.id.edit_last_name)
        editEmail = findViewById(R.id.edit_email)
        editAge = findViewById(R.id.edit_age)

    }

    private fun showdatePicker() {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editAge.setText(loginDomain.getAge(year,monthOfYear,dayOfMonth))
           // editAge.setText("$dayOfMonth /$monthOfYear/
            // editAge.setText("$dayOfMonth /$monthOfYear/$year")
            bitdDate = "$dayOfMonth/$monthOfYear/$year"

            if(loginDomain.getAge(year,monthOfYear,dayOfMonth)?.toInt()!! <14){
                ageMessage.visibility = View.VISIBLE
            }else{
                ageMessage.visibility = View.INVISIBLE
            }

        }, year, month, day)

        dpd.show()
    }

    private fun formValidation() {
        form {
            input(R.id.edit_username) {
                isNotEmpty().description("Por favor ingresa un nombre de usuario")
            }

            input(R.id.edit_last_name) {
                isNotEmpty().description("Por favor ingresa tu apellido")
            }

            input(R.id.edit_last_name) {
                isNotEmpty().description("Por favor ingresa tu apellido")
            }

            input(R.id.edit_email) {
                isEmail().description("Por favor ingresa un correo electronico valido")
                isNotEmpty().description("Por favor ingresa un correo electronico valido")
            }

            input(R.id.edit_age) {
                isNotEmpty().description("Por favor ingresa una fecha para determinar tu edad")
                isNumber().atLeast(14).description("Debes ser mayor de 14 años o mas para poder registrarte")

            }

            submitWith(R.id.btnRegister) { result ->
                // this block is only called if form is valid.
                // do something with a valid form state.
              saveUser()
            }
        }
    }


    fun saveUser(){
        val user = FirebaseAuth.getInstance().currentUser
        var userD = UserDto(
            editUsername.text.toString(),
            editLastName.text.toString(),
            editEmail.text.toString(),
            bitdDate,
            user?.phoneNumber.toString(),
            user?.uid.toString()
        )

        loginDomain.saveUser(userD,this)

        goToHome()
    }

    fun goToHome(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        finish()
        startActivity(intent)
    }
}
