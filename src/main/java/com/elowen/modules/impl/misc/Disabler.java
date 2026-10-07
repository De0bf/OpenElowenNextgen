package com.elowen.modules.impl.misc;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.files.FileManager;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.ChatUtils;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.MathUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import java.lang.reflect.Field;
import java.util.Random;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;

@ModuleInfo(R = "Disabler", M = Category.MISC, a = "Disables some checks of the anti cheat.")
public class Disabler extends Module {
   private final BooleanValue x;
   private final BooleanValue t;
   private final BooleanValue p;
   private final BooleanValue Z;
   private final BooleanValue y;
   private final BooleanValue Q;
   private float m;
   private float X;
   private float e;
   private boolean E;
   private float M;
   private float b;
   private final Random o;
   private static final double[] B;
   private static final double V = 1.0E-10;
   private int P;
   private long q;
   private boolean U;
   private ServerboundContainerClosePacket T;
   private long r;
   private long F;
   private static final String[] c = new String[]{"Failed to get xrot field", "ms", "Failed to set xrot field", "Logging", "f_134122_", "Class and fieldNames must not be null or empty", "f_134122_", "Disabling DuplicateRotPlace!", "yRot", "InventoryFrequency: Storing close packet, will send after ", "GrimDuplicateRotPlace", "Sent intermediate slot: ", "ACAPerfectRotation", "yRot", "Failed to set yrot field", "Failed to get yrot field", "xRot", "Inventory opened at: ", "InventoryFrequency: Released stored close packet", "xRot", "ACAFastSwitch", "InventoryFrequency: Allowed close packet after ", "f_134121_", "ACAInventoryFrequency", "Processed slot switch: ", "PerfectRotation: Modified rotation", " -> ", "ACAAimStep", "ms", "f_134121_"};
   public Disabler() {
      String[] var2 = c;
      this.x = com.elowen.values.ValueBuilder.m(this, "Logging").h(false).f$K().f$O();
      this.t = com.elowen.values.ValueBuilder.m(this, "ACAAimStep").h(false).f$K().f$O();
      this.p = com.elowen.values.ValueBuilder.m(this, "ACAPerfectRotation").h(false).f$K().f$O();
      this.Z = com.elowen.values.ValueBuilder.m(this, "GrimDuplicateRotPlace").h(false).f$K().f$O();
      this.y = com.elowen.values.ValueBuilder.m(this, "ACAFastSwitch").h(false).f$K().f$O();
      String[] var10000 = Teams.a$ArrString();
      this.Q = com.elowen.values.ValueBuilder.m(this, "ACAInventoryFrequency").h(false).f$K().f$O();
      String[] var1 = var10000;
      this.E = false;
      this.M = 0.0F;
      this.b = 0.0F;
      this.o = new Random();
      this.P = -1;
      this.q = 0L;
      this.U = false;
      this.T = null;
      this.r = 0L;
      this.F = 0L;
      if (var1 == null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.x());
      }
   }

   private void n(String var1) {
      if (this.x.w()) {
         com.elowen.utils.ChatUtils.b(var1);
      }
   }

   @Override
   public void h$V() {
      this.J$V();
   }

   @Override
   public void q$V() {
      this.J$V();
   }

   private void J$V() {
      this.P = -1;
      this.q = 0L;
      this.U = false;
      this.T = null;
      this.r = 0L;
      this.F = 0L;
      this.m = 0.0F;
      this.X = 0.0F;
      this.e = 0.0F;
      this.E = false;
      this.M = 0.0F;
      this.b = 0.0F;
   }

   private void d(int var1, int var2) {
      String[] var10000 = Teams.a$ArrString();
      int var4 = Math.abs(var1 - var2);
      String[] var3 = var10000;
      int var7 = var4;
      if (var3 != null) {
         if (var4 <= 1) {
            return;
         }

         var7 = ((this.n(var1, var2)) ? 1 : 0);
      }

      if (var3 != null) {
         if (var7 != 0) {
            return;
         }

         var7 = var1;
      }

      if (var3 != null) {
         var7 = var7 > var2 ? -1 : 1;
      }

      int var5 = var7;
      int var6 = var1 + var5;

      while (var6 != var2) {
         int var8 = var6;
         if (var3 != null) {
            if (var6 >= 0 && var6 <= 8) {
               com.elowen.utils.PacketUtils.c(new ServerboundSetCarriedItemPacket(var6));
               this.n("Sent intermediate slot: " + var6);
            }

            var8 = var6 + var5;
         }

         var6 = var8;
         if (var3 == null) {
            break;
         }
      }
   }

   private boolean n(int var1, int var2) {
      String[] var3 = Teams.a$ArrString();
      return var1 == 0 && var2 == 8 || var1 == 8 && var2 == 0;
   }

   private void f$V() {
      if (this.T != null && System.currentTimeMillis() - this.F >= this.r) {
         com.elowen.utils.PacketUtils.c(this.T);
         this.n("InventoryFrequency: Released stored close packet");
         this.T = null;
      }
   }

   private float w(float var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1 > 180.0F) {
         var1 -= 360.0F;
      }

      if (var1 < -180.0F) {
         var1 += 360.0F;
      }

      return var1;
   }

   private boolean U(float var1, float var2) {
      String[] var3 = Teams.a$ArrString();
      if (this.M == 0.0F && this.b == 0.0F) {
         return false;
      }

      double var4 = Math.abs(this.w(var1 - this.M));
      double var6 = Math.abs(var2 - this.b);
      boolean var8 = var4 < 1.0E-5 && var6 > 1.0;
      boolean var9 = var6 < 1.0E-5 && var4 > 1.0;
      return var8 || var9;
   }

   private float[] Q(float var1, float var2) {
      String[] var3;
      double var4;
      float var8;
      float var9;
      int var10;
      var4 = Math.abs(this.w(var1 - this.M));
      String[] var10000 = Teams.a$ArrString();
      double var6 = Math.abs(var2 - this.b);
      var8 = var1;
      var9 = var2;
      var3 = var10000;
      double var12;
      var10 = (var12 = var4 - 1.0E-5) == 0.0 ? 0 : (var12 < 0.0 ? -1 : 1);
      label43:
      if (var3 != null) {
         if (var10 < 0) {
            double var13;
            var10 = (var13 = var6 - 1.0) == 0.0 ? 0 : (var13 < 0.0 ? -1 : 1);
            if (var3 == null) {
               break label43;
            }

            if (var10 > 0) {
               var8 = this.M + (float)(this.o.nextGaussian() * 0.001);
            }
         }

         double var14;
         var10 = (var14 = var6 - 1.0E-5) == 0.0 ? 0 : (var14 < 0.0 ? -1 : 1);
      }

      label36:
      if (var3 != null) {
         if (var10 < 0) {
            double var15;
            var10 = (var15 = var4 - 1.0) == 0.0 ? 0 : (var15 < 0.0 ? -1 : 1);
            if (var3 == null) {
               break label36;
            }

            if (var10 > 0) {
               var9 = this.b + (float)(this.o.nextGaussian() * 0.001);
            }
         }

         var10 = 2;
      }

      float[] var11 = new float[var10];
      var11[0] = var8;
      var11[1] = var9;
      return var11;
   }

   private float[] V(float var1, float var2) {
      String[] var3 = Teams.a$ArrString();
      if (this.M == 0.0F && this.b == 0.0F) {
         return new float[]{var1, var2};
      }

      double var4 = Math.abs(this.w(var1 - this.M));
      double var6 = Math.abs(var2 - this.b);
      float var8 = var1;
      float var9 = var2;
      if (!this.R(var4) && this.L(var4)) {
         double var10 = this.o.nextGaussian() * 0.005;
         var8 = var1 + (float)var10;
      }

      if (!this.R(var6) && this.L(var6)) {
         double var12 = this.o.nextGaussian() * 0.005;
         var9 = var2 + (float)var12;
      }

      return new float[]{var8, var9};
   }

   private boolean R(double var1) {
      String[] var3 = Teams.a$ArrString();
      return Math.abs(var1) <= 1.0E-10 || this.z(360.0, var1);
   }

   private boolean L(double var1) {
      String[] var3 = Teams.a$ArrString();
      if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
         double[] var4 = B;
         int var5 = var4.length;
         int var6 = 0;
         while (var6 < var5) {
            double var7 = var4[var6];
            if (this.z(var7, var1)) {
               return true;
            }

            var6++;
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean z(double var1, double var3) {
      String[] var5 = Teams.a$ArrString();
      if (var1 == 0.0) {
         return Math.abs(var3) <= 1.0E-10;
      }

      double var6 = var3 / var1;
      return Math.abs(var6 - Math.round(var6)) <= 1.0E-10;
   }

   public static float N(ServerboundMovePlayerPacket var0) {
      if (G.gameMode == null) {
         return 0.0F;
      }

      Class var10000 = var0.getClass();
      String[] var10001 = new String[2];
      String[] var3 = c;
      var10001[0] = "yRot";
      var10001[1] = "f_134121_";
      Field var1 = B(var10000, var10001);

      try {
         return var1.getFloat(var0);
      } catch (Exception var4) {
         FileManager.K.error("Failed to get yrot field", var4);
         return 0.0F;
      }
   }

   public static float n(ServerboundMovePlayerPacket var0) {
      if (G.gameMode == null) {
         return 0.0F;
      }

      Class var10000 = var0.getClass();
      String[] var10001 = new String[2];
      String[] var3 = c;
      var10001[0] = "xRot";
      var10001[1] = "f_134122_";
      Field var1 = B(var10000, var10001);

      try {
         return var1.getFloat(var0);
      } catch (Exception var4) {
         FileManager.K.error("Failed to get xrot field", var4);
         return 0.0F;
      }
   }

   private static Field B(Class var0, String[] var1) {
      String[] var2 = Teams.a$ArrString();
      if (var0 != null && var1 != null && var1.length != 0) {
         Exception var3 = null;
         Class var4 = var0;
         if (var4 != null) {
            String[] var5 = var1;
            int var6 = var5.length;
            int var7 = 0;
            while (var7 < var6) {
               String var8 = var5[var7];
               if (var8 != null) {
                  try {
                     Field var9 = var4.getDeclaredField(var8);
                     var9.setAccessible(true);
                     return var9;
                  } catch (Exception var10) {
                     var3 = var10;
                  }
               }

               var7++;
            }

            var4 = var4.getSuperclass();
         }

         throw new Disabler$UnableToFindFieldException(var3);
      } else {
         throw new IllegalArgumentException("Class and fieldNames must not be null or empty");
      }
   }

   public static void X(ServerboundMovePlayerPacket var0, float var1) {
      if (G.gameMode != null) {
         Class var10000 = var0.getClass();
         String[] var10001 = new String[2];
         String[] var4 = c;
         var10001[0] = "yRot";
         var10001[1] = "f_134121_";
         Field var2 = B(var10000, var10001);

         try {
            var2.setFloat(var0, var1);
         } catch (Exception var5) {
            FileManager.K.error("Failed to set yrot field", var5);
         }
      }
   }

   public static void b(ServerboundMovePlayerPacket var0, float var1) {
      if (G.gameMode != null) {
         Class var10000 = var0.getClass();
         String[] var10001 = new String[2];
         String[] var4 = c;
         var10001[0] = "xRot";
         var10001[1] = "f_134122_";
         Field var2 = B(var10000, var10001);

         try {
            var2.setFloat(var0, var1);
         } catch (Exception var5) {
            FileManager.K.error("Failed to set xrot field", var5);
         }
      }
   }

   @EventTarget(3)
   public void C(com.elowen.events.impl.EventPacket var1) {
      String[] var2 = Teams.a$ArrString();
      if (G.player != null) {
         this.f$V();
         if (var1.M() == EventType.RECEIVE && var1.R$Packet() instanceof ClientboundLoginPacket) {
            this.J$V();
         } else {
            if (var1.M() == EventType.RECEIVE && var1.R$Packet() instanceof ClientboundOpenScreenPacket && this.Q.w()) {
               this.q = System.currentTimeMillis();
               this.U = true;
               String[] var8 = c;
               this.n("Inventory opened at: " + this.q);
            }

            if (var1.M() == EventType.SEND) {
               if (this.y.w() && var1.R$Packet() instanceof ServerboundSetCarriedItemPacket var3) {
                  int var12 = var3.getSlot();
                  if (this.P != -1 && var12 != this.P) {
                     this.d(this.P, var12);
                  }

                  this.P = var12;
                  String[] var22 = c;
                  this.n("Processed slot switch: " + this.P + " -> " + var12);
               }

               if (this.Q.w() && var1.R$Packet() instanceof ServerboundContainerClosePacket var9 && this.U) {
                  long var14 = System.currentTimeMillis();
                  long var6 = var14 - this.q;
                  if (var6 <= 150L) {
                     var1.c(true);
                     this.T = var9;
                     this.r = 151L - var6;
                     this.F = System.currentTimeMillis();
                     this.U = false;
                     this.n("InventoryFrequency: Storing close packet, will send after " + this.r + "ms");
                     return;
                  }

                  this.U = false;
                  String[] var23 = c;
                  this.n("InventoryFrequency: Allowed close packet after " + var6 + "ms");
               }
            }

            if (this.Z.r && var1.M() == EventType.SEND && !var1.c$Z() && G.player != null) {
               if (var1.R$Packet() instanceof ServerboundMovePlayerPacket var10) {
                  if (var10.hasRotation()) {
                     if (var10.getYRot(0.0F) < 360.0F && var10.getYRot(0.0F) > -360.0F) {
                        if (var10.hasPosition()) {
                           var1.S(
                              new PosRot(
                                 var10.getX(0.0),
                                 var10.getY(0.0),
                                 var10.getZ(0.0),
                                 var10.getYRot(0.0F) + 720.0F,
                                 var10.getXRot(0.0F),
                                 var10.isOnGround(),
                                 var10.horizontalCollision()
                              )
                           );
                        }

                        var1.S(new Rot(var10.getYRot(0.0F) + 720.0F, var10.getXRot(0.0F), var10.isOnGround(), var10.horizontalCollision()));
                     }

                     float var16 = this.m;
                     this.m = var10.getYRot(0.0F);
                     this.X = Math.abs(this.m - var16);
                     this.E = true;
                     if (this.X > 2.0F) {
                        float var5 = Math.abs(this.X - this.e);
                        if (var5 < 1.0E-4) {
                           this.n("Disabling DuplicateRotPlace!");
                           if (var10.hasPosition()) {
                              var1.S(
                                 new PosRot(
                                    var10.getX(0.0),
                                    var10.getY(0.0),
                                    var10.getZ(0.0),
                                    var10.getYRot(0.0F) + 0.002F,
                                    var10.getXRot(0.0F),
                                    var10.isOnGround(),
                                    var10.horizontalCollision()
                                 )
                              );
                           }

                           var1.S(new Rot(var10.getYRot(0.0F) + 0.002F, var10.getXRot(0.0F), var10.isOnGround(), var10.horizontalCollision()));
                        }
                     }
                  }
               } else if (var1.R$Packet() instanceof ServerboundUseItemOnPacket && this.E) {
                  this.e = this.X;
                  this.E = false;
               }
            }

            if ((this.t.r || this.p.r) && var1.R$Packet() instanceof ServerboundMovePlayerPacket var11) {
               float var18 = N(var11);
               float var19 = n(var11);
               boolean var20 = false;
               if (this.t.r && this.U(var18, var19)) {
                  float[] var7 = this.Q(var18, var19);
                  var18 = var7[0];
                  var19 = var7[1];
                  var20 = true;
               }

               if (this.p.r) {
                  float[] var21 = this.V(var18, var19);
                  if (var21[0] != var18 || var21[1] != var19) {
                     var18 = var21[0];
                     var19 = var21[1];
                     var20 = true;
                     this.n("PerfectRotation: Modified rotation");
                  }
               }

               if (var20) {
                  X(var11, var18);
                  b(var11, com.elowen.utils.MathUtils.l(var19));
               }

               this.M = N(var11);
               this.b = n(var11);
            }
         }
      }
   }

   static {
      B = new double[]{0.1, 0.25};
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
