package ru.impression.flow.example.things_managing.view

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import ru.impression.flow.FlowPerformer
import ru.impression.flow.example.things_managing.ThingsManagingFlow

abstract class ThingsManagingFragment : ListFragment(), FlowPerformer<ThingsManagingFlow> {

    override val flow = ThingsManagingFlow::class.java

    protected var adapterData: ArrayList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToFlow()
    }

    protected fun updateAdapter() {
        listAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1, adapterData)
    }

    override fun onDestroy() {
        detachFromFlow()
        super.onDestroy()
    }
}