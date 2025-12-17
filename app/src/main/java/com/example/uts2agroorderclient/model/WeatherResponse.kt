package com.example.uts2agroorderclient.model

data class WeatherResponse(
	val city: String,
	val temperature: Double,
	val weather: String
)