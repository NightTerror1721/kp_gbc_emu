/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu.cpu;

/**
 *
 * @author Asus
 */
public final class Z80
{
    public final Clock clock = new Clock();
    public final Registers reg = new Registers();
    public boolean halt;
    public boolean stop;
    
    public final void reset()
    {
        clock.reset();
        reg.reset();
        halt = false;
        stop = false;
    }
    
    
    
    public final class Clock
    {
        public int M;
        public int T;
        
        private void reset() { M = T = 0; }
    }
    
    public final class Registers
    {
        public int A, B, C, D, E, H, L, F;
        public int PC, SP, I, R;
        public int M, T;
        public boolean IME;
        
        private void reset()
        {
            A = B = C = D = E = H = L = F = 0;
            PC = SP = I = R = 0;
            M = T = 0;
            IME = false;
        }
    }
}
