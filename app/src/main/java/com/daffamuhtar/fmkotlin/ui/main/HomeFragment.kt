package com.daffamuhtar.fmkotlin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffamuhtar.fmkotlin.appv4.ui.RepairV4Activity
import com.daffamuhtar.fmkotlin.databinding.FragmentHomeBinding
import com.daffamuhtar.fmkotlin.ui.repair_check.RepairCheckActivity
import com.daffamuhtar.fmkotlin.ui.repair_on.RepairOnActivity
import com.daffamuhtar.fmkotlin.ui.repair_tab.RepairTaskActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRepairCheck.setOnClickListener {
            val intent = Intent(requireContext(), RepairCheckActivity::class.java)
            startActivity(intent)
        }

        binding.btnRepairOngoing.setOnClickListener {
            val intent = Intent(requireContext(), RepairTaskActivity::class.java)
            startActivity(intent)
        }

        binding.btnRepairOn.setOnClickListener {
            val intent = Intent(requireContext(), RepairOnActivity::class.java)
            startActivity(intent)
        }

        binding.btnUhTire.setOnClickListener {
            val intent = Intent(requireContext(), RepairTaskActivity::class.java)
            startActivity(intent)
        }
    }

}