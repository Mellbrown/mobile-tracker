package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.myds.g2u.mobiletracker.LoadingDialog;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.adapter.MultiSelectableAdpater;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.data.BlockService;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.viewholder.BlockViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BlockListFragment extends Fragment {

    private View itemView;

    private TextView txtIndicate;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    public MultiSelectableAdpater<Block, BlockViewHolder> mAdpater;
    public BlockService exchanger;
    private ProgressBar progressBar;

    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_block_list, container, false);

        txtIndicate = itemView.findViewById(R.id.txtIndicate);
        txtIndicate.setText("0 block loaded");

        progressBar = itemView.findViewById(R.id.loadingbar);
        mSwipe = itemView.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(() -> {
            isLoading = true;
            mAdpater.list.clear();
            mAdpater.selected.clear();
            mAdpater.notifyDataSetChanged();
            exchanger.loadBlock(10, null);
        });

        mBlockListView = itemView.findViewById(R.id.block_list);
        mBlockListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!mBlockListView.canScrollVertically(1) && !isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                    exchanger.loadBlock(10, mAdpater.list.get(mAdpater.list.size() -1));
                    isLoading = true;
                }
            }
        });
        mLayoutMgr = new LinearLayoutManager(getContext());


        mAdpater = new MultiSelectableAdpater<Block, BlockViewHolder>(
                R.layout.item_block, BlockViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(BlockViewHolder holder) {
                holder.itemView.setOnClickListener(v -> {
                    Block block = holder.getItemData(mAdpater);
                    if (!mAdpater.isSelectable()) {
                        ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                        Intent intent = new Intent(getContext(), BlockDetailActivity.class);
                        intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                        intent.putExtra("block", block.json_data.toString());
                        startActivity(intent);
                    } else {
                        if (!mAdpater.isDisabled(block)) {
                            mAdpater.setSelect(holder.getLayoutPosition(), !mAdpater.isSelected(block));
                            mAdpater.notifyItemChanged(holder.getLayoutPosition());
                        }
                    }
                });

                holder.itemView.setOnLongClickListener(v -> {
                    if (!mAdpater.isSelectable()) {
                        Block block = holder.getItemData(mAdpater);
                        setSelectable(true);
                        if (!mAdpater.isDisabled(block)) {
                            mAdpater.setSelect(block);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                    return false;
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
                if (!mAdpater.isSelectable())
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                else {
                    if (mAdpater.isDisabled(data))
                        holder.itemView.setBackgroundColor(Color.LTGRAY);
                    else if (mAdpater.isSelected(data))
                        holder.itemView.setBackgroundColor(Color.GREEN);
                    else
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }
                holder.setSaved(mAdpater.isDisabled(data));
            }
        };

        mAdpater.setOnChangeSelectableListener(selectable -> {
            if (selectable) {
                mSwipe.setEnabled(false);
            } else {
                mAdpater.notifyDataSetChanged();
                mSwipe.setEnabled(true);
            }

            if (changeSelectableListener != null)
                changeSelectableListener.onChanageSelectable(selectable);
        });

        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mAdpater);

        LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        exchanger = new BlockService();
        exchanger.setOnLoadRemoteBlocks(blocks -> {
            mAdpater.list.addAll(blocks);
            int length = mAdpater.list.size();
            exchanger.loadSavedBlock(mAdpater.list);
            txtIndicate.setText(length + " block loaded");
        });
        exchanger.setOnLoadLocalBlocks(blocks -> {
            loadingDialog.dismiss();
            mSwipe.setRefreshing(false);
            progressBar.setVisibility(View.INVISIBLE);
            isLoading = false;
            mAdpater.setDisabled(blocks);
            mAdpater.notifyDataSetChanged();
        });
        exchanger.setOnSaveLocalBlocks(() -> {
            mAdpater.setSelectable(false);
        });
        exchanger.loadBlock(10, null);

        return itemView;
    }

    public void saveSelected () {
        exchanger.saveSelectedBlocks(mAdpater.selected);
        mAdpater.disabled.addAll(mAdpater.selected);
        mAdpater.notifyDataSetChanged();
    }

    private MultiSelectableAdpater.OnChangeSelectableListener changeSelectableListener;
    public void setOnChangeSelectableListener(MultiSelectableAdpater.OnChangeSelectableListener listener) {
        this.changeSelectableListener = listener;
    }
}
