package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.db.BlockEntity;
import me.myds.g2u.mobiletracker.db.BlocksDB;
import me.myds.g2u.mobiletracker.adapter.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.viewholder.BlockViewHolder;

public class SavedBlockActivity extends AppCompatActivity {

    private static final int COMPLETE_LOAD_BLOCKS = 22;

    private TextView txtIndicate;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Block, BlockViewHolder> mBlockListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_block);

        getSupportActionBar().setTitle("  Inbox");
        getSupportActionBar().setLogo(R.drawable.ic_inbox_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtIndicate = findViewById(R.id.txtIndicate);
        txtIndicate.setText("0 block loaded");

        mBlockListView = findViewById(R.id.block_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mBlockListAdpater = new BaseRecyclerAdapter<Block, BlockViewHolder>(
                R.layout.item_block, BlockViewHolder.class) {
            @Override
            public void onCreateAfterViewHolder(BlockViewHolder holder) {
                holder.itemView.setOnClickListener((v)->{
                    int itemPosition = holder.getLayoutPosition();
                    Block block = mBlockListAdpater.list.get(itemPosition);
                    ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                    Intent intent = new Intent(SavedBlockActivity.this, BlockDetailActivity.class);
                    intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                    intent.putExtra("block", block.json_data.toString());
                    startActivity(intent);
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
            }
        };

        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mBlockListAdpater);

        loadBlock();
    }

    void loadBlock() {
        new Thread(()->{
            List<BlockEntity> blockEntities = BlocksDB.run().list();
            mBlockListAdpater.list.addAll(new ArrayList<Block>(){{
                for (BlockEntity entity : blockEntities) {
                    add(new Block(entity.body));
                }
            }});
            mHandler.sendEmptyMessage(COMPLETE_LOAD_BLOCKS);
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            if(msg.what == COMPLETE_LOAD_BLOCKS) {
                int length = mBlockListAdpater.list.size();
                txtIndicate.setText(length + " block saved");
                mBlockListAdpater.notifyDataSetChanged();
            }
        }
    };
}
