package entropia.app.com.andoidcdb.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.callback.CallbackDialog;
import entropia.app.com.andoidcdb.entity.Control;

/**
 * Created by renan on 09/08/15.
 */
public class DialogInitialContribution extends android.support.v4.app.DialogFragment {

    private UIHelper ui;
    private View view;
    private CallbackDialog callback;
    private Control control;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_initial_contribution, container);
        loadValues();
        init();
        return view;
    }

    private void loadValues() {
        control = Control.getControl();

        if (control == null){
            control = new Control();
        }
    }

    private void init() {
        ui = new UIHelper();
        setEvents();
        setValues();
        getDialog().setCanceledOnTouchOutside(false);
        setTitle();
    }

    private void setValues() {
        ui.initialContribution.setText(control.getInitialContribution().toString());
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
                ui.validator.validate();
            }
        };
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
        control.setInitialContribution(new BigDecimal(ui.initialContribution.getText().toString()));
        return control;
    }

    public void setCallback(CallbackDialog callback) {
        this.callback = callback;
    }

    private class UIHelper implements ValidationListener {
        private final Validator validator;

        @NotEmpty(messageResId = R.string.dialog_error_mandatory)
        EditText initialContribution;

        View btnOK;
        View btnCancel;

        public UIHelper() {
            this.initialContribution = (EditText) view.findViewById(R.id.dialog_other_contribution_value);
            this.btnOK = view.findViewById(R.id.dialog_other_contribution_ok);
            this.btnCancel = view.findViewById(R.id.dialog_other_contribution_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }


        @Override
        public void onValidationSucceeded() {
            saveControl();
            callback.positive();
            DialogInitialContribution.this.dismiss();
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(DialogInitialContribution.this.getActivity());

                // Display error messages ;)
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                } else {
                    Toast.makeText(DialogInitialContribution.this.getActivity(), message, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}