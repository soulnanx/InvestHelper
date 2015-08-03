package entropia.app.com.andoidcdb.db;

import android.content.Context;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

/**
 * Created by renan on 2/13/15.
 */
public class DataBaseAdapter {

    private static DataBaseAdapter singletonAdapter;
    private static SqlAdapter adapter;

    private DataBaseAdapter(Context context){
        adapter = Persistence.getAdapter(context);
    }

    public static DataBaseAdapter getInstance(Context context){

        if (singletonAdapter == null){
            singletonAdapter = new DataBaseAdapter(context);
        }

        return singletonAdapter;

    }

    public static DataBaseAdapter getInstance(){
        return getInstance(null);
    }

    public SqlAdapter getAdapter() {
        return adapter;
    }
}
