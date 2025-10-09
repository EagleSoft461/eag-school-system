import java.time.LocalDate;

public class Devamsizlik {
    private int id;
    private int ogrenciNo;
    private String dersAdi;
    private LocalDate tarih;
    private String durum; // Devamsız, Geç Geldi, Mazeretli
    private String mazeret;

    public Devamsizlik(int ogrenciNo, String dersAdi, LocalDate tarih, String durum) {
        this.ogrenciNo = ogrenciNo;
        this.dersAdi = dersAdi;
        this.tarih = tarih;
        this.durum = durum;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOgrenciNo() { return ogrenciNo; }
    public String getDersAdi() { return dersAdi; }
    public LocalDate getTarih() { return tarih; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public String getMazeret() { return mazeret; }
    public void setMazeret(String mazeret) { this.mazeret = mazeret; }

    @Override
    public String toString() {
        return ogrenciNo + " - " + dersAdi + " - " + tarih + " - " + durum +
                (mazeret != null ? " - Mazeret: " + mazeret : "");
    }
}