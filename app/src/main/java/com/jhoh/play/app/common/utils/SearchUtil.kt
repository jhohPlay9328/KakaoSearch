package com.jhoh.play.app.common.utils

import android.app.Activity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.domain.common.utils.FavoriteUtil
import com.jhoh.play.app.BaseRow
import com.jhoh.play.app.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SearchUtil {
    fun <T: Any>createBaseRow(itemMap: MutableMap<Int, T>): List<BaseRow<Any>>{
        val rows: MutableList<BaseRow<Any>> = arrayListOf()
        for(key in itemMap.keys) {
            val value = itemMap[key]
            when(value is List<*>) {
                // 데이터 array 일때
                true -> for (item in value) {
                    rows.add(BaseRow(item, key))
                }
                // 단일 데이터 일때
                false -> rows.add(BaseRow(value, key))
            }
        }
        return rows
    }

    fun dateFormatConvert(
        date: Date,
    ): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA) // yyyy-MM-dd HH:mm:ss
        return dateFormat.format(date)
    }

    fun addFragment(
        parent: FragmentActivity,
        callFragment: Fragment,
        bundle: Bundle = Bundle(),
        tag: String,
        layoutId: Int = R.id.fl_fragment_container
    ){
        parent.supportFragmentManager.commit(allowStateLoss = true) {
            callFragment.arguments = bundle
            add(layoutId, callFragment, tag)
        }
    }

    fun removeFragment(
        parent: FragmentActivity,
        tag: String
    ) {
        val fragment = parent.supportFragmentManager.findFragmentByTag(tag)
        parent.supportFragmentManager.commit(allowStateLoss = true) {
            fragment?.let {
                remove(it)
            }
        }
    }

    fun getFavoritePosition(rows: MutableList<BaseRow<*>>, favoriteItem: MediaModel.Document) =
        rows.indexOfFirst { row ->
            when(row.getItem()) {
                is MediaModel.Document -> FavoriteUtil.isSameModel(
                    row.getItem() as MediaModel.Document,
                    favoriteItem
                )
                else -> false
            }
        }

    fun hideIme(activity: Activity) {
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
            hide(WindowInsetsCompat.Type.ime())
        }
    }
}