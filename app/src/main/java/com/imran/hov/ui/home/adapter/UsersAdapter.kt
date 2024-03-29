package com.imran.hov.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imran.hov.R
import com.imran.hov.core.ClickListener
import com.imran.hov.data.model.UsersEntity
import com.imran.hov.databinding.ItemUsersBinding

class UsersAdapter(private val list: MutableList<UsersEntity>,
                   private val itemClickListener: ClickListener<UsersEntity>):
    RecyclerView.Adapter<UsersViewHolder>() {

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        context = parent.context
        val view = ItemUsersBinding.inflate(LayoutInflater.from(context),parent,false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = list[position]
        Glide.with(context).load(item.avatarUrl).placeholder(R.drawable.ic_error).into(holder.ivUser)
        holder.tvLoginUsername.text = item.login
        holder.tvUserUrl.text = item.htmlUrl

        holder.itemView.setOnClickListener {
            itemClickListener.clickedData(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}