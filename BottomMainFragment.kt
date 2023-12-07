
package com.example.bottomnavigation


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.bottomnavigation.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class BottomMainFragment : Fragment() {

    private var img: Array<String> = emptyArray()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadImagenameData()
    }

    private fun loadImagenameData() {
        try {
            val is1 = requireContext().assets.open("albums.txt")
            val reader1 = BufferedReader(InputStreamReader(is1))
            val lines1 = reader1.readLines()
            val arrayLines1 = lines1.toTypedArray()
            val allData1 = mutableListOf<Array<String>>()

            for (i in 0 until arrayLines1.size) {
                val array1 = arrayLines1[i].split("^").toTypedArray()
                allData1.add(array1)
            }

            img = allData1.filter { it.isNotEmpty() }
                .map { it.last() }
                .toTypedArray()

            reader1.close()
        } catch (e: IOException) {
            // Handle any potential exceptions, such as file not found
            e.printStackTrace()
        }
    }
    private var imageView: ImageView? = null
    private var count = 0
    private val handler = Handler(Looper.getMainLooper())
    private val imageChangeRunnable = object : Runnable {
        override fun run() {

            if (img.isNotEmpty()) {

                if (count == 0){
                    // Display "intro1.jpg" as the first image
                    val introId = resources.getIdentifier("intro1", "drawable", requireContext().packageName)
                    imageView?.setImageResource(introId)
                    count++
                }
                val fn = img[count % img.size]
                val id = resources.getIdentifier(fn, "drawable", requireContext().packageName)
                imageView?.setImageResource(id)
                count++

                if (count == img.size) {
                    count = 0
                }
                handler.postDelayed(this, 3000) // Change image every 2 seconds
            }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val view = inflater.inflate(R.layout.fragment_bottom_main, container, false)
        imageView = view.findViewById(R.id.imageView)
        handler.post(imageChangeRunnable) // Start the image slideshow

        return view
    }
}
