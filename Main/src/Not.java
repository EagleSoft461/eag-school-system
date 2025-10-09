public class Not {
    private int id;
    private int ogrenciNo;
    private String dersKodu;
    private Double vizeNotu;
    private Double finalNotu;
    private Double butunlemeNotu;
    private Double ortalama;
    private String harfNotu;
    private String donem;

    public Not(int ogrenciNo, String dersKodu, String donem) {
        this.ogrenciNo = ogrenciNo;
        this.dersKodu = dersKodu;
        this.donem = donem;
}

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOgrenciNo() { return ogrenciNo; }
    public String getDersKodu() { return dersKodu; }
    public Double getVizeNotu() { return vizeNotu; }
    public void setVizeNotu(Double vizeNotu) { this.vizeNotu = vizeNotu; }
    public Double getFinalNotu() { return finalNotu; }
    public void setFinalNotu(Double finalNotu) { this.finalNotu = finalNotu; }
    public Double getButunlemeNotu() { return butunlemeNotu; }
    public void setButunlemeNotu(Double butunlemeNotu) { this.butunlemeNotu = butunlemeNotu; }
    public Double getOrtalama() { return ortalama; }
    public void setOrtalama(Double ortalama) { this.ortalama = ortalama; }
    public String getHarfNotu() { return harfNotu; }
    public void setHarfNotu(String harfNotu) { this.harfNotu = harfNotu; }
    public String getDonem() { return donem; }

    // Not hesaplama metodları
    public void hesaplaOrtalama() {
        if (vizeNotu != null && finalNotu != null) {
            this.ortalama = (vizeNotu * 0.4) + (finalNotu * 0.6);
            this.harfNotu = harfNotuHesapla(ortalama);
        } else if (butunlemeNotu != null && finalNotu == null) {
            this.ortalama = (vizeNotu * 0.4) + (butunlemeNotu * 0.6);
            this.harfNotu = harfNotuHesapla(ortalama);
        }
    }

    private String harfNotuHesapla(double ortalama) {
        if (ortalama >= 90) return "AA";
        else if (ortalama >= 85) return "BA";
        else if (ortalama >= 80) return "BB";
        else if (ortalama >= 75) return "CB";
        else if (ortalama >= 70) return "CC";
        else if (ortalama >= 65) return "DC";
        else if (ortalama >= 60) return "DD";
        else if (ortalama >= 50) return "FD";
        else return "FF";
    }

    public boolean gectiMi() {
        return harfNotu != null && !harfNotu.equals("FF") && !harfNotu.equals("FD");
    }

    @Override
    public String toString() {
        return ogrenciNo + " - " + dersKodu + " - Ort: " + ortalama + " - Harf: " + harfNotu;
    }
}