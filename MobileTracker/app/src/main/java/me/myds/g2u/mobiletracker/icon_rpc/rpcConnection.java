package me.myds.g2u.mobiletracker.icon_rpc;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import me.myds.g2u.mobiletracker.exception.rpcRequestException;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;

public class rpcConnection {
    private static String TAG = "rpcConnection";
    public static final String API_ENDPOINT = "https://bicon.net.solidwallet.io/api/v3";

    public static rpcResponse connect(rpcRequest request) throws rpcRequestException, rpcResponseException {
        HttpsURLConnection conn = null;
        rpcResponse response = null;
        Log.d(TAG, "connecting...");
        try {
            URL url = new URL(API_ENDPOINT);
            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject request_json = request.toJSONObject();
            Log.d(TAG, "request data: " + request.toString());
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(request_json.toString().getBytes("UTF-8"));
            out.flush();

            int resp_code = conn.getResponseCode();
            String resp_msg = conn.getResponseMessage();
            Log.d(TAG, resp_code + ": " + resp_msg);
            if (!(200 <= resp_code && resp_code < 300)){
                throw new rpcRequestException(resp_code, resp_msg);
            }

            StringBuilder sb = new StringBuilder();
            String line;
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Log.d(TAG, "response string: " + sb.toString());
            response = new rpcResponse(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                Log.d(TAG, "disconnected");
            }
        }

        return response;
    }
}
