package me.myds.g2u.mobiletracker.data;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionResult {
    public JSONObject json_data;

    public TransactionResult (JSONObject json_data) {
        this.json_data = json_data;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            ArrayList<String> keys = new ArrayList() {{
               add("txIndex");
               add("blockHeight");
               add("blockHash");
               add("cumulativeStepUsed");
               add("stepUsed");
               add("stepPrice");
               add("scoreAddress");
            }};

            StringBuilder builder = new StringBuilder();
            int status = Integer.parseInt(json_data.getString("status").substring(2));
            if (status == 1) {
                builder.append(String.format("%-20s Success\n\n\n", "Status:"));
                for (String k : keys) {
                    try { builder.append(String.format("%-20s %s\n\n\n", k + ":", json_data.getString(k))); }
                    catch (JSONException e) {

                    }
                }

            } else {
                builder.append(String.format("%-20s Success", "Failure:"));
            }

            return builder.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
