
package com.herinoid.rsi.model;

/**
 *
 * @author Hewrei
 */
public class DetailPemberianObat {
    private String tglPerawatan;
    private String jam;
    private String noRawat;
    private String kodeBrng;
    private String noBatch;
    private double hBeli;
    private double biayaObat;
    private double jml;
    private double embalase;
    private double tuslah;
    private double total;
    private String status;
    private String kdBangsal;
    private String noFaktur;
    private String aturanPakai;
    private String namaObat;
    private String satuan;
    
    public DetailPemberianObat(){};
    
     @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailPemberianObat rekap = (DetailPemberianObat) o;

        if (getKodeBrng()== null ? rekap.getKodeBrng()!= null : !getKodeBrng().equals(rekap.getKodeBrng())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getKodeBrng() != null ? getKodeBrng().hashCode() : 0;
    }

    /**
     * @return the tglPerawatan
     */
    public String getTglPerawatan() {
        return tglPerawatan;
    }

    /**
     * @param tglPerawatan the tglPerawatan to set
     */
    public void setTglPerawatan(String tglPerawatan) {
        this.tglPerawatan = tglPerawatan;
    }

    /**
     * @return the jam
     */
    public String getJam() {
        return jam;
    }

    /**
     * @param jam the jam to set
     */
    public void setJam(String jam) {
        this.jam = jam;
    }

    /**
     * @return the noRawat
     */
    public String getNoRawat() {
        return noRawat;
    }

    /**
     * @param noRawat the noRawat to set
     */
    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    /**
     * @return the kodeBrng
     */
    public String getKodeBrng() {
        return kodeBrng;
    }

    /**
     * @param kodeBrng the kodeBrng to set
     */
    public void setKodeBrng(String kodeBrng) {
        this.kodeBrng = kodeBrng;
    }

    /**
     * @return the noBatch
     */
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * @param noBatch the noBatch to set
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    /**
     * @return the hBeli
     */
    public double gethBeli() {
        return hBeli;
    }

    /**
     * @param hBeli the hBeli to set
     */
    public void sethBeli(double hBeli) {
        this.hBeli = hBeli;
    }

    /**
     * @return the biayaObat
     */
    public double getBiayaObat() {
        return biayaObat;
    }

    /**
     * @param biayaObat the biayaObat to set
     */
    public void setBiayaObat(double biayaObat) {
        this.biayaObat = biayaObat;
    }

    /**
     * @return the jml
     */
    public double getJml() {
        return jml;
    }

    /**
     * @param jml the jml to set
     */
    public void setJml(double jml) {
        this.jml = jml;
    }

    /**
     * @return the embalase
     */
    public double getEmbalase() {
        return embalase;
    }

    /**
     * @param embalase the embalase to set
     */
    public void setEmbalase(double embalase) {
        this.embalase = embalase;
    }

    /**
     * @return the tuslah
     */
    public double getTuslah() {
        return tuslah;
    }

    /**
     * @param tuslah the tuslah to set
     */
    public void setTuslah(double tuslah) {
        this.tuslah = tuslah;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the kdBangsal
     */
    public String getKdBangsal() {
        return kdBangsal;
    }

    /**
     * @param kdBangsal the kdBangsal to set
     */
    public void setKdBangsal(String kdBangsal) {
        this.kdBangsal = kdBangsal;
    }

    /**
     * @return the noFaktur
     */
    public String getNoFaktur() {
        return noFaktur;
    }

    /**
     * @param noFaktur the noFaktur to set
     */
    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    /**
     * @return the aturanPakai
     */
    public String getAturanPakai() {
        return aturanPakai;
    }

    /**
     * @param aturanPakai the aturanPakai to set
     */
    public void setAturanPakai(String aturanPakai) {
        this.aturanPakai = aturanPakai;
    }

    /**
     * @return the namaObat
     */
    public String getNamaObat() {
        return namaObat;
    }

    /**
     * @param namaObat the namaObat to set
     */
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    /**
     * @return the satuan
     */
    public String getSatuan() {
        return satuan;
    }

    /**
     * @param satuan the satuan to set
     */
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
    
    public double getHargaJual(){
        return (getJml() * getBiayaObat()) +getTuslah() + getEmbalase();
    }
    
    
}
