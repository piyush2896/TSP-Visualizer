package controller;

import model.TSPData;
import view.MainContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TSPController {
    private MainContainer mainContainer;

    private void show(){
        mainContainer.setSize(1000, 1000);
        mainContainer.setVisible(true);
    }

    private void addMenuActionListeners(){
        mainContainer.addNewMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TSPData.getInstance().clean();
                mainContainer.updateMenuItemEnabled(true, false, false, true, false);
                mainContainer.revalidate();
                mainContainer.repaint();
            }
        });

        mainContainer.addRunMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: RUN Threads
            }
        });

        mainContainer.addOpenMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String tspFile = fileChooser.getSelectedFile().getAbsolutePath();
                    TSPData.getInstance().clean();
                    TSPData.getInstance().init(tspFile);
                }
                mainContainer.revalidate();
                mainContainer.repaint();
            }
        });

        mainContainer.addSaveMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Save data
            }
        });

        mainContainer.addStopMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: PAUSE Threads
            }
        });
    }

    public TSPController() {
        mainContainer = new MainContainer();
        addMenuActionListeners();
        show();
    }

    //PausableThreadPoolExecutor
}
