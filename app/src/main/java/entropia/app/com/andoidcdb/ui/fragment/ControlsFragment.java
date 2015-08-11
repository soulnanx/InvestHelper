package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.utils.MoneyUtils;


/**
 * Created by renan on 5/4/15.
 */
public class ControlsFragment extends Fragment {

    public static final int NAME_ITEM = R.string.controls_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu_;
    public static final boolean IS_CARD_REQUIRED = false;

    private View view;
    private App app;
    private UIHelper ui;
    private Control control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);
        loadValues();
        init();
        return view;
    }

    private void loadValues() {
        control = Control.getControl();
    }

    private void init() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(R.string.controls_fragment);
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
        setEvents();
        setValues();
    }

    private void setValues() {
        ui.initialContribution.setText(MoneyUtils.showAsMoney(control.getInitialContribution()));
    }

    private void setEvents() {
        ui.contentInitialContribution.setOnClickListener(onClickTotalBanlance());
    }

    private View.OnClickListener onClickTotalBanlance() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInitialContribution.show(ControlsFragment.this.getFragmentManager(), callbackDialogInitialContribution());
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

    class UIHelper {
        TextView initialContribution;
        View contentInitialContribution;

        UIHelper(View v){
            contentInitialContribution = v.findViewById(R.id.fragment_control_content_initial_contribution);
            initialContribution = (TextView)v.findViewById(R.id.fragment_control_initial_contribution);
        }
    }
}
