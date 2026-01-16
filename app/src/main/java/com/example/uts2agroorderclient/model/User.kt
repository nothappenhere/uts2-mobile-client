package com.example.uts2agroorderclient.model

data class User(
	val id: String,
	val name: String,
	val email: String,
	val role: String,
	val approved: Boolean,
	val city_id: String? = null,  // ID kota dari RajaOngkir (e.g., "152" untuk Jakarta)
	val city_name: String? = null
)