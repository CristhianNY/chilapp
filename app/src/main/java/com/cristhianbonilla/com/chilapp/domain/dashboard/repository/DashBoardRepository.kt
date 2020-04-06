package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.base.Result
import com.cristhianbonilla.com.chilapp.domain.base.awaitTaskResult
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import javax.inject.Inject

class DashBoardRepository @Inject constructor(listenerDomain: ListenerDomain) : BaseRepository(),
    DashBoardRepositoryInterface {

    var listenerDomainmio: ListenerDomain

    init {

        App.instance.getComponent().inject(this)

        listenerDomainmio = listenerDomain
    }

    lateinit var phoneNumberUtil: PhoneNumberUtil

    override fun saveSecretPost(context: Context, message :String, user: UserDto?,color:String) {
        val Key: String? =   getFirebaseInstance().child("secretPost").child(user!!.phone).push().getKey()
        var secretPost = Key?.let {
            SecretPost(
                message,
                user!!.userId,
                it,
                0,
                color,
                0,
                "https://www.artistasamerica.com/wp-content/uploads/2019/08/mariachi3.jpg",
                "https://www.youtube.com/watch?v=Q71sLoiyA4w&t=17s",
                "+573046452757",
                "Mariachis"
            )
        }

        getFirebaseInstance().child("all").child("$Key").setValue(secretPost)
    }

    override fun likeSecretPost(secretPost: SecretPost, context: Context, user: UserDto? , sumLikes:Int) {
        val contacts = getContacts(context)

        secretPost.likes = sumLikes
        getFirebaseInstance().child("secretPostLikedByUser").child(user!!.phone+"/${secretPost.id}").setValue(secretPost)

        updateLikes(context, user, secretPost, sumLikes, contacts)
    }

    private fun updateLikes(
        context: Context,
        user: UserDto?,
        secretPost: SecretPost,
        sumLikes: Int,
        contacts: List<ContactDto>
    ) {
        getFirebaseInstance().child("secretPostLikedByUser")
        getFirebaseInstance().child("all")
            .child("/${secretPost.id}" + "/likes").setValue(sumLikes)

    }

    override fun makeDislikeSecretPost(
        secretPost: SecretPost,
        context: Context,
        user: UserDto?,
        sumLikes: Int
    ) {
        getFirebaseInstance().child("secretPostLikedByUser").child(user!!.phone+"/${secretPost.id}").removeValue()
        val contacts = getContacts(context)
        updateLikes(context,user,secretPost,sumLikes, contacts)
    }

    private fun getContacts (context: Context): List<ContactDto> {

        val contactList : MutableList<ContactDto> = ArrayList()

        val contacts = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)

        while (contacts?.moveToNext()!!){

            phoneNumberUtil = PhoneNumberUtil.createInstance(context)

            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contact =
                ContactDto()

            contact.name = name

            if(formatPhoneNumber(number)!=null){
                val countryCode = formatPhoneNumber(number)?.countryCode
                val newPhoneNumber = formatPhoneNumber(number)?.nationalNumber
                contact.number = "+$countryCode$newPhoneNumber"
                contactList.add(contact)
            }
        }
        print(contactList)
        contacts.close()
        return  contactList.distinctBy { Pair(it.number, it.number) }
    }

    private fun formatPhoneNumber(number: String): Phonenumber.PhoneNumber? {
        val validatedNumber = if (number.trim().startsWith("+")) number else "+57$number"

        val phoneNumber = try {
            phoneNumberUtil.parse(validatedNumber, null)
        } catch (e: NumberParseException) {
            Log.e(ContentValues.TAG, "error during parsing a number")
            null
        }
        if(phoneNumber == null) return null

        return phoneNumber
    }


    override suspend fun getPostLikedByMe(secretPost: SecretPost, userDto: UserDto?):Result<Exception,List<SecretPost>> {
        val remote: FirebaseFirestore = FirebaseFirestore.getInstance()
        return try {
            val task = awaitTaskResult(

                remote.collection("secretPostLikedByUser") .whereEqualTo("owner", userDto?.userId)
                    .get()

            )

            resultToList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override  fun saveAnimationPreference(
        context: Context,
        isFirstTimeShowingAnimation: Boolean
    ) {
        val settings =
            context.getSharedPreferences("isAnimationShowingFirstTime", Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean("isAnimationShowed",isFirstTimeShowingAnimation)
        editor.apply()
    }

    override  fun getAnimationPreference(
        context: Context
    ): Boolean {
        val settings = context.getSharedPreferences("isAnimationShowingFirstTime", Context.MODE_PRIVATE)
        return settings.getBoolean("isAnimationShowed",false)
    }

    override fun deleteAnimationPreference(context: Context) {
        val settings =
            context.getSharedPreferences("isAnimationShowingFirstTime", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }


    override suspend fun getSecretPost(
        userDto: UserDto?
    ): Result<Exception, List<SecretPost>> {
        val remote: FirebaseFirestore = FirebaseFirestore.getInstance()

        return try {

            getFirebaseInstance().child("secretPost/${userDto?.phone}")
            val task = awaitTaskResult(

                remote.collection("secretPost").document("${userDto?.phone}").collection("${userDto?.phone}")
                    .get()

            )

            resultToList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override suspend fun getSecretPostRealTimeDataBase(userDto: UserDto?): DatabaseReference {
       return getFirebaseInstance().child("secretPost/${userDto?.phone}")
    }

    override suspend fun getALlSecretPostRealTimeDataBase(): DatabaseReference {
        return getFirebaseInstance().child("all")
    }

    override suspend fun getSecretPostRealTimeDataBaseLikes(userDto: UserDto?): DatabaseReference {
        return getFirebaseInstance().child("secretPostLikedByUser/${userDto?.phone}")
    }

}

    private fun resultToList(result: QuerySnapshot?): Result<Exception, List<SecretPost>> {
        val secrePostList = mutableListOf<SecretPost>()

        result?.forEach { documentSnapshot ->
          var secretPost =   documentSnapshot.toObject(SecretPost::class.java)
            secretPost.id = documentSnapshot.id
            secrePostList.add(secretPost)

        }

        return Result.build {
            secrePostList
        }
    }
