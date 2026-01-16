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
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)

		val etName = findViewById<TextInputEditText>(R.id.etName)
		val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
		val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
		// ⚠️ FIX: City field dihapus karena tidak ada di database
		// val etCity = findViewById<TextInputEditText>(R.id.etCity)
		val btnRegister = findViewById<Button>(R.id.btnRegister)
		val tvLogin = findViewById<TextView>(R.id.tvLogin)

		btnRegister.setOnClickListener {
			val name = etName.text.toString().trim()
			val email = etEmail.text.toString().trim()
			val password = etPassword.text.toString().trim()

			// Validasi input
			if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
				Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (password.length < 6) {
				Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
				Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			btnRegister.isEnabled = false
			btnRegister.text = "Loading..."

			lifecycleScope.launch {
				try {
					val response = RetrofitClient.apiService.register(
						mapOf(
							"name" to name,
							"email" to email,
							"password" to password,
							"role" to "CLIENT"
							// ⚠️ FIX: city field dihapus
						)
					)

					if (response.isSuccessful) {
						Toast.makeText(
							this@RegisterActivity,
							"✅ Registrasi sukses! Tunggu approval admin untuk bisa login.",
							Toast.LENGTH_LONG
						).show()
						startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
						finish()
					} else {
						// Parse error message
						val errorBody = response.errorBody()?.string()
						val errorMessage = try {
							val json = JSONObject(errorBody ?: "{}")
							json.getString("message")
						} catch (e: Exception) {
							"Registrasi gagal"
						}

						if (errorMessage.contains("already exists", ignoreCase = true)) {
							Toast.makeText(
								this@RegisterActivity,
								"❌ Email sudah terdaftar",
								Toast.LENGTH_SHORT
							).show()
						} else {
							Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
						}
					}
				} catch (e: Exception) {
					Toast.makeText(
						this@RegisterActivity,
						"Error koneksi: ${e.message}",
						Toast.LENGTH_LONG
					).show()
				} finally {
					btnRegister.isEnabled = true
					btnRegister.text = "Register"
				}
			}
		}

		tvLogin.setOnClickListener {
			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}
	}
}

/* ⚠️ CATATAN UNTUK LAYOUT:
 * Hapus EditText untuk City dari activity_register.xml:
 *
 * HAPUS BAGIAN INI:
 * <com.google.android.material.textfield.TextInputLayout
 *     android:id="@+id/tilCity"
 *     ...>
 *     <com.google.android.material.textfield.TextInputEditText
 *         android:id="@+id/etCity"
 *         .../>
 * </com.google.android.material.textfield.TextInputLayout>
 */