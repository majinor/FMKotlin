package com.daffamuhtar.fmkotlin.ui.scanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.constants.ConstantsDialog
import com.daffamuhtar.fmkotlin.databinding.ActivityScannerBinding
import com.daffamuhtar.fmkotlin.ui.dialog.DialogBig
import com.fleetify.fleetifydriverum.cons.ConstantsTire
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult


class ScannerActivity : AppCompatActivity() {
    private var isShowCaseScannerBottomSheetVehicle: Boolean = false
    private var reqcode: String? = null
    private var sourceid: String? = null
    private var scannerTarget: String? = null
    private var correctVehicleId: String? = null
    private var vehiclePhoto: String? = null
    private var vehicleName: String? = null
    private var vehicleLicenseNumber: String? = null
    private var vehicleLambungId: String? = null
    private var vehicleDistrict: String? = null
    private var vehicleChassisType: String? = null
    private var targetTirePositionId: String? = null
    private var targetTirePositionName: String? = null
    private var odometer: String? = null
    private var stickerRequestId: String? = null
    private var currentTireId: String? = null
    private var vehicleDistrictRequiredToScanTire: String? = null
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getData()
        prepareView()

        binding.zxingScanner.decodeContinuous(callback)
    }


    private fun prepareView() {

        checkReqCodeForBottomSheet()

        // scannerTarget 1 = vehicle, 2 = tire
        var scannerTarget = 1

        when (reqcode) {
            "umtireproblem_tirescan",
            "umtirereposition_target_tirescan",
            "umtirereposition_plugged_tirescan",
            "umtirelostreportsolving_scantire",
            ConstantsTire.TIREREPOSITION_NEW_PLUGGED_TIRESCAN -> scannerTarget = 2
        }


        var bottomSheetBehavior = BottomSheetBehavior.from<FrameLayout>(binding.sheet)

        val finalScannerTarget = scannerTarget
        binding.lyScannerBottomsheetUpper.viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.lyScannerBottomsheetUpper.viewTreeObserver.removeOnGlobalLayoutListener(this)
                var hidden = binding.lyScannerBottomsheetUpper.getChildAt(3)
                hidden = if (finalScannerTarget == 1) {
                    binding.lyScannerBottomsheetUpper.getChildAt(3)
                } else {
                    binding.lyScannerBottomsheetUpper.getChildAt(2)
                }
                bottomSheetBehavior.setPeekHeight(hidden.top)
            }
        })

        binding.sheet.setVisibility(View.VISIBLE)

    }

    private fun getData() {
        reqcode = intent.getStringExtra(Constants.EXTRA_REQCODE)
        sourceid = intent.getStringExtra(Constants.EXTRA_SOURCEID)
        correctVehicleId = intent.getStringExtra(Constants.EXTRA_VID)
        vehiclePhoto = intent.getStringExtra(Constants.EXTRA_VPHOTO)
        vehicleName = intent.getStringExtra(Constants.EXTRA_VNAME)
        vehicleLicenseNumber = intent.getStringExtra(Constants.EXTRA_VLICEN)
        vehicleLambungId = intent.getStringExtra(Constants.EXTRA_VLID)
        vehicleDistrict = intent.getStringExtra(Constants.EXTRA_VDIS)
        vehicleChassisType = intent.getStringExtra(Constants.EXTRA_VCHASSISTYPE)
        targetTirePositionId = intent.getStringExtra(Constants.EXTRA_TIREPOSITIONID)
        targetTirePositionName = intent.getStringExtra(Constants.EXTRA_TIREPOSITIONNAME)
        odometer = intent.getStringExtra(Constants.EXTRA_ODO)
        stickerRequestId = intent.getStringExtra(Constants.EXTRA_SR_ID)
        currentTireId = intent.getStringExtra(Constants.EXTRA_REQUIREDTIREID)
        vehicleDistrictRequiredToScanTire =
            intent.getStringExtra(Constants.EXTRA_VEHICLE_DISTRICT_TIRE_SCAN)

    }

    private fun checkReqCodeForBottomSheet() {
        when (reqcode) {
            "drvinspect", "umCheckIn", "umCheckOut" -> {
                binding.btnScannerManualinput.setVisibility(View.GONE)
                binding.lyScannerBottomsheetBaseVehicleInfo.setVisibility(View.GONE)
                binding.lyScannerBottomsheetVehicleInfo.setVisibility(View.GONE)
                binding.btnScannerBottomsheetLookVehicleList.setVisibility(View.GONE)
                binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.GONE)
            }

            "start_repair","drvreport", "tire_report_driver", "umtirelostreportsolving_scanvehicle", "drvinspectend" -> {
                binding.btnScannerManualinput.setVisibility(View.GONE)
                binding.lyScannerBottomsheetBaseVehicleInfo.setVisibility(View.VISIBLE)
                binding.lyScannerBottomsheetVehicleInfo.setVisibility(View.VISIBLE)
                binding.btnScannerBottomsheetLookVehicleList.setVisibility(View.GONE)
                binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.GONE)
                if (vehicleName != null) {
                    binding.tvScannerBottomsheetVehicleLicensenumber.setText(vehicleLicenseNumber)
                    binding.tvScannerBottomsheetVehicleName.setText(vehicleName)
                    binding.tvScannerBottomsheetVehicleLambungid.setText(vehicleLambungId)
                    binding.tvScannerBottomsheetVehicleDistrict.setText(vehicleDistrict)
                    if (vehiclePhoto != null) {
                        Glide.with(this)
                            .load(vehiclePhoto)
                            .override(200, 200)
                            .centerCrop()
                            .into(binding.ivScannerBottomsheetVehiclePhoto)
                    } else {
                        binding.ivScannerBottomsheetVehiclePhoto.setVisibility(View.GONE)
                    }
                }
            }

            "umtirereposition_plugged_tirescan", "umtirereposition_target_tirescan", "umtireproblem_tirescan", "umtirelostreportsolving_scantire", ConstantsTire.TIREREPOSITION_NEW_PLUGGED_TIRESCAN -> {
                binding.btnScannerManualinput.setVisibility(View.VISIBLE)
                binding.lyScannerBottomsheetBaseVehicleInfo.setVisibility(View.GONE)
                binding.lyScannerBottomsheetVehicleInfo.setVisibility(View.GONE)
                binding.btnScannerBottomsheetLookVehicleList.setVisibility(View.GONE)
                binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.GONE)
                if (vehicleName != null) {
                    binding.lyScannerBottomsheetBaseVehicleInfo.setVisibility(View.VISIBLE)
                    binding.lyScannerBottomsheetVehicleInfo.setVisibility(View.VISIBLE)
                    binding.tvScannerBottomsheetVehicleLicensenumber.setText(vehicleLicenseNumber)
                    binding.tvScannerBottomsheetVehicleName.setText(vehicleName)
                    binding.tvScannerBottomsheetVehicleLambungid.setText(vehicleLambungId)
                    binding.tvScannerBottomsheetVehicleDistrict.setText(vehicleDistrict)
                    if (vehiclePhoto != null) {
                        Glide.with(this)
                            .load(vehiclePhoto)
                            .override(200, 200)
                            .centerCrop()
                            .into(binding.ivScannerBottomsheetVehiclePhoto)
                    } else {
                        binding.ivScannerBottomsheetVehiclePhoto.setVisibility(View.GONE)
                    }
                }
            }

            "umreport", "tire_report_um", "tire_reposition_um", "customOrder_scanVehicle", "umtirereposition_target_scanvehicle", "postDoneReportSticker", ConstantsTire.TIRE_REPOSITION_CHANGE_VEHICLE_UM -> {
                binding.btnScannerManualinput.setVisibility(View.GONE)
                binding.lyScannerBottomsheetBaseVehicleInfo.setVisibility(View.GONE)
                binding.lyScannerBottomsheetVehicleInfo.setVisibility(View.GONE)
            }
        }
        if (Server.companyType == "1") {
//            binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.VISIBLE);
//            binding.btnScannerBottomsheetReportStickerIssue.setOnClickListener(View.OnClickListener { openDialogReportSticker() })
        } else {
            binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.GONE)
        }
        if (targetTirePositionId != null) {
            binding.lyScannerBottomsheetBaseTireInfo.setVisibility(View.VISIBLE)
            binding.lyScannerBottomsheetTireInfo.setVisibility(View.VISIBLE)
            binding.btnScannerBottomsheetReportStickerIssue.setVisibility(View.GONE)
            binding.tvScannerBottomsheetTirePositionId.setText(targetTirePositionId)
            scannerTarget = "tire"
            var positionName: String? = null
            if (targetTirePositionId != null) {
                when (targetTirePositionId) {
                    "FR" -> positionName = "Depan Kanan"
                    "FL" -> positionName = "Depan Kiri"
                    "RR" -> positionName = "Belakang Kanan"
                    "RL" -> positionName = "Belakang Kiri"
                    "RLO" -> positionName = "Belakang Kiri Luar"
                    "RLI" -> positionName = "Belakang Kiri Dalam"
                    "RRO" -> positionName = "Belakang Kanan Luar"
                    "RRI" -> positionName = "Belakang Kanan Dalam"
                    "2RLO" -> positionName = "Belakang Kiri Luar Baris Ke-2"
                    "2RLI" -> positionName = "Belakang Kiri Dalam Baris Ke-2"
                    "2RRO" -> positionName = "Belakang Kanan Luar Baris Ke-2"
                    "2RRI" -> positionName = "Belakang Kanan Dalam Baris Ke-2"
                    "S" -> positionName = "Serep/Cadangan"
                }
                binding.tvScannerBottomsheetTirePositionName.setText(positionName)
            }

//            Toast.makeText(context, "" + vehicleChassisType, Toast.LENGTH_SHORT).show();
//            if(vehicleChassisType!=null){
            binding.btnScannerTireShowChassis.setOnClickListener(View.OnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.EXTRA_VCHASSISTYPE, vehicleChassisType)
                bundle.putString(Constants.EXTRA_TIREPOSITIONID, targetTirePositionId)
//                val preview = VehicleChassisPreviewBottomSheet()
//                preview.setArguments(bundle)
//                preview.show(
//                    (context as AppCompatActivity).supportFragmentManager,
//                    "showVehicleChassisPreviewBottomSheet"
//                )
            })

//            }
            binding.btnScannerBottomsheetHelp.setOnClickListener(View.OnClickListener {
                val uri =
                    Uri.parse("https://vivid-production-0d1.notion.site/Input-Kode-BAN-9a9d39b0b2f2406c98b8f92efe584882") // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            })
        } else {
            scannerTarget = "vehicle"
            binding.lyScannerBottomsheetTireInfo.setVisibility(View.GONE)
            binding.lyScannerBottomsheetTireInfo.setVisibility(View.GONE)
            binding.btnScannerBottomsheetHelp.setOnClickListener(View.OnClickListener {
                val uri =
                    Uri.parse("https://vivid-production-0d1.notion.site/Cara-Scan-Barcode-Kendaraan-a95e42b2894942d8aacead4be4f862b6") // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            })
        }
    }


//    private fun checkShowcaseBottomSheet() {
//        when (scannerTarget) {
//            "vehicle" -> if (!isShowCaseScannerBottomSheetVehicle) {
//                Handler().postDelayed({
//                    BottomSheetBehavior.from<FrameLayout>(binding.bottomsheet).state =
//                        BottomSheetBehavior.STATE_EXPANDED
//                    Handler().postDelayed({
//                        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
//                            Constanta.my_shared_preferences_showcase,
//                            MODE_PRIVATE
//                        )
//                        val editor = sharedPreferences.edit()
//                        editor.putBoolean(
//                            Constanta.EXTRA_SHOWCASE_SCANNER_BOTTOMSHEET_VEHICLE,
//                            true
//                        )
//                        editor.commit()
//                        BottomSheetBehavior.from<FrameLayout>(bottomSheetDialog).state =
//                            BottomSheetBehavior.STATE_COLLAPSED
//                    }, 1500)
//                }, 500)
//            }
//
//            "tire" -> if (!isShowCaseScannerBottomSheetTire) {
//                Handler().postDelayed({
//                    BottomSheetBehavior.from<FrameLayout>(bottomSheetDialog).state =
//                        BottomSheetBehavior.STATE_EXPANDED
//                    Handler().postDelayed({
//                        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
//                            Constanta.my_shared_preferences_showcase,
//                            MODE_PRIVATE
//                        )
//                        val editor = sharedPreferences.edit()
//                        editor.putBoolean(Constanta.EXTRA_SHOWCASE_SCANNER_BOTTOMSHEET_TIRE, true)
//                        editor.commit()
//                        BottomSheetBehavior.from<FrameLayout>(bottomSheetDialog).state =
//                            BottomSheetBehavior.STATE_COLLAPSED
//                    }, 1500)
//                }, 500)
//            }
//        }
//    }


    override fun onResume() {
        binding.zxingScanner.resume()
        super.onResume()
    }

    override fun onPause() {
        binding.zxingScanner.pause()
        super.onPause()
    }

    private val callback = object : BarcodeCallback {
        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }

        override fun barcodeResult(result: BarcodeResult?) {
            if (result!!.text != null) {

                Toast.makeText(this@ScannerActivity, "${result!!.text}", Toast.LENGTH_SHORT).show()
                binding.zxingScanner.pause()
                openDialogSubmitConfirmation(result!!.text)

//                val intent = Intent()
//                val bundle = Bundle()
//                bundle.putString(KEY_RESULT, result.text.toString())
//                intent.putExtras(bundle)
//                setResult(Activity.RESULT_OK, intent)
//                val vibrator = application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//                vibrator.vibrate(100)
//                finish()
            }
        }

    }

    private fun openDialogSubmitConfirmation(title: String) {
        val dialogBig = DialogBig(this)
        dialogBig.setDialogType(ConstantsDialog.DIALOG_TYPE_CONFIRMATION)
        dialogBig.setDialogTitle(title)
        dialogBig.setDialogMessage("Part yang disetujui akan diproses lebih lanjut oleh SA")
        dialogBig.setOclPositive("Kirim") { v ->
//            postRegisterUsedPartCustomOrder(parentInformation)
            dialogBig.dismiss()
        }
        dialogBig.setOclClose("Periksa Kembali") { v ->
            dialogBig.cancel()
            binding.zxingScanner.resume()
        }
        dialogBig.show()
    }
}