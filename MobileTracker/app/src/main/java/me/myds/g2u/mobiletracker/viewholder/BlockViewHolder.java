package me.myds.g2u.mobiletracker.viewholder;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.R;

public class BlockViewHolder extends BaseRecyclerViewHolder<Block> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");

    public TextView txtBlockHash;
    public HashImage hashImage;
    public TextView txtTime;
    public ImageView is_saved;

    public BlockViewHolder(@NonNull View itemView) {
        super(itemView);
        txtBlockHash = itemView.findViewById(R.id.txt_block_hash);
        hashImage = itemView.findViewById(R.id.txtHash);
        txtTime = itemView.findViewById(R.id.txtTime);
        is_saved = itemView.findViewById(R.id.is_saved);
    }

    @Override
    public void bindData(Block block) {
        txtBlockHash.setText(block.getPrevBlockHash());
        hashImage.setHashString(block.getPrevBlockHash());
        txtTime.setText(dateFormat.format(new Date(block.getTimeStamp() / 1000L)));
    }

    public void setSaved(boolean save) {
        is_saved.setVisibility(save ? View.VISIBLE : View.INVISIBLE);
    }

    public void setSelect(boolean select) {

    }

    public void setDisable(boolean disable) {

    }
}
