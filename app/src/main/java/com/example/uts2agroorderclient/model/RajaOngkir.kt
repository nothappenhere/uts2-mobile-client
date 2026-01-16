package com.example.uts2agroorderclient.model

data class RajaOngkirProvince(
	val province_id: String,
	val province: String
)

data class RajaOngkirCity(
	val city_id: String,
	val province_id: String,
	val province: String,
	val type: String,  // "Kota" atau "Kabupaten"
	val city_name: String,
	val postal_code: String
)

data class ShippingCostRequest(
	val origin: String,       // City ID asal (petani)
	val destination: String,  // City ID tujuan (client)
	val weight: Int,          // Berat dalam gram
	val courier: String       // "jne", "pos", "tiki"
)

data class RajaOngkirResult(
	val code: String,
	val name: String,
	val costs: List<RajaOngkirCost>
)

data class RajaOngkirCost(
	val service: String,
	val description: String,
	val cost: List<RajaOngkirCostDetail>
)

data class RajaOngkirCostDetail(
	val value: Int,
	val etd: String,
	val note: String
)