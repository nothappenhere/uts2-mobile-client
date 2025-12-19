package com.example.uts2agroorderclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts2agroorderclient.R

class AboutFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_about, container, false)
		val tvAbout = view.findViewById<TextView>(R.id.tvAbout)

		tvAbout.text = """
            AgroOrder Client App
            
            Fitur:
            • Registrasi & Login Client
            • Melihat daftar produk petani
            • Pemesanan dengan perhitungan subtotal, pajak, dan ongkir
            • Melihat status pesanan
            
            API Publik yang Digunakan (SubCPMK 4):
            • RajaOngkir API (untuk perhitungan ongkos kirim)
              Website: https://rajaongkir.com
              Dokumentasi: https://rajaongkir.com/dokumentasi
            
            Backend: Node.js + Express + PostgreSQL
            Port: 3000
            
            Dikembangkan oleh:
						• 152022166 Muhammad Rizky Akbar
						• 152022142 Gumiwang Maysa Nusi
						• 152022137 Baraja Barsya Pinandhita
						• 152022169 Erick Erlangga P. W.
						• 152022144 Luthfiansyah Putra Dean F.
            
            Terima kasih telah menggunakan AgroOrder!
        """.trimIndent()

		return view
	}
}