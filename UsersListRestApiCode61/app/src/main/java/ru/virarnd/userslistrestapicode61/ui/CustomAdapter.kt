package ru.virarnd.userslistrestapicode61.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.item_user.view.*
import ru.virarnd.userslistrestapicode61.R
import ru.virarnd.userslistrestapicode61.data.User

class CustomAdapter(val onItemClick: (User) -> Unit) :
    ListAdapter<User, CustomAdapter.CustomViewHolder>(UserDiffUtilCallback()) {

    companion object {
        private const val VIEW_TYPE_ITEM = 152
        private const val VIEW_TYPE_LOADING = 863
    }

    var usersList: MutableList<User?> = mutableListOf()


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (holder is UserViewHolder && (getItem(position) != null)) {
            holder.bind(getItem(position))
        } else {
            Timber.d { "Fake data" }
            // Можно что-то еще делать
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val root: View?;
        if (viewType == VIEW_TYPE_ITEM) {
            root = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return UserViewHolder(root)
        } else {
            root = LayoutInflater.from(parent.context).inflate(R.layout.item_progressbar, parent, false)
            return ProgressViewHolder(root)
        }
    }

    fun addNullUser() {
        Timber.d { "fun: addNullUser" }
        val newList = usersList.toMutableList()
        newList.add(null)
        submitList(newList)
        usersList = newList
    }

    fun removeLastNullUser() {
        Timber.d { "fun: removeLastNullUser" }
        if (!usersList.isEmpty() && usersList.last() == null) {
            val newList = usersList.toMutableList()
            newList.remove(newList.last())
            submitList(newList)
            usersList = newList
        }
    }

    fun addUsers(newUsersList: MutableList<User>) {
        Timber.d { "fun: addUsers" }
        val newList = usersList.toMutableList()
        newList.addAll(newUsersList)
        Timber.d { "Try submit newUsersList" }
        submitList(newList)
        usersList = newList
    }

    open inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    inner class UserViewHolder(itemView: View) : CustomViewHolder(itemView) {
        fun bind(user: User) = with(itemView) {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            text_email.text = user.email
            val fullName = "${user.firstName} ${user.lastName}"
            text_full_name.text = fullName
            Glide.with(itemView.context)
                .load(user.avatar)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_team)
                .into(image_avatar)
            setOnClickListener { onItemClick(user) }
        }
    }

    inner class ProgressViewHolder(itemView: View) : CustomViewHolder(itemView) {

    }

    override fun getItemViewType(position: Int): Int {
//        Timber.d { "In getItemViewType position = $position" }
        if (position >= usersList.size) {
            return 0
        }
        return if (usersList[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }
}

class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}