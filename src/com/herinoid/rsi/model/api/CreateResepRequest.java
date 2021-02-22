/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

import com.herinoid.rsi.model.ObatResep;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HEWRIE
 */
public class CreateResepRequest extends BaseRequest implements Serializable {
    private String noResep;
    private Date tglResep;
    private String jamResep;
    private String noRawat;
    private String kdDokter;
    private String status;
    private String jenisPasien;
    private List<ObatResep> obatResepDetail;
    private List<ObatResep> obatResepRacikanDetail;

    public CreateResepRequest(){}

    public String getNoResep() {
        return noResep;
    }

    public void setNoResep(String noResep) {
        this.noResep = noResep;
    }

    public Date getTglResep() {
        return tglResep;
    }

    public void setTglResep(Date tglResep) {
        this.tglResep = tglResep;
    }

    public String getJamResep() {
        return jamResep;
    }

    public void setJamResep(String jamResep) {
        this.jamResep = jamResep;
    }

    public String getNoRawat() {
        return noRawat;
    }

    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    public String getKdDokter() {
        return kdDokter;
    }

    public void setKdDokter(String kdDokter) {
        this.kdDokter = kdDokter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenisPasien() {
        return jenisPasien;
    }

    public void setJenisPasien(String jenisPasien) {
        this.jenisPasien = jenisPasien;
    }

    public List<ObatResep> getObatResepDetail() {
        return obatResepDetail;
    }

    public void setObatResepDetail(List<ObatResep> obatResepDetail) {
        this.obatResepDetail = obatResepDetail;
    }

    public List<ObatResep> getObatResepRacikanDetail() {
        return obatResepRacikanDetail;
    }

    public void setObatResepRacikanDetail(List<ObatResep> obatResepRacikanDetail) {
        this.obatResepRacikanDetail = obatResepRacikanDetail;
    }
}
