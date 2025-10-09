import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DevamsizlikDAO {

    // Devamsızlık ekleme
    public static boolean devamsizlikEkle(Devamsizlik devamsizlik) {
        String sql = "INSERT INTO devamsizlik (ogrenci_no, ders_adi, tarih, durum, mazeret) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, devamsizlik.getOgrenciNo());
            pstmt.setString(2, devamsizlik.getDersAdi());
            pstmt.setDate(3, java.sql.Date.valueOf(devamsizlik.getTarih())); // java.sql.Date kullan
            pstmt.setString(4, devamsizlik.getDurum());
            pstmt.setString(5, devamsizlik.getMazeret());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Devamsızlık ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Öğrencinin devamsızlıklarını getir
    public static List<Devamsizlik> ogrenciDevamsizliklariniGetir(int ogrenciNo) {
        List<Devamsizlik> devamsizliklar = new ArrayList<>();
        String sql = "SELECT * FROM devamsizlik WHERE ogrenci_no = ? ORDER BY tarih DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Devamsizlik devamsizlik = new Devamsizlik(
                        rs.getInt("ogrenci_no"),
                        rs.getString("ders_adi"),
                        rs.getDate("tarih").toLocalDate(),
                        rs.getString("durum")
                );
                devamsizlik.setId(rs.getInt("id"));
                devamsizlik.setMazeret(rs.getString("mazeret"));
                devamsizliklar.add(devamsizlik);
            }

        } catch (SQLException e) {
            System.out.println("❌ Devamsızlık listeleme hatası: " + e.getMessage());
        }
        return devamsizliklar;
    }

    // Devamsızlık istatistikleri
    public static Map<String, Integer> devamsizlikIstatistikleri(int ogrenciNo) {
        Map<String, Integer> istatistikler = new HashMap<>();
        String sql = "SELECT durum, COUNT(*) as sayi FROM devamsizlik WHERE ogrenci_no = ? GROUP BY durum";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                istatistikler.put(rs.getString("durum"), rs.getInt("sayi"));
            }

        } catch (SQLException e) {
            System.out.println("❌ İstatistik hatası: " + e.getMessage());
        }
        return istatistikler;
    }

    // Mazeret güncelleme
    public static boolean mazeretGuncelle(int devamsizlikId, String mazeret) {
        String sql = "UPDATE devamsizlik SET mazeret = ?, durum = 'Mazeretli' WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mazeret);
            pstmt.setInt(2, devamsizlikId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Mazeret güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Tüm devamsızlıkları getir (raporlama için)
    public static List<Devamsizlik> tumDevamsizliklariGetir() {
        List<Devamsizlik> devamsizliklar = new ArrayList<>();
        String sql = "SELECT d.*, o.ad, o.soyad FROM devamsizlik d " +
                "JOIN ogrenciler o ON d.ogrenci_no = o.ogrenci_no " +
                "ORDER BY d.tarih DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Devamsizlik devamsizlik = new Devamsizlik(
                        rs.getInt("ogrenci_no"),
                        rs.getString("ders_adi"),
                        rs.getDate("tarih").toLocalDate(),
                        rs.getString("durum")
                );
                devamsizlik.setId(rs.getInt("id"));
                devamsizlik.setMazeret(rs.getString("mazeret"));
                devamsizliklar.add(devamsizlik);
            }

        } catch (SQLException e) {
            System.out.println("❌ Tüm devamsızlıkları getirme hatası: " + e.getMessage());
        }
        return devamsizliklar;
    }

    // Öğrenci ve derse göre devamsızlık sayısı
    public static int dersDevamsizlikSayisi(int ogrenciNo, String dersAdi) {
        String sql = "SELECT COUNT(*) as sayi FROM devamsizlik WHERE ogrenci_no = ? AND ders_adi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            pstmt.setString(2, dersAdi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("sayi");
            }

        } catch (SQLException e) {
            System.out.println("❌ Devamsızlık sayısı getirme hatası: " + e.getMessage());
        }
        return 0;
    }
}