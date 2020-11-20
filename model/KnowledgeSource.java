package model;

public class KnowledgeSource extends Thread {
    private int number;
    private int startPoint;
    private int endPoint;
    private NN bestNN;
    private double distance;
    private boolean isPaused;

    public KnowledgeSource(int number, int startPoint, int endPoint){
        this.number = number;
        this.startPoint = startPoint;
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
        return bestNN.getBestOrder();
    }

    @Override
    public void run() {
        try {
            double minDistance = Double.MAX_VALUE;
            NN NN = new NN(new Euclidean());
            for(int i = startPoint; i <= endPoint; i++){
                if(Thread.currentThread().isInterrupted()) break;
                while (isPaused){
                    Thread.sleep(1000);
                }
                distance = NN.run(i);
                if(distance < minDistance){
                    minDistance = distance;
                    bestNN = NN;
                    Blackboard.getInstance().checkIfInTop3(bestNN.getBestOrder(), minDistance);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resumeThread() {
        isPaused = false;
    }

}
