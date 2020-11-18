package view;

public interface PlotUpdatesListener {

    void newFileRead();
    void newOrderFound(int[] order);
}
