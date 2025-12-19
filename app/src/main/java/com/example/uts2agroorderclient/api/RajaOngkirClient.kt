package com.example.uts2agroorderclient.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RajaOngkirClient {
	private const val BASE_URL = "https://api.rajaongkir.com/"
	private const val API_KEY = "your_api_key"  // Ganti dengan API key Anda!

	val rajaOngkirService: RajaOngkirService by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(RajaOngkirService::class.java)
	}

	suspend fun getShippingCost(origin: String, destination: String, weight: Int): Double {
		return try {
			val response = rajaOngkirService.getCost(API_KEY, origin, destination, weight, "jne")
			if (response.isSuccessful) {
				val results = response.body()?.get("rajaongkir") as? Map<*, *>
				val costs = results?.get("results") as? List<*>
				val firstCost = costs?.get(0) as? Map<*, *>
				val costList = firstCost?.get("costs") as? List<*>
				val serviceCost = costList?.get(0) as? Map<*, *>
				val value = serviceCost?.get("cost") as? List<*>
				val finalValue = value?.get(0) as? Map<*, *>
				(finalValue?.get("value") as? Double) ?: 0.0
			} else {
				15000.0  // Fallback dummy
			}
		} catch (e: Exception) {
			15000.0  // Fallback jika error
		}
	}
}