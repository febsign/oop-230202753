# Catatan Minggu 09: Exception Handling

## Yang saya kerjakan
Saya melengkapi dan memperkuat penanganan kesalahan pada sistem peminjaman aset dengan menerapkan *custom domain exceptions* dan menjaga konsistensi *state* objek saat operasi gagal:
1. Menyesuaikan konstruktor dan pesan pada kelas-kelas *domain exception* (`PeminjamanBentrokException`, `KuotaHabisException`, dan `StatusTidakSahException`) sehingga menghasilkan pesan deskriptif yang memuat konteks spesifik (kode aset, identitas peminjam, kuota, atau status asal dan tujuan).
2. Mengimplementasikan metode `ubahStatus(StatusPeminjaman baru)` pada kelas `Peminjaman` sesuai aturan transisi status pada `docs/aturan-bisnis.md`. Jika transisi tidak sah, sistem melempar `StatusTidakSahException` dan memastikan status objek tetap utuh (*state consistency*).
3. Mengimplementasikan metode `ajukan(Peminjaman baru)` pada kelas `JadwalPeminjaman` dengan memvalidasi potensi bentrok jadwal serta batasan kuota laptop aktif milik peminjam. Jika terdapat pelanggaran, exception terkait dilempar dan data peminjaman baru tidak ditambahkan ke jadwal.
4. Mendokumentasikan keputusan desain terkait status `DIAJUKAN`, `DITOLAK`, dan `DIBATALKAN` pada `docs/asumsi.md` (entri A-09 dan A-10), serta menuliskan tes mandiri pembuktian di `core/src/test/java/id/ac/upb/asetpinjam/mahasiswa/JadwalExceptionAsumsiTest.java`.

## Keputusan desain
1. **Perhitungan Kuota Laptop untuk Status `DIAJUKAN` (A-09)**:
   Status `DIAJUKAN` diputuskan **tidak dihitung** dalam batas kuota laptop peminjam. Kuota laptop hanya dihitung dari peminjaman yang berstatus `DISETUJUI` atau `DIPINJAM`. Hal ini karena pengajuan awal belum tentu disetujui petugas/admin. Mengunci kuota peminjam sejak tahap pengajuan akan merugikan peminjam apabila permohonannya kemudian ditolak.
2. **Pengaruh Status `DITOLAK` dan `DIBATALKAN` pada Jadwal Bentrok (A-10)**:
   Peminjaman yang berstatus `DITOLAK` atau `DIBATALKAN` diputuskan **tidak menempati jadwal** dan tidak menyebabkan bentrok untuk pengajuan peminjaman baru. Ketika suatu peminjaman dibatalkan atau ditolak, alokasi waktu pemakaian aset tersebut otomatis dilepaskan kembali agar aset dapat dimanfaatkan secara optimal oleh peminjam lain.
3. **Format Pesan Exception Domain**:
   Seluruh exception turunan `AsetPinjamException` dikonstruksi dengan pesan yang eksplisit dan informatif tanpa teks bawaan "TODO", memudahkan pelacakan kegagalan bisnis baik dalam log sistem maupun pengujian unit test.

## Kendala
Memastikan konsistensi status (*atomic state*) saat terjadi exception: jika proses validasi gagal, tidak boleh ada mutasi data parsial pada objek maupun koleksi jadwal. Kendala ini diselesaikan dengan menempatkan seluruh validasi prakondisi (cek bentrok dan cek kuota) di awal metode sebelum mutasi `daftar.add(baru)` atau `this.status = baru` dijalankan.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Pembuatan konstruktor domain exception, logika validasi mesin status `Peminjaman.ubahStatus`, pengecekan kuota dan bentrok pada `JadwalPeminjaman.ajukan`, penyusunan asumsi A-09 & A-10 di `docs/asumsi.md`, serta perancangan unit test mandiri di `JadwalExceptionAsumsiTest.java`.
- Prompt utama: "analisis minggu-09.md" dan "kerjakan tugas minggu-09.md".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan pengecekan bentrok pada `JadwalPeminjaman` mengecualikan status `DITOLAK` dan `DIBATALKAN` agar slot waktu yang telah dilepas tidak terus-menerus memblokir pengajuan baru.
- Satu hal yang saya pahami lebih baik sekarang: Pentingnya prinsip *failure atomicity*, yaitu memastikan bahwa ketika suatu operasi melempar exception, keadaan sistem tidak boleh berada dalam kondisi korup atau setengah terubah.

