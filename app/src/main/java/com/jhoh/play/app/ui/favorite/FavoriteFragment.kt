package com.jhoh.play.app.ui.favorite

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jhoh.play.app.BaseFragment
import com.jhoh.play.app.MainViewModel
import com.jhoh.play.app.R
import com.jhoh.play.app.common.adapter.RecyclerDiffAdapter
import com.jhoh.play.app.common.listener.InteractionListener
import com.jhoh.play.app.common.utils.SearchUtil
import com.jhoh.play.app.databinding.FragmentFavoriteBinding
import com.jhoh.play.app.ui.detail.DetailFragment
import com.jhoh.play.domain.model.MediaModel
import com.theenm.android.popcaster.common.constants.BaseConst
import com.theenm.popkontv.presentation.common.decoration.GridDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    R.layout.fragment_favorite
){
    override val viewModel: FavoriteViewModel by viewModels()
    override val activityViewModel: MainViewModel by activityViewModels()

    override fun loadView() {
        super.loadView()

        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(GridDecoration(
                verticalPadding = 15.dpToPxF,
                horizontalPadding = 40.dpToPxF
            ))

            adapter = RecyclerDiffAdapter(
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
        notifyAdapter()
    }

    override fun registerFlow() {
        super.registerFlow()

        lifecycleScope.launch {
            activityViewModel.addFavoriteItem.collect {
                notifyAdapter()
            }
        }

        lifecycleScope.launch {
            activityViewModel.removeFavoriteItem.collect {
                notifyAdapter()
            }
        }
    }

    private fun notifyAdapter() {
        (binding.recyclerView.adapter as RecyclerDiffAdapter).submitList(
            SearchUtil.createBaseRow(
                mutableMapOf(BaseConst.ViewType.FAVORITE to viewModel.readFavorite())
            )
        )
    }
}