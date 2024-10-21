package com.example.locationsharingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.locationsharingapp.adapter.UserAdapter
import com.example.locationsharingapp.databinding.FragmentFriendsBinding
import com.example.locationsharingapp.viewmodel.FireStoreViewModel


class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var fireStoreViewModel: FireStoreViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root



    }
}

