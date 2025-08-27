package com.yakovitalik.fixdpi.gui;

import static javax.swing.JFrame.HIDE_ON_CLOSE;

import java.net.URL;
import javax.swing.ImageIcon;

public class AboutStart
{
    public static void start()
    {
        About about = new About("О программе");
        about.setVisible(true);
        about.setDefaultCloseOperation(HIDE_ON_CLOSE);
        about.setSize(510, 300);
        about.setResizable(false);
        about.setLocationRelativeTo(null);
        URL imageUrl = About.class.getClassLoader().getResource("images/logo.png");
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        about.setIconImage(imageIcon.getImage());
    }
}
