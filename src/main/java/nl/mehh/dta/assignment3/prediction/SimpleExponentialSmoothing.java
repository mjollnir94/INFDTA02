package nl.mehh.dta.assignment3.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 19-6-2016.
 */
public class SimpleExponentialSmoothing implements SmoothingAlgorithm {

    Map<Integer, Double> values;
    Map<Integer, Double> smoothedValues;
    int startOffset;
    double dataSmoothingFactor;
    int forecastUntilStep;
    int lastStep;
    List<String> printables;

    public SimpleExponentialSmoothing(Map<Integer, Double> values, int startOffset, double dataSmoothingFactor, int forecastUntilStep) {
        this.values = values;
        this.startOffset = startOffset;
        this.dataSmoothingFactor = dataSmoothingFactor;
        this.forecastUntilStep = forecastUntilStep;

        printables = new ArrayList<>();
    }

    @Override
    public Map<Integer, Double> generateSmoothedValues() {
        if(smoothedValues != null) return smoothedValues;

        Map<Integer, Double> SESValues = new HashMap<>();
        double avgOfStartOffset = 0;

        for (int i = 1; i <= startOffset; i++) {
            avgOfStartOffset += values.get(i);
        }
        avgOfStartOffset = avgOfStartOffset/startOffset;
        SESValues.put(1, avgOfStartOffset);

        for (int i = 2; i <= values.size()+1; i++) {
            double sT = dataSmoothingFactor*(values.get(i-1))+((1-dataSmoothingFactor)*SESValues.get(i-1));
            SESValues.put(i, sT);
        }

        int lastStep = SESValues.size();
        for (int i = lastStep; i <= forecastUntilStep; i++) {
            SESValues.put(i, SESValues.get(lastStep));
            printables.add("SES - Forecasted value of step " + i + ": " + SESValues.get(lastStep));
        }

        smoothedValues = SESValues;
        return SESValues;
    }

    @Override
    public void printPrediction() {
        printables.forEach(System.out::println);
    }

    @Override
    public double calculateError() {
        if(smoothedValues == null) generateSmoothedValues();
        double error = 0;
        int valueCount = 0;
        for (Integer key : values.keySet()) {
            if (
                    values.containsKey(key-1) &&
                    values.containsKey(key) &&
                            smoothedValues.containsKey(key-1))
            {

                double st = (dataSmoothingFactor*values.get(key-1)) + ((1-dataSmoothingFactor) * smoothedValues.get(key-1));
                double x = values.get(key);

                error += Math.pow(st-x,2);
                valueCount++;
            }
        }
        return Math.sqrt(error / valueCount);
    }


}
