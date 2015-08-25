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
import entropia.app.com.andoidcdb.entity.Contribution;
import entropia.app.com.andoidcdb.utils.MoneyUtils;

/**
 * Created by renan on 17/05/15.
 */
public class ContributionAdapter extends ArrayAdapter<Contribution> {

    private int resource;

    public ContributionAdapter(Context context, int resource, List<Contribution> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Contribution item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

        holder.date.setText(fmt.print(item.getDateContribution()));
        holder.value.setText(MoneyUtils.showAsMoney(item.getContribution()));
        holder.days.setText(calculateDays());

        return convertView;
    }

    private String calculateDays() {
        return "20";
    }

    class Holder {
        TextView date;
        TextView value;
        TextView days;

        public Holder(View v) {
            date = (TextView) v.findViewById(R.id.item_contribution_date);
            value = (TextView) v.findViewById(R.id.item_contribution_value);
            days = (TextView) v.findViewById(R.id.item_contribution_days);
        }

    }
}