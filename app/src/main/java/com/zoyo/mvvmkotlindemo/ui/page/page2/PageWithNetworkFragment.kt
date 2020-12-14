package com.zoyo.mvvmkotlindemo.ui.page.page2

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentPageBinding
import com.zoyo.mvvmkotlindemo.ui.page.ServiceLocator
import kotlinx.android.synthetic.main.fragment_page.recyclerView
import kotlinx.android.synthetic.main.fragment_page.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_page_network.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


class PageWithNetworkFragment : BaseFragment<FragmentPageBinding>(R.layout.fragment_page_network) {

    private val viewModel by viewModels<PageWithNetworkViewModel> {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val gitHubRepository = ServiceLocator.instance().getRepository()

                @Suppress("UNCHECKED_CAST")
                return PageWithNetworkViewModel(gitHubRepository, handle) as T
            }
        }
    }

    override fun getVM(): BaseViewModel {
        return viewModel
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initData() {

        val adapter = PageWithNetworkAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageWithNeteorkAdapterHeader(adapter),
            footer = PageWithNeteorkAdapterHeader(adapter)
        )
        swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }

        //需要分开写,不然后面代码不执行
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                //当前PagingDataAdapter是否在加载过程中
                swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                //开始刷新
                .distinctUntilChangedBy { it.refresh }
                //刷新结束
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
        lifecycleScope.launchWhenStarted {
            adapter.retry()

        }

        textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateList()
                true
            } else {
                false
            }
        }
    }

    private fun updateList() {
        textInputEditText.text.toString().let {
            if (it.isNotBlank() && viewModel.shouldShowList(it)) {
                viewModel.showList(it)
            }
        }
    }

}