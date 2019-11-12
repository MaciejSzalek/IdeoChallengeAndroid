package com.ideochallenge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;
import com.ideochallenge.models.Route;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-07.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "challenge.db";
    private static final int DB_VERSION = 1;

    private Dao<Destination, Integer> destinationDao = null;
    private Dao<Route, Integer> routeDao = null;
    private Dao<NearbyPlace, Integer> nearbyPlaceDao = null;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Destination.class);
            TableUtils.createTable(connectionSource, Route.class);
            TableUtils.createTable(connectionSource, NearbyPlace.class);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Destination.class, true);
            TableUtils.dropTable(connectionSource, Route.class, true);
            TableUtils.dropTable(connectionSource, NearbyPlace.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int createNewDestination(Destination destination) throws SQLException {
        getDestinationDao();
        return destinationDao.create(destination);
    }

    public int createNewRoute(Route route) throws SQLException {
        getRouteDao();
        return routeDao.create(route);
    }

    public int createNewNearbyPlace(NearbyPlace nearbyPlace) throws SQLException {
        getNearbyPlaceDao();
        return nearbyPlaceDao.create(nearbyPlace);
    }

    public List<Route> getAllRoute() throws SQLException {
        getRouteDao();
        return routeDao.queryForAll();
    }

    public List<Destination> getAllDestination() throws SQLException {
        getDestinationDao();
        return destinationDao.queryForAll();
    }

    public List<NearbyPlace> getAllNearbyPlace() throws SQLException {
        getNearbyPlaceDao();
        return nearbyPlaceDao.queryForAll();
    }

    public List<Destination> getAllDestinationFromCategory(String category) throws SQLException {
        getDestinationDao();
        return destinationDao.queryForEq("category", category);
    }

    public Dao.CreateOrUpdateStatus createOrUpdateRoute(Route route) throws SQLException{
        getRouteDao();
        return routeDao.createOrUpdate(route);
    }

    public Dao.CreateOrUpdateStatus createOrUpdateDestination(Destination destination)
            throws SQLException {
        getDestinationDao();
        return destinationDao.createOrUpdate(destination);
    }

    private Dao<Destination, Integer> getDestinationDao() throws SQLException {
        if(destinationDao == null) {
            destinationDao = getDao(Destination.class);
        }
        return destinationDao;
    }

    private Dao<Route, Integer> getRouteDao() throws SQLException {
        if(routeDao == null) {
            routeDao = getDao(Route.class);
        }
        return routeDao;
    }

    private Dao<NearbyPlace, Integer> getNearbyPlaceDao() throws SQLException {
        if(nearbyPlaceDao == null){
            nearbyPlaceDao = getDao(NearbyPlace.class);
        }
        return nearbyPlaceDao;
    }

    @Override
    public void close() {
        destinationDao = null;
        routeDao = null;
        nearbyPlaceDao = null;
        super.close();
    }
}
