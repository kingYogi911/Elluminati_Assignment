package com.yogi.elluminatiassignment.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.yogi.elluminatiassignment.data.Item
import com.yogi.elluminatiassignment.utils.AssetReader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    assetReader: AssetReader
) : ViewModel() {
    val item: Item

    init {
        item = Gson().fromJson(assetReader.readFile("package_item.json"), Item::class.java)
    }
}