package ca.quadrilateral.rpecalculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPECalculator {
    private static final Map<String, List<Integer>> RPE_TABLE = buildRPETable();

    private static Map<String, List<Integer>> buildRPETable() {
        final Map<String, List<Integer>> table = new HashMap<>();

        table.put("10", Arrays.asList(new Integer[] {100,96,92,89,86,84,81,79,76,74}));
        table.put("9.5", Arrays.asList(new Integer[] {98,94,91,88,85,82,80,77,75,72}));
        table.put("9", Arrays.asList(new Integer[]   {96,92,89,86,84,81,79,76,74,71}));
        table.put("8.5", Arrays.asList(new Integer[] {94,91,88,85,82,80,77,75,72,69}));
        table.put("8", Arrays.asList(new Integer[]   {92,89,86,84,81,79,76,74,71,68}));
        table.put("7.5", Arrays.asList(new Integer[] {91,88,85,82,80,77,75,72,69,67}));
        table.put("7", Arrays.asList(new Integer[]   {89,86,84,81,79,76,74,71,68,65}));
        table.put("6.5", Arrays.asList(new Integer[] {88,85,82,80,77,75,72,69,67,64}));
        table.put("6", Arrays.asList(new Integer[]   {86,84,81,79,76,74,71,68,65,63}));

        return table;
    }

    private final Double weight;
    private final int reps;
    private final String rpe;
    private final int targetReps;
    private final String targetRpe;

    public RPECalculator(
            final Double weight,
            final int reps,
            final String rpe,
            final int targetReps,
            final String targetRpe) {

        this.weight = weight;
        this.reps = reps;
        this.rpe = rpe;
        this.targetReps = targetReps;
        this.targetRpe = targetRpe;
    }

    public double estimated1RM() {
        return calculateEstimated1RM(weight, rpe, reps);
    }

    public double targetWeight() {
        return calculateTargetWeight(estimated1RM(), targetRpe, targetReps);
    }

    private double lookupPercentage(final String rpe, final int numberOfReps) {
        final List<Integer> percentageList = RPE_TABLE.get(rpe);
        return percentageList.get(numberOfReps - 1) * .01;
    }

    private double calculateEstimated1RM(final double weight, final String rpe, final int numberOfReps) {
        final double percentage = lookupPercentage(rpe, numberOfReps);
        return weight / percentage;
    }

    private double calculateTargetWeight(final double estimated1RM, final String rpe, final int numberOfReps) {
        final double percentage = lookupPercentage(rpe, numberOfReps);
        return estimated1RM * percentage;
    }
}
