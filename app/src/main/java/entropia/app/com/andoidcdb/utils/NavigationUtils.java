package entropia.app.com.andoidcdb.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

public class NavigationUtils {

	public static void navigate(Activity activity, Class<?> contextoDestino, boolean clearStack){
		Intent i = new Intent(activity, contextoDestino);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(i);

		if (clearStack){
			activity.finish();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void navigateWithExtra(Activity activity, Class clazz, Map<String, Serializable> extras){
		Intent intent = new Intent(activity, clazz);
		for (Entry<String, Serializable> current : extras.entrySet()) {
			intent.putExtra(current.getKey(), current.getValue());
		}
		activity.startActivity(intent);
	}

	public static void navigateWithResult(Activity activity, Class<?> contextoDestino, int requestCode){
		Intent i = new Intent(activity, contextoDestino);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivityForResult(i, requestCode);
	}
	
	public static void navigateWithExtra(Activity activity, Class<?> contextoDestino, int requestCode, Map<String, Serializable> extras ){
		Intent i = new Intent(activity, contextoDestino);
		for (Entry<String, Serializable> current : extras.entrySet()) {
			i.putExtra(current.getKey(), current.getValue());
		}
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivityForResult(i, requestCode);
	}
}
