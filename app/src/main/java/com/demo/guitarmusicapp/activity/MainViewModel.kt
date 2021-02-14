package com.demo.guitarmusicapp.activity

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.guitarmusicapp.data.ApplicationData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

public class MainViewModel : ViewModel {
    private val backcolor: MutableLiveData<String> = MutableLiveData()
    private val frequencybackcolor: MutableLiveData<String> = MutableLiveData()

    constructor() {
        GlobalScope.launch {
            val str = ApplicationData.readPreInfo("themecolor")
            if (!TextUtils.isEmpty(str)) {
                val json = JSONObject(str)
                backcolor.postValue(json.getString("backcolor"))
                frequencybackcolor.postValue(json.getString("frequencybackcolor"))
            } else {
                backcolor.postValue("#2c2c2c")
                frequencybackcolor.postValue("#202020")
            }
        }
    }

    fun getBackColor(): MutableLiveData<String> {
        return backcolor
    }

    fun getFrequencybackcolor(): MutableLiveData<String> {
        return frequencybackcolor
    }

}