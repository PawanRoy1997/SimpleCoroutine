package com.example.coroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.coroutines.databinding.ActivityCountingBinding

class CountingActivity : BaseActivity<ActivityCountingBinding>() {

    private lateinit var viewModel: CountingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CountingViewModel::class.java]
        view.startCounting.setOnClickListener(startCountingListener())
        view.stopCounting.setOnClickListener(stopCountingListener())
        viewModel.counterActive.observe(this){ active ->
            view.apply {
                startCounting.isClickable = !active
                stopCounting.isClickable = active
            }
            Log.d("Counting", "startCountBtn : ${!active}")
            Log.d("Counting", "stopCountBtn : $active")
        }
        viewModel.count.observe(this) {
            view.count.text = it.toString()
            Log.d("Counting", it.toString())
        }
    }

    private fun stopCountingListener(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.stopCounting()
        }
    }

    private fun startCountingListener(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.startCounting()
        }
    }

    override fun getContentView(): ActivityCountingBinding {
        return ActivityCountingBinding.inflate(layoutInflater)
    }
}