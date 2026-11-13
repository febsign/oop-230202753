# Catatan Minggu 07: Koleksi

## Yang saya kerjakan
Saya melengkapi implementasi metode pada kelas `JadwalPeminjaman` (`core/src/main/java/id/ac/upb/asetpinjam/jadwal/JadwalPeminjaman.java`), yang meliputi:
1. `bentrok(Peminjaman baru)`: Memeriksa apakah rentang waktu peminjaman baru bertabrakan dengan jadwal yang sudah ada untuk aset yang sama.
2. `semuaUrutMulai()`: Mengembalikan seluruh daftar peminjaman yang diurutkan secara menaik berdasarkan waktu mulai menggunakan `Comparator`.
3. `perAset()`: Mengelompokkan seluruh peminjaman ke dalam `Map<String, List<Peminjaman>>` berdasarkan kode aset.
4. `hapus(String id)`: Menghapus data peminjaman berdasarkan identitas `id` dan mengembalikan nilai boolean status keberhasilan penghapusan.

Selain itu, saya merumuskan keputusan batas waktu bentrok pada `docs/asumsi.md` (entri A-01) serta menuliskan unit test pengujian batas tersebut di `core/src/test/java/id/ac/upb/asetpinjam/mahasiswa/BatasBentrokTest.java`.

## Keputusan desain
1. **Batas Waktu Bentrok (`selesai == mulai`)**:
   Saya memutuskan bahwa peminjaman dengan waktu selesai yang sama persis dengan waktu mulai peminjaman lain pada aset yang sama dinyatakan **TIDAK BENTROK** (contoh: 09:00–11:00 dan 11:00–13:00). Interval waktu diperlakukan sebagai rentang setengah terbuka $[t_{mulai}, t_{selesai})$. Waktu selesai merepresentasikan batas pelepasan aset sehingga pada jam tersebut aset langsung tersedia untuk digunakan peminjam berikutnya. Hal ini memaksimalkan utilisasi penggunaan aset kampus. Logika deteksi tumpang tindih waktu dirumuskan secara elegan menggunakan `p1.getMulai().isBefore(p2.getSelesai()) && p2.getMulai().isBefore(p1.getSelesai())`.
2. **Struktur Data Internal**:
   Saya memutuskan untuk tetap menggunakan struktur `List<Peminjaman>` tunggal (`daftar`) sebagai penyimpan utama di memori dan tidak menambahkan `Map` atau `Set` sekunder sebagai atribut kelas. Hal ini dipilih untuk menjaga kesederhanaan desain (*Single Source of Truth*) dan menghindari risiko inkonsistensi data ketika operasi mutasi (`hapus` atau `tambah`) terjadi. Operasi pengelompokan `perAset()` dilakukan secara dinamis (*on-demand*) menggunakan `Collectors.groupingBy(...)`, dan `semuaUrutMulai()` memanfaatkan Stream API dengan `Comparator.comparing(Peminjaman::getMulai).thenComparing(Peminjaman::getId)` yang menghasilkan koleksi baru tanpa merusak urutan asli list internal.

## Kendala
Menentukan formula evaluasi tumpang tindih waktu yang ringkas namun mencakup seluruh variasi kemungkinan (tumpang tindih sebagian di awal/akhir, terkandung penuh, maupun terpisah jauh) serta membedakan perlakuan waktu batas berimpit. Kendala ini terselesaikan dengan menerapkan kondisi aljabar interval waktu `startA < endB && startB < endA` yang secara alamiah menangani seluruh kasus tersebut dengan tepat tanpa memerlukan *nested if* yang kompleks.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Implementasi metode `bentrok`, `semuaUrutMulai`, `perAset`, dan `hapus` pada `JadwalPeminjaman.java`, penulisan unit test pada `BatasBentrokTest.java`, penyusunan entri A-01 pada `docs/asumsi.md`, serta dokumentasi catatan refleksi ini.
- Prompt utama: "analisis minggu-07.md" dan "kerjakan minggu-07.md".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan pengurutan pada `semuaUrutMulai()` tidak melakukan pengurutan langsung di tempat (*in-place sort* pada `daftar`), melainkan membentuk salinan list baru dari stream untuk menghindari *side-effect* terhadap urutan penambahan data internal.
- Satu hal yang saya pahami lebih baik sekarang: Bagaimana Java Collections Framework bekerja secara harmonis bersama Stream API dan `Comparator` untuk mengolah data (filter, grouping, sorting) secara deklaratif, bersih, dan aman.

