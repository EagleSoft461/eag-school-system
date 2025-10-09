import java.sql.*;
import java.util.*;

public class NotDAO {

    // Not ekleme/güncelleme
    public static boolean notKaydet(Not not) {
        // Önce not hesapla
        not.hesaplaOrtalama();

        String sql = "INSERT INTO notlar (ogrenci_no, ders_kodu, vize_notu, final_notu, butunleme_notu, ortalama, harf_notu, donem) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE vize_notu = ?, final_notu = ?, butunleme_notu = ?, ortalama = ?, harf_notu = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, not.getOgrenciNo());
            pstmt.setString(2, not.getDersKodu());
            pstmt.setObject(3, not.getVizeNotu());
            pstmt.setObject(4, not.getFinalNotu());
            pstmt.setObject(5, not.getButunlemeNotu());
            pstmt.setObject(6, not.getOrtalama());
            pstmt.setString(7, not.getHarfNotu());
            pstmt.setString(8, not.getDonem());

            // UPDATE kısmı
            pstmt.setObject(9, not.getVizeNotu());
            pstmt.setObject(10, not.getFinalNotu());
            pstmt.setObject(11, not.getButunlemeNotu());
            pstmt.setObject(12, not.getOrtalama());
            pstmt.setString(13, not.getHarfNotu());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Not kaydetme hatası: " + e.getMessage());
            return false;
        }
    }

    // Öğrencinin notlarını getir
    public static List<Not> ogrenciNotlariniGetir(int ogrenciNo) {
        List<Not> notlar = new ArrayList<>();
        String sql = "SELECT n.*, d.ders_adi, d.kredi, d.akts FROM notlar n " +
                "JOIN ders_tanimlari d ON n.ders_kodu = d.ders_kodu " +
                "WHERE n.ogrenci_no = ? ORDER BY n.donem, d.ders_adi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Not not = new Not(
                        rs.getInt("ogrenci_no"),
                        rs.getString("ders_kodu"),
                        rs.getString("donem")
                );
                not.setId(rs.getInt("id"));
                not.setVizeNotu(rs.getDouble("vize_notu"));
                not.setFinalNotu(rs.getDouble("final_notu"));
                not.setButunlemeNotu(rs.getDouble("butunleme_notu"));
                not.setOrtalama(rs.getDouble("ortalama"));
                not.setHarfNotu(rs.getString("harf_notu"));
                notlar.add(not);
            }

        } catch (SQLException e) {
            System.out.println("❌ Öğrenci notları getirme hatası: " + e.getMessage());
        }
        return notlar;
    }

    // Dönem notlarını getir
    public static List<Not> donemNotlariniGetir(int ogrenciNo, String donem) {
        List<Not> notlar = new ArrayList<>();
        String sql = "SELECT n.*, d.ders_adi, d.kredi, d.akts FROM notlar n " +
                "JOIN ders_tanimlari d ON n.ders_kodu = d.ders_kodu " +
                "WHERE n.ogrenci_no = ? AND n.donem = ? ORDER BY d.ders_adi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ogrenciNo);
            pstmt.setString(2, donem);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Not not = new Not(
                        rs.getInt("ogrenci_no"),
                        rs.getString("ders_kodu"),
                        rs.getString("donem")
                );
                not.setId(rs.getInt("id"));
                not.setVizeNotu(rs.getDouble("vize_notu"));
                not.setFinalNotu(rs.getDouble("final_notu"));
                not.setButunlemeNotu(rs.getDouble("butunleme_notu"));
                not.setOrtalama(rs.getDouble("ortalama"));
                not.setHarfNotu(rs.getString("harf_notu"));
                notlar.add(not);
            }

        } catch (SQLException e) {
            System.out.println("❌ Dönem notları getirme hatası: " + e.getMessage());
        }
        return notlar;
    }

    // Transkript hesapla
    public static Map<String, Object> transkriptHesapla(int ogrenciNo) {
        Map<String, Object> transkript = new HashMap<>();
        List<Not> tumNotlar = ogrenciNotlariniGetir(ogrenciNo);

        double toplamKredi = 0;
        double toplamPuan = 0;
        int alinanDersSayisi = 0;
        int gecilenDersSayisi = 0;

        for (Not not : tumNotlar) {
            DersTanim ders = DersTanimDAO.dersKoduIleGetir(not.getDersKodu());
            if (ders != null && not.getOrtalama() != null) {
                toplamKredi += ders.getKredi();
                toplamPuan += not.getOrtalama() * ders.getKredi();
                alinanDersSayisi++;
                if (not.gectiMi()) {
                    gecilenDersSayisi++;
                }
            }
        }

        double genelOrtalama = toplamKredi > 0 ? toplamPuan / toplamKredi : 0;

        transkript.put("genelOrtalama", genelOrtalama);
        transkript.put("toplamKredi", toplamKredi);
        transkript.put("alinanDersSayisi", alinanDersSayisi);
        transkript.put("gecilenDersSayisi", gecilenDersSayisi);
        transkript.put("basariOrani", alinanDersSayisi > 0 ? (double) gecilenDersSayisi / alinanDersSayisi * 100 : 0);
        transkript.put("notlar", tumNotlar);

        return transkript;
    }
}