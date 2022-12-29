package com.hit.appnexthomeassignment.presentation.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hit.appnexthomeassignment.R
import com.hit.appnexthomeassignment.databinding.ActivityMainBinding
import com.hit.appnexthomeassignment.presentation.views.fragments.MainScreenFragment
import com.hit.appnexthomeassignment.services.AppNextForegroundService
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startForegroundService(Intent(this,AppNextForegroundService::class.java))
        launchFragment(
            MainScreenFragment(),
            binding.containerFrag.id,
            "")
    }


    fun launchFragment(
        fragment: Fragment,
        @IdRes containerViewId: Int?,
        tag: String?
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId!!, fragment, tag)
            .commitAllowingStateLoss()
    }
}