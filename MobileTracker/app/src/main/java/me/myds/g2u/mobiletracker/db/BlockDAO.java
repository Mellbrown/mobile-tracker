package me.myds.g2u.mobiletracker.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BlockDAO {

    @Query("SELECT * FROM BlockEntity ORDER BY time_stamp DESC")
    List<BlockEntity> list();

    @Query("SELECT * FROM BlockEntity WHERE block_hash IN (:hashes)")
    List<BlockEntity> list(String ...hashes);

    @Insert
    void insert(BlockEntity ...blockEntities);

    @Delete
    void delete(BlockEntity ...blockEntities);
}
