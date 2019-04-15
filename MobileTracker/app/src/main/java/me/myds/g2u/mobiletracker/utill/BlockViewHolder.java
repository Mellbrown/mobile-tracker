package me.myds.g2u.mobiletracker.utill;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.myds.g2u.mobiletracker.IconRPC.Block;
import me.myds.g2u.mobiletracker.R;

public class BlockViewHolder extends RecyclerView.ViewHolder {
    public TextView txtBlockHash;

    public BlockViewHolder(@NonNull View itemView) {
        super(itemView);
        txtBlockHash = itemView.findViewById(R.id.txt_block_hash);
    }

    public void bindData(Block block) {
        this.txtBlockHash.setText(block.getPrevBlockHash());
    }
}
