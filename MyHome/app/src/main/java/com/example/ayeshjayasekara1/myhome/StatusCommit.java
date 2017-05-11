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

public class StatusCommit {

    private String username;
    private static final String URLLink = "http://192.168.8.102/test/comit.php";
    private int[] CommitArray=null;

    public StatusCommit(String username) {
        this.username = username;
    }

    private String EncodedQuery (){

        String data =null;
        try {
            data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("all", "UTF-8") + "=" +
               URLEncoder.encode(""+CommitArray[0], "UTF-8");
            data += "&" + URLEncoder.encode("light1", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[1], "UTF-8");
            data += "&" + URLEncoder.encode("light2", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[2], "UTF-8");
            data += "&" + URLEncoder.encode("light3", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[3], "UTF-8");
            data += "&" + URLEncoder.encode("light4", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[4], "UTF-8");
            data += "&" + URLEncoder.encode("dim1", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[5], "UTF-8");
            data += "&" + URLEncoder.encode("dim2", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[6], "UTF-8");
            data += "&" + URLEncoder.encode("curtain", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[7], "UTF-8");
            data += "&" + URLEncoder.encode("door", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[8], "UTF-8");
            data += "&" + URLEncoder.encode("ac", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[9], "UTF-8");
            data += "&" + URLEncoder.encode("input", "UTF-8") + "=" +
                    URLEncoder.encode(""+CommitArray[16], "UTF-8");
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

    private String Communicate(){
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

    public boolean Commit(int[] arr){
        CommitArray=arr;
        return Communicate().equals("1");
    }


}
