import java.sql.*;
import java.util.*;

public class DersTanimDAO {

    // Ders tanımı ekleme
    public static boolean dersTanimEkle(DersTanim dersTanim) {
        String sql = "INSERT INTO ders_tanimlari (ders_kodu, ders_adi, kredi, akts, bolum, donem) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dersTanim.getDersKodu());
            pstmt.setString(2, dersTanim.getDersAdi());
            pstmt.setInt(3, dersTanim.getKredi());
            pstmt.setInt(4, dersTanim.getAkts());
            pstmt.setString(5, dersTanim.getBolum());
            pstmt.setString(6, dersTanim.getDonem());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ders tanımı ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Tüm ders tanımlarını getir
    public static List<DersTanim> tumDersTanimlariniGetir() {
        List<DersTanim> dersTanimlari = new ArrayList<>();
        String sql = "SELECT * FROM ders_tanimlari ORDER BY ders_kodu";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DersTanim dersTanim = new DersTanim(
                        rs.getString("ders_kodu"),
                        rs.getString("ders_adi")
                );
                dersTanim.setId(rs.getInt("id"));
                dersTanim.setKredi(rs.getInt("kredi"));
                dersTanim.setAkts(rs.getInt("akts"));
                dersTanim.setBolum(rs.getString("bolum"));
                dersTanim.setDonem(rs.getString("donem"));
                dersTanimlari.add(dersTanim);
            }

        } catch (SQLException e) {
            System.out.println("❌ Ders tanımları getirme hatası: " + e.getMessage());
        }
        return dersTanimlari;
    }

    // Ders kodu ile ders getir
    public static DersTanim dersKoduIleGetir(String dersKodu) {
        String sql = "SELECT * FROM ders_tanimlari WHERE ders_kodu = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dersKodu);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                DersTanim dersTanim = new DersTanim(
                        rs.getString("ders_kodu"),
                        rs.getString("ders_adi")
                );
                dersTanim.setId(rs.getInt("id"));
                dersTanim.setKredi(rs.getInt("kredi"));
                dersTanim.setAkts(rs.getInt("akts"));
                dersTanim.setBolum(rs.getString("bolum"));
                dersTanim.setDonem(rs.getString("donem"));
                return dersTanim;
            }

        } catch (SQLException e) {
            System.out.println("❌ Ders getirme hatası: " + e.getMessage());
        }
        return null;
    }
}