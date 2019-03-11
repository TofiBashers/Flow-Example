package ru.impression.flow.example.things_managing.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.impression.flow.example.R

class ThingsLoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_things_loading, container, false)

    companion object {
        fun newInstance() = ThingsLoadingFragment()
    }
}