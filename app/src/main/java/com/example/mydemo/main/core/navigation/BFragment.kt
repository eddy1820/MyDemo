package com.example.mydemo.main.core.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mydemo.databinding.FragmentBBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BFragment : Fragment() {

    val binding by lazy { FragmentBBinding.inflate(layoutInflater) }

    private val args: BFragmentArgs by navArgs()

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BFragment().apply {
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
//        binding.btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_BFragment_to_AFragment))
        binding.btn.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.popBackStack()
        }
//        val name = arguments?.getString("NAME")
        val name = args.titleName
        binding.titleLabel.text = name
    }

}