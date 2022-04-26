package com.example.mydemo.main.core.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mydemo.R
import com.example.mydemo.databinding.FragmentABinding

class AFragment : Fragment() {

    val binding by lazy { FragmentABinding.inflate(layoutInflater) }

    companion object {
        @JvmStatic
        fun newInstance() =
            AFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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

        binding.btn.setOnClickListener {


            if (binding.edit.text.trim().isEmpty()) {
                Toast.makeText(requireContext(), "please enter text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val navControl = Navigation.findNavController(it)
            val bundle = Bundle().apply {
                putString("NAME", binding.edit.text.toString())
            }
            navControl.navigate(R.id.action_AFragment_to_BFragment, bundle)
        }
        //binding.btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_BFragment_to_AFragment))湉
    }


}