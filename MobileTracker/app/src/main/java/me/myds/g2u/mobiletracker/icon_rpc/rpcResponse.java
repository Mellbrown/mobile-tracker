package me.myds.g2u.mobiletracker.icon_rpc;

import androidx.annotation.NonNull;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;

import org.json.JSONException;
import org.json.JSONObject;

public class rpcResponse {
    public String jsonrpc = null;
    public Integer id = null;

    public Object result = null;

    public JSONObject json_data = null;

    public rpcResponse(String json_string) throws rpcResponseException {
        try {
            this.json_data = new JSONObject(json_string);
            this.jsonrpc = this.json_data.getString("jsonrpc");
            this.id = this.json_data.getInt("id");

            if (!this.json_data.isNull("error")) {
                JSONObject error = this.json_data.getJSONObject("error");
                int error_code = error.getInt("code");
                String error_msg = error.getString("message");
                throw new rpcResponseException(error_code, error_msg);
            }
            else {
                this.result = this.json_data.get("result");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return this.json_data.toString();
    }
}
