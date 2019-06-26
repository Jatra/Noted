package uk.co.jatra.noted.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.jatra.noted.R

private const val EVENTS_TAG = "EVENTS_TAG"
private const val OCCURRENCE_TAG = "OCCURRENCE_TAG"
private const val USERS_TAG = "USERS_TAG"

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_users -> {
                pager.currentItem = 1
                setTitle(R.string.title_users)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_events -> {
                pager.currentItem = 2
                setTitle(R.string.title_events)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_occurrences -> {
                pager.currentItem = 0
                setTitle(R.string.title_occurrences)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = NotedPagerAdapter(supportFragmentManager)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
