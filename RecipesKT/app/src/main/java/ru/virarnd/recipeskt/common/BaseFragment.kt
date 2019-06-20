
package ru.virarnd.recipeskt.common

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment<F : Flow> : Fragment() {
    protected var flow: F? = null

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        flow = context as? F
    }

    override fun onDetach() {
        super.onDetach()
        flow = null
    }
}