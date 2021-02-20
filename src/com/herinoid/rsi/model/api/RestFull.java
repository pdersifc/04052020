/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author HEWRIE
 */
public class RestFull {

    private static String base_url = "http://192.168.10.8:8117/resep/";
//    private static String base_url = "http://localhost:8080/resep/";
    private static ObjectMapper mapper;

    public static BaseResponse getValidasi() throws Exception {
        mapper = new ObjectMapper();
        BaseResponse response = null;
        try {
            System.out.println("validasi get");
            URL url = new URL(base_url + "validasi");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + BasicAuth.auth());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            response = mapper.readValue(br.readLine(), BaseResponse.class);
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static BaseResponse postValidasi(ResepValidasiRequest request) {
        mapper = new ObjectMapper();
        BaseResponse response = new BaseResponse();
        try {
            URL url = new URL(base_url + "validasi");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Basic " + BasicAuth.auth());

            String input = mapper.writeValueAsString(request);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 201) {
                response.setResponseCode("301");
                response.setResponseMessage("Gagal Validasi");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            if (br.readLine() != null) {
                response.setResponseCode("201");
                response.setResponseMessage("Validasi resep Berhasil!");
            }
            conn.disconnect();
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return response;
    }

}
