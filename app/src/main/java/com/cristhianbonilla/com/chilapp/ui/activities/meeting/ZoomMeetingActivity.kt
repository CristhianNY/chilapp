package com.cristhianbonilla.com.chilapp.ui.activities.meeting

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.constants.Constants
import us.zoom.sdk.*


class ZoomMeetingActivity : AppCompatActivity(), Constants, ZoomSDKInitializeListener,
    MeetingServiceListener,
    ZoomSDKAuthenticationListener {

    lateinit var btnJoinMeeting:Button
    lateinit var meetingCode:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_meeting)


        btnJoinMeeting = findViewById<Button>(R.id.joinMeeting)
        meetingCode = findViewById<EditText>(R.id.meeting_code)
        val zoomSDK = ZoomSDK.getInstance()
        val initParams = ZoomSDKInitParams()
        //initParams.jwtToken = SDK_JWTTOKEN;
        //initParams.jwtToken = SDK_JWTTOKEN;
        initParams.appKey = Constants.SDK_KEY
        initParams.appSecret = Constants.SDK_SECRET
        initParams.enableLog = true
        initParams.logSize = 50
        initParams.domain = "zoom.us"
        initParams.videoRawDataMemoryMode = ZoomSDKRawDataMemoryMode.ZoomSDKRawDataMemoryModeStack
        zoomSDK.initialize(this,this,initParams)

        if (savedInstanceState == null) {
            zoomSDK.initialize(
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
        //		opts.no_disconnect_audio = true;
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

    override fun onZoomSDKInitializeResult(p0: Int, p1: Int) {

    }

    override fun onZoomSDKLoginResult(p0: Long) {
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
