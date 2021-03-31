package app;

import app.view.MainFrame;

import javax.swing.*;

/**
 * Main Loop
 */
public class Main {

    /**
     * MainFrame Runner
     * @param args external arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception ex) {
            System.out.println("setLookAndFeel javax.swing.plaf.nimbus.NimbusLookAndFeel error. " + ex.getMessage());

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println("setLookAndFeel SystemLookAndFeelClassName error. " + e.getMessage());
            }
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
