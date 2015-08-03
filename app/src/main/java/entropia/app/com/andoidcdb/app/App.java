package entropia.app.com.andoidcdb.app;

import android.app.Application;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entropia.app.com.andoidcdb.db.DataBaseAdapter;
import entropia.app.com.andoidcdb.db.DataBase;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.entity.Control;

/**
 * Created by renan on 17/05/15.
 */
public class App extends Application {

    public List<Balance> balanceList;
    public BigDecimal totalBalance;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
        initDB();
    }

    private void initDB() {
        DatabaseSpec database = PersistenceConfig.registerSpec(DataBase.DATABASE_VERSION);
        database.match(Balance.class, Control.class);
        DataBaseAdapter.getInstance(getApplicationContext());
    }

    private void init() {
        balanceList = new ArrayList<>();
    }
}
