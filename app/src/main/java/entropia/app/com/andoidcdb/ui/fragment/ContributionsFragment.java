package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.adapter.BalanceAdapter;
import entropia.app.com.andoidcdb.adapter.ContributionAdapter;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.entity.Contribution;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.pojo.Sms;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.ui.dialog.DialogDatePicker;
import entropia.app.com.andoidcdb.ui.dialog.DialogInitialContribution;
import entropia.app.com.andoidcdb.ui.dialog.DialogOtherContribution;
import entropia.app.com.andoidcdb.utils.MoneyUtils;


/**
 * Created by renan on 5/4/15.
 */
public class ContributionsFragment extends Fragment {

    public static final int NAME_ITEM = R.string.contributions_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu_;
    public static final boolean IS_CARD_REQUIRED = false;

    private View view;
    private App app;
    private UIHelper ui;
    private List<Sms> smsList;
    private Control control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contributions, container, false);

        loadValues();
        init();
        return view;
    }

    private void init() {
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);

        setEvents();
        setValues();
    }

    private void setEvents() {
        ui.contentInitialContribution.setOnClickListener(onClickInitialContribution());
        ui.contributionListView.setOnItemClickListener(onClickContributionsItem());
    }

    private AdapterView.OnItemClickListener onClickContributionsItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contribution contribution = (Contribution) parent.getItemAtPosition(position);
                DialogOtherContribution.show(ContributionsFragment.this.getFragmentManager(), callbackDialogOtherContribution(), contribution);
            }
        };
    }

    private void loadValues() {
        control = Control.getControl();

        if (control == null){
            control = new Control();
        }

    }

    private void setValues() {
        setTitle();
        setSubTitle(MoneyUtils.showAsMoney(app.totalBalance));
        setIntialContribution();
        setOtherContributionSum();
        setList();
    }

    private void setOtherContributionSum() {
        ui.sumOtherContribution.setText(MoneyUtils.showAsMoney(Contribution.getContributionSum()));
    }

    private void setIntialContribution() {
        if (control != null){
            ui.initialContribution.setText(MoneyUtils.showAsMoney(control.getInitialContribution()));
        } else {
            ui.initialContribution.setText(MoneyUtils.showAsMoney(BigDecimal.ZERO));
        }
    }

    private void setTitle() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(R.string.average_gain_fragment);
        setHasOptionsMenu(true);
    }

    private void setSubTitle(String subTitle) {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setSubtitle(subTitle);
    }

    private void setList() {
        if (Contribution.hasValue()) {
            ui.showContentContributions();
            ui.contributionListView.setAdapter(new ContributionAdapter(this.getActivity(), R.layout.item_contribution, Contribution.getAll()));
        } else {
            ui.showEmptyContent();
        }

    }

    private View.OnClickListener onClickInitialContribution() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInitialContribution.show(ContributionsFragment.this.getFragmentManager(), callbackDialogInitialContribution());
            }
        };
    }

    private CallbackDialog callbackDialogInitialContribution() {
        return new CallbackDialog() {
            @Override
            public void positive() {
                loadValues();
                setValues();
            }

            @Override
            public void negative() {

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_add_contribution:
                showDialogAddContribution();
                return true;
        }
        return false;
    }

    private void showDialogAddContribution() {
        DialogOtherContribution.show(ContributionsFragment.this.getFragmentManager(), callbackDialogOtherContribution(), null);
    }

    private DialogOtherContribution.Callback callbackDialogOtherContribution() {
        return new DialogOtherContribution.Callback() {
            @Override
            public void ok() {
                setValues();
            }

            @Override
            public void remove() {
                setValues();
            }

            @Override
            public void cancel() {

            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contribution, menu);
    }

    class UIHelper {
        // content initialContribution
        TextView initialContribution;
        TextView sumOtherContribution;
        View contentInitialContribution;

        // content contributions
        private View contentContributions;
        private View emptyContent;
        ListView contributionListView;

        UIHelper(View v) {
            contentInitialContribution = v.findViewById(R.id.fragment_contributions_content_initial_contribution);
            sumOtherContribution = (TextView) v.findViewById(R.id.fragment_contributions_sum_other);
            initialContribution = (TextView) v.findViewById(R.id.fragment_contributions_initial_contribution);

            contributionListView = (ListView) v.findViewById(R.id.fragment_contributions_contributions_list);
            contentContributions = v.findViewById(R.id.fragment_contributions_content_list);
            emptyContent = v.findViewById(R.id.fragment_contributions_empty_content);
        }

        public void showContentContributions() {
            contentContributions.setVisibility(View.VISIBLE);
            emptyContent.setVisibility(View.GONE);
        }

        public void showEmptyContent() {
            emptyContent.setVisibility(View.VISIBLE);
            contentContributions.setVisibility(View.GONE);
        }
    }
}
