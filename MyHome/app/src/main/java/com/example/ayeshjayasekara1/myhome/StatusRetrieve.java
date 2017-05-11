package com.example.ayeshjayasekara1.myhome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ayeshjayasekara1 on 5/10/17.
 */

public class StatusRetrieve {
        private String username;
        private static final String URLLink = "http://192.168.8.102/test/stat.php";

    public StatusRetrieve(String username) {
        this.username = username;
    }

    private String EncodedQuery (){

        String data =null;
        try {
            data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
           // data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                 //   URLEncoder.encode("DefaultPassword", "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = "ERROR";
        }
        catch (Exception e){
            data = "ERROR";
        }
        return data;
    }


    public String Communicate(){
        OutputStreamWriter wr =null;
        BufferedReader reader =null;
        try{

            URL url = new URL(URLLink);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( EncodedQuery() );
            wr.flush();
            reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    public int[] decodeArray(String a){
        int arr[] = new int[17];
        String temp;
        for(int t=0;t<17;t++){
            temp = a.substring(0,a.indexOf('!'));
            arr[t]= Integer.parseInt(temp);
            a =a.substring(a.indexOf('!')+1);
        }
        return arr;
    }
}
