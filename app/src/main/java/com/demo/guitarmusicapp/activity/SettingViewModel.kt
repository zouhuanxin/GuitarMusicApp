package com.demo.guitarmusicapp.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.guitarmusicapp.data.model.Theme

class SettingViewModel:ViewModel {
    //主题
    val themedata:MutableLiveData<ArrayList<Theme>> = MutableLiveData()

    //主函数
    constructor(){
        val themes:ArrayList<Theme> = ArrayList()
        themes.add(Theme("#2c2c2c","#202020"))
        themes.add(Theme("#FFFFCC","#CCFFFF"))
        themes.add(Theme("#99CCCC","#FFCC99"))
        themes.add(Theme("#FF9999","#996699"))
        themes.add(Theme("#CC9999","#FFFFCC"))
        themes.add(Theme("#FFCCCC","#FFFF99"))
        themes.add(Theme("#0099CC","#CCCCCC"))
        themedata.value = themes
    }

    fun getThemeData():LiveData<ArrayList<Theme>>{
        return themedata
    }

}