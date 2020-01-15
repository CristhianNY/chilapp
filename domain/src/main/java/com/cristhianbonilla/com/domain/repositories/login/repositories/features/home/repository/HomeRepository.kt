package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import android.util.Log
import com.cristhianbonilla.com.domain.dtos.ContactDto
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.base.BaseRepository
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.util.regex.Pattern


class HomeRepository : BaseRepository(), HomeRepositoryInterface{

    lateinit var phoneNumberUtil: PhoneNumberUtil
    override fun saveContactsPhoneIntoFirebase(context: Context, user: UserDto?) {


        val contactList : MutableList<ContactDto> = ArrayList()


        val contacts = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)

        while (contacts?.moveToNext()!!){

           phoneNumberUtil = PhoneNumberUtil.createInstance(context)

            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contact = ContactDto()

            contact.name = name

           if(getCountryIsoCode(number) == "GR"|| getCountryIsoCode(number) == "BE" ||getCountryIsoCode(number) == "NL" || getCountryIsoCode(number) == "RU"||getCountryIsoCode(number) == "GI"||getCountryIsoCode(number) == "IN"){

               contact.number = "+57"+number
               contactList.add(contact)

           }else{

                   if(getCountryIsoCode(number)!=null){
                       contact.number = number
                       contactList.add(contact)
                   }


           }
        }
        print(contactList)
        contacts.close()

        saveContactInFirebase(contactList,user)
    }

    override fun saveContactInFirebase(contactList: MutableList<ContactDto>,user: UserDto?) {

        for (contact in contactList){
            getFirebaseInstance().child("contactsByUser").child(user!!.phone).child(contact.number).setValue(contact)
        }

    }

    private fun getCountryIsoCode(number: String): String? {
        val validatedNumber = if (number.startsWith("+")) number else "+$number"

        val phoneNumber = try {
            phoneNumberUtil.parse(validatedNumber, null)
        } catch (e: NumberParseException) {
            Log.e(TAG, "error during parsing a number")
            null
        }
        if(phoneNumber == null) return null

        return phoneNumberUtil.getRegionCodeForCountryCode(phoneNumber.countryCode)
    }


}