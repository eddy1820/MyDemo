package com.example.mydemo.main.core.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.mydemo.R
import com.example.mydemo.databinding.FragmentBBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val binding by lazy { FragmentBBinding.inflate(layoutInflater) }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_BFragment_to_AFragment))
        binding.btn.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.popBackStack()
        }
        val name = arguments?.getString("NAME")
        binding.titleLabel.text = name
    }

}