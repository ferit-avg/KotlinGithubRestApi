package com.ferit.kotlingithubrestapi.ui.menu.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ferit.kotlingithubrestapi.R
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepo
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import com.ferit.kotlingithubrestapi.databinding.FragmentRepoDetailsBinding
import com.ferit.kotlingithubrestapi.ui.base.BaseFragment
import com.ferit.kotlingithubrestapi.ui.menu.favorites.viewmodel.FavoriteReposViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepoDetailsFragment : BaseFragment(R.layout.fragment_repo_details) {
    private var _binding: FragmentRepoDetailsBinding? = null

    // This property is only valid between onCreateView and OnDestroyView
    private val binding get() = _binding!!

    private val favoriteReposViewModel: FavoriteReposViewModel by viewModels()
    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        handleClickListeners()
    }

    private fun handleClickListeners() {
        //TODO("Not yet implemented")
    }

    private fun setupUI() {

        val repo = args.repo
        binding.apply {
            Glide.with(requireContext())
                .load(repo.owner.avatarUrl)
                .centerCrop()
                .into(ivAvatar)

            tvOwnerName.text = repo.owner.ownerName
            tvRepoName.text = repo.fullName
            tvRepoStarCount.text = getString(R.string.TextStarCount, repo.stargazersCount.toString())
            tvRepoOpenIssueCount.text = getString(R.string.TextStarCount, repo.stargazersCount.toString())
            tvRepoDescription.text = repo.description

            checkFavorite(repo)
        }
    }

    private fun checkFavorite(repo: Repo) {
        binding.apply {
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val count = favoriteReposViewModel.checkRepo(repo.repoId)
                    if (count > 0) {
                        tgFavorite.isChecked = true
                        isChecked = true
                    } else {
                        tgFavorite.isChecked = false
                        isChecked = false
                    }
                }

                tgFavorite.setOnClickListener {
                    isChecked = !isChecked
                    if (isChecked) {
                        favoriteReposViewModel.addFavorite(
                            FavoriteRepo(
                                repoId = repo.repoId,
                                ownerId = repo.owner.ownerId,
                                ownerName = repo.owner.ownerName,
                                ownerAvatarUrl = repo.owner.avatarUrl,
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
                    tgFavorite.isChecked = isChecked
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}