# Catatan Minggu 05: Abstraction

## Yang saya kerjakan
Saya mengubah class `Aset` menjadi `abstract class` serta melengkapi implementasi method `bisaDipinjam()` dari interface `DapatDipinjam` (aset hanya dapat dipinjam jika statusnya `TERSEDIA` dan kondisinya bukan `RUSAK_BERAT`). Saya juga mengubah method `hitungDenda(int hariTerlambat)` dan `getJenis()` pada class `Aset` menjadi `abstract method` yang didelegasikan ke subclass konkret. Selain itu, saya mengimplementasikan interface `AturanDenda` pada class `DendaFlat` (didelegasikan langsung ke `aset.hitungDenda(hari)`) dan pada class `DendaProgresif` dengan skema denda berjenjang (*tiered penalty*).

## Keputusan desain
Class `Aset` dijadikan abstract class karena aset adalah konsep umum (*base concept*) yang menyimpan *state* bersama (`kode`, `nama`, `status`, `kondisi`), namun tidak masuk akal untuk diinstansiasi secara mandiri tanpa wujud spesifik seperti `Ruang`, `Laptop`, atau `AlatLab`. Sebaliknya, `DapatDipinjam` dan `AturanDenda` dirancang sebagai interface karena keduanya berfungsi sebagai kontrak perilaku (*contract of capability/strategy*) tanpa keterikatan state internal.
Untuk `DendaProgresif`, saya memutuskan menggunakan skema penalti bertingkat: hari ke-1 sampai ke-7 menggunakan tarif flat normal, sedangkan mulai hari ke-8 dan seterusnya dikenakan tambahan penalti sebesar 50% dari tarif harian flat untuk setiap hari di atas 7 hari. Keputusan ini didokumentasikan di `docs/asumsi.md` (entri A-08) karena memenuhi seluruh prasyarat: bernilai 0 pada hari ke-0, monoton naik, tidak pernah lebih murah dari flat, dan pada hari ke-30 nilainya signifikan lebih tinggi daripada flat.

## Kendala
Memastikan transisi `Aset` menjadi abstract class tidak merusak pengujian unit test dari minggu-minggu sebelumnya. Kendala ini teratasi karena kelas pembantu pengujian `TestSupport.aset(...)` telah dirancang secara adaptif menggunakan refleksi untuk otomatis beralih menggunakan subclass konkret (`Ruang`) ketika `Aset` menjadi abstract.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Mengubah `Aset` menjadi abstract class, implementasi `bisaDipinjam()`, implementasi kelas strategi `DendaFlat` dan `DendaProgresif`, penulisan asumsi desain pada `docs/asumsi.md`, serta penyusunan catatan refleksi ini.
- Prompt utama: "analisis minggu-05.md" dan "kerjakan tugas minggu_05.md".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan validasi `aset == null` dan `hariTerlambat < 0` dicek secara eksplisit di awal method `DendaProgresif.hitung` agar pesan error seragam dan mencegah potensi `NullPointerException`.
- Satu hal yang saya pahami lebih baik sekarang: Kapan menggunakan abstract class (untuk mendefinisikan fondasi hierarki dengan *state* dan *code sharing*) dan kapan menggunakan interface (untuk mendefinisikan kemampuan atau strategi perilaku yang fleksibel dan terlepas dari hierarki warisan).
