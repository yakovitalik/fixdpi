package com.yakovitalik.fixdpi.gui;

import static javax.swing.JFrame.HIDE_ON_CLOSE;

public class AboutStart
{
    public static void start()
    {
        About about = new About("О программе");
        about.setVisible(true);
        about.setDefaultCloseOperation(HIDE_ON_CLOSE);
        about.setSize(500, 300);
        about.setResizable(false);
        about.setLocationRelativeTo(null);
    }
}
