package com.example.broadservice.reseivers



import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.PackageManagerCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.core.content.PackageManagerCompat.LOG_TAG

import android.os.BatteryManager
import android.widget.TextView


class USBBroadcastReceiver : BroadcastReceiver() {

    var usbStateChangeAction = "android.hardware.usb.action.USB_STATE"




    @SuppressLint("RestrictedApi")
    override fun onReceive(context: Context?, intent: Intent) {

        val action = intent.action
        Log.d("@@@", "Received Broadcast: $action")



            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        if (action.equals(usbStateChangeAction, ignoreCase = true)) {
//            Toast.makeText(context, "Check if change in USB state", Toast.LENGTH_SHORT).show()

            if (intent.extras!!.getBoolean("connected")  ) {
                Toast.makeText(context, "USB was connected", Toast.LENGTH_LONG).show()


            }else{
                Toast.makeText(context, "USB was disconnected", Toast.LENGTH_LONG).show()
            }
        }

        if((intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING) ){
            Toast.makeText(context, "Charger connected $level %", Toast.LENGTH_LONG).show()

        }else if (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            Toast.makeText(
                context, "Charger disconnected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }





}