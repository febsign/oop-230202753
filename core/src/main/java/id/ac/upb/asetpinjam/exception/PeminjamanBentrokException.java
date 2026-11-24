package id.ac.upb.asetpinjam.exception;

/** Minggu 9: jadwal peminjaman bentrok. Pesan harus menyebut aset yang bentrok. */
public class PeminjamanBentrokException extends AsetPinjamException {

    public PeminjamanBentrokException(String kodeAset) {
        super("Peminjaman bentrok untuk aset: " + kodeAset);
    }

    public PeminjamanBentrokException() {
        super("Peminjaman bentrok pada aset");
    }
}
