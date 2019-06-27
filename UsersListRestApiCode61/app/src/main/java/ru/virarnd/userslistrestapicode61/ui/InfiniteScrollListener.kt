package ru.virarnd.userslistrestapicode61.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber

class InfiniteScrollListener (private val linearLayoutManager : LinearLayoutManager, private val listener : OnLoadMoreListener) : RecyclerView.OnScrollListener() {

    private var loading: Boolean = false
    private var pauseListening = false
    private var endOfFeedAdded = false

    companion object {
        private const val VISIBLE_THRESHOLD = 2
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == 0 && dy == 0)
            return
        val totalItemCount = linearLayoutManager.itemCount
//        Timber.d { "totalItemCount = $totalItemCount" }
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
//        Timber.d { "lastVisibleItem = $lastVisibleItem" }
        // lastVisibleItem < 0 -- значит список еще пустой
        if ((!loading && (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) && totalItemCount != 0 && !endOfFeedAdded && !pauseListening) || lastVisibleItem < 0) {
            listener.onLoadMore()
            Timber.d{"listener.onLoadMore()"}
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