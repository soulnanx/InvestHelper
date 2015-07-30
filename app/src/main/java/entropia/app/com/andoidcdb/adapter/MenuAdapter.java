package entropia.app.com.andoidcdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.pojo.ItemMenu;

/**
 * Created by renan on 4/30/15.
 */
public class MenuAdapter extends ArrayAdapter<ItemMenu> {

    private int resource;

    public MenuAdapter(Context context, int resource, List<ItemMenu> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        ItemMenu item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.icon.setImageResource(item.getIcon());
        holder.label.setText(getContext().getResources().getString(item.getLabel()));

        return convertView;
    }

    class Holder {
        ImageView icon;
        TextView label;

        public Holder(View v){
            icon = (ImageView) v.findViewById(R.id.item_img);
            label = (TextView) v.findViewById(R.id.item_txt_name);
        }
    }
}
