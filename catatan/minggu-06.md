# Catatan Minggu 06: UML dan SOLID

## Yang saya kerjakan
Saya memodelkan sistem peminjaman aset (`Aset-Pinjam`) ke dalam Class Diagram dan Sequence Diagram yang mencerminkan struktur kode nyata proyek. Class Diagram memetakan seluruh kelas, interface, enum, atribut, method, visibilitas, tipe data, serta relasi pewarisan, implementasi, agregasi, dan multiplicity antarpaket. Sequence Diagram memodelkan alur interaksi metode `JadwalPeminjaman.ajukan(...)` dengan dua cabang interaksi menggunakan fragmen `alt`, yaitu skenario bentrok jadwal (melempar `PeminjamanBentrokException`) dan skenario pengajuan berhasil (disimpan ke daftar). Selain itu, saya menyusun dokumen `docs/uml/solid.md` yang menganalisis penerapan prinsip SOLID (khususnya OCP, LSP, SRP, dan DIP) pada basis kode proyek ini.

## Keputusan desain
Diagram disediakan dalam dua format: PlantUML (`.puml`) sebagai berkas sumber pemodelan formal dan Markdown (`.md` berbasis blok Mermaid) agar diagram dapat dirender secara visual langsung di GitHub maupun editor Markdown.
Untuk analisis SOLID, saya menyoroti Open-Closed Principle (OCP) pada abstraksi `AturanDenda` (`DendaFlat` dan `DendaProgresif`) karena logika perhitungan denda terbuka untuk penambahan strategi baru tanpa memodifikasi kelas `Aset`, serta Liskov Substitution Principle (LSP) pada hierarki `Aset` (`Ruang`, `Laptop`, `AlatLab`) yang dapat disubstitusikan ke mana pun tanpa mengubah kebenaran sistem atau melanggar prakondisi/pascakondisi kontrak induk.

## Kendala
Menjaga konsistensi menyeluruh antara representasi diagram dan implementasi kode aktual, termasuk struktur method dan relasi asosiasi. Hal ini diselesaikan dengan memverifikasi langsung setiap definisi kelas pada paket `kontrak`, `model`, `denda`, `katalog`, dan `jadwal` sebelum menuangkannya ke dalam sintaks PlantUML dan Mermaid.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Penyusunan diagram PlantUML dan Mermaid untuk Class Diagram dan Sequence Diagram, serta formulasi analisis prinsip SOLID pada `docs/uml/solid.md`.
- Prompt utama: "analisis minggu-06.md" dan "kerjakan tugas minggu-06.md".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Memastikan Sequence Diagram tidak hanya menggambarkan skenario sukses saja, melainkan memuat fragmen `alt` secara eksplisit untuk skenario bentrok jadwal dengan pelemparan `PeminjamanBentrokException` sesuai syarat pengujian dan aturan bisnis.
- Satu hal yang saya pahami lebih baik sekarang: Bagaimana prinsip OCP dan LSP saling melengkapi di mana abstraksi antarmuka/kelas abstrak memungkinkan ekstensi perilaku tanpa merusak perilaku komponen yang sudah ada.
