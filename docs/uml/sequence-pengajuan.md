# Sequence Diagram - Pengajuan Peminjaman

Diagram interaksi berikut memodelkan alur proses pengajuan peminjaman aset pada sistem `Aset-Pinjam`, yang mencakup skenario bentrok jadwal dan skenario pengajuan berhasil menggunakan fragmen alternatif (`alt`).

```mermaid
sequenceDiagram
    autonumber
    actor P as Peminjam
    participant J as jadwal: JadwalPeminjaman
    participant B as baru: Peminjaman
    participant D as daftar: List<Peminjaman>

    P->>J: ajukan(baru)
    activate J

    J->>B: getAset()
    activate B
    B-->>J: aset
    deactivate B

    J->>J: bentrok(baru)
    Note over J: Cek tumpang tindih waktu pada aset yang sama

    alt isBentrok == true (Skenario Bentrok Jadwal)
        Note over P, J: Kasus 1: Waktu peminjaman bertabrakan
        J-->>P: throw PeminjamanBentrokException
        Note over J, D: Peminjaman ditolak & TIDAK dimasukkan ke daftar
    else isBentrok == false (Skenario Sukses)
        Note over P, J: Kasus 2: Jadwal aset tersedia
        opt aset adalah Laptop
            J->>J: validasi kuotaLaptop peminjam
        end
        J->>D: add(baru)
        activate D
        D-->>J: true
        deactivate D
        J-->>P: return void (Sukses)
        Note over P, J: Peminjaman berhasil dicatat dengan status DIAJUKAN
    end

    deactivate J
```
