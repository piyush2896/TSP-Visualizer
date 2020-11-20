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

//    private double run(int src, HashSet<Integer> visited){
//
//        double minDistance=0.0;
//        if(visited.size() == tspData.getPoints().size()-1){
//            visited.add(src);
//            bestOrder[tspData.getPoints().size()-1] = src;
//
//            for(int i = 1; i < bestOrder.length; i++) {
//                minDistance += metric.calculate(
//                        tspData.getPoints().get(bestOrder[i - 1]), tspData.getPoints().get(bestOrder[i]));
//            }
//
//        }else {
//            bestOrder[visited.size()] = src;
//            visited.add(src);
//
//
//            Integer[] closestPoints = argSort(metric.calculate(tspData.getPoints().get(src), tspData.getPoints(), visited));
//
//            minDistance = Double.MAX_VALUE;
//            int minPoint = -1;
//            for (int i = 0; i < k; i++) {
//                if (visited.contains(closestPoints[i])) continue;
//
//                double distance = run(closestPoints[i], visited);
//
//                if (minDistance > distance) {
//                    minPoint = closestPoints[i];
//                    minDistance = distance;
//                }
//
//            }
//
//            bestOrder[visited.size()] = minPoint;
//        }
//        visited.remove(src);
//
//        return minDistance;
//
//    }

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

    public static void main(String[] args) {
        TSPData tspData = TSPData.getInstance();
        tspData.init("C:\\Piyush\\Fall2020\\CSE564\\Assignment05\\CSE564-Assign05\\Data\\wi29_2.tsp");

        try {
            NN NN = new NN(new Euclidean());

            NN.run(0);
            ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, 300, 300);
            for(int order: NN.bestOrder){
                Point p1 = tspData.getPoints().get(order);
                Point p2 = scaledPoints.get(order);
                System.out.println(order + "\t(" + p1.getX() + " " + p2.getY() + ") " + "\t(" + p2.getX() + " " + p2.getY() + ")");
            }
            System.out.println("---------");
            NN.run(2);
            scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, 300, 300);
            for(int order: NN.bestOrder){
                Point p1 = tspData.getPoints().get(order);
                Point p2 = scaledPoints.get(order);
                System.out.println(order + "\t(" + p1.getX() + " " + p2.getY() + ") " + "\t(" + p2.getX() + " " + p2.getY() + ")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
