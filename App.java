import controller.TSPController;
import model.Blackboard;

public class App {

    public static void main(String[] args) {
        TSPController controller = new TSPController();
        Blackboard.getInstance().addObserver(controller.getMainContainer().getPlotPanel());
    }
}
