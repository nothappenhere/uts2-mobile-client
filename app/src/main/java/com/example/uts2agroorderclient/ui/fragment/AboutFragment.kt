package com.example.uts2agroorderclient.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts2agroorderclient.R
import com.example.uts2agroorderclient.ui.activity.LoginActivity
import com.example.uts2agroorderclient.util.PreferencesManager

class AboutFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_about, container, false)

		val tvAbout = view.findViewById<TextView>(R.id.tvAbout)
		val btnYoutube = view.findViewById<Button>(R.id.btnYoutube)
		val btnLogout = view.findViewById<Button>(R.id.btnLogout)

		// ⚠️ LENGKAPI INFORMASI INI SESUAI TIM ANDA
		tvAbout.text = """
            🌾 AgroOrder Client App
            
            ═══════════════════════════════════
            📋 DESKRIPSI APLIKASI
            ═══════════════════════════════════
            
            AgroOrder adalah sistem pemesanan hasil tani berbasis client-admin yang menghubungkan petani dengan restoran/rumah makan.
            
            ✨ Fitur Utama:
            • Registrasi & Login Client (dengan approval admin)
            • Melihat katalog produk hasil tani
            • Pemesanan dengan perhitungan otomatis:
              - Subtotal
              - Pajak 10%
              - Ongkir real-time (RajaOngkir API)
            • Tracking status pesanan real-time
            • Riwayat pemesanan lengkap
            
            ═══════════════════════════════════
            🔌 API PUBLIK YANG DIGUNAKAN
            ═══════════════════════════════════
            
            📦 RajaOngkir API
            Fungsi: Perhitungan ongkos kirim real-time
            Website: https://rajaongkir.com
            Dokumentasi: https://rajaongkir.com/dokumentasi
            
            ═══════════════════════════════════
            💻 TEKNOLOGI
            ═══════════════════════════════════
            
            Frontend:
            • Kotlin
            • Android Studio
            • Material Design 3
            • Retrofit 2 (HTTP Client)
            • Coroutines (Asynchronous)
            
            Backend:
            • Node.js + Express.js
            • PostgreSQL Database
            • JWT Authentication
            • RESTful API
            
            ═══════════════════════════════════
            👥 TIM PENGEMBANG
            ═══════════════════════════════════
            
            152022166 - Muhammad Rizky Akbar
            152022142 - Gumiwang Maysa Nusi
            152022137 - Baraja Barsya P.
            152022169 - Erick Erlangga Putra W.
            152022144 - Luthfiansyah Putra Dean F.
            
            📅 Tahun: 2025
            🏫 Institut Teknologi Nasional Bandung
            📚 Mata Kuliah: Pemrograman Mobile
            
            ═══════════════════════════════════
            
            ⚠️ CATATAN:
            • Akun client harus di-approve admin terlebih dahulu
            • Ongkir dihitung otomatis berdasarkan jarak
            • Pastikan koneksi internet aktif
            
            Terima kasih telah menggunakan AgroOrder! 🙏
        """.trimIndent()

		// Button untuk buka video demo YouTube
		btnYoutube?.setOnClickListener {
			val youtubeUrl = "https://youtu.be/rWghOVLkuLM?si=Sy5mTpWje6UmmkkY"
			val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
			startActivity(intent)
		}

		// Button logout
		btnLogout?.setOnClickListener {
			PreferencesManager(requireContext()).clear()
			startActivity(Intent(requireContext(), LoginActivity::class.java))
			requireActivity().finish()
		}

		return view
	}
}