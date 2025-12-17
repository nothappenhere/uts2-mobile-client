package com.example.uts2agroorderclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.model.Order

class MyOrderAdapter : ListAdapter<Order, MyOrderAdapter.ViewHolder>(OrderDiffCallback()) {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val tvProduct: TextView = view.findViewById(R.id.tvProductName)
		val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
		val tvTotal: TextView = view.findViewById(R.id.tvTotal)
		val tvStatus: TextView = view.findViewById(R.id.tvStatus)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_my_order, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val order = getItem(position)
		holder.tvProduct.text = "Produk: ${order.product_name ?: "Unknown"}"
		holder.tvQuantity.text = "Jumlah: ${order.quantity}"
		holder.tvTotal.text = "Total: Rp ${String.format("%.2f", order.total_price)}"
		holder.tvStatus.text = "Status: ${order.status.uppercase()}"
	}

	class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
		override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
			oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
			oldItem == newItem
	}
}