package entropia.app.com.andoidcdb.ui.fragment;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.receiver.SMSReceiver;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutMain;
import entropia.app.com.andoidcdb.ui.activity.DrawerLayoutNew;
import entropia.app.com.andoidcdb.ui.dialog.DialogInitialContribution;
import entropia.app.com.andoidcdb.utils.MoneyUtils;


/**
 * Created by renan on 5/4/15.
 */
public class ControlsFragment extends Fragment {

    public static final int NAME_ITEM = R.string.controls_fragment;
    public static final int ICON_ITEM = R.drawable.ic_my_cards_menu_;
    public static final int ID_FRAGMENT = R.id.settings;

    private View view;
    private App app;
    private UIHelper ui;
    private Control control;
    private PackageManager pm;
    private ComponentName componentName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);
        loadValues();
        init();
        return view;
    }

    private void loadValues() {
        control = Control.getControl();

        if (control == null){
            control = new Control();
        }

        pm  = ControlsFragment.this.getActivity().getPackageManager();
        componentName = new ComponentName(ControlsFragment.this.getActivity(), SMSReceiver.class);
    }

    private void init() {
        ((DrawerLayoutNew) getActivity()).getSupportActionBar().setTitle(R.string.controls_fragment);
        app = (App) getActivity().getApplication();
        ui = new UIHelper(view);
        setEvents();
        setValues();
    }

    private void setValues() {
        setIntialContribution();
        setSwitchValue();
    }

    private void setIntialContribution() {
        if (control != null){
            ui.initialContribution.setText(MoneyUtils.showAsMoney(control.getInitialContribution()));
        } else {
            ui.initialContribution.setText("0");
        }
    }

    private void setSwitchValue() {
        int enableCode = pm.getComponentEnabledSetting(componentName);

        switch (enableCode){
            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
                ui.receiveSMS.setChecked(true);
                break;
            case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
                ui.receiveSMS.setChecked(false);
                break;

        }
    }

    private void setEvents() {
        ui.contentInitialContribution.setOnClickListener(onClickTotalBanlance());
        ui.receiveSMS.setOnCheckedChangeListener(onSwitchReceiveSMS());
    }

    private CompoundButton.OnCheckedChangeListener onSwitchReceiveSMS() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                } else {
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                }
            }
        };
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
        @Bind(value = R.id.fragment_control_initial_contribution)
        TextView initialContribution;

        @Bind(value = R.id.fragment_control_content_initial_contribution)
        View contentInitialContribution;

        @Bind(value = R.id.fragment_control_switch_receive_sms_notification)
        Switch receiveSMS;

        UIHelper(View v){
            ButterKnife.bind(this, v);
        }
    }
}
