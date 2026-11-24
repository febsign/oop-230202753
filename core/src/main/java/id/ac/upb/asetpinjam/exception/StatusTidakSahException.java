package id.ac.upb.asetpinjam.exception;

import id.ac.upb.asetpinjam.model.StatusPeminjaman;

/** Minggu 9: perpindahan status peminjaman tidak sah. Pesan harus menyebut status asal dan tujuan. */
public class StatusTidakSahException extends AsetPinjamException {

    public StatusTidakSahException(StatusPeminjaman asal, StatusPeminjaman tujuan) {
        super("Perpindahan status tidak sah dari " + asal + " ke " + tujuan);
    }

    public StatusTidakSahException(String pesan) {
        super(pesan);
    }

    public StatusTidakSahException() {
        super("Perpindahan status peminjaman tidak sah");
    }
}
