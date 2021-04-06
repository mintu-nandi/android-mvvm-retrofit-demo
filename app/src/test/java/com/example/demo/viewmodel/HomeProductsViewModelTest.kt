package com.example.demo.viewmodel

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.demo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.Observer
import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.HomeDataSource
import com.example.demo.api.status.Result
import com.example.demo.model.response.InvestorProducts
import com.example.demo.repository.HomeRepository
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
class HomeProductsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var apiService: ApiService

    private lateinit var mainViewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences

    @MockK
    private lateinit var mainDataSource: HomeDataSource

    private lateinit var mainRepository: HomeRepository

    @RelaxedMockK
    private lateinit var apiUserObserver: Observer<Result<InvestorProducts>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainDataSource =  spyk(HomeDataSource(apiService))
        mainRepository = spyk(HomeRepository(mainDataSource))
        mainViewModel = HomeViewModel(sharedPreferences, mainRepository)
    }

    @Test
    fun `test for loading server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getInvestorProducts().observeForever(apiUserObserver)
            coEvery { mainRepository.getInvestorProducts("Bearer ") } returns Result.Loading

            mainViewModel.getInvestorProductsData()

            verify { apiUserObserver.onChanged(Result.Loading) }
            assert(mainViewModel.getInvestorProducts().value == Result.Loading)
        }
    }

    @Test
    fun `test for successed server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getInvestorProducts().observeForever(apiUserObserver)
            coEvery { mainRepository.getInvestorProducts("Bearer ") } returns Result.Success(
                InvestorProducts(0.0, emptyList())
            )

            mainViewModel.getInvestorProductsData()

            verify { apiUserObserver.onChanged(Result.Success(InvestorProducts(0.0, emptyList()))) }

            assert(mainViewModel.getInvestorProducts().value != null)
            assert(mainViewModel.getInvestorProducts().value == Result.Success(InvestorProducts(0.0, emptyList())))
        }
    }

    @Test
    fun `test for errors server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getInvestorProducts().observeForever(apiUserObserver)
            coEvery { mainRepository.getInvestorProducts("Bearer ") } returns Result.Error("Error")

            mainViewModel.getInvestorProductsData()

            verify { apiUserObserver.onChanged(Result.Error("Error")) }

            assert(mainViewModel.getInvestorProducts().value != null)
            assert(mainViewModel.getInvestorProducts().value == Result.Error("Error"))
        }
    }

    @After
    fun tearDown() {
    }

}