package id.ac.upb.asetpinjam.model;

import id.ac.upb.asetpinjam.exception.StatusTidakSahException;
import java.time.LocalDateTime;

/**
 * Satu pengajuan peminjaman aset oleh seorang peminjam pada rentang waktu tertentu.
 * Konstruktor dan getter DISEDIAKAN dosen. Anda mengerjakan ubahStatus (minggu 9).
 */
public class Peminjaman {

    private final String id;
    private final Peminjam peminjam;
    private final Aset aset;
    private final LocalDateTime mulai;
    private final LocalDateTime selesai;
    private StatusPeminjaman status = StatusPeminjaman.DIAJUKAN;

    public Peminjaman(String id, Peminjam peminjam, Aset aset, LocalDateTime mulai, LocalDateTime selesai) {
        if (id == null || id.isBlank() || peminjam == null || aset == null || mulai == null || selesai == null) {
            throw new IllegalArgumentException("semua data peminjaman wajib diisi");
        }
        if (!selesai.isAfter(mulai)) {
            throw new IllegalArgumentException("waktu selesai harus setelah waktu mulai");
        }
        this.id = id;
        this.peminjam = peminjam;
        this.aset = aset;
        this.mulai = mulai;
        this.selesai = selesai;
    }

    public String getId() {
        return id;
    }

    public Peminjam getPeminjam() {
        return peminjam;
    }

    public Aset getAset() {
        return aset;
    }

    public LocalDateTime getMulai() {
        return mulai;
    }

    public LocalDateTime getSelesai() {
        return selesai;
    }

    public StatusPeminjaman getStatus() {
        return status;
    }

    /** Dipakai DAO saat memuat data dari database. Tidak memvalidasi perpindahan. */
    public void setStatusDariPenyimpanan(StatusPeminjaman status) {
        this.status = status;
    }

    /**
     * Minggu 9: pindahkan ke status baru hanya jika perpindahan sah menurut
     * docs/aturan-bisnis.md. Jika tidak sah, lempar StatusTidakSahException (dengan pesan jelas).
     */
    public void ubahStatus(StatusPeminjaman baru) {
        if (baru == null || !isPerpindahanSah(this.status, baru)) {
            throw new StatusTidakSahException(this.status, baru);
        }
        this.status = baru;
    }

    private boolean isPerpindahanSah(StatusPeminjaman asal, StatusPeminjaman tujuan) {
        return switch (asal) {
            case DIAJUKAN -> tujuan == StatusPeminjaman.DISETUJUI || tujuan == StatusPeminjaman.DITOLAK;
            case DISETUJUI -> tujuan == StatusPeminjaman.DIPINJAM || tujuan == StatusPeminjaman.DIBATALKAN;
            case DIPINJAM -> tujuan == StatusPeminjaman.DIKEMBALIKAN || tujuan == StatusPeminjaman.TERLAMBAT;
            case TERLAMBAT -> tujuan == StatusPeminjaman.DIKEMBALIKAN;
            case DITOLAK, DIBATALKAN, DIKEMBALIKAN -> false;
        };
    }
}
