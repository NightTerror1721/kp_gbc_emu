/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.gbcemu.cpu;

import static kp.gbcemu.cpu.OpcodeId.*;
import kp.gbcemu.mbc.MMU;


/**
 *
 * @author Asus
 */
public final class Opcode
{
    private Opcode() {}
    
    private static final OpcodeAction[] OPS = new OpcodeAction[0x100];
    private static final OpcodeAction[] CBOPS = new OpcodeAction[0x100];
    
    private static void op(int opcodeId, OpcodeAction action)
    {
        if(opcodeId <= 0xff)
            OPS[opcodeId] = action;
        if((opcodeId & 0xCB00) != 0)
            CBOPS[opcodeId & 0x00FF] = action;
        throw new IllegalArgumentException();
    }
    
    private static void fz(Z80 cpu, int i)
    {
        cpu.reg.F = 0;
        if((i & 0xff) != 0)
            cpu.reg.F |= 128;
        //cpu.reg.F |= as == 0 ? 0x40 : 0;
    }
    private static void fz(Z80 cpu, int i, int as)
    {
        cpu.reg.F = 0;
        if((i & 0xff) == 0)
            cpu.reg.F |= 128;
        cpu.reg.F |= as != 0 ? 0x40 : 0;
    }
    
    static
    {
        /* Load/Store */
        op(LDrr_bb, (cpu, mmu) -> { cpu.reg.B = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bc, (cpu, mmu) -> { cpu.reg.B = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bd, (cpu, mmu) -> { cpu.reg.B = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_be, (cpu, mmu) -> { cpu.reg.B = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bh, (cpu, mmu) -> { cpu.reg.B = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bl, (cpu, mmu) -> { cpu.reg.B = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ba, (cpu, mmu) -> { cpu.reg.B = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cb, (cpu, mmu) -> { cpu.reg.C = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cc, (cpu, mmu) -> { cpu.reg.C = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cd, (cpu, mmu) -> { cpu.reg.C = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ce, (cpu, mmu) -> { cpu.reg.C = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ch, (cpu, mmu) -> { cpu.reg.C = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cl, (cpu, mmu) -> { cpu.reg.C = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ca, (cpu, mmu) -> { cpu.reg.C = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_db, (cpu, mmu) -> { cpu.reg.D = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dc, (cpu, mmu) -> { cpu.reg.D = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dd, (cpu, mmu) -> { cpu.reg.D = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_de, (cpu, mmu) -> { cpu.reg.D = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dh, (cpu, mmu) -> { cpu.reg.D = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dl, (cpu, mmu) -> { cpu.reg.D = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_da, (cpu, mmu) -> { cpu.reg.D = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_eb, (cpu, mmu) -> { cpu.reg.E = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ec, (cpu, mmu) -> { cpu.reg.E = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ed, (cpu, mmu) -> { cpu.reg.E = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ee, (cpu, mmu) -> { cpu.reg.E = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_eh, (cpu, mmu) -> { cpu.reg.E = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_el, (cpu, mmu) -> { cpu.reg.E = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ea, (cpu, mmu) -> { cpu.reg.E = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hb, (cpu, mmu) -> { cpu.reg.H = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hc, (cpu, mmu) -> { cpu.reg.H = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hd, (cpu, mmu) -> { cpu.reg.H = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_he, (cpu, mmu) -> { cpu.reg.H = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hh, (cpu, mmu) -> { cpu.reg.H = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hl, (cpu, mmu) -> { cpu.reg.H = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ha, (cpu, mmu) -> { cpu.reg.H = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lb, (cpu, mmu) -> { cpu.reg.L = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lc, (cpu, mmu) -> { cpu.reg.L = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ld, (cpu, mmu) -> { cpu.reg.L = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_le, (cpu, mmu) -> { cpu.reg.L = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lh, (cpu, mmu) -> { cpu.reg.L = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ll, (cpu, mmu) -> { cpu.reg.L = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_la, (cpu, mmu) -> { cpu.reg.L = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ab, (cpu, mmu) -> { cpu.reg.A = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ac, (cpu, mmu) -> { cpu.reg.A = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ad, (cpu, mmu) -> { cpu.reg.A = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ae, (cpu, mmu) -> { cpu.reg.A = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ah, (cpu, mmu) -> { cpu.reg.A = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_al, (cpu, mmu) -> { cpu.reg.A = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_aa, (cpu, mmu) -> { cpu.reg.A = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        op(LDrHLm_b, (cpu, mmu) -> { cpu.reg.B = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_c, (cpu, mmu) -> { cpu.reg.C = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_d, (cpu, mmu) -> { cpu.reg.D = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_e, (cpu, mmu) -> { cpu.reg.E = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_h, (cpu, mmu) -> { cpu.reg.H = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_l, (cpu, mmu) -> { cpu.reg.L = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrHLm_a, (cpu, mmu) -> { cpu.reg.A = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDHLmr_b, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.B); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_c, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.C); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_d, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.D); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_e, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.E); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_h, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.H); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_l, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.L); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDHLmr_a, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.B = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.C = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.D = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.E = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.H = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.L = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDrn_b, (cpu, mmu) -> { cpu.reg.A = mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDHLmn, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, mmu.rb(cpu.reg.PC)); cpu.reg.PC++; cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(LDBCmA, (cpu, mmu) -> { mmu.wb((cpu.reg.B << 8) + cpu.reg.C, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDDEmA, (cpu, mmu) -> { mmu.wb((cpu.reg.D << 8) + cpu.reg.E, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDmmA, (cpu, mmu) -> { mmu.wb(mmu.rw(cpu.reg.PC), cpu.reg.A); cpu.reg.PC += 2; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(LDABCm, (cpu, mmu) -> { cpu.reg.A = mmu.rb((cpu.reg.B << 8) + cpu.reg.C); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDADEm, (cpu, mmu) -> { cpu.reg.A = mmu.rb((cpu.reg.D << 8) + cpu.reg.E); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDAmm, (cpu, mmu) -> { cpu.reg.A = mmu.rb(mmu.rw(cpu.reg.PC)); cpu.reg.PC += 2; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(LDBCnn, (cpu, mmu) -> { cpu.reg.C = mmu.rb(cpu.reg.PC); cpu.reg.B = mmu.rb(cpu.reg.PC + 1); cpu.reg.PC += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(LDDEnn, (cpu, mmu) -> { cpu.reg.E = mmu.rb(cpu.reg.PC); cpu.reg.D = mmu.rb(cpu.reg.PC + 1); cpu.reg.PC += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(LDHLnn, (cpu, mmu) -> { cpu.reg.L = mmu.rb(cpu.reg.PC); cpu.reg.H = mmu.rb(cpu.reg.PC + 1); cpu.reg.PC += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(LDSPnn, (cpu, mmu) -> { cpu.reg.SP = mmu.rw(cpu.reg.PC); cpu.reg.PC += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        
        //op(LDHLmm, (cpu, mmu) -> { int i = mmu.rw(cpu.reg.PC); cpu.reg.PC += 2; cpu.reg.L = mmu.rb(i); cpu.reg.H = mmu.rb(i+1); cpu.reg.M = 5; cpu.reg.T = 20; });
        //op(LDmmHL, (cpu, mmu) -> { int i = mmu.rw(cpu.reg.PC); cpu.reg.PC += 2; mmu.ww(i, (cpu.reg.H << 8) + cpu.reg.L); cpu.reg.M = 5; cpu.reg.T = 20; });
        
        op(LDHLIA, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.A); cpu.reg.L = (cpu.reg.L + 1) & 0xff; if(cpu.reg.L == 0) cpu.reg.H = (cpu.reg.H + 1) & 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDAHLI, (cpu, mmu) -> { cpu.reg.A = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.L = (cpu.reg.L + 1) & 0xff; if(cpu.reg.L == 0) cpu.reg.H = (cpu.reg.H + 1) & 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDHLIA, (cpu, mmu) -> { mmu.wb((cpu.reg.H << 8) + cpu.reg.L, cpu.reg.A); cpu.reg.L = (cpu.reg.L - 1) & 0xff; if(cpu.reg.L == 0xff) cpu.reg.H = (cpu.reg.H - 1) & 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDAHLI, (cpu, mmu) -> { cpu.reg.A = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.L = (cpu.reg.L - 1) & 0xff; if(cpu.reg.L == 0xff) cpu.reg.H = (cpu.reg.H - 1) & 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDAIOn, (cpu, mmu) -> { cpu.reg.A = mmu.rb(0xff00 + mmu.rb(cpu.reg.PC)); cpu.reg.PC++; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(LDIOnA, (cpu, mmu) -> { mmu.wb(0xff00 + mmu.rb(cpu.reg.PC), cpu.reg.A); cpu.reg.PC++; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(LDAIOC, (cpu, mmu) -> { cpu.reg.A = mmu.rb(0xff00 + mmu.rb(cpu.reg.C)); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(LDIOCA, (cpu, mmu) -> { mmu.wb(0xff00 + mmu.rb(cpu.reg.C), cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(LDHLSPn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; i += cpu.reg.SP; cpu.reg.H = (i >> 8) & 0xff; cpu.reg.L = i & 0xff; cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(SWAPr_b, (cpu, mmu) -> { int tr = cpu.reg.B; cpu.reg.B = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_c, (cpu, mmu) -> { int tr = cpu.reg.C; cpu.reg.C = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_d, (cpu, mmu) -> { int tr = cpu.reg.D; cpu.reg.D = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_e, (cpu, mmu) -> { int tr = cpu.reg.E; cpu.reg.E = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_h, (cpu, mmu) -> { int tr = cpu.reg.H; cpu.reg.H = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_l, (cpu, mmu) -> { int tr = cpu.reg.L; cpu.reg.L = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        op(SWAPr_a, (cpu, mmu) -> { int tr = cpu.reg.A; cpu.reg.A = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); mmu.wb((cpu.reg.H << 8) + cpu.reg.L, tr); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        
        /* Data Orocessing */
        op(ADDr_b, (cpu, mmu) -> { cpu.reg.A += cpu.reg.B; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_c, (cpu, mmu) -> { cpu.reg.A += cpu.reg.C; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_d, (cpu, mmu) -> { cpu.reg.A += cpu.reg.D; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_e, (cpu, mmu) -> { cpu.reg.A += cpu.reg.E; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_h, (cpu, mmu) -> { cpu.reg.A += cpu.reg.H; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_l, (cpu, mmu) -> { cpu.reg.A += cpu.reg.L; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADDr_a, (cpu, mmu) -> { cpu.reg.A += cpu.reg.A; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        
    }
    
    @FunctionalInterface
    public interface OpcodeAction
    {
        void execute(Z80 cpu, MMU mmu);
    }
}
