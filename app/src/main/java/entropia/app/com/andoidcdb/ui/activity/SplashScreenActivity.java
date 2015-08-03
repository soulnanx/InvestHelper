package entropia.app.com.andoidcdb.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.codeslap.persistence.SqlAdapter;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.pojo.Sms;
import entropia.app.com.andoidcdb.utils.NavigationUtils;
import entropia.app.com.andoidcdb.utils.SMSReader;


public class SplashScreenActivity extends ActionBarActivity {

    private App app;
    private List<Sms> smsList;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        app = (App) getApplication();
        smsList = new SMSReader().getAllSms(this, SMSReader.BRADESCO_ADDRESS);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask() {

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(SplashScreenActivity.this);
                dialog.setMessage("Lendo sms");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                calculateValues();
                return null;
            }
        }.execute();

    }

    private void calculateValues() {
        BigDecimal lastValue = BigDecimal.ZERO;
        Collections.reverse(smsList);
        for (Sms sms : new LinkedList<>(smsList)) {

            if (sms.getMsg().contains(SMSReader.FILTER_BY_INV)) {
                DateTime receipt = new DateTime(Long.parseLong(sms.getTime()));
                String message = sms.getMsg().replaceAll("((.*?)(BX ))", "");
                message = message.substring(0, message.length() - 1);
                message = message.replace(".", "").replace(",", ".");

                BigDecimal balance = new BigDecimal(message);
                BigDecimal gain = lastValue.subtract(balance);
                lastValue = balance;

                app.balanceList.add(buildBalance(balance, gain, receipt, sms.getId()));

            }
        }
        saveNewBalancesFromSms(app.balanceList);
        Collections.reverse(app.balanceList);
        app.totalBalance = app.balanceList.get(0).getBalance();
    }

    private void saveNewBalancesFromSms(List<Balance> balanceListFromSms) {
        List<Balance> newBalancesToSave = new LinkedList<>();
        List<Balance> savedBalances = Balance.getAll();

        for (Balance fromSms : balanceListFromSms){
            if (savedBalances.contains(fromSms)){
                continue;
            } else {
                newBalancesToSave.add(fromSms);
            }
        }

        if (newBalancesToSave.isEmpty()){
            dialog.dismiss();
            NavigationUtils.navigate(this, DrawerLayoutMain.class, true);
        } else {
            Balance.saveAll(newBalancesToSave, callbackSaveAllBalances());
        }
    }

    private Balance buildBalance(BigDecimal balance, BigDecimal gain, DateTime receipt, String smsId) {
        Balance newBalance = new Balance();
        newBalance.setBalance(balance);
        newBalance.setDate(receipt);
        newBalance.setSmsId(smsId);
        newBalance.setGain(gain.multiply(BigDecimal.ONE.negate()));

        return newBalance;
    }

    private SqlAdapter.ProgressListener callbackSaveAllBalances() {
        return new SqlAdapter.ProgressListener() {
            @Override
            public void onProgressChange(final int percentage) {
                updateProgress(percentage);

                if (percentage >= 100) {
                    dialog.dismiss();
                    NavigationUtils.navigate(SplashScreenActivity.this, DrawerLayoutMain.class, true);
                }
            }
        };
    }

    private void updateProgress(int percentage) {
        dialog.setProgress(percentage);
    }

}
