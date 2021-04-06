package com.example.demo.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.demo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.Observer
import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.PaymentDetailsDataSource
import com.example.demo.api.status.Result
import com.example.demo.model.request.OneOffPayment
import com.example.demo.model.response.PaymentDetails
import com.example.demo.repository.PaymentDetailsRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PaymentDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var application: Application

    @RelaxedMockK
    private lateinit var apiService: ApiService

    private lateinit var mainViewModel: PaymentDetailsViewModel

    @MockK
    private lateinit var mainDataSource: PaymentDetailsDataSource

    private lateinit var mainRepository: PaymentDetailsRepository

    @RelaxedMockK
    private lateinit var apiUserObserver: Observer<Result<PaymentDetails>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainDataSource =  spyk(PaymentDetailsDataSource(apiService))
        mainRepository = spyk(PaymentDetailsRepository(mainDataSource))
        mainViewModel = PaymentDetailsViewModel(mainRepository)
    }

    @Test
    fun `test for loading server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getPaymentDetails().observeForever(apiUserObserver)
            coEvery { mainRepository.getPaymentDetails("Bearer ", OneOffPayment(0.0,1)) } returns Result.Loading

            mainViewModel.getPaymentDetailsData(application, OneOffPayment(0.0,1))

            verify { apiUserObserver.onChanged(Result.Loading) }
            assert(mainViewModel.getPaymentDetails().value == Result.Loading)
        }
    }

    @Test
    fun `test for successed server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getPaymentDetails().observeForever(apiUserObserver)
            coEvery { mainRepository.getPaymentDetails("Bearer ", OneOffPayment(0.0,1))
            }returns Result.Success(PaymentDetails(0.0, "", ""))

            mainViewModel.getPaymentDetailsData(application, OneOffPayment(0.0,1))

            verify { apiUserObserver.onChanged(Result.Success(PaymentDetails(0.0, "", ""))) }

            assert(mainViewModel.getPaymentDetails().value != null)
            assert(mainViewModel.getPaymentDetails().value == Result.Success(PaymentDetails(0.0, "", "")))
        }
    }

    @Test
    fun `test for errors server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getPaymentDetails().observeForever(apiUserObserver)
            coEvery { mainRepository.getPaymentDetails("Bearer ", OneOffPayment(0.0,1)) } returns Result.Error("Error")

            mainViewModel.getPaymentDetailsData(application, OneOffPayment(0.0,1))

            verify { apiUserObserver.onChanged(Result.Error("Error")) }

            assert(mainViewModel.getPaymentDetails().value != null)
            assert(mainViewModel.getPaymentDetails().value == Result.Error("Error"))
        }
    }

    @After
    fun tearDown() {
    }

}