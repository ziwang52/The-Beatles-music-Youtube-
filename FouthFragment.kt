package com.example.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R.layout
import com.example.bottomnavigation.R.layout.*



class FouthFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder>? = null
    companion object
    {
        private var instance : FouthFragment? = null
        public fun getInstance() : FouthFragment
        {
            return instance!!
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance=this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fouth, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arguments = this.getArguments()
        var pos = arguments?.getInt("position")

            var recycler_view2 =
                MainActivity.getInstance().findViewById<RecyclerView>(R.id.recycler_view2)


            layoutManager = LinearLayoutManager(MainActivity.getInstance())
            recycler_view2.layoutManager = layoutManager
            adapter = RecyclerAdapterSchedule(
                requireContext(),
                pos!!
            ) //this is the desired contact selected
            recycler_view2.adapter = adapter

    }
}