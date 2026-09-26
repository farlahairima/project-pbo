public class Pelanggan extends User implements Login {
    // Variabel email dihapus dari sini karena udah numpang di class User
    private static int counter = 1;
    private RiwayatPemesanan riwayatPemesanan;

    public Pelanggan(String nama, String email, String password) {
        super(nama, email, password, "Pelanggan"); 
        this.riwayatPemesanan = new RiwayatPemesanan();
        counter = counter + 1; 
    }

    public void showMenu() {
        System.out.println("\n--- Menu Pelanggan ---");
        System.out.println("1. Lihat Jadwal Tayang");
        System.out.println("2. Pesan Tiket");
        System.out.println("3. Riwayat Pesanan"); 
        System.out.println("0. Logout");
    }

    public void tambahPemesanan(Pemesanan pemesanan) {
        riwayatPemesanan.tambahPemesanan(pemesanan);
    }

    public void showRiwayatPemesanan() {
        riwayatPemesanan.tampilkanRiwayat(this);
    }

    @Override
    public void signUp() {
        System.out.println("Pelanggan berhasil mendaftar.");
    }

    @Override
    public void signIn() {
        System.out.println("Pelanggan berhasil masuk.");
    }
}