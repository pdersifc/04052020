/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hewrei
 */
public class BasicAuth {
    public static String auth() {
        try {
            String userpassword = "herinoid@gmail.com:123";
            return Base64.getEncoder().encodeToString(userpassword.getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BasicAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
