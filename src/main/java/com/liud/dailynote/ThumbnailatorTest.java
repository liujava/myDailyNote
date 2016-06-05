package com.liud.dailynote;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/************************************************************************************
 * Copyright (c) 2016 ©  All Rights Reserved.
 * This software is published under liudong.
 * Software License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * <p>
 * File name:      ThumbnailatorTest.java
 * Create on:      2016/6/5
 * Author :        liudong
 * <p>
 * ChangeList
 * ----------------------------------------------------------------------------------
 * Date									Editor						ChangeReasons
 * 2016/6/5			                    liudong				    Create
 ************************************************************************************/
public class ThumbnailatorTest {

    public static void main(String[] args) throws IOException {
        // 1 对图片指定比例压缩
        ThumbnailatorTest thumbnailatorTest = new ThumbnailatorTest();
        thumbnailatorTest.testYS1();
        thumbnailatorTest.testYS2();
        thumbnailatorTest.testYS3();
        thumbnailatorTest.testYS4();
        thumbnailatorTest.testYS5();
        thumbnailatorTest.testYS6();
        thumbnailatorTest.testYS7();
        thumbnailatorTest.testYS8();
    }

    public void testYS1() throws IOException {
        String result = "src/main/resources/images/";
        Thumbnails.of(result + "sijili.jpg").size(200, 300).toFile(result + "image_200x300.jpg");
    }

    public void testYS2() throws IOException {
        String result = "src/main/resources/images/";
        Thumbnails.of(result + "sijili.jpg").scale(0.5f).toFile(result + "image_0.5f.jpg");
    }

    public void testYS3() throws IOException {
        String result = "src/main/resources/images/";
        Thumbnails.of(result + "sijili.jpg").scale(1.0f).rotate(180).toFile(result + "image_r180.jpg");
    }

    public void testYS4() throws IOException {
        String result = "src/main/resources/images/";
        Thumbnails.of(result + "sijili.jpg").scale(1.0f).outputFormat("png").toFile(result + "image_sijiali.png");
    }

    public void testYS5() throws IOException {
        String result = "src/main/resources/images/";
        Thumbnails.of(result + "sijili.jpg").scale(1.0f).sourceRegion(Positions.CENTER, 400,
                400).toFile(result + "image_center.png");
        Thumbnails.of(result + "sijili.jpg").sourceRegion(100, 100, 100, 100).scale(1.0f).toFile(
                result + "image_100_4.jpg");
    }

    public void testYS6() throws IOException {
        String result = "src/main/resources/images/";
        // watermark 参数 1.位置 2.水印图片 3.透明度
         Thumbnails.of(result + "sijili.jpg").scale(1.0f).watermark(Positions.CENTER, ImageIO.read(new File(result + "warter.jpg")), 0.25f).toFile(result + "image_warter.jpg");
    }

    public void testYS7() throws IOException {
        String result = "src/main/resources/images/";
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, 10, 10);
        char[] data = "liudTest".toCharArray();
        g.drawChars(data, 0, data.length, 5, 32);

        // watermark 参数 1.位置 2.水印 3.透明度
        Thumbnails.of(result + "sijili.jpg").scale(1.0f).watermark(Positions.CENTER, bi, 1.0f).toFile(result + "image_warter_liud.jpg");
    }

    private void testYS8() throws IOException {
        String result = "src/main/resources/images/";
        OutputStream os = new FileOutputStream(result + "sijili_out.jpg");

        Image image = ImageIO.read(new File(result + "sijili.jpg"));

        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image.getScaledInstance(100, 100, image.SCALE_SMOOTH), 0, 0, null);

        ImageIO.write(bufferedImage, "jpg",os);
        os.close();
    }
}
