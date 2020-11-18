package view;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private static final String FILE_MENU_TEXT = "File";
    private static final String PROJECT_MENU_TEXT = "Project";
    private static final String ABOUT_MENU_TEXT = "About";

    private static final String OPEN_MENU_TEXT = "Open";
    private static final String SAVE_MENU_TEXT = "Save";
    private static final String RUN_MENU_TEXT = "Run";
    private static final String STOP_MENU_TEXT = "Stop";
    private static final String NEW_MENU_TEXT = "New";

    private JMenu fileMenu;
    private JMenu projectMenu;
    private JMenu aboutMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem runMenuItem;
    private JMenuItem stopMenuItem;
    private JMenuItem newMenuItem;

    private void initMenuItems(){
        openMenuItem = new JMenuItem(MenuBar.OPEN_MENU_TEXT);
        saveMenuItem = new JMenuItem(MenuBar.SAVE_MENU_TEXT);
        runMenuItem = new JMenuItem(MenuBar.RUN_MENU_TEXT);
        stopMenuItem = new JMenuItem(MenuBar.STOP_MENU_TEXT);
        newMenuItem = new JMenuItem(MenuBar.NEW_MENU_TEXT);
    }

    public MenuBar(){
        super();
        initMenuItems();

        fileMenu = new JMenu(MenuBar.FILE_MENU_TEXT);
        projectMenu = new JMenu(MenuBar.PROJECT_MENU_TEXT);
        aboutMenu = new JMenu(MenuBar.ABOUT_MENU_TEXT);

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);

        projectMenu.add(runMenuItem);
        projectMenu.add(stopMenuItem);
        projectMenu.add(newMenuItem);

        add(fileMenu);
        add(projectMenu);
        add(aboutMenu);
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenu getProjectMenu() {
        return projectMenu;
    }

    public JMenu getAboutMenu() {
        return aboutMenu;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public JMenuItem getRunMenuItem() {
        return runMenuItem;
    }

    public JMenuItem getStopMenuItem() {
        return stopMenuItem;
    }

    public JMenuItem getNewMenuItem() {
        return newMenuItem;
    }
}
