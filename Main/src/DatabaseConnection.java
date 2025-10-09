import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/eag_student_info_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "47749099";
    private static boolean tablesCreated = false;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Tablolar henüz oluşturulmadıysa oluştur
            if (!tablesCreated) {
                createTablesIfNotExists(conn);
                tablesCreated = true;
            }

            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver bulunamadı!", e);
        }
    }

    // Tabloları otomatik oluştur
    private static void createTablesIfNotExists(Connection conn) {
        System.out.println("🔧 Tablolar kontrol ediliyor ve oluşturuluyor...");

        String[] createTablesSQL = {
                // Öğrenciler tablosu
                "CREATE TABLE IF NOT EXISTS ogrenciler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogrenci_no INT UNIQUE NOT NULL, " +
                        "ad VARCHAR(50) NOT NULL, " +
                        "soyad VARCHAR(50) NOT NULL, " +
                        "bolum VARCHAR(100) NOT NULL, " +
                        "ortalama DOUBLE DEFAULT 0.0, " +
                        "kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",

                // Öğretmenler tablosu
                "CREATE TABLE IF NOT EXISTS ogretmenler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogretmen_no INT UNIQUE NOT NULL, " +
                        "ad VARCHAR(50) NOT NULL, " +
                        "soyad VARCHAR(50) NOT NULL, " +
                        "bolum VARCHAR(100) NOT NULL, " +
                        "eposta VARCHAR(100), " +
                        "telefon VARCHAR(20), " +
                        "kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",

                // Dersler tablosu
                "CREATE TABLE IF NOT EXISTS dersler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogrenci_no INT NOT NULL, " +
                        "ders_adi VARCHAR(100) NOT NULL, " +
                        "FOREIGN KEY (ogrenci_no) REFERENCES ogrenciler(ogrenci_no) ON DELETE CASCADE)",

                // Devamsızlık tablosu
                "CREATE TABLE IF NOT EXISTS devamsizlik (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogrenci_no INT NOT NULL, " +
                        "ders_adi VARCHAR(100) NOT NULL, " +
                        "tarih DATE NOT NULL, " +
                        "durum ENUM('Devamsız', 'Geç Geldi', 'Mazeretli') DEFAULT 'Devamsız', " +
                        "mazeret VARCHAR(200), " +
                        "kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "FOREIGN KEY (ogrenci_no) REFERENCES ogrenciler(ogrenci_no) ON DELETE CASCADE)",

                // Ders atama tablosu
                "CREATE TABLE IF NOT EXISTS ders_atama (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogretmen_id INT NOT NULL, " +
                        "ders_adi VARCHAR(100) NOT NULL, " +
                        "gun VARCHAR(20), " +
                        "baslangic_saat TIME, " +
                        "bitis_saat TIME, " +
                        "FOREIGN KEY (ogretmen_id) REFERENCES ogretmenler(id) ON DELETE CASCADE)",

                // Ders tanımları tablosu
                "CREATE TABLE IF NOT EXISTS ders_tanimlari (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ders_kodu VARCHAR(20) UNIQUE NOT NULL, " +
                        "ders_adi VARCHAR(100) NOT NULL, " +
                        "kredi INT DEFAULT 3, " +
                        "akts INT DEFAULT 5, " +
                        "bolum VARCHAR(100), " +
                        "donem VARCHAR(20))",

                // Notlar tablosu
                "CREATE TABLE IF NOT EXISTS notlar (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ogrenci_no INT NOT NULL, " +
                        "ders_kodu VARCHAR(20) NOT NULL, " +
                        "vize_notu DOUBLE, " +
                        "final_notu DOUBLE, " +
                        "butunleme_notu DOUBLE, " +
                        "ortalama DOUBLE, " +
                        "harf_notu VARCHAR(2), " +
                        "donem VARCHAR(20), " +
                        "kayit_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "FOREIGN KEY (ogrenci_no) REFERENCES ogrenciler(ogrenci_no) ON DELETE CASCADE, " +
                        "FOREIGN KEY (ders_kodu) REFERENCES ders_tanimlari(ders_kodu) ON DELETE CASCADE)",

                // Dönem bilgileri tablosu
                "CREATE TABLE IF NOT EXISTS donemler (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "donem_adi VARCHAR(20) UNIQUE NOT NULL, " +
                        "baslangic_tarihi DATE, " +
                        "bitis_tarihi DATE, " +
                        "aktif BOOLEAN DEFAULT FALSE)"
        };

        try (Statement stmt = conn.createStatement()) {
            for (int i = 0; i < createTablesSQL.length; i++) {
                try {
                    stmt.execute(createTablesSQL[i]);
                    System.out.println("✅ Tablo oluşturuldu: " + (i+1) + "/" + createTablesSQL.length);
                } catch (SQLException e) {
                    System.out.println("❌ Tablo oluşturma hatası [" + (i+1) + "]: " + e.getMessage());
                }
            }
            System.out.println("🎉 Tüm tablolar hazır!");

        } catch (SQLException e) {
            System.out.println("❌ Tablo oluşturma hatası: " + e.getMessage());
        }
    }

    // Bağlantı testi - TABLOLARI KESİNLİKLE OLUŞTURUR
    public static boolean testConnection() {
        try {
            Connection conn = getConnection(); // Bu tabloları oluşturacak
            System.out.println("✅ MySQL bağlantısı başarılı ve tablolar hazır!");
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ MySQL bağlantı hatası: " + e.getMessage());
            return false;
        }
    }

    // Manuel tablo oluşturma metodu
    public static void forceCreateTables() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            createTablesIfNotExists(conn);
            tablesCreated = true;
        } catch (SQLException e) {
            System.out.println("❌ Manuel tablo oluşturma hatası: " + e.getMessage());
        }
    }
}