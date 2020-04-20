package com.cristhianbonilla.com.artistasamerica.ui.activities.meeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.cristhianbonilla.com.artistasamerica.R
import kotlinx.android.synthetic.main.activity_success.*

class SuccessMeetingActivity : AppCompatActivity() {

    lateinit var numero:TextView
    lateinit var contraseña:TextView
    lateinit var nombre:TextView
    lateinit var fecha:TextView
    lateinit var btnShare:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val numeroDeSerenata = intent.getLongExtra("numero_serenata",12123)
        val contraseñaSerenata = intent.getStringExtra("contraseña_serenata")
        val nombreSerenata = intent.getStringExtra("nombre_serenata")
        val fechaSerenata = intent.getStringExtra("fecha_del_evento")


        numero = findViewById<TextView>(R.id.mcode) as TextView
        contraseña = findViewById<TextView>(R.id.mpassword) as TextView
        nombre = findViewById<TextView>(R.id.mtitle) as TextView
        fecha = findViewById<TextView>(R.id.mdate) as TextView
        btnShare = findViewById<Button>(R.id.btnShare) as Button


        numero.text = "Codigo de videollamada  $numeroDeSerenata.toString()"
        contraseña.text = "Contraseña $contraseñaSerenata"
        nombre.text = "Nombre del evento $nombreSerenata"
        fecha.text = "fechas $fechaSerenata"

        btnShare.setOnClickListener{
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "Hola este es el codigo de la serenata virtual y la contraseña: $numeroDeSerenata $contraseñaSerenata , Con estos datos en artistas america puedes ingresar a tu serenata virtual, la hora es $fechaSerenata, ahi nos vemos , abre el siguiente link desde la app y pon el id del evento y la contraseña www.artistasamerica.com/artistasapp"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Artistas América")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Compartir a cliente"))
        }

    }
}
