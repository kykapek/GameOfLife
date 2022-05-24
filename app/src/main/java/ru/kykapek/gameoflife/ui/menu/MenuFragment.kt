package ru.kykapek.gameoflife.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.kykapek.gameoflife.R
import ru.kykapek.gameoflife.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var mBinding: FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = mBinding.root
        mBinding.ivPlay.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToGameFragment())
        }
        // Inflate the layout for this fragment
        return view
    }
}