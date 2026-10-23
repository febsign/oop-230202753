package id.ac.upb.asetpinjam.minggu01;

import id.ac.upb.asetpinjam.config.Varian;

/** Minggu 1, gaya OOP: data dan perilaku dibungkus dalam sebuah class. */
public class HelloOOP {

    private final String nama;
    private final String nim;

    public HelloOOP(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    /** Mengembalikan sapaan yang memuat "Aset-Pinjam", nama, dan NIM. */
    public String sapa() {
        return "Hello Aset-Pinjam, saya " + nama + " (" + nim + ")";
    }

    public static void main(String[] args) {
        Varian v = Varian.aktif();
        System.out.println(new HelloOOP(v.nama, v.nim).sapa());
    }
}
