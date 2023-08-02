package com.daffamuhtar.fmkotlin.ui.repair_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairDetailBinding
import com.daffamuhtar.fmkotlin.model.*
import com.daffamuhtar.fmkotlin.model.response.*
import com.daffamuhtar.fmkotlin.ui.adapter.PhotoAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailAfterRepairAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailPartAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairDetailProblemAdapter
import com.daffamuhtar.fmkotlin.ui.bottomsheet.AdditionalPartRequestBottomSheet
import com.daffamuhtar.fmkotlin.ui.bottomsheet.PhotoVideoPreviewBottomSheet
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.toEditable
import com.daffamuhtar.fmkotlin.util.VehicleHelper

class RepairDetailActivity : AppCompatActivity() {

    private lateinit var repairDetailViewModel: RepairDetailViewModel
    private lateinit var binding: ActivityRepairDetailBinding

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

    private var vehicleId: String? = null
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
    private val repairDetailProblems: ArrayList<RepairDetailProblem> = ArrayList()

    private val repairDetailPartAdapter: RepairDetailPartAdapter = RepairDetailPartAdapter()
    private val repairDetailParts: ArrayList<RepairDetailPart> = ArrayList()

    private val repairDetailAfterCheckAdapter: PhotoAdapter = PhotoAdapter()
    private val repairDetailAfterCheckPhoto: ArrayList<Photo> = ArrayList()

    private val repairDetailAfterRepairAdapter: RepairDetailAfterRepairAdapter =
        RepairDetailAfterRepairAdapter()
    private val repairDetailAfterRepairs: ArrayList<RepairDetailAfterRepair> = ArrayList()

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
//    private val tireInspectionItemModels: ArrayList<TireInspectionItemModel>? = null
//
//    private val tireConditionCategoryModels: ArrayList<TireConditionCategoryModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun callData() {
        Log.i("callData stageId ", ""+stageId)
        repairDetailViewModel.getRepairDetailRepairStage(
            this, stageId
        )
        repairDetailViewModel.getRepairDetailProblemList(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailActiveDriver(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailWorkshopInfo(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailMechanicInfo(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailPartList(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId, pbId
        )
        repairDetailViewModel.getRepairDetailNote(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailAfterCheck(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId
        )
        repairDetailViewModel.getRepairDetailAfterRepairList(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId, spkId, pbId
        )
        repairDetailViewModel.getRepairDetailAfterRepairWaste(
            this, ConstantaApp.BASE_URL_V1_0, orderType, spkId
        )
        repairDetailViewModel.getRepairDetailAfterRepairComplain(
            this, ConstantaApp.BASE_URL_V1_0, orderType, orderId
        )

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
                binding.cvReportx,
                binding.cvComplain,
                binding.btnAddPhoto,
                binding.btnAddPhotoWaste,
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

        repairDetailViewModel.afterRepairWaste.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    setAfterRepairWaste(it)
                } else {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.btnAddPhotoWaste.visibility = View.VISIBLE
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
                    binding.btnAddPhotoWaste.visibility = View.VISIBLE
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
        repairDetailAfterRepairWasteAdapter.setItems(repairDetailAfterRepairWastePhoto)

        binding.rvAfterRepairWaste.layoutManager = (GridLayoutManager(this, 3))
        binding.rvAfterRepairWaste.adapter = repairDetailAfterRepairWasteAdapter

        if (stageId != 13){
            
        }
        if (repairDetailAfterRepairWastePhoto.size < 3) {
            binding.btnAddPhoto.visibility = View.VISIBLE
        } else {
            binding.btnAddPhoto.visibility = View.GONE
        }
    }

    private fun setAfterRepair(it: List<RepairDetailAfterRepairResponse>) {
        repairDetailAfterRepairs.clear()
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
        repairDetailAfterRepairs.addAll(listProblem)

        setToViewAfterRepair()
    }

    private fun setToViewAfterRepair() {
        repairDetailAfterRepairAdapter.setItems(repairDetailAfterRepairs)
        repairDetailAfterRepairAdapter.setRepairStageId(stageId)

        binding.rvDonereport.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }
        binding.rvDonereport.adapter = repairDetailAfterRepairAdapter

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
                    items.add(Photo(item.problemPhoto1, problemPhoto1, "photo", false))
                }
                item.problemPhoto2?.let {
                    items.add(Photo(item.problemPhoto2, problemPhoto2, "photo", false))
                }
                item.problemPhoto3?.let {
                    items.add(Photo(item.problemPhoto3, problemPhoto3, "photo", false))
                }
                item.problemPhoto4?.let {
                    items.add(Photo(item.problemPhoto4, problemPhoto4, "photo", false))
                }
                item.problemPhoto5?.let {
                    items.add(Photo(item.problemPhoto5, problemPhoto5, "photo", false))
                }
                item.problemPhoto6?.let {
                    items.add(Photo(item.problemPhoto6, problemPhoto6, "photo", false))
                }
            }
        }
        repairDetailAfterCheckPhoto.addAll(items)

        setToViewAfterCheck()
    }

    private fun setToViewAfterCheck() {
        repairDetailAfterCheckAdapter.setItems(repairDetailAfterCheckPhoto)

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

                    item.partUnit,
                    item.partUnit,
                    item.partUnit,

//                    item.newPartId,
//                    item.itemPrice,
//                    item.totalPrice
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
        repairDetailProblems.clear()
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
        repairDetailProblems.addAll(listProblem)

        setToViewProblemList()
    }

    private fun setToViewProblemList() {
        repairDetailProblemAdapter.setItems(repairDetailProblems)

        binding.rvProblem.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically() = false
        }

        binding.rvProblem.adapter = repairDetailProblemAdapter

    }


    private fun prepareView() {
        callData()

        setClickListener()

        when (stageId) {
            13 -> binding.btnAddPhoto.visibility = View.VISIBLE
            19 -> binding.btnAddPhotoWaste.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        binding.btnAdditionalPartRequest.setOnClickListener { openBottomSheetAdditionalPartRequest() }
    }

    private fun openBottomSheetAdditionalPartRequest() {
        val bundle = Bundle()
        bundle.putString(Constanta.EXTRA_ORDERID, orderId)
        bundle.putString(Constanta.EXTRA_SPKID, spkId)
        val preview = AdditionalPartRequestBottomSheet()
        preview.arguments = bundle
        preview.show((this).supportFragmentManager, "openBottomSheetAdditionalPartRequest")
    }

    private fun getData() {
        actionType = intent.getStringExtra(Constanta.EXTRA_ACTION_TYPE)
        reqType = intent.getStringExtra(Constanta.EXTRA_REQTYPE)
        orderId = intent.getStringExtra(Constanta.EXTRA_ORDERID)!!
        spkId = intent.getStringExtra(Constanta.EXTRA_SPKID)
        isStoring = intent.getStringExtra(Constanta.EXTRA_ISSTORING)
        pbId = intent.getStringExtra(Constanta.EXTRA_PBID)
        stageId = intent.getIntExtra(Constanta.EXTRA_STAGEID,0)
        stageName = intent.getStringExtra(Constanta.EXTRA_STAGENAME)
        startRepairOdometer = intent.getStringExtra(Constanta.EXTRA_ODO)
        vehicleId = intent.getStringExtra(Constanta.EXTRA_VID)
        vehicleBrand = intent.getStringExtra(Constanta.EXTRA_VBRAND)
        vehicleType = intent.getStringExtra(Constanta.EXTRA_VTYPE)
        vehicleVarian = intent.getStringExtra(Constanta.EXTRA_VVAR)
        vehicleYear = intent.getStringExtra(Constanta.EXTRA_VYEAR)
        vehicleLicenseNumber = intent.getStringExtra(Constanta.EXTRA_VLICEN)
        vehicleLambungId = intent.getStringExtra(Constanta.EXTRA_VLID)
        vehicleDistrict = intent.getStringExtra(Constanta.EXTRA_VDIS)
        noteSA = intent.getStringExtra(Constanta.EXTRA_NOTESA)
        repairDate = intent.getStringExtra(Constanta.EXTRA_SASSIGN)
        locationOption = intent.getStringExtra(Constanta.EXTRA_LOCOPTION)
        isUsingTMS = intent.getIntExtra(Constanta.EXTRA_IS_USING_TMS, 0)

        orderType = RepairHelper.getRepairOrderType(orderId, isStoring)
        Log.d("StageId ", "get data " + stageId)
    }
}