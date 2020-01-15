package com.cristhianbonilla.com.chilapp.ui.activities

import android.content.ClipData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val isChecked = MutableLiveData<Boolean>()

    fun select(item: Boolean) {
        isChecked.value = item
    }
}
