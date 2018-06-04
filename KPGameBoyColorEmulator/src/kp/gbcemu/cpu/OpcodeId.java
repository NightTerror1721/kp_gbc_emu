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
public final class OpcodeId
{
    private OpcodeId() {}
    
    // 00
    public static final int NOP = 0x0000;
    public static final int LDBCnn = 0x0001;
    public static final int LDBCmA = 0x0002;
    public static final int INCBC = 0x0003;
    public static final int INCr_b = 0x0004;
    public static final int DECr_b = 0x0005;
    public static final int LDrn_b = 0x0006;
    public static final int RLCA = 0x0007;
    public static final int LDmmSP = 0x0008;
    public static final int ADDHLBC = 0x0009;
    public static final int LDABCm = 0x000A;
    public static final int DECBC = 0x000B;
    public static final int INCr_c = 0x000C;
    public static final int DECr_c = 0x000D;
    public static final int LDrn_c = 0x000E;
    public static final int RRCA = 0x000F;

    // 10
    public static final int DJNZn = 0x0010;
    public static final int LDDEnn = 0x0011;
    public static final int LDDEmA = 0x0012;
    public static final int INCDE = 0x0013;
    public static final int INCr_d = 0x0014;
    public static final int DECr_d = 0x0015;
    public static final int LDrn_d = 0x0016;
    public static final int RLA = 0x0017;
    public static final int JRn = 0x0018;
    public static final int ADDHLDE = 0x0019;
    public static final int LDADEm = 0x001A;
    public static final int DECDE = 0x001B;
    public static final int INCr_e = 0x001C;
    public static final int DECr_e = 0x001D;
    public static final int LDrn_e = 0x001E;
    public static final int RRA = 0x001F;

    // 20
    public static final int JRNZn = 0x0020;
    public static final int LDHLnn = 0x0021;
    public static final int LDHLIA = 0x0022;
    public static final int INCHL = 0x0023;
    public static final int INCr_h = 0x0024;
    public static final int DECr_h = 0x0025;
    public static final int LDrn_h = 0x0026;
    //public static final int XX = 0x0027;
    public static final int JRZn = 0x0028;
    public static final int ADDHLHL = 0x0029;
    public static final int LDAHLI = 0x002A;
    public static final int DECHL = 0x002B;
    public static final int INCr_l = 0x002C;
    public static final int DECr_l = 0x002D;
    public static final int LDrn_l = 0x002E;
    public static final int CPL = 0x002F;

    // 30
    public static final int JRNCn = 0x0030;
    public static final int LDSPnn = 0x0031;
    public static final int LDHLDA = 0x0032;
    public static final int INCSP = 0x0033;
    public static final int INCHLm = 0x0034;
    public static final int DECHLm = 0x0035;
    public static final int LDHLmn = 0x0036;
    public static final int SCF = 0x0037;
    public static final int JRCn = 0x0038;
    public static final int ADDHLSP = 0x0039;
    public static final int LDAHLD = 0x003A;
    public static final int DECSP = 0x003B;
    public static final int INCr_a = 0x003C;
    public static final int DECr_a = 0x003D;
    public static final int LDrn_a = 0x003E;
    public static final int CCF = 0x003F;

    // 40
    public static final int LDrr_bb = 0x0040;
    public static final int LDrr_bc = 0x0041;
    public static final int LDrr_bd = 0x0042;
    public static final int LDrr_be = 0x0043;
    public static final int LDrr_bh = 0x0044;
    public static final int LDrr_bl = 0x0045;
    public static final int LDrHLm_b = 0x0046;
    public static final int LDrr_ba = 0x0047;
    public static final int LDrr_cb = 0x0048;
    public static final int LDrr_cc = 0x0049;
    public static final int LDrr_cd = 0x004A;
    public static final int LDrr_ce = 0x004B;
    public static final int LDrr_ch = 0x004C;
    public static final int LDrr_cl = 0x004D;
    public static final int LDrHLm_c = 0x004E;
    public static final int LDrr_ca = 0x004F;

    // 50
    public static final int LDrr_db = 0x0050;
    public static final int LDrr_dc = 0x0051;
    public static final int LDrr_dd = 0x0052;
    public static final int LDrr_de = 0x0053;
    public static final int LDrr_dh = 0x0054;
    public static final int LDrr_dl = 0x0055;
    public static final int LDrHLm_d = 0x0056;
    public static final int LDrr_da = 0x0057;
    public static final int LDrr_eb = 0x0058;
    public static final int LDrr_ec = 0x0059;
    public static final int LDrr_ed = 0x005A;
    public static final int LDrr_ee = 0x005B;
    public static final int LDrr_eh = 0x005C;
    public static final int LDrr_el = 0x005D;
    public static final int LDrHLm_e = 0x005E;
    public static final int LDrr_ea = 0x005F;

    // 60
    public static final int LDrr_hb = 0x0060;
    public static final int LDrr_hc = 0x0061;
    public static final int LDrr_hd = 0x0062;
    public static final int LDrr_he = 0x0063;
    public static final int LDrr_hh = 0x0064;
    public static final int LDrr_hl = 0x0065;
    public static final int LDrHLm_h = 0x0066;
    public static final int LDrr_ha = 0x0067;
    public static final int LDrr_lb = 0x0068;
    public static final int LDrr_lc = 0x0069;
    public static final int LDrr_ld = 0x006A;
    public static final int LDrr_le = 0x006B;
    public static final int LDrr_lh = 0x006C;
    public static final int LDrr_ll = 0x006D;
    public static final int LDrHLm_l = 0x006E;
    public static final int LDrr_la = 0x006F;

    // 70
    public static final int LDHLmr_b = 0x0070;
    public static final int LDHLmr_c = 0x0071;
    public static final int LDHLmr_d = 0x0072;
    public static final int LDHLmr_e = 0x0073;
    public static final int LDHLmr_h = 0x0074;
    public static final int LDHLmr_l = 0x0075;
    public static final int HALT = 0x0076;
    public static final int LDHLmr_a = 0x0077;
    public static final int LDrr_ab = 0x0078;
    public static final int LDrr_ac = 0x0079;
    public static final int LDrr_ad = 0x007A;
    public static final int LDrr_ae = 0x007B;
    public static final int LDrr_ah = 0x007C;
    public static final int LDrr_al = 0x007D;
    public static final int LDrHLm_a = 0x007E;
    public static final int LDrr_aa = 0x007F;

    // 80
    public static final int ADDr_b = 0x0080;
    public static final int ADDr_c = 0x0081;
    public static final int ADDr_d = 0x0082;
    public static final int ADDr_e = 0x0083;
    public static final int ADDr_h = 0x0084;
    public static final int ADDr_l = 0x0085;
    public static final int ADDHL = 0x0086;
    public static final int ADDr_a = 0x0087;
    public static final int ADCr_b = 0x0088;
    public static final int ADCr_c = 0x0089;
    public static final int ADCr_d = 0x008A;
    public static final int ADCr_e = 0x008B;
    public static final int ADCr_h = 0x008C;
    public static final int ADCr_l = 0x008D;
    public static final int ADCHL = 0x008E;
    public static final int ADCr_a = 0x008F;

    // 90
    public static final int SUBr_b = 0x0090;
    public static final int SUBr_c = 0x0091;
    public static final int SUBr_d = 0x0092;
    public static final int SUBr_e = 0x0093;
    public static final int SUBr_h = 0x0094;
    public static final int SUBr_l = 0x0095;
    public static final int SUBHL = 0x0096;
    public static final int SUBr_a = 0x0097;
    public static final int SBCr_b = 0x0098;
    public static final int SBCr_c = 0x0099;
    public static final int SBCr_d = 0x009A;
    public static final int SBCr_e = 0x009B;
    public static final int SBCr_h = 0x009C;
    public static final int SBCr_l = 0x009D;
    public static final int SBCHL = 0x009E;
    public static final int SBCr_a = 0x009F;

    // A0
    public static final int ANDr_b = 0x00A0;
    public static final int ANDr_c = 0x00A1;
    public static final int ANDr_d = 0x00A2;
    public static final int ANDr_e = 0x00A3;
    public static final int ANDr_h = 0x00A4;
    public static final int ANDr_l = 0x00A5;
    public static final int ANDHL = 0x00A6;
    public static final int ANDr_a = 0x00A7;
    public static final int XORr_b = 0x00A8;
    public static final int XORr_c = 0x00A9;
    public static final int XORr_d = 0x00AA;
    public static final int XORr_e = 0x00AB;
    public static final int XORr_h = 0x00AC;
    public static final int XORr_l = 0x00AD;
    public static final int XORHL = 0x00AE;
    public static final int XORr_a = 0x00AF;

    // B0
    public static final int ORr_b = 0x00B0;
    public static final int ORr_c = 0x00B1;
    public static final int ORr_d = 0x00B2;
    public static final int ORr_e = 0x00B3;
    public static final int ORr_h = 0x00B4;
    public static final int ORr_l = 0x00B5;
    public static final int ORHL = 0x00B6;
    public static final int ORr_a = 0x00B7;
    public static final int CPr_b = 0x00B8;
    public static final int CPr_c = 0x00B9;
    public static final int CPr_d = 0x00BA;
    public static final int CPr_e = 0x00BB;
    public static final int CPr_h = 0x00BC;
    public static final int CPr_l = 0x00BD;
    public static final int CPHL = 0x00BE;
    public static final int CPr_a = 0x00BF;

    // C0
    public static final int RETNZ = 0x00C0;
    public static final int POPBC = 0x00C1;
    public static final int JPNZnn = 0x00C2;
    public static final int JPnn = 0x00C3;
    public static final int CALLNZnn = 0x00C4;
    public static final int PUSHBC = 0x00C5;
    public static final int ADDn = 0x00C6;
    public static final int RST00 = 0x00C7;
    public static final int RETZ = 0x00C8;
    public static final int RET = 0x00C9;
    public static final int JPZnn = 0x00CA;
    public static final int MAPcb = 0x00CB;
    public static final int CALLZnn = 0x00CC;
    public static final int CALLnn = 0x00CD;
    public static final int ADCn = 0x00CE;
    public static final int RST08 = 0x00CF;

    // D0
    public static final int RETNC = 0x00D0;
    public static final int POPDE = 0x00D1;
    public static final int JPNCnn = 0x00D2;
    //public static final int XX = 0x00D3;
    public static final int CALLNCnn = 0x00D4;
    public static final int PUSHDE = 0x00D5;
    public static final int SUBn = 0x00D6;
    public static final int RST10 = 0x00D7;
    public static final int RETC = 0x00D8;
    public static final int RETI = 0x00D9;
    public static final int JPCnn = 0x00DA;
    //public static final int XX = 0x00DB;
    public static final int CALLCnn = 0x00DC;
    //public static final int XX = 0x00DD;
    public static final int SBCn = 0x00DE;
    public static final int RST18 = 0x00DF;

    // E0
    public static final int LDIOnA = 0x00E0;
    public static final int POPHL = 0x00E1;
    public static final int LDIOCA = 0x00E2;
    //public static final int XX = 0x00E3;
    //public static final int XX = 0x00E4;
    public static final int PUSHHL = 0x00E5;
    public static final int ANDn = 0x00E6;
    public static final int RST20 = 0x00E7;
    public static final int ADDSPn = 0x00E8;
    public static final int JPHL = 0x00E9;
    public static final int LDmmA = 0x00EA;
    //public static final int XX = 0x00EB;
    //public static final int XX = 0x00EC;
    //public static final int XX = 0x00ED;
    public static final int ORn = 0x00EE;
    public static final int RST28 = 0x00EF;

    // F0
    public static final int LDAIOn = 0x00F0;
    public static final int POPAF = 0x00F1;
    public static final int LDAIOC = 0x00F2;
    public static final int DI = 0x00F3;
    //public static final int XX = 0x00F4;
    public static final int PUSHAF = 0x00F5;
    public static final int XORn = 0x00F6;
    public static final int RST30 = 0x00F7;
    public static final int LDHLSPn = 0x00F8;
    //public static final int XX = 0x00F9;
    public static final int LDAmm = 0x00FA;
    public static final int EI = 0x00FB;
    //public static final int XX = 0x00FC;
    //public static final int XX = 0x00FD;
    public static final int CPn = 0x00FE;
    public static final int RST38 = 0x00FF;
    
    
    
    public static final int RLCr_b = 0xCB00;
    public static final int RLCr_c = 0xCB01;
    public static final int RLCr_d = 0xCB02;
    public static final int RLCr_e = 0xCB03;
    public static final int RLCr_h = 0xCB04;
    public static final int RLCr_l = 0xCB05;
    public static final int RLCHL = 0xCB06;
    public static final int RLCr_a = 0xCB07;
    public static final int RRCr_b = 0xCB08;
    public static final int RRCr_c = 0xCB09;
    public static final int RRCr_d = 0xCB0A;
    public static final int RRCr_e = 0xCB0B;
    public static final int RRCr_h = 0xCB0C;
    public static final int RRCr_l = 0xCB0D;
    public static final int RRCHL = 0xCB0E;
    public static final int RRCr_a = 0xCB0F;

    // CB10
    public static final int RLr_b = 0xCB10;
    public static final int RLr_c = 0xCB11;
    public static final int RLr_d = 0xCB12;
    public static final int RLr_e = 0xCB13;
    public static final int RLr_h = 0xCB14;
    public static final int RLr_l = 0xCB15;
    public static final int RLHL = 0xCB16;
    public static final int RLr_a = 0xCB17;
    public static final int RRr_b = 0xCB18;
    public static final int RRr_c = 0xCB19;
    public static final int RRr_d = 0xCB1A;
    public static final int RRr_e = 0xCB1B;
    public static final int RRr_h = 0xCB1C;
    public static final int RRr_l = 0xCB1D;
    public static final int RRHL = 0xCB1E;
    public static final int RRr_a = 0xCB1F;

    // CB20
    public static final int SLAr_b = 0xCB20;
    public static final int SLAr_c = 0xCB21;
    public static final int SLAr_d = 0xCB22;
    public static final int SLAr_e = 0xCB23;
    public static final int SLAr_h = 0xCB24;
    public static final int SLAr_l = 0xCB25;
    //public static final int XX = 0xCB26;
    public static final int SLAr_a = 0xCB27;
    public static final int SRAr_b = 0xCB28;
    public static final int SRAr_c = 0xCB29;
    public static final int SRAr_d = 0xCB2A;
    public static final int SRAr_e = 0xCB2B;
    public static final int SRAr_h = 0xCB2C;
    public static final int SRAr_l = 0xCB2D;
    //public static final int XX = 0xCB2E;
    public static final int SRAr_a = 0xCB2F;

    // CB30
    public static final int SWAPr_b = 0xCB30;
    public static final int SWAPr_c = 0xCB31;
    public static final int SWAPr_d = 0xCB32;
    public static final int SWAPr_e = 0xCB33;
    public static final int SWAPr_h = 0xCB34;
    public static final int SWAPr_l = 0xCB35;
    //public static final int XX = 0xCB36;
    public static final int SWAPr_a = 0xCB37;
    public static final int SRLr_b = 0xCB38;
    public static final int SRLr_c = 0xCB39;
    public static final int SRLr_d = 0xCB3A;
    public static final int SRLr_e = 0xCB3B;
    public static final int SRLr_h = 0xCB3C;
    public static final int SRLr_l = 0xCB3D;
    //public static final int XX = 0xCB3E;
    public static final int SRLr_a = 0xCB3F;

    // CB40
    public static final int BIT0b = 0xCB40;
    public static final int BIT0c = 0xCB41;
    public static final int BIT0d = 0xCB42;
    public static final int BIT0e = 0xCB43;
    public static final int BIT0h = 0xCB44;
    public static final int BIT0l = 0xCB45;
    public static final int BIT0m = 0xCB46;
    public static final int BIT0a = 0xCB47;
    public static final int BIT1b = 0xCB48;
    public static final int BIT1c = 0xCB49;
    public static final int BIT1d = 0xCB4A;
    public static final int BIT1e = 0xCB4B;
    public static final int BIT1h = 0xCB4C;
    public static final int BIT1l = 0xCB4D;
    public static final int BIT1m = 0xCB4E;
    public static final int BIT1a = 0xCB4F;

    // CB50
    public static final int BIT2b = 0xCB50;
    public static final int BIT2c = 0xCB51;
    public static final int BIT2d = 0xCB52;
    public static final int BIT2e = 0xCB53;
    public static final int BIT2h = 0xCB54;
    public static final int BIT2l = 0xCB55;
    public static final int BIT2m = 0xCB56;
    public static final int BIT2a = 0xCB57;
    public static final int BIT3b = 0xCB58;
    public static final int BIT3c = 0xCB59;
    public static final int BIT3d = 0xCB5A;
    public static final int BIT3e = 0xCB5B;
    public static final int BIT3h = 0xCB5C;
    public static final int BIT3l = 0xCB5D;
    public static final int BIT3m = 0xCB5E;
    public static final int BIT3a = 0xCB5F;

    // CB60
    public static final int BIT4b = 0xCB60;
    public static final int BIT4c = 0xCB61;
    public static final int BIT4d = 0xCB62;
    public static final int BIT4e = 0xCB63;
    public static final int BIT4h = 0xCB64;
    public static final int BIT4l = 0xCB65;
    public static final int BIT4m = 0xCB66;
    public static final int BIT4a = 0xCB67;
    public static final int BIT5b = 0xCB68;
    public static final int BIT5c = 0xCB69;
    public static final int BIT5d = 0xCB6A;
    public static final int BIT5e = 0xCB6B;
    public static final int BIT5h = 0xCB6C;
    public static final int BIT5l = 0xCB6D;
    public static final int BIT5m = 0xCB6E;
    public static final int BIT5a = 0xCB6F;

    // CB70
    public static final int BIT6b = 0xCB70;
    public static final int BIT6c = 0xCB71;
    public static final int BIT6d = 0xCB72;
    public static final int BIT6e = 0xCB73;
    public static final int BIT6h = 0xCB74;
    public static final int BIT6l = 0xCB75;
    public static final int BIT6m = 0xCB76;
    public static final int BIT6a = 0xCB77;
    public static final int BIT7b = 0xCB78;
    public static final int BIT7c = 0xCB79;
    public static final int BIT7d = 0xCB7A;
    public static final int BIT7e = 0xCB7B;
    public static final int BIT7h = 0xCB7C;
    public static final int BIT7l = 0xCB7D;
    public static final int BIT7m = 0xCB7E;
    public static final int BIT7a = 0xCB7F;
}
