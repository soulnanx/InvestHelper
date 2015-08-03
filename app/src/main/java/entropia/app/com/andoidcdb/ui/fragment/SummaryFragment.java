package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.LinkedList;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.utils.MoneyUtils;


/**
 * Created by renan on 5/4/15.
 */
public class SummaryFragment extends Fragment {

    public static final int NAME_ITEM = R.string.summary_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu;

    private View view;
    private App app;
    private UIHelper ui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary, container, false);
        init();
        return view;
    }

    private void init() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(NAME_ITEM);
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
        setValues();
    }

    private void setValues() {
        Balance lastBalance = Balance.getCurrentBalance();

        ui.totalBalance.setText(MoneyUtils.showAsMoney(lastBalance.getBalance()));

        BigDecimal totalGain = lastBalance.calculateTotalGain(new BigDecimal("200000.00"));
        ui.totalGain.setText(MoneyUtils.showAsMoney(totalGain));
        ui.totalPercent.setText(lastBalance.calculateTotalPercent(totalGain));

        ui.itemDate.setText(DateTimeFormat.forPattern("dd/MM/yyyy").print(lastBalance.getDate()));
        ui.itemPercent.setText(lastBalance.calculatePercent());
        ui.itemGain.setText(MoneyUtils.showAsMoney(lastBalance.getGain()));
    }

    class UIHelper {
        TextView totalBalance;
        TextView totalGain;
        TextView totalPercent;

        TextView itemDate;
        TextView itemPercent;
        TextView itemGain;

        UIHelper(View v){
            totalBalance = (TextView) v.findViewById(R.id.fragment_summary_total_balance);
            totalGain = (TextView) v.findViewById(R.id.fragment_summary_total_gain);
            totalPercent = (TextView) v.findViewById(R.id.fragment_summary_total_percent);

            itemDate = (TextView) v.findViewById(R.id.item_balance_date);
            itemPercent = (TextView) v.findViewById(R.id.item_balance_percent);
            itemGain = (TextView) v.findViewById(R.id.item_balance_gain);
        }
    }
}
