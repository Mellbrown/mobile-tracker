package me.myds.g2u.mobiletracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction implements Parcelable {
    public JSONObject json_data;

    public Transaction (JSONObject json_data) {
        this.json_data = json_data;
    }

    protected Transaction(Parcel in) {
        try {
            this.json_data = new JSONObject(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTxHash () {
        try {
            return this.json_data.getString("txHash");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFrom () {
        try {
            return this.json_data.getString("from");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTo () {
        try {
            return this.json_data.getString("to");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.json_data.toString());
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

}
