package me.myds.g2u.mobiletracker.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import me.myds.g2u.mobiletracker.data.Block;

@Entity
public class BlockEntity {

    @PrimaryKey
    @NonNull
    public final String block_hash;
    public final long time_stamp;
    public final String body;

    public BlockEntity(String block_hash, long time_stamp, String body) {
        this.block_hash = block_hash;
        this.time_stamp = time_stamp;
        this.body = body;
    }

    public BlockEntity(Block block) {
        this(block.getBlockHash(), block.getTimeStamp(), block.json_data.toString());
    }
}
