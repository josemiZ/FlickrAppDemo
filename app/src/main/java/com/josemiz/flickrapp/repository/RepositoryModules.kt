package com.josemiz.flickrapp.repository

import com.josemiz.flickrapp.repository.remote.interfaces.PhotosConnection
import com.josemiz.flickrapp.repository.remote.request.PhotoRequest
import com.josemiz.flickrapp.repository.remote.request.PhotoRequestImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteConnectionModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun providesHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesPhotosConnection(httpClient: OkHttpClient) : PhotosConnection {
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PhotosConnection::class.java)
    }

    @Singleton
    @Provides
    fun providesPhotoRequest(
        photosConnection: PhotosConnection
    ): PhotoRequest {
        return PhotoRequestImpl(photosConnection)
    }
}
