package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.widget.Toolbar;
import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.LoadingDialog;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.data.TransactionResult;
import me.myds.g2u.mobiletracker.icon_rpc.rpcConnection;
import me.myds.g2u.mobiletracker.icon_rpc.rpcRequest;
import me.myds.g2u.mobiletracker.exception.rpcRequestException;
import me.myds.g2u.mobiletracker.icon_rpc.rpcResponse;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;
import me.myds.g2u.mobiletracker.R;

public class TransactionResultActivity extends AppCompatActivity {

    public static final String PARAM_TRANSACTION = "transaction";
    private static final int COMPLETE_LOAD_BLOCKS = 22;

    Transaction transaction = null;
    TransactionResult transactionResult = null;

    private HashImage imgHash;
    private HashImage imgFrom;
    private HashImage imgTo;
    private TextView txtHash;
    private TextView txtFrom;
    private TextView txtTo;

    private TextView txtResult;

    LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_result);

        getSupportActionBar().setTitle("Transaction");

        Intent intent = getIntent();
        transaction = intent.getParcelableExtra(PARAM_TRANSACTION);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        loadTransactionResult(transaction.getTxHash());

        imgHash = findViewById(R.id.imgHash);
        imgFrom = findViewById(R.id.imgFrom);
        imgTo = findViewById(R.id.imgTo);
        txtHash = findViewById(R.id.txtHash);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);

        txtResult = findViewById(R.id.txtResult);

    }

    void printTransactionResult () {
        if (transactionResult == null) return;

        try {
            txtResult.setText(transactionResult.toString());

            String txHash = transactionResult.json_data.getString("txHash").substring(2);
            String from = transaction.getFrom().substring(2);
            String to = transactionResult.json_data.getString("to").substring(2);

            imgHash.setHashString(txHash);
            imgFrom.setHashString(from);
            imgTo.setHashString(to);

            txtHash.setText(txHash);
            txtFrom.setText(from);
            txtTo.setText(to);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void loadTransactionResult (String txHash) {
        new Thread(() -> {
            try {
                JSONObject requestParams = new JSONObject();
                requestParams.put("txHash", txHash);
                rpcRequest request = new rpcRequest(rpcRequest.ICX_GET_TRANSACTION_RESULT, requestParams);
                rpcResponse response = null;
                response = rpcConnection.connect(request);
                transactionResult = new TransactionResult((JSONObject) response.result);

            } catch (rpcRequestException e) {
                e.printStackTrace();
            } catch (rpcResponseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                mHandler.sendEmptyMessage(COMPLETE_LOAD_BLOCKS);
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            if(msg.what == COMPLETE_LOAD_BLOCKS) {
                loadingDialog.dismiss();
                printTransactionResult();
            }
        }
    };
}
