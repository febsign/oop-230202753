package id.ac.upb.asetpinjam.jadwal;

import id.ac.upb.asetpinjam.model.Peminjaman;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Jadwal peminjaman di memori.
 *
 * <p>Minggu 7: bentrok, semuaUrutMulai, perAset, hapus. Minggu 9: ajukan (melempar exception).
 * tambah() DISEDIAKAN dan sengaja TIDAK memeriksa apa pun.
 */
public class JadwalPeminjaman {

    private final List<Peminjaman> daftar = new ArrayList<>();
    private final int kuotaLaptop;

    public JadwalPeminjaman(int kuotaLaptop) {
        this.kuotaLaptop = kuotaLaptop;
    }

    protected int kuotaLaptop() {
        return kuotaLaptop;
    }

    protected List<Peminjaman> daftarMutable() {
        return daftar;
    }

    /** Menambah apa adanya tanpa pemeriksaan. */
    public void tambah(Peminjaman p) {
        daftar.add(p);
    }

    /**
     * TODO minggu 7: true jika {@code baru} bentrok dengan peminjaman yang sudah ada untuk ASET YANG SAMA.
     * Definisi "bentrok" di batas waktu (selesai sama dengan mulai) adalah KEPUTUSAN ANDA (docs/asumsi.md).
     */
    public boolean bentrok(Peminjaman baru) {
        if (baru == null) {
            return false;
        }
        return daftar.stream()
                .filter(lama -> lama.getAset().getKode().equals(baru.getAset().getKode()))
                .anyMatch(lama -> isTumpangTindih(lama, baru));
    }

    private boolean isTumpangTindih(Peminjaman p1, Peminjaman p2) {
        // Model interval setengah terbuka [mulai, selesai):
        // Bentrok jika p1.mulai < p2.selesai DAN p2.mulai < p1.selesai.
        // Jika p1.selesai == p2.mulai atau sebaliknya, tidak bentrok.
        return p1.getMulai().isBefore(p2.getSelesai()) && p2.getMulai().isBefore(p1.getSelesai());
    }

    /** TODO minggu 7: semua peminjaman, diurutkan menurut waktu mulai (gunakan Comparator). */
    public List<Peminjaman> semuaUrutMulai() {
        return daftar.stream()
                .sorted(Comparator.comparing(Peminjaman::getMulai).thenComparing(Peminjaman::getId))
                .collect(Collectors.toList());
    }

    /** TODO minggu 7: peminjaman dikelompokkan menurut kode aset. */
    public Map<String, List<Peminjaman>> perAset() {
        return daftar.stream()
                .collect(Collectors.groupingBy(p -> p.getAset().getKode()));
    }

    /** TODO minggu 7: hapus peminjaman berdasarkan id. True jika ada yang terhapus. */
    public boolean hapus(String id) {
        if (id == null) {
            return false;
        }
        return daftar.removeIf(p -> id.equals(p.getId()));
    }

    /**
     * TODO minggu 9: periksa lalu tambahkan.
     * Bentrok: PeminjamanBentrokException. Peminjam sudah memegang {@link #kuotaLaptop()} laptop yang
     * aktif (status DISETUJUI atau DIPINJAM) dan mengajukan laptop lagi: KuotaHabisException.
     */
    public void ajukan(Peminjaman baru) {
        throw new UnsupportedOperationException("TODO minggu 9: ajukan");
    }
}
