package com.herinoid.rsi.model.report;

public class ResepReport {
    private String kodeObat;
    private String nama;
    private String no;
    private String etiket;
    private String dosis;
    private String racikan;

    public ResepReport(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResepReport resepReport = (ResepReport) o;
        if (getNo() != resepReport.getNo()) return false;
        return true;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEtiket() {
        return etiket;
    }

    public void setEtiket(String etiket) {
        this.etiket = etiket;
    }


    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }


    public String getRacikan() {
        return racikan;
    }

    public void setRacikan(String racikan) {
        this.racikan = racikan;
    }

    public String getNamaDosis() {
        return getNama() + (getDosis().equals("") ? "" : "  *No.") + getDosis();
    }
    public String getRacikanEtiket() {
        return getRacikan() + (getRacikan().equals("") ? "" : "\n") + "S " +getEtiket();
    }

    /**
     * @return the kodeObat
     */
    public String getKodeObat() {
        return kodeObat;
    }

    /**
     * @param kodeObat the kodeObat to set
     */
    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
    }
    
    
}
