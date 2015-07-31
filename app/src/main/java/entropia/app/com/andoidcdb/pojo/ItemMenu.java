package entropia.app.com.andoidcdb.pojo;

import java.util.ArrayList;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.ui.fragment.AverageGainFragment;
import entropia.app.com.andoidcdb.ui.fragment.ControlsFragment;

public class ItemMenu {

    private String fragmentName;
    private int label;
    private int icon;
    private boolean isCardRequired;

    private ItemMenu(Class fragment) {
        this.fragmentName =  fragment.getName();
        try {
            this.label = fragment.getDeclaredField("NAME_ITEM").getInt(Integer.class);
            this.icon = fragment.getDeclaredField("ICON_ITEM").getInt(Integer.class);
            this.isCardRequired = fragment.getDeclaredField("IS_CARD_REQUIRED").getBoolean(Boolean.class);
        } catch (Exception e) {
            this.label = R.string.average_gain_fragment;
            this.icon = android.R.drawable.ic_media_play;
            this.isCardRequired = false;
        }
    }

    public static ArrayList<ItemMenu> getItemsMenu(){
        ArrayList<ItemMenu> list = new ArrayList<ItemMenu>();
        list.add(new ItemMenu(AverageGainFragment.class));
        list.add(new ItemMenu(ControlsFragment.class));

        return list;
    }

    public boolean isCardRequired() {
        return isCardRequired;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public int getLabel() {
        return label;
    }

    public int getIcon() {
        return icon;
    }

}