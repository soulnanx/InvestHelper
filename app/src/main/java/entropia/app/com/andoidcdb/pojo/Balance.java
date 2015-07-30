package entropia.app.com.andoidcdb.pojo;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renan on 17/05/15.
 */
public class Balance {
    private String date;
    private BigDecimal balance;
    private BigDecimal gain;

    public Balance(DateTime da, BigDecimal msg, BigDecimal msg1) {
        setDate(da);
        setBalance(msg);
        setGain(msg1);
    }

    public DateTime getDate() {
        DateTime dateTime = new DateTime(Long.parseLong(date));
        return dateTime;
    }

    public void setDate(DateTime dateTime) {
        this.date = String.valueOf(dateTime.getMillis());
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getGain() {
        return gain;
    }

    public void setGain(BigDecimal gain) {
        this.gain = gain;
    }
}
