package com.jhoh.play.app

import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jhoh.play.app.common.adapter.MainPagerAdapter
import com.jhoh.play.app.databinding.ActivityMainBinding
import com.jhoh.play.app.ui.favorite.FavoriteFragment
import com.jhoh.play.app.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {
    override val viewModel: MainViewModel by viewModels()

    private lateinit var backPressedCallback: OnBackPressedCallback
    private var backKeyInterval = false

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    override fun loadView() {
        super.loadView()

        val fragmentList = listOf(
            SearchFragment(),
            FavoriteFragment(),
        )
        binding.viewPager.adapter = MainPagerAdapter(this, fragmentList)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.title_search)
                1 -> getString(R.string.title_favorite)
                else -> return@TabLayoutMediator
            }
        }.attach()
    }

    override fun registerListener() {
        super.registerListener()

        if(::backPressedCallback.isInitialized.not()) {
            backPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    doubleTapFinishApp()
                }
            }
            onBackPressedDispatcher.addCallback(this, backPressedCallback)
        }

    }

    private fun doubleTapFinishApp() {
        if(!backKeyInterval) {
            backKeyInterval = true

            Snackbar.make(
                binding.root,
                getString(R.string.snackbar_finish_app),
                Snackbar.LENGTH_SHORT
            ).addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    // snackbar가 완전히 보여진 뒤에 호출되는 이벤트로 빠르게 두번 클릭하면 종료되지 않는
                    // 이슈가 발생하여 제거
//                    backKeyInterval = true
                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    backKeyInterval = false
                }
            }).show()
        } else {
            finishApp()
        }
    }
}