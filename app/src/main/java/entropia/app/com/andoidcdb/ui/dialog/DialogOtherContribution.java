package entropia.app.com.andoidcdb.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.entity.Contribution;

/**
 * Created by renan on 09/08/15.
 */
public class DialogOtherContribution extends android.support.v4.app.DialogFragment {

    private UIHelper ui;
    private View view;
    private Callback callback;
    private Contribution contribution;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_other_contributions, container);
        loadValues();
        init();
        return view;
    }

    private void loadValues() {

    }

    private void init() {
        ui = new UIHelper();
        setEvents();
        setValues();
        getDialog().setCanceledOnTouchOutside(false);
        setTitle();
    }

    private void setValues() {

        if (contribution != null){
            ui.value.setText(contribution.getContribution().toString());

            ui.showUpdateBtn();
            ui.showRemoveBtn();
        } else {
            ui.showSaveBtn();
            ui.hideRemoveBtn();
        }
    }

    private void setTitle() {
        getDialog().setTitle(R.string.dialog_title_other_contribution);
    }

    private void setEvents() {
        ui.date.setOnClickListener(onClickDateField());

        ui.btnOK.setOnClickListener(onClickBtnOk());
        ui.btnRemove.setOnClickListener(onClickBtnRemove());
        ui.btnCancel.setOnClickListener(onClickBtnCancel());
    }

    private View.OnClickListener onClickDateField() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogDatePicker() {
                    @Override
                    public void setDate(Calendar c) {
                        ui.date.setText(new SimpleDateFormat("dd MMM yyyy").format(c.getTime()).toUpperCase());
                    }

                }.show(DialogOtherContribution.this.getActivity().getFragmentManager(), "");
            }
        };
    }

    private View.OnClickListener onClickBtnRemove() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeContribution();
                callback.remove();
                DialogOtherContribution.this.dismiss();
            }
        };
    }

    private void removeContribution() {
        contribution.delete();
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
                callback.cancel();
                DialogOtherContribution.this.dismiss();
            }
        };
    }

    public static DialogOtherContribution show(FragmentManager fragmentManager, Callback callback, Contribution contribution) {
        DialogOtherContribution dialog = new DialogOtherContribution();
        dialog.setRetainInstance(true);
        dialog.setCallback(callback);
        dialog.setContribution(contribution);
        dialog.show(fragmentManager, "OtherContribution");

        return dialog;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    public interface Callback {
        void ok();
        void remove();
        void cancel();
    }

    private void saveOrUpdateContribution() {
        buildContribution().saveOrUpdate();
    }

    private Contribution buildContribution() {
        Contribution contribution = new Contribution();
        contribution.setContribution(new BigDecimal(ui.value.getText().toString()));
        // TODO set date contribution
        return contribution;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private class UIHelper implements ValidationListener {
        private final Validator validator;

        @NotEmpty(messageResId = R.string.dialog_error_mandatory)
        EditText value;

        @NotEmpty(messageResId = R.string.dialog_error_mandatory)
        EditText date;

        View btnOK;
        View btnRemove;
        View btnCancel;

        public UIHelper() {
            this.value = (EditText) view.findViewById(R.id.dialog_other_contribution_value);
            this.date = (EditText) view.findViewById(R.id.dialog_other_contribution_date);

            this.btnOK = view.findViewById(R.id.dialog_other_contribution_ok);
            this.btnRemove = view.findViewById(R.id.dialog_other_contribution_remove);
            this.btnCancel = view.findViewById(R.id.dialog_other_contribution_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }


        @Override
        public void onValidationSucceeded() {
            saveOrUpdateContribution();
            callback.ok();
            DialogOtherContribution.this.dismiss();
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(DialogOtherContribution.this.getActivity());

                // Display error messages ;)
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                } else {
                    Toast.makeText(DialogOtherContribution.this.getActivity(), message, Toast.LENGTH_LONG).show();
                }
            }
        }

        public void showUpdateBtn() {
            ((TextView)ui.btnOK).setText(R.string.dialog_update);
        }

        public void showSaveBtn() {
            ((TextView)ui.btnOK).setText(R.string.dialog_save);
        }

        public void showRemoveBtn() {
            ui.btnRemove.setVisibility(View.VISIBLE);
        }

        public void hideRemoveBtn() {
            ui.btnRemove.setVisibility(View.GONE);
        }
    }
}