package com.imran.hov.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.imran.hov.R
import com.imran.hov.core.BaseFragment
import com.imran.hov.core.ClickListener
import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.databinding.FragmentUsersDetailsBinding
import com.imran.hov.ui.home.UsersViewModel
import com.imran.hov.ui.home.adapter.UsersAdapter
import com.imran.hov.utils.BUNDLE_VAL
import com.imran.hov.utils.toast

class UsersDetailsFragment : BaseFragment<FragmentUsersDetailsBinding>(),ClickListener<UsersListResponseClass> {
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersDetailsBinding = FragmentUsersDetailsBinding.inflate(layoutInflater,container,false)

    override fun init(savedInstanceState: Bundle?) {
        usersViewModel.getUsers()
        observers()
        val userDetails = requireArguments().getSerializable(BUNDLE_VAL) as UsersListResponseClass
        setUserDetailsValues(userDetails)
    }

    private fun observers(){
        usersViewModel.progressBar.observe(viewLifecycleOwner,{
            binding.progressBar.visibility = it
        })
        usersViewModel.error.observe(viewLifecycleOwner,{
            toast(it)
        })
        usersViewModel.users.observe(viewLifecycleOwner,{
            initUsersAdapter(it)
        })

    }

    private fun initUsersAdapter(list: MutableList<UsersListResponseClass>){
        val adapter = UsersAdapter(list,this)
        val layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.HORIZONTAL,false)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
    }

    override fun clickedData(response: UsersListResponseClass) {
        setUserDetailsValues(response)
    }

    private fun setUserDetailsValues(response : UsersListResponseClass){
        Glide.with(requireContext()).load(response.avatarUrl).placeholder(R.drawable.ic_error).into(binding.includeUserDetails.ivUser)
        binding.includeUserDetails.tvUsernameVal.text = response.login
        binding.includeUserDetails.tvUserTypeVal.text = response.type
        binding.includeUserDetails.tvUrlVal.text = response.url
        binding.includeUserDetails.tvRepoUrlVal.text = response.reposUrl
        binding.includeUserDetails.tvEventsUrlVal.text = response.eventsUrl
    }
}