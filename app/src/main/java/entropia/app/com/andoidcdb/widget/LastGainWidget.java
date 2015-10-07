package entropia.app.com.andoidcdb.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.joda.time.format.DateTimeFormat;

import entropia.app.com.andoidcdb.R;
import entropia.app.com.andoidcdb.entity.Balance;
import entropia.app.com.andoidcdb.entity.Control;
import entropia.app.com.andoidcdb.ui.activity.SplashScreenActivity;
import entropia.app.com.andoidcdb.utils.MoneyUtils;

public class LastGainWidget extends AppWidgetProvider {
    private RemoteViews views;
    private Control control;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        views = new RemoteViews(context.getPackageName(), R.layout.widget_last_gain);

        loadValues();
        setValues(views);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    private void loadValues() {
        control = Control.getControl();

        if (control == null) {
            control = new Control();
        }


    }

    private void setValues(RemoteViews views) {
        Balance lastBalance = Balance.getCurrentBalance();

        views.setTextViewText(R.id.item_balance_percent, lastBalance.calculatePercent());
        views.setTextViewText(R.id.item_balance_date, DateTimeFormat.forPattern("dd/MM/yyyy").print(lastBalance.getDate()));
        views.setTextViewText(R.id.item_total_balance, MoneyUtils.showAsMoney(lastBalance.getGain()));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    private PendingIntent servicePendingIntent(Context ctx, String acao, int appWidgetId) {

        Intent serviceIntent = new Intent(ctx, SplashScreenActivity.class);

        PendingIntent pit = PendingIntent.getService(ctx, appWidgetId, serviceIntent, 0);
        return pit;
    }

}
