package com.imran.hov.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imran.hov.R
import com.imran.hov.databinding.FragmentUserListBinding
import com.imran.hov.core.BaseFragment
import com.imran.hov.core.ClickListener
import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.ui.home.adapter.UsersAdapter
import com.imran.hov.utils.BUNDLE_VAL
import com.imran.hov.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(),ClickListener<UsersListResponseClass> {

    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        usersViewModel.getUsers()
        observers()

        binding.swipeLayout.setOnRefreshListener {
            usersViewModel.getUsers()
        }
    }

    private fun observers(){
        usersViewModel.progressBar.observe(viewLifecycleOwner,{
            binding.swipeLayout.isRefreshing = false
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
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
    }

    override fun clickedData(response: UsersListResponseClass) {
        val bundle = bundleOf(BUNDLE_VAL to response)
        findNavController().navigate(R.id.usersDetailsFragment,bundle)
    }

}