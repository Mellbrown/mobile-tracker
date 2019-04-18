package me.myds.g2u.mobiletracker.icon_rpc;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class rpcRequest {
    public static final String JSONRPC_2_0 = "2.0";
    public static final int NET_TESTNET_DAPPS = 3;

    public static final String ICX_GET_LAST_BLOCK = "icx_getLastBlock";
    public static final String ICX_GET_BLOCK_BY_HASH = "icx_getBlockByHash";
    public static final String ICX_GET_TRANSACTION_RESULT = "icx_getTransactionResult";

    public String jsonrpc = JSONRPC_2_0;
    public String method = null;
    public int id = NET_TESTNET_DAPPS;
    public JSONObject params = null;

    public rpcRequest(String method) {
        this.method = method;
    }

    public rpcRequest(String method, JSONObject params) {
        this(method);
        this.params = params;
    }

    public JSONObject toJSONObject () {
        JSONObject json_data = new JSONObject();
        try {
            json_data.put("jsonrpc", this.jsonrpc);
            json_data.put("method", this.method);
            json_data.put("id", this.id);
            if (params != null) {
                json_data.put("params", this.params);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return json_data;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }
}
