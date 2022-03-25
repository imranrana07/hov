package com.imran.hov.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.imran.hov.databinding.ItemUsersBinding

class UsersViewHolder(itemView: ItemUsersBinding): RecyclerView.ViewHolder(itemView.root){
    val ivUser = itemView.ivUser
    val tvLoginUsername = itemView.tvLoginUsername
    val tvUserUrl = itemView.tvUserUrl
}
