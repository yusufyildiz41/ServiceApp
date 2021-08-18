package com.yusufyildiz.kullanicikayit.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yusufyildiz.kullanicikayit.R
import com.yusufyildiz.kullanicikayit.databinding.ActivityLocationOfServiceBinding
import java.lang.Exception
import java.util.*

class LocationOfService : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationOfServiceBinding

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(myListener)

        mMap.isMyLocationEnabled = true
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener
        {
            override fun onLocationChanged(location: Location) {
                if(location != null)
                {
                    mMap.clear()
                    var newUserLocation = LatLng(location.latitude,location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newUserLocation,15f))

                }
            }

        }

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )

        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)

        }


    }

    //If the user get back form LocationOfService
    override fun onBackPressed() {
        super.onBackPressed()
        var intentToUserInfo = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    var myListener = object : GoogleMap.OnMapLongClickListener{
        override fun onMapLongClick(p0: LatLng) {
            var address = ""
            var geocoder = Geocoder(this@LocationOfService, Locale.getDefault())
            if(p0!= null)
            {
                try
                {
                    mMap.clear()
                    var addressList = geocoder.getFromLocation(p0.latitude,p0.longitude,1)

                    if(addressList != null && addressList.size>0)
                    {
                        if(addressList[0].getAddressLine(0)!= null)
                        {
                            address += " "+ addressList[0].getAddressLine(0).toString()
                        }
                    }
                    else
                    {
                        address = "Tanımlanmayan Konum"
                    }


                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }

            mMap.addMarker(MarkerOptions().position(p0).title(address))
            var alert = AlertDialog.Builder(this@LocationOfService)
            alert.setCancelable(false)
            alert.setTitle("Emin misin ? ")
            alert.setMessage(address)
            alert.setPositiveButton("Evet"){dialog,which->

                    println("saved")
                    var intent = Intent(this@LocationOfService, UserInfos::class.java)
                    intent.putExtra("stationAddress",address)
                    startActivity(intent)
                    finish()


            }
            alert.setNegativeButton("Hayır"){dialog,which->
                println("not saved")
            }
            alert.show()



        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1)
        {
            if(grantResults.size>1)
            {
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }





}