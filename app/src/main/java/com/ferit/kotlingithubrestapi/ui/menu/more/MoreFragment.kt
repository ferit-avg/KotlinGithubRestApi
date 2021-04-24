package com.ferit.kotlingithubrestapi.ui.menu.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ferit.kotlingithubrestapi.R
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import com.ferit.kotlingithubrestapi.databinding.FragmentMoreBinding
import com.ferit.kotlingithubrestapi.ui.base.BaseFragment
import com.ferit.kotlingithubrestapi.ui.menu.home.viewmodel.SearchViewModel
import com.ferit.kotlingithubrestapi.ui.menu.home.adapter.SearchAdapter

class MoreFragment: BaseFragment(R.layout.fragment_more), SearchAdapter.Interaction {
    private var _binding: FragmentMoreBinding? = null
    // This property is only valid between onCreateView and OnDestroyView
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        initRecyclerView()

        handleClickListeners()
    }

    private fun handleClickListeners() {
        //TODO("Not yet implemented")
    }

    private fun initRecyclerView() {


    }

    private fun setupUI() {
        //TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSelectedRepo(position: Int, repo: Repo) {
        //TODO("Not yet implemented")
    }

    override fun toggleFavorite(position: Int, repo: Repo) {
        //TODO("Not yet implemented")
    }

}