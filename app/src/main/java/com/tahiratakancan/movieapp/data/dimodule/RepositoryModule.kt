package com.tahiratakancan.movieapp.data.dimodule

import com.tahiratakancan.movieapp.data.repository.MovieRepository
import com.tahiratakancan.movieapp.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository
}