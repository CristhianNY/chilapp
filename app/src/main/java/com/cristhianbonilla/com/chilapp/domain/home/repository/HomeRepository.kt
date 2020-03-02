package com.cristhianbonilla.com.chilapp.domain.home.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.home.HomeListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.home.FriendsAdapterRecyclerView
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import javax.inject.Inject


class HomeRepository  @Inject constructor(listenerDomain: HomeListenerDomain) : BaseRepository(),
    HomeRepositoryInterface {

    var listenerHomeDomain: HomeListenerDomain

    init {

        App.instance.getComponent().inject(this)

        listenerHomeDomain = listenerDomain
    }

    lateinit var phoneNumberUtil: PhoneNumberUtil

    override fun saveContactsPhoneIntoFirebase(context: Context, user: UserDto?) {


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

        saveContactInFirebase(contactList,user)
    }

    override fun saveContactInFirebase(contactList: MutableList<ContactDto>, user: UserDto?) {

        for (contact in contactList){
            getFirebaseInstance().child("contactsByUser").child(user!!.phone).child(contact.number).setValue(contact)
        }

    }

    override fun getFriendsByUser( context: Context , root: RecyclerView?, friendsAdapterRecyclerView:FriendsAdapterRecyclerView) :List<ContactDto>  {

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

            listenerHomeDomain.onFriendsRead(contactList.distinctBy { Pair(it.number,it.number) }.toMutableList(),friendsAdapterRecyclerView)

            return  contactList.distinctBy { Pair(it.number, it.number) }
        }


    private fun formatPhoneNumber(number: String): Phonenumber.PhoneNumber? {
        val validatedNumber = if (number.trim().startsWith("+")) number else "+57$number"

        val phoneNumber = try {
            phoneNumberUtil.parse(validatedNumber, null)
        } catch (e: NumberParseException) {
            Log.e(TAG, "error during parsing a number")
            null
        }
        if(phoneNumber == null) return null

        return phoneNumber
    }


}