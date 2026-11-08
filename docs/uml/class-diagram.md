# Class Diagram - Sistem Peminjaman Aset (Aset-Pinjam)

Diagram kelas berikut memodelkan struktur kode proyek `asetpinjam` yang mencakup paket kontrak, model aset, strategi denda, katalog, dan penjadwalan.

```mermaid
classDiagram
    direction TB

    namespace id_ac_upb_asetpinjam_kontrak {
        class DapatDipinjam {
            <<interface>>
            +bisaDipinjam() bool*
        }
        class AturanDenda {
            <<interface>>
            +hitung(Aset aset, int hariTerlambat) long*
        }
    }

    namespace id_ac_upb_asetpinjam_model {
        class Aset {
            <<abstract>>
            -String kode
            -String nama
            -StatusAset status
            -Kondisi kondisi
            +Aset(String kode, String nama)
            +getKode() String
            +getNama() String
            +getStatus() StatusAset
            +getKondisi() Kondisi
            +setNama(String nama) void
            +setStatus(StatusAset status) void
            +setKondisi(Kondisi kondisi) void
            +bisaDipinjam() bool
            +getJenis()* JenisAset
            +hitungDenda(int hariTerlambat)* long
        }

        class Ruang {
            -int kapasitas
            +Ruang(String kode, String nama, int kapasitas)
            +getKapasitas() int
            +getJenis() JenisAset
            +hitungDenda(int hariTerlambat) long
        }

        class Laptop {
            -String spesifikasi
            -String nomorInventaris
            +Laptop(String kode, String nama, String spesifikasi, String nomorInventaris)
            +getSpesifikasi() String
            +getNomorInventaris() String
            +getJenis() JenisAset
            +hitungDenda(int hariTerlambat) long
        }

        class AlatLab {
            -String laboratorium
            -TingkatRisiko tingkatRisiko
            +AlatLab(String kode, String nama, String laboratorium, TingkatRisiko tingkatRisiko)
            +getLaboratorium() String
            +getTingkatRisiko() TingkatRisiko
            +getJenis() JenisAset
            +hitungDenda(int hariTerlambat) long
        }

        class Peminjam {
            <<record>>
            -String id
            -String nama
            +id() String
            +nama() String
        }

        class Peminjaman {
            -String id
            -Peminjam peminjam
            -Aset aset
            -LocalDateTime mulai
            -LocalDateTime selesai
            -StatusPeminjaman status
            +Peminjaman(String id, Peminjam peminjam, Aset aset, LocalDateTime mulai, LocalDateTime selesai)
            +getId() String
            +getPeminjam() Peminjam
            +getAset() Aset
            +getMulai() LocalDateTime
            +getSelesai() LocalDateTime
            +getStatus() StatusPeminjaman
            +setStatusDariPenyimpanan(StatusPeminjaman status) void
            +ubahStatus(StatusPeminjaman baru) void
        }

        class JenisAset {
            <<enumeration>>
            RUANG
            LAPTOP
            ALAT_LAB
        }

        class StatusAset {
            <<enumeration>>
            TERSEDIA
            DIPINJAM
            RUSAK
        }

        class Kondisi {
            <<enumeration>>
            BAIK
            RUSAK_RINGAN
            RUSAK_BERAT
        }

        class TingkatRisiko {
            <<enumeration>>
            RENDAH
            SEDANG
            TINGGI
        }

        class StatusPeminjaman {
            <<enumeration>>
            DIAJUKAN
            DISETUJUI
            DITOLAK
            DIBATALKAN
            DIPINJAM
            TERLAMBAT
            DIKEMBALIKAN
        }
    }

    namespace id_ac_upb_asetpinjam_denda {
        class DendaFlat {
            +hitung(Aset aset, int hariTerlambat) long
        }

        class DendaProgresif {
            +hitung(Aset aset, int hariTerlambat) long
        }
    }

    namespace id_ac_upb_asetpinjam_katalog {
        class KatalogAset {
            -List~Aset~ daftar
            +tambah(Aset aset) void
            +semua() List~Aset~
            +cari(String kata) List~Aset~
            +cari(String kata, JenisAset jenis) List~Aset~
        }
    }

    namespace id_ac_upb_asetpinjam_jadwal {
        class JadwalPeminjaman {
            -List~Peminjaman~ daftar
            -int kuotaLaptop
            +JadwalPeminjaman(int kuotaLaptop)
            #kuotaLaptop() int
            #daftarMutable() List~Peminjaman~
            +tambah(Peminjaman p) void
            +bentrok(Peminjaman baru) bool
            +semuaUrutMulai() List~Peminjaman~
            +perAset() Map
            +hapus(String id) bool
            +ajukan(Peminjaman baru) void
        }
    }

    DapatDipinjam <|.. Aset : implements
    Aset <|-- Ruang : extends
    Aset <|-- Laptop : extends
    Aset <|-- AlatLab : extends

    AturanDenda <|.. DendaFlat : implements
    AturanDenda <|.. DendaProgresif : implements

    Aset --> StatusAset : status
    Aset --> Kondisi : kondisi
    AlatLab --> TingkatRisiko : tingkatRisiko

    Peminjaman " * " o-- "1" Peminjam : peminjam
    Peminjaman " * " o-- "1" Aset : aset
    Peminjaman --> StatusPeminjaman : status

    KatalogAset "1" o-- "0..*" Aset : daftar
    JadwalPeminjaman "1" *-- "0..*" Peminjaman : daftar
    AturanDenda ..> Aset : relies on
```
