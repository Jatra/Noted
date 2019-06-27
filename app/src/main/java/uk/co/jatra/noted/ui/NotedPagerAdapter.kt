package uk.co.jatra.noted.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uk.co.jatra.noted.ui.event.EventFragment
import uk.co.jatra.noted.ui.event.UserFragment
import uk.co.jatra.noted.ui.occurred.OccurredFragment

class NotedPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment =
         when(position) {
            0 -> OccurredFragment()
            1 -> UserFragment()
            else -> EventFragment()
        }
    override fun getCount() = 3
}