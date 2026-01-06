package com.unicofrance.uniexo.ui.googleMap

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.unicofrance.uniexo.data.local.database.entities.Container
import com.unicofrance.uniexo.data.repositories.ContainerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class GoogleMapViewModel(
    private val containerRepository: ContainerRepository,
) : ViewModel() {
    private val _location = MutableStateFlow<LatLng?>(null)
    val location = _location.asStateFlow()

    val startPosition = LatLng(43.3430, 3.21555) // Map centered on Béziers
    private val _fakeLocation = MutableStateFlow<LatLng>(startPosition)
    val fakeLocation: StateFlow<LatLng> = _fakeLocation.asStateFlow()

    val containers = containerRepository.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    private val _selectedContainer = MutableStateFlow<Container?>(null)
    val selectedContainer: StateFlow<Container?> = _selectedContainer

    fun selectContainer(container: Container) {
        _selectedContainer.value = container
    }

    fun closeContainer() {
        _selectedContainer.value = null
    }

    fun setFakeLocation(latLng: LatLng) {
        _fakeLocation.value = latLng
        checkSurroundingContainers()
    }

    private fun checkSurroundingContainers() {
        containers.value.forEach {
            val results = FloatArray(1)
            Location.distanceBetween(
                it.latitude,
                it.longitude,
                _fakeLocation.value.latitude,
                _fakeLocation.value.longitude,
                results
            )
            val distanceMeters = results[0]
            if (distanceMeters < 50) {
                selectContainer(it)
            }
        }
    }

    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val userLatLng = LatLng(it.latitude, it.longitude)
                        _location.value = userLatLng
                        Log.d("Location", "New location : ${it.latitude} ${it.longitude}")
                    }
                }
            } catch (e: SecurityException) {
                Log.e("Location permission","Permission for location access was revoked: ${e.localizedMessage}")
            }
        } else {
            Log.d("Location permission","Location permission is not granted.")
        }
    }

}