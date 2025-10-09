public class DersTanim {
    private int id;
    private String dersKodu;
    private String dersAdi;
    private int kredi;
    private int akts;
    private String bolum;
    private String donem;

    public DersTanim(String dersKodu, String dersAdi) {
        this.dersKodu = dersKodu;
        this.dersAdi = dersAdi;
        this.kredi = 3;
        this.akts = 5;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDersKodu() { return dersKodu; }
    public String getDersAdi() { return dersAdi; }
    public int getKredi() { return kredi; }
    public void setKredi(int kredi) { this.kredi = kredi; }
    public int getAkts() { return akts; }
    public void setAkts(int akts) { this.akts = akts; }
    public String getBolum() { return bolum; }
    public void setBolum(String bolum) { this.bolum = bolum; }
    public String getDonem() { return donem; }
    public void setDonem(String donem) { this.donem = donem; }

    @Override
    public String toString() {
        return dersKodu + " - " + dersAdi + " (" + kredi + " Kredi)";
    }
}