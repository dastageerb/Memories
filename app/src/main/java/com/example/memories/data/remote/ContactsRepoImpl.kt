package com.example.memories.data.remote

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.example.memories.domain.ContactsRepo
import com.example.memories.model.ContactEntity


class ContactsRepoImpl(private val contentResolver: ContentResolver) : ContactsRepo
{

    @SuppressLint("Range")
    override fun getContactNumbers():List<ContactEntity>
    {
        val list = mutableListOf<ContactEntity>()
        val phones: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        // use the cursor to access the contacts
        while (phones?.moveToNext() == true)
        {
            val name: String = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            list.add(ContactEntity(name = name, number = phoneNumber))
        }
        return list
    }


} // contactsRepoImpl