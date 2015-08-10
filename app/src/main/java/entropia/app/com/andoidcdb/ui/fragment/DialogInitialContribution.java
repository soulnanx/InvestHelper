package entropia.app.com.andoidcdb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.entity.Control;

/**
 * Created by renan on 09/08/15.
 */
public class DialogInitialContribution extends android.support.v4.app.DialogFragment {

    private UIHelper ui;
    private View view;
    private App app;
    private CallbackDialog callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_initial_contribution, container);
        init();
        return view;
    }

    private void init() {
        app = (App) this.getActivity().getApplication();
        ui = new UIHelper();
        setEvents();
        getDialog().setCanceledOnTouchOutside(false);
        setTitle();
    }


    private void setTitle() {
        getDialog().setTitle(R.string.dialog_initial_contribution);
    }

    private void setEvents() {
        ui.btnOK.setOnClickListener(onClickBtnOk());
        ui.btnCancel.setOnClickListener(onClickBtnCancel());
    }

    private View.OnClickListener onClickBtnOk() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()){
                    saveControl();
                    callback.positive();
                }
                DialogInitialContribution.this.dismiss();
            }
        };
    }

    private boolean validate() {

        if (ui.initialContribution.getText().toString().isEmpty()){
            return false;
        }

        return true;
    }

    private View.OnClickListener onClickBtnCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.negative();
                DialogInitialContribution.this.dismiss();
            }
        };
    }

    public static DialogInitialContribution show(FragmentManager fragmentManager, CallbackDialog callback) {
        DialogInitialContribution dialog = new DialogInitialContribution();
        dialog.setRetainInstance(true);
        dialog.setCallback(callback);
        dialog.show(fragmentManager, "InitialContribution");

        return dialog;
    }


    private void saveControl() {
       buildControl().saveOrUpdate();
    }

    private Control buildControl() {
        Control control = new Control();
        control.setInitialContribution(ui.initialContribution.getText().toString());
        return control;
    }

    public void setCallback(CallbackDialog callback) {
        this.callback = callback;
    }

    private class UIHelper {

        EditText initialContribution;

        View btnOK;
        View btnCancel;

        public UIHelper() {
            this.initialContribution = (EditText) view.findViewById(R.id.dialog_initial_contribution_value);
            this.btnOK = view.findViewById(R.id.dialog_initial_contribution_ok);
            this.btnCancel = view.findViewById(R.id.dialog_initial_contribution_cancel);
        }
    }
}