package com.example.bottomnavigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
data class Song(var title: String, var author: String)

class RecyclerAdapterSchedule(private val context: Context, pos: Int = -1) : RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder>() {
    private var pos: Int = -1
    private var item: Int = -1
    private val albumNames = mutableListOf<String>()
    private val songs = mutableListOf<Song>()

    init {
        loadAlbumData()
        setPos(pos) // Initialize the adapter with the selected position
    }

    private fun loadAlbumData() {
        try {
            val is1 = context.assets.open("albums.txt")
            val reader1 = BufferedReader(InputStreamReader(is1))

            reader1.useLines { lines ->
                lines.forEach { line ->
                    val columns = line.split("^")
                    if (columns.isNotEmpty()) {
                        val albumName = columns.last().trim()
                        albumNames.add(albumName)
                    }
                }
            }

            reader1.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadSongData() {
        if (pos in 0 until albumNames.size) {
            val fileName = "${albumNames[pos]}.txt"
            val is1 = context.assets.open(fileName)
            val reader1 = BufferedReader(InputStreamReader(is1))
            reader1.useLines { lines ->
                lines.forEach { line ->
                    val columns = line.split("^")
                    if (columns.size >= 2) {
                        val songName = columns[0].trim()
                        val authorName = columns[1].trim()
                        val song = Song(songName, authorName)
                        songs.add(song)
                    }
                }
            }
            reader1.close()
        }
    }

    public fun setPos(pos: Int) {
        if (pos in 0 until albumNames.size) {
            this.pos = pos
            songs.clear() // Clear existing songs
            loadSongData() // Load songs for the selected album
            notifyDataSetChanged() // Notify the adapter that the data has changed

            // Create the bundle and add song information


        }
    }

    override fun onBindViewHolder(holder: RecyclerAdapterSchedule.ViewHolder, position: Int) {
        holder.itemScheduleHeading.text = songs[position].title
        holder.itemSchedule.text = songs[position].author
        // Pass the list of songs to YoutubeFragment

    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterSchedule.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule_card_layout, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemScheduleHeading: TextView
        var itemSchedule: TextView

        init {
            itemScheduleHeading = itemView.findViewById(R.id.scheduleHeading)
            itemSchedule = itemView.findViewById(R.id.schedule)

            var handler = Handler()
            itemView.setOnClickListener(handler)
        }
        inner class Handler() : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
                val itemPosition = getLayoutPosition()
                //Get the navigation controller
                var navController = Navigation.findNavController(FouthFragment.getInstance().requireView())
                 val bundle = Bundle()
                if (itemPosition in 0 until songs.size) {
                    bundle.putString("title", songs[itemPosition].title)
                    bundle.putString("author", songs[itemPosition].author)
                }


                navController.navigate(R.id.fourthToyoutube,bundle)
            }
        }
    }
}
