package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.adapter.BalanceAdapter;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.pojo.Sms;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutNew;
import entropia.app.com.andoidcdb.utils.MoneyUtils;
import entropia.app.com.andoidcdb.utils.SMSReader;


/**
 * Created by renan on 5/4/15.
 */
public class AverageGainFragment extends Fragment {

    public static final int ID_FRAGMENT = R.id.average_gain;
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
        app = (App) getActivity().getApplication();
        setTitle();
        setSubTitle(MoneyUtils.showAsMoney(app.totalBalance));
        ui = new UIHelper(view);
        setList();
    }

    private void setTitle(){
        ((DrawerLayoutNew) getActivity()).getSupportActionBar().setTitle(R.string.average_gain_fragment);
    }

    private void setSubTitle(String subTitle){
        ((DrawerLayoutNew) getActivity()).getSupportActionBar().setSubtitle(subTitle);
    }

    private void setList() {
        ui.balanceListView.setAdapter(new BalanceAdapter(this.getActivity(), R.layout.item_gain, app.balanceList));
    }

    class UIHelper {
        @Bind(value = R.id.balance_list)
        ListView balanceListView;

        UIHelper(View v){
            ButterKnife.bind(this, v);
        }
    }
}
