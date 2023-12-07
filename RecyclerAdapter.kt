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

class Contacts
{
    private var name : String = ""
    private var title : String = ""
    private var pic : String = ""

    constructor(name : String, title : String, pic : String)
    {
        this.name = name
        this.title = title
        this.pic = pic
    }

    public fun setName(name : String)
    {
        this.name = name
    }

    public fun getName() : String
    {
        return name
    }

    public fun getTitle() : String
    {
        return title
    }

    public fun setTitle(title : String)
    {
        this.title = title
    }

    public fun setPic(pic : String)
    {
        this.pic = pic
    }

    public fun getPic() : String
    {
        return pic
    }

}



class RecyclerAdapter(private val context: Context)  : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
    private var people: Array<Contacts> = emptyArray()
    public var  album = arrayOf<Array<String>>()

    init {
        // Read data from albums.txt and populate the 'people' array
        try {

            val is1 = context.assets.open("albums.txt")
            val reader1 = BufferedReader(InputStreamReader(is1))
            val allData1 = mutableListOf<Contacts>()

            reader1.useLines { lines ->
                lines.forEach { line ->
                    val columns = line.split("^")
                    if (columns.size >= 3) {
                        val name = columns[0].trim()
                        // Skip second column
                        val title = columns[2].trim()
                        val pic = columns.last().trim()

                        val contact = Contacts(name, title, pic)
                        allData1.add(contact)
                    }
                }
            }

            people = allData1.toTypedArray()

            reader1.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int
    {
        return people.size
    }

    //This creates a ViewHolder object based on card_layout for each cell
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemTitle.text = people[position].getName()
        holder.itemDetail.text = people[position].getTitle()
        holder.itemImage.setImageResource(MainActivity.getInstance().resources.getIdentifier(people[position].getPic(),"drawable",
            MainActivity.getInstance().packageName))
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init
        {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTitle = itemView.findViewById(R.id.name)
            itemDetail = itemView.findViewById(R.id.title)

            var handler = Handler()
            itemView.setOnClickListener(handler)

        }

        inner class Handler() : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
                val itemPosition = getLayoutPosition()
                //Get the navigation controller
                var navController = v?.let { Navigation.findNavController(it) }
                val bundle = Bundle()
                bundle.putInt("position", itemPosition)
                if (navController != null) {
                    navController.navigate(R.id.secondToFifth,bundle)
                }
            }
        }

    }
}
