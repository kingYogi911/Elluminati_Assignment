package com.yogi.elluminatiassignment.utils

import android.content.Context
import android.content.res.AssetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetReader_Impl @Inject constructor(@ApplicationContext context: Context) : AssetReader {
    val assetManager:AssetManager = context.assets
    override fun readFile(fileName: String): String {
        try{
            val inputStream=assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        }catch (e:Exception){
            e.printStackTrace()
            return "No Data"
        }
    }
}