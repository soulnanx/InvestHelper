package entropia.app.com.andoidcdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.app.App;
import entropia.app.com.andoidcdb.pojo.Balance;
import entropia.app.com.andoidcdb.utils.MoneyUtils;

/**
 * Created by renan on 17/05/15.
 */
public class BalanceAdapter extends ArrayAdapter<Balance> {

    private int resource;
    private App app;

    public BalanceAdapter(Context context, int resource, List<Balance> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.app = (App) getContext().getApplicationContext();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Balance item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

        holder.date.setText(fmt.print(item.getDate()));
        holder.gain.setText(MoneyUtils.showAsMoney(item.getGain()));
        holder.balance.setText(MoneyUtils.showAsMoney(item.getBalance()));

        return convertView;
    }

    class Holder {
        TextView date;
        TextView gain;
        TextView balance;

        public Holder(View v) {
            date = (TextView) v.findViewById(R.id.item_gain_date);
            gain = (TextView) v.findViewById(R.id.item_gain_gain);
            balance = (TextView) v.findViewById(R.id.item_gain_balance);
        }

    }
}