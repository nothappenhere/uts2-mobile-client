package com.example.uts2agroorderclient.model

data class SubmitOrderRequest(
	val product_id: String,
	val quantity: Int,
	val shipping_cost: Double
)