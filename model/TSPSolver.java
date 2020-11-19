package model;


public class TSPSolver {

    private int nWorkers;
    private PausableThreadPoolService threadPoolService;
    private int k;

    public TSPSolver(int nWorkers, int k) {
        this.nWorkers = nWorkers;
        this.k = k;
        threadPoolService = new PausableThreadPoolService();
    }

    public PausableThreadPoolService getThreadPoolService() {
        return threadPoolService;
    }

    public void run() {
        int windowSize = TSPData.getInstance().getPoints().size() / nWorkers;
        int start = 0;
        for(int i = 0; i < nWorkers; i++){
            KnowledgeSource ks;
            if(start+windowSize < TSPData.getInstance().getPoints().size())
                ks = new KnowledgeSource(i, k, start, start+windowSize);
            else
                ks = new KnowledgeSource(i, k, start, TSPData.getInstance().getPoints().size()-1);
            System.out.println("Created: " + i + "(" + start + ", " + (start+windowSize) + ")");
            start += windowSize+1;
            threadPoolService.execute(ks);
        }

        threadPoolService.shutdown();
    }

    public void pause() {
        threadPoolService.pause();
    }

    public void resume() {
        threadPoolService.resume();
    }

    public void kill() {
        try{
            threadPoolService.shutdownNow();
        }catch (Exception e){
            System.out.println("Interrupted Threads!");
        }
    }
}
