package com.picpay.desafio.android.domain.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.entities.User

class UserListAdapter : ListAdapter<User, UserListItemViewHolder>(UserListDiffCallback()) {

//    var users = emptyList<User>()
//        set(value) {
//            val result = DiffUtil.calculateDiff(
//                UserListDiffCallback(
//                    field,
//                    value
//                )
//            )
//            result.dispatchUpdatesTo(this)
//            field = value
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemUserBinding.inflate(inflater, parent, false)
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



//    override fun getItemCount(): Int = users.size
}