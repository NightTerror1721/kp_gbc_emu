/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu.lcd;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author Asus
 */
public final class GBCanvas
{
    public static final int WIDTH = 160;
    public static final int HEIGHT = 144;
    
    private final BufferedImage screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final DataBufferInt dataBuffer = (DataBufferInt) screenImage.getRaster().getDataBuffer();
    
    
    public final int[] rawData() { return dataBuffer.getData(0); }
}
