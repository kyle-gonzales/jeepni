package com.example.jeepni.feature.home

import com.example.jeepni.core.data.repository.AuthRepositoryImpl
import com.example.jeepni.core.data.repository.DailyAnalyticsRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
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

    private lateinit var viewModel: MainViewModel


    @Before
    fun setUp() {
        viewModel =
            MainViewModel(dailyAnalyticsRepository, authRepository, fusedLocationProviderClient)
    }

    @Test
    fun `test if analytics values are correct`() { // Testing Mockito stuff
        val badSalary = "0.0.0.0"
        val badFuelCost = "0.0.0.0"
        viewModel.onEvent((MainEvent.OnSalaryChange(badSalary)))
        Assert.assertTrue(viewModel.salary == badSalary)
        viewModel.onEvent((MainEvent.OnFuelCostChange(badFuelCost)))
        Assert.assertTrue(viewModel.fuelCost == badFuelCost)

        // Both should return False
        Assert.assertFalse(viewModel.isValidSalary)
        Assert.assertFalse(viewModel.isValidFuelCost)
    }

    @Test
    fun `test if initial location returns correct values`() {
        // Might return in an error if initial longitude and latitude value in MainViewModel.kt is changed
        val loc = viewModel.cameraPositionState.position.target
        val lat = loc.latitude
        val lon = loc.longitude

        Assert.assertTrue(lat == 10.3157) // Latitude and Longitude Values in MainViewModel.kt
        Assert.assertTrue(lon == 123.8854)
    }

    @Test
    fun `test if location on camera position has changed`() {
        val fakeLat = 13.1666
        val fakeLon = 172.5030
        val targetPosition = LatLng(fakeLat, fakeLon)

        viewModel.cameraPositionState =
            CameraPositionState(CameraPosition.fromLatLngZoom(targetPosition, 15f))

        val loc = viewModel.cameraPositionState.position.target
        val lat = loc.latitude
        val lon = loc.longitude

        Assert.assertTrue(lat == fakeLat)
        Assert.assertTrue(lon == fakeLon)
    }

    @Test
    fun `test if permissions are given properly`() {
        val perms = listOf(
            "ACCESS_COARSE_LOCATION",
            "ACCESS_FINE_LOCATION"
        )

        for (perm in perms) {
            viewModel.onPermissionResult(perm, false)
            Assert.assertTrue(perm in viewModel.visiblePermissionDialogQueue)
        }

    }

}
