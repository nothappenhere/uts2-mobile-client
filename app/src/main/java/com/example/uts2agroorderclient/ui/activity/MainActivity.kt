package com.example.uts2agroorderclient.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.ui.fragment.AboutFragment
import com.example.uts2agroorderclient.ui.fragment.MyOrdersFragment
import com.example.uts2agroorderclient.ui.fragment.ProductsFragment
import com.example.uts2agroorderclient.util.PreferencesManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
	private lateinit var prefs: PreferencesManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		prefs = PreferencesManager(this)

		val token = prefs.getToken()
		if (token == null) {
			startActivity(Intent(this, LoginActivity::class.java))
			finish()
			return
		}

		// Setup Toolbar
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.title = "AgroOrder Client"

		val fragments = listOf(
			ProductsFragment(),
			MyOrdersFragment(),
			AboutFragment()
		)
		val titles = listOf("Products", "My Orders", "About")

		val viewPager = findViewById<ViewPager2>(R.id.viewPager)
		viewPager.adapter = ViewPagerAdapter(this, fragments)

		val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
		TabLayoutMediator(tabLayout, viewPager) { tab, position ->
			tab.text = titles[position]
		}.attach()
	}

	// Inflate menu
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	// Handle menu item click
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.action_logout) {
			logout()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	private fun logout() {
		prefs.clear()  // Hapus token
		startActivity(Intent(this, LoginActivity::class.java))
		finish()
	}
}

class ViewPagerAdapter(
	fragmentActivity: FragmentActivity,
	private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
	override fun getItemCount(): Int = fragments.size
	override fun createFragment(position: Int): Fragment = fragments[position]
}