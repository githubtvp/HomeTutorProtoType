package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.login_page1.databinding.FragmentHomefragementBinding

class Homefragement : Fragment() {

    private var _binding: FragmentHomefragementBinding? = null
    private val binding get() = _binding!!

    private val nextPageCreateProfile: Class<*> = CreateProfile::class.java
    private val nextPageCategory: Class<*> = Category::class.java

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomefragementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.searchActivity.setOnClickListener {
            startNextPage(nextPageCategory)
        }

        binding.search.setOnClickListener {
            startNextPage(nextPageCategory)
        }

        binding.profile.setOnClickListener {
            startNextPage(nextPageCreateProfile)
        }

        binding.editProfile.setOnClickListener {
            startNextPage(nextPageCreateProfile)
        }
    }

    private fun startNextPage(nextPage: Class<*>) {
        val intent = Intent(requireActivity(), nextPage)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}