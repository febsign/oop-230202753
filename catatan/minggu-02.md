# Catatan Minggu 02: Struktur OOP dan Encapsulation

## Yang saya kerjakan
Saya melengkapi class `Aset` dengan field privat untuk kode, nama, status, dan kondisi. Konstruktor memberi status awal `TERSEDIA` dan kondisi `BAIK`, sementara getter dan setter menyediakan akses yang terkontrol dengan validasi untuk nama, status, dan kondisi.

## Keputusan desain
Kode aset dibuat `final` karena identitas aset tidak boleh berubah setelah objek dibuat, dan tidak disediakan setter untuk kode. Kode serta nama yang null atau hanya whitespace ditolak, tetapi teks yang valid tidak di-trim atau diubah kapitalisasinya agar nilai yang dimasukkan tetap tersimpan persis. Dalam sistem peminjaman aset, enkapsulasi menjaga perubahan status dan kondisi melalui validasi, meskipun setter tetap memungkinkan perubahan nilai selama aplikasi berjalan.

## Kendala
Validasi perlu dilakukan sebelum nilai disimpan supaya setter yang gagal tidak merusak nilai lama. Saya menggunakan satu metode privat untuk validasi teks kode dan nama, sedangkan status dan kondisi diperiksa agar tidak menerima null.

## Penggunaan AI
- Alat yang dipakai: AI assistant menggunakan Copilot SDK di VS Code.
- Bagian yang dibantu AI: implementasi field, konstruktor, getter, setter, validasi pada `Aset`, dan penyusunan catatan; bantuan AI sekitar 50% dari pengerjaan.
- Prompt utama: "kerjakan tugas minggu-02.md dan update catatan minggu ke 2, buat di catatan ai 50%".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: saya memilih tidak menormalisasi teks dengan trim atau mengubah kapitalisasi, supaya kode dan nama tetap sama seperti masukan yang valid serta keputusan ini tercatat sesuai instruksi tugas.
- Satu hal yang saya pahami lebih baik sekarang: validasi sebelum assignment membuat field tetap menyimpan nilai yang valid jika perubahan baru ditolak.
