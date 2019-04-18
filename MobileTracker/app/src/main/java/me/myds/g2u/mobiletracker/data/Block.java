package me.myds.g2u.mobiletracker.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Block {
    public JSONObject json_data;

    public Block(JSONObject json_data) {
        this.json_data = json_data;
    }
    public Block(String json_data) {
        try {
            this.json_data = new JSONObject(json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getBlockHash() {
        String block_hash = null;
        try {
            block_hash = this.json_data.getString("block_hash");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return block_hash;
    }

    public Long getTimeStamp () {
        Long time_stamp = null;
        try {
            time_stamp = this.json_data.getLong("time_stamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return time_stamp;
    }

    public String getPrevBlockHash() {
        String prev_block_hash = null;
        try {
            prev_block_hash = this.json_data.getString("prev_block_hash");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prev_block_hash;
    }

    public ArrayList<Transaction> getConfirmedTransactionList () {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            JSONArray jsonArray = json_data.getJSONArray("confirmed_transaction_list");
            for (int i = 0; jsonArray.length() > i; i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                transactions.add(new Transaction(jsonObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public int hashCode() {
        return json_data.toString().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Block) {
            return json_data.toString().equals(((Block) obj).json_data.toString());
        }
        return super.equals(obj);
    }
}
