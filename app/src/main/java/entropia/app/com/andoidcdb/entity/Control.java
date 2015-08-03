package entropia.app.com.andoidcdb.entity;

/**
 * Created by renan on 01/08/15.
 */
public class Control {

    private long id;
    private String initialContribution;
    private long dateFirstContribution;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitialContribution() {
        return initialContribution;
    }

    public void setInitialContribution(String initialContribution) {
        this.initialContribution = initialContribution;
    }

    public long getDateFirstContribution() {
        return dateFirstContribution;
    }

    public void setDateFirstContribution(long dateFirstContribution) {
        this.dateFirstContribution = dateFirstContribution;
    }
}
