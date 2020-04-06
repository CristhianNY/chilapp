package com.cristhianbonilla.com.chilapp.ui.activities.meeting

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.constants.Constants
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import us.zoom.sdk.*
import javax.inject.Inject


class ZoomMeetingActivity : AppCompatActivity(), Constants, ZoomSDKInitializeListener,
    MeetingServiceListener,
    ZoomSDKAuthenticationListener {

    lateinit var btnJoinMeeting:Button
    lateinit var startMeeting:Button
    lateinit var meetingCode:EditText
    lateinit var mZoomSDK:ZoomSDK


    @Inject
    lateinit var loginDomain : LoginDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_meeting)

        (application as App).getComponent().inject(this)

        btnJoinMeeting = findViewById<Button>(R.id.joinMeeting)
        meetingCode = findViewById<EditText>(R.id.meeting_code)
        startMeeting = findViewById<Button>(R.id.startMetting)

         mZoomSDK = ZoomSDK.getInstance()
         mZoomSDK.loginWithZoom("cristhianbonillacolombia@gmail.com","Cristhian$123")
        val initParams = ZoomSDKInitParams()
        //initParams.jwtToken = SDK_JWTTOKEN;
        //initParams.jwtToken = SDK_JWTTOKEN;
        initParams.appKey = Constants.SDK_KEY
        initParams.appSecret = Constants.SDK_SECRET
        initParams.enableLog = true
        initParams.logSize = 50
        initParams.domain = "zoom.us"
        initParams.videoRawDataMemoryMode = ZoomSDKRawDataMemoryMode.ZoomSDKRawDataMemoryModeStack
        mZoomSDK.initialize(this,this,initParams)


        if (savedInstanceState == null) {
            mZoomSDK.initialize(
                this,
                Constants.SDK_KEY,
                Constants.SDK_SECRET,
                "zoom.us",
                this
            )
        }
        btnJoinMeeting.setOnClickListener{

            joinMeetingClick()
        }

        val user = loginDomain.getUserPreference("userId",this)
        startMeeting.setOnClickListener{

        if(user.type == "admin"){

            StartSerenata()
        }else{
            val pm: PackageManager = this!!.packageManager
            try {
                val waIntent = Intent(Intent.ACTION_SEND)
                waIntent.type = "text/plain"
                val text = "Hola Soy ${user.name} Soy un artista y Quiero Activar Serenatas virtuales " // Replace with your own message.
                val info: PackageInfo =
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp")
                waIntent.putExtra(Intent.EXTRA_TEXT, text)
                startActivity(Intent.createChooser(waIntent, "Share with"))
            } catch (e: PackageManager.NameNotFoundException) {
                 AlertDialog.Builder(this)
                    .setTitle("Necesitas Activar").setMessage("Por favor escribe al whatsapp +57 315 711 9388 y solicita activación ")
                    .setIcon(android.R.drawable.presence_video_online).show()

                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        }

    }


    private fun StartSerenata() {
        // Step 1: Get Zoom SDK instance.

        // Step 1: Get Zoom SDK instance.


        // Check if the zoom SDK is initialized
        // Check if the zoom SDK is initialized
        if (!mZoomSDK.isInitialized) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG)
                .show()
            return
        }
        // Step 2: Get meeting service from zoom SDK instance.
        // Step 2: Get meeting service from zoom SDK instance.
        val meetingService = mZoomSDK.meetingService

        val mInMeetingAudioController = ZoomSDK.getInstance().inMeetingService.inMeetingAudioController;

        mInMeetingAudioController.connectAudioWithVoIP()

        // Step 3: Configure meeting options.
        // Step 3: Configure meeting options.
        val opts = InstantMeetingOptions()

        opts.no_disconnect_audio = true;
        // Some available options

        // Some available options

        // Step 4: Call meeting service to start instant meeting

        // Step 4: Call meeting service to start instant meeting
        meetingService.startInstantMeeting(this, opts)
    }

    fun joinMeetingClick(){
        // Step 1: Get meeting number from input field.

        // Step 1: Get meeting number from input field.
        val meetingNo: String = meetingCode.text.toString().trim()
        // Check if the meeting number is empty.
        // Check if the meeting number is empty.
        if (meetingNo.isEmpty()) {
            Toast.makeText(
                this,
                "Ingresa el código de la clase .",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        // Step 2: Get Zoom SDK instance.

        // Step 2: Get Zoom SDK instance.
        val zoomSDK = ZoomSDK.getInstance()

        val mInMeetingAudioController = ZoomSDK.getInstance().inMeetingService.inMeetingAudioController;

        mInMeetingAudioController.connectAudioWithVoIP()

        // Check if the zoom SDK is initialized
        // Check if the zoom SDK is initialized
        if (!zoomSDK.isInitialized) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG)
                .show()
            return
        }



        // Step 3: Get meeting service from zoom SDK instance.

        // Step 3: Get meeting service from zoom SDK instance.
        val meetingService = zoomSDK.meetingService

        // Step 4: Configure meeting options.

        // Step 4: Configure meeting options.
        val opts = JoinMeetingOptions()

        // Some available options
        //		opts.no_driving_mode = true;
        //		opts.no_invite = true;
        //		opts.no_meeting_end_message = true;
        //		opts.no_titlebar = true;
        //		opts.no_bottom_toolbar = true;
        //		opts.no_dial_in_via_phone = true;
        //		opts.no_dial_out_to_phone = true;
        		opts.no_disconnect_audio = true;
        //		opts.no_share = true;
        //		opts.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
        //		opts.no_audio = true;
        //		opts.no_video = true;
        //		opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
        //		opts.no_meeting_error_message = true;
        //		opts.participant_id = "participant id";

        // Step 5: Setup join meeting parameters

        // Some available options
        //		opts.no_driving_mode = true;
        //		opts.no_invite = true;
        //		opts.no_meeting_end_message = true;
        //		opts.no_titlebar = true;
        //		opts.no_bottom_toolbar = true;
        //		opts.no_dial_in_via_phone = true;
        //		opts.no_dial_out_to_phone = true;
        //		opts.no_disconnect_audio = true;
        //		opts.no_share = true;
        //		opts.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
        //		opts.no_audio = true;
        //		opts.no_video = true;
        //		opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
        //		opts.no_meeting_error_message = true;
        //		opts.participant_id = "participant id";

        // Step 5: Setup join meeting parameters
        val params = JoinMeetingParams()

        params.displayName = "Bienvenido a  clase"
        params.meetingNo = meetingNo

        // Step 6: Call meeting service to join meeting

        // Step 6: Call meeting service to join meeting
        meetingService.joinMeetingWithParams(this, params, opts)
    }

    override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
        if( errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

            if(mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                mZoomSDK.addAuthenticationListener(this);
               // mBtnLogin.setVisibility(View.GONE);
              //  mBtnWithoutLogin.setVisibility(View.GONE);
                Toast.makeText(this,"entro" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"no Entro" , Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onZoomSDKLoginResult(p0: Long) {
        Toast.makeText(this,p0.toString(),Toast.LENGTH_LONG).show()
     }

    override fun onZoomIdentityExpired() {

    }

    override fun onZoomSDKLogoutResult(p0: Long) {

    }

    override fun onZoomAuthIdentityExpired() {

    }

    override fun onMeetingStatusChanged(p0: MeetingStatus?, p1: Int, p2: Int) {

    }
}
