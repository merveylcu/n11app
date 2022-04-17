package com.merveylcu.n11app.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.databinding.ItemUserBinding
import com.merveylcu.n11app.util.listener.OnItemClickListener

class UserListAdapter(
    private val onUserClick: OnItemClickListener,
    private val onUserFavorite: OnItemClickListener
) :
    PagingDataAdapter<User, UserListAdapter.UserViewHolder>(
        Comparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User, position: Int) {
            binding.user = item
            binding.container.setOnClickListener {
                onUserClick.onItemClick(item)
            }
            binding.ivFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                onUserFavorite.onItemClick(item)
                notifyItemChanged(position)
            }
            binding.executePendingBindings()
        }
    }

    object Comparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ) =
            oldItem.login == newItem.login

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }
}