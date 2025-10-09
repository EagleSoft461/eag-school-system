import java.util.ArrayList;
import java.util.List;

public class Ogretmen {
    private int id;
    private int ogretmenNo;
    private String ad;
    private String soyad;
    private String bolum;
    private String eposta;
    private String telefon;
    private List<String> verdigiDersler;

    public Ogretmen(int ogretmenNo, String ad, String soyad, String bolum) {
        this.ogretmenNo = ogretmenNo;
        this.ad = ad;
        this.soyad = soyad;
        this.bolum = bolum;
        this.verdigiDersler = new ArrayList<>();
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOgretmenNo() { return ogretmenNo; }
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getBolum() { return bolum; }
    public String getEposta() { return eposta; }
    public void setEposta(String eposta) { this.eposta = eposta; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public List<String> getVerdigiDersler() { return verdigiDersler; }
    public void setVerdigiDersler(List<String> verdigiDersler) { this.verdigiDersler = verdigiDersler; }

    public void dersEkle(String ders) {
        if (!verdigiDersler.contains(ders)) {
            verdigiDersler.add(ders);
        }
    }

    @Override
    public String toString() {
        return ad + " " + soyad + " (" + ogretmenNo + ") - " + bolum;
    }
}