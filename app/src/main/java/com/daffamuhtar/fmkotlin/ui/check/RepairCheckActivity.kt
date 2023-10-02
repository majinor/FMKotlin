package com.daffamuhtar.fmkotlin.ui.check

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairCheckBinding
import com.daffamuhtar.fmkotlin.data.Filter
import com.daffamuhtar.fmkotlin.data.Repair
import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.ui.adapter.CheckAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.main.MainViewModel
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailActivity
import com.daffamuhtar.fmkotlin.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairCheckActivity : AppCompatActivity() {

    private val repairCheckViewModel : RepairCheckViewModel by viewModel()

    private lateinit var binding: ActivityRepairCheckBinding

    private val context: Context = this@RepairCheckActivity

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()

    private val repairAdapter: RepairAdapter = RepairAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        declare()
        initViewModelObserver()
        prepareFilter()
        prepareView()
        callData()
    }

    private fun prepareView() {
        binding.srCheck.setOnRefreshListener {
            repairCheckViewModel.getAllCheckRepair("MEC-MBA-99")

        }
    }

    private fun callData() {
        repairCheckViewModel.getAllCheckRepair("MEC-MBA-99")

    }

    private fun initViewModelObserver() {
        repairCheckViewModel.repairList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.srCheck.isRefreshing = false
                    it.data?.let { users -> setReportResults(users) }
                }
                Status.LOADING -> {
                    binding.srCheck.isRefreshing = true
                    Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                }
                Status.ERROR -> {
                    binding.srCheck.isRefreshing = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun declare() {

    }

    private fun loading(value: Boolean) {
        binding.srCheck.isRefreshing = value
    }

    private fun setReportResults(repairs: List<RepairCheckResponse>) {
        repairList.clear()
        val listReport = ArrayList<Repair>()

        for (repair in repairs) {
            repair.apply {
                val getResult = Repair(
                    orderId,
                    spkId = null,
                    pbId = null,
                    stageId,
                    stageName = null,
                    vehicleId,
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
                    startAssignment,
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

        binding.rvCoFilter.layoutManager = LinearLayoutManager(this@RepairCheckActivity,LinearLayoutManager.HORIZONTAL,false)
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

        binding.rvCheck.layoutManager = LinearLayoutManager(this@RepairCheckActivity)
        binding.rvCheck.adapter = repairAdapter

        repairAdapter.setOnItemClickCallback(object : RepairAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Repair, position: Int) {
                Toast.makeText(context, data.orderId, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, RepairDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_SPKID, data.spkId)
                intent.putExtra(Constants.EXTRA_ORDERID, data.orderId)
                intent.putExtra(Constants.EXTRA_VID, data.vehicleId)
                intent.putExtra(Constants.EXTRA_VBRAND, data.vehicleBrand)
                intent.putExtra(Constants.EXTRA_VTYPE, data.vehicleType)
                intent.putExtra(Constants.EXTRA_VVAR, data.vehicleVarian)
                intent.putExtra(Constants.EXTRA_VYEAR, data.vehicleYear)
                intent.putExtra(Constants.EXTRA_VLICEN, data.vehicleLicenseNumber)
                intent.putExtra(Constants.EXTRA_VDIS, data.vehicleDistrict)
                intent.putExtra(Constants.EXTRA_VLID, data.vehicleLambungId)
//                if (position ==0 ){
//                    intent.putExtra(Constanta.EXTRA_STAGEID, "13".toInt())
//                }else{
                    intent.putExtra(Constants.EXTRA_STAGEID, data.stageId.toInt())
//                }
                intent.putExtra(Constants.EXTRA_STAGENAME, data.stageName)
                intent.putExtra(Constants.EXTRA_LOCOPTION, data.locationOption)
                intent.putExtra(Constants.EXTRA_SASSIGN, data.startAssignment)
                intent.putExtra(Constants.EXTRA_ISSTORING, data.isStoring)
                intent.putExtra(Constants.EXTRA_NOTESA, data.noteSA)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        callData()
    }
}