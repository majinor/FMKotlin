package com.daffamuhtar.fmkotlin.ui.repair_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.constants.ConstantsRepair
import com.daffamuhtar.fmkotlin.data.model.Photo
import com.daffamuhtar.fmkotlin.data.model.RepairDetailAfterRepair
import com.daffamuhtar.fmkotlin.data.model.RepairDetailAfterRepairInspection
import com.daffamuhtar.fmkotlin.data.model.RepairDetailAfterRepairTireInspection
import com.daffamuhtar.fmkotlin.data.model.RepairDetailPart
import com.daffamuhtar.fmkotlin.data.model.RepairDetailProblem
import com.daffamuhtar.fmkotlin.data.model.TireConditionCategory
import com.daffamuhtar.fmkotlin.data.response.RepairDetailActiveDriverResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailAfterCheckResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailAfterRepairComplainResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailAfterRepairInspectionResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailAfterRepairResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailAfterRepairWasteResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailMechanicInfoResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailNoteResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailPartResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailProblemResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailWorkshopInfoResponse
import com.daffamuhtar.fmkotlin.data.response.TireConditionCategoryResponse
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairDetailBinding
import com.daffamuhtar.fmkotlin.ui.adapter.PhotoAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailAfterRepairAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailAfterRepairInspectionAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailPartAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailProblemAdapter
import com.daffamuhtar.fmkotlin.ui.bottomsheet.AdditionalPartRequestBottomSheet
import com.daffamuhtar.fmkotlin.ui.scanner.ScannerActivity
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.toEditable
import com.daffamuhtar.fmkotlin.util.VehicleHelper
import com.fleetify.fleetifydriverum.cons.ConstantsTire

class RepairDetailActivity : AppCompatActivity() {

    private lateinit var repairDetailViewModel: RepairDetailViewModel
    private lateinit var binding: ActivityRepairDetailBinding

    private val context: Context = this

    private var reqType: String? = null
    private var actionType: String? = null
    private var orderType: String? = null
    private lateinit var orderId: String
    private var spkId: String? = null
    private var repairDate: String? = null
    private var stageId: Int = 0
    private var stageName: String? = null
    private var pbId: String? = null
    private var isStoring: String? = null
    private var startRepairOdometer: String? = null
    private var isUsingTMS: Int = 0
    private var locationOption: String? = null
    private var noteSA: String? = null

    private var noteAfterRepairFromMechanic: String? = null

    private lateinit var vehicleId: String
    private var vehiclePhoto: String? = null
    private var vehicleBrand: String? = null
    private var vehicleType: String? = null
    private var vehicleVarian: String? = null
    private var vehicleYear: String? = null
    private var vehicleLicenseNumber: String? = null
    private var vehicleLambungId: String? = null
    private var vehicleDistrict: String? = null

    private val repairDetailProblemAdapter: RepairDetailProblemAdapter =
        RepairDetailProblemAdapter()
    private val repairDetailProblemList: ArrayList<RepairDetailProblem> = ArrayList()

    private val repairDetailPartAdapter: RepairDetailPartAdapter = RepairDetailPartAdapter()
    private val repairDetailParts: ArrayList<RepairDetailPart> = ArrayList()

    private val repairDetailAfterCheckAdapter: PhotoAdapter = PhotoAdapter()
    private val repairDetailAfterCheckPhoto: ArrayList<Photo> = ArrayList()

    private val repairDetailAfterRepairAdapter: RepairDetailAfterRepairAdapter =
        RepairDetailAfterRepairAdapter()
    private val repairDetailAfterRepairList: ArrayList<RepairDetailAfterRepair> = ArrayList()

    private val repairDetailAfterRepairInspectionAdapter: RepairDetailAfterRepairInspectionAdapter =
        RepairDetailAfterRepairInspectionAdapter()

    private val repairDetailAfterRepairInspectionList: ArrayList<RepairDetailAfterRepairInspection> =
        ArrayList()

    private val repairDetailAfterRepairTireInspectionList: ArrayList<RepairDetailAfterRepairTireInspection> =
        ArrayList()

    private val tireConditionCategoryList: ArrayList<TireConditionCategory> =
        ArrayList()

    private val repairDetailAfterRepairWasteAdapter: PhotoAdapter = PhotoAdapter()
    private val repairDetailAfterRepairWastePhoto: ArrayList<Photo> = ArrayList()

    private val repairDetailAfterRepairComplainAdapter: PhotoAdapter = PhotoAdapter()
    private val repairDetailAfterRepairComplainPhoto: ArrayList<Photo> = ArrayList()
//
//    private val mRepairDetailDoneNewAdapter: RepairDetailDoneNewAdapter? = null
//    private val mRepairDetailDoneNewList: ArrayList<RepairDetailDoneNewModel>? = null
//
//    private val repairDetailItemInspectionAdapter: RepairDetailItemInspectionAdapter? = null
//    private val repairDetailItemInspectionModels: ArrayList<RepairDetailItemInspectionModel>? = null
//
//    private val mRepairDetailDoneTireAdapter: RepairDetailDoneTireAdapter? = null
//    private val mRepairDetailDoneTireList: ArrayList<RepairDetailDoneTireModel>? = null
//
//    private val mRepairDetailWasteTireAdapter: RepairDetailWasteTireAdapter? = null
//    private val mRepairDetailWasteTireList: ArrayList<RepairDetailWasteTireModel>? = null
//
//    private val tireInspectionItemAdapter: TireInspectionItemAdapter? = null
//    private val repairDetailAfterRepairTireInspections: ArrayList<repairDetailAfterRepairTireInspection>? = null
//
//    private val tireConditionCategoryModels: ArrayList<TireConditionCategoryModel>? = null

    private lateinit var ivTireFR: ImageView
    private lateinit var ivTireFL: ImageView
    private lateinit var ivTireRL: ImageView
    private lateinit var ivTireRR: ImageView
    private lateinit var ivTireRLO: ImageView
    private lateinit var ivTireRLI: ImageView
    private lateinit var ivTireRRI: ImageView
    private lateinit var ivTireRRO: ImageView
    private lateinit var ivTire2RLO: ImageView
    private lateinit var ivTire2RLI: ImageView
    private lateinit var ivTire2RRI: ImageView
    private lateinit var ivTire2RRO: ImageView
    private lateinit var ivTireS: ImageView

    private lateinit var ivTireConditionFL: ImageView
    private lateinit var ivTireConditionFR: ImageView
    private lateinit var ivTireConditionRL: ImageView
    private lateinit var ivTireConditionRR: ImageView
    private lateinit var ivTireConditionRLI: ImageView
    private lateinit var ivTireConditionRLO: ImageView
    private lateinit var ivTireConditionRRI: ImageView
    private lateinit var ivTireConditionRRO: ImageView
    private lateinit var ivTireCondition2RLI: ImageView
    private lateinit var ivTireCondition2RLO: ImageView
    private lateinit var ivTireCondition2RRI: ImageView
    private lateinit var ivTireCondition2RRO: ImageView
    private lateinit var ivTireConditionS: ImageView

    private lateinit var tireInspectionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = Html.fromHtml("<b>Detail Perbaikan</b>")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        declare()
        getData()
        setData()
        prepareView()
        initViewModel()

    }

    private fun setData() {
        binding.lyAssignmentNote.visibility = View.GONE

        binding.tvRepairTitle.text = RepairHelper.getRepairTitle(orderId, isStoring)
        binding.ivRepairIcon.setBackgroundResource(
            RepairHelper.getRepairIcon(
                orderId, isStoring
            )
        )
        binding.tvRepairId.text = RepairHelper.getRepairId(orderId, spkId)
        binding.tvRepairDate.text = RepairHelper.getRepairDate(repairDate!!)

        binding.tvVehicleName.text = VehicleHelper.getVehicleName(
            vehicleId!!,
            vehicleBrand!!,
            vehicleType!!,
            vehicleVarian!!,
            vehicleYear!!,
            vehicleLicenseNumber!!
        )

        noteSA?.let {
            binding.lyAssignmentNote.visibility = View.VISIBLE
            binding.tvAssignmentNote.text = noteSA
        }
    }

    private fun declare() {
        repairDetailViewModel = ViewModelProvider(this)[RepairDetailViewModel::class.java]
    }

    private fun initViewModel() {
        initViewModelIsLoading()
        initViewModelIsSuccess()

        repairDetailViewModel.repairStageId.observe(this) {

            Log.d("StageId ", "view model " + stageId)
            stageId = it
            RepairHelper.setRepairStage(
                this,
                it,
                stageName,
                binding.tvRepairStage,
                binding.ivRepairStageIcon,
                binding.lyRepairStage
            )

            RepairHelper.setRepairDetailLayout(
                this,
                it,
                orderType!!,
                noteAfterRepairFromMechanic,
                binding.cvProblem,
                binding.cvDriver,
                binding.cvCheck,
                binding.cvParts,
                binding.cvReportv,
                binding.cvAfterRepairInspection,
                binding.cvAfterRepairTireInspection,
                binding.cvReportx,
                binding.cvComplain,
                binding.lyAfterCheckNote,
                binding.lyAfterRepairNote,
                binding.etAfterCheckNote,
                binding.etAfterRepairNote,
                binding.lyAdditionalPartRequest,
                binding.btnAdditionalPartRequest,
                binding.btnRepairStart,
                binding.btnRepairStart,
                binding.btnRepairNext,
                binding.btnRepairDone,
                binding.btnCheckStart,
                binding.btnCheckDone,
            )
        }

        repairDetailViewModel.problemList.observe(this) {
            if (it.isNotEmpty()) {
                setProblemList(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.activeDriver.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    setActiveDriver(it)
                } else {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        repairDetailViewModel.workshopInfo.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    setWorkshopInfo(it)
                } else {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        repairDetailViewModel.mechanicInfo.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    setMechanicInfo(it)
                } else {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        repairDetailViewModel.partList.observe(this) {
            if (it.isNotEmpty()) {
                setPartList(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.note.observe(this) {
            if (it.isNotEmpty()) {
                setNote(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.afterCheck.observe(this) {
            if (it.isNotEmpty()) {
                setAfterCheck(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.afterRepairList.observe(this) {
            if (it.isNotEmpty()) {
                setAfterRepair(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.afterRepairInspectionList.observe(this) {
            if (it.isNotEmpty()) {
                setAfterRepairInspection(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.tireConditionCategoryList.observe(this) {
            if (it.isNotEmpty()) {
                setTireConditionCategoryList(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

        repairDetailViewModel.afterRepairWaste.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    setAfterRepairWaste(it)
                } else {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
            }
        }

        repairDetailViewModel.afterRepairComplain.observe(this) {
            if (it.isNotEmpty()) {
                setAfterRepairComplain(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun callData() {
        Log.i("callData stageId ", "" + stageId)
        repairDetailViewModel.getRepairDetailRepairStage(
            this, stageId
        )
        repairDetailViewModel.getRepairDetailProblemList(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailActiveDriver(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailWorkshopInfo(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailMechanicInfo(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailPartList(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId, pbId
        )
        repairDetailViewModel.getRepairDetailNote(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailAfterCheck(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailAfterRepairList(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId, pbId
        )
        repairDetailViewModel.getRepairDetailAfterRepairInspectionList(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId, pbId
        )
        repairDetailViewModel.getRepairDetailAfterRepairWaste(
            this, ConstantsApp.BASE_URL_V1_0, orderType, spkId
        )
        repairDetailViewModel.getRepairDetailAfterRepairComplain(
            this, ConstantsApp.BASE_URL_V1_0, orderType, orderId
        )

    }

    private fun loadAfterRepairTireInspection(it: Boolean?) {

    }

    private fun setTireInspectionResultIntoChassis() {
        for (repairDetailAfterRepairTireInspection in repairDetailAfterRepairTireInspectionList) {
            when (repairDetailAfterRepairTireInspection.tirePositionId) {
                ConstantsTire.TIRE_POSITION_FR -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireFR,
                    ivTireConditionFR
                )

                ConstantsTire.TIRE_POSITION_FL -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireFL,
                    ivTireConditionFL
                )

                ConstantsTire.TIRE_POSITION_RR -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRR,
                    ivTireConditionRR
                )

                ConstantsTire.TIRE_POSITION_RL -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRL,
                    ivTireConditionRL
                )

                ConstantsTire.TIRE_POSITION_RRI -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRRI,
                    ivTireConditionRRI
                )

                ConstantsTire.TIRE_POSITION_RRO -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRRO,
                    ivTireConditionRRO
                )

                ConstantsTire.TIRE_POSITION_RLI -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRLI,
                    ivTireConditionRLI
                )

                ConstantsTire.TIRE_POSITION_RLO -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireRLO,
                    ivTireConditionRLO
                )

                ConstantsTire.TIRE_POSITION_2RRI -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTire2RRI,
                    ivTireCondition2RRI
                )

                ConstantsTire.TIRE_POSITION_2RRO -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTire2RRO,
                    ivTireCondition2RRO
                )

                ConstantsTire.TIRE_POSITION_2RLI -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTire2RLI,
                    ivTireCondition2RLI
                )

                ConstantsTire.TIRE_POSITION_2RLO -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTire2RLO,
                    ivTireCondition2RLO
                )

                ConstantsTire.TIRE_POSITION_S -> setConditionResult(
                    repairDetailAfterRepairTireInspection.tireConditionId,
                    ivTireS,
                    ivTireConditionS
                )
            }
        }
        for (repairDetailAfterRepairTireInspection in repairDetailAfterRepairTireInspectionList) {
            tireInspectionId = repairDetailAfterRepairTireInspection.inspectionId
        }
    }

    private fun setConditionResult(
        tireConditionCategory: Int,
        ivSelelectedTire: ImageView,
        ivSelelectedTireCondition: ImageView
    ) {

        if (tireConditionCategory == 0) {
            ivSelelectedTire.setBackgroundResource(R.drawable.background_rounded_tire_grey)
        } else if (tireConditionCategory == 1) {
            ivSelelectedTire.setBackgroundResource(R.drawable.background_rounded_tire_green)
        } else {
            ivSelelectedTire.setBackgroundResource(R.drawable.background_rounded_tire_red)
        }
    }

    private fun initViewModelIsLoading() {
        repairDetailViewModel.isLoadingGetAllRepairProblem.observe(this) {
            loadAllRepairProblem(it)
        }
        repairDetailViewModel.isLoadingGetRepairActiveDriver.observe(this) {
            loadActiveDriver(it)

        }
        repairDetailViewModel.isLoadingGetRepairWorkshopAndMechanicInfo.observe(this) {
            loadWorkshopAndMechanicInfo(it)

        }
        repairDetailViewModel.isLoadingGetRepairDetailPartList.observe(this) {
            loadPartList(it)

        }
        repairDetailViewModel.isLoadingGetRepairDetailAfterCheck.observe(this) {
            loadAfterCheck(it)

        }
        repairDetailViewModel.isLoadingGetRepairDetailAfterRepairList.observe(this) {
            loadAfterRepair(it)
        }

        repairDetailViewModel.isLoadingGetRepairDetailAfterRepairInspectionList.observe(this) {
            loadAfterRepairInspection(it)
        }

        repairDetailViewModel.isLoadingGetRepairDetailAfterRepairTireInspectionList.observe(this) {
            loadAfterRepairTireInspection(it)
        }

        repairDetailViewModel.isLoadingGetRepairDetailAfterRepairWaste.observe(this) {
            loadAfterRepairWaste(it)

        }
        repairDetailViewModel.isLoadingGetRepairDetailAfterRepairComplain.observe(this) {
            loadAfterRepairWaste(it)

        }

    }

    private fun initViewModelIsSuccess() {
        repairDetailViewModel.isSuccessGetRepairDetailProblemList.observe(this) {
            setIsSuccessGetRepairDetailProblemList(it)
        }

        repairDetailViewModel.isSuccessGetRepairActiveDriver.observe(this) {
            setIsSuccessGetRepairActiveDriver(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailAfterCheck.observe(this) {
            setIsSuccessGetRepairDetailAfterCheck(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailPartList.observe(this) {
            setIsSuccessGetRepairDetailPartList(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailAfterRepairList.observe(this) {
            setIsSuccessGetRepairDetailAfterRepairList(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailAfterRepairInspectionList.observe(this) {
            setIsSuccessGetRepairDetailAfterRepairInspectionList(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailTireConditionCategoryList.observe(this) {
            setIsSuccessGetRepairDetailTireConditionCategoryList(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailAfterRepairWaste.observe(this) {
            setIsSuccessGetRepairDetailAfterRepairWaste(it)
        }

        repairDetailViewModel.isSuccessGetRepairDetailAfterRepairComplain.observe(this) {
            setIsSuccessGetRepairDetailAfterRepairComplain(it)
        }

    }


    private fun setIsSuccessGetRepairDetailProblemList(it: Boolean) {
        binding.cvProblem.visibility = if (it) View.VISIBLE else View.GONE

    }

    private fun setIsSuccessGetRepairActiveDriver(it: Boolean) {
        binding.cvDriver.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setIsSuccessGetRepairDetailPartList(it: Boolean) {
        binding.cvParts.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setIsSuccessGetRepairDetailAfterCheck(it: Boolean) {
        if (stageId != 12 or 13) {
            binding.cvCheck.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setIsSuccessGetRepairDetailAfterRepairList(it: Boolean) {
        if (stageId != 18 && stageId != 19) {
            binding.cvReportv.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setIsSuccessGetRepairDetailAfterRepairInspectionList(it: Boolean) {
        if (ConstantsRepair.repairOrderStageAfterRepair.contains(stageId)) {
            binding.cvAfterRepairInspection.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setIsSuccessGetRepairDetailTireConditionCategoryList(it: Boolean) {
        if (stageId != 18 && stageId != 19) {
        } else {
            repairDetailViewModel.getRepairDetailAfterRepairTireInspectionList(
                this, ConstantsApp.BASE_URL_V1_0, orderType, orderId, spkId, vehicleId
            )
        }
    }

    private fun setIsSuccessGetRepairDetailAfterRepairWaste(it: Boolean) {
        if (stageId != 18 && stageId != 19) {
            binding.cvReportx.visibility = if (it) View.VISIBLE else View.GONE
        } else {
        }

    }

    private fun setIsSuccessGetRepairDetailAfterRepairComplain(it: Boolean) {
        binding.cvComplain.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun loadAfterRepairWaste(it: Boolean) {
        if (it) {
            binding.lyShimmerRepairx.visibility = View.VISIBLE
            binding.lyRepairx.visibility = View.GONE
        } else {
            binding.lyShimmerRepairx.visibility = View.GONE
            binding.lyRepairx.visibility = View.VISIBLE
        }
    }


    private fun setAfterRepairWaste(it: List<RepairDetailAfterRepairWasteResponse>) {
        repairDetailAfterRepairWastePhoto.clear()
        val items = ArrayList<Photo>()

        for (item in it) {
            item.apply {
                item.photo1?.let {
                    items.add(Photo(item.photo1, photo1, "photo", false))
                }
                item.photo2?.let {
                    items.add(Photo(item.photo2, photo2, "photo", false))
                }
                item.photo3?.let {
                    items.add(Photo(item.photo3, photo3, "photo", false))
                }
            }
            repairDetailAfterRepairWastePhoto.addAll(items)

            setToViewAfterRepairWaste()
        }
    }

    private fun setAfterRepairComplain(it: List<RepairDetailAfterRepairComplainResponse>) {
        repairDetailAfterRepairComplainPhoto.clear()
        val items = ArrayList<Photo>()

        for (item in it) {
            item.apply {
                item.problemPhoto1?.let {
                    items.add(Photo(item.problemPhoto1, problemPhoto1, "photo", false))
                }
                item.problemPhoto2?.let {
                    items.add(Photo(item.problemPhoto2, problemPhoto2, "photo", false))
                }
                item.problemPhoto3?.let {
                    items.add(Photo(item.problemPhoto3, problemPhoto3, "photo", false))
                }
            }
            repairDetailAfterRepairComplainPhoto.addAll(items)

        }
    }

    private fun setToViewAfterRepairWaste() {
        repairDetailAfterRepairWasteAdapter.setItems(repairDetailAfterRepairWastePhoto, 3)

        binding.rvAfterRepairWaste.layoutManager = (GridLayoutManager(this, 3))
        binding.rvAfterRepairWaste.adapter = repairDetailAfterRepairWasteAdapter

        if (stageId != 13) {

        }

//        if (repairDetailAfterRepairWastePhoto.size < 3) {
//            binding.btnAddPhoto.visibility = View.VISIBLE
//        } else {
//            binding.btnAddPhoto.visibility = View.GONE
//        }
    }

    private fun setAfterRepair(it: List<RepairDetailAfterRepairResponse>) {
        repairDetailAfterRepairList.clear()
        val listProblem = ArrayList<RepairDetailAfterRepair>()

        for (item in it) {
            item.apply {

                val photos: ArrayList<Photo> = ArrayList()

                if (problemPhoto1 != null) {
                    photos.add(
                        Photo(
                            problemPhoto1, problemPhoto1, "photo", false
                        )
                    )
                }
                if (problemPhoto2 != null) {
                    photos.add(
                        Photo(
                            problemPhoto2, problemPhoto2, "photo", false
                        )
                    )
                }
                if (problemPhoto3 != null) {
                    photos.add(
                        Photo(
                            problemPhoto3, problemPhoto3, "photo", false
                        )
                    )
                }


                val getResult = RepairDetailAfterRepair(
                    item.partIdFromFleetify,
                    item.partSku,
                    item.partName,
                    item.totalUsedPart,
                    item.partQuantity,
                    item.realPartQuantity,
                    item.isRequiredScan,
                    item.usedPartType,
                    item.isSkip,
                    photos,
                    item.isAllowToSkip,
                    item.tirePositionId,
                )

                listProblem.add(getResult)
            }
        }
        repairDetailAfterRepairList.addAll(listProblem)

        setToViewAfterRepair()
    }

    private fun setTireConditionCategoryList(it: List<TireConditionCategoryResponse>) {
        tireConditionCategoryList.clear()
        val list = ArrayList<TireConditionCategory>()

        for (item in it) {
            item.apply {

                list.add(
                    TireConditionCategory(
                        tireConditionCategoryId = tireConditionCategoryId,
                        tireConditionCategoryName = tireConditionCategoryName,
                    )
                )
            }
        }
        tireConditionCategoryList.addAll(list)
    }

    private fun setAfterRepairInspection(it: List<RepairDetailAfterRepairInspectionResponse>) {
        repairDetailAfterRepairInspectionList.clear()
        val list = ArrayList<RepairDetailAfterRepairInspection>()

        for (item in it) {
            item.apply {

                val photos: ArrayList<Photo> = ArrayList()

                if (problemPhoto1 != null) {
                    photos.add(
                        Photo(
                            problemPhoto1, problemPhoto1, "photo", false
                        )
                    )
                }
                if (problemPhoto2 != null) {
                    photos.add(
                        Photo(
                            problemPhoto2, problemPhoto2, "photo", false
                        )
                    )
                }
                if (problemPhoto3 != null) {
                    photos.add(
                        Photo(
                            problemPhoto3, problemPhoto3, "photo", false
                        )
                    )
                }


                val getResult = RepairDetailAfterRepairInspection(
                    item.itemInspectionId,
                    item.itemInspectionName,
                    item.isRequired,
                    photos,
                )

                list.add(getResult)
            }
        }
        repairDetailAfterRepairInspectionList.addAll(list)

        setToViewAfterRepairInspection()
    }

    private fun setToViewAfterRepair() {
        repairDetailAfterRepairAdapter.setItems(repairDetailAfterRepairList)
        repairDetailAfterRepairAdapter.setRepairStageId(stageId)

        binding.rvDonereport.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }
        binding.rvDonereport.adapter = repairDetailAfterRepairAdapter

    }

    private fun setToViewAfterRepairInspection() {
        repairDetailAfterRepairInspectionAdapter.setItems(repairDetailAfterRepairInspectionList)
        repairDetailAfterRepairInspectionAdapter.setRepairStageId(stageId)

        binding.rvAfterRepairInspection.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }
        binding.rvAfterRepairInspection.adapter = repairDetailAfterRepairInspectionAdapter

    }

    private fun loadAfterRepair(it: Boolean) {
        if (it) {
            binding.lyShimmerRepairv.visibility = View.VISIBLE
            binding.lyRepairv.visibility = View.GONE
        } else {
            binding.lyShimmerRepairv.visibility = View.GONE
            binding.lyRepairv.visibility = View.VISIBLE
        }
    }

    private fun loadAfterRepairInspection(it: Boolean) {
        if (it) {
            binding.lyShimmerAfterRepairInspection.visibility = View.VISIBLE
            binding.lyAfterRepairInspection.visibility = View.GONE
        } else {
            binding.lyShimmerAfterRepairInspection.visibility = View.GONE
            binding.lyAfterRepairInspection.visibility = View.VISIBLE
        }

        if (repairDetailAfterRepairInspectionList.isNotEmpty()) {
            binding.cvAfterRepairInspection.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun loadPartList(it: Boolean) {
        if (it) {
            binding.lyShimmerParts.visibility = View.VISIBLE
            binding.lyParts.visibility = View.GONE
        } else {
            binding.lyShimmerParts.visibility = View.GONE
            binding.lyParts.visibility = View.VISIBLE
        }
    }

    private fun loadAfterCheck(it: Boolean) {
        if (it) {
            binding.lyShimmerChecking.visibility = View.VISIBLE
            binding.lyChecking.visibility = View.GONE
        } else {
            binding.lyShimmerChecking.visibility = View.GONE
            binding.lyChecking.visibility = View.VISIBLE
        }
    }

    private fun setAfterCheck(it: List<RepairDetailAfterCheckResponse>) {
        repairDetailAfterCheckPhoto.clear()
        val items = ArrayList<Photo>()

        for (item in it) {
            item.apply {
                item.problemPhoto1?.let {
                    items.add(
                        Photo(
                            item.problemPhoto1,
                            problemPhoto1,
                            "photo",
                            RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
                item.problemPhoto2?.let {
                    items.add(
                        Photo(
                            item.problemPhoto2, problemPhoto2, "photo", RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
                item.problemPhoto3?.let {
                    items.add(
                        Photo(
                            item.problemPhoto3, problemPhoto3, "photo", RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
                item.problemPhoto4?.let {
                    items.add(
                        Photo(
                            item.problemPhoto4, problemPhoto4, "photo", RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
                item.problemPhoto5?.let {
                    items.add(
                        Photo(
                            item.problemPhoto5, problemPhoto5, "photo", RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
                item.problemPhoto6?.let {
                    items.add(
                        Photo(
                            item.problemPhoto6, problemPhoto6, "photo", RepairHelper.isEdistable(
                                context,
                                stageId,
                                6,
                                ConstantsRepair.REPAIR_SECTION_CHECK
                            )
                        )
                    )
                }
            }
        }

        items.add(
            Photo(
                "add", "add", "add", RepairHelper.isEdistable(
                    context,
                    stageId,
                    6,
                    ConstantsRepair.REPAIR_SECTION_CHECK
                )
            )
        )

        repairDetailAfterCheckPhoto.addAll(items)

        setToViewAfterCheck()
    }

    private fun setToViewAfterCheck() {
        repairDetailAfterCheckAdapter.setItems(repairDetailAfterCheckPhoto, 6)
        repairDetailAfterCheckAdapter.setOnItemClickCallback(object :
            PhotoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Photo, position: Int) {
                Toast.makeText(context, "Add photo after check", Toast.LENGTH_SHORT).show()
            }

        })

        binding.rvAfterCheckPhoto.layoutManager = (GridLayoutManager(this, 3))
        binding.rvAfterCheckPhoto.adapter = repairDetailAfterCheckAdapter

    }

    private fun setNote(it: List<RepairDetailNoteResponse>) {

        binding.etAfterCheckNote.text = (it[0].noteCheckFromMechanic)?.toEditable()
        binding.etAfterRepairNote.text = (it[0].noteAfterRepairFromMechanic)?.toEditable()
        val saName = it[0].saName
        binding.tvCverif.text = ("Diverifikasi oleh $saName (SA)")
        binding.tvWarehouseName.text = (it[0].warehouseName)

    }

    private fun setPartList(it: List<RepairDetailPartResponse>) {
        repairDetailParts.clear()
        val items = ArrayList<RepairDetailPart>()

        for (item in it) {
            item.apply {

                val getResult = RepairDetailPart(
                    item.partSku,
                    item.partName,
                    item.partBrand,
                    item.partQuantity,
                    item.partUnit,

                    item.newPartId,
                    item.itemPrice,
                    item.totalPrice,
                )

                items.add(getResult)
            }
        }
        repairDetailParts.addAll(items)

        setToViewPartList()

    }

    private fun setToViewPartList() {
        repairDetailPartAdapter.setItems(repairDetailParts)

        binding.rvPart.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }
        binding.rvPart.adapter = repairDetailPartAdapter

    }

    private fun setWorkshopInfo(it: List<RepairDetailWorkshopInfoResponse>) {
        binding.tvWorkshopName.text = it[0].workshopName
        binding.tvWorkshopAddress.text = it[0].locationAddress

        it[0].isRequiredTireInspection?.let { isRequiredTireInspection ->

            repairDetailViewModel.getRepairDetailTireConditionCategoryList(
                this, ConstantsApp.BASE_URL_V1_0, vehicleId
            )

            binding.cvAfterRepairTireInspection.visibility =
                if (isRequiredTireInspection) View.VISIBLE else View.GONE

            it[0].vehicleChassisTypeId?.let { vehicleChassisTypeId ->
                declareTirePosition(vehicleChassisTypeId)

            }
        }
    }

    private fun declareTirePosition(vehicleChassisTypeId: String) {

        when (vehicleChassisTypeId) {
            Constants.VEHICLE_CHASSIS_TYPE_CDE -> {
                binding.lyRdCde.visibility = View.VISIBLE

                ivTireFR = binding.ivRdCdeFr
                ivTireFL = binding.ivRdCdeFl
                ivTireRR = binding.ivRdCdeRr
                ivTireRL = binding.ivRdCdeRl
                ivTireS = binding.ivRdCdeS


                ivTireConditionFR = binding.ivRdCdeFrcondition
                ivTireConditionFL = binding.ivRdCdeFlcondition
                ivTireConditionRR = binding.ivRdCdeRrcondition
                ivTireConditionRL = binding.ivRdCdeRlcondition
                ivTireConditionS = binding.ivRdCdeScondition


            }

            Constants.VEHICLE_CHASSIS_TYPE_CDD -> {
                binding.lyRdCdd.visibility = View.VISIBLE

                ivTireFR = binding.ivRdCddFr
                ivTireFL = binding.ivRdCddFl
                ivTireRRI = binding.ivRdCddRri
                ivTireRRO = binding.ivRdCddRro
                ivTireRLI = binding.ivRdCddRli
                ivTireRLO = binding.ivRdCddRlo
                ivTireS = binding.ivRdCddS


                ivTireConditionFR = binding.ivRdCddFrcondition
                ivTireConditionFL = binding.ivRdCddFlcondition
                ivTireConditionRRI = binding.ivRdCddRricondition
                ivTireConditionRRO = binding.ivRdCddRrocondition
                ivTireConditionRLI = binding.ivRdCddRlicondition
                ivTireConditionRLO = binding.ivRdCddRlocondition
                ivTireConditionS = binding.ivRdCddScondition
            }

            Constants.VEHICLE_CHASSIS_TYPE_TRN -> {
                binding.lyRdTrn.visibility = View.VISIBLE

                ivTireFR = binding.ivRdTrnFr
                ivTireFL = binding.ivRdTrnFl
                ivTireRRI = binding.ivRdTrnRri
                ivTireRRO = binding.ivRdTrnRro
                ivTireRLI = binding.ivRdTrnRli
                ivTireRLO = binding.ivRdTrnRlo
                ivTire2RRI = binding.ivRdTrn2rri
                ivTire2RRO = binding.ivRdTrn2rro
                ivTire2RLI = binding.ivRdTrn2rli
                ivTire2RLO = binding.ivRdTrn2rlo
                ivTireS = binding.ivRdTrnS

                ivTireConditionFR = binding.ivRdTrnFrcondition
                ivTireConditionFL = binding.ivRdTrnFlcondition
                ivTireConditionRRI = binding.ivRdTrnRricondition
                ivTireConditionRRO = binding.ivRdTrnRrocondition
                ivTireConditionRLI = binding.ivRdTrnRlicondition
                ivTireConditionRLO = binding.ivRdTrnRlocondition
                ivTireCondition2RRI = binding.ivRdTrn2rricondition
                ivTireCondition2RRO = binding.ivRdTrn2rrocondition
                ivTire2RLI = binding.ivRdTrn2rlicondition
                ivTire2RLO = binding.ivRdTrn2rlocondition
                ivTireS = binding.ivRdTrnScondition
            }
        }

    }

    private fun setMechanicInfo(it: List<RepairDetailMechanicInfoResponse>) {
        binding.tvMechanicName.text = it[0].mechanicName
        binding.tvMechanicPhone.text = it[0].mechanicPhone
        RepairHelper.setOnClickChat(
            this@RepairDetailActivity, it[0].mechanicPhone, binding.btnWhatsappMechanic
        )
    }

    private fun loadWorkshopAndMechanicInfo(it: Boolean) {
        if (it) {
            binding.lyShimmerWorkshop.visibility = View.VISIBLE
            binding.lyWorkshop.visibility = View.GONE
        } else {
            binding.lyShimmerWorkshop.visibility = View.GONE
            binding.lyWorkshop.visibility = View.VISIBLE
        }
    }

    private fun loadActiveDriver(it: Boolean) {
        if (it) {
            binding.lyShimmerDriver.visibility = View.VISIBLE
            binding.lyDriver.visibility = View.GONE
        } else {
            binding.lyShimmerDriver.visibility = View.GONE
            binding.lyDriver.visibility = View.VISIBLE

        }
    }

    private fun loadAllRepairProblem(it: Boolean) {
        if (it) {
            binding.lyShimmerProblem.visibility = View.VISIBLE
            binding.rvProblem.visibility = View.GONE
        } else {
            binding.lyShimmerProblem.visibility = View.GONE
            binding.rvProblem.visibility = View.VISIBLE
        }
    }

    private fun setActiveDriver(it: List<RepairDetailActiveDriverResponse>) {
        binding.tvDriverName.text = it[0].driverName
        RepairHelper.setDriverPhoto(this, it[0].driverPhoto, binding.ivDriverPhoto)
        RepairHelper.setOnClickChat(
            this@RepairDetailActivity, it[0].driverPhone, binding.btnWhatsappMechanic
        )
    }

    private fun setProblemList(items: List<RepairDetailProblemResponse>) {
        repairDetailProblemList.clear()
        val listProblem = ArrayList<RepairDetailProblem>()

        for (item in items) {
            item.apply {

                val problemPhotos: ArrayList<Photo> = ArrayList()

                problemPhotos.add(
                    Photo(
                        problemPhoto1, problemPhoto1, "photo", false
                    )
                )
                if (problemPhoto2 != null) {
                    problemPhotos.add(
                        Photo(
                            problemPhoto2, problemPhoto2, "photo", false
                        )
                    )
                }
                if (problemPhoto3 != null) {
                    problemPhotos.add(
                        Photo(
                            problemPhoto3, problemPhoto3, "photo", false
                        )
                    )
                }


                val getResult = RepairDetailProblem(
                    item.problemId, item.problemNote, problemPhotos
                )

                listProblem.add(getResult)
            }
        }
        repairDetailProblemList.addAll(listProblem)

        setToViewProblemList()
    }

    private fun setToViewProblemList() {
        repairDetailProblemAdapter.setItems(repairDetailProblemList)

        binding.rvProblem.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }

        binding.rvProblem.adapter = repairDetailProblemAdapter

    }


    private fun prepareView() {
        callData()

        setClickListener()

        when (stageId) {
//            13 -> binding.btnAddPhoto.visibility = View.VISIBLE
//            19 -> binding.btnAddPhotoWaste.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        binding.btnAdditionalPartRequest.setOnClickListener { openBottomSheetAdditionalPartRequest() }
        binding.btnRepairStart.setOnClickListener { openScanner("start_repair") }
    }

    private fun openScanner(scannerType: String) {
        val intent = Intent(context, ScannerActivity::class.java)
        intent.putExtra(Constants.EXTRA_REQCODE, scannerType)
        intent.putExtra(Constants.EXTRA_SPKID, spkId)
        intent.putExtra(Constants.EXTRA_ORDERID, orderId)
        intent.putExtra(Constants.EXTRA_VID, vehicleId)
        intent.putExtra(Constants.EXTRA_VPHOTO, vehiclePhoto)
        intent.putExtra(
            Constants.EXTRA_VNAME,
            VehicleHelper.getVehicleName(
                vehicleId,
                vehicleBrand,
                vehicleType,
                vehicleVarian,
                vehicleYear,
                null
            )
        )
        intent.putExtra(Constants.EXTRA_VLICEN, vehicleLicenseNumber)
        intent.putExtra(Constants.EXTRA_VDIS, vehicleDistrict)
        intent.putExtra(Constants.EXTRA_VLID, vehicleLambungId)
        intent.putExtra(Constants.EXTRA_STAGEID, stageId)
        intent.putExtra(Constants.EXTRA_STAGENAME, stageName)
        intent.putExtra(Constants.EXTRA_LOCOPTION, locationOption)
        startActivity(intent)
    }

    private fun openBottomSheetAdditionalPartRequest() {
        val bundle = Bundle()
        bundle.putString(Constants.EXTRA_ORDERID, orderId)
        bundle.putString(Constants.EXTRA_SPKID, spkId)
        val preview = AdditionalPartRequestBottomSheet()
        preview.arguments = bundle
        preview.show((this).supportFragmentManager, "openBottomSheetAdditionalPartRequest")
    }

    private fun getData() {
        actionType = intent.getStringExtra(Constants.EXTRA_ACTION_TYPE)
        reqType = intent.getStringExtra(Constants.EXTRA_REQTYPE)
        orderId = intent.getStringExtra(Constants.EXTRA_ORDERID)!!
        spkId = intent.getStringExtra(Constants.EXTRA_SPKID)
        isStoring = intent.getStringExtra(Constants.EXTRA_ISSTORING)
        pbId = intent.getStringExtra(Constants.EXTRA_PBID)
        stageId = intent.getIntExtra(Constants.EXTRA_STAGEID, 0)
        stageName = intent.getStringExtra(Constants.EXTRA_STAGENAME)
        startRepairOdometer = intent.getStringExtra(Constants.EXTRA_ODO)
        vehicleId = intent.getStringExtra(Constants.EXTRA_VID)!!
        vehicleBrand = intent.getStringExtra(Constants.EXTRA_VBRAND)
        vehicleType = intent.getStringExtra(Constants.EXTRA_VTYPE)
        vehicleVarian = intent.getStringExtra(Constants.EXTRA_VVAR)
        vehicleYear = intent.getStringExtra(Constants.EXTRA_VYEAR)
        vehicleLicenseNumber = intent.getStringExtra(Constants.EXTRA_VLICEN)
        vehicleLambungId = intent.getStringExtra(Constants.EXTRA_VLID)
        vehicleDistrict = intent.getStringExtra(Constants.EXTRA_VDIS)
        noteSA = intent.getStringExtra(Constants.EXTRA_NOTESA)
        repairDate = intent.getStringExtra(Constants.EXTRA_SASSIGN)
        locationOption = intent.getStringExtra(Constants.EXTRA_LOCOPTION)
        isUsingTMS = intent.getIntExtra(Constants.EXTRA_IS_USING_TMS, 0)

        orderType = RepairHelper.getRepairOrderType(orderId, isStoring)
        Log.d("StageId ", "get data " + stageId)
    }
}