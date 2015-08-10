package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);
        init();
        return view;
    }

    private void init() {
        ((DrawerLayoutMain) getActivity()).getSupportActionBar().setTitle(R.string.controls_fragment);
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
        setEvents();
    }

    private void setEvents() {
        ui.totalBalance.setOnClickListener(onClickTotalBanlance());
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

            }

            @Override
            public void negative() {

            }
        };
    }

    class UIHelper {
        View totalBalance;
        ListView balanceListView;

        UIHelper(View v){
            totalBalance = v.findViewById(R.id.totalBalance);
        }
    }
}
