package com.cristhianbonilla.com.artistasamerica.ui.activities.meeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.login.LoginDomain
import com.cristhianbonilla.com.artistasamerica.domain.meetings.MeetingDomain
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class SuccessMeetingActivity : AppCompatActivity() {

    lateinit var numero:TextView
    lateinit var contraseña:TextView
    lateinit var nombre:TextView
    lateinit var fecha:TextView
    lateinit var btnShare:Button

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var loginDomain: LoginDomain

    companion object{
       lateinit var vm: MeetingDomain
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        (application as App).getComponent().inject(this)
        vm = ViewModelProviders.of(this, viewModelFactory)[MeetingDomain::class.java]

        val numeroDeSerenata = intent.getLongExtra("numero_serenata",12123)
        val contraseñaSerenata = intent.getStringExtra("contraseña_serenata")
        val nombreSerenata = intent.getStringExtra("nombre_serenata")
        val fechaSerenata = intent.getStringExtra("fecha_del_evento")


        numero = findViewById<TextView>(R.id.mcode) as TextView
        contraseña = findViewById<TextView>(R.id.mpassword) as TextView
        nombre = findViewById<TextView>(R.id.mtitle) as TextView
        fecha = findViewById<TextView>(R.id.mdate) as TextView
        btnShare = findViewById<Button>(R.id.btnShare) as Button


        numero.text = "Codigo de videollamada: $numeroDeSerenata"
        contraseña.text = "Contraseña: $contraseñaSerenata"
        nombre.text = "Nombre del evento: $nombreSerenata"
        fecha.text = "fecha: $fechaSerenata"
        val user = loginDomain.getUserPreference("userId",this)

        CoroutineScope(IO).launch {
            vm.saveMeeting(user.userId,user.name,user.phone,nombreSerenata,numeroDeSerenata.toString(),contraseñaSerenata,fechaSerenata,"60")
        }

        btnShare.setOnClickListener{



            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "Tu serenata  fue reservada con el numero $numeroDeSerenata, y contraseña $contraseñaSerenata , Con estos datos en artistas america puedes ingresar a tu serenata virtual, la hora es $fechaSerenata, ahi nos vemos , abre el siguiente link desde la app y pon el id del evento y la contraseña www.artistasamerica.com/artistasapp"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Artistas América")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Compartir a cliente"))
        }

    }
}
