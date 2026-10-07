package com.elowen.utils.renderer.threeD;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.skija.PaintStrokeJoin;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.Rect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joml.Vector4f;

public class Skija3DRenderer {
   private final List<Skija3DRenderer$Face> c = new ArrayList<>();
   private final List<Skija3DRenderer$Edge> I = new ArrayList<>();
   private final List<Skija3DRenderer$PolyPath> A = new ArrayList<>();
   private Canvas C;
   private WorldProjector L;
   private static final int[][] S = new int[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {0, 1, 5, 4}, {1, 2, 6, 5}, {2, 3, 7, 6}, {3, 0, 4, 7}};
   private static final double[][] D = new double[][]{{0.0, -1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, -1.0}, {1.0, 0.0, 0.0}, {0.0, 0.0, 1.0}, {-1.0, 0.0, 0.0}};
   private static final int[][] m = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
   private static final double[] Q = new double[]{0.0, 1.0, 0.0};

   public void I(Canvas var1, WorldProjector var2) {
      this.C = var1;
      this.L = var2;
      this.c.clear();
      this.I.clear();
      this.A.clear();
   }

   public boolean s$Z() {
      String var1 = WorldProjector.E$String();
      return this.c.isEmpty() && this.I.isEmpty() && this.A.isEmpty();
   }

   public void u(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, float var15) {
      this.G(var1, var3, var5, var7, var9, var11, var13, var14, var15, true);
   }

   public void E(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, float var15) {
      this.G(var1, var3, var5, var7, var9, var11, var13, var14, var15, false);
   }

   private void G(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, float var15, boolean var16) {
      String var17 = WorldProjector.E$String();
      if (this.C != null && this.L != null) {
         double[][] var18 = new double[][]{
            {var1, var3, var5},
            {var7, var3, var5},
            {var7, var3, var11},
            {var1, var3, var11},
            {var1, var9, var5},
            {var7, var9, var5},
            {var7, var9, var11},
            {var1, var9, var11}
         };
         double var19 = 0.4;
         double var21 = 0.8;
         double var23 = -0.4;
         double var25 = Math.sqrt(var19 * var19 + var21 * var21 + var23 * var23);
         double var27 = var19 / var25;
         double var29 = var21 / var25;
         double var31 = var23 / var25;
         int var33 = 0;
         while (var33 < 6) {
            int[] var34 = S[var33];
            double[] var35 = D[var33];
            double var36 = var35[0] * var27 + var35[1] * var29 + var35[2] * var31;
            double var38 = 0.45 + 0.55 * Math.max(0.0, var36);
            int var40 = var16 ? R(var13, var38) : var13;
            ArrayList var41 = new ArrayList(4);
            float var42 = 0.0F;
            int var43 = 0;
            while (var43 < 4) {
               double[] var44 = var18[var34[var43]];
               Vector4f var45 = this.L.F(var44[0], var44[1], var44[2]);
               var41.add(var45);
               var42 += var45.w;
               var43++;
            }

            List var53 = WorldProjector.a(var41);
            if (var53.size() >= 3) {
               this.c.add(new Skija3DRenderer$Face(this.H(var53), var42 * 0.25F, var40));
            }

            var33++;
         }

         int[][] var47 = m;
         int var48 = var47.length;
         int var49 = 0;
         while (var49 < var48) {
            int[] var51 = var47[var49];
            this.g(var18[var51[0]][0], var18[var51[0]][1], var18[var51[0]][2], var18[var51[1]][0], var18[var51[1]][1], var18[var51[1]][2], var14, var15);
            var49++;
         }
      }
   }

   public void g(double var1, double var3, double var5, double var7, double var9, double var11, int var13, float var14) {
      String var15 = WorldProjector.E$String();
      if (this.C != null && this.L != null) {
         Vector4f var16 = this.L.F(var1, var3, var5);
         Vector4f var17 = this.L.F(var7, var9, var11);
         float[] var18 = this.L.d(var16, var17);
         if (var18 != null) {
            this.I.add(new Skija3DRenderer$Edge(var18[0], var18[1], var18[2], var18[3], var13, var14));
         }
      }
   }

   public void m(double[][] var1, boolean var2, int var3, float var4) {
      String var5 = WorldProjector.E$String();
      if (this.C != null && this.L != null && var1.length >= 2) {
         ArrayList var6 = new ArrayList();
         int var7 = 0;
         if (var7 + 1 < var1.length) {
            Vector4f var8 = this.L.F(var1[var7][0], var1[var7][1], var1[var7][2]);
            Vector4f var9 = this.L.F(var1[var7 + 1][0], var1[var7 + 1][1], var1[var7 + 1][2]);
            float[] var10 = this.L.d(var8, var9);
            if (var10 == null) {
               if (var6.size() >= 2) {
                  this.A.add(new Skija3DRenderer$PolyPath(x(var6), var3, var4, var2));
               }

               var6.clear();
            }

            if (var6.isEmpty()) {
               var6.add(new float[]{var10[0], var10[1]});
            }

            var6.add(new float[]{var10[2], var10[3]});
            var7++;
         }

         if (var6.size() >= 2) {
            this.A.add(new Skija3DRenderer$PolyPath(x(var6), var3, var4, var2));
         }
      }
   }

   private static float[] x(List var0) {
      float[] var4 = null;
      String var10000 = WorldProjector.E$String();
      float[] var2 = new float[var0.size() * 2];
      String var1 = var10000;
      int var3 = 0;

      while (true) {
         if (var3 < var0.size()) {
            var2[var3 * 2] = ((float[])var0.get(var3))[0];
            var4 = var2;
            if (var1 != null) {
               break;
            }

            var2[var3 * 2 + 1] = ((float[])var0.get(var3))[1];
            var3++;
            if (var1 == null) {
               continue;
            }
         }

         var4 = var2;
         break;
      }

      return var4;
   }

   public void N(double[][] var1, int var2, float var3, int var4) {
      String var5 = WorldProjector.E$String();
      if (this.C != null && this.L != null && var1.length >= 2) {
         int var6 = var1.length;
         if (var4 < 3) {
            var4 = 3;
         }

         double[][] var7 = new double[var6][3];
         int var8 = 0;
         while (var8 < var6) {
            int var9 = var8 == 0 ? 0 : var8 - 1;
            int var10 = var8 == var6 - 1 ? var6 - 1 : var8 + 1;
            double var11 = var1[var10][0] - var1[var9][0];
            double var13 = var1[var10][1] - var1[var9][1];
            double var15 = var1[var10][2] - var1[var9][2];
            double var17 = Math.sqrt(var11 * var11 + var13 * var13 + var15 * var15);
            if (var17 < 1.0E-9) {
               var7[var8] = new double[]{0.0, 1.0, 0.0};
            }

            var7[var8] = new double[]{var11 / var17, var13 / var17, var15 / var17};
            var8++;
         }

         double[][][] var47 = new double[var6][var4][3];
         double[] var48 = null;
         int var49 = 0;
         while (var49 < var6) {
            double[] var52 = var7[var49];
            double var12 = Q[1] * var52[2] - Q[2] * var52[1];
            double var14 = Q[2] * var52[0] - Q[0] * var52[2];
            double var16 = Q[0] * var52[1] - Q[1] * var52[0];
            double var18 = Math.sqrt(var12 * var12 + var14 * var14 + var16 * var16);
            if (var18 < 1.0E-9) {
               double[] var10000 = new double[]{1.0, 0.0, 0.0};
            }

            double[] var20 = new double[]{var12 / var18, var14 / var18, var16 / var18};
            if (var48 != null && var20[0] * var48[0] + var20[1] * var48[1] + var20[2] * var48[2] < 0.0) {
               var20[0] = -var20[0];
               var20[1] = -var20[1];
               var20[2] = -var20[2];
            }

            double[] var21 = new double[]{
               var52[1] * var20[2] - var52[2] * var20[1], var52[2] * var20[0] - var52[0] * var20[2], var52[0] * var20[1] - var52[1] * var20[0]
            };
            int var22 = 0;
            while (var22 < var4) {
               double var23 = (Math.PI * 2) * var22 / var4;
               double var25 = Math.cos(var23);
               double var27 = Math.sin(var23);
               var47[var49][var22][0] = var1[var49][0] + var3 * (var25 * var20[0] + var27 * var21[0]);
               var47[var49][var22][1] = var1[var49][1] + var3 * (var25 * var20[1] + var27 * var21[1]);
               var47[var49][var22][2] = var1[var49][2] + var3 * (var25 * var20[2] + var27 * var21[2]);
               var22++;
            }

            var49++;
         }

         double var51 = Math.sqrt(0.9600000000000002);
         double var53 = 0.4 / var51;
         double var54 = 0.8 / var51;
         double var55 = -0.4 / var51;
         int var56 = 0;
         while (var56 < var6 - 1) {
            int var19 = 0;
            while (var19 < var4) {
               int var59 = (var19 + 1) % var4;
               double[][] var60 = new double[][]{var47[var56][var19], var47[var56][var59], var47[var56 + 1][var59], var47[var56 + 1][var19]};
               double var62 = var47[var56][var59][0] - var47[var56][var19][0];
               double var24 = var47[var56][var59][1] - var47[var56][var19][1];
               double var26 = var47[var56][var59][2] - var47[var56][var19][2];
               double var28 = var47[var56 + 1][var19][0] - var47[var56][var19][0];
               double var30 = var47[var56 + 1][var19][1] - var47[var56][var19][1];
               double var32 = var47[var56 + 1][var19][2] - var47[var56][var19][2];
               double var34 = var24 * var32 - var26 * var30;
               double var36 = var26 * var28 - var62 * var32;
               double var38 = var62 * var30 - var24 * var28;
               double var40 = Math.sqrt(var34 * var34 + var36 * var36 + var38 * var38);
               double var42 = 0.45;
               if (var40 > 1.0E-9) {
                  double var44 = var34 / var40 * var53 + var36 / var40 * var54 + var38 / var40 * var55;
                  var42 = 0.45 + 0.55 * Math.max(0.0, var44);
               }

               this.R(var60, R(var2, var42));
               var19++;
            }

            var56++;
         }
      }
   }

   public void R(double[][] var1, int var2) {
      String var3 = WorldProjector.E$String();
      if (this.C != null && this.L != null && var1.length >= 3) {
         ArrayList var4 = new ArrayList(var1.length);
         float var5 = 0.0F;
         double[][] var6 = var1;
         int var7 = var6.length;
         int var8 = 0;
         while (var8 < var7) {
            double[] var9 = var6[var8];
            Vector4f var10 = this.L.F(var9[0], var9[1], var9[2]);
            var4.add(var10);
            var5 += var10.w;
            var8++;
         }

         List var11 = WorldProjector.a(var4);
         if (var11.size() >= 3) {
            this.c.add(new Skija3DRenderer$Face(this.H(var11), var5 / var1.length, var2));
         }
      }
   }

   public void W(double var1, double var3, double var5, int var7, float var8) {
      String var9 = WorldProjector.E$String();
      if (this.C != null && this.L != null) {
         float[] var10 = this.L.p(var1, var3, var5);
         if (var10 != null) {
            Paint var11 = new Paint();

            try {
               var11.setColor(var7);
               var11.setAntiAlias(true);
               this.C.drawCircle(var10[0], var10[1], var8, var11);
            } catch (Throwable var15) {
               try {
                  var11.close();
               } catch (Throwable var14) {
                  var15.addSuppressed(var14);
               }

               throw var15;
            }

            var11.close();
         }
      }
   }

   public void G(double var1, double var3, double var5, int var7, float var8) {
      String var9 = WorldProjector.E$String();
      if (this.C != null && this.L != null) {
         float[] var10 = this.L.p(var1, var3, var5);
         if (var10 != null) {
            Paint var11 = new Paint();

            try {
               var11.setColor(var7);
               var11.setAntiAlias(true);
               var11.setMode(PaintMode.FILL);
               this.C.drawRect(Rect.makeLTRB(var10[0] - var8, var10[1] - var8, var10[0] + var8, var10[1] + var8), var11);
            } catch (Throwable var15) {
               try {
                  var11.close();
               } catch (Throwable var14) {
                  var15.addSuppressed(var14);
               }

               throw var15;
            }

            var11.close();
         }
      }
   }

   public void G$V() {
      String var1 = WorldProjector.E$String();
      if (this.C != null) {
         this.c.sort(Skija3DRenderer::deobfLambda$endFrame$0);
         Iterator var2 = this.c.iterator();
         while (var2.hasNext()) {
            Skija3DRenderer$Face var3 = (Skija3DRenderer$Face)var2.next();
            Paint var4 = new Paint();

            try {
               Path var5 = Path.makePolygon(s(var3.d), true);

               try {
                  var4.setColor(var3.g);
                  var4.setAntiAlias(true);
                  var4.setMode(PaintMode.FILL);
                  this.C.drawPath(var5, var4);
               } catch (Throwable var15) {
                  if (var5 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var12) {
                        var15.addSuppressed(var12);
                     }
                  }

                  throw var15;
               }

               if (var5 != null) {
                  var5.close();
               }
            } catch (Throwable var17) {
               try {
                  var4.close();
               } catch (Throwable var11) {
                  var17.addSuppressed(var11);
               }

               throw var17;
            }

            var4.close();
         }

         var2 = this.I.iterator();
         while (var2.hasNext()) {
            Skija3DRenderer$Edge var20 = (Skija3DRenderer$Edge)var2.next();
            Paint var22 = new Paint();

            try {
               var22.setColor(var20.g);
               var22.setAntiAlias(true);
               var22.setMode(PaintMode.STROKE);
               var22.setStrokeWidth(var20.W);
               this.C.drawLine(var20.M, var20.A, var20.m, var20.B, var22);
            } catch (Throwable var13) {
               try {
                  var22.close();
               } catch (Throwable var10) {
                  var13.addSuppressed(var10);
               }

               throw var13;
            }

            var22.close();
         }

         for (Skija3DRenderer$PolyPath var21 : this.A) {
            if (var21.w.length >= 4) {
               Paint var23 = new Paint();

               try {
                  Path var24 = Path.makePolygon(s(var21.w), var21.f);

                  try {
                     var23.setColor(var21.O);
                     var23.setAntiAlias(true);
                     var23.setMode(PaintMode.STROKE);
                     var23.setStrokeWidth(var21.X);
                     var23.setStrokeJoin(PaintStrokeJoin.ROUND);
                     var23.setStrokeCap(PaintStrokeCap.ROUND);
                     this.C.drawPath(var24, var23);
                  } catch (Throwable var14) {
                     if (var24 != null) {
                        try {
                           var24.close();
                        } catch (Throwable var9) {
                           var14.addSuppressed(var9);
                        }
                     }

                     throw var14;
                  }

                  if (var24 != null) {
                     var24.close();
                  }
               } catch (Throwable var16) {
                  try {
                     var23.close();
                  } catch (Throwable var8) {
                     var16.addSuppressed(var8);
                  }

                  throw var16;
               }

               var23.close();
               break;
            }
         }
      }
   }

   private float[] H(List var1) {
      float[] var6 = null;
      String var10000 = WorldProjector.E$String();
      float[] var3 = new float[var1.size() * 2];
      int var4 = 0;
      String var2 = var10000;

      while (true) {
         if (var4 < var1.size()) {
            float[] var5 = this.L.N((Vector4f)var1.get(var4));
            var3[var4 * 2] = var5[0];
            var6 = var3;
            if (var2 != null) {
               break;
            }

            var3[var4 * 2 + 1] = var5[1];
            var4++;
            if (var2 == null) {
               continue;
            }
         }

         var6 = var3;
         break;
      }

      return var6;
   }

   private static Point[] s(float[] var0) {
      Point[] var4 = null;
      String var10000 = WorldProjector.E$String();
      Point[] var2 = new Point[var0.length / 2];
      String var1 = var10000;
      int var3 = 0;

      while (true) {
         if (var3 < var2.length) {
            var4 = var2;
            if (var1 != null) {
               break;
            }

            var2[var3] = new Point(var0[var3 * 2], var0[var3 * 2 + 1]);
            var3++;
            if (var1 == null) {
               continue;
            }
         }

         var4 = var2;
         break;
      }

      return var4;
   }

   private static int R(int var0, double var1) {
      int var3 = var0 >>> 24 & 0xFF;
      int var4 = (int)((var0 >>> 16 & 0xFF) * var1);
      int var5 = (int)((var0 >>> 8 & 0xFF) * var1);
      int var6 = (int)((var0 & 0xFF) * var1);
      return var3 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   private static int deobfLambda$endFrame$0(Skija3DRenderer$Face var0, Skija3DRenderer$Face var1) {
      return Float.compare(var1.K, var0.K);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
