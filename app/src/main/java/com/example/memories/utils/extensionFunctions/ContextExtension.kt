package com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.media.*
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.memories.BuildConfig
import com.example.memories.utils.Constants
import com.example.memories.utils.Constants.TAG
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import kotlin.time.Duration.Companion.milliseconds


object ContextExtension
{


    /******* Toast *******/

    fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


    fun Context.drawable(id: Int) = ContextCompat.getDrawable(this, id)

    fun Context.color(id: Int) = ContextCompat.getColor(this, id)

    fun Context.getArray(id: Int): Array<String> = this.resources.getStringArray(id)


//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
//        {
//            val vibrator  = this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
//            val audioAttributes = VibrationAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_ALARM)
//                .build()
//            val combinedVibrator = CombinedVibration.createParallel(
//                    VibrationEffect.startComposition()
//                        .addPrimitive(VibrationEffect.Composition.PRIMITIVE_LOW_TICK)
//                        .compose())
//                // it is safe to cancel other vibrations currently taking place
//                vibrator.cancel();
//                vibrator.vibrate(combinedVibrator,audioAttributes);
//        } // if lcosed
//        else
//        {
//            val vibrator  = this.getSystemService(VIBRATOR_SERVICE) as Vibrator
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            {
//                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
//            }else
//            {
//                Log.d(TAG, "vibrate: 2")
//                vibrator.vibrate(100);
//            } // else closed
//        } // else closed


    fun Context.copyToClipboard(text: String)
    {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

    }


    /******* Internet *******/

//    fun Context.hasInternetConnection() : Boolean
//    {
//        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//
//        return when
//        {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            else -> false
//        } // return When closed
//    } // hasInternet closed


    fun Context.checkWifiEnabled(): Boolean
    {
        val wifi = this.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        return wifi!!.isWifiEnabled
    }


    /******* Dialog *******/

    fun Context.showDialog(message: String)
    {
        // notify user
        AlertDialog.Builder(this)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok")
            { paramDialogInterface, _ ->
                paramDialogInterface.dismiss()
            }
            .show()
    } // checkGps

    /******* Permissions *******/

    fun Context.locationPermissionsGranted(): Boolean
    {
        return !(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) // if closed

    } // checkIfPermissionsGiven


    fun Context.permissionDenied(msg: String)
    {
        this.showToast(msg)
        this.startActivity(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply()
            {
                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            })
    }// permissionDenied


    fun Context.hasStorageReadingPermission(): Boolean
    {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }


    fun Context.hasCameraPermission(): Boolean
    {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
    }

//
//    fun Context.hasCameraPermission():Boolean
//    {
//        return  (ContextCompat.checkSelfPermission(this,
//            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
//    }


//    @SuppressLint("MissingPermission")
//    fun Context.uploadUserLocation()
//    {
//        Timber.tag(Constants.TAG).d("worker Working inside ")
//        val firebaseFirestore = FirebaseFirestore.getInstance()
//        val firebaseAuth = FirebaseAuth.getInstance()
//
//        if(firebaseAuth.currentUser!=null)
//        {
//
//            var fusedLocationProviderClient: FusedLocationProviderClient? = null
//            var locationRequest: LocationRequest? = null
//            var locationCallback: LocationCallback?=null
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            locationRequest = LocationRequest.create()
//            locationRequest.interval = 1000
//            locationRequest.fastestInterval = 500
//            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//            locationCallback = object : LocationCallback()
//            {
//                override fun onLocationResult(locationResult: LocationResult)
//                {
//                    val latitude =locationResult.lastLocation.latitude
//                    val longitude = locationResult.lastLocation.longitude
//                    //val latlng = com.google.android.gms.maps.model.LatLng(latitued,longitude)
//                    //Timber.tag(TAG).d(latlng.toString())
//                    val user = SharedPrefsHelper(this@uploadUserLocation).getUser()
//                    val userLocation = UserLocation(latitude,longitude)
//                    user?.userLocation = userLocation
//                    firebaseFirestore
//                        .collection(Constants.USER_COLLECTION)
//                        .document(firebaseAuth.currentUser?.phoneNumber!!)
//                        .update("userLocation",userLocation)
//
//                    fusedLocationProviderClient.removeLocationUpdates(locationCallback!!)
//
//                }
//            }
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
//
//
//
//
//        } // currentUser closed
//
//
//    } // uploadUserLocation
//


    fun Activity.hideKeyboard()
    {
        val imm: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = this.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
        {
            view = View(this)
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }


    fun Activity.showKeyBoard()
    {
        val imm =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }


}
