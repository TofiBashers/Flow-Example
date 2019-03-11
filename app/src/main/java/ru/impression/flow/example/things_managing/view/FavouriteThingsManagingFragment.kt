package ru.impression.flow.example.things_managing.view

import android.view.View
import android.widget.ListView
import ru.impression.flow.Flow
import ru.impression.flow.example.things_managing.*

class FavouriteThingsManagingFragment : ThingsManagingFragment() {

    override fun performAction(action: Flow.Action) {
        when (action) {
            is ShowFavouriteThings -> {
                adapterData.addAll(action.things)
                updateAdapter()
            }
            is RemoveFavouriteThing -> {
                adapterData.remove(action.thing)
                updateAdapter()
                onEvent(FavouriteThingsUpdated())
            }
            is AddFavouriteThing -> {
                adapterData.add(action.thing)
                updateAdapter()
                onEvent(FavouriteThingsUpdated())
            }
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        onEvent(FavouriteThingsUnliked(adapterData[position]))
        onEvent(E2())
    }

    companion object {
        fun newInstance() = FavouriteThingsManagingFragment()
    }
}