package ru.virarnd.recipeskt.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.fragment_main.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.common.BaseFragment
import ru.virarnd.recipeskt.common.HomeFlow
import ru.virarnd.recipeskt.data.RecipeDataProvider



class MainFragment : BaseFragment<HomeFlow>() {

    private lateinit var myAdapter: RecipesRVAdapter

    companion object {

        fun newInstance(updateList: Boolean): MainFragment {
            val fragment = MainFragment()
            val arguments = Bundle()
            arguments.putBoolean("need_update", updateList)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = RecipesRVAdapter { position: Int -> recipeClicked(position) }
        rv_recipes.addItemDecoration(RecipeListDecorator(8))
        rv_recipes.adapter = myAdapter
        myAdapter.updateRecipes(RecipeDataProvider.getRecipesList())
        fab_main.setOnClickListener { addNewRecipe() }

    }

    override fun onResume() {
        super.onResume()
        flow?.changeTitle(activity?.getString(R.string.app_name))
        flow?.changeHomeAsUpEnabled(false)
    }


    private fun addNewRecipe() {
        flow?.openEdit(-1)
    }

    private fun recipeClicked(position: Int) {
        Timber.d { "position in mainActivity = $position" }
        flow?.openDetails(position)
    }

}
