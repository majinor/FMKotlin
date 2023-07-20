package com.daffamuhtar.fmkotlin.ui.repair_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairDetailBinding
import com.daffamuhtar.fmkotlin.model.*
import com.daffamuhtar.fmkotlin.model.response.CheckRepairResponse
import com.daffamuhtar.fmkotlin.model.response.RepairProblemResponse
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairProblemAdapter
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.VehicleHelper
import java.util.*
import kotlin.collections.ArrayList

class RepairDetailActivity : AppCompatActivity() {

    private lateinit var repairDetailViewModel: RepairDetailViewModel
    private lateinit var binding: ActivityRepairDetailBinding

    private var reqType: String? = null
    private var actionType: String? = null
    private var orderId: String? = null
    private var spkId: String? = null
    private var repairDate: String? = null
    private var stageId: String? = null
    private var stageName: String? = null
    private var pbId: String? = null
    private var isStoring: String? = null
    private var startRepairOdometer: String? = null
    private var isUsingTMS: Int = 0
    private var locationOption: String? = null
    private var noteSA: String? = null

    private var vehicleId: String? = null
    private var vehiclePhoto: String? = null
    private var vehicleBrand: String? = null
    private var vehicleType: String? = null
    private var vehicleVarian: String? = null
    private var vehicleYear: String? = null
    private var vehicleLicenseNumber: String? = null
    private var vehicleLambungId: String? = null
    private var vehicleDistrict: String? = null

    private val repairProblemAdapter: RepairProblemAdapter = RepairProblemAdapter()
    private val repairProblems: ArrayList<ProblemRepair> = ArrayList<ProblemRepair>()

//    private val mRepairDetailPartsAdapter: RepairDetailPartsAdapter? = null
//    private val mRepairDetailPartsList: ArrayList<RepairDetailPartsModel>? = null
//
//    private val mRepairDetailDoneAdapter: RepairDetailDoneAdapter? = null
//    private val mRepairDetailDoneList: ArrayList<RepairDetailDoneModel>? = null
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
        initObserver()

    }

    private fun setData() {
        binding.lyAssignmentNote.visibility = View.GONE

        binding.tvRepairTitle.text = RepairHelper.getRepairTitle(orderId!!, isStoring!!)
        binding.ivRepairIcon.setBackgroundResource(
            RepairHelper.getRepairIcon(
                orderId!!,
                isStoring!!
            )
        )
        binding.tvRepairId.text = RepairHelper.getRepairId(orderId!!, spkId)
        binding.tvRepairDate.text = RepairHelper.getRepairDate(repairDate!!)
        RepairHelper.setRepairStage(
            this,
            stageId!!,
            stageName,
            binding.tvRepairStage,
            binding.ivRepairStageIcon,
            binding.lyRepairStage
        )
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
        repairDetailViewModel.getAllRepairProblem(this, ConstantaApp.BASE_URL_V1_0, orderId!!)
    }

    private fun initObserver() {
        repairDetailViewModel.isLoadingGetAllRepairProblem.observe(this) {
            if (it) {
                binding.lyUcdShimmerProblem.visibility = View.VISIBLE
                binding.rvConfirmdetail.visibility = View.GONE

            } else {
                binding.lyUcdShimmerProblem.visibility = View.GONE
                binding.rvConfirmdetail.visibility = View.VISIBLE

            }
        }

        repairDetailViewModel.problemList.observe(this) {
            if (it.isNotEmpty()) {
                setReportResults(it)
            } else {
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setReportResults(items: List<RepairProblemResponse>) {
        repairProblems.clear()
        val listProblem = ArrayList<ProblemRepair>()

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


                val getResult = ProblemRepair(
                    item.problemId,
                    item.problemNote,
                    problemPhotos
                )

                listProblem.add(getResult)
            }
        }
        repairProblems.addAll(listProblem)

//        if (repairList.isEmpty()) {
//            binding.lyNotFound.visibility = View.VISIBLE
//        } else {
//            binding.lyNotFound.visibility = View.GONE
//        }

        setRecycler()
    }

    private fun setRecycler() {
        repairProblemAdapter.setItems(repairProblems)

        binding.rvConfirmdetail.layoutManager = LinearLayoutManager(this)
        binding.rvConfirmdetail.adapter = repairProblemAdapter

    }


    private fun prepareView() {
        callData()
    }

    private fun getData() {
        actionType = intent.getStringExtra(Constanta.EXTRA_ACTION_TYPE)
        reqType = intent.getStringExtra(Constanta.EXTRA_REQTYPE)
        orderId = intent.getStringExtra(Constanta.EXTRA_ORDERID)
        spkId = intent.getStringExtra(Constanta.EXTRA_SPKID)
        isStoring = intent.getStringExtra(Constanta.EXTRA_ISSTORING)
        pbId = intent.getStringExtra(Constanta.EXTRA_PBID)
        stageId = intent.getStringExtra(Constanta.EXTRA_STAGEID)
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
    }
}