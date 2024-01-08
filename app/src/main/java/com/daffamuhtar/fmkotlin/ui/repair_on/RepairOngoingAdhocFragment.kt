package com.daffamuhtar.fmkotlin.ui.repair_on

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.databinding.FragmentRepairOnAdhocBinding
import com.daffamuhtar.fmkotlin.data.Filter
import com.daffamuhtar.fmkotlin.data.Repair
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.LoadMoreAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairNewAdapter
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairOngoingAdhocFragment : Fragment() {

//    private lateinit var repairOngoingAdhocViewCopyModel: RepairOngoingAdhocViewModel
    private val repairOngoingAdhocViewCopyModel: RepairOngoingAdhocViewCopyModel by viewModel()

    private lateinit var binding: FragmentRepairOnAdhocBinding
    private lateinit var context: Context

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()

    private val repairNewAdapter: RepairNewAdapter = RepairNewAdapter()
//    private val repairAdapter: RepairAdapter = RepairAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepairOnAdhocBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        declare()
//        initViewModel()
        prepareFilter()
//        callData()
        binding.apply {

            lifecycleScope.launch {
                repairOngoingAdhocViewCopyModel.repairPaging.collect {
                    repairNewAdapter.submitData(it)
                }
            }

//            repairAdapter.setOnItemClickListener {
//                val direction =
//                    MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
//                findNavController().navigate(direction)
//            }

            lifecycleScope.launch {
                repairNewAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    srCheck.isRefreshing = state is LoadState.Loading
                }
            }


            rvCheck.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = repairNewAdapter
            }

            rvCheck.adapter = repairNewAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    repairNewAdapter.retry()
                }
            )

//            lifecycleScope.launch {
//                repairOngoingAdhocViewCopyModel.repairPaging.collect {
//                    repairAdapter.setAllLaporan(it)
//                }
//            }
        }
    }

//    private fun callData() {
//        this.repairOngoingAdhocViewCopyModel.getRepairAdhoc(context, ConstantsApp.BASE_URL_V2_0, "MEC-MBA-99")
//
//    }
//
//    private fun initViewModel() {
//        this.repairOngoingAdhocViewCopyModel.repairList.observe(requireActivity()) {
//            if (it.isNotEmpty()) {
//                setReportResults(it)
//            } else {
//                binding.lyNotFound.visibility = View.VISIBLE
//            }
//        }
//
//        this.repairOngoingAdhocViewCopyModel.isLoadingGetRepair.observe(requireActivity()) {
//            loading(it)
//        }
//
//        binding.srCheck.setOnRefreshListener {
//            this.repairOngoingAdhocViewCopyModel.getRepairAdhoc(context, ConstantsApp.BASE_URL_V2_0, "MEC-MBA-99")
//
//        }
//    }
//
    private fun declare() {
        context = requireActivity()
//        this.repairOngoingAdhocViewCopyModel = ViewModelProvider(this)[RepairOngoingAdhocViewModel::class.java]
    }

    private fun loading(value: Boolean) {
        binding.srCheck.isRefreshing = value
    }

//    private fun setReportResults(repairs: List<RepairOnAdhocResponse>) {
//        repairList.clear()
//        val listReport = ArrayList<Repair>()
//
//        for (repair in repairs) {
//            repair.apply {
//                val getResult = Repair(
//                    orderId,
//                    spkId,
//                    pbId = null,
//                    stageId,
//                    stageName = null,
//                    vehicleId,
//                    vehicleBrand,
//                    vehicleType,
//                    vehicleVarian,
//                    vehicleYear,
//                    vehicleLicenseNumber,
//                    vehicleLambungId,
//                    vehicleDistrict,
//                    noteFromSA,
//                    workshopName = null,
//                    workshopLocation = null,
//                    startAssignment,
//                    additionalPartNote,
//                    startRepairOdometer,
//                    locationOption,
//                    isStoring,
//                    orderType = null,
//                    colorCode = null
//
//                )
//                listReport.add(getResult)
//            }
//        }
//        repairList.addAll(listReport)
//
//        if (repairList.isEmpty()) {
//            binding.lyNotFound.visibility = View.VISIBLE
//        } else {
//            binding.lyNotFound.visibility = View.GONE
//        }
//
//        setRecycler()
//    }

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
                18,
                "Menunggu"
            )
        )
        filterList.add(
            Filter(
                false,
                19,
                "Diperbaiki"
            )
        )
        filterList.add(
            Filter(
                false,
                20,
                "Approval"
            )
        )

        filterAdapter.setAllFilter(filterList)

        binding.rvFilter.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvFilter.adapter = filterAdapter
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

//    private fun setRecycler() {
//        repairAdapter.setAllLaporan(repairList)
//
//        binding.rvCheck.layoutManager = LinearLayoutManager(context)
//        binding.rvCheck.adapter = repairAdapter
//
//        repairAdapter.setOnItemClickCallback(object : RepairAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Repair, position: Int) {
//                Toast.makeText(context, data.orderId, Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, RepairDetailActivity::class.java)
//                intent.putExtra(Constants.EXTRA_SPKID, data.spkId)
//                intent.putExtra(Constants.EXTRA_ORDERID, data.orderId)
//                intent.putExtra(Constants.EXTRA_SPKID, data.spkId)
//                intent.putExtra(Constants.EXTRA_VID, data.vehicleId)
//                intent.putExtra(Constants.EXTRA_VBRAND, data.vehicleBrand)
//                intent.putExtra(Constants.EXTRA_VTYPE, data.vehicleType)
//                intent.putExtra(Constants.EXTRA_VVAR, data.vehicleVarian)
//                intent.putExtra(Constants.EXTRA_VYEAR, data.vehicleYear)
//                intent.putExtra(Constants.EXTRA_VLICEN, data.vehicleLicenseNumber)
//                intent.putExtra(Constants.EXTRA_VDIS, data.vehicleDistrict)
//                intent.putExtra(Constants.EXTRA_VLID, data.vehicleLambungId)
//                intent.putExtra(Constants.EXTRA_STAGEID, data.stageId.toInt())
//                intent.putExtra(Constants.EXTRA_STAGENAME, data.stageName)
//                intent.putExtra(Constants.EXTRA_LOCOPTION, data.locationOption)
//                intent.putExtra(Constants.EXTRA_SASSIGN, data.startAssignment)
//                intent.putExtra(Constants.EXTRA_ODO, data.startRepairOdometer)
//                intent.putExtra(Constants.EXTRA_ISSTORING, data.isStoring)
//                intent.putExtra(Constants.EXTRA_NOTESA, data.noteSA)
//                startActivity(intent)
//            }
//        })
//    }
}