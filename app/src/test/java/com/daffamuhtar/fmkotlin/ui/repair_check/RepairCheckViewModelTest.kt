package com.daffamuhtar.fmkotlin.ui.repair_check

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.data.api.RepairCheckApiHelperImpl
import com.daffamuhtar.fmkotlin.data.repository_old.RepairCheckRepository
import com.daffamuhtar.fmkotlin.util.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(RobolectricTestRunner::class)
class RepairCheckViewModelTest : KoinTest {


//    val repairCheckViewModel: RepairCheckViewModel by inject()
//    lateinit var viewModel: RepairCheckViewModel

    lateinit var context: Context

//
//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        modules(
//            listOf(
//                appModule,
//                repositoryModule,
//                viewModelModule
//            )
//        )
//    }

    @Before
    fun setUp() {

        context = ApplicationProvider.getApplicationContext<Context>()


//        startKoin {
//            modules(
//                listOf(
//                    appModule,
//                    repositoryModule,
//                    viewModelModule
//                )
//            )
//        }
    }

    @After
    fun tearDown() {
//        stopKoin()
    }

    @Test
    fun getToken() {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYiLCJ1c2VybmFtZSI6ImZsZWV0MS10ZXN0aW5nIiwiaWF0IjoxNzAwMjA1Nzc4LCJleHAiOjE3MDAyNDg5Nzh9.fwQnvq5Sj78d6-OmxMDYW1zPXgfMFZXCpjRMRdZMEJU"
                    )
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(loggingInterceptor)
            .build()


        val repairCheckViewModel = RepairCheckViewModel(
            RepairCheckRepository(RepairCheckApiHelperImpl()),
            NetworkHelper(context),
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.URL1)
                .client(okHttpClient)
                .build(),
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.URL1_V20)
                .client(okHttpClient)
                .build(),
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.URL1_V20_REP)
                .client(okHttpClient)
                .build(),
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.URL2)
                .client(okHttpClient)
                .build()
        )


        val networkHelper = NetworkHelper(context)
        val repairCheckApiHelper = RepairCheckApiHelperImpl()
        val repairCheckRepository = RepairCheckRepository(repairCheckApiHelper)
//        val repairCheckViewModel = RepairCheckViewModel(repairCheckRepository,networkHelper,retrofi)
//
////
////      val latch = CountDownLatch(1)
//        repairCheckViewModel.repairList.observe(ApplicationProvider.getApplicationContext()) {
//            assertEquals(Status.SUCCESS, it.status)
//        }
////


        `when`(repairCheckViewModel.getAllCheckRepair(
            ApplicationProvider.getApplicationContext(),
            ConstantsApp.BASE_URL_V1_0,
            "MEC-MBA-99"
        )).then {
            assertNotNull(repairCheckViewModel.repairList.value)
        }


//
//        assertEquals(5, 5)

//        val observer = Observer<Result> { result ->
//            // put your assert statements here
//        }
//        viewModel.liveData.observeForever(observer)
//        viewModel.fetchSomeData()
//        advanceUntilIdle()
//        viewModel.liveData.removeObserver(observer)
//
//
//        val result = viewModel..getOrAwaitValue {
//            viewModel.fetchSomeData()
//            advanceUntilIdle()
//        }

//        assertNotNull(result)
    }

}