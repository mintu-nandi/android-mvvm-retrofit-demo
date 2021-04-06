package com.example.demo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.demo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.lifecycle.Observer
import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.AuthenticationDataSource
import com.example.demo.repository.AuthenticationRepository
import com.example.demo.api.status.Result
import com.example.demo.model.request.UserCredential
import com.example.demo.model.response.Session
import com.example.demo.model.response.UserSession
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
class AuthenticationViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var apiService: ApiService

    private lateinit var mainViewModel: AuthenticationViewModel

    @MockK
    private lateinit var mainDataSource: AuthenticationDataSource

    private lateinit var mainRepository: AuthenticationRepository

    @RelaxedMockK
    private lateinit var apiUserObserver: Observer<Result<UserSession>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainDataSource =  spyk(AuthenticationDataSource(apiService))
        mainRepository = spyk(AuthenticationRepository(mainDataSource))
        mainViewModel = AuthenticationViewModel(mainRepository)
    }

    @Test
    fun `test for loading server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getUserSession().observeForever(apiUserObserver)
            coEvery { mainRepository.getUserData(UserCredential("","")) } returns Result.Loading

            mainViewModel.getUserSessionData(UserCredential("",""))

            verify { apiUserObserver.onChanged(Result.Loading) }
            assert(mainViewModel.getUserSession().value == Result.Loading)
        }
    }

    @Test
    fun `test for successed server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getUserSession().observeForever(apiUserObserver)
            coEvery { mainRepository.getUserData(UserCredential("", "")) }returns Result.Success(UserSession(
                Session("")
            ))

            mainViewModel.getUserSessionData(UserCredential("", ""))

            verify { apiUserObserver.onChanged(Result.Success(UserSession(Session("")))) }

            assert(mainViewModel.getUserSession().value != null)
            assert(mainViewModel.getUserSession().value == Result.Success(UserSession(Session(""))))
        }
    }

    @Test
    fun `test for errors server response`() {
        testCoroutineRule.runBlockingTest {
            mainViewModel.getUserSession().observeForever(apiUserObserver)
            coEvery { mainRepository.getUserData(UserCredential("", "")) } returns Result.Error("Error")

            mainViewModel.getUserSessionData(UserCredential("", ""))

            verify { apiUserObserver.onChanged(Result.Error("Error")) }

            assert(mainViewModel.getUserSession().value != null)
            assert(mainViewModel.getUserSession().value == Result.Error("Error"))
        }
    }

    @After
    fun tearDown() {
    }

}