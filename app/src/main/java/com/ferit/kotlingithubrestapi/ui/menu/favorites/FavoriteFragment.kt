package com.ferit.kotlingithubrestapi.ui.menu.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferit.kotlingithubrestapi.R
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepo
import com.ferit.kotlingithubrestapi.data.model.repo.Owner
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import com.ferit.kotlingithubrestapi.databinding.FragmentFavoriteBinding
import com.ferit.kotlingithubrestapi.ext.gone
import com.ferit.kotlingithubrestapi.ext.navigate
import com.ferit.kotlingithubrestapi.ext.visible
import com.ferit.kotlingithubrestapi.ui.base.BaseFragment
import com.ferit.kotlingithubrestapi.ui.menu.favorites.adapter.FavoriteAdapter
import com.ferit.kotlingithubrestapi.ui.menu.favorites.viewmodel.FavoriteReposViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment: BaseFragment(R.layout.fragment_favorite), FavoriteAdapter.Interaction {
    private var _binding: FragmentFavoriteBinding? = null
    // This property is only valid between onCreateView and OnDestroyView
    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: FavoriteAdapter

    private val favoriteReposViewModel: FavoriteReposViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        initRecyclerView()

        handleClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorite.adapter = null
        _binding = null
    }

    override fun onSelectedFavorite(favoriteRepo: FavoriteRepo) {
        val repo = Repo(
            repoId = favoriteRepo.repoId,
            repoName = favoriteRepo.repoName,
            fullName = favoriteRepo.fullName,
            owner = Owner(ownerId = favoriteRepo.ownerId, ownerName = favoriteRepo.ownerName, avatarUrl = favoriteRepo.ownerAvatarUrl),
            description = favoriteRepo.description,
            stargazersCount = favoriteRepo.stargazersCount,
            openIssuesCount = favoriteRepo.openIssuesCount
        )
        navigate(FavoriteFragmentDirections.actionFavoriteFragmentToRepoDetailsFragment(repo = repo))
    }

    private fun handleClickListeners() {
        //TODO("Not yet implemented")
    }

    private fun initRecyclerView() {
        favoriteAdapter = FavoriteAdapter(this)

        binding.rvFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }

        favoriteReposViewModel.getFavoriteRepos.observe(viewLifecycleOwner, {
            if (it.size > 0) {
                binding.apply {
                    rvFavorite.visible()
                    tvEmpty.gone()
                }
                favoriteAdapter.submitList(it)
            } else {
                emptyFavoriteList()
            }
        })
    }

    private fun emptyFavoriteList() {
        binding.apply {
            rvFavorite.gone()
            tvEmpty.visible()
        }
    }

    private fun setupUI() {
        // TODO()
    }

    override fun onToggleFavorite(repo: FavoriteRepo) {
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val count = favoriteReposViewModel.checkRepo(repo.repoId)
                isChecked = count > 0
            }

            isChecked = !isChecked
            if (isChecked) {
                favoriteReposViewModel.addFavorite(
                    FavoriteRepo(
                        repoId = repo.repoId,
                        ownerId = repo.ownerId,
                        ownerName = repo.ownerName,
                        ownerAvatarUrl = repo.ownerAvatarUrl,
                        repoName = repo.repoName,
                        fullName = repo.fullName,
                        description = repo.description ?: "",
                        stargazersCount = repo.stargazersCount,
                        openIssuesCount = repo.openIssuesCount
                    )
                )
            } else {
                favoriteReposViewModel.deleteFromFavorites(repo.repoId)
            }
        }
    }
}