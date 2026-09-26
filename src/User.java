public abstract class User {

    private String nama;
    private String email;
    private String username;
    private String password;
    private String role;

    // Constructor 1: Pelanggan
    public User(String nama, String email, String password, String role) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor 2: Admin
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    //Setter
    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

}