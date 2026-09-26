import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static ArrayList<Bioskop> listBioskop = new ArrayList<>();
    static ArrayList<Studio> listStudio = new ArrayList<>();
    static ArrayList<Film> listFilm = new ArrayList<>();
    static ArrayList<JadwalFilm> listJadwal = new ArrayList<>();
    static ArrayList<Pelanggan> listPelanggan = new ArrayList<>();
    static ArrayList<Pemesanan> listPemesanan = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        DataFile.readBioskop(listBioskop);
        DataFile.readStudio(listStudio, listBioskop);
        DataFile.readFilm(listFilm);
        DataFile.readJadwal(listJadwal, listStudio, listFilm);
        DataFile.readPelanggan(listPelanggan);

        int menuAwal;
        do {
            System.out.println("\n--- APLIKASI TIKET BIOSKOP ---");
            System.out.println("1. Login Admin");
            System.out.println("2. Daftar Pelanggan (Sign Up)");
            System.out.println("3. Login Pelanggan (Sign In)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            menuAwal = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (menuAwal) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    daftarPelanggan();
                    break;
                case 3:
                    loginPelanggan();
                    break;
                case 0:
                    System.out.println("Menutup aplikasi...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (menuAwal != 0);

        scanner.close();
    }
    
    //pelanggan
    public static void daftarPelanggan() {
        System.out.println("\n--- Daftar Pelanggan ---");
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();

        for (Pelanggan p : listPelanggan) { // buat cek email ada yang sama apa engga
            if (p.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Pendaftaran gagal! Email '" + email + "' sudah terdaftar.");
                System.out.println("Silakan gunakan email lain atau langsung Login (Sign In).");
                return; 
            }
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        //kalo belum ada buat objek n masukin arraylist 
        Pelanggan pelangganBaru = new Pelanggan(nama, email, password);
        listPelanggan.add(pelangganBaru);
        System.out.println("Pelanggan berhasil mendaftar.");
        System.out.println("Silahkan login ulang.");
        DataFile.writePelanggan(listPelanggan);
    }

    public static void loginPelanggan() {
        System.out.println("\n--- Login Pelanggan ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Pelanggan p : listPelanggan) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                System.out.println("Login Berhasil! Selamat datang, " + p.getNama());
                menuPelanggan(p);

                return; 
            }
        }
        
        System.out.println("Email atau Password salah!");
    }

    public static void menuPelanggan(Pelanggan p) {
        int pilihan;
        do {
            p.showMenu();
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println();

            switch (pilihan) {
                case 1:
                    showJadwal(); 
                    break;
                case 2:
                    pesanTiket(p);
                    break;
                case 3:
                    showRiwayatPemesanan(p); 
                    break;
                case 0:
                    System.out.println("Logout berhasil...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    //admin
    public static void loginAdmin() {
        System.out.println("\n--- Login Admin ---");
        System.out.print("Username: ");
        String usernameAdmin = scanner.nextLine();
        System.out.print("Password: ");
        String passwordAdmin = scanner.nextLine();

        String userAsli = "";
        String passAsli = "";

        try {
            java.io.File fileAdmin = new java.io.File("src/admin.txt");
            java.util.Scanner scAdmin = new java.util.Scanner(fileAdmin);
            if (scAdmin.hasNextLine()) userAsli = scAdmin.nextLine().trim();
            if (scAdmin.hasNextLine()) passAsli = scAdmin.nextLine().trim();
            scAdmin.close();
        } catch (Exception e) {
            System.out.println("File admin.txt tidak ditemukan!");
        }

        if (usernameAdmin.equals(userAsli) && passwordAdmin.equals(passAsli)) {
            Admin admin = new Admin(usernameAdmin, passwordAdmin);
            admin.signIn();

            int opsiAdmin;
            do {
                Admin.showMenu();
                
                System.out.print("Pilih opsi (0 untuk keluar): ");
                opsiAdmin = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                
                switch (opsiAdmin) {
                    case 1: menuBioskop(); break;
                    case 2: menuStudio(); break;
                    case 3: menuFilm(); break;
                    case 4: menuJadwal(); break;
                    case 0:
                        System.out.println("Keluar dari menu admin...");
                        opsiAdmin = 0;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
                
            } while (opsiAdmin != 0);

        } else {
            System.out.println("Username atau Password Admin salah!");
        }
    }

    //bioskop
    static void menuBioskop() {
        int pilihan;
        do {
            System.out.println("\n--- Bioskop ---");
            System.out.println("1. Tambah Bioskop");
            System.out.println("2. Lihat Semua Bioskop");
            System.out.println("3. Lihat Detail Bioskop");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addBioskop(); break;
                case 2: showBioskop(); break;
                case 3: lihatDetailBioskop(); break;
                case 0: break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    static void lihatDetailBioskop() {
        if (listBioskop.isEmpty()) {
            System.out.println("Belum ada data bioskop!");
            return;
        }

        showBioskop(); 
        System.out.print("\nMasukkan Kode Bioskop untuk melihat detail: ");
        int kode = scanner.nextInt();
        scanner.nextLine();

        Bioskop target = null;
        for (Bioskop b : listBioskop) {
            if (b.getKodeBioskop() == kode) { 
                target = b; break; 
            }
        }

        if (target != null) {
            System.out.println("\n--- Detail Bioskop ---");
            System.out.println("Nama Bioskop : " + target.getNamaBioskop());
            System.out.println("Lokasi       : " + target.getLokasi());
            System.out.println("--- Daftar Studio ---");
            boolean adaStudio = false;
            for (Studio s : listStudio) {
                if (s.getBioskop().getKodeBioskop() == kode) {
                    System.out.println("- Studio " + s.getNomorStudio() + " (" + s.getJenisStudio() + ") | Rp " + s.getHarga());
                    adaStudio = true;
                }
            }
            if (!adaStudio) System.out.println("Belum ada studio di bioskop ini.");
        } else {
            System.out.println("Kode Bioskop tidak ditemukan!");
        }
    }

    static void addBioskop() {
        listBioskop.add(new Bioskop(listBioskop));
        showBioskop();
        DataFile.writeBioskop(listBioskop);
    }

    static void showBioskop() {
        if (listBioskop.isEmpty()) {
            System.out.println("Belum ada data bioskop!");
            return;
        }
        int no = 1;
        for (Bioskop b : listBioskop) {
            System.out.println("\n" + no + ".");
            b.showBioskop();
            no++;
        }
    }

    //studio
    static void menuStudio() {
        int pilihan;
        do {
            System.out.println("\n--- Studio ---");
            System.out.println("1. Tambah Studio");
            System.out.println("2. Lihat Semua Studio");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addStudio(); break;
                case 2: showStudio(); break;
                case 0: break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    static void addStudio() {
        if (listBioskop.isEmpty()) {
            System.out.println("Belum ada bioskop yang terdaftar!");
            return;
        }
        listStudio.add(new Studio(listStudio, listBioskop));
        showStudio();
        DataFile.writeStudio(listStudio);
    }

    static void showStudio() {
        if (listStudio.isEmpty()) {
            System.out.println("Belum ada data studio!");
            return;
        }
        int no = 1;
        for (Studio s : listStudio) {
            System.out.println("\n" + no + ".");
            s.showStudio();
            System.out.println();
            no++;
        }
    }

    //jadwal
    static void menuJadwal() {
        int pilihan;
        do {
            System.out.println("\n--- Jadwal ---");
            System.out.println("1. Tambah Jadwal");
            System.out.println("2. Lihat Semua Jadwal");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addJadwal(); break;
                case 2: showJadwal(); break;
                case 0: break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }
    static void addJadwal() {
        if (listBioskop.isEmpty() || listStudio.isEmpty() || listFilm.isEmpty()) {
            System.out.println("Data bioskop, studio, dan film harus ada dulu!");
            return;
        }
        else{
            listJadwal.add(new JadwalFilm(listJadwal, listBioskop, listStudio, listFilm));
            showJadwal();
            DataFile.writeJadwal(listJadwal);
        }
    }

    static void showJadwal() {
        if (listJadwal.isEmpty()) {
            System.out.println("Belum ada data jadwal!");
            return;
        }
        int no = 1;
        for (JadwalFilm j : listJadwal) {
            System.out.println("\n" + no + ".");
            j.showJadwal();
            System.out.println();
            no++;
        }
    }

    //film
    static void menuFilm() {
        int pilihan;
        do {
            System.out.println("\n--- Film ---");
            System.out.println("1. Tambah Film");
            System.out.println("2. Lihat Semua Film");
            System.out.println("3. Lihat Detail Film (Beserta Jadwalnya)");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addFilm(); break;
                case 2: showFilm(); break;
                case 3: lihatDetailFilm(); break;
                case 0: break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    static void lihatDetailFilm() {
        if (listFilm.isEmpty()) {
            System.out.println("Belum ada data film!");
            return;
        }
        showFilm();
        System.out.print("\nMasukkan Kode Film untuk melihat detail: ");
        int kode = scanner.nextInt();
        scanner.nextLine();

        Film target = null;
        for (Film f : listFilm) {
            if (f.getKodeFilm() == kode) { target = f; break; }
        }

        if (target != null) {
            System.out.println("\n--- Detail Film ---");
            System.out.println("Judul  : " + target.getJudul());
            System.out.println("Genre  : " + target.getGenre());
            System.out.println("Durasi : " + target.getDurasi() + " Menit");
            System.out.println("--- Jadwal Tayang ---");
            boolean adaJadwal = false;
            for (JadwalFilm j : listJadwal) {
                if (j.getFilm().getKodeFilm() == kode) {
                    System.out.println("- " + j.getTanggal() + " | " + j.getJam() + 
                                       " | Bioskop " + j.getStudio().getBioskop().getNamaBioskop() + 
                                       " (Studio " + j.getStudio().getNomorStudio() + ")");
                    adaJadwal = true;
                }
            }
            if (!adaJadwal) System.out.println("Belum ada jadwal untuk film ini.");
        } else {
            System.out.println("Kode Film tidak ditemukan!");
        }
    }
    static void addFilm() {
        listFilm.add(new Film(listFilm));
        showFilm();
        DataFile.writeFilm(listFilm);
    }
    
    static void showFilm() {
        if (listFilm.isEmpty()) {
            System.out.println("Belum ada data film!");
            return;
        }
        int no = 1;
        for (Film f : listFilm) {
            System.out.println(no + "."); 
            f.showFilm();
            System.out.println();
            no++;
        }
    }

    //tiket
    static void pesanTiket(Pelanggan pelangganAktif) {
        Pemesanan pesananBaru = new Pemesanan(listFilm, listJadwal, listBioskop);
        pesananBaru.setUser(pelangganAktif);
        listPemesanan.add(pesananBaru);
        DataFile.writePemesanan(listPemesanan);
        
        System.out.println("Tiket berhasil dipesan dan otomatis tersimpan di pemesanan.txt!");
    }

    static void showRiwayatPemesanan(Pelanggan p) {
        System.out.println("--- Riwayat Pesanan ---");
        
        boolean adaPesanan = false;
        int no = 1;
        
        for (Pemesanan pesanan : listPemesanan) {if (pesanan.getUser() != null && pesanan.getUser().getEmail().equalsIgnoreCase(p.getEmail())) {
                System.out.println(no + ".");
                pesanan.showRiwayatPemesanan(); 
                System.out.println();
                no++;
                adaPesanan = true;
            }
        }
        
        if (!adaPesanan) {
            System.out.println("Kamu belum memiliki riwayat pemesanan.");
        }
    }
}