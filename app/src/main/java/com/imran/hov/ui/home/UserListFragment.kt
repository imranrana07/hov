package com.imran.hov.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imran.hov.R
import com.imran.hov.databinding.FragmentUserListBinding
import com.imran.hov.core.BaseFragment
import com.imran.hov.core.ClickListener
import com.imran.hov.data.model.UsersEntity
import com.imran.hov.data.model.UsersListResponseClass
import com.imran.hov.ui.home.adapter.UsersAdapter
import com.imran.hov.utils.BUNDLE_VAL
import com.imran.hov.utils.Resource
import com.imran.hov.utils.isInternetAvailable
import com.imran.hov.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(),ClickListener<UsersEntity> {

    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
//        usersViewModel.getUsers()
        observers()

        binding.swipeLayout.setOnRefreshListener {
//            usersViewModel.users
        }
    }

    private fun observers(){
        usersViewModel.users.observe(viewLifecycleOwner) {
            it.data?.let { it1 -> initUsersAdapter(it1) }
            binding.progressBar.isVisible = it is Resource.Loading && it.data.isNullOrEmpty()
//            textViewError.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
            it.error?.localizedMessage?.let {
                    it1 -> toast(it1) }
        }
    }

    private fun initUsersAdapter(list: MutableList<UsersEntity>){
        val adapter = UsersAdapter(list,this)
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = adapter
    }

    override fun clickedData(response: UsersEntity) {
        if (isInternetAvailable()) {
            val bundle = bundleOf(BUNDLE_VAL to response)
            findNavController().navigate(R.id.usersDetailsFragment, bundle)
        }else{
            toast("Internet connection required")
        }
    }

}