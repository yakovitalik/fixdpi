package com.yakovitalik.fixdpi;

import static com.yakovitalik.fixdpi.gui.Colors.BACKGROUND_COLOR;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import com.yakovitalik.fixdpi.gui.MainWindow;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("CoolProxy");
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setSize(350, 250);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.getContentPane().setBackground(BACKGROUND_COLOR);
    }
}
