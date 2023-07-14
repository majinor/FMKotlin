package com.daffamuhtar.fmkotlin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffamuhtar.fmkotlin.databinding.FragmentHomeBinding
import com.daffamuhtar.fmkotlin.ui.check.CheckActivity

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


        binding.btnUhReportproblem.setOnClickListener {
            val intent = Intent(requireContext(), CheckActivity::class.java)
            startActivity(intent)
        }
    }

}