package com.example.uts2agroorderclient.model

data class OrderSummary(
	val status: String,
	val count: Int,
	val total_spent: Double
)

data class SpendingHistory(
	val month: String,
	val order_count: Int,
	val total_spent: Double
)