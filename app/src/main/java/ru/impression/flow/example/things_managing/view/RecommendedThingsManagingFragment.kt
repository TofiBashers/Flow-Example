package ru.impression.flow.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.flow.Flow
import ru.impression.flow.example.things_managing.E1
import ru.impression.flow.example.things_managing.RecommendedThingsLiked
import ru.impression.flow.example.things_managing.ShowRecommendedThings

class RecommendedThingsManagingFragment : ThingsManagingFragment() {

    override fun performAction(action: Flow.Action) {
        when (action) {
            is ShowRecommendedThings -> {
                adapterData.addAll(action.things)
                updateAdapter()
            }
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        onEvent(RecommendedThingsLiked(adapterData[position]))
        onEvent(E1())
    }

    companion object {
        fun newInstance() = RecommendedThingsManagingFragment()
    }
}