package entropia.app.com.andoidcdb.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

import entropia.app.com.andoidcdb.adapter.BalanceAdapter;
import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.pojo.Balance;
import entropia.app.com.andoidcdb.utils.SMSReader;
import entropia.app.com.andoidcdb.pojo.Sms;


public class MainActivity extends ActionBarActivity {

    private App app;
    private UIHelper ui;
    private List<Sms> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        app = (App)getApplication();
        ui = new UIHelper();
        list = new SMSReader().getAllSms(this, SMSReader.BRADESCO_ADDRESS);
        calculateValues();
    }

    private void setList() {
        ui.balanceListView.setAdapter(new BalanceAdapter(this, R.layout.item_gain, app.balanceList));
    }

    private void calculateValues(){
        BigDecimal anterior = BigDecimal.ZERO;
        for (Sms sms : list){
            sms.getAddress();
            sms.getTime();

            if (sms.getMsg().contains(SMSReader.FILTER_BY_INV)){
                DateTime dateTime = new DateTime(Long.parseLong(sms.getTime()));
                String msg1 = sms.getMsg().replaceAll("((.*?)(BX ))", "");
                msg1 = msg1.substring(0, msg1.length() - 1);
                msg1 = msg1.replace(".", "");
                msg1 = msg1.replace(",", ".");

                BigDecimal balance = new BigDecimal(msg1);
                BigDecimal gain = anterior.subtract(balance);
                anterior = balance;

                app.balanceList.add(new Balance(dateTime, balance, gain));
            }
        }

        setList();
    }

    class UIHelper {
        TextView totalBalance;
        ListView balanceListView;

        UIHelper(){
            totalBalance = (TextView) findViewById(R.id.totalBalance);
            balanceListView =  (ListView) findViewById(R.id.balance_list);
        }
    }
}
