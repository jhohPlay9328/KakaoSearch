
## 사용한 기술
* [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#kotlin_dsl)
* [Kotlin](https://kotlinlang.org/), [Coroutine](https://kotlinlang.org/docs/coroutines-overview.html), 
* [DataBinding](https://developer.android.com/topic/libraries/data-binding?hl=ko), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Material Design](https://m3.material.io/)
* [Hilt](https://dagger.dev/hilt/), [Retrofit2](https://square.github.io/retrofit/), [OkHttp3](https://square.github.io/okhttp/), [Gson](https://github.com/google/gson)
* [security-crypto-ktx](https://developer.android.com/jetpack/androidx/releases/security?hl=ko)
* [Glide](https://github.com/bumptech/glide)
* [Flow](https://kotlinlang.org/docs/flow.html), [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/), [SharedFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/)

* [MVVM](https://ko.wikipedia.org/wiki/%EB%AA%A8%EB%8D%B8-%EB%B7%B0-%EB%B7%B0%EB%AA%A8%EB%8D%B8)
* [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
* [Single Activity Architecture](https://www.youtube.com/watch?v=2k8x8V77CrU)
* [Dependency Injection](https://developer.android.com/training/dependency-injection)

## 사용한 API - Kakao developers Rest API 
* [이미지 검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-image)
* [동영상 검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video)

```

MainActivity(메인 화면)
- 변경된 즐겨찾기 정보를 addFavoriteItem, removeFavoriteItem flow 변수로 하위 Fragment에게 전달

SearchFragment(검색 화면)
- RecyclerView.Adapter를 사용하여 notifyItemRangeInserted()로 RecyclerView에 아이템 추가
- 즐겨찾기 정보가 변경되면 변경된 아이템만 찾아서 notifyItemChanged()로 아이템 변경

FavoriteFragment(즐겨찾기 화면)
- ListAdapter와 DiffUtil.ItemCallback을 사용하여 submitList()로 아이템 추가
- 즐겨찾기 정보가 변경되면 전체 즐겨찾기 데이터를 가져와 submitList() 다시 아이템 적용

DetailFragment(상세 화면)
- 선택한 리스트 아이템의 상세 정보를 보여주는 화면


```
