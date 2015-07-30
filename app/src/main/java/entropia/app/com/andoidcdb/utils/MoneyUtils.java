package entropia.app.com.andoidcdb.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by renan on 6/29/15.
 */
public class MoneyUtils {

    public static String showAsMoney(BigDecimal money) {
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        usdCostFormat.setMinimumFractionDigits(2);
        usdCostFormat.setMaximumFractionDigits(2);
        return usdCostFormat.format(money.doubleValue());
    }

    public static String showAsMoney(String money) {
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        usdCostFormat.setMinimumFractionDigits(2);
        usdCostFormat.setMaximumFractionDigits(2);
        return usdCostFormat.format(new BigDecimal(money).doubleValue());
    }
}
