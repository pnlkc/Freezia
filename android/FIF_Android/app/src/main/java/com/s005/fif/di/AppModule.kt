package com.s005.fif.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.s005.fif.di.LoginUser.memberId
import com.s005.fif.main.data.DefaultMainRepository
import com.s005.fif.main.data.MainRepository
import com.s005.fif.main.network.MainService
import com.s005.fif.recipe.data.DefaultRecipeRepository
import com.s005.fif.recipe.data.RecipeRepository
import com.s005.fif.recipe.network.RecipeService
import com.s005.fif.shopping_list.data.DefaultShoppingListRepository
import com.s005.fif.shopping_list.data.ShoppingListRepository
import com.s005.fif.shopping_list.network.ShoppingListService
import com.s005.fif.user.data.DefaultUserRepository
import com.s005.fif.user.data.UserRepository
import com.s005.fif.user.dto.MemberSelectRequest
import com.s005.fif.user.network.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://j10s005.p.ssafy.io/api/"

    @Singleton
    @Provides
    fun provideFIFPreference(@ApplicationContext context: Context): FIFPreferenceModule =
        FIFPreferenceModule(context)

    @Singleton
    @Provides
    fun provideOkhttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this

            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit,
            ) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter =
                    retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

                override fun convert(value: ResponseBody) =
                    if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
        }

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @PlainOkhttpClient
    fun providePlainOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    @PlainRetrofit
    fun providePainRetrofit(
        @PlainOkhttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this

            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit,
            ) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter =
                    retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

                override fun convert(value: ResponseBody) =
                    if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
        }

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository {
        return DefaultUserRepository(userService)
    }

//    @Singleton
    @Provides
    @PlainUserService
    fun providePlainUserService(@PlainRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    @PlainUserRepository
    fun providePlainUserRepository(@PlainUserService userService: UserService): UserRepository {
        return DefaultUserRepository(userService)
    }

    @Singleton
    @Provides
    fun provideMainService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(mainService: MainService): MainRepository {
        return DefaultMainRepository(mainService)
    }


    @Singleton
    @Provides
    fun provideShoppingListService(retrofit: Retrofit): ShoppingListService {
        return retrofit.create(ShoppingListService::class.java)
    }

    @Singleton
    @Provides
    fun provideShoppingListRepository(shoppingListService: ShoppingListService): ShoppingListRepository {
        return DefaultShoppingListRepository(shoppingListService)
    }

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(recipeService: RecipeService): RecipeRepository {
        return DefaultRecipeRepository(recipeService)
    }

    @Singleton
    class AuthInterceptor @Inject constructor(
        private val fifPreference: FIFPreferenceModule,
        @PlainUserRepository private val userRepository: UserRepository,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = runBlocking {
                fifPreference.accessTokenFlow.first()
            }

            val request = request().newBuilder()
                .apply {
                    if (accessToken != null) addHeader(AUTHORIZATION, "Bearer $accessToken")
                }
                .build()

            var response = chain.proceed(request)

            if (response.code == 401) {
                synchronized(this) {
                    runBlocking {
                        val responseResult =
                            userRepository.getAccessToken(MemberSelectRequest(memberId))

                        if (responseResult.isSuccessful) {
                            val newAccessToken = responseResult.body()!!.accessToken

                            if (accessToken == newAccessToken) {
                                fifPreference.setAccessToken(newAccessToken)
                            } else {
                                response.close()
                            }

                            val newRequest = chain.request().newBuilder()
                                .apply {
                                    addHeader(
                                        AUTHORIZATION,
                                        "Bearer $newAccessToken"
                                    )
                                }
                                .build()

                            response = chain.proceed(newRequest)
                        }
                    }
                }
            }


            return response
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlainOkhttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlainRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlainUserService

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlainUserRepository
