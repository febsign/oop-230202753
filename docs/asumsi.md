# Asumsi dan Keputusan Desain

Isi bagian ini sendiri. Satu entri per keputusan. Dinilai dari kejelasan alasan dan konsistensi dengan kode dan tes Anda,
bukan dari jawaban "benar".

Format tiap entri:

```
## A-NN: judul singkat
- Pertanyaan: ...
- Keputusan: ...
- Alasan: ...
- Dampak ke kode: ... (kelas/metode)
- Tes yang membuktikan: ... (nama tes di folder mahasiswa/)
```

## Aturan ambigu dari docs/aturan-bisnis.md

Salin setiap pertanyaan ambigu (nomor 1 sampai 7) ke sini dan jawab. Contoh:

## A-01: batas waktu bentrok
- Pertanyaan: 09:00-11:00 dan 11:00-13:00 pada aset yang sama bentrok atau tidak?
- Keputusan: (isi)
- Alasan: (isi)
- Dampak ke kode: (isi)
- Tes yang membuktikan: (isi)

## Asumsi lain

Tambahkan di sini keputusan lain yang Anda ambil: rumus denda progresif, perubahan skema database, format error API, dan seterusnya.

## A-08: Rumus Denda Progresif (Minggu 5)
- Pertanyaan: Bagaimana rumus denda progresif untuk keterlambatan pengembalian aset?
- Keputusan: Menggunakan skema tarif berjenjang (tiered penalty). Keterlambatan hari ke-1 sampai ke-7 menggunakan tarif flat normal. Mulai hari ke-8 dan seterusnya, setiap hari keterlambatan setelah hari ke-7 dikenakan tambahan biaya penalti sebesar 50% dari tarif harian flat aset:
  `Denda = dendaFlat + (hariTerlambat - 7) * (tarifHarian / 2)`
- Alasan: Memberikan masa toleransi satu minggu pertama dengan tarif standar, namun memberikan disinsentif tambahan yang semakin memberatkan jika aset tertahan lebih dari satu minggu. Rumus ini memenuhi seluruh prasyarat: bernilai 0 pada hari ke-0, tidak pernah lebih murah dari tarif flat, monoton naik (tidak pernah turun saat hari bertambah), dan pada hari ke-30 nilainya signifikan lebih tinggi daripada tarif flat.
- Dampak ke kode: `id.ac.upb.asetpinjam.denda.DendaProgresif.hitung(Aset, int)`.
- Tes yang membuktikan: `id.ac.upb.asetpinjam.Minggu05AbstraksiTest.dendaProgresifMemenuhiSyaratMinimal`.

