# AgroOrder Client App

Aplikasi mobile Android untuk **Client (Restoran/Rumah Makan)** pada sistem pemesanan hasil tani "AgroOrder". Aplikasi ini dibangun dengan **Kotlin** dan **Android Studio** menggunakan arsitektur View System (XML Layout + Fragments).

## Deskripsi Aplikasi
AgroOrder adalah sistem pemesanan hasil tani berbasis client-admin. Aplikasi Client digunakan oleh restoran/rumah makan untuk:
- Registrasi akun (menunggu approval admin).
- Login (hanya jika sudah di-approve).
- Melihat daftar produk hasil tani (sayur, buah, beras, dll).
- Melakukan pemesanan dengan perhitungan otomatis:  
  **Subtotal + Pajak (10%) + Ongkir (real dari API RajaOngkir)**.
- Melihat status pesanan sendiri.

## Fitur Utama
- Registrasi & Login Client
- Daftar produk petani
- Pemesanan dengan dialog perhitungan real-time
- Lihat riwayat pesanan (My Orders)
- Integrasi **RajaOngkir API** untuk ongkos kirim berdasarkan kota
- Halaman About dengan informasi API publik yang digunakan

## Teknologi yang Digunakan
- **Bahasa**: Kotlin
- **IDE**: Android Studio
- **Arsitektur**: MVVM sederhana + Coroutines + Lifecycle
- **Networking**: Retrofit 2 + Gson
- **Database/Backend**: Node.js + Express + PostgreSQL (terpisah)
- **Authentication**: JWT Token
- **API Publik**: RajaOngkir (perhitungan ongkir real)
- **UI**: Material Design

## Cara Menjalankan
1. Clone repository ini.
2. Ganti `API_KEY` di `RajaOngkirClient.kt` dengan API key RajaOngkir Anda.
3. Pastikan backend Node.js berjalan di `http://IP_LOKAL:3000` (ganti di `RetrofitClient.kt`).
4. Allow cleartext HTTP di `AndroidManifest.xml` atau gunakan network security config.
5. Build dan jalankan di emulator/real device.

## API Publik yang Digunakan
- **RajaOngkir API** (perhitungan ongkos kirim)  
  Website: [https://rajaongkir.com](https://rajaongkir.com)  

## Pengembang
- 152022166 - Muhammad Rizky Akbar
- 152022142 - Gumiwang Maysa Nusi
- 152022137 - Baraja Barsya P.
- 152022169 - Erick Erlangga Putra W.
- 152022144 - Luthfiansyah Putra Dean F.
