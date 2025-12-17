package com.example.uts2agroorderclient.model

data class LoginResponse(
	val token: String,
	val role: String,
	val userId: String
)