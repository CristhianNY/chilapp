package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.base.Result
import com.cristhianbonilla.com.chilapp.domain.base.awaitTaskResult
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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

    override fun saveSecretPost(context: Context, message :String, user: UserDto?) {

        val Key: String? =   getFirebaseInstance().child("secretPost").child(user!!.phone).push().getKey()
        var secretPost = Key?.let {
            SecretPost(
                message,
                user!!.userId,
                it,
                0
            )
        }

        val contacts = getContacts(context)
        getFirebaseInstance().child("secretPost").child(user!!.phone+"/$Key").setValue(secretPost)
        for (contact in contacts ){

            if(contact.number!= user?.phone){
                getFirebaseInstance().child("secretPost").child(contact.number+"/$Key").setValue(secretPost)
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

    override fun likeSecretPost(secretPost: SecretPost, context: Context, user: UserDto? , sumLikes:Int) {
        val contacts = getContacts(context)
        val remote: FirebaseFirestore = FirebaseFirestore.getInstance()
        remote.collection("secretPostLikedByUser").document("${user?.phone}").set(secretPost)

        getFirebaseInstance().child("secretPostLikedByUser")
            .child("${secretPost.id}").setValue(secretPost)

        updateLikes(context, user, secretPost, sumLikes, contacts)
    }

    private fun updateLikes(
        context: Context,
        user: UserDto?,
        secretPost: SecretPost,
        sumLikes: Int,
        contacts: List<ContactDto>
    ) {
        getFirebaseInstance().child("secretPost")
            .child(user!!.phone + "/${secretPost.id}" + "/likes").setValue(sumLikes)
        for (contact in contacts) {

            if (contact.number != user?.phone) {
                getFirebaseInstance().child("secretPost")
                    .child(contact.number + "/${secretPost.id}" + "/likes").setValue(sumLikes)
            }
        }
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

    private fun resultToList(result: QuerySnapshot?): Result<Exception, List<SecretPost>> {
        val secrePostList = mutableListOf<SecretPost>()

        result?.forEach { documentSnapshot ->
            secrePostList.add(documentSnapshot.toObject(SecretPost::class.java))
        }

        return Result.build {
            secrePostList
        }
    }
}