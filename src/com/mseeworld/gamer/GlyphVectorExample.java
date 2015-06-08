package com.mseeworld.gamer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class GlyphVectorExample {

  public static void main1(String[] args) {
    Frame frame = new Frame("GlyphVectorExample");
    frame.setSize(400, 400);
    frame.add(new CanvasToDisplay());
    frame.setVisible(true);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}

class CanvasToDisplay extends Component {

  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    Font font = new Font("宋体", Font.ITALIC, 64);
    FontRenderContext fontRenderContext = g2D.getFontRenderContext();
    GlyphVector glyphVector = font.createGlyphVector(fontRenderContext, "我爱你");
    g2D.drawGlyphVector(glyphVector, 40, 60);
  }
}
