package me.austinng.austintab

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var austinTabView1: AustinTabView
    private lateinit var austinTabView2: AustinTabView
    private lateinit var austinTabView3: AustinTabView
    private lateinit var austinTabView4: AustinTabView
    private lateinit var austinTabView5: AustinTabView
    private lateinit var austinTabView6: AustinTabView
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        austinTabView1 = findViewById(R.id.austinTabView1)
        austinTabView2 = findViewById(R.id.austinTabView2)
        austinTabView3 = findViewById(R.id.austinTabView3)
        austinTabView4 = findViewById(R.id.austinTabView4)
        austinTabView5 = findViewById(R.id.austinTabView5)
        austinTabView6 = findViewById(R.id.austinTabView6)
        viewPager2 = findViewById(R.id.viewPager2)
        viewPager2.adapter = ViewPagerAdapter(listOf("Page 1", "Page 2", "Page 3", "Page 4", "Page 5", "Page 6", "Page 7", "Page 8", "Page 9", "Page 10"))

        austinTabView1.setData(
            listOf(
                TabData("Home", icon = R.drawable.ic_home),
                TabData("Notifications", badge = "3"),
                TabData("Profile"),
            )
        )
        austinTabView1.setTabSelectedListener { index ->
            Log.d(TAG, "Index $index selected")
        }
        austinTabView1.setTabReselectedListener { index ->
            Log.d(TAG, "Index $index reselected")
        }
        austinTabView1.setTabUnSelectedListener { index ->
            Log.d(TAG, "Index $index unselected")
        }

        austinTabView2.setData(
            listOf(
                TabData("Home", icon = R.drawable.ic_home),
                TabData("Notifications", badge = "3"),
                TabData("Profile"),
            )
        )
        austinTabView2.setIndex(1)

        austinTabView3.setData(
            listOf(
                TabData("Home"),
                TabData("Search"),
                TabData("Profile"),
            )
        )

        austinTabView4.setData(
            listOf(
                TabData("Home"),
                TabData("Search"),
                TabData("Profile"),
            )
        )

        austinTabView5.setData(
            listOf(
                TabData("Home"),
                TabData("Profile"),
                TabData("Dashboard"),
                TabData("Notifications"),
                TabData("Settings"),
                TabData("Messages"),
                TabData("Favorites"),
                TabData("Search"),
                TabData("History"),
                TabData("Statistics"),
            )
        )

        austinTabView6.setData(
            listOf(
                TabData("Home"),
                TabData("Profile"),
                TabData("Dashboard"),
                TabData("Notifications"),
                TabData("Settings"),
                TabData("Messages"),
                TabData("Favorites"),
                TabData("Search"),
                TabData("History"),
                TabData("Statistics"),
            )
        )
        austinTabView6.attachWithViewPager2(viewPager2)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}