package com.daffamuhtar.fmkotlin.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.databinding.ActivityMainBinding
import com.daffamuhtar.fmkotlin.databinding.DialogErrorRefreshtokenBinding
import com.daffamuhtar.fmkotlin.ui.bottomsheet.NoConnectionBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val context: Context = this

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialogErrorRefreshtokenBinding: DialogErrorRefreshtokenBinding

    private var doubleBackToExitPressedOnce = false

    private lateinit var userid: String
    private lateinit var token: String
    private lateinit var versionName: String
    private var versionCode: Int = 0
    private var notifcount: Int = 0
    private lateinit var noConnectionBottomSheet: NoConnectionBottomSheet
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var menuView: BottomNavigationMenuView
    private lateinit var itemView: BottomNavigationItemView
    private lateinit var notificationBadge: View

    private var connected: Boolean = false
    private lateinit var stageid: String
    private lateinit var ordertype: String
    private lateinit var orderid: String
    private lateinit var spkid: String
    private lateinit var pbid: String
    private lateinit var vid: String
    private lateinit var vbrand: String
    private lateinit var vtype: String
    private lateinit var vvarian: String
    private lateinit var vyear: String
    private lateinit var vlicen: String
    private lateinit var vdis: String
    private lateinit var vlid: String
    private lateinit var notesa: String
    private lateinit var locationOption: String
    private lateinit var date: String

    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        PushNotifications.start(applicationContext, "6f89a6d7-c899-4cce-b0ad-1547d4259174")
//        PushNotifications.addDeviceInterest("mechanics")

        checkInternetConnection()
        checkAllPermission()
        getVersionCode()
        declare()
        getData();
        prepareView()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finish()
                }
                doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
//                showSnackBar(
//                    context = mContext,
//                    layout = binding.layoutMain,
//                    type = Constants.SnackBarTypes.Warn,
//                    message = mContext.getString(R.string.tap_twice_to_terminate)
//                )
            }
        })
    }

    private fun prepareView() {
        changeFragment(HomeFragment(), HomeFragment::class.simpleName)

//        menuView = bottomNavigation.getChildAt(0) as BottomNavigationMenuView
//        itemView = menuView.getChildAt(2) as BottomNavigationItemView
//        notificationBadge = LayoutInflater.from(this).inflate(R.layout.badge_layout, menuView, false)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    changeFragment(HomeFragment(), HomeFragment::class.simpleName)
                }
                R.id.history -> {
                    changeFragment(HomeFragment(), HomeFragment::class.simpleName)
                }
                R.id.notification -> {
                    changeFragment(HomeFragment(), HomeFragment::class.simpleName)
                }
                R.id.profile -> {
                    changeFragment(ProfileFragment(), ProfileFragment::class.simpleName)
                }
            }
            true
        }
    }

    private fun getData() {

    }

    private fun checkAllPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun declare() {
        mainActivity = this
    }

    private fun checkInternetConnection() {
        connected = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
        ) {
            //we are connected to a network
            connected = true
        } else {
            noConnectionBottomSheet = NoConnectionBottomSheet()
            noConnectionBottomSheet.show(supportFragmentManager, "NoConnection")
        }
    }

    private fun getVersionCode() {
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            versionName = pInfo.versionName
            versionCode = pInfo.versionCode
            Log.i(Constants.TAG, "getVersionCode: $versionName - $versionCode")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    // end upload image and get permissions -|

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        private const val REQUEST_CODE_PERMISSIONS = 20
        private const val USER_ID: String = "BkuF8at7TQ"
    }

    fun changeFragment(fragment: Fragment?, tagFragmentName: String?) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        var fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.page_container, fragmentTemp!!, tagFragmentName)
        } else {
            fragmentTransaction.show(fragmentTemp)
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }


}


