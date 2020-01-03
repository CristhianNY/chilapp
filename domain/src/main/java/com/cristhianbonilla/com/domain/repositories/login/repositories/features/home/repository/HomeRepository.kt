package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository

import android.content.Context
import android.provider.ContactsContract
import com.cristhianbonilla.com.domain.dtos.ContactDto
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.base.BaseRepository


class HomeRepository : BaseRepository(), HomeRepositoryInterface{

    override fun saveContactsPhoneIntoFirebase(context: Context, user: UserDto?) {


        val contactList : MutableList<ContactDto> = ArrayList()
        val contacts = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)

        while (contacts?.moveToNext()!!){

            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contact = ContactDto()

            contact.name = name
            contact.number = number

            contactList.add(contact)
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


}