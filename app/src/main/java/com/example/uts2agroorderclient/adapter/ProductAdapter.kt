package com.example.uts2agroorderclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.model.Product

class ProductAdapter(private val onItemClick: (Product) -> Unit) :
	ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback()) {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val tvName: TextView = view.findViewById(R.id.tvProductName)
		val tvPrice: TextView = view.findViewById(R.id.tvPrice)
		val tvUnit: TextView = view.findViewById(R.id.tvUnit)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_product, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val product = getItem(position)
		holder.tvName.text = product.name
		holder.tvPrice.text = "Rp ${String.format("%.2f", product.price)}"
		holder.tvUnit.text = product.unit ?: "unit"
		holder.itemView.setOnClickListener { onItemClick(product) }
	}

	class DiffCallback : DiffUtil.ItemCallback<Product>() {
		override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
		override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
	}
}