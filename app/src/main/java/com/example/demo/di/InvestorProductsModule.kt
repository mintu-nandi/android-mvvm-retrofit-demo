package com.example.demo.di

import com.example.demo.api.apiservice.ApiService
import com.example.demo.api.datasource.InvestorProductsDataSource
import com.example.demo.repository.InvestorProductsRepository
import com.example.demo.viewmodel.InvestorProductsViewModel
import com.example.demo.viewmodel.factory.VMInvestorProductsFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object InvestorProductsModule {
    @ActivityScoped
    @Provides
    fun provideInvestorProductsDataSource(apiService: ApiService): InvestorProductsDataSource {
        return InvestorProductsDataSource(apiService)
    }

    @ActivityScoped
    @Provides
    fun provideInvestorProductsRepository(investorProductsDataSource: InvestorProductsDataSource): InvestorProductsRepository {
        return InvestorProductsRepository(
            investorProductsDataSource
        )
    }

    @ActivityScoped
    @Provides
    fun provideVMInvestorProductsFactory(investorProductsRepository: InvestorProductsRepository): VMInvestorProductsFactory {
        return VMInvestorProductsFactory(investorProductsRepository)
    }

    @ActivityScoped
    @Provides
    fun provideInvestorProductsViewModel(investorProductsRepository: InvestorProductsRepository): InvestorProductsViewModel {
        return InvestorProductsViewModel(investorProductsRepository)
    }
}