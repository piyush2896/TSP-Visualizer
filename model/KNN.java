package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

public class KNN {
    private int k;
    private DistanceMetric metric;
    private int[] bestOrder;
    private TSPData tspData;

    public KNN(int k, DistanceMetric metric) throws InstantiationException{
        this.k = k;
        this.tspData = TSPData.getInstance();
        this.metric = metric;
        if(!tspData.isInitialized()){
            throw new InstantiationException("TSPData is not initialized, first call tspData.init(...)");
        }

        bestOrder = new int[tspData.getPoints().size()];
    }

    public void run(int startPoint){
        HashSet<Integer> visited = new HashSet<>();
        double totalDistance = run(startPoint, visited);

        System.out.println("Total Distance: " + totalDistance);
    }

    private double run(int src, HashSet<Integer> visited){

        double minDistance=0.0;
        if(visited.size() == tspData.getPoints().size()-1){
            visited.add(src);
            bestOrder[tspData.getPoints().size()-1] = src;

            for(int i = 1; i < bestOrder.length; i++) {
                minDistance += metric.calculate(
                        tspData.getPoints().get(bestOrder[i - 1]), tspData.getPoints().get(bestOrder[i]));
            }

        }else {
            bestOrder[visited.size()] = src;
            visited.add(src);


            int[] closestPoints = argMin(metric.calculate(tspData.getPoints().get(src), tspData.getPoints(), visited));

            minDistance = Double.MAX_VALUE;
            int minPoint = -1;
            for (int i = 0; i < k; i++) {
                if (visited.contains(closestPoints[i])) continue;

                double distance = run(closestPoints[i], visited);

                if (minDistance > distance) {
                    minPoint = closestPoints[i];
                    minDistance = distance;
                }

            }

            bestOrder[visited.size()] = minPoint;
        }
        visited.remove(src);

        return minDistance;

    }

    private int[] argMin(double[] distances){
        int[] sortedIndices = IntStream.range(0, distances.length)
                              .boxed().sorted((i, j)-> (int)(distances[i]-distances[j]))
                              .mapToInt(ele->ele).toArray();
        return sortedIndices;
    }

    public static void main(String[] args) {
        TSPData tspData = TSPData.getInstance();
        tspData.init("C:\\Piyush\\Fall2020\\CSE564\\Assignment05\\CSE564-Assign05\\Data\\wi29.tsp");

        try {
            KNN knn = new KNN(1, new Euclidean());

            knn.run(0);
            ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, 300, 300);
            for(int order: knn.bestOrder){
                Point p1 = tspData.getPoints().get(order);
                Point p2 = scaledPoints.get(order);
                System.out.println(order + "\t(" + p1.getX() + " " + p2.getY() + ") " + "\t(" + p2.getX() + " " + p2.getY() + ")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
