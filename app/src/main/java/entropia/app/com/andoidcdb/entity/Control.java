package entropia.app.com.andoidcdb.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.math.BigDecimal;

/**
 * Created by enan on 01/08/15.
 */

@Table(name = "controls")
public class Control extends Model {

    @Column
    private String initialContribution;

    @Column
    private long dateFirstContribution;

    public boolean hasValue() {
        return new Select().from(Control.class).execute().size() > 0;
    }

    public static Control getControl() {
        return new Select().from(Control.class).where("id == 1").executeSingle();
    }

    public static BigDecimal getInitialContribution() {
        Control control = getControl();
        if (control != null && (control.initialContribution != null || !control.initialContribution.isEmpty())) {
            return new BigDecimal(control.initialContribution);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public void setInitialContribution(BigDecimal initialContribution) {
        this.initialContribution = initialContribution.toString();
    }

    public long getDateFirstContribution() {
        return dateFirstContribution;
    }

    public void setDateFirstContribution(long dateFirstContribution) {
        this.dateFirstContribution = dateFirstContribution;
    }
}
