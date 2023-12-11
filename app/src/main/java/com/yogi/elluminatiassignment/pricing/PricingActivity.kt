package com.yogi.elluminatiassignment.pricing

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.yogi.elluminatiassignment.R
import com.yogi.elluminatiassignment.databinding.ActivityPricingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PricingActivity : AppCompatActivity() {
    lateinit var binding: ActivityPricingBinding
    private val viewModel: PricingViewModel by viewModels()
    private val adapter = SpecificationsAdapter(
        onItemSelected = { spec, opt, isSelected ->
            viewModel.onAnyOptionSelectedChange(spec, opt, isSelected)
        },
        onClickCount = { spec, opt, increase ->
            viewModel.changeCountOfSelected(spec, opt, increase)
        }
    )

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPricingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("Item", Gson().toJson(viewModel.data))
        binding.apply {
            rv.adapter = adapter
            toolbar.setNavigationOnClickListener {
                this@PricingActivity.finish()
            }
        }
        viewModel.specificationsList.observe(this) {
            adapter.updateData(it)
        }
        viewModel.totalPrice.observe(this){
            binding.btAddToCart.text = "Add to Cart - ₹%.2f".format(it)
        }
    }
}