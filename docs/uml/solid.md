# Penerapan Prinsip SOLID pada Proyek Aset-Pinjam

Dokumen ini menjelaskan implementasi prinsip-prinsip desain berorientasi objek **SOLID** (*Single Responsibility*, *Open-Closed*, *Liskov Substitution*, *Interface Segregation*, dan *Dependency Inversion*) yang diterapkan secara nyata pada basis kode `Aset-Pinjam`.

---

## 1. Open-Closed Principle (OCP)

> *"Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification."*

### Kelas dan Antarmuka yang Terlibat:
- **Interface:** `id.ac.upb.asetpinjam.kontrak.AturanDenda`
- **Implementasi:** 
  - `id.ac.upb.asetpinjam.denda.DendaFlat`
  - `id.ac.upb.asetpinjam.denda.DendaProgresif`
- **Klien:** `id.ac.upb.asetpinjam.denda.LayananDenda`

### Alasan dan Analisis Desain:
Perhitungan denda keterlambatan tidak dikunci secara statis (*hardcoded*) di dalam hierarki kelas `Aset`. Sebaliknya, logika perhitungan denda diabstraksikan melalui interface `AturanDenda` menggunakan pola desain *Strategy Pattern*.
- **Open for Extension:** Jika di masa depan kampus menerapkan skema denda baru (misalnya denda khusus akhir semester, diskon organisasi mahasiswa, atau denda flat musiman), pengembang cukup membuat kelas baru yang mengimplementasikan `AturanDenda` tanpa mengubah satu baris pun kode yang sudah ada.
- **Closed for Modification:** Kelas inti `Aset`, kelas `Ruang`, `Laptop`, `AlatLab`, maupun kelas layanan `LayananDenda` tidak perlu dimodifikasi saat aturan denda bertambah atau berubah. Hal ini mencegah terjadinya *regression bug* pada fungsionalitas yang sudah stabil.

---

## 2. Liskov Substitution Principle (LSP)

> *"Subtypes must be substitutable for their base types without altering the correctness of the program."*

### Kelas yang Terlibat:
- **Kelas Induk (Superclass):** `id.ac.upb.asetpinjam.model.Aset` (kelas abstrak)
- **Kelas Anak (Subclasses):**
  - `id.ac.upb.asetpinjam.model.Ruang`
  - `id.ac.upb.asetpinjam.model.Laptop`
  - `id.ac.upb.asetpinjam.model.AlatLab`
- **Pemakai Referensi:** `id.ac.upb.asetpinjam.katalog.KatalogAset`, `id.ac.upb.asetpinjam.model.Peminjaman`, `id.ac.upb.asetpinjam.kontrak.AturanDenda`

### Alasan dan Analisis Desain:
Setiap turunan dari `Aset` dapat menggantikan objek referensi tipe `Aset` di bagian mana pun dalam sistem tanpa merusak konsistensi maupun memicu perilaku aneh:
1. **Kepatuhan Prakondisi (*Preconditions*):** Method `hitungDenda(int hariTerlambat)` pada semua subclass (`Ruang`, `Laptop`, `AlatLab`) memiliki prakondisi yang identik dengan kontrak kelas induknya, yaitu menerima `hariTerlambat >= 0` dan melempar `IllegalArgumentException` jika bernilai negatif. Tidak ada subclass yang memperketat aturan (misalnya melarang hari terlambat genap) atau melonggarkan validasi secara tidak terduga.
2. **Kepatuhan Pascakondisi (*Postconditions*):** Nilai denda yang dikembalikan oleh semua subclass bertipe `long` non-negatif dan konsisten dengan formula tarif varian yang berlaku.
3. **Substitusi pada Koleksi:** Pada kelas `KatalogAset`, koleksi `List<Aset>` dapat menampung campuran dari ketiga subclass tersebut, dan pemanggilan method `aset.getNama()`, `aset.getStatus()`, `aset.bisaDipinjam()`, maupun `aset.hitungDenda(hari)` bekerja secara polimorfik tanpa perlu melakukan pengecekan tipe menggunakan `instanceof` atau *downcasting*.

---

## 3. Single Responsibility Principle (SRP)

> *"A class should have one, and only one, reason to change."*

### Kelas yang Terlibat:
- `id.ac.upb.asetpinjam.model.Aset`
- `id.ac.upb.asetpinjam.katalog.KatalogAset`
- `id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman`
- `id.ac.upb.asetpinjam.model.Peminjaman`

### Alasan dan Analisis Desain:
Masing-masing kelas memiliki batas tanggung jawab tunggal yang tegas:
- **`Aset`**: Bertanggung jawab murni merepresentasikan entitas aset fisik beserta status (`StatusAset`) dan kondisi fisik (`Kondisi`). Kelas ini tidak bertanggung jawab atas pencarian di memori maupun validasi waktu jadwal peminjaman.
- **`KatalogAset`**: Bertanggung jawab khusus mengelola koleksi aset dalam memori dan algoritma pencarian substring (*case-insensitive*) serta penyaringan berdasarkan jenis aset.
- **`JadwalPeminjaman`**: Bertanggung jawab mengelola transaksi peminjaman dalam rentang waktu, memeriksa tumpang tindih waktu (*bentrok*), dan memastikan kuota peminjaman laptop tidak terlampaui.
- **`Peminjaman`**: Bertanggung jawab menyimpan status pengajuan peminjaman antara seorang `Peminjam` dengan `Aset` tertentu serta memvalidasi sah/tidaknya perpindahan status (State Lifecycle).

Pemisahan ini memastikan bahwa perubahan pada aturan pencarian katalog tidak akan memengaruhi logika penjadwalan peminjaman atau representasi fisik aset.

---

## 4. Dependency Inversion Principle (DIP)

> *"High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions."*

### Kelas dan Antarmuka yang Terlibat:
- **Abstraksi (Interface):** `id.ac.upb.asetpinjam.kontrak.AturanDenda`
- **Modul Tingkat Tinggi:** Layanan denda / pemrosesan pengembalian
- **Modul Tingkat Rendah:** `DendaFlat`, `DendaProgresif`

### Alasan dan Analisis Desain:
Modul layanan yang memerlukan kalkulasi denda tidak bergantung langsung pada implementasi konkret seperti `new DendaFlat()` atau `new DendaProgresif()`. Sebaliknya, ketergantungan diinjeksi melalui antarmuka `AturanDenda`. Desain ini memungkinkan pengujian unit yang terisolasi (*testability* tinggi) karena strategi denda dapat dengan mudah disubstitusikan dengan *mock* atau *stub* tanpa perlu instansiasi kalkulasi riil.
