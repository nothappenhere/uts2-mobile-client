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
import com.example.uts2agroorderclient.util.PreferencesManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
	private lateinit var prefs: PreferencesManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		prefs = PreferencesManager(this)

		if (prefs.getToken() != null) {
			startActivity(Intent(this, MainActivity::class.java))
			finish()
			return
		}

		val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
		val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
		val btnLogin = findViewById<Button>(R.id.btnLogin)
		val tvRegister = findViewById<TextView>(R.id.tvRegister)  // Tambah TextView untuk link register

		btnLogin.setOnClickListener {
			val email = etEmail.text.toString().trim()
			val password = etPassword.text.toString()

			if (email.isEmpty() || password.isEmpty()) {
				Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			lifecycleScope.launch {
				try {
					val response = RetrofitClient.apiService.login(
						mapOf("email" to email, "password" to password)
					)
					if (response.isSuccessful) {
						val body = response.body()
						if (body?.role == "CLIENT") {
							prefs.saveToken("Bearer ${body.token}")
							startActivity(Intent(this@LoginActivity, MainActivity::class.java))
							finish()
						} else {
							Toast.makeText(this@LoginActivity, "Hanya client yang boleh login di app ini", Toast.LENGTH_SHORT).show()
						}
					} else {
						Toast.makeText(this@LoginActivity, "Login gagal atau belum di-approve", Toast.LENGTH_SHORT).show()
					}
				} catch (e: Exception) {
					Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
				}
			}
		}

		tvRegister.setOnClickListener {
			startActivity(Intent(this, RegisterActivity::class.java))
			finish()
		}
	}
}