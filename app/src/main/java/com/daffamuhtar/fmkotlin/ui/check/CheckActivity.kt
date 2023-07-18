package com.daffamuhtar.fmkotlin.ui.check

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.RequestQueue
import com.daffamuhtar.fmkotlin.constants.ConstantaApp
import com.daffamuhtar.fmkotlin.databinding.ActivityCheckBinding
import com.daffamuhtar.fmkotlin.model.Filter
import com.daffamuhtar.fmkotlin.model.Repair
import com.daffamuhtar.fmkotlin.model.response.CheckRepairResponse
import com.daffamuhtar.fmkotlin.ui.adapter.CheckAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter

class CheckActivity : AppCompatActivity() {

    private lateinit var checkViewModel: CheckViewModel

    private lateinit var binding: ActivityCheckBinding

    private val context: Context = this@CheckActivity
    private val mRecyclerView: RecyclerView? = null
    private val mCheckAdapter: CheckAdapter? = null
    private val mCheckList: ArrayList<Repair>? = null
    private val mRequestQueue: RequestQueue? = null

    private val userid: String? = null
    private var companyType: String? = null
    private var token: String? = null
    private val isGetCheckWo = false

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()

    private val repairAdapter: RepairAdapter = RepairAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        checkViewModel = ViewModelProvider(this)[CheckViewModel::class.java]

        checkViewModel.getAllCheckRepair(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")
        checkViewModel.repairList.observe(this) {
            if (it.isNotEmpty()) {
                setReportResults(it)
            } else {
                binding.lyNotFound.visibility = View.VISIBLE
            }
        }

        checkViewModel.isLoadingGetAllCheckRepair.observe(this) {
            loading(it)
        }

        binding.srCheck.setOnRefreshListener {
            checkViewModel.getAllCheckRepair(this, ConstantaApp.BASE_URL_V2_0, "MEC-MBA-99")

        }

        prepareFilter()
    }

    private fun loading(value: Boolean) {
        binding.srCheck.isRefreshing = value
    }

    private fun setReportResults(repairs: List<CheckRepairResponse>) {
        repairList.clear()
        val listReport = ArrayList<Repair>()

        for (repair in repairs) {
            repair.apply {
                val getResult = Repair(
                    orderId,
                    null,
                    stageId,
                    null,
                    vehicleId,
                    vehicleBrand,
                    vehicleType,
                    vehicleVarian,
                    vehicleYear,
                    vehicleLicenseNumber,
                    vehicleLambungId,
                    vehicleDistrict,
                    noteFromSA,
                    null,
                    null,
                    startAssignment,
                    locationOption,
                    isStoring

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

        binding.rvCoFilter.layoutManager = LinearLayoutManager(this@CheckActivity,LinearLayoutManager.HORIZONTAL,false)
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

        binding.rvCheck.layoutManager = LinearLayoutManager(this@CheckActivity)
        binding.rvCheck.adapter = repairAdapter

        repairAdapter.setOnItemClickCallback(object : RepairAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Repair) {
                Toast.makeText(context, data.orderId, Toast.LENGTH_SHORT).show()
            }
        })
    }
}