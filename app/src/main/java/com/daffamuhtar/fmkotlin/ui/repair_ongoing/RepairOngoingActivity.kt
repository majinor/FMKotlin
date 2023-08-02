package com.daffamuhtar.fmkotlin.ui.check

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairOngoingBinding
import com.daffamuhtar.fmkotlin.model.Filter
import com.daffamuhtar.fmkotlin.model.Repair
import com.daffamuhtar.fmkotlin.model.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.ui.Ongoing.RepairOngoingViewModel
import com.daffamuhtar.fmkotlin.ui.adapter.CheckAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailActivity

class RepairOngoingActivity : AppCompatActivity() {

    private lateinit var repairOngoingViewModel: RepairOngoingViewModel

    private lateinit var binding: ActivityRepairOngoingBinding

    private val context: Context = this@RepairOngoingActivity

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()

    private val repairAdapter: RepairAdapter = RepairAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairOngoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        declare()
        initViewModel()
        prepareFilter()
        callData()
    }

    private fun callData() {
//        checkViewModel.getRepairOngoing(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")
        repairOngoingViewModel.getRepairOngoing(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")

    }

    private fun initViewModel() {
        repairOngoingViewModel.repairList.observe(this) {
            if (it.isNotEmpty()) {
                setReportResults(it)
            } else {
                binding.lyNotFound.visibility = View.VISIBLE
            }
        }

        repairOngoingViewModel.isLoadingGetRepairOngoing.observe(this) {
            loading(it)
        }

        binding.srCheck.setOnRefreshListener {
            repairOngoingViewModel.getRepairOngoing(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")

        }
    }

    private fun declare() {
        repairOngoingViewModel = ViewModelProvider(this)[RepairOngoingViewModel::class.java]
    }

    private fun loading(value: Boolean) {
        binding.srCheck.isRefreshing = value
    }

    private fun setReportResults(repairs: List<RepairOnAdhocResponse>) {
        repairList.clear()
        val listReport = ArrayList<Repair>()

        for (repair in repairs) {
            repair.apply {
                val getResult = Repair(
                    orderId,
                    spkId,
                    pbId = "null",
                    stageId,
                    stageName = null,
                    "vehicleId",
                    vehicleBrand,
                    vehicleType,
                    vehicleVarian,
                    vehicleYear,
                    vehicleLicenseNumber,
                    vehicleLambungId,
                    vehicleDistrict,
                    noteFromSA,
                    workshopName = null,
                    workshopLocation = null,
                    "2021-08-31 10:00:00",
                    additionalPartNote = null,
                    startRepairOdometer = null,
                    locationOption,
                    isStoring,
                    orderType = null,
                    colorCode = null

                )
                listReport.add(getResult)
            }
        }
        repairList.addAll(listReport)

        if (repairList.isEmpty()) {
            binding.lyNotFound.visibility = View.VISIBLE
        } else {
            binding.lyNotFound.visibility = View.GONE
        }

        setRecycler()
    }

    private fun prepareFilter() {
        filterList.add(
            Filter(
                true,
                0,
                "Semua"
            )
        )
        filterList.add(
            Filter(
                false,
                12,
                "Menunggu"
            )
        )
        filterList.add(
            Filter(
                false,
                13,
                "Diperiksa"
            )
        )
        filterList.add(
            Filter(
                false,
                14,
                "Approval"
            )
        )

        filterAdapter.setAllFilter(filterList)

        binding.rvCoFilter.layoutManager = LinearLayoutManager(this@RepairOngoingActivity,LinearLayoutManager.HORIZONTAL,false)
        binding.rvCoFilter.adapter = filterAdapter
        filterAdapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Filter, position: Int) {

                for (i in filterList.indices) {
                    val filter = filterList[i]
                    filter.isActive = i == position
                }
                filterAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setRecycler() {
        repairAdapter.setAllLaporan(repairList)

        binding.rvCheck.layoutManager = LinearLayoutManager(this@RepairOngoingActivity)
        binding.rvCheck.adapter = repairAdapter

        repairAdapter.setOnItemClickCallback(object : RepairAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Repair, position: Int) {
                Toast.makeText(context, data.orderId, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, RepairDetailActivity::class.java)
                intent.putExtra(Constanta.EXTRA_SPKID, data.spkId)
                intent.putExtra(Constanta.EXTRA_ORDERID, data.orderId)
                intent.putExtra(Constanta.EXTRA_PBID, data.pbId)
                intent.putExtra(Constanta.EXTRA_VID, data.vehicleId)
                intent.putExtra(Constanta.EXTRA_VBRAND, data.vehicleBrand)
                intent.putExtra(Constanta.EXTRA_VTYPE, data.vehicleType)
                intent.putExtra(Constanta.EXTRA_VVAR, data.vehicleVarian)
                intent.putExtra(Constanta.EXTRA_VYEAR, data.vehicleYear)
                intent.putExtra(Constanta.EXTRA_VLICEN, data.vehicleLicenseNumber)
                intent.putExtra(Constanta.EXTRA_VDIS, data.vehicleDistrict)
                intent.putExtra(Constanta.EXTRA_VLID, data.vehicleLambungId)
                if (position ==0 ){
                    intent.putExtra(Constanta.EXTRA_STAGEID, "13")
                }else{
                    intent.putExtra(Constanta.EXTRA_STAGEID, data.stageId.toInt())
                }
                intent.putExtra(Constanta.EXTRA_STAGENAME, data.stageName)
                intent.putExtra(Constanta.EXTRA_LOCOPTION, data.locationOption)
                intent.putExtra(Constanta.EXTRA_SASSIGN, data.startAssignment)
                intent.putExtra(Constanta.EXTRA_ISSTORING, data.isStoring)
                intent.putExtra(Constanta.EXTRA_NOTESA, data.noteSA)
                startActivity(intent)
            }
        })
    }
}