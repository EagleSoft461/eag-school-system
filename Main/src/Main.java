import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class Main extends JFrame {
    private JTable ogrenciTablosu;
    private DefaultTableModel tableModel;
    private JTextField txtOgrenciNo, txtAd, txtSoyad, txtBolum, txtOrtalama, txtDers;

    public Main() {
        setTitle("🎓 Eag School System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // Yüksekliği biraz artırdık
        setLocationRelativeTo(null);

        initializeComponents();
        ogrencileriTabloyaYukle();
    }

    private void initializeComponents() {
        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Üst panel - Form
        JPanel formPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Öğrenci Bilgileri"));

        formPanel.add(new JLabel("Öğrenci No:"));
        txtOgrenciNo = new JTextField();
        formPanel.add(txtOgrenciNo);

        formPanel.add(new JLabel("Ad:"));
        txtAd = new JTextField();
        formPanel.add(txtAd);

        formPanel.add(new JLabel("Soyad:"));
        txtSoyad = new JTextField();
        formPanel.add(txtSoyad);

        formPanel.add(new JLabel("Bölüm:"));
        txtBolum = new JTextField();
        formPanel.add(txtBolum);

        formPanel.add(new JLabel("Ortalama:"));
        txtOrtalama = new JTextField();
        formPanel.add(txtOrtalama);

        formPanel.add(new JLabel("Ders:"));
        txtDers = new JTextField();
        formPanel.add(txtDers);

        // Buton paneli - ÖĞRENCİ İŞLEMLERİ
        JPanel ogrenciButtonPanel = new JPanel(new FlowLayout());
        ogrenciButtonPanel.setBorder(BorderFactory.createTitledBorder("Öğrenci İşlemleri"));

        JButton btnEkle = new JButton("Öğrenci Ekle");
        JButton btnSil = new JButton("Öğrenci Sil");
        JButton btnGuncelle = new JButton("Ortalama Güncelle");
        JButton btnDersEkle = new JButton("Ders Ekle");
        JButton btnYenile = new JButton("Listeyi Yenile");

        ogrenciButtonPanel.add(btnEkle);
        ogrenciButtonPanel.add(btnSil);
        ogrenciButtonPanel.add(btnGuncelle);
        ogrenciButtonPanel.add(btnDersEkle);
        ogrenciButtonPanel.add(btnYenile);

        // Buton paneli - YENİ ÖĞRETMEN İŞLEMLERİ
        JPanel ogretmenButtonPanel = new JPanel(new FlowLayout());
        ogretmenButtonPanel.setBorder(BorderFactory.createTitledBorder("Öğretmen İşlemleri"));

        JButton btnOgretmenEkle = new JButton("Öğretmen Ekle");
        JButton btnOgretmenListele = new JButton("Öğretmenleri Listele");
        JButton btnDersAta = new JButton("Ders Ata");

        ogretmenButtonPanel.add(btnOgretmenEkle);
        ogretmenButtonPanel.add(btnOgretmenListele);
        ogretmenButtonPanel.add(btnDersAta);

        // Buton paneli - YENİ DEVAMSIZLIK İŞLEMLERİ
        JPanel devamsizlikButtonPanel = new JPanel(new FlowLayout());
        devamsizlikButtonPanel.setBorder(BorderFactory.createTitledBorder("Devamsızlık İşlemleri"));

        JButton btnDevamsizlikEkle = new JButton("Devamsızlık Ekle");
        JButton btnDevamsizlikGoruntule = new JButton("Devamsızlıkları Görüntüle");
        JButton btnMazeretGirisi = new JButton("Mazeret Girişi");
        JButton btnDevamsizlikRapor = new JButton("Devamsızlık Raporu");

        devamsizlikButtonPanel.add(btnDevamsizlikEkle);
        devamsizlikButtonPanel.add(btnDevamsizlikGoruntule);
        devamsizlikButtonPanel.add(btnMazeretGirisi);
        devamsizlikButtonPanel.add(btnDevamsizlikRapor);

        // 🔥 YENİ: Buton paneli - NOT SİSTEMİ
        JPanel notSistemiPanel = new JPanel(new FlowLayout());
        notSistemiPanel.setBorder(BorderFactory.createTitledBorder("Not Sistemi"));

        JButton btnDersTanimEkle = new JButton("Ders Tanımı Ekle");
        JButton btnNotGirisi = new JButton("Not Girişi");
        JButton btnTranskriptGoruntule = new JButton("Transkript Görüntüle");
        JButton btnDonemNotlari = new JButton("Dönem Notları");

        notSistemiPanel.add(btnDersTanimEkle);
        notSistemiPanel.add(btnNotGirisi);
        notSistemiPanel.add(btnTranskriptGoruntule);
        notSistemiPanel.add(btnDonemNotlari);

        // Tablo
        String[] columnNames = {"Öğrenci No", "Ad", "Soyad", "Bölüm", "Ortalama"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ogrenciTablosu = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ogrenciTablosu);

        // Panel yerleşimi
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);

        JPanel butonPanelleri = new JPanel(new GridLayout(4, 1)); // 3'ten 4'e çıkar
        butonPanelleri.add(ogrenciButtonPanel);
        butonPanelleri.add(ogretmenButtonPanel);
        butonPanelleri.add(devamsizlikButtonPanel);
        butonPanelleri.add(notSistemiPanel); // YENİ EKLENDİ

        northPanel.add(butonPanelleri, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // BUTON AKSİYONLARI

        // Mevcut öğrenci buton aksiyonları
        btnEkle.addActionListener(e -> ogrenciEkle());
        btnSil.addActionListener(e -> ogrenciSil());
        btnGuncelle.addActionListener(e -> ortalamaGuncelle());
        btnDersEkle.addActionListener(e -> dersEkle());
        btnYenile.addActionListener(e -> ogrencileriTabloyaYukle());

        // YENİ ÖĞRETMEN buton aksiyonları
        btnOgretmenEkle.addActionListener(e -> ogretmenEkle());
        btnOgretmenListele.addActionListener(e -> ogretmenleriListele());
        btnDersAta.addActionListener(e -> dersAta());

        // YENİ DEVAMSIZLIK buton aksiyonları
        btnDevamsizlikEkle.addActionListener(e -> devamsizlikEkle());
        btnDevamsizlikGoruntule.addActionListener(e -> devamsizliklariGoruntule());
        btnMazeretGirisi.addActionListener(e -> mazeretGirisi());
        btnDevamsizlikRapor.addActionListener(e -> devamsizlikRaporu());

        // 🔥 YENİ: NOT SİSTEMİ buton aksiyonları
        btnDersTanimEkle.addActionListener(e -> dersTanimEkle());
        btnNotGirisi.addActionListener(e -> notGirisi());
        btnTranskriptGoruntule.addActionListener(e -> transkriptGoruntule());
        btnDonemNotlari.addActionListener(e -> donemNotlariniGoruntule());
    }

    // === MEVCUT METODLAR (Aynı kalacak) ===
    private void ogrenciEkle() {
        try {
            int ogrenciNo = Integer.parseInt(txtOgrenciNo.getText());
            String ad = txtAd.getText();
            String soyad = txtSoyad.getText();
            String bolum = txtBolum.getText();
            double ortalama = txtOrtalama.getText().isEmpty() ? 0.0 : Double.parseDouble(txtOrtalama.getText());

            if (ad.isEmpty() || soyad.isEmpty() || bolum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurunuz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Ogrenci yeniOgrenci = new Ogrenci(ogrenciNo, ad, soyad, bolum, ortalama);

            if (OgrenciDAO.ogrenciEkle(yeniOgrenci)) {
                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla eklendi!");
                formuTemizle();
                ogrencileriTabloyaYukle();
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçerli sayısal değer giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ogrenciSil() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek öğrenciyi seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Öğrenciyi silmek istediğinizden emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (OgrenciDAO.ogrenciSil(ogrenciNo)) {
                JOptionPane.showMessageDialog(this, "Öğrenci başarıyla silindi!");
                ogrencileriTabloyaYukle();
            } else {
                JOptionPane.showMessageDialog(this, "Öğrenci silinemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ortalamaGuncelle() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen güncellenecek öğrenciyi seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
            String ortalamaText = txtOrtalama.getText().trim();

            if (ortalamaText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ortalama değeri giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double yeniOrtalama = Double.parseDouble(ortalamaText);

            if (yeniOrtalama < 0 || yeniOrtalama > 4.0) {
                JOptionPane.showMessageDialog(this, "Ortalama 0-4 arasında olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (OgrenciDAO.ortalamaGuncelle(ogrenciNo, yeniOrtalama)) {
                JOptionPane.showMessageDialog(this, "Ortalama başarıyla güncellendi!");
                ogrencileriTabloyaYukle();
                txtOrtalama.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ortalama güncellenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçerli bir ortalama giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dersEkle() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen ders eklenecek öğrenciyi seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dersAdi = txtDers.getText().trim();
        if (dersAdi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ders adını giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);

        if (OgrenciDAO.dersEkle(ogrenciNo, dersAdi)) {
            JOptionPane.showMessageDialog(this, "Ders başarıyla eklendi!");
            txtDers.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Ders eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ogrencileriTabloyaYukle() {
        tableModel.setRowCount(0);

        List<Ogrenci> ogrenciler = OgrenciDAO.tumOgrencileriGetir();
        for (Ogrenci ogrenci : ogrenciler) {
            Object[] row = {
                    ogrenci.getOgrenciNo(),
                    ogrenci.getAd(),
                    ogrenci.getSoyad(),
                    ogrenci.getBolum(),
                    ogrenci.getOrtalama()
            };
            tableModel.addRow(row);
        }
    }

    private void formuTemizle() {
        txtOgrenciNo.setText("");
        txtAd.setText("");
        txtSoyad.setText("");
        txtBolum.setText("");
        txtOrtalama.setText("");
        txtDers.setText("");
    }

    // === YENİ METODLAR - ÖĞRETMEN İŞLEMLERİ ===

    private void ogretmenEkle() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField txtOgretmenNo = new JTextField();
        JTextField txtAd = new JTextField();
        JTextField txtSoyad = new JTextField();
        JTextField txtBolum = new JTextField();
        JTextField txtEposta = new JTextField();
        JTextField txtTelefon = new JTextField();

        panel.add(new JLabel("Öğretmen No:"));
        panel.add(txtOgretmenNo);
        panel.add(new JLabel("Ad:"));
        panel.add(txtAd);
        panel.add(new JLabel("Soyad:"));
        panel.add(txtSoyad);
        panel.add(new JLabel("Bölüm:"));
        panel.add(txtBolum);
        panel.add(new JLabel("E-posta:"));
        panel.add(txtEposta);
        panel.add(new JLabel("Telefon:"));
        panel.add(txtTelefon);

        int result = JOptionPane.showConfirmDialog(this, panel, "Yeni Öğretmen Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int ogretmenNo = Integer.parseInt(txtOgretmenNo.getText());
                String ad = txtAd.getText();
                String soyad = txtSoyad.getText();
                String bolum = txtBolum.getText();
                String eposta = txtEposta.getText();
                String telefon = txtTelefon.getText();

                if (ad.isEmpty() || soyad.isEmpty() || bolum.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Zorunlu alanları doldurunuz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ogretmen ogretmen = new Ogretmen(ogretmenNo, ad, soyad, bolum);
                ogretmen.setEposta(eposta);
                ogretmen.setTelefon(telefon);

                if (OgretmenDAO.ogretmenEkle(ogretmen)) {
                    JOptionPane.showMessageDialog(this, "Öğretmen başarıyla eklendi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Öğretmen eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçerli bir öğretmen numarası giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ogretmenleriListele() {
        List<Ogretmen> ogretmenler = OgretmenDAO.tumOgretmenleriGetir();

        if (ogretmenler.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kayıtlı öğretmen bulunmamaktadır.");
            return;
        }

        String[] columnNames = {"Öğretmen No", "Ad", "Soyad", "Bölüm", "E-posta", "Telefon", "Verdiği Dersler"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Ogretmen ogretmen : ogretmenler) {
            Object[] row = {
                    ogretmen.getOgretmenNo(),
                    ogretmen.getAd(),
                    ogretmen.getSoyad(),
                    ogretmen.getBolum(),
                    ogretmen.getEposta() != null ? ogretmen.getEposta() : "",
                    ogretmen.getTelefon() != null ? ogretmen.getTelefon() : "",
                    String.join(", ", ogretmen.getVerdigiDersler())
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Tüm Öğretmenler", JOptionPane.INFORMATION_MESSAGE);
    }

    private void dersAta() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField txtOgretmenNo = new JTextField();
        JTextField txtDersAdi = new JTextField();
        JComboBox<String> cmbGun = new JComboBox<>(new String[]{"Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"});
        JTextField txtBaslangic = new JTextField("09:00");
        JTextField txtBitis = new JTextField("10:00");

        panel.add(new JLabel("Öğretmen No:"));
        panel.add(txtOgretmenNo);
        panel.add(new JLabel("Ders Adı:"));
        panel.add(txtDersAdi);
        panel.add(new JLabel("Gün:"));
        panel.add(cmbGun);
        panel.add(new JLabel("Başlangıç Saati:"));
        panel.add(txtBaslangic);
        panel.add(new JLabel("Bitiş Saati:"));
        panel.add(txtBitis);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ders Atama",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int ogretmenNo = Integer.parseInt(txtOgretmenNo.getText());
                String dersAdi = txtDersAdi.getText();
                String gun = (String) cmbGun.getSelectedItem();
                String baslangic = txtBaslangic.getText();
                String bitis = txtBitis.getText();

                if (dersAdi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ders adını giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ogretmenId = OgretmenDAO.ogretmenNoToId(ogretmenNo);

                if (ogretmenId != -1) {
                    if (OgretmenDAO.dersAta(ogretmenId, dersAdi, gun, baslangic, bitis)) {
                        JOptionPane.showMessageDialog(this, "Ders başarıyla atandı!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Ders atanamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Öğretmen bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçerli bir öğretmen numarası giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === YENİ METODLAR - DEVAMSIZLIK İŞLEMLERİ ===

    private void devamsizlikEkle() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField txtOgrenciNo = new JTextField();
        JTextField txtDersAdi = new JTextField();
        JTextField txtTarih = new JTextField(java.time.LocalDate.now().toString());
        JComboBox<String> cmbDurum = new JComboBox<>(new String[]{"Devamsız", "Geç Geldi"});

        panel.add(new JLabel("Öğrenci No:"));
        panel.add(txtOgrenciNo);
        panel.add(new JLabel("Ders Adı:"));
        panel.add(txtDersAdi);
        panel.add(new JLabel("Tarih (YYYY-MM-DD):"));
        panel.add(txtTarih);
        panel.add(new JLabel("Durum:"));
        panel.add(cmbDurum);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Bugün: " + java.time.LocalDate.now()));

        int result = JOptionPane.showConfirmDialog(this, panel, "Devamsızlık Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int ogrenciNo = Integer.parseInt(txtOgrenciNo.getText());
                String dersAdi = txtDersAdi.getText();
                String tarihStr = txtTarih.getText();
                String durum = (String) cmbDurum.getSelectedItem();

                if (dersAdi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ders adını giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate tarih = LocalDate.parse(tarihStr);
                Devamsizlik devamsizlik = new Devamsizlik(ogrenciNo, dersAdi, tarih, durum);

                if (DevamsizlikDAO.devamsizlikEkle(devamsizlik)) {
                    JOptionPane.showMessageDialog(this, "Devamsızlık başarıyla eklendi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Devamsızlık eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Geçerli veriler giriniz! Tarih formatı: YYYY-MM-DD", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void devamsizliklariGoruntule() {
        List<Devamsizlik> devamsizliklar = DevamsizlikDAO.tumDevamsizliklariGetir();

        if (devamsizliklar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kayıtlı devamsızlık bulunmamaktadır.");
            return;
        }

        String[] columnNames = {"ID", "Öğrenci No", "Ders Adı", "Tarih", "Durum", "Mazeret"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Devamsizlik devamsizlik : devamsizliklar) {
            Object[] row = {
                    devamsizlik.getId(),
                    devamsizlik.getOgrenciNo(),
                    devamsizlik.getDersAdi(),
                    devamsizlik.getTarih(),
                    devamsizlik.getDurum(),
                    devamsizlik.getMazeret() != null ? devamsizlik.getMazeret() : ""
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Tüm Devamsızlıklar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mazeretGirisi() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtDevamsizlikId = new JTextField();
        JTextArea txtMazeret = new JTextArea(3, 20);
        txtMazeret.setLineWrap(true);
        JScrollPane scrollMazeret = new JScrollPane(txtMazeret);

        panel.add(new JLabel("Devamsızlık ID:"));
        panel.add(txtDevamsizlikId);
        panel.add(new JLabel("Mazeret:"));
        panel.add(scrollMazeret);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Devamsızlık ID'sini listeden alınız"));

        int result = JOptionPane.showConfirmDialog(this, panel, "Mazeret Girişi",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int devamsizlikId = Integer.parseInt(txtDevamsizlikId.getText());
                String mazeret = txtMazeret.getText().trim();

                if (mazeret.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Mazeret açıklaması giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (DevamsizlikDAO.mazeretGuncelle(devamsizlikId, mazeret)) {
                    JOptionPane.showMessageDialog(this, "Mazeret başarıyla kaydedildi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Mazeret kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçerli bir devamsızlık ID giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void devamsizlikRaporu() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir öğrenci seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
        String ogrenciAd = (String) tableModel.getValueAt(selectedRow, 1);
        String ogrenciSoyad = (String) tableModel.getValueAt(selectedRow, 2);

        List<Devamsizlik> devamsizliklar = DevamsizlikDAO.ogrenciDevamsizliklariniGetir(ogrenciNo);
        java.util.Map<String, Integer> istatistikler = DevamsizlikDAO.devamsizlikIstatistikleri(ogrenciNo);

        StringBuilder rapor = new StringBuilder();
        rapor.append("=== DEVAMSIZLIK RAPORU ===\n");
        rapor.append("Öğrenci: ").append(ogrenciAd).append(" ").append(ogrenciSoyad).append(" (").append(ogrenciNo).append(")\n\n");

        rapor.append("📊 İstatistikler:\n");
        for (java.util.Map.Entry<String, Integer> entry : istatistikler.entrySet()) {
            rapor.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" kayıt\n");
        }

        rapor.append("\n📅 Detaylı Kayıtlar:\n");
        if (devamsizliklar.isEmpty()) {
            rapor.append("  Kayıtlı devamsızlık bulunmamaktadır.\n");
        } else {
            for (Devamsizlik devamsizlik : devamsizliklar) {
                rapor.append("  - ").append(devamsizlik.getTarih())
                        .append(" | ").append(devamsizlik.getDersAdi())
                        .append(" | ").append(devamsizlik.getDurum());
                if (devamsizlik.getMazeret() != null) {
                    rapor.append(" | Mazeret: ").append(devamsizlik.getMazeret());
                }
                rapor.append("\n");
            }
        }

        JTextArea textArea = new JTextArea(20, 50);
        textArea.setText(rapor.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "Devamsızlık Raporu - " + ogrenciAd + " " + ogrenciSoyad,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // 🔥 YENİ METODLAR - NOT SİSTEMİ ===

    private void dersTanimEkle() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField txtDersKodu = new JTextField();
        JTextField txtDersAdi = new JTextField();
        JTextField txtKredi = new JTextField("3");
        JTextField txtAkts = new JTextField("5");
        JTextField txtBolum = new JTextField();
        JTextField txtDonem = new JTextField("2024-2025 Güz");

        panel.add(new JLabel("Ders Kodu:"));
        panel.add(txtDersKodu);
        panel.add(new JLabel("Ders Adı:"));
        panel.add(txtDersAdi);
        panel.add(new JLabel("Kredi:"));
        panel.add(txtKredi);
        panel.add(new JLabel("AKTS:"));
        panel.add(txtAkts);
        panel.add(new JLabel("Bölüm:"));
        panel.add(txtBolum);
        panel.add(new JLabel("Dönem:"));
        panel.add(txtDonem);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ders Tanımı Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String dersKodu = txtDersKodu.getText();
                String dersAdi = txtDersAdi.getText();
                int kredi = Integer.parseInt(txtKredi.getText());
                int akts = Integer.parseInt(txtAkts.getText());
                String bolum = txtBolum.getText();
                String donem = txtDonem.getText();

                if (dersKodu.isEmpty() || dersAdi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ders kodu ve adı zorunludur!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DersTanim dersTanim = new DersTanim(dersKodu, dersAdi);
                dersTanim.setKredi(kredi);
                dersTanim.setAkts(akts);
                dersTanim.setBolum(bolum);
                dersTanim.setDonem(donem);

                if (DersTanimDAO.dersTanimEkle(dersTanim)) {
                    JOptionPane.showMessageDialog(this, "Ders tanımı başarıyla eklendi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Ders tanımı eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçerli sayısal değerler giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void notGirisi() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir öğrenci seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
        String ogrenciAd = (String) tableModel.getValueAt(selectedRow, 1) + " " + (String) tableModel.getValueAt(selectedRow, 2);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField txtOgrenciNo = new JTextField(String.valueOf(ogrenciNo));
        txtOgrenciNo.setEditable(false);
        JComboBox<String> cmbDersKodu = new JComboBox<>();
        JTextField txtVize = new JTextField();
        JTextField txtFinal = new JTextField();
        JTextField txtButunleme = new JTextField();
        JTextField txtDonem = new JTextField("2024-2025 Güz");

        // Ders kodlarını yükle
        List<DersTanim> dersler = DersTanimDAO.tumDersTanimlariniGetir();
        for (DersTanim ders : dersler) {
            cmbDersKodu.addItem(ders.getDersKodu() + " - " + ders.getDersAdi());
        }

        panel.add(new JLabel("Öğrenci:"));
        panel.add(new JLabel(ogrenciAd));
        panel.add(new JLabel("Ders:"));
        panel.add(cmbDersKodu);
        panel.add(new JLabel("Vize Notu:"));
        panel.add(txtVize);
        panel.add(new JLabel("Final Notu:"));
        panel.add(txtFinal);
        panel.add(new JLabel("Bütünleme Notu:"));
        panel.add(txtButunleme);
        panel.add(new JLabel("Dönem:"));
        panel.add(txtDonem);

        int result = JOptionPane.showConfirmDialog(this, panel, "Not Girişi - " + ogrenciAd,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String secilenDers = (String) cmbDersKodu.getSelectedItem();
                String dersKodu = secilenDers.split(" - ")[0];
                Double vize = txtVize.getText().isEmpty() ? null : Double.parseDouble(txtVize.getText());
                Double finalNot = txtFinal.getText().isEmpty() ? null : Double.parseDouble(txtFinal.getText());
                Double butunleme = txtButunleme.getText().isEmpty() ? null : Double.parseDouble(txtButunleme.getText());
                String donem = txtDonem.getText();

                Not not = new Not(ogrenciNo, dersKodu, donem);
                not.setVizeNotu(vize);
                not.setFinalNotu(finalNot);
                not.setButunlemeNotu(butunleme);

                if (NotDAO.notKaydet(not)) {
                    JOptionPane.showMessageDialog(this, "Not başarıyla kaydedildi!\nOrtalama: " +
                            (not.getOrtalama() != null ? String.format("%.2f", not.getOrtalama()) : "Hesaplanamadı") +
                            "\nHarf Notu: " + not.getHarfNotu());
                } else {
                    JOptionPane.showMessageDialog(this, "Not kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Geçerli not değerleri giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void transkriptGoruntule() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir öğrenci seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
        String ogrenciAd = (String) tableModel.getValueAt(selectedRow, 1) + " " + (String) tableModel.getValueAt(selectedRow, 2);

        java.util.Map<String, Object> transkript = NotDAO.transkriptHesapla(ogrenciNo);
        List<Not> notlar = (List<Not>) transkript.get("notlar");

        StringBuilder rapor = new StringBuilder();
        rapor.append("=== AKADEMİK TRANSKRİPT ===\n");
        rapor.append("Öğrenci: ").append(ogrenciAd).append(" (").append(ogrenciNo).append(")\n\n");

        rapor.append("📊 GENEL BİLGİLER:\n");
        rapor.append("  Genel Ortalama: ").append(String.format("%.2f", transkript.get("genelOrtalama"))).append("\n");
        rapor.append("  Toplam Kredi: ").append(transkript.get("toplamKredi")).append("\n");
        rapor.append("  Alınan Ders: ").append(transkript.get("alinanDersSayisi")).append("\n");
        rapor.append("  Geçilen Ders: ").append(transkript.get("gecilenDersSayisi")).append("\n");
        rapor.append("  Başarı Oranı: %").append(String.format("%.1f", transkript.get("basariOrani"))).append("\n\n");

        rapor.append("📝 DERS NOTLARI:\n");
        if (notlar.isEmpty()) {
            rapor.append("  Kayıtlı not bulunmamaktadır.\n");
        } else {
            String currentDonem = "";
            for (Not not : notlar) {
                if (!not.getDonem().equals(currentDonem)) {
                    currentDonem = not.getDonem();
                    rapor.append("\n🎓 ").append(currentDonem).append(" Dönemi:\n");
                }

                DersTanim ders = DersTanimDAO.dersKoduIleGetir(not.getDersKodu());
                String dersAdi = ders != null ? ders.getDersAdi() : not.getDersKodu();

                rapor.append("  ").append(not.getDersKodu()).append(" - ").append(dersAdi).append(": ");
                rapor.append("Vize: ").append(not.getVizeNotu() != null ? not.getVizeNotu() : "-").append(" | ");
                rapor.append("Final: ").append(not.getFinalNotu() != null ? not.getFinalNotu() : "-").append(" | ");
                rapor.append("Ort: ").append(not.getOrtalama() != null ? String.format("%.2f", not.getOrtalama()) : "-").append(" | ");
                rapor.append("Harf: ").append(not.getHarfNotu() != null ? not.getHarfNotu() : "-").append(" | ");
                rapor.append(not.gectiMi() ? "✅ Geçti" : "❌ Kaldı").append("\n");
            }
        }

        JTextArea textArea = new JTextArea(25, 60);
        textArea.setText(rapor.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "Transkript - " + ogrenciAd,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void donemNotlariniGoruntule() {
        int selectedRow = ogrenciTablosu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir öğrenci seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ogrenciNo = (int) tableModel.getValueAt(selectedRow, 0);
        String ogrenciAd = (String) tableModel.getValueAt(selectedRow, 1) + " " + (String) tableModel.getValueAt(selectedRow, 2);

        String donem = JOptionPane.showInputDialog(this, "Dönem giriniz (örn: 2024-2025 Güz):", "2024-2025 Güz");
        if (donem == null || donem.trim().isEmpty()) return;

        List<Not> notlar = NotDAO.donemNotlariniGetir(ogrenciNo, donem);

        String[] columnNames = {"Ders Kodu", "Ders Adı", "Vize", "Final", "Bütünleme", "Ortalama", "Harf Notu", "Durum"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Not not : notlar) {
            DersTanim ders = DersTanimDAO.dersKoduIleGetir(not.getDersKodu());
            String dersAdi = ders != null ? ders.getDersAdi() : not.getDersKodu();
            String durum = not.gectiMi() ? "✅ Geçti" : "❌ Kaldı";

            Object[] row = {
                    not.getDersKodu(),
                    dersAdi,
                    not.getVizeNotu() != null ? not.getVizeNotu() : "-",
                    not.getFinalNotu() != null ? not.getFinalNotu() : "-",
                    not.getButunlemeNotu() != null ? not.getButunlemeNotu() : "-",
                    not.getOrtalama() != null ? String.format("%.2f", not.getOrtalama()) : "-",
                    not.getHarfNotu() != null ? not.getHarfNotu() : "-",
                    durum
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Dönem Notları - " + ogrenciAd + " - " + donem,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}