package com.example.e_social.dependencyinjection

import android.content.Context
import com.example.e_social.models.Constants
import com.example.e_social.models.data.remote.GroupApi
import com.example.e_social.models.data.remote.PostApi
import com.example.e_social.models.data.remote.TopicApi
import com.example.e_social.models.data.remote.UserApi
import com.example.e_social.models.data.repo.group.GroupRepository
import com.example.e_social.models.data.repo.group.impl.GroupRepositoryImpl
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.repo.post.impl.PostRepositoryImpl
import com.example.e_social.models.data.repo.topic.TopicRepository
import com.example.e_social.models.data.repo.topic.impl.TopicRepositoryImpl
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.repo.user.impl.UserRepositoryImpl
import com.example.e_social.util.AuthInterceptor
import com.example.e_social.util.SessionManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideUserRepository (api: UserApi):UserRepository = UserRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideTopicRepository(
        api: TopicApi
    ):TopicRepository = TopicRepositoryImpl(api)

    @Singleton
    @Provides
    fun providePostRepository(api:PostApi):PostRepository = PostRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideGroupRepository(api:GroupApi):GroupRepository = GroupRepositoryImpl(api)


    @Provides
    @Singleton
    fun provideUserApi(@Named("OkHttpClientInterceptor")okHttpClient: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(UserApi::class.java)
    }
    @Singleton
    @Provides
    fun provideTopicApi(@Named("OkHttpClientInterceptor")okHttpClient: OkHttpClient): TopicApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(TopicApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGroupApi(@Named("OkHttpClientInterceptor")okHttpClient: OkHttpClient): GroupApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(GroupApi::class.java)
    }
    @Singleton
    @Provides
    fun providePostApi(@Named("OkHttpClientInterceptor")okHttpClient: OkHttpClient): PostApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PostApi::class.java)
    }

    @Singleton
    @Provides
    fun ProvideSessionManager(@ApplicationContext context: Context)= SessionManager(context)

    @Singleton
    @Provides
    @Named("AuthInterceptor")
    fun provideAuthInterceptor(sessionManager: SessionManager):Interceptor=AuthInterceptor(sessionManager)

    @Singleton
    @Provides
    @Named("OkHttpClientInterceptor")
    fun provideOkHttpClient(@Named("AuthInterceptor") interceptor: Interceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }
}


