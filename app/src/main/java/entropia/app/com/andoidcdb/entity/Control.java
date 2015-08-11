package entropia.app.com.andoidcdb.entity;

import java.math.BigDecimal;

import entropia.app.com.andoidcdb.db.DataBaseAdapter;

/**
 * Created by renan on 01/08/15.
 */
public class Control {

    private long id;
    private String initialContribution;
    private long dateFirstContribution;

    public Long save() {
        if (DataBaseAdapter.getInstance() != null) {
            return (Long) DataBaseAdapter.getInstance().getAdapter().store(this);
        }
        return null;
    }

    public int update(Control newControl) {
        if (DataBaseAdapter.getInstance() != null) {
            return DataBaseAdapter.getInstance().getAdapter().update(newControl, this);
        }
        return 0;
    }

    public boolean hasValue() {
        if (DataBaseAdapter.getInstance() != null) {
            if (DataBaseAdapter.getInstance().getAdapter().count(Control.class) > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void saveOrUpdate() {
        if (DataBaseAdapter.getInstance() != null) {
            if (DataBaseAdapter.getInstance().getAdapter().count(Control.class) > 0) {
                Control savedControl = getControl();
                DataBaseAdapter.getInstance().getAdapter().update(this, savedControl);
            } else {
                DataBaseAdapter.getInstance().getAdapter().store(this);
            }
        }
    }

    public static Control getControl() {
        if (DataBaseAdapter.getInstance() != null) {
            Control c = new Control();
            c.setId(1);

            return DataBaseAdapter.getInstance().getAdapter().findFirst(c);
        }
        return null;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getInitialContribution() {
        if (initialContribution == null || initialContribution.isEmpty()){
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(initialContribution);
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
