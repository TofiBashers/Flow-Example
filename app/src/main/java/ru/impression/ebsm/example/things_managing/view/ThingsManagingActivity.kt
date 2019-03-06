package ru.impression.ebsm.example.things_managing.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_things_managing.*
import ru.impression.ebsm.Flow
import ru.impression.ebsm.FlowManager
import ru.impression.ebsm.FlowPerformer
import ru.impression.ebsm.R
import ru.impression.ebsm.example.things_managing.*
import ru.impression.ebsm.example.things_managing.view.model.ThingsManagingViewModel

class ThingsManagingActivity : AppCompatActivity(), FlowPerformer<ThingsManagingFlow> {

    override val flow = ThingsManagingFlow::class.java

    private lateinit var viewModel: ThingsManagingViewModel

    init {
        FlowManager.startFlow(flow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_things_managing)
        viewModel = ViewModelProviders
            .of(this, ViewModelProvider.NewInstanceFactory())
            .get(ThingsManagingViewModel::class.java)

        recommended_things_loader_button.setOnClickListener {
            if ((it as Button).text == "Показать") {
                it.text = "Скрыть"
                onEvent(RecommendedThingsRequested())
            } else {
                it.text = "Показать"
                onEvent(RecommendedThingsHideRequested())
            }
        }

        attachToFlow()
    }

    override fun performAction(action: Flow.Action) {
        when (action) {
            is LoadFavouriteThings -> showFragment(R.id.top_container, ThingsLoadingFragment.newInstance())
            is ShowFavouriteThings -> showFragment(R.id.top_container, FavouriteThingsManagingFragment.newInstance())
            is LoadRecommendedThings -> showFragment(R.id.bottom_container, ThingsLoadingFragment.newInstance())
            is CancelLoadingRecommendedThings -> removeFragment(ThingsLoadingFragment::class.java)
            is ShowRecommendedThings ->
                showFragment(R.id.bottom_container, RecommendedThingsManagingFragment.newInstance())
            is HideRecommendedThings -> removeFragment(RecommendedThingsManagingFragment::class.java)
        }
    }

    private fun showFragment(container: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(container, fragment, fragment::class.java.canonicalName)
            .commit()
    }

    private fun <F : Fragment> removeFragment(fragment: Class<F>) =
        supportFragmentManager.findFragmentByTag(fragment.canonicalName)?.let {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }

    override fun finish() {
        FlowManager.finishFlow(flow)
        super.finish()
    }
}