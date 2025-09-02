package com.yakovitalik.fixdpi.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

public class RoundedButton extends JButton {

    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false);  // отключает заливку по умолчанию
        setFocusPainted(false);       // отключает рамку фокуса
        setBorderPainted(false);      // отключает стандартную рамку
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Цвет фона
        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY); // при нажатии
        } else {
            g2.setColor(getBackground());  // обычный фон
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Если хочешь рисовать рамку — сделай это тут
    }
}
