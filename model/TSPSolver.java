package model;


public class TSPSolver {

    private int nWorkers;
    private PausableThreadPoolService threadPoolService;

    public TSPSolver(int nWorkers) {
        this.nWorkers = nWorkers;
        threadPoolService = new PausableThreadPoolService();
    }

    public PausableThreadPoolService getThreadPoolService() {
        return threadPoolService;
    }

    public void run() {
        int windowSize = Blackboard.getInstance().getPoints().size() / nWorkers;
        int start = 0;
        for(int i = 0; i < nWorkers; i++){
            KnowledgeSource ks;
            if(start+windowSize < Blackboard.getInstance().getPoints().size())
                ks = new KnowledgeSource(i, start, start+windowSize);
            else
                ks = new KnowledgeSource(i, start, Blackboard.getInstance().getPoints().size()-1);
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
