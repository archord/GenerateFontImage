/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mseeworld.gamer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author xy
 */
public class GenerateFontImage {
  
  /**
   * 将所有的字体和大小的汉字生成在一幅图中
   */
  public void outToOneImg() {
    try {
      String rootPath = "e:\\font\\allInOne";
      String tstr = getCommonGB2312();

      String fontName[] = {"宋体", "楷体", "黑体", "幼圆", "微软雅黑"}; //"宋体", "楷体", "黑体", "幼圆", "微软雅黑"
      int fontSize[] = {16, 32, 64}; //16, 32, 64
      int num = tstr.length(); //3755
      int col = num;
      int row = fontSize.length * fontName.length;
      int maxFontSize = fontSize[fontSize.length - 1];
      int width = (maxFontSize + 2) * col;
      int height = (maxFontSize + 2) * row;
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();

      int rowIdx = 1;
      for (String tfont : fontName) {
        System.out.println("开始添加" + tfont + "...");
        for (int tsize : fontSize) {
          Font font = new Font(tfont, Font.PLAIN, tsize);
          ig2.setPaint(Color.black);
          ig2.setFont(font);
          FontMetrics fontMetrics = ig2.getFontMetrics();
          int descent = fontMetrics.getDescent();
          for (int i = 0; i < tstr.length(); i++) {
            ig2.drawString(tstr.charAt(i) + "", i * (maxFontSize + 2), rowIdx * (maxFontSize + 2) - descent);
          }
          rowIdx++;
        }
      }

      String tpath = rootPath;
      File file = new File(tpath);
      if (!file.exists()) {
        file.mkdirs();
      }
      ImageIO.write(bi, "png", new File(tpath + "\\allInOne.png"));

    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
  
  /**
   * 将所有汉字分别按照字体和大小为类别生成在一幅图中，如16号宋体一幅图
   */
  public void outToOneImgForFontAndSize() {
    try {
      String fontName[] = {"微软雅黑"}; //"宋体", "楷体", "黑体", "幼圆", "微软雅黑"
      int fontSize[] = {16, 32, 64};
      String rootPath = "e:\\font\\allInOne";

      int num = 3755;
      byte bytes[] = new byte[2];
      bytes[0] = (byte) 0xb0;

      StringBuilder tsb = new StringBuilder("");
      for (int i = 0; i < 39; i++) {
        bytes[1] = (byte) 0xa0;
        for (int j = 0; j < 94; j++) {
          bytes[1] += 1;
          tsb.append(new String(bytes, "GB2312"));
        }
        bytes[0] += 1;
      }
      bytes[1] = (byte) 0xa0;
      for (int j = 0; j < 89; j++) {
        bytes[1] += 1;
        tsb.append(new String(bytes, "GB2312"));
      }
      String tstr = tsb.toString();

      for (String tfont : fontName) {
        for (int tsize : fontSize) {
          System.out.println("生成：" + tfont + tsize);

          int width = (tsize + 2) * num;
          int height = tsize + 2;

          BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
          Graphics2D ig2 = bi.createGraphics();
          Font font = new Font(tfont, Font.PLAIN, tsize);
          ig2.setFont(font);
          ig2.setPaint(Color.black);
          FontMetrics fontMetrics = ig2.getFontMetrics();
          int descent = fontMetrics.getDescent();

          ig2.drawString(tstr, 0, height - descent);

          String tpath = rootPath;
          File file = new File(tpath);
          if (!file.exists()) {
            file.mkdirs();
          }
          ImageIO.write(bi, "png", new File(tpath + "\\" + tfont + "_" + tsize + ".png"));
        }
      }

    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }

  public void outToOneImg2() {
    try {
      String fontName[] = {"宋体", "楷体", "黑体", "幼圆"};
      int num = 3755;
      int fontSize = 32;
      int width = (fontSize + 2) * num;
      int height = fontSize + 2;
      String rootPath = "e:\\font";

      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D ig2 = bi.createGraphics();
      Font font = new Font(fontName[1], Font.PLAIN, fontSize);
      ig2.setFont(font);
      ig2.setPaint(Color.black);
      FontMetrics fontMetrics = ig2.getFontMetrics();
      int stringHeight = fontMetrics.getAscent();
      int ascent = fontMetrics.getAscent();
      int descent = fontMetrics.getDescent();
      int leading = fontMetrics.getLeading();
      int height1 = fontMetrics.getHeight();
      System.out.printf("Height = %d\tAscent = %d\tDescent = %d\tLeading = %d", height1,
              ascent, descent, leading);

      byte bytes[] = new byte[2];
      bytes[0] = (byte) 0xb0;
      int twidth = 0;
      for (int i = 0; i < 39; i++) {
        bytes[1] = (byte) 0xa0;
        for (int j = 0; j < 94; j++) {
          bytes[1] += 1;
          String tstr = new String(bytes, "GB2312");
//          System.out.println(tstr);

          int stringWidth = fontMetrics.stringWidth(tstr);
          ig2.drawString(tstr, twidth, height - descent);
          twidth += stringWidth;
        }
        bytes[0] += 1;
      }
      bytes[1] = (byte) 0xa0;
      for (int j = 0; j < 89; j++) {
        bytes[1] += 1;
        String tstr = new String(bytes, "GB2312");
//          System.out.println(tstr);

        int stringWidth = fontMetrics.stringWidth(tstr);
        ig2.drawString(tstr, twidth, height - descent);
        twidth += stringWidth;
      }
      String tpath = rootPath;
      File file = new File(tpath);
      if (!file.exists()) {
        file.mkdirs();
      }
      ImageIO.write(bi, "png", new File(tpath + "\\allInOne" + fontSize + ".png"));

    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }
  
  /**
   * 分别按照字体和大小，每个汉字每种字体每种大小一幅图
   */
  public void outToEachImg() {
    try {
      String rootPath = "e:\\font\\category";
      String tstr = getCommonGB2312();

      String fontName[] = {"宋体", "楷体", "黑体", "幼圆", "微软雅黑"}; //"宋体", "楷体", "黑体", "幼圆", "微软雅黑"
      int fontSize[] = {16, 32, 64}; //16, 32, 64
      int num = tstr.length(); //3755
      int col = num;
      int row = fontSize.length * fontName.length;
      int maxFontSize = fontSize[fontSize.length - 1];
      int width = (maxFontSize) * 1;
      int height = (maxFontSize) * 1;

      for (String tfont : fontName) {
        for (int tsize : fontSize) {
          System.out.println("开始生成" + tfont + "_" + tsize + "...");
          for (int i = 0; i < tstr.length(); i++) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D ig2 = bi.createGraphics();
            Font font = new Font(tfont, Font.PLAIN, tsize);
            ig2.setPaint(Color.black);
            ig2.setFont(font);
            FontMetrics fontMetrics = ig2.getFontMetrics();
            int descent = fontMetrics.getDescent();
            ig2.drawString(tstr.charAt(i) + "", (maxFontSize - tsize) / 2, (maxFontSize + tsize) / 2 - descent);

            String tpath = rootPath + "\\" + tstr.charAt(i) + "";
            File file = new File(tpath);
            if (!file.exists()) {
              file.mkdirs();
            }
            ImageIO.write(bi, "png", new File(tpath + "\\" + tfont + "_" + tsize + ".png"));
          }
        }
      }

    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }

  /**
   * 获取GB2312中常用的3755个汉字
   * @return 
   */
  public String getCommonGB2312() {

    byte bytes[] = new byte[2];
    bytes[0] = (byte) 0xb0;
    StringBuilder tsb = new StringBuilder("");
    try {
      for (int i = 0; i < 39; i++) {
        bytes[1] = (byte) 0xa0;
        for (int j = 0; j < 94; j++) {
          bytes[1] += 1;
          tsb.append(new String(bytes, "GB2312"));
        }
        bytes[0] += 1;
      }
      bytes[1] = (byte) 0xa0;
      for (int j = 0; j < 89; j++) {
        bytes[1] += 1;
        tsb.append(new String(bytes, "GB2312"));
      }
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    return tsb.toString();
  }
}
