package nl.mehh.dta;

import nl.mehh.dta.algorithm.ClusteringStrategy;
import nl.mehh.dta.algorithm.kmeans.Forgy;
import nl.mehh.dta.data.Loader;
import nl.mehh.dta.vector.WineDataVector;

import java.util.List;
import java.util.Map;
import nl.mehh.dta.algorithm.AbsClusteringAlgorithm.Observation;


public class Main {

    /**
     * Instance of the Main class
     */
    private static Main instance;

    /**
     * Getter for {@link nl.mehh.dta.Main#instance}
     * Creates the instance if it is null
     *
     * @return
     *      {@link nl.mehh.dta.Main#instance}
     */
    public static Main getInstance() {
        if (instance == null) instance = new Main();
        return instance;
    }

    /**
     * The data set for the application.
     *
     * Key      = CustomerIdentifier
     * Value    = {@link nl.mehh.dta.vector.WineDataVector}
     */
    private Map<Integer, WineDataVector> data;

    /**
     * Main entry point for the application.
     *
     * @param args
     *      A list of all commandline arguments
     */
    public static void main(String[] args) {
        Main.getInstance().init();
        int k = 0, l = 0;
        try {
            k = Integer.parseInt(args[0]);
            l = Integer.parseInt(args[1]);
        } catch (Exception e) {
            if(args.length != 2) System.out.println("You should pass 2 Numeric arguments!");
            if(args.length == 2) System.out.println("The passed arguments should both be numbers!");
            System.exit(0);
        }
        Main.getInstance().start(k,l);
    }

    /**
     * Private constructor to deny any more instances
     */
    private Main() {}

    /**
     * init method for the application, Initiates the data set
     */
    public void init() {
        System.out.println("init");
        data = Loader.load("WineData.csv");
    }

    /**
     * start method that does everything from calculation till printing
     */
    public void start(int k, int l) {
        System.out.println("start");

        List<Observation> observations = ClusteringStrategy.getInstance().cluster(
                Forgy.getInstance(),
                k,
                l
        );

        for (Observation o : observations
             ) {
            System.out.println(observations);
        };
    }

    /**
     * Getter for the data set {@link nl.mehh.dta.Main#data}
     * @return
     *      {@link nl.mehh.dta.Main#data}
     */
    public Map<Integer, WineDataVector> getData() {
        return data;
    }
}
