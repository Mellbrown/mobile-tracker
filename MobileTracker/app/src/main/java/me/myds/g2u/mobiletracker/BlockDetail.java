package me.myds.g2u.mobiletracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.utill.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.utill.TransactionViewHolder;

public class BlockDetail extends AppCompatActivity {
    public static final String PARAM_TRANSACTIONS = "transactions";

    private TextView txtIndicate;
    private RecyclerView mTransactionistView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Transaction, TransactionViewHolder> mTransactionListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_detail);

        Intent intent = getIntent();
        ArrayList<Transaction> transactions = intent.getParcelableArrayListExtra(PARAM_TRANSACTIONS);

        txtIndicate = findViewById(R.id.txtIndicate);
        txtIndicate.setText(transactions.size() + " transactions");

        mTransactionistView = findViewById(R.id.transaction_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mTransactionListAdpater = new BaseRecyclerAdapter<Transaction, TransactionViewHolder>(
                R.layout.item_transaction, TransactionViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(TransactionViewHolder holder, Transaction data) {
                holder.dataBind(data);
            }
        };
        mTransactionListAdpater.dataList.addAll(transactions);
        mTransactionistView.setLayoutManager(mLayoutMgr);
        mTransactionistView.setAdapter(mTransactionListAdpater);
    }
}