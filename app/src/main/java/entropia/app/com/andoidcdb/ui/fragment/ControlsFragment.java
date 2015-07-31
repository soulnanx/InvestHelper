package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.adapter.BalanceAdapter;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.pojo.Balance;
import entropia.app.com.andoidcdb.pojo.Sms;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.utils.SMSReader;


/**
 * Created by renan on 5/4/15.
 */
public class ControlsFragment extends Fragment {

    public static final int NAME_ITEM = R.string.controls_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu;
    public static final boolean IS_CARD_REQUIRED = false;

    private View view;
    private App app;
    private UIHelper ui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_average_gain, container, false);
        init();
        return view;
    }

    private void init() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(R.string.controls_fragment);
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
    }

    class UIHelper {
        TextView totalBalance;
        ListView balanceListView;

        UIHelper(View v){
            totalBalance = (TextView) v.findViewById(R.id.totalBalance);
            balanceListView =  (ListView) v.findViewById(R.id.balance_list);
        }
    }
}
