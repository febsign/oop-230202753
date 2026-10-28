# Catatan Minggu 04: Polymorphism

## Yang saya kerjakan
Saya mengimplementasikan konsep polimorfisme melalui method overriding dan method overloading. Pada method overriding, saya mengimplementasikan dan meng-override method `hitungDenda(int hariTerlambat)` pada class `Aset` serta ketiga subclass-nya (`Ruang`, `Laptop`, dan `AlatLab`) dengan formula denda sesuai multiplier masing-masing (Ruang 1x, Laptop 2x, AlatLab 4x) dikalikan tarif dasar dari `Varian.aktif().dendaPerHari`, serta memvalidasi agar hari terlambat negatif melempar `IllegalArgumentException`. Pada method overloading, saya mengimplementasikan dua variasi method `cari(...)` pada class `KatalogAset`: `cari(String kata)` untuk mencari aset berdasarkan nama yang memuat kata pencarian tanpa membedakan huruf besar/kecil (*case-insensitive*), dan `cari(String kata, JenisAset jenis)` untuk menyaring hasil pencarian khusus pada jenis aset tertentu.

## Keputusan desain
Untuk method overriding `hitungDenda(int hariTerlambat)`, validasi hari negatif (`hariTerlambat < 0`) dan kalkulasi denda diterapkan langsung pada masing-masing subclass (`Ruang`, `Laptop`, `AlatLab`) menggunakan multiplier spesifiknya. Hal ini menjaga kemandirian kalkulasi setiap jenis aset dan mempermudah transisi saat class `Aset` dan method `hitungDenda` dijadikan `abstract` pada minggu ke-5.
Untuk method overloading `cari` di `KatalogAset`, method `cari(String kata, JenisAset jenis)` mendaur ulang hasil dari `cari(String kata)` kemudian menyaringnya berdasarkan `aset.getJenis() == jenis`. Desain ini mematuhi prinsip DRY (*Don't Repeat Yourself*) sehingga logika pencarian substring teks *case-insensitive* terpusat pada satu tempat.

## Kendala
Memastikan penanganan nilai `null` dan perbandingan string *case-insensitive* aman tanpa menyebabkan `NullPointerException`. Kendala ini diatasi dengan memeriksa `kata == null` di awal metode pencarian dan mengubah string ke huruf kecil (`toLowerCase()`) sebelum mencocokkan dengan `contains()`.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Implementasi method overriding `hitungDenda` pada `Aset`, `Ruang`, `Laptop`, dan `AlatLab`, implementasi overloading `cari` pada `KatalogAset`, serta pembuatan file catatan refleksi.
- Prompt utama: "baca instruksi minggu-04.md" dan "kerjakan tugas minggu-04.md".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan `Aset` tetap memiliki implementasi konkret dan validasi dasar untuk `hitungDenda` di minggu 4 ini (sebelum diubah menjadi abstrak di minggu 5) agar objek yang dibuat via `TestSupport.aset(...)` tidak menyebabkan `UnsupportedOperationException`.
- Satu hal yang saya pahami lebih baik sekarang: Perbedaan fundamental antara overloading (nama method sama dengan parameter berbeda, ditentukan saat kompilasi / *static binding*) dan overriding (definisi method sama pada subclass yang menimpa perilaku induk, ditentukan saat runtime / *dynamic dispatch*).
