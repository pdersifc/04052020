/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

import com.herinoid.rsi.model.ObatResep;
import java.util.List;

/**
 *
 * @author HEWRIE
 */
public class ResepValidasiRequest {
    private String norawat;
    private String noresep;
    private String kdPoli;
    private String depo;
    private String user;
    private String jenisRawat;
    private List<ObatResep> obatResepList;
    
    public ResepValidasiRequest(){}

    /**
     * @return the norawat
     */
    public String getNorawat() {
        return norawat;
    }

    /**
     * @param norawat the norawat to set
     */
    public void setNorawat(String norawat) {
        this.norawat = norawat;
    }

    /**
     * @return the noresep
     */
    public String getNoresep() {
        return noresep;
    }

    /**
     * @param noresep the noresep to set
     */
    public void setNoresep(String noresep) {
        this.noresep = noresep;
    }

    /**
     * @return the depo
     */
    public String getDepo() {
        return depo;
    }

    /**
     * @param depo the depo to set
     */
    public void setDepo(String depo) {
        this.depo = depo;
    }

    /**
     * @return the obatResepList
     */
    public List<ObatResep> getObatResepList() {
        return obatResepList;
    }

    /**
     * @param obatResepList the obatResepList to set
     */
    public void setObatResepList(List<ObatResep> obatResepList) {
        this.obatResepList = obatResepList;
    }

    /**
     * @return the kdPoli
     */
    public String getKdPoli() {
        return kdPoli;
    }

    /**
     * @param kdPoli the kdPoli to set
     */
    public void setKdPoli(String kdPoli) {
        this.kdPoli = kdPoli;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the jenisRawat
     */
    public String getJenisRawat() {
        return jenisRawat;
    }

    /**
     * @param jenisRawat the jenisRawat to set
     */
    public void setJenisRawat(String jenisRawat) {
        this.jenisRawat = jenisRawat;
    }
    
    
}
