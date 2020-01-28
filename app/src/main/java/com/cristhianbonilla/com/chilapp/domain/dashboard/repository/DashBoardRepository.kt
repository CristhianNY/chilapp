package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber

class DashBoardRepository : BaseRepository(),
    DashBoardRepositoryInterface {

    lateinit var phoneNumberUtil: PhoneNumberUtil

    override fun saveSecretPost(context: Context, message :String, user: UserDto?) {


        var secretPost = SecretPost(
            user!!.userId,
            message
        )

        getFirebaseInstance().child("secretPost").child(user.phone).setValue(secretPost)

        val contacts = getContacts(context)
        for (contact in contacts ){

            getFirebaseInstance().child("secretPost").child(contact.number).push().setValue(secretPost)
        }
    }

    private fun getContacts (context: Context): List<ContactDto> {

        val contactList : MutableList<ContactDto> = ArrayList()
        val contactListWihoutDuplicationData : MutableList<ContactDto> = ArrayList()

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