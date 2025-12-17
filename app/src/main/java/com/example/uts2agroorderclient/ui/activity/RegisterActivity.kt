package com.example.uts2agroorderclient.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.api.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)

		val etName = findViewById<TextInputEditText>(R.id.etName)
		val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
		val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
		val etCity = findViewById<TextInputEditText>(R.id.etCity)
		val btnRegister = findViewById<Button>(R.id.btnRegister)
		val tvLogin = findViewById<TextView>(R.id.tvLogin)

		btnRegister.setOnClickListener {
			val name = etName.text.toString().trim()
			val email = etEmail.text.toString().trim()
			val password = etPassword.text.toString().trim()
			val city = etCity.text.toString().trim()

			if (name.isEmpty() || email.isEmpty() || password.isEmpty() || city.isEmpty()) {
				Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			lifecycleScope.launch {
				try {
					val response = RetrofitClient.apiService.register(
						mapOf(
							"name" to name,
							"email" to email,
							"password" to password,
							"role" to "CLIENT",
							"city" to city
						)
					)
					if (response.isSuccessful) {
						Toast.makeText(this@RegisterActivity, "Registrasi sukses, tunggu approval admin", Toast.LENGTH_LONG).show()
						startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
						finish()
					} else {
						Toast.makeText(this@RegisterActivity, "Registrasi gagal", Toast.LENGTH_SHORT).show()
					}
				} catch (e: Exception) {
					Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
				}
			}
		}

		tvLogin.setOnClickListener {
			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}
	}
}