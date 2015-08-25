package entropia.app.com.andoidcdb.entity;

import java.math.BigDecimal;
import java.util.List;

import entropia.app.com.andoidcdb.db.DataBaseAdapter;

/**
 * Created by renan on 01/08/15.
 */
public class Contribution {

    private long id;
    private String contribution;
    private long dateContribution;

    public Long save() {
        if (DataBaseAdapter.getInstance() != null) {
            return (Long) DataBaseAdapter.getInstance().getAdapter().store(this);
        }
        return null;
    }

    public int update(Contribution newControl) {
        if (DataBaseAdapter.getInstance() != null) {
            return DataBaseAdapter.getInstance().getAdapter().update(newControl, this);
        }
        return 0;
    }

    public static boolean hasValue() {
        if (DataBaseAdapter.getInstance() != null) {
            if (DataBaseAdapter.getInstance().getAdapter().count(Contribution.class) > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static BigDecimal  getContributionSum(){
        BigDecimal sum = BigDecimal.ZERO;
        for (Contribution contribution : Contribution.getAll()){
            sum = sum.add(contribution.getContribution());
        }

        return sum;
    }

    public void saveOrUpdate() {
        if (DataBaseAdapter.getInstance() != null) {

            if (this.getId() > 0){
                Contribution savedContribution = getContributionById(this.getId());
                DataBaseAdapter.getInstance().getAdapter().update(this, savedContribution);
            } else {
                DataBaseAdapter.getInstance().getAdapter().store(this);
            }
        }
    }

    private Contribution getContributionById(long id) {
        Contribution c = new Contribution();
        c.setId(id);
        return DataBaseAdapter.getInstance().getAdapter().findFirst(c);
    }

    public static List<Contribution> getAll() {
        if (DataBaseAdapter.getInstance() != null) {
            return DataBaseAdapter.getInstance().getAdapter().findAll(Contribution.class);
        }
        return null;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getContribution() {
        if (contribution == null || contribution.isEmpty()){
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(contribution);
        }

    }

    public void setContribution(BigDecimal contribution) {
        this.contribution = contribution.toString();
    }

    public long getDateContribution() {
        return dateContribution;
    }

    public void setDateContribution(long dateContribution) {
        this.dateContribution = dateContribution;
    }

    public int delete() {
        return DataBaseAdapter.getInstance().getAdapter().delete(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contribution that = (Contribution) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
