package com.daffamuhtar.fmkotlin.ui.repair_ongoing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairOngoingBinding
import com.daffamuhtar.fmkotlin.data.model.Filter
import com.daffamuhtar.fmkotlin.data.model.HorizontalCalendar
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.HorizontalCalendarAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.PagingListAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairOngoingActivity : AppCompatActivity() {

    private val repairOngoingViewModel: RepairOngoingViewModel by viewModel()

    private lateinit var binding: ActivityRepairOngoingBinding

    private val context: Context = this@RepairOngoingActivity

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()
    private val calendarList = ArrayList<HorizontalCalendar>()

    private val repairAdapter: RepairAdapter = RepairAdapter()
    private var pagingListAdapter : PagingListAdapter?=  PagingListAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()
    private lateinit var horizontalCalendarAdapter: HorizontalCalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairOngoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        declare()
        initViewModel()
        prepareCalendarView()
        prepareFilter()
        callData()
    }

    private fun prepareCalendarView() {

        calendarList.add(HorizontalCalendar("2024-2-1",5,true))
        calendarList.add(HorizontalCalendar("2024-2-2",0,false))
        calendarList.add(HorizontalCalendar("2024-2-3",0,false))
        calendarList.add(HorizontalCalendar("2024-2-4",1,false))
        calendarList.add(HorizontalCalendar("2024-2-5",0,false))
        calendarList.add(HorizontalCalendar("2024-2-6",0,false))
        calendarList.add(HorizontalCalendar("2024-2-7",1,false))
        calendarList.add(HorizontalCalendar("2024-2-8",1,false))

        horizontalCalendarAdapter.setItems(calendarList)

        binding.rvCoCalendar.layoutManager = LinearLayoutManager(this@RepairOngoingActivity,LinearLayoutManager.HORIZONTAL,false)
        binding.rvCoCalendar.adapter = horizontalCalendarAdapter

    }

    private fun callData() {
//        checkViewModel.getRepairOngoing(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")
//        repairOngoingViewModel.getRepairOngoingMetaData(this, ConstantsApp.BASE_URL_V2_0, "MEC-MBA-99")
        lifecycleScope.launch {
            repairOngoingViewModel.beerPagingFlow.collectLatest { repairs -> pagingListAdapter?.submitData(repairs)}

        }

    }

    private fun initViewModel() {
//        repairOngoingViewModel.repairList.observe(this) {
//            if (it.isNotEmpty()) {
//                setReportResults(it)
//            } else {
//                binding.lyNotFound.visibility = View.VISIBLE
//            }
//        }

//        repairOngoingViewModel.isLoadingGetRepairOngoing.observe(this) {
//            loading(it)
//        }
//
        binding.srCheck.setOnRefreshListener {
//            repairOngoingViewModel.getRepairOngoingMetaData(this, ConstantsApp.BASE_URL_V2_0, "MEC-MBA-99")
            lifecycleScope.launch {
                repairOngoingViewModel.beerPagingFlow.collectLatest { repairs -> pagingListAdapter?.submitData(repairs)}

            }
        }
    }

    private fun declare() {
        horizontalCalendarAdapter = HorizontalCalendarAdapter(context)

        binding.rvCheck.layoutManager = LinearLayoutManager(this@RepairOngoingActivity)
        binding.rvCheck.adapter = pagingListAdapter
//        repairOngoingViewModel = ViewModelProvider(this)[RepairOngoingViewModel::class.java]
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
                intent.putExtra(Constants.EXTRA_SPKID, data.spkId)
                intent.putExtra(Constants.EXTRA_ORDERID, data.orderId)
                intent.putExtra(Constants.EXTRA_PBID, data.pbId)
                intent.putExtra(Constants.EXTRA_VID, data.vehicleId)
                intent.putExtra(Constants.EXTRA_VBRAND, data.vehicleBrand)
                intent.putExtra(Constants.EXTRA_VTYPE, data.vehicleType)
                intent.putExtra(Constants.EXTRA_VVAR, data.vehicleVarian)
                intent.putExtra(Constants.EXTRA_VYEAR, data.vehicleYear)
                intent.putExtra(Constants.EXTRA_VLICEN, data.vehicleLicenseNumber)
                intent.putExtra(Constants.EXTRA_VDIS, data.vehicleDistrict)
                intent.putExtra(Constants.EXTRA_VLID, data.vehicleLambungId)
                if (position ==0 ){
                    intent.putExtra(Constants.EXTRA_STAGEID, "13")
                }else{
                    intent.putExtra(Constants.EXTRA_STAGEID, data.stageId.toInt())
                }
                intent.putExtra(Constants.EXTRA_STAGENAME, data.stageName)
                intent.putExtra(Constants.EXTRA_LOCOPTION, data.locationOption)
                intent.putExtra(Constants.EXTRA_SASSIGN, data.startAssignment)
                intent.putExtra(Constants.EXTRA_ISSTORING, data.isStoring)
                intent.putExtra(Constants.EXTRA_NOTESA, data.noteSA)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}