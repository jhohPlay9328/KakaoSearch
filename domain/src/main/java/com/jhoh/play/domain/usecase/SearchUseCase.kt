package com.jhoh.play.domain.usecase

import com.jhoh.play.domain.common.utils.FavoriteUtil
import com.jhoh.play.domain.entity.ApiResult
import com.jhoh.play.domain.model.ImageModel
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.domain.model.VideoModel
import com.jhoh.play.domain.repository.PreferenceRepository
import com.jhoh.play.domain.repository.SearchRepository
import com.theenm.android.popcaster.common.constants.ApiConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val preferenceRepository: PreferenceRepository
){
    private val sortType: String = "recency"
    private val pageSize: Int = 10

    private var imageMediaModel: MediaModel = MediaModel()
    private var videoMediaModel: MediaModel = MediaModel()

    companion object {
        const val IMAGE_MAX_PAGE_NUM: Int = 50 // https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-image 문서 내용에서 최대 페이지 수 확인
        const val VIDEO_MAX_PAGE_NUM: Int = 15 // https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video 문서 내용에서 최대 페이지 수 확인
    }

    fun requestMediaData(
        callbackId: Int,
        query: String,
        isRefresh: Boolean
    ): Flow<ApiResult> = flow {
        if(checkImageEndPage() && checkVideoEndPage()) return@flow

        if(isRefresh) refreshValue()

        val imagePageNum = imageMediaModel.meta.pageNum
        val videoPageNum = videoMediaModel.meta.pageNum

        if(imageMediaModel.meta.endPage.not()) {
            searchRepository.requestImageData(
                callbackId,
                query,
                sortType,
                imagePageNum,
                pageSize
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.ResultSuccess<*> -> {
                        if (apiResult.data is ImageModel) parsingImageData(apiResult.data)
                    }

                    is ApiResult.ResultFail -> emit(apiResult)
                }
            }
        }

        if(videoMediaModel.meta.endPage.not()) {
            searchRepository.requestVideoData(
                callbackId,
                query,
                sortType,
                videoPageNum,
                pageSize
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.ResultSuccess<*> -> {
                        if (apiResult.data is VideoModel) parsingVideoData(apiResult.data)
                    }

                    is ApiResult.ResultFail -> emit(apiResult)
                }
            }
        }

        val combineMediaModel = MediaModel(
            MediaModel.Meta(
                // 이미지, 동영상 페이지 번호 중에 큰 번호를 저장
                pageNum = imagePageNum.takeIf {
                    page -> page >= videoPageNum
                }?: videoPageNum,
                endPage = checkImageEndPage() && checkVideoEndPage()
            ),
            imageMediaModel.documents.plus(videoMediaModel.documents).toMutableList()
        )

        // 최신순 정렬
        sortLatestMediaModels(combineMediaModel)
        // 즐겨찾기 여부 체크
        checkFavorite(combineMediaModel)

//        Timber.d("Search Test SearchUseCase requestMediaData combineMediaModel $combineMediaModel")
        emit(ApiResult.ResultSuccess(callbackId, combineMediaModel))
    }

    suspend fun clearMediaData(callbackId: Int): Flow<ApiResult> = flow{
        refreshValue()
        emit(ApiResult.ResultSuccess(callbackId, true))
    }

    private fun refreshValue() {
        imageMediaModel.meta.pageNum = 1
        imageMediaModel.meta.endPage = false
        imageMediaModel.documents.clear()

        videoMediaModel.meta.pageNum = 1
        videoMediaModel.meta.endPage = false
        videoMediaModel.documents.clear()
    }
    private fun checkImageEndPage(): Boolean = imageMediaModel.meta.checkEndPage(IMAGE_MAX_PAGE_NUM)
    private fun checkVideoEndPage(): Boolean = videoMediaModel.meta.checkEndPage(VIDEO_MAX_PAGE_NUM)

    private fun parsingImageData(imageModel: ImageModel) {
//        Timber.d("Search Test SearchUseCase parsingImageData imageModel $imageModel")
        imageMediaModel.meta.pageNum++
        imageMediaModel.meta.endPage = imageModel.meta.is_end
        imageMediaModel.documents = imageModel.documents.map { document ->
            MediaModel.Document(
                thumbnail = document.thumbnail_url,
                title = document.display_sitename,
                category = document.collection,
                contents = document.doc_url,
                time = document.datetime,
                isFavorite = false,
                mediaType = ApiConst.Param.MEDIA_TYPE_IMAGE
            )
        }.toMutableList()
//        Timber.d("Search Test SearchUseCase parsingImageData imageMediaModel $imageMediaModel")
    }

    private fun parsingVideoData(videoModel: VideoModel) {
//        Timber.d("Search Test SearchUseCase parsingVideoData videoModel $videoModel")
        videoMediaModel.meta.pageNum++
        videoMediaModel.meta.endPage = videoModel.meta.is_end
        videoMediaModel.documents = videoModel.documents.map { document ->
            MediaModel.Document(
                thumbnail = document.thumbnail,
                title = document.title,
                category = "",
                contents = document.url,
                time = document.datetime,
                isFavorite = false,
                mediaType = ApiConst.Param.MEDIA_TYPE_VIDEO
            )
        }.toMutableList()
//        Timber.d("Search Test SearchUseCase parsingImageData videoMediaModel $videoMediaModel")
    }

    private fun sortLatestMediaModels(combineMediaModel: MediaModel) {
        combineMediaModel.documents.sortWith{ model1, model2 ->
            model2.time.compareTo(model1.time)
        }
    }

    private fun checkFavorite(combineMediaModel: MediaModel) {
        val favoriteList: MutableList<MediaModel.Document> = preferenceRepository.readFavorite()
//        Timber.d("Search Test SearchUseCase checkFavorite favoriteList $favoriteList")
        combineMediaModel.documents.forEach { mediaModel ->
            favoriteList.forEach { favoriteItem ->
                if(FavoriteUtil.isSameModel(mediaModel, favoriteItem)) {
                    mediaModel.isFavorite = true
                }
            }
        }
    }
}