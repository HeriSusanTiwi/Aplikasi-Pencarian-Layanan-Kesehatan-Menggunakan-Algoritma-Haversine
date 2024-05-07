package com.herisusan.pencarianlayanankesehatan.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.herisusan.pencarianlayanankesehatan.R
import com.herisusan.pencarianlayanankesehatan.adapter.MainAdapter
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.databinding.ActivityMainBinding
import com.herisusan.pencarianlayanankesehatan.viewmodel.ListResultViewModel
import kotlinx.android.synthetic.main.activity_location.*
import java.util.ArrayList


class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var strLokasi: String = ""
    var strLatitude = 0.0
    var strLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getMyLocation()
        binding.layoutApotek.setOnClickListener {
            val intent = Intent(this@MainActivity, SubMenuActivity::class.java)
            intent.putExtra("label","Klinik")
            intent.putExtra("lokasi", strLokasi)
            intent.putExtra("lat", strLatitude)
            intent.putExtra("long", strLongitude)
            startActivity(intent)
        }

        binding.layoutDoctor.setOnClickListener {
            val intent = Intent(this@MainActivity, SubMenuActivity::class.java)
            intent.putExtra("label","Dokter")
            intent.putExtra("lokasi", strLokasi)
            intent.putExtra("lat", strLatitude)
            intent.putExtra("long", strLongitude)
            startActivity(intent)
        }

        binding.layoutDrugStore.setOnClickListener {
            val intent = Intent(this@MainActivity, SubMenuActivity::class.java)
            intent.putExtra("label","Toko Obat")
            intent.putExtra("lokasi", strLokasi)
            intent.putExtra("lat", strLatitude)
            intent.putExtra("long", strLongitude)
            startActivity(intent)
        }

        binding.layoutHospital.setOnClickListener {
            val intent = Intent(this@MainActivity, SubMenuActivity::class.java)
            intent.putExtra("label","Rumah Sakit")
            intent.putExtra("lokasi", strLokasi)
            intent.putExtra("lat", strLatitude)
            intent.putExtra("long", strLongitude)
            startActivity(intent)
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    strLatitude = location.latitude
                    strLongitude = location.longitude
                    strLokasi = "$strLatitude,$strLongitude"
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

}