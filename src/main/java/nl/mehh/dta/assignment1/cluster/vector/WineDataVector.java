package nl.mehh.dta.assignment1.cluster.vector;

import java.util.HashMap;
import java.util.Map;

public class WineDataVector {

    /**
     * Constant value for that represents the offers that are not taken by a customer
     */
    private static final double NOT_TAKEN = 0;

    /**
     * Constant value for that represents the offers that are taken by a customer
     */
    private static final double     TAKEN = 1;

    /**
     * Identifier for the customer
     */
    private Integer customerIdentifier;

    /**
     * List  of all the offers
     *
     * Key      = Offer identifier
     * Value    = 0: Offer not taken,
     *            1: Offer taken
     */
    private Map<Integer, Double> points;

    /**
     * Constructor for a new WineDataVector.
     *
     * Sets {@link WineDataVector#customerIdentifier} to the {@param cid}
     * Initialize the {@link  WineDataVector#points} Map with a size of 32
     *      and fills it with 32 items with value 0
     */
    public WineDataVector(int cid) {
        this.customerIdentifier = cid;
        points = new HashMap<>(32);
        for (int i = 1; i <= 32; i++) points.put(i, NOT_TAKEN);
    }

    /**
     * sets the {@param id} to {@link WineDataVector#TAKEN}
     */
    public void addOffer(int id) {
        points.put(id, TAKEN);
    }

    /**
     * sets the {@param id} to {@link WineDataVector#NOT_TAKEN}
     */
    public void removeOffer(int id) {
        points.put(id, NOT_TAKEN);
    }

    /**
     * Checks if the {@link WineDataVector#points} contains {@param id} and if
     *          this id has {@link WineDataVector#TAKEN}
     *
     * @return
     *      {@link java.lang.Boolean}   : TRUE if offer == {@link WineDataVector#TAKEN}
     *                                  : FALSE if offer == {@link WineDataVector#NOT_TAKEN}
     */
    public boolean hasTakenOffer(int id) {
        return (points.get(id) != null) && (points.get(id) == TAKEN);
    }

    /**
     * @return
     *      {@link WineDataVector#customerIdentifier}
     */
    public Integer getCustomerIdentifier() {
        return customerIdentifier;
    }

    /**
     * Sets the {@link WineDataVector#customerIdentifier} to the {@param customerIdentifier}
     */
    public void setCustomerIdentifier(Integer customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }

    public Map<Integer, Double> getPoints() {
        return points;
    }

    public double getPoint(int key){
        return points.containsKey(key) ? points.get(key) : 0;
    }

    public void setPoints(Map<Integer, Double> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "WineDataVector{" +
                "cid=" + customerIdentifier +
                ", offers=" + points +
                '}';
    }
}


