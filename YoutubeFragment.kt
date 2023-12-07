package com.example.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.net.URL

class UIThreadHelper : Runnable
{
    private var video : String = ""
    constructor(video : String)
    {
        this.video = video
    }
    override fun run()
    {
        var web = MainActivity.getInstance().findViewById<WebView>(R.id.web)
        val settings = web.getSettings()
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        settings.setMinimumFontSize(10)
        settings.setLoadWithOverviewMode(true)
        settings.setUseWideViewPort(true)
        settings.setBuiltInZoomControls(true)
        settings.setDisplayZoomControls(false)
        web.setVerticalScrollBarEnabled(false)
        settings.setDomStorageEnabled(true)
        web.setWebViewClient(WebViewClient())
        var str = "https://www.youtube.com/watch?v=" + video
        web.loadUrl(str)





    }
}


class Helper : Runnable
{
    private var url : String = ""
    private var song : String = ""
    private var artist : String = ""

    constructor(url : String, song:String, artist:String)
    {
        this.url = url
        this.song = song
        this.artist = artist
    }

    override fun run()
    {
        val data = URL(url).readText()
        println(data)
        var json = JSONObject(data)
        var items = json.getJSONArray("items") // this is the "items: [ ] part

        var titles = ArrayList<String>()
        var videos = ArrayList<String>()

        for (i in 0 until items.length())
        {
            var videoObject = items.getJSONObject(i)
            //val title = videoObject.getString("title")
            //val videoId = videoObject.getString("id")
            println(videoObject)
            var idDict = videoObject.getJSONObject("id")
            println(idDict)
            var videoId = idDict.getString("videoId")
            println(videoId)
            var snippetDict = videoObject.getJSONObject("snippet")
            var title =  snippetDict.getString("title")
            println(title)
            titles.add(title)
            videos.add(videoId)
        }
        for (i in 0 until items.length())
        {
            //Get the ith item
            var videoObject = items.getJSONObject(i)

            //Extracth the id Hashmap
            var idDict = videoObject.getJSONObject("id")

            //Get the videoid using videoId key
            var videoId = idDict.getString("videoId")
            println(videoId)
            //Get the snippet Hashmap
            var snippetDict = videoObject.getJSONObject("snippet")
            //Get the title
            var title =  snippetDict.getString("title")

            //Add the titles to the lists
            titles.add(title)
            videos.add(videoId)
        }
        var selected_video : String = ""
        var selected_title : String = ""
        selected_video = videos[20]
        selected_title = titles[20]
            if (selected_video.isEmpty())
            {
                YoutubeFragment.getInstance().displayVideoNotFoundMessage()

            }else {
                var helper1 = UIThreadHelper(selected_video)
                MainActivity.getInstance().runOnUiThread(helper1)
            }
        //println(json)
    }

}


class YoutubeFragment : Fragment() {
    fun displayVideoNotFoundMessage() {
        // Handle UI-related operations in the Fragment
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), "Video Not Found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private var instance: YoutubeFragment? = null
        public fun getInstance(): YoutubeFragment {
            return instance!!
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        val position = arguments?.getInt("position") ?: -1
        val url = arguments?.getString("url")

        var song = arguments?.getString("title")
        song = song?.replace(" ", "+")
        var origSong = arguments?.getString("title")

        //Set the artist
        var artist = arguments?.getString("author")
        artist = artist?.replace(" ", "+") // Replace spaces with '+'
        artist = artist?.replace("&", "+") // Replace '&' with '+'
        var origArtist = arguments?.getString("author")

        //Encode search for YouTube
        val keywords = artist + "+" + song
        val max = 30

        //Set the youtube search
        val string =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&videoCategory=Music&order=relevance&maxResults=$max&type=video&key=AIzaSyAYOtQXgNkaoxh8WxxmtmYZaMnn_6FD-KQ"


        var helper = Helper(string, origSong!!, origArtist!!)
        var thread = Thread(helper)

        thread.start()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
