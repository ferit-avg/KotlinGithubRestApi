package com.ferit.kotlingithubrestapi.ui.menu.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepo
import com.ferit.kotlingithubrestapi.databinding.ItemRepoBinding

class FavoriteAdapter(private val interaction: Interaction? = null):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteRepo>() {
            override fun areItemsTheSame(oldItem: FavoriteRepo, newItem: FavoriteRepo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FavoriteRepo, newItem: FavoriteRepo): Boolean =
                oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return FavoriteViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        when(holder) {
            is FavoriteViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
            else -> {}
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<FavoriteRepo>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemRepoBinding, val interaction: Interaction?):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteRepo: FavoriteRepo) = with(binding) {
            tvRepoName.text = favoriteRepo.fullName

            tgFavorite.isChecked = true
            tgFavorite.setOnClickListener {
                interaction?.onToggleFavorite(favoriteRepo)
            }

            root.setOnClickListener {
                interaction?.onSelectedFavorite(favoriteRepo)
            }
        }
    }

    interface Interaction {
        fun onSelectedFavorite(favoriteRepo: FavoriteRepo)
        fun onToggleFavorite(favoriteRepo: FavoriteRepo)
    }
}