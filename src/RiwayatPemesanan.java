import java.util.ArrayList;

public class RiwayatPemesanan {
    private ArrayList<Pemesanan> daftarPemesanan;

    public RiwayatPemesanan() {
        daftarPemesanan = new ArrayList<>();
    }

    public void tambahPemesanan(Pemesanan pemesanan) {
        daftarPemesanan.add(pemesanan);
    }

    public void tampilkanRiwayat(User user) {
        System.out.println("\nRiwayat Pemesanan");

        int jumlahPemesanan = 0;

        for (Pemesanan p : daftarPemesanan) {
            if (p.getUser() == user) {
                jumlahPemesanan++;
            }
        }

        if (jumlahPemesanan == 0) {
            System.out.println("Belum ada riwayat pemesanan.");
        }
        else if (jumlahPemesanan > 0) {
            for (Pemesanan p : daftarPemesanan) {
                if (p.getUser() == user) {
                    System.out.println("Nomor Pemesanan     : " + p.getKodePemesanan());
                    System.out.println("Film                : " + p.getKodeJadwal().getFilm().getJudul());
                    System.out.println("Jadwal              : " + p.getKodeJadwal().getTanggal() + " " + 
                                                                  p.getKodeJadwal().getJam());
                    System.out.println("Jumlah Tiket        : " + p.getJumlahPemesanan());
                    System.out.println("Metode Bayar        : " + p.getMetodePembayaran());
                    System.out.println("Total Bayar         : " + p.getTotalBayar());
                }
            }
        }
    }
}