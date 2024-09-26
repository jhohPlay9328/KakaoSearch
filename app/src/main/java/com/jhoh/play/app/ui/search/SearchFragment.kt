package com.jhoh.play.app.ui.search

import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jhoh.play.app.BaseFragment
import com.jhoh.play.app.MainViewModel
import com.jhoh.play.app.R
import com.jhoh.play.app.common.adapter.RecyclerAdapter
import com.jhoh.play.app.common.extension.repeatLaunchWhenCreated
import com.jhoh.play.app.common.listener.InteractionListener
import com.jhoh.play.app.common.utils.SearchUtil
import com.jhoh.play.app.databinding.FragmentSearchBinding
import com.jhoh.play.app.ui.detail.DetailFragment
import com.jhoh.play.domain.model.MediaModel
import com.theenm.android.popcaster.common.constants.BaseConst
import com.theenm.popkontv.presentation.common.decoration.LinearDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.layout.fragment_search
), SwipeRefreshLayout.OnRefreshListener,
    OnClickListener,
    TextView.OnEditorActionListener {
    override val viewModel: SearchViewModel by viewModels()
    override val activityViewModel: MainViewModel by activityViewModels()

    companion object {
        const val MEDIA_SEARCH = 100
        const val MEDIA_CLEAR = 101
        const val FAVORITE_ADD = 200
        const val FAVORITE_REMOVE = 201
    }

    private var query: String = ""

    override fun loadView() {
        super.loadView()

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(LinearDecoration(topPadding = 24.dpToPxF))

            adapter = RecyclerAdapter(
                interactionListener = object: InteractionListener() {
                    override fun <T> onClick(view: View, item: T) {
                        super.onClick(view, item)

                        when(view.id) {
                            R.id.cl_root -> SearchUtil.addFragment(
                                requireActivity(),
                                DetailFragment(),
                                bundleOf(BaseConst.Bundle.MEDIA_MODEL to item),
                                DetailFragment.TAG
                            )
                            R.id.iv_favorite ->
                                activityViewModel.changeFavoriteItem(item as MediaModel.Document)
                        }
                    }
                }
            )
        }
    }

    override fun registerFlow() {
        super.registerFlow()

        lifecycleScope.repeatLaunchWhenCreated(this) {
            launch {
                activityViewModel.addFavoriteItem.collect { favoriteItem ->
                    notifyAdapter(FAVORITE_ADD, favoriteItem)
                }
            }
            launch {
                activityViewModel.removeFavoriteItem.collect { favoriteItem ->
                    notifyAdapter(FAVORITE_REMOVE, favoriteItem)
                }
            }
            launch {
                viewModel.searchMediaData.collectLatest { mediaModel ->
                    notifyAdapter(MEDIA_SEARCH, mediaModel)
                }
            }
            launch {
                viewModel.clearMediaData.collect { isClear ->
                    notifyAdapter(MEDIA_CLEAR, isClear)
                }
            }
        }
    }

    override fun registerListener() {
        super.registerListener()
        binding.recyclerView.addOnScrollListener(
            object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!
                            .findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (lastVisibleItemPosition > 0 && itemTotalCount > 0
                        && lastVisibleItemPosition == itemTotalCount) {
                        requestMediaData()
                    }
                }
            }
        )

        binding.swipeRefresh.setOnRefreshListener(this)
        binding.ivSearch.setOnClickListener(this)
        binding.ivClear.setOnClickListener(this)
        binding.etSearch.setOnEditorActionListener(this)
    }

    override fun onRefresh() {
        SearchUtil.hideIme(requireActivity())
        binding.etSearch.clearFocus()
        binding.swipeRefresh.isRefreshing = false
        (binding.recyclerView.adapter as RecyclerAdapter).clear()

        query = binding.etSearch.text.toString()
        requestMediaData(true)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.ivSearch -> onRefresh()
            binding.ivClear -> clearMediaData()
        }
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ) {
            onRefresh()
            return true
        }
        return false
    }

    private fun requestMediaData(isRefresh: Boolean = false) {
        viewModel.requestMediaData(
            query,
            isRefresh
        )
    }

    private fun clearMediaData() {
        viewModel.clearMediaData()
    }

    private fun notifyAdapter(notifyType: Int, data: Any) {
        (binding.recyclerView.adapter as RecyclerAdapter).apply {
            when(notifyType) {
                MEDIA_SEARCH -> {
                    data as MediaModel
                    if(data.documents.isNotEmpty()) {
                        itemInsertRange(
                            SearchUtil.createBaseRow(
                                mutableMapOf(
                                    // 검색 목록을 보여주는 holder 추가
                                    BaseConst.ViewType.MEDIA to data.documents,
                                    // 페이지 번호를 보여주는 holder 추가
                                    BaseConst.ViewType.PAGE_NUM to when (data.meta.endPage) {
                                        true -> getString(R.string.last_page)
                                        false -> "${data.meta.pageNum}"
                                    }
                                )
                            )
                        )
                    }
                }
                MEDIA_CLEAR -> {
                    binding.etSearch.text?.clear()
                    clear()
                }
                FAVORITE_ADD -> {
                    data as MediaModel.Document
                    val index = SearchUtil.getFavoritePosition(getRows(), data)
                    if(index >= 0) {
                        (getItem(index) as MediaModel.Document).isFavorite = true
                        itemChange(index)
                    }
                }
                FAVORITE_REMOVE -> {
                    data as MediaModel.Document
                    val index = SearchUtil.getFavoritePosition(getRows(), data)
                    if(index >= 0) {
                        (getItem(index) as MediaModel.Document).isFavorite = false
                        itemChange(index)
                    }
                }
            }
        }
    }
}