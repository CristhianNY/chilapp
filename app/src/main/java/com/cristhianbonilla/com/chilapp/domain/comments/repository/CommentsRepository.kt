package com.cristhianbonilla.com.chilapp.domain.comments.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import javax.inject.Inject

class CommentsRepository  @Inject constructor(listenerDomain: ListenerCommentsDomain) : BaseRepository(), CommentsRepositortyInterface{

    var listenerCommentsDomain: ListenerCommentsDomain
    lateinit var phoneNumberUtil: PhoneNumberUtil
    init {
        App.instance.getComponent().inject(this)
        listenerCommentsDomain = listenerDomain
    }

    override fun readComments(
        userDto: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    ) {
        val commentpostList = ArrayList<CommentPostDto>()
        getFirebaseInstance().child("commentsPost/$idSecretPost").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                commentpostList.clear()
                for (postSnapshot in dataSnapshot.children) {

                    var sercretPost = postSnapshot.getValue(CommentPostDto::class.java)
                    sercretPost?.let { commentpostList.add(it) }
                    listenerCommentsDomain.onCommentsPostRead(commentpostList,userDto,root,commentsPostAdapter)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    override fun saveCommentPost(
        contex: Context,
        message: String,
        user: UserDto,
        idSecretPost: String?
    ) {

        val Key: String? =
            idSecretPost?.let { getFirebaseInstance().child("commentsPost").child(it).push().getKey() }
        var commentPost = Key?.let {
            CommentPostDto(
                message,
                user!!.userId,
                "",
                it
            )
        }

        getFirebaseInstance().child("commentsPost").child(idSecretPost+"/"+Key!!).setValue(commentPost)
        getFirebaseInstance().child("commentsPost/$idSecretPost").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getFirebaseInstance().child("secretPost")
                    .child(user!!.phone + "/$idSecretPost" + "/comments").setValue(dataSnapshot.childrenCount)
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