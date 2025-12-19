package com.example.uts2agroorderclient.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.adapter.ProductAdapter
import com.example.uts2agroorderclient.api.RajaOngkirClient
import com.example.uts2agroorderclient.api.RetrofitClient
import com.example.uts2agroorderclient.model.Product
import com.example.uts2agroorderclient.model.SubmitOrderRequest
import com.example.uts2agroorderclient.util.PreferencesManager
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {
	private lateinit var adapter: ProductAdapter
	private lateinit var swipeRefresh: SwipeRefreshLayout

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_products, container, false)

		swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
		swipeRefresh.setOnRefreshListener {
			loadProducts()
		}

		adapter = ProductAdapter { product ->
			showOrderDialog(product)
		}

		view.findViewById<RecyclerView>(R.id.rvProducts).apply {
			layoutManager = LinearLayoutManager(requireContext())
			this.adapter = this@ProductsFragment.adapter
		}

		loadProducts()
		return view
	}

	private fun loadProducts() {
		swipeRefresh.isRefreshing = true

		lifecycleScope.launch {
			try {
				val response = RetrofitClient.apiService.getProducts()
				if (response.isSuccessful) {
					adapter.submitList(response.body())
				} else {
					Toast.makeText(requireContext(), "Gagal load products", Toast.LENGTH_SHORT).show()
				}
			} catch (e: Exception) {
				Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
			} finally {
				swipeRefresh.isRefreshing = false
			}
		}
	}

	private fun showOrderDialog(product: Product) {
		val dialogView = layoutInflater.inflate(R.layout.dialog_order, null)
		val dialog = AlertDialog.Builder(requireContext())
			.setTitle("Order ${product.name}")
			.setView(dialogView)
			.setNegativeButton("Cancel", null)
			.create()

		dialog.show()

		val etQuantity = dialogView.findViewById<EditText>(R.id.etQuantity)
		val tvSubtotal = dialogView.findViewById<TextView>(R.id.tvSubtotal)
		val tvTax = dialogView.findViewById<TextView>(R.id.tvTax)
		val tvShipping = dialogView.findViewById<TextView>(R.id.tvShipping)
		val tvTotal = dialogView.findViewById<TextView>(R.id.tvTotal)
		val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

		val clientCity = "152"  // Jakarta (ID 152), ganti sesuai kebutuhan

		// Variabel untuk simpan shipping cost terbaru
		var currentShipping = 15000.0  // Default dummy

		// Fungsi update UI
		fun updateCalculation(quantity: Int) {
			if (quantity <= 0) return

			val subtotal = product.price * quantity
			val tax = subtotal * 0.1
			val weight = quantity * 1000

			// Panggil RajaOngkir di coroutine
			lifecycleScope.launch {
				try {
					currentShipping = RajaOngkirClient.getShippingCost("23", clientCity, weight)  // Bandung (23)
				} catch (e: Exception) {
					currentShipping = 15000.0
				}

				val total = subtotal + tax + currentShipping

				tvSubtotal.text = "Subtotal: Rp ${String.format("%.2f", subtotal)}"
				tvTax.text = "Pajak (10%): Rp ${String.format("%.2f", tax)}"
				tvShipping.text = "Ongkir: Rp ${String.format("%.2f", currentShipping)} (JNE)"
				tvTotal.text = "Total: Rp ${String.format("%.2f", total)}"
			}
		}

		etQuantity.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
			override fun afterTextChanged(s: Editable?) {
				val quantity = s.toString().toIntOrNull() ?: 0
				updateCalculation(quantity)
			}
		})

		btnSubmit.setOnClickListener {
			val quantity = etQuantity.text.toString().toIntOrNull() ?: 0
			if (quantity <= 0) {
				Toast.makeText(requireContext(), "Jumlah harus lebih dari 0", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			// Gunakan currentShipping terakhir
			submitOrder(product.id, quantity, currentShipping)
			dialog.dismiss()
		}

		// Initial calculation jika quantity sudah ada
		updateCalculation(etQuantity.text.toString().toIntOrNull() ?: 0)
	}

	private fun submitOrder(productId: String, quantity: Int, shipping: Double) {
		val token = PreferencesManager(requireContext()).getToken() ?: return

		lifecycleScope.launch {
			try {
				val request = SubmitOrderRequest(
					product_id = productId, quantity = quantity, shipping_cost = shipping
				)

				val response = RetrofitClient.apiService.submitOrder(token, request)
				if (response.isSuccessful) {
					Toast.makeText(requireContext(), "Order berhasil disubmit!", Toast.LENGTH_LONG).show()
				} else {
					Toast.makeText(requireContext(), "Gagal submit order: ${response.message()}", Toast.LENGTH_SHORT).show()
				}
			} catch (e: Exception) {
				Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
			}
		}
	}
}