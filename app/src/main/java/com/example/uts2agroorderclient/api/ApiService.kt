package com.example.uts2agroorderclient.api

import com.example.uts2agroorderclient.model.ApiResponse
import com.example.uts2agroorderclient.model.LoginResponse
import com.example.uts2agroorderclient.model.Order
import com.example.uts2agroorderclient.model.Product
import com.example.uts2agroorderclient.model.SubmitOrderRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
	@POST("auth/register")
	suspend fun register(@Body request: Map<String, String>): Response<ApiResponse>

	@POST("auth/login")
	suspend fun login(@Body request: Map<String, String>): Response<LoginResponse>

	@GET("products")
	suspend fun getProducts(): Response<List<Product>>  // Public, no token

	@POST("orders")
	suspend fun submitOrder(
		@Header("Authorization") token: String,
		@Body order: SubmitOrderRequest
	): Response<ApiResponse>

	@GET("orders")  // Get all orders for client
	suspend fun getMyOrders(@Header("Authorization") token: String): Response<List<Order>>
}