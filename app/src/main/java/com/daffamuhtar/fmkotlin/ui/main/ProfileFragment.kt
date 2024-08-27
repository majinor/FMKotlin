package com.daffamuhtar.fmkotlin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.appv4.ui.RepairV4Activity
import com.daffamuhtar.fmkotlin.databinding.FragmentHomeBinding
import com.daffamuhtar.fmkotlin.databinding.FragmentProfileBinding
import com.daffamuhtar.fmkotlin.ui.repair_check.RepairCheckActivity
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailViewModel
import com.daffamuhtar.fmkotlin.ui.repair_on.RepairOnActivity
import com.daffamuhtar.fmkotlin.ui.repair_tab.RepairTaskActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        declare()
    }

    private fun declare() {
        profileFragmentViewModel = ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
        profileFragmentViewModel.getProfile(requireActivity(),Server.URL1,"MEC-MBA-99")
    }
}