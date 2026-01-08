@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.unicofrance.uniexo.ui.googleMap

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.unicofrance.uniexo.ui.lib.ContainerDetails
import com.unicofrance.uniexo.ui.lib.rememberMarkerIcon

@Composable
fun GoogleMapScreen(
    modifier: Modifier = Modifier,
    viewModel: GoogleMapViewModel
) {
    val containers by viewModel.containers.collectAsState()
    val selectedContainer by viewModel.selectedContainer.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.startPosition, 15f)
    }

    val fakeLocation by viewModel.fakeLocation.collectAsState()
    val fakePositionMarkerState = rememberUpdatedMarkerState(fakeLocation)

    val context = LocalContext.current
    remember { LocationServices.getFusedLocationProviderClient(context) }

    val hasLocationPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission.value = isGranted
        if (isGranted) {
            // User location not fetched when testing with physical device
            //viewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            Log.d("Location permission", "Location permission was denied by the user.")
        }
    }

    /* Request the location permission when the composable is launched */
    LaunchedEffect(Unit) {
        // Check if the location permission is already granted
        if (hasLocationPermission.value) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
            // Check device location activation status
            if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                // User location not fetched when testing with physical device
                //viewModel.fetchUserLocation(context, fusedLocationClient)
            } else {
                // Open location settings
                context.startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        } else {
            // Request the location permission if it has not been granted
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true
        ),
        properties = MapProperties(
            isMyLocationEnabled = hasLocationPermission.value
        ),
        onMapClick = { latLng -> viewModel.setFakeLocation(latLng) }
    ) {
        Marker(state = fakePositionMarkerState)
        containers.forEach { container ->
            Marker(
                state = MarkerState(position = LatLng(container.latitude, container.longitude)),
                icon = rememberMarkerIcon(container.iconId)
            )
        }
    }

    selectedContainer?.let { container ->
        ContainerDetails(
            modifier = modifier,
            name = container.producingPlaceLabel,
            id = container.id,
            latitude = container.latitude,
            longitude = container.longitude,
            stream = container.streamLabel,
            creationDate = container.creationDatetime,
            iconId = container.iconId,
            onClose = { viewModel.closeContainer() }
        )
    }
}