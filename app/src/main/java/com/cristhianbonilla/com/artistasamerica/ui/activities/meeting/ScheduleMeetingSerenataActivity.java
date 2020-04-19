package com.cristhianbonilla.com.artistasamerica.ui.activities.meeting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cristhianbonilla.com.artistasamerica.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import us.zoom.androidlib.util.TimeZoneUtil;
import us.zoom.sdk.AccountService;
import us.zoom.sdk.Alternativehost;
import us.zoom.sdk.MeetingItem;
import us.zoom.sdk.MobileRTCDialinCountry;
import us.zoom.sdk.PreMeetingError;
import us.zoom.sdk.PreMeetingService;
import us.zoom.sdk.PreMeetingServiceListener;
import us.zoom.sdk.ZoomSDK;

public class ScheduleMeetingSerenataActivity extends Activity implements PreMeetingServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button mBtnSchedule;

    private AccountService mAccoutnService;

    private EditText mEdtTopic;
    private EditText mEdtPassword;
    private EditText edittextMostrarHora;
    private TextView mTxtDate;
    private TextView mTxtTimeFrom;
    private TextView mTxtTimeTo;
    private CheckBox mChkEnableJBH;
    private CheckBox mChkHostVideo;
    private CheckBox mChkAttendeeVideo;
    private ImageButton obtenerFechaButton;
    private ImageButton ibObtenerHora;

    private TextView mTxtTimeZoneName;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    private View mOptionScheduleFor;



    private Calendar mDateFrom;
    private Calendar mDateTo;
    private String mTimeZoneId;


    private TextView selectedCountryList;
    private ScheduleForHostAdapter mAlterNativeHostdapter;
    private String mSelectScheduleForHostEmail = null;
    private EditText fechaEditext;

    private PreMeetingService mPreMeetingService = null;

    MobileRTCDialinCountry mCountry;

    private View layoutCountry;

    public final Calendar c = Calendar.getInstance();

    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.schedule_meeting);

        mBtnSchedule = (Button) findViewById(R.id.btnSchedule);
        mBtnSchedule.setOnClickListener(this);

        mEdtTopic = (EditText) findViewById(R.id.edtTopic);
        mEdtPassword = (EditText) findViewById(R.id.contraseña_meeting);
        mChkEnableJBH = (CheckBox) findViewById(R.id.chkEnableJBH);
        mChkHostVideo = (CheckBox) findViewById(R.id.chkHostVideo);
        mChkAttendeeVideo = (CheckBox) findViewById(R.id.chkAttendeeVideo);
        obtenerFechaButton = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        edittextMostrarHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
        fechaEditext = (EditText) findViewById(R.id.et_mostrar_hora_picker);


        obtenerFechaButton.setOnClickListener(v -> {
            obtenerFecha();
        });

              edittextMostrarHora.setOnClickListener(v->{
            obtenerHora();
        });

        ibObtenerHora.setOnClickListener(v->{
            obtenerHora();
        });

        fechaEditext.setOnClickListener(v->{
            obtenerFecha();
        });



        mOptionScheduleFor = findViewById(R.id.optionScheduleFor);

        mTxtDate = (TextView) findViewById(R.id.txtDate);
        mTxtTimeFrom = (TextView) findViewById(R.id.txtTimeFrom);
        mTxtTimeTo = (TextView) findViewById(R.id.txtTimeTo);
        mTxtTimeZoneName = (TextView) findViewById(R.id.txtTimeZone);
        fechaEditext = (EditText) findViewById(R.id.et_mostrar_fecha_picker);


        if (ZoomSDK.getInstance().isInitialized()) {
            mAccoutnService = ZoomSDK.getInstance().getAccountService();
            mPreMeetingService = ZoomSDK.getInstance().getPreMeetingService();
          //  mCountry = ZoomSDK.getInstance().getAccountService().getAvailableDialInCountry();
            if (mAccoutnService == null || mPreMeetingService == null) {
                finish();
            }
        }

        initDateAndTime();
        intUI();
    }

    private void initDateAndTime() {
        mTimeZoneId = TimeZone.getDefault().getID();


        Date timeFrom = new Date(System.currentTimeMillis() + 3600 * 1000);
        Date timeTo = new Date(System.currentTimeMillis() + 7200 * 1000);

        mDateFrom = Calendar.getInstance();
        mDateFrom.setTime(timeFrom);
        mDateFrom.set(Calendar.MINUTE, 0);
        mDateFrom.set(Calendar.SECOND, 0);

        mDateTo = Calendar.getInstance();
        mDateTo.setTime(timeTo);
        mDateTo.set(Calendar.MINUTE, 0);
        mDateTo.set(Calendar.SECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(mDateFrom.getTime());

        if (mDateFrom.get(Calendar.MINUTE) < 10) {
          //  mTxtTimeFrom.setText(mDateFrom.get(Calendar.HOUR_OF_DAY) + ":0" + mDateFrom.get(Calendar.MINUTE));
        } else {
            //mTxtTimeFrom.setText(mDateFrom.get(Calendar.HOUR_OF_DAY) + ":" + mDateFrom.get(Calendar.MINUTE));
        }
        if (mDateFrom.get(Calendar.MINUTE) < 10) {
          //  mTxtTimeTo.setText(mDateTo.get(Calendar.HOUR_OF_DAY) + ":0" + mDateTo.get(Calendar.MINUTE));
        } else {
           // mTxtTimeTo.setText(mDateTo.get(Calendar.HOUR_OF_DAY) + ":" + mDateTo.get(Calendar.MINUTE));
        }
    }

    private void intUI() {

        if (mAccoutnService != null) {
            if (mAccoutnService.isTurnOnAttendeeVideoByDefault()) {
               // mChkHostVideo.setChecked(false);
            } else {
              //  mChkHostVideo.setChecked(true);
            }

            if (mAccoutnService.isTurnOnAttendeeVideoByDefault()) {
            //    mChkAttendeeVideo.setChecked(false);
            } else {
              //  mChkAttendeeVideo.setChecked(true);
            }

            if (mAccoutnService.isEnableJoinBeforeHostByDefault()) {
             //   mChkEnableJBH.setChecked(true);
            } else {
             //   mChkEnableJBH.setChecked(false);
            }

            List<Alternativehost> hostList = mAccoutnService.getCanScheduleForUsersList();
            if (hostList != null && hostList.size() > 0) {
                Alternativehost myself = new Alternativehost();
                myself.setEmail(mAccoutnService.getAccountEmail());
                myself.setFirstName(mAccoutnService.getAccountName());
                myself.setLastName("");
                hostList.add(myself);
                mAlterNativeHostdapter = new ScheduleForHostAdapter(this, hostList);
            } else {
              //  mOptionScheduleFor.setVisibility(View.GONE);
            }
        }
    //    refreshSelectCountry();
    }

    private void refreshSelectCountry()
    {
        if (null != mCountry) {

            StringBuilder sb=new StringBuilder();
            for(String str:mCountry.getSelectedCountries())
            {
                sb.append(str+" ");
            }
            selectedCountryList.setText(sb.toString());
        }
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.btnSchedule) {
            onClickSchedule();
        }
    }

    public void onClickEditCountry(View view) {
        final ArrayList<String> allCountries = mCountry.getAllCountries();
        ArrayList<String> selectedCountries = mCountry.getSelectedCountries();
        if (null == allCountries || allCountries.size() <= 0) {
            return;
        }

        String items[] = new String[allCountries.size()];
        items = allCountries.toArray(items);

        final List<Integer> selectedIndex=new ArrayList<>();
        boolean selectItems[] = new boolean[allCountries.size()];
        for (int i = 0; i < allCountries.size(); i++) {
            String item = allCountries.get(i);
            if (null != selectedCountries && selectedCountries.indexOf(item) >= 0) {
                selectItems[i] = true;
                selectedIndex.add(Integer.valueOf(i));
            } else {
                selectItems[i] = false;
            }
        }


        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.zm_lbl_edit_dial_in_country_104883)
                .setNegativeButton(R.string.zm_btn_cancel, null).setPositiveButton(R.string.zm_btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<String> arrayList=new ArrayList<>();

                        for(Integer index:selectedIndex)
                        {
                            arrayList.add(allCountries.get(index));
                        }
                        mCountry.setSelectedCountries(arrayList);
                        refreshSelectCountry();
                    }
                })
                .setMultiChoiceItems(items, selectItems, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(!isChecked)
                        {
                            selectedIndex.remove(Integer.valueOf(which));
                        }else {
                            selectedIndex.add(Integer.valueOf(which));
                        }
                    }
                }).create();
        dialog.show();
    }

    private void onClickSchedule() {
        String topic = mEdtTopic.getText().toString().trim();
        String contraseña = mEdtPassword.getText().toString().trim();
        if (topic.length() == 0) {
            Toast.makeText(this, "Nombre para tu serenata ", Toast.LENGTH_LONG).show();
            return;
        }
        Date fechaDeInicioParaSetear = null;
        String fechaInicio =  fechaEditext.getText().toString() +"/"+ edittextMostrarHora.getText().toString();
        try {
            fechaDeInicioParaSetear=new SimpleDateFormat("dd/MM/yyyy/hh:mm").parse(fechaInicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (fechaDeInicioParaSetear == null) {
            Toast.makeText(this, "Por favor ingresa una fecha", Toast.LENGTH_LONG).show();
            return;
        }

        if (contraseña.length() == 0) {
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_LONG).show();
            return;
        }



        if (mPreMeetingService == null) return;

        MeetingItem meetingItem = mPreMeetingService.createScheduleMeetingItem();

        edittextMostrarHora.getText();
        fechaEditext.getText();

        meetingItem.setMeetingTopic(topic);
        meetingItem.setStartTime(fechaDeInicioParaSetear.getTime());
        meetingItem.setDurationInMinutes(40);
        meetingItem.setCanJoinBeforeHost(true);
        meetingItem.setPassword(contraseña);
        meetingItem.setHostVideoOff(false);
        meetingItem.setAttendeeVideoOff(false);
        meetingItem.setAudioType(MeetingItem.AudioType.AUDIO_TYPE_VOIP_AND_TELEPHONEY);

        meetingItem.setUsePmiAsMeetingID(false);
        meetingItem.setTimeZoneId(mTimeZoneId);
        //meetingItem.setRepeatType(MeetingItem.RepeatType.EveryMonth);

        meetingItem.setAutoRecordType(MeetingItem.AutoRecordType.AutoRecordType_LocalRecord);

        if (mPreMeetingService != null) {
            mPreMeetingService.addListener(this);
            PreMeetingService.ScheduleOrEditMeetingError error = mPreMeetingService.scheduleMeeting(meetingItem);
            if (error == PreMeetingService.ScheduleOrEditMeetingError.SUCCESS) {
                mBtnSchedule.setEnabled(false);
            } else {
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Usuario no registrado.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreMeetingService != null) {
            mPreMeetingService.removeListener(this);
        }
    }

    private Date getBeginTime() {
        Date date = mDateFrom.getTime();
        date.setSeconds(0);
        return date;
    }


    @Override
    public void onListMeeting(int result, List<Long> meetingList) {
        Log.d("Lista", meetingList.toString());
    }

    @Override
    public void onScheduleMeeting(int result, long meetingNumber) {
        if (result == PreMeetingError.PreMeetingError_Success) {
            Toast.makeText(this, "Schedule successfully. Meeting's unique id is " + meetingNumber, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Schedule failed result code =" + result, Toast.LENGTH_LONG).show();
            mBtnSchedule.setEnabled(true);
        }

    }

    @Override
    public void onUpdateMeeting(int result, long meetingUniqueId) {
        //No op

    }

    @Override
    public void onDeleteMeeting(int result) {
        //No op
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectScheduleForHostEmail = mAlterNativeHostdapter.getItem(position).getEmail();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String domainsListToString(List<String> domainList) {
        if (domainList != null && domainList.size() > 0) {
            StringBuilder domainStringBuilder = new StringBuilder();
            for (int i = 0; i < domainList.size(); i++) {
                domainStringBuilder.append(domainList.get(i));
                if (i != domainList.size() - 1) {
                    domainStringBuilder.append(";");
                }
            }

            return domainStringBuilder.toString();
        }
        return "";
    }

    private List<String> domainStringToDomainList(String domainString) {
        if (!TextUtils.isEmpty(domainString)) {
            String[] domains = domainString.split(";");

            ArrayList<String> specifiedDomains = new ArrayList<String>();
            for (String domain : domains) {
                if (!TextUtils.isEmpty(domain)) {
                    specifiedDomains.add(domain);
                }
            }
            return specifiedDomains;
        }
        return null;
    }

    class ScheduleForHostAdapter extends BaseAdapter {
        private List<Alternativehost> mList;
        private Context mContext;

        public ScheduleForHostAdapter(Context pContext, List<Alternativehost> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Alternativehost getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.alterhost_item, null);

            if (convertView != null) {
                TextView txtHostName = convertView.findViewById(R.id.txtHostName);
                txtHostName.setText(mList.get(position).getFirstName() + " " + mList.get(position).getLastName());
            }
            return convertView;
        }
    }


    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                fechaEditext.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget

        recogerFecha.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        recogerFecha.show();

    }



    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato

                edittextMostrarHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

}
