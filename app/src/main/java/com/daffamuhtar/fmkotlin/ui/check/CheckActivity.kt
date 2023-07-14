package com.daffamuhtar.fmkotlin.ui.check

import android.content.Context
import android.content.Intent
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
import com.daffamuhtar.fmkotlin.model.Repair
import com.daffamuhtar.fmkotlin.ui.adapter.CheckAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.splash.SplashViewModel

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

    private val etSearch: EditText? = null
    private val progressBar: ProgressBar? = null
    private val lynull: LinearLayout? = null
    private val mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private val listReports = ArrayList<Repair>()

    private val repairAdapter: RepairAdapter = RepairAdapter()

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


    }

    private fun loading(value: Boolean) {
        binding.srCCheck.isRefreshing = value
    }

    private fun setReportResults(repairs: List<Repair>) {
        listReports.clear()
        val listReport = java.util.ArrayList<Repair>()

        for (repair in repairs) {
            repair.apply {
                val getResult = Repair(
                    orderId,
                    spkId,
                    stageId,
                    stageName,
                    vehicleId,
                    vehicleBrand,
                    vehicleType,
                    vehicleVarian,
                    vehicleYear,
                    vehicleLicenseNumber,
                    vehicleLambungId,
                    vehicleDistrict,
                    noteSA,
                    workshopName,
                    workshopLocation,
                    startAssignment,
                    locoption,
                    isStoring

                )
                listReport.add(getResult)
            }
        }
        listReports.addAll(listReport)

        if (listReports.isEmpty()) {
            binding.lyNotFound.visibility = View.VISIBLE
        } else {
            binding.lyNotFound.visibility = View.GONE
        }

        setRecycler()
    }

    private fun setRecycler() {
        repairAdapter.setAllLaporan(listReports)

        binding.rvCheck.layoutManager = LinearLayoutManager(this@CheckActivity)
        binding.rvCheck.adapter = repairAdapter

        repairAdapter.setOnItemClickCallback(object : RepairAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Repair) {
                Toast.makeText(context,data.orderId , Toast.LENGTH_SHORT).show()
            }
        })
    }
}