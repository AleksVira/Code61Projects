package ru.virarnd.userslistrestapicode61.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber

class InfiniteScrollListener (private val linearLayoutManager : LinearLayoutManager, private val listener : OnLoadMoreListener) : RecyclerView.OnScrollListener() {

    private var loading: Boolean = false
    private var pauseListening = false
    private var endOfFeedAdded = false

    companion object {
        private const val VISIBLE_THRESHOLD = 1
        private const val NUM_LOAD_ITEMS = 5
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
//        Timber.d { "Scroll detected! dx = $dx, dy = $dy" }
        if (dx == 0 && dy == 0)
            return
        val totalItemCount = linearLayoutManager.itemCount
//        Timber.d { "totalItemCount = $totalItemCount" }
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
//        Timber.d { "lastVisibleItem = $lastVisibleItem" }
        if (!loading && (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) && totalItemCount != 0 && !endOfFeedAdded && !pauseListening) {
            listener.onLoadMore()
            loading = true
        }
    }

    fun setLoaded() {
        loading = false
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
//        Timber.d { "Scroll State Changed!" }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val canScrollDownMore = recyclerView.canScrollVertically(1)
            // Если recyclerView.canScrollVertically(1) возвращает false значит достигнут конец списка
            if (!canScrollDownMore) {
                //Принудительно вызываю метод onScrolled() имитируя прокрутку вниз
                onScrolled(recyclerView, 0, 1)
            } else {
//                Timber.d { "Достигнут конец списка" }
                // Можно что-то еще делать
            }
        }
    }
}