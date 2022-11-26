package com.example.roomex.screens.main.play

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.roomex.R
import com.example.roomex.databinding.FragmentPlayBinding

class PlayFragment : Fragment(R.layout.fragment_play) {

    private lateinit var binding: FragmentPlayBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)


    }

}