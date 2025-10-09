public class Ogrenci {
    private int id;
    private int ogrenciNo;
    private String ad;
    private String soyad;
    private String bolum;
    private double ortalama;

    public Ogrenci(int ogrenciNo, String ad, String soyad, String bolum, double ortalama) {
        this.ogrenciNo = ogrenciNo;
        this.ad = ad;
        this.soyad = soyad;
        this.bolum = bolum;
        this.ortalama = ortalama;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOgrenciNo() { return ogrenciNo; }
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getBolum() { return bolum; }
    public double getOrtalama() { return ortalama; }
    public void setOrtalama(double ortalama) { this.ortalama = ortalama; }

    @Override
    public String toString() {
        return ad + " " + soyad + " (" + ogrenciNo + ")";
    }
}