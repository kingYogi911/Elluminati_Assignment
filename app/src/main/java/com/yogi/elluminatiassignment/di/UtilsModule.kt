package com.yogi.elluminatiassignment.di

import com.yogi.elluminatiassignment.utils.AssetReader
import com.yogi.elluminatiassignment.utils.AssetReader_Impl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun providesAssetReader(assetReaderImpl: AssetReader_Impl):AssetReader{
        return assetReaderImpl
    }
}