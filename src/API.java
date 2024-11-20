

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class API {
    public static String api;
    public static boolean mode = true;
    public API(String api){
        this.api = api;
    }
    public static String getLeaderdoard() throws IOException {
        URL url = new URL("http://"+api+"/snake");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return new Scanner(conn.getInputStream()).nextLine();
    }

    public static String getUser(String name) throws IOException {
        URL url = new URL("http://"+api+"/snake/" + name);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return new Scanner(conn.getInputStream()).nextLine();
    }


    public static boolean checkLogin(String name) throws IOException {
        URL url = new URL("http://"+api+"/snake/check");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String body = "{ \"name\" : \"" + name + "\"}";
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        new DataOutputStream(conn.getOutputStream()).writeBytes(body);
        return Boolean.parseBoolean(new Scanner(conn.getInputStream()).nextLine());
    }

    public static void addUser(String name) throws IOException {
        URL url = new URL("http://"+api+"/snake");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String body = "{ \"name\" : \"" + name + "\"}";
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(body);
        conn.getInputStream();
        conn.disconnect();
    }

    public static void updateRecord(String name, int score) throws IOException {
        URL url = new URL("http://"+api+"/snake");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String body = "{ \"name\" : \"" + name + "\", \"score\" : " + score + "}";
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        DataOutputStream dos = new  DataOutputStream(conn.getOutputStream());
        dos.writeBytes(body);
        conn.getInputStream();
    }
}
