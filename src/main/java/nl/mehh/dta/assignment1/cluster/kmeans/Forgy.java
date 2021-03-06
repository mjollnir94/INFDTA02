package nl.mehh.dta.assignment1.cluster.kmeans;

import nl.mehh.dta.assignment1.cluster.util.CentroidColors;
import nl.mehh.dta.assignment1.cluster.util.L;
import nl.mehh.dta.assignment1.cluster.util.Tuple;
import nl.mehh.dta.assignment1.cluster.vector.WineDataVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Forgy extends AbsClusteringAlgorithm {

    public Forgy(){ }

    /**
     * This method picks K centroids which are placed on randomly chosen actual observations
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     *
     * @return
     *      ?
     */
    @Override
    protected Tuple<Double, List<Observation>> cluster(int k, int i) {
        relocatedTimer = -1;
        // List of all observations
        List<Observation> observations = new ArrayList<>();
        for(WineDataVector vector : getData().values()) {
            observations.add(
                    new Observation(
                            vector
                    )
            );
        }
        L.t("created list of observations [%d]", observations.size());

        Set<Observation> pickedObservations = new HashSet<>();
        // List of all centroids
        List<Centroid> centroids = new ArrayList<>(k);
        for (int j = 0; j < k; j++) {
            Centroid centroid = new Centroid(CentroidColors.values()[centroids.size()]);

            boolean picked = false;
            Observation observation = null;
            while (!picked) {
                observation = observations.get(new Random().nextInt(observations.size()));
                picked = !pickedObservations.contains(observation);
            }
            L.t("created centroid at [%s]", observation);
            pickedObservations.add(observation);

            centroid.setPoints(observation.getData().getPoints());

            centroids.add(centroid);
        }
        L.t("created list of centroids");
        L.t("%s", centroids);

        cluster(observations, centroids); // Initial clustering

        int loops = 0;
        while (relocate(observations, centroids)) {
            cluster(observations, centroids);
            loops++;

            if(loops > i) break;
        }

        double sse = observations.parallelStream().map(o -> Math.abs(calculateError(o))).reduce(Double::sum).get();
        L.d("Done in %d iterations", relocatedTimer);
        L.i("Calculated SSE: %f", sse);

        return new Tuple<>(sse, observations);
    }


}
