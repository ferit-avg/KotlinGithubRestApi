package com.ferit.kotlingithubrestapi.ui.menu.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferit.kotlingithubrestapi.R
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepo
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import com.ferit.kotlingithubrestapi.databinding.FragmentHomeBinding
import com.ferit.kotlingithubrestapi.ext.gone
import com.ferit.kotlingithubrestapi.ext.navigate
import com.ferit.kotlingithubrestapi.ext.visible
import com.ferit.kotlingithubrestapi.ui.base.BaseFragment
import com.ferit.kotlingithubrestapi.ui.menu.favorites.viewmodel.FavoriteReposViewModel
import com.ferit.kotlingithubrestapi.ui.menu.home.adapter.RepoLoadStateAdapter
import com.ferit.kotlingithubrestapi.ui.menu.home.adapter.SearchAdapter
import com.ferit.kotlingithubrestapi.ui.menu.home.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment(R.layout.fragment_home), SearchAdapter.Interaction {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and OnDestroyView
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter

    private val searchViewModel: SearchViewModel by viewModels()
    private val favoriteReposViewModel: FavoriteReposViewModel by viewModels()

    private var favoriteIdList = mutableListOf<Int>()
    private var lastQuery: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        handleClickListeners()

        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        getFavorites()
    }

    private fun getFavorites() {
        favoriteReposViewModel.getFavoriteRepos.observe(viewLifecycleOwner, {
            favoriteIdList = mutableListOf()
            it.map { x -> favoriteIdList.add(x.repoId) }

            initRecyclerView()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lastQuery = query
                if (query != null) {
                    binding.rvRepo.scrollToPosition(0)
                    searchViewModel.searchRepos(username = query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun handleClickListeners() {
        //TODO("Not yet implemented")
    }

    private fun initRecyclerView() {

        searchAdapter = SearchAdapter(this, favoriteIdList)

        val headerAdapter = RepoLoadStateAdapter { searchAdapter.retry() }
        val footerAdapter = RepoLoadStateAdapter { searchAdapter.retry() }

        val concateAdapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = headerAdapter,
            footer = footerAdapter
        )

        binding.buttonRetry.setOnClickListener { searchAdapter.retry() }

        binding.rvRepo.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concateAdapter
        }

        searchViewModel.repos.observe(viewLifecycleOwner) {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        /*
        searchAdapter.addLoadStateListener { loadState ->

            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    //hideLoading()
                }
                is LoadState.Loading -> {
                    //showLoading()
                }
                is LoadState.Error -> {
                    //hideLoading()
                }
            }

            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading &&
                    searchAdapter.itemCount == 0 && lastQuery?.trim() != ""

            if (isListEmpty) {
                binding.tvEmpty.visible()
                binding.rvRepo.gone()
            } else {
                binding.tvEmpty.gone()
                binding.rvRepo.visible()
            }
        }
        */
    }

    private fun setupUI() {
        //TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvRepo.adapter = null
        _binding = null
    }

    override fun onSelectedRepo(position: Int, repo: Repo) {
        navigate(HomeFragmentDirections.actionHomeFragmentToRepoDetailsFragment(repo = repo))
    }

    override fun toggleFavorite(position: Int, repo: Repo) {
        binding.apply {
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
            }
        }
    }
}