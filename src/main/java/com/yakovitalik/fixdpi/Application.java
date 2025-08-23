package com.yakovitalik.fixdpi;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("FixDPI 2.0");
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setSize(350, 250);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
    }
}
