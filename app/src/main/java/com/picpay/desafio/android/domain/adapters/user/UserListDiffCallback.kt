package com.picpay.desafio.android.domain.adapters.user

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.entities.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
}