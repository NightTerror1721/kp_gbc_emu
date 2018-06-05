/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import kp.gbcemu.lcd.GBCanvas;

/**
 *
 * @author Asus
 */
public class Main
{
    public static void main(String[] args)
    {
        GBCanvas canvas = new GBCanvas();
        
        Graphics2D g;
        BufferedImage bi = new BufferedImage(160, 144, BufferedImage.TYPE_INT_RGB);
        WritableRaster wr = (WritableRaster) bi.getData();
        long t1 = System.nanoTime();
        wr.setPixels(0, 0, 160, 144, new int[160 * 144 * 3]);
        long t2 = System.nanoTime();
        System.out.println("executed in " + ((t2 - t1) / 1000000) + " ms\n");
        
        for(int i=0;i<10;i++)
        {
            t1 = System.nanoTime();
            wr.setPixels(0, 0, 160, 144, new int[160 * 144 * 3]);
            t2 = System.nanoTime();
            System.out.println("executed in " + ((t2 - t1) / 1000000) + " ms");
        }
    }
}
