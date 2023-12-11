package com.yogi.elluminatiassignment.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yogi.elluminatiassignment.databinding.ActivityMainBinding
import com.yogi.elluminatiassignment.pricing.PricingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btMove.setOnClickListener {
            val i = Intent(this@MainActivity, PricingActivity::class.java).also {
                it.putExtra("item", viewModel.item)
            }
            startActivity(i)
        }
    }
}