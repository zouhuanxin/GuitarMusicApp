package com.demo.guitarmusicapp.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.guitarmusicapp.R
import com.demo.guitarmusicapp.activity.adapter.BaseRecyclerAdapter
import com.demo.guitarmusicapp.data.ApplicationData
import com.demo.guitarmusicapp.data.model.Theme
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.setting_item.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class SettingActivity : AppCompatActivity() {
    var settingViewModel: SettingViewModel? = null
    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initData()
        initView()
    }

    fun initView() {
        settingViewModel!!.themedata.observe(this, Observer {
            val adapter = BaseRecyclerAdapter<Theme>(R.layout.setting_item) {
                onBindViewHolder { holder, position ->
                    holder.back1.setBackgroundColor(Color.parseColor(getItem(position).backcolor))
                    holder.back2.setBackgroundColor(Color.parseColor(getItem(position).frequencybackcolor))
                    holder.item_group.setOnClickListener {
                        GlobalScope.launch {
                            val json = JSONObject()
                            json.put("backcolor", getItem(position).backcolor.toString())
                            json.put("frequencybackcolor", getItem(position).frequencybackcolor.toString())
                            ApplicationData.savePreInfo("themecolor", json.toString())
                            mainViewModel!!.getBackColor().postValue(getItem(position).backcolor.toString())
                            mainViewModel!!.getFrequencybackcolor().postValue(getItem(position).frequencybackcolor.toString())
                            finish()
                        }
                    }
                }
            }
            adapter.setData(it)
            themecontent.adapter = adapter
            themecontent.layoutManager = GridLayoutManager(this, 4)
        })
    }

    fun initData() {
        settingViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SettingViewModel::class.java)
        mainViewModel = ViewModelProvider(MainActivity.mainActivity).get(MainViewModel::class.java)
    }

}