package com.example.uts2agroorderclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.adapter.MyOrderAdapter
import com.example.uts2agroorderclient.api.RetrofitClient
import com.example.uts2agroorderclient.util.PreferencesManager
import kotlinx.coroutines.launch

class MyOrdersFragment : Fragment() {

	private lateinit var adapter: MyOrderAdapter
	private lateinit var swipeRefresh: SwipeRefreshLayout

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_my_orders, container, false)

		swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
		swipeRefresh.setOnRefreshListener {
			loadMyOrders()
		}

		adapter = MyOrderAdapter()

		view.findViewById<RecyclerView>(R.id.rvMyOrders).apply {
			layoutManager = LinearLayoutManager(requireContext())
			adapter = this@MyOrdersFragment.adapter
		}

		loadMyOrders()
		return view
	}

	private fun loadMyOrders() {
		swipeRefresh.isRefreshing = true
		val token = PreferencesManager(requireContext()).getToken() ?: return

		lifecycleScope.launch {
			try {
				val response = RetrofitClient.apiService.getMyOrders(token)
				if (response.isSuccessful) {
					adapter.submitList(response.body())
				} else {
					Toast.makeText(requireContext(), "Gagal memuat pesanan", Toast.LENGTH_SHORT).show()
				}
			} catch (e: Exception) {
				Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
			} finally {
				swipeRefresh.isRefreshing = false
			}
		}
	}
}
