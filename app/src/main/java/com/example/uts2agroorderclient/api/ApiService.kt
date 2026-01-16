package com.example.uts2agroorderclient.api

import com.example.uts2agroorderclient.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
	// ============== AUTH ==============
	@POST("auth/register")
	suspend fun register(@Body request: Map<String, String>): Response<ApiResponse>

	@POST("auth/login")
	suspend fun login(@Body request: Map<String, String>): Response<LoginResponse>

	// ============== PRODUCTS ==============
	@GET("products")
	suspend fun getProducts(): Response<List<Product>>  // Public, no token

	@GET("products/{id}")
	suspend fun getProductById(@Path("id") productId: String): Response<Product>

	// ============== ORDERS ==============
	@POST("orders")
	suspend fun submitOrder(
		@Header("Authorization") token: String,
		@Body order: SubmitOrderRequest
	): Response<ApiResponse>

	@GET("orders/my-orders")
	suspend fun getMyOrders(@Header("Authorization") token: String): Response<List<Order>>

	@GET("orders/{id}")
	suspend fun getOrderById(
		@Header("Authorization") token: String,
		@Path("id") orderId: String
	): Response<Order>

	// ============== RAJAONGKIR (via Backend) ==============
	@GET("rajaongkir/province")
	suspend fun getProvinces(): Response<List<RajaOngkirProvince>>

	@GET("rajaongkir/city")
	suspend fun getCities(@Query("province") provinceId: String? = null): Response<List<RajaOngkirCity>>

	@POST("rajaongkir/cost")
	suspend fun getShippingCost(@Body request: ShippingCostRequest): Response<List<RajaOngkirResult>>

	// ============== STATISTICS (untuk grafik) ==============
	@GET("statistics/my-order-summary")
	suspend fun getMyOrderSummary(@Header("Authorization") token: String): Response<List<OrderSummary>>

	@GET("statistics/my-spending-history")
	suspend fun getMySpendingHistory(@Header("Authorization") token: String): Response<List<SpendingHistory>>
}