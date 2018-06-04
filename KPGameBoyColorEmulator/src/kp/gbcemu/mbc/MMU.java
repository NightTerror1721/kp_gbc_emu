/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu.mbc;

/**
 *
 * @author Asus
 */
public final class MMU
{
    private boolean inbios;
    
    /*private final byte[] bios;
    private final byte[] rom;
    private final byte[] wram;
    private final byte[] eram;
    private final byte[] zram;*/
    
    /**
     * Read byte value from addr address.
     * 
     * @param addr
     * @return 
     */
    public final int rb(int addr)
    {
        return -1;
    }
    
    /**
     * Read Word (short) value from addr address.
     * 
     * @param addr
     * @return 
     */
    public final int rw(int addr)
    {
        return -1;
    }
    
    /**
     * Write byte value to addr address.
     * 
     * @param addr
     * @param value 
     */
    public final void wb(int addr, int value)
    {
        
    }
    
    /**
     * Write Word (short) value to addr address.
     * 
     * @param addr
     * @param value 
     */
    public final void ww(int addr, int value)
    {
        
    }
}
