package com.ideochallenge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ideochallenge.model.Destination;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Maciej Szałek on 2019-10-07.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "challenge.db";
    private static final int DB_VERSION = 1;

    private Dao<Destination, Integer> destinationDao = null;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Destination.class);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Destination.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int createNewDestination(Destination destination) throws SQLException {
        getDestinationDao();
        return destinationDao.create(destination);
    }

    public List<Destination> getAllDestination() throws SQLException {
        getDestinationDao();
        return destinationDao.queryForAll();
    }

    public Dao<Destination, Integer> getDestinationDao() throws SQLException {
        if(destinationDao == null) {
            destinationDao = getDao(Destination.class);
        }
        return destinationDao;
    }

    @Override
    public void close() {
        destinationDao = null;
        super.close();
    }
}
