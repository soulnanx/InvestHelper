package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.ui.chart.LineView;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.utils.MoneyUtils;


/**
 * Created by renan on 5/4/15.
 */
public class SummaryFragment extends Fragment {

    public static final int NAME_ITEM = R.string.summary_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu_;

    private View view;
    private UIHelper ui;
    private Control control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary, container, false);
        loadValues();
        init();
        initGraph();
        return view;
    }

    private void loadValues() {
        control = Control.getControl();

        if (control == null){
            control = new Control();
        }
    }

    private void initGraph() {
        LineView lineView = (LineView)view.findViewById(R.id.line_view);
        lineView.setDrawDotLine(true); //optional
        lineView.setAnimation(null);
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
//        lineView.setBottomTextList(Balance.getLastDaysBottomChart(10));
        lineView.setBottomTextList(Balance.getLastDaysBottomChart(10));
        setLine(lineView);
    }

    private void setLine(LineView lineView){
        ArrayList<ArrayList<Double>> dataLists = new ArrayList<>();
        dataLists.add(Balance.getLastDays(10));
//        dataLists.add(Balance.getChartValues());

        lineView.setDataList(dataLists);
    }

    private void init() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(NAME_ITEM);
        ui = new UIHelper(view);
        setValues();
    }

    private void setValues() {
        Balance lastBalance = Balance.getCurrentBalance();
        BigDecimal totalGain = lastBalance.calculateTotalGain(control.getInitialContribution());

        ui.totalBalance.setText(MoneyUtils.showAsMoney(lastBalance.getBalance().subtract(totalGain)));
        ui.totalBalancePlusGain.setText(MoneyUtils.showAsMoney(lastBalance.getBalance()));

        ui.itemTotalDate.setText(DateTimeFormat.forPattern("dd/MM/yyyy").print(lastBalance.getDate()));
        ui.itemTotalGain.setText(MoneyUtils.showAsMoney(totalGain));
        ui.itemTotalGain.setTextColor(getResources().getColor(R.color.strong_gray));

        ui.itemTotalPercent.setText(lastBalance.calculateTotalPercent(totalGain));
        ui.itemTotalPercent.setTextColor(getResources().getColor(R.color.strong_gray));

        ui.itemLastDate.setText(DateTimeFormat.forPattern("dd/MM/yyyy").print(lastBalance.getDate()));
        ui.itemLastPercent.setText(lastBalance.calculatePercent());
        ui.itemLastGain.setText(MoneyUtils.showAsMoney(lastBalance.getGain()));
    }

    class UIHelper {
        TextView totalBalance;
        TextView totalBalancePlusGain;

        TextView itemLastDate;
        TextView itemLastPercent;
        TextView itemLastGain;

        TextView itemTotalDate;
        TextView itemTotalPercent;
        TextView itemTotalGain;

        UIHelper(View v){
            totalBalance = (TextView) v.findViewById(R.id.fragment_summary_total_balance);
            totalBalancePlusGain = (TextView) v.findViewById(R.id.fragment_summary_total_balance_plus_gain);

            View contentLast = v.findViewById(R.id.item_content_last_balance);
            itemLastDate = (TextView) contentLast.findViewById(R.id.item_balance_date);
            itemLastPercent = (TextView) contentLast.findViewById(R.id.item_balance_percent);
            itemLastGain = (TextView) contentLast.findViewById(R.id.item_total_balance);

            View contentTotal = v.findViewById(R.id.item_content_total_balance);
            itemTotalDate = (TextView) contentTotal.findViewById(R.id.item_balance_date);
            itemTotalPercent = (TextView) contentTotal.findViewById(R.id.item_balance_percent);
            itemTotalGain = (TextView) contentTotal.findViewById(R.id.item_total_balance);
        }
    }
}
