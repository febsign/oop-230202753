# Catatan Minggu 03: Inheritance

## Yang saya kerjakan
Saya mengimplementasikan pewarisan (inheritance) dari class induk `Aset` ke tiga subclass: `Ruang`, `Laptop`, dan `AlatLab`. Pada setiap subclass, saya melengkapi konstruktor yang memanggil `super(kode, nama)`, menambahkan validasi khusus pada atribut spesifik subclass, menyediakan getter, serta meng-override method `getJenis()` untuk mengembalikan tipe aset yang sesuai (`JenisAset.RUANG`, `JenisAset.LAPTOP`, dan `JenisAset.ALAT_LAB`).

## Keputusan desain
Atribut umum seperti `kode`, `nama`, `status`, dan `kondisi` diletakkan di class induk `Aset` karena semua aset yang dapat dipinjam pasti memiliki identitas dan status tersebut. Sebaliknya, atribut khusus hanya ditempatkan pada subclass yang relevan: `kapasitas` hanya pada `Ruang`, `spesifikasi` dan `nomorInventaris` hanya pada `Laptop`, serta `laboratorium` dan `tingkatRisiko` hanya pada `AlatLab`. Konstruktor subclass memanfaatkan validasi warisan dari `super` untuk kode dan nama, lalu menambahkan validasi spesifik seperti kapasitas > 0, teks tidak kosong, dan tingkat risiko tidak null.

Contoh di mana inheritance kurang tepat dibanding komposisi pada sistem ini: jika nantinya ada aset hibrida—seperti alat laboratorium yang dilengkapi laptop terintegrasi (*Laptop Lab*) atau laboratorium yang memiliki kapasitas sekaligus tingkat risiko keselamatan—pewarisan tunggal (single inheritance) di Java tidak mampu menangani kelas yang mewarisi beberapa kategori tersebut sekaligus tanpa menciptakan hierarki yang rumit dan menduplikasi kode. Dalam kasus ini, komposisi (composition) jauh lebih fleksibel karena sebuah aset dapat memiliki atribut/perilaku spesifik melalui objek komponen (misalnya komponen `SpesifikasiPerangkat`, `RisikoLab`, atau strategi denda) daripada terikat hierarki pewarisan yang kaku.

## Kendala
Memastikan urutan validasi berjalan tepat bersama kode warisan. Di Java, pemanggilan `super(kode, nama)` harus berada pada baris pertama konstruktor subclass, sehingga validasi field induk otomatis dijalankan terlebih dahulu sebelum pemeriksaan field unik subclass.

## Penggunaan AI
- Alat yang dipakai: AI coding assistant (Antigravity).
- Bagian yang dibantu AI: Implementasi subclass `Ruang`, `Laptop`, `AlatLab` (konstruktor, validasi argumen, getter, override `getJenis()`), serta penyusunan catatan refleksi inheritance vs komposisi.
- Prompt utama: "baca file minggu 03 .md" dan "kerjakan sesui instruksi".
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Menjaga field khusus subclass tetap bersifat `private final` tanpa menambahkan setter yang tidak dibutuhkan, guna menjaga imutabilitas data spesifikasi dan kapasitas sesuai prinsip enkapsulasi.
- Satu hal yang saya pahami lebih baik sekarang: Pewarisan sangat baik untuk hubungan *is-a* murni dengan pemakaian ulang kode induk, tetapi memiliki keterbatasan dalam fleksibilitas penggabungan ragam varian sifat dibanding pendekatan komposisi (*has-a*).
