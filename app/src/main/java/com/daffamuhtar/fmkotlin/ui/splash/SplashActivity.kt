package com.daffamuhtar.fmkotlin.ui.splash

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.ui.main.MainActivity
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.constants.Constanta.TAG
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.databinding.ActivitySplashBinding
import com.daffamuhtar.fmkotlin.databinding.DialogErrorRefreshtokenBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var bindingDialogErrorRefreshtokenBinding: DialogErrorRefreshtokenBinding
    private lateinit var splashViewModel: SplashViewModel

    private lateinit var context: Context

    private var frombottom: Animation? = null
    private var fromtop: Animation? = null

    private var splashlogo: ImageView? = null
    private var lyBgSplash: LinearLayout? = null

    private var userid: String? = null
    private var token: String? = null
    private var companyType: String? = null
    private var versioprivate: String? = null
    private var versionName: String? = null

    private var stageid: String? = null
    private var ordertype: String? = null
    private var orderid: String? = null
    private var spkid: String? = null
    private var pbid: String? = null
    private var vid: String? = null
    private var vbrand: String? = null
    private var vtype: String? = null
    private var vvarian: String? = null
    private var vyear: String? = null
    private var vlicen: String? = null
    private var vdis: String? = null
    private var vlid: String? = null
    private var notesa: String? = null
    private var locationOption: String? = null
    private var date: String? = null

    private var waktu_loading: Int = 0
    private var versionCode: Int = 0

    private var splashActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        Server.urlSample(this@SplashActivity, ConstantaApp.BASE_URL_V1_0)
//        PushNotifications.start(applicationContext, "6f89a6d7-c899-4cce-b0ad-1547d4259174")

        getVersionCode()
        getData()
        declare()
        checkId()

        initViewModelObserver();

    }

    private fun getData() {
        spkid = intent.getStringExtra("stageId")
        stageid = intent.getStringExtra("stageId")
        ordertype = intent.getStringExtra("orderType")
        orderid = intent.getStringExtra("orderId")
    }

    private fun initViewModelObserver() {
        splashViewModel.isLoadingRefreshToken.observe(this@SplashActivity) {
//            loadingAdd(it)
            Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
        }

        splashViewModel.messageRefreshToken.observe(this@SplashActivity) {
            showMessage(it, isBindingAdd = true)
        }

        splashViewModel.isSuccessRefreshToken.observe(this@SplashActivity) {
            if (it) {
                intentToMainActivity()
            } else {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun loadingAdd(value: Boolean) {
//        bindingDialog.btnAddReport.isEnabled = !value
//        bindingDialog.pbAddDialog.visibility = if (value) View.VISIBLE else View.GONE
//    }

    private fun showMessage(value: String = "Cant get data!", isBindingAdd: Boolean = false) {
        if (!isBindingAdd) {
            Snackbar.make(
                binding.root, value, Snackbar.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this@SplashActivity, value, Toast.LENGTH_SHORT).show()
        }
    }

    private fun declare() {
        splashActivity = this

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

//
//        lyBgSplash = findViewById(R.id.ly_bg_splash)
//        splashlogo = findViewById(R.id.splash_logo)
    }

    private fun getVersionCode() {
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            versionName = pInfo.versionName
            versionCode = pInfo.versionCode
            Log.i(TAG, "getVersionCode: $versionName - $versionCode")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun checkId() {
        val sharedpreferences =
            getSharedPreferences(Constanta.my_shared_preferences, Context.MODE_PRIVATE)
        userid = sharedpreferences.getString(Constanta.EXTRA_USERID, "UM-BLOG-9999")
        token = sharedpreferences.getString(Constanta.EXTRA_TOKEN, null)
        companyType = sharedpreferences.getString(Constanta.EXTRA_COMPANYTYPE, null)
    }

    override fun onResume() {
        super.onResume()
        if (userid != null) {
            binding.lyBgSplash.visibility = View.VISIBLE
            refreshToken()

        } else {
            Handler().postDelayed({
                intentToMainActivity()

            }, waktu_loading.toLong())
        }
    }

    private fun intentToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
    }

    private fun refreshToken() {
        userid?.let {
            splashViewModel.postRefreshToken(
                this, ConstantaApp.BASE_URL_V2_0, it,
                versionCode.toString(), "mechanic"
            )
        }
    }

//    private fun showDialogVehicle() {
//        val bottomDialogVehicle = BottomSheetDialog(this@SplashActivity)
//        val bindingDialogVehicle = DialogErrorRefreshtokenBinding.inflate(layoutInflater)
//        bottomDialogVehicle.setContentView(bindingDialogVehicle.root)
//        bottomDialogVehicle.show()
//
//        bindingDialogVehicle.apply {
//            val listVehicleAdapter = ListVehicleAdapter(listVehicles)
//
//            rvVehicles.layoutManager = LinearLayoutManager(this@MainActivity)
//            rvVehicles.addItemDecoration(
//                DividerItemDecoration(
//                    this@MainActivity,
//                    LinearLayoutManager.VERTICAL
//                )
//            )
//            rvVehicles.adapter = listVehicleAdapter
//            listVehicleAdapter.setOnItemClickCallback(object :
//                ListVehicleAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: Vehicle) {
//                    vehicleId = data.vehicleId!!
//                    bindingDialog.tvPickVehicle.text = data.type
//                    bottomDialogVehicle.dismiss()
//                }
//            })
//        }
//    }

    private fun openDialogErrorToken(message: String?, isActive: Int) {
        val verifyDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .create()
        bindingDialogErrorRefreshtokenBinding =
            DialogErrorRefreshtokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingDialogErrorRefreshtokenBinding.apply {
            if (message != null) {
                if (isActive == 2 || isActive == 3) {
                    tvDerTokenMessage.text = message
                }
            }

            btnDerTokenClose.setOnClickListener {
                val sharedPreferences =
                    getSharedPreferences(Constanta.my_shared_preferences, Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().commit()
//            PushNotifications.stop()
//            PushNotifications.clearAllState()

                val log = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(log)
                overridePendingTransition(R.anim.faded_in, R.anim.faded_out)
                verifyDialog.dismiss()
                finish()
            }
        }


        verifyDialog.show()

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    // end upload image and get permissions -|

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 20
        private const val USER_ID: String = "BkuF8at7TQ"
    }

}