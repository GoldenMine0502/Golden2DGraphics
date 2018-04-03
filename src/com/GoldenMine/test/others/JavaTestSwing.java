package com.GoldenMine.test.others;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JavaTestSwing {

    static JFrameWin jFrameWindow;

    public static class MyComponent extends JComponent{

        @Override
        protected void paintComponent(Graphics g) {
            try {
                //prepare a original Image source
                //Image image = ImageIO.read(this.getClass().getResource("duke.png"));

                BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics grap = image.getGraphics();

                grap.setColor(Color.GREEN);
                grap.fillRect(0,0, 50,50);

                int w = image.getWidth(null);
                int h = image.getHeight(null);
                //g.drawImage(image, 0, 0, w, h, null);

                //translate and scale with AffineTransform
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.translate(0, 0);
                Double sx = 2.0;
                Double sy = 2.0;
                affineTransform.setToScale(sx, sy);
                affineTransform.setToScale(sx, sy);
                ((Graphics2D)g).drawImage(image, affineTransform, null);

            } catch (Exception ex) {
                //Logger.getLogger(JavaTestSwing.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static class JFrameWin extends JFrame {
        public JFrameWin(){
            this.setTitle("java-buddy.blogspot.com");
            this.setSize(300, 300);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MyComponent myComponent = new MyComponent();
            this.add(myComponent);
        }
    }

    public static void main(String[] args){
        Runnable doSwingLater = new Runnable(){

            @Override
            public void run() {
                jFrameWindow = new JFrameWin();
                jFrameWindow.setVisible(true);
            }
        };

        SwingUtilities.invokeLater(doSwingLater);

    }

}
