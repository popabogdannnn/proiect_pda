package com.example.proiect_pda.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_pda.MainActivity
import com.example.proiect_pda.MyAdapter
import com.example.proiect_pda.Pictures
import com.example.proiect_pda.R
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.absolutePathString

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [settings.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val PHOTO_DIRECTORY = "/storage/emulated/0/Android/data/com.example.proiect_pda/files/Pictures/"
class settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recycler_view : RecyclerView
    private lateinit var picture_array : ArrayList<Pictures>
    private lateinit var searched_picture_array: ArrayList<Pictures>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        var curr_activity = activity as MainActivity
        recycler_view = view.findViewById(R.id.recyclerView)
        recycler_view.layoutManager = LinearLayoutManager(curr_activity)
        recycler_view.setHasFixedSize(true)

        picture_array = arrayListOf<Pictures>()
        searched_picture_array = arrayListOf<Pictures>()

        Files.walk(Paths.get(PHOTO_DIRECTORY))
            .filter { Files.isRegularFile(it) }
            .forEach {
                val new_picture = Pictures(it.absolutePathString())
                picture_array.add(new_picture)
            }
        searched_picture_array.addAll(picture_array)

        recycler_view.adapter = MyAdapter(picture_array)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.search_action)
        val search_view = item?.actionView as SearchView
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Nu e nevoie")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searched_picture_array.clear()
                val searched_text = newText!!.toLowerCase(Locale.getDefault())
                if(searched_text.isNotEmpty()) {
                    picture_array.forEach({
                        if(it.image_path.toLowerCase(Locale.getDefault()).contains(searched_text)) {
                            searched_picture_array.add(it)
                        }
                    })
                    recycler_view.adapter!!.notifyDataSetChanged()
                }
                else {
                    searched_picture_array.addAll(picture_array)
                    recycler_view.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}