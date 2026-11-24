package id.ac.upb.asetpinjam.exception;

/** Minggu 9: kuota peminjaman peminjam sudah habis. Pesan harus menyebut peminjam dan kuotanya. */
public class KuotaHabisException extends AsetPinjamException {

    public KuotaHabisException(String idPeminjam, int kuota) {
        super("Kuota laptop habis untuk peminjam " + idPeminjam + " (kuota: " + kuota + ")");
    }

    public KuotaHabisException(String pesan) {
        super(pesan);
    }

    public KuotaHabisException() {
        super("Kuota peminjaman laptop sudah habis");
    }
}
