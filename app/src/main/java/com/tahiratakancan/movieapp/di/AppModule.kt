package com.tahiratakancan.movieapp.di

import com.tahiratakancan.movieapp.data.apiinterface.MovieApi
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //Tüm uygulama yaşadığı sürece bu modül yaşar
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //JSON'ı dataclass ına çevirir.
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi:: class.java)
    }
}

// Retrofit nesnesini her sayfada tekrar oluşturmak yerine, Hilt sayesinde bunu tek bir kez oluşturup (Singleton), tüm uygulamaya dağıtacağız.