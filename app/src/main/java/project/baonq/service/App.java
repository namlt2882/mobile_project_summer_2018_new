package project.baonq.service;

import android.app.Application;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import project.baonq.model.DaoMaster;
import project.baonq.model.DaoSession;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "personal_finance.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        QueryBuilder.LOG_SQL = true;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
