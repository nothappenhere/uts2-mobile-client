package com.example.uts2agroorderclient.model

data class Order(
	val id: String,
	val client_id: String,
	val client_name: String? = null,
	val product_id: String,
	val product_name: String? = null,
	val quantity: Int,
	val subtotal: Double,
	val tax: Double,
	val shipping_cost: Double,
	val total_price: Double,
	val status: String,
	val created_at: String? = null
)