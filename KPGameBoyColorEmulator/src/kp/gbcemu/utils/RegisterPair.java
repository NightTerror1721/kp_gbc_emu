/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu.utils;

/**
 *
 * @author Asus
 */
public enum RegisterPair
{
    BC,
    DE,
    HL,
    SP;
    
    private static final RegisterPair[] VALUES = values();
    public static final RegisterPair decode(int index) { return VALUES[index]; }
}
