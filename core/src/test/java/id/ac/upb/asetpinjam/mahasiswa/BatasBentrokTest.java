package id.ac.upb.asetpinjam.mahasiswa;

import static id.ac.upb.asetpinjam.TestSupport.pinjam;
import static org.assertj.core.api.Assertions.assertThat;

import id.ac.upb.asetpinjam.TestSupport;
import id.ac.upb.asetpinjam.config.Varian;
import id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman;
import id.ac.upb.asetpinjam.model.Peminjaman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * FOLDER INI MILIK ANDA. Tulis tes Anda sendiri di sini (minggu 7 dan seterusnya).
 *
 * <p>Tes dosen sengaja tidak menentukan: apakah peminjaman 09:00-11:00 dan 11:00-13:00 pada aset yang sama
 * bentrok? Putuskan, catat alasannya di docs/asumsi.md, lalu tuliskan tesnya di sini.
 * Hapus @Disabled setelah Anda mengisinya.
 *
 * <p>Syarat minggu 10: folder ini memuat minimal 8 tes (tidak termasuk contoh bawaan ini).
 */
@Tag("week7")
class BatasBentrokTest {

    private JadwalPeminjaman jadwal;

    @BeforeEach
    void siapkan() {
        jadwal = new JadwalPeminjaman(Varian.aktif().kuotaLaptop);
    }

    @Test
    void selesaiSamaDenganMulai() {
        // Peminjaman 1: 09:00 - 11:00 pada ruang R-1
        jadwal.tambah(pinjam("1", TestSupport.peminjam("P1"), TestSupport.ruang("R-1"), 9, 11));

        // Peminjaman 2: 11:00 - 13:00 pada ruang R-1 (selesai p1 persis sama dengan mulai p2)
        Peminjaman baru = pinjam("2", TestSupport.peminjam("P2"), TestSupport.ruang("R-1"), 11, 13);

        // Berdasarkan keputusan A-01, interval [09:00, 11:00) dan [11:00, 13:00) tidak bentrok
        assertThat(jadwal.bentrok(baru)).isFalse();
    }

    @Test
    void mulaiSamaDenganSelesaiKebalikan() {
        // Peminjaman 1: 11:00 - 13:00 pada ruang R-1
        jadwal.tambah(pinjam("1", TestSupport.peminjam("P1"), TestSupport.ruang("R-1"), 11, 13));

        // Peminjaman 2: 09:00 - 11:00 pada ruang R-1 (selesai p2 persis sama dengan mulai p1)
        Peminjaman baru = pinjam("2", TestSupport.peminjam("P2"), TestSupport.ruang("R-1"), 9, 11);

        assertThat(jadwal.bentrok(baru)).isFalse();
    }
}
