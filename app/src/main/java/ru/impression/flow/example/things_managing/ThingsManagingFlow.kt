package ru.impression.flow.example.things_managing

import ru.impression.flow.Flow

class ThingsManagingFlow : Flow<ThingsManagingFlow.State>(State()) {

    override fun start() {

        performAction(LoadFavouriteThings())

        subscribeOnEvent<FavouriteThingsLoaded> {

            performAction(ShowFavouriteThings(it.things))
        }

        subscribeOnEvent<FavouriteThingsUnliked> {

            performAction(RemoveFavouriteThing(it.thing))
        }

        subscribeOnEvent<RecommendedThingsRequested> {

            state.recommendedThingsEnabled = true

            loadRecommendedThings()
        }

        subscribeOnEvent<FavouriteThingsUpdated> {

            if (state.recommendedThingsEnabled) {

                if (state.loadingRecommendedThings) {

                    performAction(CancelLoadingRecommendedThings())
                }

                loadRecommendedThings()
            }
        }

        subscribeOnEvent<RecommendedThingsLoaded> {

            state.loadingRecommendedThings = false

            performAction(ShowRecommendedThings(it.things))

            state.showingRecommendedThings = true
        }

        subscribeOnEvent<RecommendedThingsLiked> {

            performAction(AddFavouriteThing(it.thing))
        }

        subscribeOnEvent<RecommendedThingsHideRequested> {

            if (state.loadingRecommendedThings) {

                performAction(CancelLoadingRecommendedThings())

                state.loadingRecommendedThings = false

            } else if (state.showingRecommendedThings) {

                performAction(HideRecommendedThings())

                state.showingRecommendedThings = false
            }

            state.recommendedThingsEnabled = false
        }

        subscribeOnSeriesOfEvents<E1, E2> { e1, e2 ->
            performAction(A1())
        }
    }

    private fun loadRecommendedThings() {

        performAction(LoadRecommendedThings())

        state.loadingRecommendedThings = true
    }

    class State : Flow.State() {
        var recommendedThingsEnabled: Boolean = false
        var loadingRecommendedThings: Boolean = false
        var showingRecommendedThings: Boolean = false
    }
}

class LoadFavouriteThings : Flow.Action()

class FavouriteThingsLoaded(val things: List<String>) : Flow.Event()

class ShowFavouriteThings(val things: List<String>) : Flow.Action()

class FavouriteThingsUnliked(val thing: String) : Flow.Event()

class RemoveFavouriteThing(val thing: String) : Flow.Action()

class RecommendedThingsRequested : Flow.Event()

class LoadRecommendedThings : Flow.Action()

class RecommendedThingsLoaded(val things: List<String>) : Flow.Event()

class CancelLoadingRecommendedThings : Flow.Action()

class ShowRecommendedThings(val things: List<String>) : Flow.Action()

class RecommendedThingsLiked(val thing: String) : Flow.Event()

class AddFavouriteThing(val thing: String) : Flow.Action()

class FavouriteThingsUpdated : Flow.Event()

class RecommendedThingsHideRequested : Flow.Event()

class HideRecommendedThings : Flow.Action()

class E1 : Flow.Event()
class E2 : Flow.Event()

class A1 : Flow.Action()