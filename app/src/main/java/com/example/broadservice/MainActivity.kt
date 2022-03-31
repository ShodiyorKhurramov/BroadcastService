package com.example.broadservice

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import com.example.broadservice.reseivers.USBBroadcastReceiver

class MainActivity : AppCompatActivity() {

//    lateinit var receiver: NetworkBroadcastReceiver

    lateinit var receiver_battery : USBBroadcastReceiver
    lateinit var receiver_usb : USBBroadcastReceiver


    var boundService: BoundService? = null
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }



    private fun initView() {

//        receiver = NetworkBroadcastReceiver()

        receiver_battery = USBBroadcastReceiver()
        receiver_usb = USBBroadcastReceiver()



        val b_start1 = findViewById<Button>(R.id.bt_Start_Started_Service)
        val b_stop1 = findViewById<Button>(R.id.bt_Stop_Started_Service)
        val b_start2 = findViewById<Button>(R.id.bt_Start_Bound_Service)
        val b_stop2 = findViewById<Button>(R.id.bt_Stop_Bound_Service)
        val tv_level_battery = findViewById<TextView>(R.id.tv_level_battery)
        val tv_time = findViewById<TextView>(R.id.tv_time)



        b_start1.setOnClickListener {
            startStartedServise()
        }

        b_stop1.setOnClickListener {
            stopStartedServise()
        }

        b_start2.setOnClickListener {
            startBoundServise()
        }
        b_stop2.setOnClickListener {
            stopBoundServise()
        }

        tv_time.setOnClickListener {
            if (isBound) {
                tv_time.setText(boundService!!.timesTamp)
            }
        }






    }


    override fun onStart() {
        super.onStart()
        val filter_usb = IntentFilter("android.hardware.usb.action.USB_STATE")
        val filter_battery= IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        registerReceiver(receiver_usb,filter_usb)
        registerReceiver(receiver_battery,filter_battery)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver_battery)

    }

    private fun startStartedServise() {
        val intent = Intent(this, StartedService::class.java)
        startService(intent)
    }

    private fun stopStartedServise() {
        val intent = Intent(this, StartedService::class.java)
        stopService(intent)
    }

    private fun startBoundServise() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)

    }


    private fun stopBoundServise() {
        if (isBound) {
            unbindService(mServiceConnection)
            isBound = false

        }

    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val myBinder: BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }


    }
}