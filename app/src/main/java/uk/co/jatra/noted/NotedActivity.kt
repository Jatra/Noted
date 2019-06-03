package uk.co.jatra.noted

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uk.co.jatra.noted.ui.occurred.OccurredFragment

//INITIAL: no navigation, so a single fragment.
class NotedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.occurred_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, OccurredFragment.newInstance())
                .commitNow()
        }
    }
}
