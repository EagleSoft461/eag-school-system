import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OgrenciDAO {

    // Öğrenci ekleme
    public static boolean ogrenciEkle(Ogrenci ogrenci) {
        String sql = "INSERT INTO ogrenciler (ogrenci_no, ad, soyad, bolum, ortalama) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenci.getOgrenciNo());
            pstmt.setString(2, ogrenci.getAd());
            pstmt.setString(3, ogrenci.getSoyad());
            pstmt.setString(4, ogrenci.getBolum());
            pstmt.setDouble(5, ogrenci.getOrtalama());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tüm öğrencileri getir
    public static List<Ogrenci> tumOgrencileriGetir() {
        List<Ogrenci> ogrenciler = new ArrayList<>();
        String sql = "SELECT * FROM ogrenciler ORDER BY ad, soyad";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ogrenci ogrenci = new Ogrenci(
                        rs.getInt("ogrenci_no"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getString("bolum"),
                        rs.getDouble("ortalama")
                );
                ogrenci.setId(rs.getInt("id"));
                ogrenciler.add(ogrenci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ogrenciler;
    }

    // Öğrenci silme
    public static boolean ogrenciSil(int ogrenciNo) {
        // Önce dersler tablosundan ilgili kayıtları sil
        dersleriSil(ogrenciNo);

        String sql = "DELETE FROM ogrenciler WHERE ogrenci_no = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ortalama güncelleme
    public static boolean ortalamaGuncelle(int ogrenciNo, double yeniOrtalama) {
        String sql = "UPDATE ogrenciler SET ortalama = ? WHERE ogrenci_no = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, yeniOrtalama);
            pstmt.setInt(2, ogrenciNo);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ders ekleme
    public static boolean dersEkle(int ogrenciNo, String dersAdi) {
        String sql = "INSERT INTO dersler (ogrenci_no, ders_adi) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            pstmt.setString(2, dersAdi);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Öğrencinin derslerini getir
    public static List<String> ogrenciDersleriniGetir(int ogrenciNo) {
        List<String> dersler = new ArrayList<>();
        String sql = "SELECT ders_adi FROM dersler WHERE ogrenci_no = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dersler.add(rs.getString("ders_adi"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dersler;
    }

    // Öğrenci derslerini silme
    private static void dersleriSil(int ogrenciNo) {
        String sql = "DELETE FROM dersler WHERE ogrenci_no = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}