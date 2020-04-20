package com.cristhianbonilla.com.artistasamerica.ui.activities.meeting

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.meetings.MeetingDomain
import com.cristhianbonilla.com.artistasamerica.ui.activities.base.BaseActivity
import us.zoom.sdk.*
import us.zoom.sdk.PreMeetingService.ScheduleOrEditMeetingError
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AgendaActivity : BaseActivity(), PreMeetingServiceListener,
    View.OnClickListener {

    private var mBtnSchedule: Button? = null

    private var mAccoutnService: AccountService? = null

    private var mEdtTopic: EditText? = null
    private var mEdtPassword: EditText? = null
    private var edittextMostrarHora: EditText? = null

    private var obtenerFechaButton: ImageButton? = null
    private var ibObtenerHora: ImageButton? = null

    private var mTxtTimeZoneName: TextView? = null

    private val CERO = "0"
    private val BARRA = "/"
    private val DOS_PUNTOS = ":"

    private var mOptionScheduleFor: View? = null


    private var mDateFrom: Calendar? = null
    private var mDateTo: Calendar? = null
    private var mTimeZoneId: String? = null


    private val selectedCountryList: TextView? = null
    private var mAlterNativeHostdapter: ScheduleForHostAdapter? = null
    private val mSelectScheduleForHostEmail: String? = null
    private var fechaEditext: EditText? = null

    private var meetingDomain: MeetingDomain? = null

    private var mPreMeetingService: PreMeetingService? = null

    var mCountry: MobileRTCDialinCountry? = null

    private lateinit var topic:String
    private val layoutCountry: View? = null

    val c = Calendar.getInstance()

    val mes = c[Calendar.MONTH]
    val dia = c[Calendar.DAY_OF_MONTH]
    val anio = c[Calendar.YEAR]

    val hora = c[Calendar.HOUR_OF_DAY]
    val minuto = c[Calendar.MINUTE]


    lateinit var fechaDelEvento:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.schedule_meeting)
        meetingDomain = MeetingDomain()


        mBtnSchedule = findViewById<View>(R.id.btnSchedule) as Button

        mBtnSchedule!!.setOnClickListener(this)

        mEdtTopic = findViewById<View>(R.id.edtTopic) as EditText
        mEdtPassword = findViewById<View>(R.id.contraseña_meeting) as EditText

        obtenerFechaButton = findViewById<View>(R.id.ib_obtener_fecha) as ImageButton
        edittextMostrarHora =
            findViewById<View>(R.id.et_mostrar_hora_picker) as EditText
        ibObtenerHora = findViewById<View>(R.id.ib_obtener_hora) as ImageButton
        fechaEditext = findViewById<View>(R.id.et_mostrar_hora_picker) as EditText



        obtenerFechaButton!!.setOnClickListener { v: View? -> obtenerFecha() }

        edittextMostrarHora!!.setOnClickListener { v: View? -> obtenerHora() }

        ibObtenerHora!!.setOnClickListener { v: View? -> obtenerHora() }

        fechaEditext!!.setOnClickListener { v: View? -> obtenerFecha() }



        mOptionScheduleFor = findViewById(R.id.optionScheduleFor)

        fechaEditext = findViewById<View>(R.id.et_mostrar_fecha_picker) as EditText

        if (ZoomSDK.getInstance().isInitialized) {
            mAccoutnService = ZoomSDK.getInstance().accountService
            mPreMeetingService = ZoomSDK.getInstance().preMeetingService
            //  mCountry = ZoomSDK.getInstance().getAccountService().getAvailableDialInCountry();
            if (mAccoutnService == null || mPreMeetingService == null) {
                finish()
            }
        }

        initDateAndTime()
        intUI()
    }

    private fun intUI() {
        if (mAccoutnService != null) {
            if (mAccoutnService!!.isTurnOnAttendeeVideoByDefault) {
                // mChkHostVideo.setChecked(false);
            } else {
                //  mChkHostVideo.setChecked(true);
            }
            if (mAccoutnService!!.isTurnOnAttendeeVideoByDefault) {
                //    mChkAttendeeVideo.setChecked(false);
            } else {
                //  mChkAttendeeVideo.setChecked(true);
            }
            if (mAccoutnService!!.isEnableJoinBeforeHostByDefault) {
                //   mChkEnableJBH.setChecked(true);
            } else {
                //   mChkEnableJBH.setChecked(false);
            }
            val hostList =
                mAccoutnService!!.canScheduleForUsersList
            if (hostList != null && hostList.size > 0) {
                val myself = Alternativehost()
                myself.email = mAccoutnService!!.accountEmail
                myself.firstName = mAccoutnService!!.accountName
                myself.lastName = ""
                hostList.add(myself)
                mAlterNativeHostdapter = ScheduleForHostAdapter(this, hostList)
            } else {
                //  mOptionScheduleFor.setVisibility(View.GONE);
            }
        }
        //    refreshSelectCountry();
    }

    private fun initDateAndTime() {
        mTimeZoneId = TimeZone.getDefault().id
        val timeFrom =
            Date(System.currentTimeMillis() + 3600 * 1000)
        val timeTo =
            Date(System.currentTimeMillis() + 7200 * 1000)
        mDateFrom = Calendar.getInstance()
        mDateFrom?.setTime(timeFrom)
        mDateFrom?.set(Calendar.MINUTE, 0)
        mDateFrom?.set(Calendar.SECOND, 0)
        mDateTo = Calendar.getInstance()
        mDateTo?.setTime(timeTo)
        mDateTo?.set(Calendar.MINUTE, 0)
        mDateTo?.set(Calendar.SECOND, 0)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateStr = sdf.format(mDateFrom?.getTime())
        if (mDateFrom?.get(Calendar.MINUTE)!! < 10) {
            //  mTxtTimeFrom.setText(mDateFrom.get(Calendar.HOUR_OF_DAY) + ":0" + mDateFrom.get(Calendar.MINUTE));
        } else {
            //mTxtTimeFrom.setText(mDateFrom.get(Calendar.HOUR_OF_DAY) + ":" + mDateFrom.get(Calendar.MINUTE));
        }
        if (mDateFrom?.get(Calendar.MINUTE)!! < 10) {
            //  mTxtTimeTo.setText(mDateTo.get(Calendar.HOUR_OF_DAY) + ":0" + mDateTo.get(Calendar.MINUTE));
        } else {
            // mTxtTimeTo.setText(mDateTo.get(Calendar.HOUR_OF_DAY) + ":" + mDateTo.get(Calendar.MINUTE));
        }
    }

    private fun refreshSelectCountry() {
        if (null != mCountry) {
            val sb = StringBuilder()
            for (str in mCountry!!.selectedCountries) {
                sb.append("$str ")
            }
            selectedCountryList!!.text = sb.toString()
        }
    }

    override fun onUpdateMeeting(p0: Int, p1: Long) {

    }

    override fun onScheduleMeeting(result: Int, meetingNumber: Long) {
        if (result == PreMeetingError.PreMeetingError_Success) {
            Toast.makeText(
                this,
                " Se agendo tu evento correctamente $meetingNumber",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, SuccessMeetingActivity::class.java)
            intent.putExtra("numero_serenata", meetingNumber)
            intent.putExtra("contraseña_serenata", mEdtPassword?.text.toString())
            intent.putExtra("nombre_serenata", topic);
            intent.putExtra("fecha_del_evento", fechaDelEvento);
            startActivity(intent)

            finish()
        } else {
            Toast.makeText(this, "Lo sentimos por ahora no puedes agendar tu evento =$result", Toast.LENGTH_LONG).show()
            mBtnSchedule!!.isEnabled = true
        }
    }

    override fun onListMeeting(p0: Int, meetingList: MutableList<Long>?) {
        Log.d("Lista", meetingList.toString())
    }

    override fun onDeleteMeeting(p0: Int) {

    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btnSchedule) {
                onClickSchedule()
            }
        }
    }

    private fun onClickSchedule() {
        topic = mEdtTopic!!.text.toString().trim { it <= ' ' }
        val contraseña = mEdtPassword!!.text.toString().trim { it <= ' ' }
        if (topic.length == 0) {
            Toast.makeText(this, "Nombre para tu serenata ", Toast.LENGTH_LONG).show()
            return
        }
        var fechaDeInicioParaSetear: Date? = null
         fechaDelEvento =
            fechaEditext!!.text.toString() + "/" + edittextMostrarHora!!.text.toString()
        try {
            fechaDeInicioParaSetear =
                SimpleDateFormat("dd/MM/yyyy/hh:mm").parse(fechaDelEvento)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (fechaDeInicioParaSetear == null) {
            Toast.makeText(this, "Por favor ingresa una fecha", Toast.LENGTH_LONG).show()
            return
        }
        if (contraseña.length == 0) {
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_LONG).show()
            return
        }
        if (mPreMeetingService == null) return
        val meetingItem = mPreMeetingService!!.createScheduleMeetingItem()
        edittextMostrarHora!!.text
        fechaEditext!!.text
        meetingItem.meetingTopic = topic
        meetingItem.startTime = fechaDeInicioParaSetear.time
        meetingItem.durationInMinutes = 40
        meetingItem.canJoinBeforeHost = true
        meetingItem.password = contraseña
        meetingItem.isHostVideoOff = false
        meetingItem.isAttendeeVideoOff = false
        meetingItem.audioType = MeetingItem.AudioType.AUDIO_TYPE_VOIP_AND_TELEPHONEY
        meetingItem.isUsePmiAsMeetingID = false
        meetingItem.timeZoneId = mTimeZoneId
        //meetingItem.setRepeatType(MeetingItem.RepeatType.EveryMonth);
        meetingItem.autoRecordType = MeetingItem.AutoRecordType.AutoRecordType_LocalRecord
        if (mPreMeetingService != null) {
            mPreMeetingService!!.addListener(this)
            val error = mPreMeetingService!!.scheduleMeeting(meetingItem)
            if (error == ScheduleOrEditMeetingError.SUCCESS) {
                mBtnSchedule!!.isEnabled = false
            } else {
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Usuario no registrado.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPreMeetingService != null) {
            mPreMeetingService!!.removeListener(this)
        }
    }

    internal class ScheduleForHostAdapter(
        private val mContext: Context,
        private val mList: List<Alternativehost>
    ) :
        BaseAdapter() {
        override fun getCount(): Int {
            return mList.size
        }

        override fun getItem(position: Int): Alternativehost {
            return mList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val layoutInflater = LayoutInflater.from(mContext)
            convertView = layoutInflater.inflate(R.layout.alterhost_item, null)
            if (convertView != null) {
                val txtHostName = convertView.findViewById<TextView>(R.id.txtHostName)
                txtHostName.text = mList[position].firstName + " " + mList[position].lastName
            }
            return convertView
        }

    }

    private fun obtenerFecha() {
        val recogerFecha =
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                    val mesActual = month + 1
                    //Formateo el día obtenido: antepone el 0 si son menores de 10
                    val diaFormateado =
                        if (dayOfMonth < 10) CERO + dayOfMonth.toString() else dayOfMonth.toString()
                    //Formateo el mes obtenido: antepone el 0 si son menores de 10
                    val mesFormateado =
                        if (mesActual < 10) CERO + mesActual.toString() else mesActual.toString()
                    //Muestro la fecha con el formato deseado
                    fechaEditext!!.setText(diaFormateado + BARRA + mesFormateado + BARRA + year)
                }, //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
                /**
                 * También puede cargar los valores que usted desee
                 */
                anio, mes, dia
            )
        //Muestro el widget
        recogerFecha.datePicker.minDate = System.currentTimeMillis() - 1000
        recogerFecha.show()
    }


    private fun obtenerHora() {
        val recogerHora =
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> //Formateo el hora obtenido: antepone el 0 si son menores de 10
                    val horaFormateada =
                        if (hourOfDay < 10) CERO + hourOfDay else hourOfDay.toString()
                    //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                    val minutoFormateado =
                        if (minute < 10) CERO + minute else minute.toString()
                    //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                    val AM_PM: String
                    AM_PM = if (hourOfDay < 12) {
                        "a.m."
                    } else {
                        "p.m."
                    }
                    //Muestro la hora con el formato
                    edittextMostrarHora!!.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM)
                }, //Estos valores deben ir en ese orden
                //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
                //Pero el sistema devuelve la hora en formato 24 horas
                hora, minuto, false
            )
        recogerHora.show()
    }

}
