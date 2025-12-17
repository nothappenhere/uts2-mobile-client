package com.example.uts2agroorderclient.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RajaOngkirService {
	@POST("starter/cost")
	@FormUrlEncoded
	suspend fun getCost(
		@Header("key") apiKey: String,
		@Field("origin") origin: String,      // City ID petani, e.g., "23" untuk Bandung
		@Field("destination") destination: String,  // City ID client, e.g., "152" untuk Jakarta
		@Field("weight") weight: Int,         // Gram
		@Field("courier") courier: String     // "jne"
	): Response<Map<String, Any>>  // { "rajaongkir": { "results": [...] } }
}