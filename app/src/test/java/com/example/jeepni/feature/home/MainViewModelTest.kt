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

        viewModel.onEvent((MainEvent.OnSalaryChange(badSalary))) // See if salary data is equal to badSalary
        Assert.assertTrue(viewModel.salary == badSalary)

        viewModel.onEvent((MainEvent.OnFuelCostChange(badFuelCost)))  // See if fuelCost data is equal to fuelCost
        Assert.assertTrue(viewModel.fuelCost == badFuelCost)

        // Both should return False since badSalary and badFuelCost are not valid inputs
        Assert.assertFalse(viewModel.isValidSalary)
        Assert.assertFalse(viewModel.isValidFuelCost)
    }

    @Test
    fun `test if initial location returns correct values`() {
        // Might return in an error if initial longitude and latitude value in MainViewModel.kt is changed
        val loc = viewModel.cameraPositionState.position.target
        val lat = loc.latitude
        val lon = loc.longitude

        Assert.assertTrue(lat == 10.3157)   // Latitude and Longitude Values in MainViewModel.kt
        Assert.assertTrue(lon == 123.8854)  // Simply check if the camera is at the specific longitude and latitude
    }

    @Test
    fun `test if location on camera position has changed`() {
        val fakeLat = 13.1666       // faked values can be changed to whatever value
        val fakeLon = 172.5030
        val targetPosition = LatLng(fakeLat, fakeLon)

        // NOTE: had to change viewModel.cameraPositionState into a public property to access it,
        // might change it once i understand how to use spyk or ReflectionUtils
        viewModel.cameraPositionState =
            CameraPositionState(CameraPosition.fromLatLngZoom(targetPosition, 15f))

        val loc = viewModel.cameraPositionState.position.target
        val lat = loc.latitude
        val lon = loc.longitude

        // simply check if faked values are reflected in viewModel.cameraPositionState
        Assert.assertTrue(lat == fakeLat)
        Assert.assertTrue(lon == fakeLon)
    }

    @Test
    fun `test if permissions are given properly`() {
        Assert.assertArrayEquals(arrayOf(), viewModel.visiblePermissionDialogQueue.toTypedArray())

        val permA = "ACCESS_COARSE_LOCATION"
        val permB = "ACCESS_FINE_LOCATION"

        viewModel.onPermissionResult(permA, false) // This should add the perm into the array
        Assert.assertArrayEquals(
            arrayOf(permA),
            viewModel.visiblePermissionDialogQueue.toTypedArray()
        )
        viewModel.onPermissionResult(
            permA,
            false
        ) // This should not add the perm into the array since the perm already exists
        Assert.assertArrayEquals(
            arrayOf(permA),
            viewModel.visiblePermissionDialogQueue.toTypedArray()
        )

        viewModel.onPermissionResult(
            permB,
            true
        ) // Perm B should not be added into the array since request was approved
        Assert.assertArrayEquals(
            arrayOf(permA),
            viewModel.visiblePermissionDialogQueue.toTypedArray()
        )

        viewModel.dismissDialog()
        Assert.assertArrayEquals(
            arrayOf(),
            viewModel.visiblePermissionDialogQueue.toTypedArray()
        ) // array should be empty
    }


}
