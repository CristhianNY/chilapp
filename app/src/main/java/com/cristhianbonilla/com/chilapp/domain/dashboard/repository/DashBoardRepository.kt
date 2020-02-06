package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

    override fun saveSecretPost(context: Context, message :String, user: UserDto?) {


        var secretPost = SecretPost(
            message,
            user!!.userId
        )

        val contacts = getContacts(context)
        getFirebaseInstance().child("secretPost").child(user.phone).push().setValue(secretPost)
        for (contact in contacts ){

            if(contact.number!= user.phone){
                getFirebaseInstance().child("secretPost").child(contact.number).push().setValue(secretPost)
            }

        }
    }

    override fun readSecrePost(
        userDto: UserDto?,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    ) {
        val secretpostlist = ArrayList<SecretPost>()
        getFirebaseInstance().child("secretPost/${userDto?.phone}").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                secretpostlist.clear()
                for (postSnapshot in dataSnapshot.children) {

                    var sercretPost = postSnapshot.getValue(SecretPost::class.java)
                    sercretPost?.let { secretpostlist.add(it) }
                    listenerDomainmio.onReadSecretPost(secretpostlist,root, secretPostRvAdapter)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
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
}