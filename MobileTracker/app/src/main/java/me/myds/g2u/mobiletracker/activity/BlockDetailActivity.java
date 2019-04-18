package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.data.BlockService;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.adapter.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.viewholder.TransactionViewHolder;

public class BlockDetailActivity extends AppCompatActivity {
    public static final String PARAM_TRANSACTION_LIST = "transaction list";

    public TextView txtBlockHash;
    public HashImage hashImage;
    public TextView txtTime;
    public ImageView is_saved;

    private TextView txtIndicate;
    private RecyclerView mTransactionistView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Transaction, TransactionViewHolder> mTransactionListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_detail);

        getSupportActionBar().setTitle("Block");

        txtBlockHash = findViewById(R.id.txt_block_hash);
        hashImage = findViewById(R.id.txtHash);
        txtTime = findViewById(R.id.txtTime);
        is_saved = findViewById(R.id.is_saved);

        Intent intent = getIntent();
        ArrayList<Transaction> transactions = intent.getParcelableArrayListExtra(PARAM_TRANSACTION_LIST);
        Block block = new Block(intent.getStringExtra("block"));

        txtBlockHash.setText(block.getBlockHash());
        hashImage.setHashString(block.getBlockHash());
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
        txtTime.setText(dateFormat.format(new Date(block.getTimeStamp() / 1000L)));
        is_saved.setVisibility(View.INVISIBLE);
        BlockService blockService = new BlockService();
        blockService.loadSavedBlock(new ArrayList<Block>(){{add(block);}});
        blockService.setOnLoadLocalBlocks(blocks -> {
            if (blocks.size() == 1) {
                is_saved.setVisibility(View.VISIBLE);
            }
        });

        txtIndicate = findViewById(R.id.txtIndicate);
        txtIndicate.setText(transactions.size() + " transactions");

        mTransactionistView = findViewById(R.id.transaction_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mTransactionListAdpater = new BaseRecyclerAdapter<Transaction, TransactionViewHolder>(
                R.layout.item_transaction, TransactionViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(TransactionViewHolder holder) {
                holder.cardView.setOnClickListener((v)->{
                    int itemPosition = holder.getLayoutPosition();
                    Transaction transaction = mTransactionListAdpater.list.get(itemPosition);
                    Intent intent = new Intent(BlockDetailActivity.this, TransactionResultActivity.class);
                    intent.putExtra(TransactionResultActivity.PARAM_TRANSACTION, transaction);
                    startActivity(intent);
                });
            }

            @Override
            public void dataConvertViewHolder(TransactionViewHolder holder, Transaction data) {
                holder.bindData(data);
            }
        };
        mTransactionListAdpater.list.addAll(transactions);
        mTransactionistView.setLayoutManager(mLayoutMgr);
        mTransactionistView.setAdapter(mTransactionListAdpater);
    }
}
