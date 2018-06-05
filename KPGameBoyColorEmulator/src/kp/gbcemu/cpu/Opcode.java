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
        {
            if(OPS[opcodeId] != null)
                throw new IllegalArgumentException("Duplicated Operation: " + opcodeId);
            OPS[opcodeId] = action;
        }
        if((opcodeId & 0xCB00) != 0)
        {
            if(CBOPS[opcodeId] != null)
                throw new IllegalArgumentException("Duplicated Operation: " + opcodeId);
            CBOPS[opcodeId & 0x00FF] = action;
        }
        throw new IllegalArgumentException();
    }
    
    private static void fillUnimplementedOps()
    {
        final OpcodeAction action = (cpu, mmu) -> {
            cpu.stop = true;
            throw new RuntimeException("Unimplemented extended (CBOP) instruction at $" + (Integer.toHexString(cpu.reg.PC - 1)));
        };
        for(int i=0;i<0x100;i++)
        {
            if(OPS[i] == null)
                OPS[i] = action;
            if(CBOPS[i] == null)
                OPS[i] = action;
        }
    }
    
    private static void fz(Z80 cpu, int i)
    {
        cpu.reg.F = 0;
        if((i & 0xff) == 0)
            cpu.reg.F |= 128;
        //cpu.reg.F |= as == 0 ? 0x40 : 0;
    }
    private static void fzt(Z80 cpu, int i)
    {
        cpu.reg.F = 0;
        if((i & 0xff) == 0)
            cpu.reg.F |= 128;
        cpu.reg.F |= 0x40;
    }
    
    private static int b(int value) { return value != 0 ? 1 : 0; }
    private static int b(int value, int itrue) { return value != 0 ? itrue : 0; }
    private static int b(int value, int itrue, int ifalse) { return value != 0 ? itrue : ifalse; }
    
    static
    {
        /* Load/Store */
        op(LDrr_bb, (cpu, mmu) -> { /*cpu.reg.B = cpu.reg.B;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bc, (cpu, mmu) -> { cpu.reg.B = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bd, (cpu, mmu) -> { cpu.reg.B = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_be, (cpu, mmu) -> { cpu.reg.B = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bh, (cpu, mmu) -> { cpu.reg.B = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_bl, (cpu, mmu) -> { cpu.reg.B = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ba, (cpu, mmu) -> { cpu.reg.B = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cb, (cpu, mmu) -> { cpu.reg.C = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cc, (cpu, mmu) -> { /*cpu.reg.C = cpu.reg.C;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cd, (cpu, mmu) -> { cpu.reg.C = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ce, (cpu, mmu) -> { cpu.reg.C = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ch, (cpu, mmu) -> { cpu.reg.C = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_cl, (cpu, mmu) -> { cpu.reg.C = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ca, (cpu, mmu) -> { cpu.reg.C = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_db, (cpu, mmu) -> { cpu.reg.D = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dc, (cpu, mmu) -> { cpu.reg.D = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dd, (cpu, mmu) -> { /*cpu.reg.D = cpu.reg.D;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_de, (cpu, mmu) -> { cpu.reg.D = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dh, (cpu, mmu) -> { cpu.reg.D = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_dl, (cpu, mmu) -> { cpu.reg.D = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_da, (cpu, mmu) -> { cpu.reg.D = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_eb, (cpu, mmu) -> { cpu.reg.E = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ec, (cpu, mmu) -> { cpu.reg.E = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ed, (cpu, mmu) -> { cpu.reg.E = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ee, (cpu, mmu) -> { /*cpu.reg.E = cpu.reg.E;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_eh, (cpu, mmu) -> { cpu.reg.E = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_el, (cpu, mmu) -> { cpu.reg.E = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ea, (cpu, mmu) -> { cpu.reg.E = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hb, (cpu, mmu) -> { cpu.reg.H = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hc, (cpu, mmu) -> { cpu.reg.H = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hd, (cpu, mmu) -> { cpu.reg.H = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_he, (cpu, mmu) -> { cpu.reg.H = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hh, (cpu, mmu) -> { /*cpu.reg.H = cpu.reg.H;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_hl, (cpu, mmu) -> { cpu.reg.H = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ha, (cpu, mmu) -> { cpu.reg.H = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lb, (cpu, mmu) -> { cpu.reg.L = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lc, (cpu, mmu) -> { cpu.reg.L = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ld, (cpu, mmu) -> { cpu.reg.L = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_le, (cpu, mmu) -> { cpu.reg.L = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_lh, (cpu, mmu) -> { cpu.reg.L = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ll, (cpu, mmu) -> { /*cpu.reg.L = cpu.reg.L;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_la, (cpu, mmu) -> { cpu.reg.L = cpu.reg.A; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ab, (cpu, mmu) -> { cpu.reg.A = cpu.reg.B; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ac, (cpu, mmu) -> { cpu.reg.A = cpu.reg.C; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ad, (cpu, mmu) -> { cpu.reg.A = cpu.reg.D; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ae, (cpu, mmu) -> { cpu.reg.A = cpu.reg.E; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_ah, (cpu, mmu) -> { cpu.reg.A = cpu.reg.H; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_al, (cpu, mmu) -> { cpu.reg.A = cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(LDrr_aa, (cpu, mmu) -> { /*cpu.reg.A = cpu.reg.A;*/ cpu.reg.M = 1; cpu.reg.T = 4; });
        
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
        op(ADDHL, (cpu, mmu) -> { cpu.reg.A += mmu.rb((cpu.reg.H << 8) + cpu.reg.L); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(ADDn, (cpu, mmu) -> { cpu.reg.A += mmu.rb(cpu.reg.PC); cpu.reg.PC++; fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(ADDHLBC, (cpu, mmu) -> { int h1 = (cpu.reg.H << 8) + cpu.reg.L; h1 += (cpu.reg.B << 8) + cpu.reg.C; if(h1 > 65535) cpu.reg.F |= 0x10; else cpu.reg.F &= 0xEF; cpu.reg.H = (h1 >> 8) & 0xff; cpu.reg.L = h1 & 0xff; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(ADDHLDE, (cpu, mmu) -> { int h1 = (cpu.reg.H << 8) + cpu.reg.L; h1 += (cpu.reg.D << 8) + cpu.reg.E; if(h1 > 65535) cpu.reg.F |= 0x10; else cpu.reg.F &= 0xEF; cpu.reg.H = (h1 >> 8) & 0xff; cpu.reg.L = h1 & 0xff; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(ADDHLHL, (cpu, mmu) -> { int h1 = (cpu.reg.H << 8) + cpu.reg.L; h1 += (cpu.reg.H << 8) + cpu.reg.L; if(h1 > 65535) cpu.reg.F |= 0x10; else cpu.reg.F &= 0xEF; cpu.reg.H = (h1 >> 8) & 0xff; cpu.reg.L = h1 & 0xff; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(ADDHLSP, (cpu, mmu) -> { int h1 = (cpu.reg.H << 8) + cpu.reg.L; h1 += cpu.reg.SP; if(h1 > 65535) cpu.reg.F |= 0x10; else cpu.reg.F &= 0xEF; cpu.reg.H = (h1 >> 8) & 0xff; cpu.reg.L = h1 & 0xff; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(ADDSPn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.SP += i; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(ADCr_b, (cpu, mmu) -> { cpu.reg.A += cpu.reg.B; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_c, (cpu, mmu) -> { cpu.reg.A += cpu.reg.C; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_d, (cpu, mmu) -> { cpu.reg.A += cpu.reg.D; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_e, (cpu, mmu) -> { cpu.reg.A += cpu.reg.E; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_h, (cpu, mmu) -> { cpu.reg.A += cpu.reg.H; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_l, (cpu, mmu) -> { cpu.reg.A += cpu.reg.L; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCr_a, (cpu, mmu) -> { cpu.reg.A += cpu.reg.A; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ADCHL, (cpu, mmu) -> { cpu.reg.A += mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(ADCn, (cpu, mmu) -> { cpu.reg.A += mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.A += b(cpu.reg.F & 0x10); fz(cpu, cpu.reg.A); if(cpu.reg.A > 0xff) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(SUBr_b, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.B; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_c, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.C; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_d, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.D; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_e, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.E; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_h, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.H; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_l, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.L; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBr_a, (cpu, mmu) -> { cpu.reg.A -= cpu.reg.A; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SUBHL, (cpu, mmu) -> { cpu.reg.A -= mmu.rb((cpu.reg.H << 8) + cpu.reg.L); fz(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SUBn, (cpu, mmu) -> { cpu.reg.A -= mmu.rb(cpu.reg.PC); cpu.reg.PC++; fz(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(CPr_b, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_c, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_d, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_e, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_h, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_l, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPr_a, (cpu, mmu) -> { int i = cpu.reg.A; i -= cpu.reg.B; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(CPHL, (cpu, mmu) -> { int i = cpu.reg.A; i -= mmu.rb((cpu.reg.H << 8) + cpu.reg.L); fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(CPn, (cpu, mmu) -> { int i = cpu.reg.A; i -= mmu.rb(cpu.reg.PC); cpu.reg.PC++; fzt(cpu, i); if(i < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(ANDr_b, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.B; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_c, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.C; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_d, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.D; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_e, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.E; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_h, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.H; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_l, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.L; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDr_a, (cpu, mmu) -> { cpu.reg.A &= cpu.reg.A; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ANDHL, (cpu, mmu) -> { cpu.reg.A &= mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(ANDn, (cpu, mmu) -> { cpu.reg.A &= mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(ORr_b, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.B; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_c, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.C; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_d, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.D; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_e, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.E; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_h, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.H; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_l, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.L; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORr_a, (cpu, mmu) -> { cpu.reg.A |= cpu.reg.A; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(ORHL, (cpu, mmu) -> { cpu.reg.A |= mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(ORn, (cpu, mmu) -> { cpu.reg.A |= mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(XORr_b, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.B; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_c, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.C; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_d, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.D; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_e, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.E; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_h, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.H; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_l, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.L; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORr_a, (cpu, mmu) -> { cpu.reg.A ^= cpu.reg.A; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(XORHL, (cpu, mmu) -> { cpu.reg.A ^= mmu.rb((cpu.reg.H << 8) + cpu.reg.L); cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(XORn, (cpu, mmu) -> { cpu.reg.A ^= mmu.rb(cpu.reg.PC); cpu.reg.PC++; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(INCr_b, (cpu, mmu) -> { cpu.reg.B++; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_c, (cpu, mmu) -> { cpu.reg.C++; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_d, (cpu, mmu) -> { cpu.reg.D++; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_e, (cpu, mmu) -> { cpu.reg.E++; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_h, (cpu, mmu) -> { cpu.reg.H++; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_l, (cpu, mmu) -> { cpu.reg.L++; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCr_a, (cpu, mmu) -> { cpu.reg.A++; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCHLm, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L) + 1; i &= 0xff; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); fz(cpu, i); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(DECr_b, (cpu, mmu) -> { cpu.reg.B--; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_c, (cpu, mmu) -> { cpu.reg.C--; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_d, (cpu, mmu) -> { cpu.reg.D--; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_e, (cpu, mmu) -> { cpu.reg.E--; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_h, (cpu, mmu) -> { cpu.reg.H--; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_l, (cpu, mmu) -> { cpu.reg.L--; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECr_a, (cpu, mmu) -> { cpu.reg.A--; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECHLm, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L) - 1; i &= 0xff; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); fz(cpu, i); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(INCBC, (cpu, mmu) -> {  cpu.reg.C = (cpu.reg.C + 1) & 0xff; if(cpu.reg.C == 0) cpu.reg.B = (cpu.reg.B - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCDE, (cpu, mmu) -> {  cpu.reg.E = (cpu.reg.E + 1) & 0xff; if(cpu.reg.E == 0) cpu.reg.D = (cpu.reg.D - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCHL, (cpu, mmu) -> {  cpu.reg.L = (cpu.reg.L + 1) & 0xff; if(cpu.reg.L == 0) cpu.reg.H = (cpu.reg.H - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(INCSP, (cpu, mmu) -> {  cpu.reg.SP = (cpu.reg.SP + 1) & 65535; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        op(DECBC, (cpu, mmu) -> {  cpu.reg.C = (cpu.reg.C - 1) & 0xff; if(cpu.reg.C == 0xff) cpu.reg.B = (cpu.reg.B - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECDE, (cpu, mmu) -> {  cpu.reg.E = (cpu.reg.E - 1) & 0xff; if(cpu.reg.E == 0xff) cpu.reg.D = (cpu.reg.D - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECHL, (cpu, mmu) -> {  cpu.reg.L = (cpu.reg.L - 1) & 0xff; if(cpu.reg.L == 0xff) cpu.reg.H = (cpu.reg.H - 1) & 0xff; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(DECSP, (cpu, mmu) -> {  cpu.reg.SP = (cpu.reg.SP - 1) & 65535; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        /* Bit Manipulation */
        op(BIT0b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x01); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT0m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x01); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES0b, (cpu, mmu) -> { cpu.reg.B &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0c, (cpu, mmu) -> { cpu.reg.C &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0d, (cpu, mmu) -> { cpu.reg.D &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0e, (cpu, mmu) -> { cpu.reg.E &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0h, (cpu, mmu) -> { cpu.reg.H &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0l, (cpu, mmu) -> { cpu.reg.L &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0a, (cpu, mmu) -> { cpu.reg.A &= 0xFE; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES0m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xFE; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET0b, (cpu, mmu) -> { cpu.reg.B |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0c, (cpu, mmu) -> { cpu.reg.C |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0d, (cpu, mmu) -> { cpu.reg.D |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0e, (cpu, mmu) -> { cpu.reg.E |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0h, (cpu, mmu) -> { cpu.reg.H |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0l, (cpu, mmu) -> { cpu.reg.L |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0a, (cpu, mmu) -> { cpu.reg.A |= 0x01; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET0m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x01; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT1b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x02); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT1m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x02); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES1b, (cpu, mmu) -> { cpu.reg.B &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1c, (cpu, mmu) -> { cpu.reg.C &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1d, (cpu, mmu) -> { cpu.reg.D &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1e, (cpu, mmu) -> { cpu.reg.E &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1h, (cpu, mmu) -> { cpu.reg.H &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1l, (cpu, mmu) -> { cpu.reg.L &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1a, (cpu, mmu) -> { cpu.reg.A &= 0xFD; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES1m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xFD; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET1b, (cpu, mmu) -> { cpu.reg.B |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1c, (cpu, mmu) -> { cpu.reg.C |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1d, (cpu, mmu) -> { cpu.reg.D |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1e, (cpu, mmu) -> { cpu.reg.E |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1h, (cpu, mmu) -> { cpu.reg.H |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1l, (cpu, mmu) -> { cpu.reg.L |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1a, (cpu, mmu) -> { cpu.reg.A |= 0x02; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET1m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x02; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT2b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x04); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT2m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x04); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES2b, (cpu, mmu) -> { cpu.reg.B &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2c, (cpu, mmu) -> { cpu.reg.C &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2d, (cpu, mmu) -> { cpu.reg.D &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2e, (cpu, mmu) -> { cpu.reg.E &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2h, (cpu, mmu) -> { cpu.reg.H &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2l, (cpu, mmu) -> { cpu.reg.L &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2a, (cpu, mmu) -> { cpu.reg.A &= 0xFB; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES2m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xFB; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET2b, (cpu, mmu) -> { cpu.reg.B |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2c, (cpu, mmu) -> { cpu.reg.C |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2d, (cpu, mmu) -> { cpu.reg.D |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2e, (cpu, mmu) -> { cpu.reg.E |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2h, (cpu, mmu) -> { cpu.reg.H |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2l, (cpu, mmu) -> { cpu.reg.L |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2a, (cpu, mmu) -> { cpu.reg.A |= 0x04; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET2m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x04; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT3b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x08); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT3m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x08); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES3b, (cpu, mmu) -> { cpu.reg.B &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3c, (cpu, mmu) -> { cpu.reg.C &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3d, (cpu, mmu) -> { cpu.reg.D &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3e, (cpu, mmu) -> { cpu.reg.E &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3h, (cpu, mmu) -> { cpu.reg.H &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3l, (cpu, mmu) -> { cpu.reg.L &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3a, (cpu, mmu) -> { cpu.reg.A &= 0xF7; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES3m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xF7; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET3b, (cpu, mmu) -> { cpu.reg.B |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3c, (cpu, mmu) -> { cpu.reg.C |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3d, (cpu, mmu) -> { cpu.reg.D |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3e, (cpu, mmu) -> { cpu.reg.E |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3h, (cpu, mmu) -> { cpu.reg.H |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3l, (cpu, mmu) -> { cpu.reg.L |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3a, (cpu, mmu) -> { cpu.reg.A |= 0x08; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET3m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x08; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT4b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x10); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT4m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x10); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES4b, (cpu, mmu) -> { cpu.reg.B &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4c, (cpu, mmu) -> { cpu.reg.C &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4d, (cpu, mmu) -> { cpu.reg.D &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4e, (cpu, mmu) -> { cpu.reg.E &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4h, (cpu, mmu) -> { cpu.reg.H &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4l, (cpu, mmu) -> { cpu.reg.L &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4a, (cpu, mmu) -> { cpu.reg.A &= 0xEF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES4m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xEF; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET4b, (cpu, mmu) -> { cpu.reg.B |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4c, (cpu, mmu) -> { cpu.reg.C |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4d, (cpu, mmu) -> { cpu.reg.D |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4e, (cpu, mmu) -> { cpu.reg.E |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4h, (cpu, mmu) -> { cpu.reg.H |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4l, (cpu, mmu) -> { cpu.reg.L |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4a, (cpu, mmu) -> { cpu.reg.A |= 0x10; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET4m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x10; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT5b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x20); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT5m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x20); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES5b, (cpu, mmu) -> { cpu.reg.B &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5c, (cpu, mmu) -> { cpu.reg.C &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5d, (cpu, mmu) -> { cpu.reg.D &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5e, (cpu, mmu) -> { cpu.reg.E &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5h, (cpu, mmu) -> { cpu.reg.H &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5l, (cpu, mmu) -> { cpu.reg.L &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5a, (cpu, mmu) -> { cpu.reg.A &= 0xDF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES5m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xDF; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET5b, (cpu, mmu) -> { cpu.reg.B |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5c, (cpu, mmu) -> { cpu.reg.C |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5d, (cpu, mmu) -> { cpu.reg.D |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5e, (cpu, mmu) -> { cpu.reg.E |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5h, (cpu, mmu) -> { cpu.reg.H |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5l, (cpu, mmu) -> { cpu.reg.L |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5a, (cpu, mmu) -> { cpu.reg.A |= 0x20; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET5m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x20; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT6b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x40); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT6m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x40); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES6b, (cpu, mmu) -> { cpu.reg.B &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6c, (cpu, mmu) -> { cpu.reg.C &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6d, (cpu, mmu) -> { cpu.reg.D &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6e, (cpu, mmu) -> { cpu.reg.E &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6h, (cpu, mmu) -> { cpu.reg.H &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6l, (cpu, mmu) -> { cpu.reg.L &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6a, (cpu, mmu) -> { cpu.reg.A &= 0xBF; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES6m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0xBF; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET6b, (cpu, mmu) -> { cpu.reg.B |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6c, (cpu, mmu) -> { cpu.reg.C |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6d, (cpu, mmu) -> { cpu.reg.D |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6e, (cpu, mmu) -> { cpu.reg.E |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6h, (cpu, mmu) -> { cpu.reg.H |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6l, (cpu, mmu) -> { cpu.reg.L |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6a, (cpu, mmu) -> { cpu.reg.A |= 0x40; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET6m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x40; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(BIT7b, (cpu, mmu) -> { fz(cpu, cpu.reg.B & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7c, (cpu, mmu) -> { fz(cpu, cpu.reg.C & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7d, (cpu, mmu) -> { fz(cpu, cpu.reg.D & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7e, (cpu, mmu) -> { fz(cpu, cpu.reg.E & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7h, (cpu, mmu) -> { fz(cpu, cpu.reg.H & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7l, (cpu, mmu) -> { fz(cpu, cpu.reg.L & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7a, (cpu, mmu) -> { fz(cpu, cpu.reg.A & 0x80); cpu.reg.M = 2; cpu.reg.T = 8; });
        op(BIT7m, (cpu, mmu) -> { fz(cpu, mmu.rb((cpu.reg.H << 8) + cpu.reg.L) & 0x80); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(RES7b, (cpu, mmu) -> { cpu.reg.B &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7c, (cpu, mmu) -> { cpu.reg.C &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7d, (cpu, mmu) -> { cpu.reg.D &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7e, (cpu, mmu) -> { cpu.reg.E &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7h, (cpu, mmu) -> { cpu.reg.H &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7l, (cpu, mmu) -> { cpu.reg.L &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7a, (cpu, mmu) -> { cpu.reg.A &= 0x7F; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RES7m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i &= 0x7F; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SET7b, (cpu, mmu) -> { cpu.reg.B |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7c, (cpu, mmu) -> { cpu.reg.C |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7d, (cpu, mmu) -> { cpu.reg.D |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7e, (cpu, mmu) -> { cpu.reg.E |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7h, (cpu, mmu) -> { cpu.reg.H |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7l, (cpu, mmu) -> { cpu.reg.L |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7a, (cpu, mmu) -> { cpu.reg.A |= 0x80; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SET7m, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); i |= 0x80; mmu.wb((cpu.reg.H << 8) + cpu.reg.L, i); cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(RLA, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) + ci; cpu.reg.A &= 0xff; cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(RLCA, (cpu, mmu) -> { int ci = b(cpu.reg.A & 0x80); int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) + ci; cpu.reg.A &= 0xff; cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(RRA, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = (cpu.reg.A >> 1) + ci; cpu.reg.A &= 0xff; cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(RRCA, (cpu, mmu) -> { int ci = b(cpu.reg.A & 1, 0x80); int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = (cpu.reg.A >> 1) + ci; cpu.reg.A &= 0xff; cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        op(RLr_b, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.B & 0x80, 0x10); cpu.reg.B = (cpu.reg.B << 1) + ci; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_c, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.C & 0x80, 0x10); cpu.reg.C = (cpu.reg.C << 1) + ci; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_d, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.D & 0x80, 0x10); cpu.reg.D = (cpu.reg.D << 1) + ci; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_e, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.E & 0x80, 0x10); cpu.reg.E = (cpu.reg.E << 1) + ci; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_h, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.H & 0x80, 0x10); cpu.reg.H = (cpu.reg.H << 1) + ci; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_l, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.L & 0x80, 0x10); cpu.reg.L = (cpu.reg.L << 1) + ci; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLr_a, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10); int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) + ci; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLHL, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); int ci = b(cpu.reg.F & 0x10); int co = b(i & 0x80, 0x10); i = (i << 1) + ci; i &= 0xff; fz(cpu, i); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(RLCr_b, (cpu, mmu) -> { int ci = b(cpu.reg.B & 0x80); int co = b(cpu.reg.B & 0x80, 0x10); cpu.reg.B = (cpu.reg.B << 1) + ci; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_c, (cpu, mmu) -> { int ci = b(cpu.reg.C & 0x80); int co = b(cpu.reg.C & 0x80, 0x10); cpu.reg.C = (cpu.reg.C << 1) + ci; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_d, (cpu, mmu) -> { int ci = b(cpu.reg.D & 0x80); int co = b(cpu.reg.D & 0x80, 0x10); cpu.reg.D = (cpu.reg.D << 1) + ci; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_e, (cpu, mmu) -> { int ci = b(cpu.reg.E & 0x80); int co = b(cpu.reg.E & 0x80, 0x10); cpu.reg.E = (cpu.reg.E << 1) + ci; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_h, (cpu, mmu) -> { int ci = b(cpu.reg.H & 0x80); int co = b(cpu.reg.H & 0x80, 0x10); cpu.reg.H = (cpu.reg.H << 1) + ci; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_l, (cpu, mmu) -> { int ci = b(cpu.reg.L & 0x80); int co = b(cpu.reg.L & 0x80, 0x10); cpu.reg.L = (cpu.reg.L << 1) + ci; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCr_a, (cpu, mmu) -> { int ci = b(cpu.reg.A & 0x80); int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) + ci; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RLCHL, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); int ci = b(i & 0x80); int co = b(i & 0x80, 0x10); i = (i << 1) + ci; i &= 0xff; fz(cpu, i); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(RRr_b, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.B & 1, 0x10); cpu.reg.B = (cpu.reg.B >> 1) + ci; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_c, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.C & 1, 0x10); cpu.reg.C = (cpu.reg.C >> 1) + ci; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_d, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.D & 1, 0x10); cpu.reg.D = (cpu.reg.D >> 1) + ci; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_e, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.E & 1, 0x10); cpu.reg.E = (cpu.reg.E >> 1) + ci; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_h, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.H & 1, 0x10); cpu.reg.H = (cpu.reg.H >> 1) + ci; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_l, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.L & 1, 0x10); cpu.reg.L = (cpu.reg.L >> 1) + ci; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRr_a, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = (cpu.reg.A >> 1) + ci; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRHL, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); int ci = b(cpu.reg.F & 0x10, 0x80); int co = b(i & 1, 0x10); i = (i >> 1) + ci; i &= 0xff; fz(cpu, i); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(RRCr_b, (cpu, mmu) -> { int ci = b(cpu.reg.B & 1, 0x80); int co = b(cpu.reg.B & 1, 0x10); cpu.reg.B = (cpu.reg.B >> 1) + ci; cpu.reg.B &= 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_c, (cpu, mmu) -> { int ci = b(cpu.reg.C & 1, 0x80); int co = b(cpu.reg.C & 1, 0x10); cpu.reg.C = (cpu.reg.C >> 1) + ci; cpu.reg.C &= 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_d, (cpu, mmu) -> { int ci = b(cpu.reg.D & 1, 0x80); int co = b(cpu.reg.D & 1, 0x10); cpu.reg.D = (cpu.reg.D >> 1) + ci; cpu.reg.D &= 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_e, (cpu, mmu) -> { int ci = b(cpu.reg.E & 1, 0x80); int co = b(cpu.reg.E & 1, 0x10); cpu.reg.E = (cpu.reg.E >> 1) + ci; cpu.reg.E &= 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_h, (cpu, mmu) -> { int ci = b(cpu.reg.H & 1, 0x80); int co = b(cpu.reg.H & 1, 0x10); cpu.reg.H = (cpu.reg.H >> 1) + ci; cpu.reg.H &= 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_l, (cpu, mmu) -> { int ci = b(cpu.reg.L & 1, 0x80); int co = b(cpu.reg.L & 1, 0x10); cpu.reg.L = (cpu.reg.L >> 1) + ci; cpu.reg.L &= 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCr_a, (cpu, mmu) -> { int ci = b(cpu.reg.A & 1, 0x80); int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = (cpu.reg.A >> 1) + ci; cpu.reg.A &= 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(RRCHL, (cpu, mmu) -> { int i = mmu.rb((cpu.reg.H << 8) + cpu.reg.L); int ci = b(i & 1, 0x80); int co = b(i & 1, 0x10); i = (i >> 1) + ci; i &= 0xff; fz(cpu, i); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 4; cpu.reg.T = 16; });
        
        op(SLAr_b, (cpu, mmu) -> { int co = b(cpu.reg.B & 0x80, 0x10); cpu.reg.B = (cpu.reg.B << 1) & 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_c, (cpu, mmu) -> { int co = b(cpu.reg.C & 0x80, 0x10); cpu.reg.C = (cpu.reg.C << 1) & 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_b, (cpu, mmu) -> { int co = b(cpu.reg.D & 0x80, 0x10); cpu.reg.D = (cpu.reg.D << 1) & 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_e, (cpu, mmu) -> { int co = b(cpu.reg.E & 0x80, 0x10); cpu.reg.E = (cpu.reg.E << 1) & 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_h, (cpu, mmu) -> { int co = b(cpu.reg.H & 0x80, 0x10); cpu.reg.H = (cpu.reg.H << 1) & 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_l, (cpu, mmu) -> { int co = b(cpu.reg.L & 0x80, 0x10); cpu.reg.L = (cpu.reg.L << 1) & 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SLAr_a, (cpu, mmu) -> { int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) & 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        //op(SLLr_b, (cpu, mmu) -> { int co = b(cpu.reg.B & 0x80, 0x10); cpu.reg.B = (cpu.reg.B << 1) & 0xff + 1; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_c, (cpu, mmu) -> { int co = b(cpu.reg.C & 0x80, 0x10); cpu.reg.C = (cpu.reg.C << 1) & 0xff + 1; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_b, (cpu, mmu) -> { int co = b(cpu.reg.D & 0x80, 0x10); cpu.reg.D = (cpu.reg.D << 1) & 0xff + 1; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_e, (cpu, mmu) -> { int co = b(cpu.reg.E & 0x80, 0x10); cpu.reg.E = (cpu.reg.E << 1) & 0xff + 1; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_h, (cpu, mmu) -> { int co = b(cpu.reg.H & 0x80, 0x10); cpu.reg.H = (cpu.reg.H << 1) & 0xff + 1; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_l, (cpu, mmu) -> { int co = b(cpu.reg.L & 0x80, 0x10); cpu.reg.L = (cpu.reg.L << 1) & 0xff + 1; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        //op(SLLr_a, (cpu, mmu) -> { int co = b(cpu.reg.A & 0x80, 0x10); cpu.reg.A = (cpu.reg.A << 1) & 0xff + 1; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(SRAr_b, (cpu, mmu) -> { int ci = cpu.reg.B & 0x80; int co = b(cpu.reg.B & 1, 0x10); cpu.reg.B = ((cpu.reg.B >> 1) + ci) & 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_c, (cpu, mmu) -> { int ci = cpu.reg.C & 0x80; int co = b(cpu.reg.C & 1, 0x10); cpu.reg.C = ((cpu.reg.C >> 1) + ci) & 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_b, (cpu, mmu) -> { int ci = cpu.reg.D & 0x80; int co = b(cpu.reg.D & 1, 0x10); cpu.reg.D = ((cpu.reg.D >> 1) + ci) & 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_e, (cpu, mmu) -> { int ci = cpu.reg.E & 0x80; int co = b(cpu.reg.E & 1, 0x10); cpu.reg.E = ((cpu.reg.E >> 1) + ci) & 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_h, (cpu, mmu) -> { int ci = cpu.reg.H & 0x80; int co = b(cpu.reg.H & 1, 0x10); cpu.reg.H = ((cpu.reg.H >> 1) + ci) & 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_l, (cpu, mmu) -> { int ci = cpu.reg.L & 0x80; int co = b(cpu.reg.L & 1, 0x10); cpu.reg.L = ((cpu.reg.L >> 1) + ci) & 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRAr_a, (cpu, mmu) -> { int ci = cpu.reg.A & 0x80; int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = ((cpu.reg.A >> 1) + ci) & 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(SRLr_b, (cpu, mmu) -> { int co = b(cpu.reg.B & 1, 0x10); cpu.reg.B = (cpu.reg.B >> 1) & 0xff; fz(cpu, cpu.reg.B); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_c, (cpu, mmu) -> { int co = b(cpu.reg.C & 1, 0x10); cpu.reg.C = (cpu.reg.C >> 1) & 0xff; fz(cpu, cpu.reg.C); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_b, (cpu, mmu) -> { int co = b(cpu.reg.D & 1, 0x10); cpu.reg.D = (cpu.reg.D >> 1) & 0xff; fz(cpu, cpu.reg.D); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_e, (cpu, mmu) -> { int co = b(cpu.reg.E & 1, 0x10); cpu.reg.E = (cpu.reg.E >> 1) & 0xff; fz(cpu, cpu.reg.E); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_h, (cpu, mmu) -> { int co = b(cpu.reg.H & 1, 0x10); cpu.reg.H = (cpu.reg.H >> 1) & 0xff; fz(cpu, cpu.reg.H); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_l, (cpu, mmu) -> { int co = b(cpu.reg.L & 1, 0x10); cpu.reg.L = (cpu.reg.L >> 1) & 0xff; fz(cpu, cpu.reg.L); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        op(SRLr_a, (cpu, mmu) -> { int co = b(cpu.reg.A & 1, 0x10); cpu.reg.A = (cpu.reg.A >> 1) & 0xff; fz(cpu, cpu.reg.A); cpu.reg.F = (cpu.reg.F & 0xEF) + co; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(CPL, (cpu, mmu) -> { cpu.reg.A = (~cpu.reg.A) & 0xff; fzt(cpu, cpu.reg.A); cpu.reg.M = 1; cpu.reg.T = 4; });
        //op(NEG, (cpu, mmu) -> { cpu.reg.A = 0 - cpu.reg.A; fzt(cpu, cpu.reg.A); if(cpu.reg.A < 0) cpu.reg.F |= 0x10; cpu.reg.A &= 0xff; cpu.reg.M = 2; cpu.reg.T = 8; });
        
        op(CCF, (cpu, mmu) -> { int ci = b(cpu.reg.F & 0x10, 0, 0x10); cpu.reg.F = (cpu.reg.F & 0xEF) + ci; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(SCF, (cpu, mmu) -> { cpu.reg.F |= 0x10; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        /* Stack */
        op(PUSHBC, (cpu, mmu) -> { cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.B); cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.C); cpu.reg.M = 3; cpu.reg.T = 12; });
        op(PUSHDE, (cpu, mmu) -> { cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.D); cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.E); cpu.reg.M = 3; cpu.reg.T = 12; });
        op(PUSHHL, (cpu, mmu) -> { cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.H); cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.L); cpu.reg.M = 3; cpu.reg.T = 12; });
        op(PUSHAF, (cpu, mmu) -> { cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.A); cpu.reg.SP--; mmu.wb(cpu.reg.SP, cpu.reg.F); cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(POPBC, (cpu, mmu) -> { cpu.reg.C = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.B = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(POPDE, (cpu, mmu) -> { cpu.reg.E = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.D = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(POPHL, (cpu, mmu) -> { cpu.reg.L = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.H = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(POPAF, (cpu, mmu) -> { cpu.reg.F = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.A = mmu.rb(cpu.reg.SP); cpu.reg.SP++; cpu.reg.M = 3; cpu.reg.T = 12; });
        
        /* Jump */
        op(JPnn, (cpu, mmu) -> { cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M = 3; cpu.reg.T = 12; });
        op(JPHL, (cpu, mmu) -> { cpu.reg.PC = (cpu.reg.H << 8) + cpu.reg.L; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(JPNZnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x80) == 0x00) { cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M++; cpu.reg.T += 4; } else { cpu.reg.PC += 2; } });
        op(JPZnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x80) == 0x80) { cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M++; cpu.reg.T += 4; } else { cpu.reg.PC += 2; } });
        op(JPNCnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x10) == 0x00) { cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M++; cpu.reg.T += 4; } else { cpu.reg.PC += 2; } });
        op(JPCnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x10) == 0x10) { cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M++; cpu.reg.T += 4; } else { cpu.reg.PC += 2; } });
        
        op(JRn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; });
        op(JRNZn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; if((cpu.reg.F & 0x80) == 0x00) { cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; } });
        op(JRZn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; if((cpu.reg.F & 0x80) == 0x80) { cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; } });
        op(JRNCn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; if((cpu.reg.F & 0x10) == 0x00) { cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; } });
        op(JRCn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8; if((cpu.reg.F & 0x10) == 0x10) { cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; } });
        
        op(DJNZn, (cpu, mmu) -> { int i = mmu.rb(cpu.reg.PC); if(i > 127) i = -((~i + 1) & 0xff); cpu.reg.PC++; cpu.reg.M = 2; cpu.reg.T = 8;  cpu.reg.B--; if(cpu.reg.B != 0) { cpu.reg.PC += i; cpu.reg.M++; cpu.reg.T += 4; } });
        
        op(CALLnn, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC + 2); cpu.reg.PC = mmu.rw(cpu.reg.PC); cpu.reg.M = 5; cpu.reg.T = 20; });
        op(CALLNZnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x80) == 0x00) { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC + 2); cpu.reg.PC = mmu.rw(cpu.reg.PC);  cpu.reg.M += 2; cpu.reg.T += 8; } else { cpu.reg.PC += 2; } });
        op(CALLZnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x80) == 0x80) { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC + 2); cpu.reg.PC = mmu.rw(cpu.reg.PC);  cpu.reg.M += 2; cpu.reg.T += 8; } else { cpu.reg.PC += 2; } });
        op(CALLNCnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x10) == 0x00) { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC + 2); cpu.reg.PC = mmu.rw(cpu.reg.PC);  cpu.reg.M += 2; cpu.reg.T += 8; } else { cpu.reg.PC += 2; } });
        op(CALLCnn, (cpu, mmu) -> { cpu.reg.M = 3; cpu.reg.T = 12; if((cpu.reg.F & 0x10) == 0x10) { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC + 2); cpu.reg.PC = mmu.rw(cpu.reg.PC);  cpu.reg.M += 2; cpu.reg.T += 8; } else { cpu.reg.PC += 2; } });
        
        op(RET, (cpu, mmu) -> { cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RETI, (cpu, mmu) -> { cpu.reg.IME = true; cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RETNZ, (cpu, mmu) -> { cpu.reg.M = 1; cpu.reg.T = 4; if((cpu.reg.F & 0x80) == 0x00) { cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M += 2; cpu.reg.T += 4; } });
        op(RETZ, (cpu, mmu) -> { cpu.reg.M = 1; cpu.reg.T = 4; if((cpu.reg.F & 0x80) == 0x80) { cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M += 2; cpu.reg.T += 4; } });
        op(RETNC, (cpu, mmu) -> { cpu.reg.M = 1; cpu.reg.T = 4; if((cpu.reg.F & 0x10) == 0x00) { cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M += 2; cpu.reg.T += 4; } });
        op(RETC, (cpu, mmu) -> { cpu.reg.M = 1; cpu.reg.T = 4; if((cpu.reg.F & 0x10) == 0x10) { cpu.reg.PC = mmu.rw(cpu.reg.SP); cpu.reg.SP += 2; cpu.reg.M += 2; cpu.reg.T += 4; } });
        
        op(RST00, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x00; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST08, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x08; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST10, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x10; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST18, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x18; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST20, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x20; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST28, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x28; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST30, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x30; cpu.reg.M = 3; cpu.reg.T = 12; });
        op(RST38, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x38; cpu.reg.M = 3; cpu.reg.T = 12; });
        //op(RST40, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x40; cpu.reg.M = 3; cpu.reg.T = 12; });
        //op(RST48, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x48; cpu.reg.M = 3; cpu.reg.T = 12; });
        //op(RST50, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x50; cpu.reg.M = 3; cpu.reg.T = 12; });
        //op(RST58, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x58; cpu.reg.M = 3; cpu.reg.T = 12; });
        //op(RST60, (cpu, mmu) -> { cpu.reg.SP -= 2; mmu.ww(cpu.reg.SP, cpu.reg.PC); cpu.reg.PC = 0x60; cpu.reg.M = 3; cpu.reg.T = 12; });
        
        op(NOP, (cpu, mmu) -> { cpu.reg.M = 1; cpu.reg.T = 4; });
        op(HALT, (cpu, mmu) -> { cpu.halt = true; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        op(DI, (cpu, mmu) -> { cpu.reg.IME = false; cpu.reg.M = 1; cpu.reg.T = 4; });
        op(EI, (cpu, mmu) -> { cpu.reg.IME = true; cpu.reg.M = 1; cpu.reg.T = 4; });
        
        op(MAPcb, (cpu, mmu) -> {
            int i = mmu.rb(cpu.reg.PC);
            cpu.reg.PC++;
            if(i >= 0 && i < 0x100)
                CBOPS[i].execute(cpu, mmu);
            throw new RuntimeException("Unimplemented extended (CBOP) instruction at $" + (Integer.toHexString(cpu.reg.PC - 1)));
        });
        
        fillUnimplementedOps();
    }
    
    @FunctionalInterface
    public interface OpcodeAction
    {
        void execute(Z80 cpu, MMU mmu);
    }
}
