package entropia.app.com.andoidcdb.app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import entropia.app.com.andoidcdb.pojo.Balance;

/**
 * Created by renan on 17/05/15.
 */
public class App extends Application {

    public List<Balance> balanceList;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        balanceList = new ArrayList<>();
    }


}
