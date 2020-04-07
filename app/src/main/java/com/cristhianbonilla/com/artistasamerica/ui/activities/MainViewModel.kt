package com.cristhianbonilla.com.artistasamerica.ui.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val isChecked = MutableLiveData<Boolean>()

    fun select(item: Boolean) {
        isChecked.value = item
    }
}
