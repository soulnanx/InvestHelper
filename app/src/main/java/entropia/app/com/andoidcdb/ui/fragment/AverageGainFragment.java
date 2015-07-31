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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.adapter.BalanceAdapter;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.pojo.Balance;
import entropia.app.com.andoidcdb.pojo.Sms;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.utils.MoneyUtils;
import entropia.app.com.andoidcdb.utils.SMSReader;


/**
 * Created by renan on 5/4/15.
 */
public class AverageGainFragment extends Fragment {

    public static final int NAME_ITEM = R.string.average_gain_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu;
    public static final boolean IS_CARD_REQUIRED = false;

    private View view;
    private App app;
    private UIHelper ui;
    private List<Sms> smsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_average_gain, container, false);
        init();
        return view;
    }

    private void init() {
        setTitle();
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
        smsList = new SMSReader().getAllSms(this.getActivity(), SMSReader.BRADESCO_ADDRESS);
        calculateValues();
    }

    private void setTitle(){
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(R.string.average_gain_fragment);
    }

    private void setSubTitle(String subTitle){
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setSubtitle(subTitle);
    }

    private void setList() {
        ui.balanceListView.setAdapter(new BalanceAdapter(this.getActivity(), R.layout.item_gain, app.balanceList));
    }

    private void calculateValues(){
        BigDecimal anterior = BigDecimal.ZERO;
        Collections.reverse(smsList);
        for (Sms sms : new LinkedList<>(smsList)){
            sms.getAddress();
            sms.getTime();

            if (sms.getMsg().contains(SMSReader.FILTER_BY_INV)){
                DateTime dateTime = new DateTime(Long.parseLong(sms.getTime()));
                String msg1 = sms.getMsg().replaceAll("((.*?)(BX ))", "");
                msg1 = msg1.substring(0, msg1.length() - 1);
                msg1 = msg1.replace(".", "");
                msg1 = msg1.replace(",", ".");

                BigDecimal balance = new BigDecimal(msg1);
                BigDecimal gain = anterior.subtract(balance);
                anterior = balance;

                app.balanceList.add(new Balance(dateTime, balance, gain.multiply(BigDecimal.ONE.negate())));
            }
        }
        Collections.reverse(app.balanceList);
        app.totalBalance = app.balanceList.get(0).getBalance();
        setList();
        setSubTitle(MoneyUtils.showAsMoney(app.totalBalance));
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
