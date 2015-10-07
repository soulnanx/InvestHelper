package entropia.app.com.andoidcdb.app;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entropia.app.com.andoidcdb.entity.Balance;

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
        ActiveAndroid.initialize(this);
    }

    private void init() {
        balanceList = new ArrayList<>();
    }
}
