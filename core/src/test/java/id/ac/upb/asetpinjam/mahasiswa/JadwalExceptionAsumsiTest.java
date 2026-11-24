package id.ac.upb.asetpinjam.mahasiswa;

import static id.ac.upb.asetpinjam.TestSupport.pinjam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import id.ac.upb.asetpinjam.TestSupport;
import id.ac.upb.asetpinjam.config.Varian;
import id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman;
import id.ac.upb.asetpinjam.model.Peminjam;
import id.ac.upb.asetpinjam.model.Peminjaman;
import id.ac.upb.asetpinjam.model.StatusPeminjaman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Uji mandiri keputusan desain minggu 9 (asumsi A-09 dan A-10 di docs/asumsi.md).
 */
@Tag("week9")
class JadwalExceptionAsumsiTest {

    private JadwalPeminjaman jadwal;
    private int kuota;

    @BeforeEach
    void siapkan() {
        kuota = Varian.aktif().kuotaLaptop;
        jadwal = new JadwalPeminjaman(kuota);
    }

    @Test
    void diajukanTidakDihitungDalamKuotaLaptop() {
        Peminjam budi = TestSupport.peminjam("P-BUDI");

        // Peminjam mengajukan peminjaman laptop pertama (status DIAJUKAN)
        Peminjaman p1 = pinjam("L1", budi, TestSupport.laptop("LAP-1"), 8, 10);
        jadwal.tambah(p1);

        // Karena status p1 masih DIAJUKAN (belum DISETUJUI / DIPINJAM), kuota aktif belum berkurang.
        // Peminjam masih dapat meminjam laptop lain hingga batas kuota aktif tercapai.
        for (int i = 1; i <= kuota; i++) {
            Peminjaman p = pinjam("L-SETUJU-" + i, budi, TestSupport.laptop("LAP-SETUJU-" + i), 13, 15);
            p.ubahStatus(StatusPeminjaman.DISETUJUI);
            jadwal.tambah(p);
        }

        assertThat(p1.getStatus()).isEqualTo(StatusPeminjaman.DIAJUKAN);
    }

    @Test
    void peminjamanDitolakAtauDibatalkanTidakMenempatiJadwal() {
        // Peminjaman 1: 09:00 - 11:00 pada ruang R-1, kemudian ditolak
        Peminjaman p1 = pinjam("1", TestSupport.peminjam("P1"), TestSupport.ruang("R-1"), 9, 11);
        p1.ubahStatus(StatusPeminjaman.DITOLAK);
        jadwal.tambah(p1);

        // Peminjaman 2: 09:00 - 11:00 pada ruang R-1 oleh peminjam lain.
        // Karena p1 berstatus DITOLAK, aset tidak lagi terhalang dan peminjaman baru tidak bentrok.
        Peminjaman p2 = pinjam("2", TestSupport.peminjam("P2"), TestSupport.ruang("R-1"), 9, 11);
        assertThat(jadwal.bentrok(p2)).isFalse();
        assertThatCode(() -> jadwal.ajukan(p2)).doesNotThrowAnyException();
    }

    @Test
    void peminjamanDibatalkanTidakMenempatiJadwal() {
        // Peminjaman 1: disetujui lalu dibatalkan
        Peminjaman p1 = pinjam("1", TestSupport.peminjam("P1"), TestSupport.ruang("R-1"), 13, 15);
        p1.ubahStatus(StatusPeminjaman.DISETUJUI);
        p1.ubahStatus(StatusPeminjaman.DIBATALKAN);
        jadwal.tambah(p1);

        // Peminjaman 2: waktu sama pada ruang R-1 tidak bentrok
        Peminjaman p2 = pinjam("2", TestSupport.peminjam("P2"), TestSupport.ruang("R-1"), 13, 15);
        assertThat(jadwal.bentrok(p2)).isFalse();
        assertThatCode(() -> jadwal.ajukan(p2)).doesNotThrowAnyException();
    }
}

