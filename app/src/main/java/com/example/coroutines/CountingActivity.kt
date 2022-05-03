package com.example.coroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coroutines.databinding.ActivityCountingBinding

class CountingActivity : BaseActivity<ActivityCountingBinding>() {

    private lateinit var viewModel: CountingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CountingViewModel::class.java]

        view.apply {
            startCounting.setOnClickListener(startCountingListener())
            stopCounting.setOnClickListener(stopCountingListener())
            dataStoreBtn.setOnClickListener(dataStoreBtnListener())
        }

        viewModel.apply {
            counterActive.observe(this@CountingActivity, jobObserver())
            count.observe(this@CountingActivity, countObserver())
        }
    }

    private fun dataStoreBtnListener(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.stopCounting()
            startActivity(Intent(this, DataStoreActivity::class.java))
        }
    }

    private fun countObserver(): Observer<Int> {
        return Observer {
            view.count.text = it.toString()
            Log.d("Counting", it.toString())
        }
    }

    private fun jobObserver(): Observer<Boolean> {
        return Observer { active ->
            view.apply {
                startCounting.isClickable = !active
                stopCounting.isClickable = active
            }
            Log.d("Counting", "startCountBtn : ${!active}")
            Log.d("Counting", "stopCountBtn : $active")
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