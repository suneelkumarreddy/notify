package in.notwork.notify.server.util;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by insreddy1 on 7/24/2015.
 */
public class KapsysSMS {

    public void addSslCertificate() throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {


                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {

                    }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {

                return false;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                                                /*
                                                * end of the fix
                                                */


    }

    //  declaring class variable
    static String working_key;
    static String sender_id;
    static String api_url;
    static String start;
    String time;
    String mob_no;
    String message;
    String unicode;
    String dlr_url;
    String type;

    // function to set sender id
    public static void setsender_id(String sid) {
        sender_id = sid;
        return;

    }

    // function to set working key
    public static void setworking_key(String wk) {
        // checking for valid working key
        working_key = wk;
        return;
    }


    // function to set Api url
    public static void setapi_url(String ap) {
        //checking for valid url format
        String check = ap;
        String str = check.substring(0, 7);
//                                     System.out.println(""+str );
        String t = "http://";
        String s = "https:/";
        String st = "https://";
        if (str.equals(t)) {
            start = t;
            api_url = check.substring(7);
        } else if (check.substring(0, 8).equals(st)) {
            start = st;
            api_url = check.substring(8);
        } else if (str.equals(s)) {
            start = st;
            api_url = check.substring(7);
        } else {
            start = t;
            api_url = ap;
        }
    }

    //function to set parameter import java.net.URLEncoder;
    public void setparams(String ap, String wk, String sd) {
        setworking_key(wk);
        //System.out.println(wk);
        setsender_id(sd);
        setapi_url(ap);
    }

    /*function to send sms
      @ Simple message : last two field are set to null
      @ Unicode message :set unicode parameter to one
      @ Scheduled message : give time in 'ddmmyyyyhhmm' format
    */
    public String process_sms(String mob_no, String message, String dlr_url, String unicode, String time) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        addSslCertificate();
        message = URLEncoder.encode(message, "UTF-8");

        if (unicode == null)
            unicode = "0";
        unicode = "&unicode=" + unicode;
        if (time == null)
            time = "";
        else time = "&time=" + time;
       // URL url = new URL("" + start + api_url + "/web2sms.php?workingkey=" + working_key + "&sender=" + sender_id + "&to=" + mob_no + "&message=" + message + unicode + time + "&dlr_url=" + dlr_url + "&type=json");

        URL url = new URL("http://"+api_url+"/api/v3/index.php?method=sms&api_key="+working_key+"&to="+mob_no+"&sender="+sender_id+"&message="+message+"&format=json");

//                             System.out.println("url look like " + url );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        //System.out.println(result);
        return result;


    }

    /*function to send sms
      @ Simple message : last two field are set to null
      @ Unicode message :set unicode parameter to one
      @ Scheduled message : give time in 'ddmmyyyyhhmm' format
    */
    public String processBulkSMS(String mob_no, String message, String dlr_url, String unicode, String time) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        addSslCertificate();
        message = URLEncoder.encode(message, "UTF-8");

        if (unicode == null)
            unicode = "0";
        unicode = "&unicode=" + unicode;
        if (time == null)
            time = "";
        else time = "&time=" + time;
        URL url = new URL("" + start + api_url + "/web2sms.php?workingkey=" + working_key + "&sender=" + sender_id + "&to=" + mob_no + "&message=" + message + unicode + time + "&dlr_url=" + dlr_url + "&type=json");

//                             System.out.println("url look like " + url );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        //System.out.println(con.getResponseCode());
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        //System.out.println("Result is" + result);
        return result;


    }

    //function for checking message delivery status
    public String messagedelivery_status(String mid) throws Exception {
        URL url = new URL("" + start + api_url + "/api/v3/index.php?method=sms.status&api_key=" + working_key + "&id=" + mid + "&format=json&numberinfo=1");
        //System.out.println("url look like " + url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        //System.out.println("Result is" + result);
        return result;
    }

    //function for checking message delivery status
    public String myMessagedeliveryStatus(String xapi_url, String working_key, String mid) throws Exception {
        URL url = new URL("http://" + api_url + "/api/v3/index.php?method=sms.status&api_key=" + working_key + "&format=json&id=" + mid + "&numberinfo=1");

        //System.out.println("url look like " + url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        //System.out.println("Result is" + result);
        return result;
    }


    //function for checking group delivery status
    public String groupdelivery_status(String gid) throws Exception {
        URL url = new URL("" + start + api_url + "/api/v3/index.php?method=sms.groupstatus&api_key=" + working_key + "&groupid=" + gid + "&format=json&numberinfo=1");
        //System.out.println("url look like " + url );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
//                                                         System.out.println("Result is" + result);
        }
        rd.close();
        //System.out.println("Result is" + result);
        return result;
    }

    //function for checking group delivery status
    public String myGroupdelivery_status(String xapi_url, String working_key, String gid) throws Exception {
        URL url = new URL("http://" + xapi_url + "/groupstatus.php?workingkey=" + working_key + "&messagegid=" + gid + "&type=json");
        //System.out.println("url look like " + url );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream();
        con.getInputStream();
        BufferedReader rd;
        String line;
        String result = "";
        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
//                                                         System.out.println("Result is" + result);
        }
        rd.close();
        //System.out.println("Result is" + result);
        return result;
    }

    public void unicode_sms(String mob_no, String message, String dlr_url, String unicode) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        process_sms(mob_no, message, dlr_url, unicode, time = null);

    }


    public String send_sms(String mob_no, String message, String dlr_url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
       return process_sms(mob_no, message, dlr_url, unicode = null, time = null);
    }

    public void schedule_sms(String mob_no, String message, String dlr_url, String unicode, String time) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        process_sms(mob_no, message, dlr_url, unicode, time);

    }

    public void schedule_sms(String mob_no, String message, String dlr_url, String time) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        process_sms(mob_no, message, dlr_url, unicode = null, time);

    }

    public static void main(String args[])throws Exception {
        KapsysSMS kapsysSMS = new KapsysSMS();
        kapsysSMS.setparams("trans.kapsystem.com","Ad2c65b43d6633133355364f7e46cb8f2", "KAPMSG");
        //kapsysSMS.send_sms("9611721982","hi241449","ABC");
        kapsysSMS.myMessagedeliveryStatus("trans.kapsystem.com/api", "Ad2c65b43d6633133355364f7e46cb8f2", "1526369227-1");
        // kapsysSMS.myGroupdelivery_status("trans.kapsystem.com/api", "Ad2c65b43d6633133355364f7e46cb8f2", "1530280167");

    }

}
