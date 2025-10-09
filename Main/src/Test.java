import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("🚀 Öğrenci Bilgi Sistemi - MANUEL KURULUM");
        System.out.println("=".repeat(50));

        // 1. ÖNCE TABLOLARI KESİNLİKLE OLUŞTUR
        System.out.println("1. 🔧 TABLOLAR MANUEL OLUŞTURULUYOR...");
        DatabaseConnection.forceCreateTables();

        // 2. Bağlantı testi
        System.out.println("\n2. 🔗 Veritabanı bağlantı testi...");
        if (!DatabaseConnection.testConnection()) {
            System.out.println("❌ Bağlantı başarısız! Program sonlandırılıyor.");
            return;
        }

        // 3. Örnek verileri ekle
        System.out.println("\n3. 📝 Örnek veriler ekleniyor...");
        addSampleData();

        System.out.println("\n🎉 KURULUM TAMAMLANDI! Sistem kullanıma hazır.");
    }

    private static void addSampleData() {
        // Örnek öğrenciler
        System.out.println("   👨‍🎓 Örnek öğrenciler ekleniyor...");
        Ogrenci ogr1 = new Ogrenci(1001, "Ali", "Yılmaz", "Bilgisayar Mühendisliği", 3.2);
        Ogrenci ogr2 = new Ogrenci(1002, "Ayşe", "Demir", "Elektrik Mühendisliği", 3.5);
        Ogrenci ogr3 = new Ogrenci(1003, "Mehmet", "Kaya", "Makine Mühendisliği", 2.8);

        OgrenciDAO.ogrenciEkle(ogr1);
        OgrenciDAO.ogrenciEkle(ogr2);
        OgrenciDAO.ogrenciEkle(ogr3);

        // Öğrencilere ders ekle
        OgrenciDAO.dersEkle(1001, "Matematik");
        OgrenciDAO.dersEkle(1001, "Programlama");
        OgrenciDAO.dersEkle(1002, "Fizik");
        OgrenciDAO.dersEkle(1003, "Kimya");

        // Örnek öğretmenler
        System.out.println("   👨‍🏫 Örnek öğretmenler ekleniyor...");
        Ogretmen ogretmen1 = new Ogretmen(5001, "Ahmet", "Yılmaz", "Matematik");
        ogretmen1.setEposta("ahmet@okul.edu");
        ogretmen1.setTelefon("05551234567");

        Ogretmen ogretmen2 = new Ogretmen(5002, "Fatma", "Kaya", "Fizik");
        ogretmen2.setEposta("fatma@okul.edu");
        ogretmen2.setTelefon("05559876543");

        OgretmenDAO.ogretmenEkle(ogretmen1);
        OgretmenDAO.ogretmenEkle(ogretmen2);

        // Ders atama
        System.out.println("   📚 Ders atamaları yapılıyor...");
        int ogretmen1Id = OgretmenDAO.ogretmenNoToId(5001);
        int ogretmen2Id = OgretmenDAO.ogretmenNoToId(5002);

        if (ogretmen1Id != -1) {
            OgretmenDAO.dersAta(ogretmen1Id, "Matematik", "Pazartesi", "09:00", "10:30");
            System.out.println("   ✅ Matematik dersi atandı");
        }

        if (ogretmen2Id != -1) {
            OgretmenDAO.dersAta(ogretmen2Id, "Fizik", "Salı", "10:30", "12:00");
            System.out.println("   ✅ Fizik dersi atandı");
        }

        // Örnek devamsızlık
        System.out.println("   📅 Örnek devamsızlık kayıtları...");
        java.time.LocalDate today = java.time.LocalDate.now();
        Devamsizlik devamsizlik1 = new Devamsizlik(1001, "Matematik", today, "Devamsız");
        Devamsizlik devamsizlik2 = new Devamsizlik(1002, "Fizik", today, "Geç Geldi");

        if (DevamsizlikDAO.devamsizlikEkle(devamsizlik1)) {
            System.out.println("   ✅ Devamsızlık 1 eklendi");
        }
        if (DevamsizlikDAO.devamsizlikEkle(devamsizlik2)) {
            System.out.println("   ✅ Devamsızlık 2 eklendi");
        }

        // Test: Verileri kontrol et
        System.out.println("\n4. 🔍 Eklenen veriler kontrol ediliyor...");
        System.out.println("   Öğrenci sayısı: " + OgrenciDAO.tumOgrencileriGetir().size());
        System.out.println("   Öğretmen sayısı: " + OgretmenDAO.tumOgretmenleriGetir().size());

        List<Devamsizlik> devamsizliklar = DevamsizlikDAO.tumDevamsizliklariGetir();
        System.out.println("   Devamsızlık kaydı: " + devamsizliklar.size());

        if (!devamsizliklar.isEmpty()) {
            System.out.println("   Son devamsızlık: " + devamsizliklar.get(0));
        }
    }
}