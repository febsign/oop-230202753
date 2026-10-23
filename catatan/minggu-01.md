# Catatan Minggu 01: Paradigma Pemrograman dan Setup

## Yang saya kerjakan
Saya membuat tiga cara menghasilkan sapaan untuk aplikasi Aset-Pinjam: prosedural melalui metode statis, OOP melalui objek dengan field privat, dan fungsional melalui lambda. Ketiganya menghasilkan teks yang sama dan menggunakan nama serta NIM dari konfigurasi mahasiswa.

## Keputusan desain
Saya memakai format `Hello Aset-Pinjam, saya [Favian Rizky Febriansyah] ([230202753])` agar ketiga pendekatan jelas menghasilkan isi yang identik. Pendekatan prosedural sederhana untuk operasi kecil, tetapi data dan perilaku tidak dikelompokkan dalam objek. OOP mengelompokkan nama dan NIM dengan perilaku sapaan, tetapi menambah kebutuhan membuat objek. Pendekatan fungsional memungkinkan pola sapaan dibuat sebagai fungsi yang menerima nama, tetapi pemahaman alur fungsi sebagai nilai mungkin kurang langsung bagi pembaca yang belum terbiasa dengan lambda.

## Kendala
Perbedaan utamanya adalah cara setiap paradigma menyimpan atau menerima data: parameter metode, field objek, atau fungsi yang dikembalikan. Saya menyamakan format teksnya dan membandingkan hasilnya melalui tes agar perbedaan implementasi tidak mengubah keluaran.

## Penggunaan AI
- Alat yang dipakai: AI assistant menggunakan Copilot SDK di VS Code.
- Bagian yang dibantu AI: implementasi metode sapaan di `HelloProsedural`, `HelloOOP`, dan `HelloFungsional`, serta penyusunan catatan; bantuan AI sekitar 50% dari pengerjaan.
- Prompt utama: "kerjakan tugas minggu 1.md dan update catatan minggu ke 1, buat di catatan ai 50%".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: belum ada saran yang saya ubah atau tolak secara terpisah; format sapaan dibuat sama di ketiga gaya agar sesuai dengan ketentuan tes.
- Satu hal yang saya pahami lebih baik sekarang: lambda dapat dikembalikan sebagai nilai fungsi, dan fungsi itu dapat menerima nama lalu membentuk sapaan dengan NIM yang sudah ditangkap.
