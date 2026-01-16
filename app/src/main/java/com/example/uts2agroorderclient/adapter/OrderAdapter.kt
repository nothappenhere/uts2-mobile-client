package com.example.uts2agroorderclient.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter : ListAdapter<Order, OrderAdapter.ViewHolder>(OrderDiffCallback()) {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val tvProduct: TextView = view.findViewById(R.id.tvProduct)
		val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
		val tvTotal: TextView = view.findViewById(R.id.tvTotal)
		val tvCurrentStatus: TextView = view.findViewById(R.id.tvCurrentStatus)
		val tvDate: TextView = view.findViewById(R.id.tvDate)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_order_client, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val order = getItem(position)
		val context = holder.itemView.context

		holder.tvProduct.text = "Produk: ${order.product_name ?: "Unknown"}"
		holder.tvQuantity.text = "Jumlah: ${order.quantity} kg"
		holder.tvTotal.text = "Rp ${String.format("%,.0f", order.total_price)}"

		// Format tanggal
		val date = try {
			val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
			val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
			val parsedDate = inputFormat.parse(order.created_at ?: "")
			outputFormat.format(parsedDate ?: Date())
		} catch (e: Exception) {
			"N/A"
		}
		holder.tvDate.text = date

		// SET STATUS COLOR BERDASARKAN STATUS ORDER
		holder.tvCurrentStatus.text = order.status.uppercase()
		val statusColor = when (order.status.uppercase()) {
			"PENDING" -> ContextCompat.getColor(context, R.color.status_pending)
			"APPROVED" -> ContextCompat.getColor(context, R.color.status_approved)
			"SHIPPED" -> ContextCompat.getColor(context, R.color.status_shipped)
			"DELIVERED" -> ContextCompat.getColor(context, R.color.status_delivered)
			"REJECTED" -> ContextCompat.getColor(context, R.color.status_rejected)
			else -> ContextCompat.getColor(context, R.color.gray_medium)
		}

		holder.tvCurrentStatus.setBackgroundColor(statusColor)
		holder.tvCurrentStatus.setTextColor(Color.WHITE)
	}

	class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
		override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
			oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
			oldItem == newItem
	}
}