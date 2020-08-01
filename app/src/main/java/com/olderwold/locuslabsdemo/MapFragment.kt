package com.olderwold.locuslabsdemo

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.locuslabs.sdk.maps.model.*
import com.locuslabs.sdk.maps.model.Map
import com.locuslabs.sdk.maps.view.MapView
import com.olderwold.locuslabsdemo.util.overridableViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.map_fullscreen.*

@AndroidEntryPoint
internal class MapFragment : Fragment(R.layout.map_fullscreen) {
    companion object {
        const val PERMISSIONS_REQUEST_CODE = 200
    }

    private val viewModel: VenueViewModel by overridableViewModels()
    private val viewModel2: VenueViewModel2 by overridableViewModels()
    private var mapView: MapView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
                ),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            viewModel.load()
            viewModel.state.observe(viewLifecycleOwner, Observer { database ->
                load(database, "lax")
            })
        }
        viewModel2.load()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // Assume all permissions were granted (in practice you would need to check each permission)
            if (grantResults.isNotEmpty()) {
                viewModel.load()
                viewModel.state.observe(viewLifecycleOwner, Observer { database ->
                    load(database, "lax")
                })
            }
        }
    }

    private fun load(venueDatabase: VenueDatabase, venueId: String) {
        val listeners = VenueDatabase.OnLoadVenueAndMapListeners()
        listeners.loadedInitialViewListener = VenueDatabase.OnLoadedInitialViewListener { view ->
            val parent = view.parent as? ViewGroup
            parent?.removeView(view)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            mapContainer.addView(view)
            venueDatabase.resumeLoadVenueAndMap()
        }

        val activity = requireActivity()
        listeners.loadCompletedListener = object : VenueDatabase.OnLoadCompletedListener {
            override fun onLoadCompleted(
                venue: Venue,
                _map: Map,
                _mapView: MapView,
                p3: Floor,
                p4: Marker
            ) {
                mapView = _mapView
                _mapView.setPositioningEnabled(true)

                // Inform the SDK which activity will handle certain actions like showing error messages, opening pdfs etc. from selected POIs
                _mapView.setOnSupplyCurrentActivityListener { activity }
            }
        }

        listeners.loadFailedListener = VenueDatabase.OnLoadFailedListener {
            // Handle errors here
        }

        // The second parameter is an optional initial search term

        // The second parameter is an optional initial search term
        venueDatabase.loadVenueAndMap(venueId, null, listeners)
    }
}
