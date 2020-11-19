package model;

public class KnowledgeSource implements Runnable {
    private int number;
    private int startPoint;
    private int endPoint;
    private int k;
    private KNN bestKNN;
    private double distance;
    private boolean isPaused;

    public KnowledgeSource(int number, int k, int startPoint, int endPoint){
        this.number = number;
        this.startPoint = startPoint;
        this.k = k;
        this.endPoint = endPoint;
        this.isPaused = false;
    }

    public int getNumber() {
        return number;
    }

    public double getDistance() {
        return distance;
    }

    public int[] getOrder(){
        return bestKNN.getBestOrder();
    }

    @Override
    public void run() {
        try {
            double minDistance = Double.MAX_VALUE;
            KNN knn = new KNN(this.k, new Euclidean());
            for(int i = startPoint; i <= endPoint; i++){
                while (isPaused){
                    Thread.sleep(1000);
                }
                distance = knn.run(i);
                if(distance < minDistance){
                    minDistance = distance;
                    bestKNN = knn;
                    RouteData.getInstance().checkIfInTop3(bestKNN.getBestOrder(), minDistance);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

}
