import java.sql.*;
import java.util.*;

public class OgretmenDAO {

    public static boolean ogretmenEkle(Ogretmen ogretmen) {
        String sql = "INSERT INTO ogretmenler (ogretmen_no, ad, soyad, bolum, eposta, telefon) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogretmen.getOgretmenNo());
            pstmt.setString(2, ogretmen.getAd());
            pstmt.setString(3, ogretmen.getSoyad());
            pstmt.setString(4, ogretmen.getBolum());
            pstmt.setString(5, ogretmen.getEposta());
            pstmt.setString(6, ogretmen.getTelefon());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Öğretmen ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Tüm öğretmenleri getir
    public static List<Ogretmen> tumOgretmenleriGetir() {
        List<Ogretmen> ogretmenler = new ArrayList<>();
        String sql = "SELECT * FROM ogretmenler ORDER BY ad, soyad";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ogretmen ogretmen = new Ogretmen(
                        rs.getInt("ogretmen_no"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getString("bolum")
                );
                ogretmen.setId(rs.getInt("id"));
                ogretmen.setEposta(rs.getString("eposta"));
                ogretmen.setTelefon(rs.getString("telefon"));

                // Öğretmenin derslerini yükle
                List<String> dersler = ogretmenDersleriniGetir(ogretmen.getId());
                ogretmen.setVerdigiDersler(dersler);

                ogretmenler.add(ogretmen);
            }

        } catch (SQLException e) {
            System.out.println("❌ Öğretmen listeleme hatası: " + e.getMessage());
        }
        return ogretmenler;
    }

    // Öğretmene ders ata
    public static boolean dersAta(int ogretmenId, String dersAdi, String gun, String baslangicSaati, String bitisSaati) {
        String sql = "INSERT INTO ders_atama (ogretmen_id, ders_adi, gun, baslangic_saat, bitis_saat) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogretmenId);
            pstmt.setString(2, dersAdi);
            pstmt.setString(3, gun);
            pstmt.setString(4, baslangicSaati);
            pstmt.setString(5, bitisSaati);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ders atama hatası: " + e.getMessage());
            return false;
        }
    }

    // Öğretmenin derslerini getir
    public static List<String> ogretmenDersleriniGetir(int ogretmenId) {
        List<String> dersler = new ArrayList<>();
        String sql = "SELECT ders_adi FROM ders_atama WHERE ogretmen_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogretmenId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dersler.add(rs.getString("ders_adi"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Öğretmen dersleri getirme hatası: " + e.getMessage());
        }
        return dersler;
    }

    // Öğretmen no'dan ID bul
    public static int ogretmenNoToId(int ogretmenNo) {
        String sql = "SELECT id FROM ogretmenler WHERE ogretmen_no = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogretmenNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("❌ Öğretmen bulma hatası: " + e.getMessage());
        }
        return -1;
    }
}