package ru.virarnd.userslistrestapicode61.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.virarnd.userslistrestapicode61.data.ReqresService
import ru.virarnd.userslistrestapicode61.data.User
import ru.virarnd.userslistrestapicode61.data.UsersListResponse
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity(), InfiniteScrollListener.OnLoadMoreListener {

    lateinit var recyclerAdapter: CustomAdapter
    lateinit var infiniteScrollListener: InfiniteScrollListener
    private val reqresService: ReqresService = ReqresService.create()
    private var refreshingInProcess: Boolean
    private var nextPageToLoad: Long
    private var totalPages: Long
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
    private var call: Call<UsersListResponse>? = null
    private var recyclerState: Parcelable?
    private var usersInstance: ArrayList<User> = arrayListOf()

    init {
        refreshingInProcess = false
        recyclerState = null
        nextPageToLoad = 1L
        totalPages = Long.MAX_VALUE
    }

    companion object {
        const val LIST_STATE_KEY = "List State Key"
        const val NEXT_PAGE_KEY = "Next Page To Load"
        const val RECYCLER_STATE_KEY = "Recycler State Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.virarnd.userslistrestapicode61.R.layout.activity_main)
        if (savedInstanceState != null) {
            loadState(savedInstanceState)
            displayUsers()
        } else {
            initView()
        }


    }

    private fun displayUsers() {
        swipe_refresh_layout.isEnabled = false
        swipe_refresh_layout.setOnRefreshListener { swipeDown() }
        swipe_refresh_layout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        infiniteScrollListener = InfiniteScrollListener(layoutManager, this)
        infiniteScrollListener.setLoaded()
        recyclerAdapter = CustomAdapter { user: User -> userClicked(user) }
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = recyclerAdapter
        recycler_view.addOnScrollListener(infiniteScrollListener)
        recycler_view.addItemDecoration(RecipeListDecorator(8))
        recyclerAdapter.addUsers(usersInstance)
        (recycler_view.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerState)
    }


    private fun initView() {
        swipe_refresh_layout.isEnabled = false
        swipe_refresh_layout.setOnRefreshListener { swipeDown() }
        swipe_refresh_layout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        infiniteScrollListener = InfiniteScrollListener(layoutManager, this)
        infiniteScrollListener.setLoaded()
        recyclerAdapter = CustomAdapter { user: User -> userClicked(user) }
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = recyclerAdapter
        recycler_view.addOnScrollListener(infiniteScrollListener)
        recycler_view.addItemDecoration(RecipeListDecorator(8))
        onLoadMore()
//        recyclerAdapter.initAdapter(mutableListOf())
    }

    inner class UsersListResponseCallback : Callback<UsersListResponse> {
        override fun onFailure(call: Call<UsersListResponse>, t: Throwable) {
            if (refreshingInProcess) {
                refreshingInProcess = false
                switchSwipeRefresh()
            }
            recyclerAdapter.removeLastNullUser()
            Toast.makeText(applicationContext, "Pull to refresh list!", Toast.LENGTH_LONG).show()
            swipe_refresh_layout.isEnabled = true
            Timber.d { "Failure!" }
        }

        private fun switchSwipeRefresh() {
            if (swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
                swipe_refresh_layout.isEnabled = false
            }
        }

        override fun onResponse(call: Call<UsersListResponse>, response: Response<UsersListResponse>) {
            refreshingInProcess = false
            recyclerAdapter.removeLastNullUser()
            val serverResponse = response.body()
            totalPages = serverResponse?.totalPages ?: Long.MAX_VALUE
            val userDataList = response.body()?.usersDataList
            if (userDataList.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "No more data!", Toast.LENGTH_SHORT).show()
                return
            }
            val newUsersList = userDataList.toMutableList()
            recyclerAdapter.addUsers(newUsersList)
            Timber.d { "response ${response.body()?.usersDataList?.get(1)?.email}" }
            switchSwipeRefresh()
            nextPageToLoad++
            infiniteScrollListener.setLoaded()
        }
    }

    private fun swipeDown() {
        Timber.d { "Swiped!!!" }
        if (!refreshingInProcess) {
            refreshingInProcess = true
            Timber.d { "Trying to refresh" }
            onLoadMore()
        }
    }


    private fun userClicked(user: User) {
        Timber.d { "Users id clicked = ${user.id}" }
    }

    override fun onLoadMore() {
        Timber.d { "CurrentPage = $nextPageToLoad, TotalPages = $totalPages" }
        if (nextPageToLoad > totalPages) {
            Toast.makeText(applicationContext, "No more data!", Toast.LENGTH_SHORT).show()
            return
        }
        Timber.d { "Want load more!" }
        recyclerAdapter.addNullUser()

        call = reqresService.getUsers(nextPageToLoad)
        call!!.enqueue(UsersListResponseCallback())
    }

    override fun onPause() {
        super.onPause()
        if (call != null) {
            call!!.cancel()
            recyclerAdapter.removeLastNullUser()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(LIST_STATE_KEY, recyclerAdapter.usersList.toCollection(ArrayList()))
        outState?.putParcelable(RECYCLER_STATE_KEY, recycler_view.layoutManager?.onSaveInstanceState())
        outState?.putLong(NEXT_PAGE_KEY, nextPageToLoad)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            loadState(savedInstanceState)
        }
    }

    private fun loadState(savedInstanceState: Bundle) {
        recyclerState = savedInstanceState.getParcelable(RECYCLER_STATE_KEY)
        usersInstance = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY)
        nextPageToLoad = savedInstanceState.getLong(NEXT_PAGE_KEY)
    }


    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
    }
}