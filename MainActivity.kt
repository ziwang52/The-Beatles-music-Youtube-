package com.example.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable

class Info : Serializable {
  private var id: String = ""
  private var url: String = ""

  public fun getId(): String {
    return id
  }

  public fun setId(id: String) {
    this.id = id.toString()
  }

  public fun getUrl(): String {
    return url
  }

  public fun setUrl(url: String) {
    this.url = url.toString()
  }
}
class MainActivity : AppCompatActivity()
{
  var imagename = arrayOf<String>()
  companion object
  {
    private var instance : MainActivity? = null
    public fun getInstance() : MainActivity
    {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    instance = this



    val navController = Navigation.findNavController(this, R.id.fragment)

    var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

    bottomNavigationView.setupWithNavController(navController)

  }
}