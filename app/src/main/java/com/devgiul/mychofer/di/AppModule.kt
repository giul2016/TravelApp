package com.devgiul.mychofer.di

import android.app.Application
import android.content.Context
import com.devgiul.mychofer.data.api.RideApi
import com.devgiul.mychofer.domain.repository.RideRepository
import com.devgiul.mychofer.domain.repository.RideRepositoryImpl
import com.devgiul.mychofer.presentation.usecase.ValidateRideUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRideApi(retrofit: Retrofit): RideApi {
        return retrofit.create(RideApi::class.java)
    }

    @Provides
    fun provideRideRepository(rideApi: RideApi, context: Context): RideRepository {
        return RideRepositoryImpl(rideApi, context)
    }

    @Provides
    fun provideValidateRideUseCase(rideRepository: RideRepository): ValidateRideUseCase {
        return ValidateRideUseCase(rideRepository)
    }
}

