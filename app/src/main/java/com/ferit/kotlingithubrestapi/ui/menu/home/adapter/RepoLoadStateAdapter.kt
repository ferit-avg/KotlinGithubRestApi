package com.ferit.kotlingithubrestapi.ui.menu.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ferit.kotlingithubrestapi.databinding.ItemRepoLoadStateBinding

class RepoLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<RepoLoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(private val binding: ItemRepoLoadStateBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState is LoadState.Error
            tvViewError.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                tvViewError.text = loadState.error.localizedMessage
            }

            if (loadState is LoadState.Error && loadState.error is Exception) {
                buttonRetry.isVisible = false
                tvViewError.isVisible = false
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemRepoLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(
            binding
        )
    }
}