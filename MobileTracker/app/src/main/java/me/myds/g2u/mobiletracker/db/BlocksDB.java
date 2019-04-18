package me.myds.g2u.mobiletracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BlockEntity.class}, version = 1, exportSchema = false)
public abstract class BlocksDB extends RoomDatabase {

    private static final String DB_NAME = "local-blocks.db";
    private static volatile BlocksDB mInst;
    private static volatile BlockDAO mDAO;

    public static synchronized void init(Context context) {
        mInst = Room.databaseBuilder(context, BlocksDB.class, DB_NAME).build();
        mDAO = mInst.blockDAO();
    }

    public static synchronized BlockDAO run() {
        return mDAO;
    }

    public abstract BlockDAO blockDAO();
}
