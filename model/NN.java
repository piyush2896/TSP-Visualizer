package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.IntStream;

public class NN {
    private DistanceMetric metric;
    private int[] bestOrder;
    private Blackboard blackboard;

    public NN(DistanceMetric metric) throws InstantiationException{
        this.blackboard = Blackboard.getInstance();
        this.metric = metric;
        if(!blackboard.isInitialized()){
            throw new InstantiationException("Blackboard is not initialized, first call blackboard.init(...)");
        }

        bestOrder = new int[blackboard.getPoints().size()];
    }

    public int[] getBestOrder() {
        return bestOrder;
    }

    public double run(int startPoint, HashSet<Integer> visited) {
        int index = 0;

        int src = startPoint;
        while(index < blackboard.getPoints().size()-1) {
            visited.add(src);
            bestOrder[index] = src;
            Integer[] closestPoints = argSort(metric.calculate(blackboard.getPoints().get(src), blackboard.getPoints(), visited));
            for(int i = 0 ; i < closestPoints.length; i++) {
                if(!visited.contains(closestPoints[i])) {
                    src = closestPoints[i];
                    break;
                }
            }
            index += 1;
        }
        bestOrder[index] = src;

        double distance = 0.0;
        for(int i = 0; i < bestOrder.length-1; i++) {
            distance += metric.calculate(blackboard.getPoints().get(i), blackboard.getPoints().get(i+1));
        }
        return distance;
    }

    public double run(int startPoint){
        HashSet<Integer> visited = new HashSet<>();
        double totalDistance = run(startPoint, visited);

        return totalDistance;
    }

    private Integer[] argSort(double[] distances) {
        Integer[] indexes = new Integer[distances.length];
        for(int i = 0; i < distances.length; i++) indexes[i] = i;

        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Double.compare(distances[o1], distances[o2]);
            }
        });

        return indexes;
    }

    private int[] argMin(double[] distances){
        return IntStream.range(0, distances.length)
                              .boxed().sorted((i, j)-> (int)(distances[i]-distances[j]))
                              .mapToInt(ele->ele).toArray();
    }

}
