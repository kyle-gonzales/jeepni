package com.example.jeepni.feature.home

import com.example.jeepni.core.data.repository.AuthRepositoryImpl
import com.example.jeepni.core.data.repository.DailyAnalyticsRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val authRepository: AuthRepositoryImpl = Mockito.mock(AuthRepositoryImpl::class.java)
    private val dailyAnalyticsRepository: DailyAnalyticsRepositoryImpl =
        Mockito.mock(DailyAnalyticsRepositoryImpl::class.java)
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        Mockito.mock(FusedLocationProviderClient::class.java)

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel =
            MainViewModel(dailyAnalyticsRepository, authRepository, fusedLocationProviderClient)
    }

    @Test
    fun `Invalid Analytics`() { // Testing Mockito stuff
        val badSalary = "0.0.0.0"
        val badFuelCost = "0.0.0.0"
        mainViewModel.onEvent((MainEvent.OnSalaryChange(badSalary)))
        Assert.assertTrue(mainViewModel.salary == badSalary)
        mainViewModel.onEvent((MainEvent.OnFuelCostChange(badFuelCost)))
        Assert.assertTrue(mainViewModel.fuelCost == badFuelCost)

        // Both should return False
        Assert.assertFalse(mainViewModel.isValidSalary)
        Assert.assertFalse(mainViewModel.isValidFuelCost)
    }

    @Test
    fun `Test Permission Results`() {

    }

}