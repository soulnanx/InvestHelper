package entropia.app.com.andoidcdb.entity;

import com.codeslap.persistence.SqlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entropia.app.com.andoidcdb.db.DataBaseAdapter;

/**
 * Created by renan on 17/05/15.
 */
public class Balance {
    private long id;
    private String smsId;
    private long date;
    private String balance;
    private String gain;

    public Balance() {
    }

    public DateTime getDate() {
        DateTime dateTime = new DateTime(date);
        return dateTime;
    }

    public Balance save() {
        return (Balance) DataBaseAdapter.getInstance().getAdapter().store(this);
    }

    public static void saveAll(List<Balance> balanceList, SqlAdapter.ProgressListener progressListener) {
        DataBaseAdapter.getInstance().getAdapter().storeCollection(balanceList, progressListener);
    }

    public static List<Balance> getAll() {
        return DataBaseAdapter.getInstance().getAdapter().findAll(Balance.class);
    }

    public static Balance getCurrentBalance() {
        return new LinkedList<Balance>(Balance.getAll()).getLast();
    }

    public BigDecimal calculateTotalGain(BigDecimal initialContribution) {
        return getBalance().subtract(initialContribution);
    }

    public String calculateTotalPercent(BigDecimal totalGain) {
        BigDecimal result = totalGain.multiply(new BigDecimal("100"));

        if (result.equals(BigDecimal.ZERO) || balance.equals(BigDecimal.ZERO)) {
            return "0%";
        } else {
            return result.divide(getBalance(), 2, RoundingMode.HALF_UP).toString() + "%";
        }
    }

    public String calculatePercent() {
        BigDecimal result = getGain().multiply(new BigDecimal("100"));

        if (result.equals(BigDecimal.ZERO) || balance.equals(BigDecimal.ZERO)) {
            return "0%";
        } else {
            return result.divide(getBalance(), 3, RoundingMode.HALF_UP).toString() + "%";
        }
    }

    public BigDecimal calculatePercentBigDecimal() {
        BigDecimal result = getGain().multiply(new BigDecimal("100"));

        if (result.equals(BigDecimal.ZERO) || balance.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        } else {
            return result.divide(getBalance(), 3, RoundingMode.HALF_UP);
        }
    }

    public static ArrayList<String> getBottomValues() {
        ArrayList<String> values = new ArrayList<>();
        for (Balance balance : Balance.getAll()) {
            values.add(DateTimeFormat.forPattern("MM/yy").print(balance.getDate()));
        }
        return values;
    }

    public static ArrayList<String> getLastDaysBottomChart(int days) {
        ArrayList<String> values = new ArrayList<>();
        LinkedList<Balance> balanceList = new LinkedList<>(Balance.getAll());
        Collections.reverse(balanceList);

        for (int i = 0; i < days; i++) {
            if (balanceList.get(i).calculatePercentBigDecimal().compareTo(BigDecimal.ONE) < 0) {
                values.add(DateTimeFormat.forPattern("dd/MM").print(balanceList.get(i).getDate()));
            }
        }
        Collections.reverse(values);
        return values;
    }

    public static ArrayList<Double> getLastDays(int days) {
        List<Balance> balanceList = Balance.getAll();
        ArrayList<Double> lastBalanceList = new ArrayList<>();
        Collections.reverse(balanceList);

        for (int i = 0; i < days; i++) {
            if (balanceList.get(i).calculatePercentBigDecimal().compareTo(BigDecimal.ONE) < 0) {
                lastBalanceList.add(balanceList.get(i).calculatePercentBigDecimal().doubleValue());
            }
        }

        Collections.reverse(lastBalanceList);
        return lastBalanceList;
    }

    public static ArrayList<Double> getChartValues() {
        ArrayList<Double> values = new ArrayList<>();
        for (Balance balance : Balance.getAll()) {
            if (balance.calculatePercentBigDecimal().compareTo(BigDecimal.ONE) < 0) {
                values.add(balance.calculatePercentBigDecimal().doubleValue());
            }
        }
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        return !(smsId != null ? !smsId.equals(balance.smsId) : balance.smsId != null);

    }

    @Override
    public int hashCode() {
        return smsId != null ? smsId.hashCode() : 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(DateTime dateTime) {
        this.date = dateTime.getMillis();
    }

    public BigDecimal getBalance() {
        return new BigDecimal(balance);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.toString();
    }

    public BigDecimal getGain() {
        return new BigDecimal(gain);
    }

    public void setGain(BigDecimal gain) {
        this.gain = gain.toString();
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
