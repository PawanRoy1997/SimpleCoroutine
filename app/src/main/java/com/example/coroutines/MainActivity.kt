package com.example.coroutines

import com.example.coroutines.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getContentView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}