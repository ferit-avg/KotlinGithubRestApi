package com.ferit.kotlingithubrestapi.ui.menu.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import com.ferit.kotlingithubrestapi.databinding.ItemRepoBinding

class SearchAdapter(private val interaction: Interaction? = null, val favoriteIdList: MutableList<Int>):
    PagingDataAdapter<Repo, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK){

    //private var favoriteIdList = mutableListOf<Int>()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.repoId == newItem.repoId

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(
            binding, interaction
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class SearchViewHolder(private val binding: ItemRepoBinding, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(repo: Repo) = with(binding) {
                tvRepoName.text = repo.fullName

                if (repo.repoId in favoriteIdList) {
                    tgFavorite.isChecked = true
                } else {
                    tgFavorite.isChecked = false
                }

                tgFavorite.setOnClickListener {
                    interaction?.toggleFavorite(position = bindingAdapterPosition, repo = repo)
                }

                root.setOnClickListener {
                    interaction?.onSelectedRepo(position = bindingAdapterPosition, repo = repo)
                }
            }

    }

    interface Interaction {
        fun onSelectedRepo(position: Int, repo: Repo)

        fun toggleFavorite(position: Int, repo: Repo)
    }
}