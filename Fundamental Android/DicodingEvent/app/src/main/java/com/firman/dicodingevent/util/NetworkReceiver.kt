package com.firman.dicodingevent.utils

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NetworkReceiver(private val activity: AppCompatActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = isNetworkAvailable(context)

        if (!isConnected) {
            showNoInternetDialog()
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        return false
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(activity).apply {
            setTitle("Tidak Ada Koneksi Internet")
            setMessage("Aplikasi ini membutuhkan koneksi internet. Tekan OK untuk keluar.")
            setCancelable(false)
            setPositiveButton("OK") { _, _ ->
                activity.finishAffinity()
            }
            show()
        }
    }
}
