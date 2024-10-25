package com.firman.dicodingevent.util

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity

class NetworkHelper(private val activity: AppCompatActivity) {

    private var dialog: AlertDialog? = null

    fun checkNetworkConnection(onConnected: () -> Unit, onDisconnected: () -> Unit) {
        if (isNetworkAvailable(activity)) {
            onConnected()
            dialog?.dismiss() // Dismiss the dialog if internet is restored
        } else {
            onDisconnected()
            showNoInternetDialog()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetDialog() {
        if (dialog == null || !dialog!!.isShowing) {
            dialog = AlertDialog.Builder(activity).apply {
                setTitle("Tidak Ada Koneksi Internet")
                setMessage("Aplikasi ini membutuhkan koneksi internet. Tekan OK untuk keluar.")
                setCancelable(false)
                setPositiveButton("OK") { _, _ ->
                    activity.finishAffinity()
                }
            }.create()

            dialog?.show()
        }
    }
}
