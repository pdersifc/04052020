/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.session;

import com.herinoid.rsi.model.DokterRajal;

/**
 *
 * @author Herinoid
 */
public class SessionLogin {
    private DokterRajal dokter;
    private String user;
    
    private SessionLogin() {
    }
    
    public static SessionLogin getInstance() {
        return SessionLoginHolder.INSTANCE;
    }
    
    private static class SessionLoginHolder {

        private static final SessionLogin INSTANCE = new SessionLogin();
    }

    /**
     * @return the dokter
     */
    public DokterRajal getDokter() {
        return dokter;
    }
    
    

    /**
     * @param dokter the dokter to set
     */
    public void setDokter(DokterRajal dokter) {
        this.dokter = dokter;
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
    
    
}
