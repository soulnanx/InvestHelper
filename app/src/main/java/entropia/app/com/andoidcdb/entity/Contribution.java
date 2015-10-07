package entropia.app.com.andoidcdb.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by renan on 01/08/15.
 */

@Table(name = "contributions")
public class Contribution extends Model{

    @Column
    private String contribution;

    @Column
    private long dateContribution;

    public static boolean hasValue() {
        return new Select().from(Contribution.class).execute().size() > 0;
    }

    public static BigDecimal  getContributionSum(){
        BigDecimal sum = BigDecimal.ZERO;
        for (Contribution contribution : Contribution.getAll()){
            sum = sum.add(contribution.getContribution());
        }

        return sum;
    }

    private Contribution getContributionById(long id) {
        return new Select()
                .from(Contribution.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Contribution> getAll() {
        return new Select().from(Contribution.class).execute();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contribution that = (Contribution) o;

        return super.getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return (int) (super.getId() ^ (super.getId() >>> 32));
    }
}
