package com.cristhianbonilla.com.artistasamerica.ui.activities.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.afollestad.vvalidator.form
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.ui.activities.MainActivity
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.contrats.UserPreferenceValidator
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.domain.login.LoginDomain
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    lateinit var editUsername : EditText
    lateinit var editLastName : EditText
    lateinit var editEmail : EditText


    lateinit var validateUserPreferen : UserPreferenceValidator

    @Inject
    lateinit var loginDomain : LoginDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (application as App).getComponent().inject(this)

        initViews()


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
            user?.phoneNumber.toString(),
            user?.uid.toString(),"client"
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
