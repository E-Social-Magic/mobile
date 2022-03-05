package com.example.e_social.dependencyinjection

import com.example.e_social.models.Constants
import com.example.e_social.models.data.remote.TopicApi
import com.example.e_social.models.data.remote.UserAPI
import com.example.e_social.models.data.repo.TopicRepository
import com.example.e_social.models.data.repo.UserRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    var gson = GsonBuilder()
        .setLenient()
        .create()
    @Provides
    @Singleton
    fun provideUserRepository (api: UserAPI) = UserRepository(api)

    @Singleton
    @Provides
    fun provideTopicRepository(
        api: TopicApi
    ) = TopicRepository(api)

    @Provides
    @Singleton
    fun provideUserApi(): UserAPI {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(UserAPI::class.java)
    }
    @Singleton
    @Provides
    fun provideTopicApi(): TopicApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(TopicApi::class.java)
    }

}


