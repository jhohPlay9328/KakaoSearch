package com.jhoh.play.app.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.domain.common.utils.FavoriteUtil
import com.jhoh.play.app.BaseFragment
import com.jhoh.play.app.MainViewModel
import com.jhoh.play.app.R
import com.jhoh.play.app.databinding.FragmentDetailBinding
import com.jhoh.play.app.common.utils.SearchUtil
import com.theenm.android.popcaster.common.constants.BaseConst
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, DetailViewModel>(
    R.layout.fragment_detail
){
    override val viewModel: DetailViewModel by viewModels()
    override val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var backPressedCallback: OnBackPressedCallback

    companion object {
        const val TAG: String = "DetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val mediaModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(
                    BaseConst.Bundle.MEDIA_MODEL, MediaModel.Document::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                it.getSerializable(BaseConst.Bundle.MEDIA_MODEL)
            } as MediaModel.Document

            viewModel.setMediaModel(mediaModel)
        }
    }

    override fun loadView() {
        super.loadView()

        binding.toolbar.addMenuProvider(
            object: MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    favoriteIcon(
                        menu.findItem(R.id.appbar_favorite),
                        viewModel.mediaModel.value
                    )
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.appbar_favorite -> {
                            val model: MediaModel.Document = viewModel.mediaModel.value
                            model.isFavorite = model.isFavorite.not()
                            activityViewModel.changeFavoriteItem(model)
                            true
                        }
                        else -> false
                    }
                }
            }
        )
    }

    fun favoriteIcon(menuItem: MenuItem, mediaModel: MediaModel.Document) {
        if(menuItem.itemId == R.id.appbar_favorite) {
            menuItem.icon = ContextCompat.getDrawable(
                requireActivity(),
                if(mediaModel.isFavorite) {
                    R.drawable.icon_like_on
                }  else {
                    R.drawable.icon_like_off
                }
            )
        }
    }

    override fun registerFlow() {
        super.registerFlow()

        lifecycleScope.launch {
            activityViewModel.addFavoriteItem.collect { favoriteItem ->
                invalidateMenu(favoriteItem)
            }
        }

        lifecycleScope.launch {
            activityViewModel.removeFavoriteItem.collect { favoriteItem ->
                invalidateMenu(favoriteItem)
            }
        }
    }

    override fun registerListener() {
        super.registerListener()

        if(::backPressedCallback.isInitialized.not()) {
            backPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finishFragment()
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(
                requireActivity(),
                backPressedCallback
            )
        }

        binding.toolbar.setNavigationOnClickListener {
            finishFragment()
        }
    }

    private fun invalidateMenu(favoriteItem: MediaModel.Document) {
        if(FavoriteUtil.isSameModel(favoriteItem, viewModel.mediaModel.value)) {
            viewModel.setMediaModel(favoriteItem)
            binding.toolbar.invalidateMenu()
        }
    }

    private fun finishFragment() {
        SearchUtil.removeFragment(requireActivity(), TAG)
    }
}