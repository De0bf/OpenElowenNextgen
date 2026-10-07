package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventRenderAfterGUI;
import com.elowen.events.impl.EventMouseClick;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.impl.render.PostProcess;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.Colors;
import com.elowen.utils.MouseUtils;
import com.elowen.utils.FontIcons;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.Fonts;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaFonts;
import com.elowen.utils.renderer.shader.SkijaBlur;
import com.elowen.utils.renderer.shader.SkijaShadow;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.StringValue;
import com.elowen.values.impl.ModeValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public class ClickGUI extends Screen {
   private static final Minecraft I;
   private static final float W = 23.498F;
   private static final float m = 32.0F;
   private static final int H = 256;
   private static final float M = 40.0F;
   private static final float b = 14.0F;
   private static final float Y = 32.0F;
   private static final float s = 0.6F;
   private static final float w = 0.5F;
   private static final int e = 150;
   private static final int N = 190;
   public static float y;
   public static float t;
   public static float BS;
   public static float B;
   Category O = null;
   Module r = null;
   int[] o = new int[]{-1, -1};
   boolean d = false;
   SmoothAnimationTimer X = new SmoothAnimationTimer(100.0F);
   SmoothAnimationTimer f = new SmoothAnimationTimer(140.0F);
   SmoothAnimationTimer Bp = new SmoothAnimationTimer(100.0F);
   SmoothAnimationTimer l = new SmoothAnimationTimer(0.0F);
   SmoothAnimationTimer i = new SmoothAnimationTimer(0.0F);
   SmoothAnimationTimer Bw = new SmoothAnimationTimer(0.0F);
   HashMap L = new ClickGUI$7(this);
   HashMap U = new ClickGUI$3(this);
   HashMap V = new ClickGUI$6(this);
   HashMap D = new ClickGUI$1(this);
   HashMap Bo = new ClickGUI$5(this);
   HashMap u = new ClickGUI$4(this);
   HashMap G = new ClickGUI$2(this);
   String J = "";
   float B8;
   float B3;
   boolean z;
   boolean Q;
   boolean R;
   boolean B_;
   boolean T;
   Category K;
   Module Z;
   Module a;
   SmoothAnimationTimer g;
   SmoothAnimationTimer h;
   List k;
   List x;
   BooleanValue Bn;
   FloatValue c;
   FloatValue S;
   ModeValue E;
   StringValue Bj;
   StringValue n;
   int C;
   int p;
   String BC;
   private boolean P;
   private float A;
   private SmoothAnimationTimer q;
   private com.elowen.utils.TimeHelper BK;
   private float BR;
   private SmoothAnimationTimer Bg;
   private com.elowen.utils.TimeHelper F;
   private final com.elowen.utils.renderer.SkijaRenderer v;
   private PostProcess j;
   private static String Bv;
   private static final String[] ab = new String[]{" / ", "Elowen", " - ", "[ClickGUI] Flush failed: ", "Press a key to bind ", "(Press ESC to remove/cancel key bind)", "Elowen", "< ", " / ", "[ClickGUI] Render failed: "};
   public ClickGUI() {
      super(Component.nullToEmpty("Elowen"));
      String var10000 = I();
      this.z = false;
      this.Q = false;
      this.R = false;
      this.B_ = false;
      this.T = false;
      this.K = null;
      String var1 = var10000;
      this.Z = null;
      this.a = null;
      this.g = new SmoothAnimationTimer(0.0F);
      this.h = new SmoothAnimationTimer(0.0F);
      this.P = false;
      this.A = 0.0F;
      this.q = new SmoothAnimationTimer(0.0F);
      this.BK = new com.elowen.utils.TimeHelper();
      this.BR = 0.0F;
      this.Bg = new SmoothAnimationTimer(0.0F);
      this.F = new com.elowen.utils.TimeHelper();
      this.v = com.elowen.utils.renderer.SkiaRenderManager.c$m();
      if (var1 != null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.x());
      }
   }

   private PostProcess T$PostProcess() {
      String var1 = I();
      if (this.j == null) {
         try {
            this.j = (PostProcess)Elowen.S$Elowen().q$ModuleManager().A(PostProcess.class);
         } catch (Exception var3) {
         }
      }

      return this.j;
   }

   private float P(String var1, float var2) {
      float var3 = var2 * 23.498F;
      return com.elowen.utils.renderer.Fonts.i(var3).measureTextWidth(var1);
   }

   private void c(Canvas var1, String var2, float var3, float var4, float var5, int var6) {
      float var7 = var5 * 23.498F;
      Typeface var8 = com.elowen.utils.renderer.Fonts.i(var7).getTypeface();
      this.G(var1, var2, var3, var4, var8, var7, var6);
   }

   private void G(Canvas var1, String var2, float var3, float var4, Typeface var5, float var6, int var7) {
      String var8 = I();
      if (var2 != null && !var2.isEmpty() && var5 != null) {
         var2 = com.elowen.utils.renderer.SkijaFonts.b(var2);
         if (!var2.isEmpty()) {
            Font var9 = com.elowen.utils.renderer.SkijaFonts.V(var5, var6);

            try {
               Paint var10 = new Paint();

               try {
                  var10.setColor(var7);
                  var10.setAntiAlias(true);
                  FontMetrics var11 = var9.getMetrics();
                  float var12 = var4 - var11.getAscent();
                  var1.drawString(var2, var3, var12, var9, var10);
               } catch (Throwable var15) {
                  try {
                     var10.close();
                  } catch (Throwable var14) {
                     var15.addSuppressed(var14);
                  }

                  throw var15;
               }

               var10.close();
            } catch (Throwable var16) {
               if (var9 != null) {
                  try {
                     var9.close();
                  } catch (Throwable var13) {
                     var16.addSuppressed(var13);
                  }
               }

               throw var16;
            }

            if (var9 != null) {
               var9.close();
            }
         }
      }
   }

   private void M(Canvas var1, String var2, float var3, float var4, Typeface var5, float var6, int var7) {
      String var8 = I();
      if (var2 != null && !var2.isEmpty() && var5 != null) {
         var2 = com.elowen.utils.renderer.SkijaFonts.b(var2);
         if (!var2.isEmpty()) {
            Font var9 = com.elowen.utils.renderer.SkijaFonts.V(var5, var6);

            try {
               Paint var10 = new Paint();

               try {
                  var10.setColor(var7);
                  var10.setAntiAlias(true);
                  var1.drawString(var2, var3, var4, var9, var10);
               } catch (Throwable var15) {
                  try {
                     var10.close();
                  } catch (Throwable var14) {
                     var15.addSuppressed(var14);
                  }

                  throw var15;
               }

               var10.close();
            } catch (Throwable var16) {
               if (var9 != null) {
                  try {
                     var9.close();
                  } catch (Throwable var13) {
                     var16.addSuppressed(var13);
                  }
               }

               throw var16;
            }

            if (var9 != null) {
               var9.close();
            }
         }
      }
   }

   protected void init() {
      Elowen.S$Elowen().e().I(this);
      this.u.forEach((a, b) -> deobfLambda$init$0((Value)a, (SmoothAnimationTimer)b));
   }

   public void onClose() {
      String var10000 = I();
      Elowen.S$Elowen().q$S().X$V();
      String var1 = var10000;
      Elowen.S$Elowen().e().R(this);
      ClickGUI var2 = this;
      if (var1 == null) {
         if (this.n != null) {
            this.n = null;
            I.onTextInputFocusChange(this, false);
         }

         var2 = this;
      }

      var2.onClose();
   }

   @EventTarget
   public void G(EventMouseClick var1) {
      String var2 = I();
      if (I.gui.screen() == this) {
         double var3 = I.mouseHandler.xpos();
         double var5 = I.mouseHandler.ypos();
         int var7 = (int)(var3 * I.getWindow().getGuiScaledWidth() / I.getWindow().getWidth());
         int var8 = (int)(var5 * I.getWindow().getGuiScaledHeight() / I.getWindow().getHeight());
         int var9 = var1.v$I();
         boolean var10 = var1.Z();
         if (var10) {
            if (var9 == 0) {
               this.P = true;
            }

            if (this.a == null || var9 != 3 && var9 != 4) {
               label247:
               if (var9 != 2 && this.a == null) {
                  if (this.Z != null) {
                     if (var9 == 0) {
                        this.Z.R$V();
                     }

                     if (var9 == 1) {
                        this.r = this.Z;
                        this.x = Elowen.S$Elowen().E$C().G(this.Z);
                        this.Bw.J = this.Bw.l = 0.0F;
                     }
                  }

                  if (var9 != 0) {
                     return;
                  }

                  if (this.d && !this.B_ && !this.T) {
                     this.O = null;
                     this.r = null;
                     this.x = null;
                  }

                  if (this.R && this.K != null) {
                     this.O = this.K;
                     this.i.l = this.i.J = 0.0F;
                     this.g.l = 5.0F;
                     this.g.J = 255.0F;
                     this.R = false;
                  }

                  boolean var11 = this.O != null
                     ? com.elowen.utils.MouseUtils.J(var7, var8, y, t, y + BS, t + 25.0F)
                     : com.elowen.utils.MouseUtils.J(var7, var8, y, t, y + 100.0F, t + 40.0F);
                  if ((this.O == null || !this.d) && var11) {
                     this.F(var7, var8);
                     this.T = true;
                  }

                  if (com.elowen.utils.MouseUtils.J(var7, var8, y + BS - 10.0F, t + B - 10.0F, y + BS, t + B)) {
                     this.F(var7, var8);
                     this.B_ = true;
                  }

                  if (this.Bn != null) {
                     this.Bn.P(!this.Bn.w());
                  }

                  if (this.c != null) {
                     this.S = this.c;
                  }

                  if (this.E != null) {
                     this.E.y(this.p);
                     SmoothAnimationTimer var12 = (SmoothAnimationTimer)this.u.get(this.E);
                     var12.l = 0.0F;
                     var12.J = 255.0F;
                  }

                  if (this.Bj != null) {
                     if (this.n == this.Bj) {
                        break label247;
                     }

                     this.n = this.Bj;
                     String var13 = this.Bj.R$String();
                     this.C = var13 == null ? 0 : var13.length();
                     I.onTextInputFocusChange(this, true);
                  }

                  if (this.n != null) {
                     this.n = null;
                     I.onTextInputFocusChange(this, false);
                  }
               }

               if (var9 != 2 || this.Z == null) {
                  return;
               }

               this.a = this.Z;
               this.n = null;
            }

            this.a.V(-var9);
            this.a = null;
         }

         if (var9 == 0) {
            this.P = false;
         }

         if (this.B_) {
            this.B_ = false;
            this.F(-1, -1);
         }

         if (this.T) {
            this.T = false;
            this.F(-1, -1);
         }

         if (this.S != null) {
            this.S = null;
         }
      }
   }

   public boolean keyPressed(KeyEvent var1) {
      String var2 = I();
      if (this.a != null) {
         if (var1.key() == 41) {
            this.a.V(0);
            this.a = null;
            return true;
         }

         this.a.V(var1.key());
         this.a = null;
      }

      return this.n != null && this.v(var1.key()) ? true : super.keyPressed(var1);
   }

   private boolean v(int var1) {
      String var2;
      String var3;
      label134: {
         String var10000 = I();
         var3 = this.n.R$String();
         var2 = var10000;
         var10000 = var3;
         if (var2 == null) {
            if (var3 != null) {
               break label134;
            }

            var10000 = "";
         }

         var3 = var10000;
      }

      int var4 = Math.max(0, Math.min(this.C, var3.length()));
      int var6 = var1;
      if (var2 == null) {
         label125:
         if (var1 != 41) {
            var6 = var1;
            byte var10001 = 40;
            if (var2 == null) {
               if (var1 == 40) {
                  break label125;
               }

               var6 = var1;
               var10001 = 42;
            }

            if (var2 == null) {
               if (var6 == var10001) {
                  var6 = var4;
                  if (var2 == null) {
                     if (var4 > 0) {
                        this.n.j(var3.substring(0, var4 - 1) + var3.substring(var4));
                        this.C = var4 - 1;
                     }

                     var6 = 1;
                  }

                  return (boolean)((var6) != 0);
               }

               var6 = var1;
               var10001 = 76;
            }

            if (var2 == null) {
               if (var6 == var10001) {
                  var6 = var4;
                  if (var2 == null) {
                     if (var4 < var3.length()) {
                        this.n.j(var3.substring(0, var4) + var3.substring(var4 + 1));
                     }

                     var6 = 1;
                  }

                  return (boolean)((var6) != 0);
               }

               var6 = var1;
               var10001 = 80;
            }

            if (var2 == null) {
               if (var6 == var10001) {
                  this.C = Math.max(0, var4 - 1);
                  return true;
               }

               var6 = var1;
               var10001 = 79;
            }

            if (var2 == null) {
               if (var6 == var10001) {
                  this.C = Math.min(var3.length(), var4 + 1);
                  return true;
               }

               var6 = var1;
               var10001 = 74;
            }

            if (var2 == null) {
               if (var6 == var10001) {
                  this.C = 0;
                  return true;
               }

               var6 = var1;
               if (var2 != null) {
                  return (boolean)((var1) != 0);
               }

               var10001 = 77;
            }

            if (var6 == var10001) {
               this.C = var3.length();
               return true;
            }

            return false;
         }

         this.n = null;
         I.onTextInputFocusChange(this, false);
         var6 = 1;
      }

      return (boolean)((var6) != 0);
   }

   public boolean charTyped(CharacterEvent var1) {
      String var10000 = I();
      StringValue var3 = this.n;
      String var2 = var10000;
      if (var3 != null) {
         boolean var7 = var1.isAllowedChatCharacter();
         if (var2 != null) {
            return var7;
         }

         if (var7) {
            String var4;
            label35: {
               var4 = var3.R$String();
               var10000 = var4;
               if (var2 == null) {
                  if (var4 != null) {
                     break label35;
                  }

                  var10000 = "";
               }

               var4 = var10000;
            }

            int var5 = Math.max(0, Math.min(this.C, var4.length()));
            String var6 = var1.codepointAsString();
            int var9 = var4.length() + var6.length();
            if (var2 == null) {
               if (var9 <= 256) {
                  var3.j(var4.substring(0, var5) + var6 + var4.substring(var5));
                  this.C = var5 + var6.length();
               }

               var9 = 1;
            }

            return (boolean)((var9) != 0);
         }
      }

      return super.charTyped(var1);
   }

   private int[] X(String var1, int var2, float var3) {
      int var12 = 0;
      float var14 = 0.0F;
      String var10000 = I();
      int var5 = var2;
      float var6 = 0.0F;
      String var4 = var10000;

      while (true) {
         label62:
         if (var5 > 0) {
            float var7 = this.P(var1.substring(var5 - 1, var5), 0.4F);
            float var11 = var6 + var7;
            float var10001 = var3;
            if (var4 == null) {
               float var17;
               var12 = (var17 = var11 - var3) == 0.0F ? 0 : (var17 < 0.0F ? -1 : 1);
               if (var4 != null) {
                  break;
               }

               if (var12 > 0 && var4 == null) {
                  break label62;
               }

               var11 = var6;
               var10001 = var7;
            }

            var6 = var11 + var10001;
            var5--;
            if (var4 == null) {
               continue;
            }
         }

         var12 = var2;
         break;
      }

      int var10 = var12;
      float var8 = 0.0F;

      while (true) {
         label47:
         if (var10 < var1.length()) {
            float var9 = this.P(var1.substring(var10, var10 + 1), 0.4F);
            float var13 = var6 + var8 + var9;
            float var16 = var3;
            if (var4 == null) {
               float var18;
               var14 = (byte)((var18 = var13 - var3) == 0.0F ? 0 : (var18 < 0.0F ? -1 : 1));
               if (var4 != null) {
                  break;
               }

               if (var14 > 0 && var4 == null) {
                  break label47;
               }

               var13 = var8;
               var16 = var9;
            }

            var8 = var13 + var16;
            var10++;
            if (var4 == null) {
               continue;
            }
         }

         var14 = 2;
         break;
      }

      int[] var15 = new int[((int)(var14))];
      var15[0] = var5;
      var15[1] = var10;
      return var15;
   }

   public boolean mouseScrolled(double var1, double var3, double var5, double var7) {
      String var10000 = I();
      double var10 = var7;
      String var9 = var10000;
      ClickGUI var12 = this;
      if (var9 == null) {
         if (this.a == null && com.elowen.utils.MouseUtils.E((int)var1, (int)var3, y + 5.0F, t + 20.0F, 100.0F, B - 5.0F)) {
            this.i.J = (float)(this.i.J + var10 * 15.0);
            this.BK.p();
         }

         var12 = this;
      }

      if (var9 == null) {
         if (var12.x == null) {
            return true;
         }

         var12 = this;
      }

      if (var12.a == null) {
         boolean var13 = com.elowen.utils.MouseUtils.E((int)var1, (int)var3, y + 140.0F, t + 20.0F, BS - 155.0F, B - 25.0F);
         if (var9 != null) {
            return var13;
         }

         if (var13) {
            this.Bw.J = (float)(this.Bw.J + var10 * 15.0);
            this.F.p();
         }
      }

      return true;
   }

   @EventTarget
   public void j(com.elowen.events.impl.EventShader var1) {
      if (I.gui.screen() == this) {
         int var2 = com.elowen.utils.Colors.z(0, 0, 0, 64);
         this.v.d(y, t, this.X.J, this.f.J, 5.0F, var2);
      }
   }

   public void extractBackground(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
   }

   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      String var5 = I();

      try {
         Canvas var6 = com.elowen.utils.renderer.SkiaRenderManager.R$Canvas();
         if (var6 == null) {
            return;
         }

         try {
            this.i(var6, var2, var3);
         } finally {
            com.elowen.utils.renderer.SkiaRenderManager.S$V();
         }
      } catch (RuntimeException var11) {
         System.err.println("[ClickGUI] Render failed: " + var11);
      }
   }

   @EventTarget
   public void a(EventRenderAfterGUI var1) {
      String var2 = I();
      if (I.gui.screen() == this) {
         try {
            com.elowen.utils.renderer.SkiaRenderManager.u$V();
         } catch (RuntimeException var4) {
            System.err.println("[ClickGUI] Flush failed: " + var4);
         }
      }
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   private void i(Canvas var1, int var2, int var3) {
      ClickGUI var136 = null;
      ClickGUI var141 = null;
      ClickGUI var145 = null;
      float var146 = 0.0F;
      ClickGUI var147 = null;
      boolean var148 = false;
      ClickGUI var150 = null;
      float var152 = 0.0F;
      boolean var154 = false;
      boolean var157 = false;
      int var80 = 0;
      ClickGUI var167 = null;
      float var171 = 0.0F;
      String var174 = null;
      SmoothAnimationTimer var143 = null;
      List var47 = null;
      String var4;
      label1112: {
         String var10000 = I();
         this.Z = null;
         var4 = var10000;
         this.z = this.Q = this.R = false;
         ClickGUI var131 = this;
         if (var4 == null) {
            if (this.O == null) {
               this.X.J = 100.0F;
               int var5 = Category.values().length;
               this.f.J = 40.0F + var5 * 25.0F;
               if (var4 == null) {
                  break label1112;
               }
            }

            this.X.J = BS;
            var131 = this;
         }

         var131.f.J = B;
      }

      PostProcess var37;
      boolean var133;
      label1105: {
         label1115: {
            this.X.F(true);
            this.f.F(true);
            var37 = this.T$PostProcess();
            PostProcess var132 = var37;
            if (var4 == null) {
               if (var37 == null) {
                  break label1115;
               }

               var132 = var37;
            }

            var133 = var132.w();
            if (var4 == null) {
               if (!var133) {
                  break label1115;
               }

               var133 = var37.U.w();
            }

            if (var4 != null) {
               break label1105;
            }

            if (var133) {
               var133 = ((1) != 0);
               break label1105;
            }
         }

         var133 = ((0) != 0);
      }

      label1089: {
         boolean var6;
         label1116: {
            var6 = (boolean)var133;
            PostProcess var134 = var37;
            if (var4 == null) {
               if (var37 == null) {
                  break label1116;
               }

               var134 = var37;
            }

            var133 = var134.w();
            if (var4 != null) {
               break label1089;
            }

            if (var133) {
               var133 = var37.r.w();
               if (var4 != null) {
                  break label1089;
               }

               if (var133) {
                  com.elowen.utils.renderer.shader.SkijaBlur.s(
                     this.v, var1, y, t, this.X.l, this.f.l, var37.S.o$F(), 5.0F, List.of(new float[]{y, t, this.X.l, this.f.l})
                  );
               }
            }
         }

         var133 = var6;
      }

      if (var133) {
         float var7 = 4.0F;
         float var8 = var7 * 2.0F;
         float var9 = y - var8;
         float var10 = t - var8;
         float var11 = this.X.l + var8 * 2.0F;
         float var12 = this.f.l + var8 * 2.0F;
         Rect var13 = Rect.makeLTRB(var9, var10, var9 + var11, var10 + var12);
         var1.saveLayer(var13, null);
         RRect var14 = RRect.makeLTRB(y, t, y + this.X.l, t + this.f.l, 5.0F);
         com.elowen.utils.renderer.shader.SkijaShadow.m(var1, var14, var7, Integer.MIN_VALUE, 0.0F, 0.0F);
         var1.restore();
      }

      this.v.d(y, t, this.X.l, this.f.l, 5.0F, com.elowen.utils.Colors.z(0, 0, 0, 40));
      Typeface var38 = com.elowen.utils.renderer.Fonts.n(12.8F).getTypeface();
      float var39 = 0.4F;
      Category[] var40 = Category.values();
      int var43 = var40.length;
      int var46 = 0;

      while (true) {
         if (var46 < var43) {
            Category var48;
            SmoothAnimationTimer var55;
            SmoothAnimationTimer var64;
            label1071: {
               label1070: {
                  var48 = var40[var46];
                  var55 = (SmoothAnimationTimer)this.L.get(var48);
                  var64 = (SmoothAnimationTimer)this.U.get(var48);
                  if (var4 == null) {
                     var136 = this;
                     if (var4 != null) {
                        break;
                     }

                     if (this.O != null) {
                        break label1070;
                     }

                     var64.J = 255.0F;
                  }

                  if (var4 == null) {
                     break label1071;
                  }
               }

               var64.J = 0.0F;
            }

            var55.F(true);
            var64.F(true);
            float var15 = var48.ordinal() * 25 * (var64.l / 255.0F);
            float var182;
            var133 = (((var182 = var64.J - 4.0F) == 0.0F ? 0 : (var182 < 0.0F ? -1 : 1)) != 0);
            if (var4 == null) {
               if (((var133) ? 1 : 0) >= 0) {
                  float var16 = var64.l / 255.0F;
                  int var17 = (int)(var16 * 255.0F) << 24 | 16777215;
                  this.G(var1, var48.B$String(), y + 8.0F + var55.l, t + 41.0F + var15, var38, 12.8F, var17);
                  this.c(var1, var48.G$String(), y + 25.0F + var55.l, t + 40.0F + var15, var39, var17);
               }

               var133 = com.elowen.utils.MouseUtils.J(var2, var3, y, t + 40.0F + var15, y + 100.0F, t + 40.0F + var15 + 20.0F);
            }

            int var76;
            label1060: {
               label1059: {
                  var76 = ((var133) ? 1 : 0);
                  if (var4 == null) {
                     if (var76 == 0) {
                        break label1059;
                     }

                     var55.J = 5.0F;
                  }

                  if (var4 == null) {
                     break label1060;
                  }
               }

               var55.J = 0.0F;
            }

            label1051: {
               float var183;
               var133 = (((var183 = var64.l - 250.0F) == 0.0F ? 0 : (var183 < 0.0F ? -1 : 1)) != 0);
               if (var4 == null) {
                  if (((var133) ? 1 : 0) < 0) {
                     break label1051;
                  }

                  var133 = ((var76) != 0);
               }

               if (((var133) ? 1 : 0) != 0) {
                  this.K = var48;
                  this.R = true;
               }
            }

            var46++;
            if (var4 == null) {
               continue;
            }
         }

         this.Bp.F(true);
         this.l.F(true);
         var136 = this;
         break;
      }

      float var139 = var136.Bp.l;
      float var10001 = 5.0F;
      if (var4 == null) {
         if (var136.Bp.l > 5.0F) {
            float var41 = this.Bp.l / 255.0F;
            var43 = (int)(var41 * 255.0F) << 24 | 16777215;
            this.c(var1, this.J, y + 6.0F + this.l.l, t + 3.0F, 0.4F, var43);
         }

         var139 = 255.0F - this.Bp.l;
         var10001 = 255.0F;
      }

      label1041: {
         float var42 = var139 / var10001;
         var43 = (int)(var42 * 255.0F) << 24 | 16777215;
         this.c(var1, "Elowen", y + 50.0F - this.P("Elowen", 0.75F) / 2.0F, t + 5.0F, 0.75F, var43);
         ClickGUI var140 = this;
         if (var4 == null) {
            if (this.O != null) {
               String var179;
               label1036: {
                  this.Bp.J = 255.0F;
                  var174 = this.O.G$String();
                  Module var10002 = this.r;
                  if (var4 == null) {
                     if (this.r == null) {
                        var179 = "";
                        break label1036;
                     }

                     var10002 = this.r;
                  }

                  var179 = var10002.i();
                  String var10003 = this.r.D();
                  String[] var36 = ab;
                  var179 = " / " + var179 + " - " + var10003;
               }

               label1030: {
                  String var34 = var179;
                  String var35 = var174;
                  this.J = "< " + var35 + var34;
                  this.d = com.elowen.utils.MouseUtils.J(
                     var2, var3, y + 8.0F, t + 5.0F, y + 5.0F + this.P(this.J, 0.4F), t + 5.0F + com.elowen.utils.renderer.Fonts.i(8.0F).getMetrics().getHeight()
                  );
                  var141 = this;
                  label1029:
                  if (var4 == null) {
                     if (this.d) {
                        var141 = this;
                        if (var4 != null) {
                           break label1029;
                        }

                        if (!this.B_) {
                           var141 = this;
                           if (var4 != null) {
                              break label1029;
                           }

                           if (!this.T) {
                              this.l.J = -2.0F;
                              if (var4 == null) {
                                 break label1030;
                              }
                           }
                        }
                     }

                     var141 = this;
                  }

                  var141.l.J = 0.0F;
               }

               label1120: {
                  float var184;
                  var133 = (((var184 = this.i.J - -this.B8) == 0.0F ? 0 : (var184 < 0.0F ? -1 : 1)) != 0);
                  if (var4 == null) {
                     if (((var133) ? 1 : 0) < 0) {
                        this.i.J = -this.B8;
                     }

                     var143 = this.i;
                     if (var4 != null) {
                        break label1120;
                     }

                     float var185;
                     var133 = (((var185 = this.i.J - 0.0F) == 0.0F ? 0 : (var185 < 0.0F ? -1 : 1)) != 0);
                  }

                  if (((var133) ? 1 : 0) > 0) {
                     this.i.J = 0.0F;
                  }

                  var143 = this.i;
               }

               var143.F(true);
               if (var4 == null) {
                  break label1041;
               }
            }

            var140 = this;
         }

         var140.Bp.J = 4.0F;
      }

      label1005: {
         label1004: {
            var1.save();
            var1.clipRect(Rect.makeXYWH(y, t + 20.0F, this.X.l, this.f.l - 25.0F));
            var47 = (List)this.V.get(this.O);
            if (var4 == null) {
               if (var47 == null) {
                  break label1004;
               }

               this.k = var47;
               this.g.J = 255.0F;
            }

            if (var4 == null) {
               break label1005;
            }
         }

         this.g.J = 5.0F;
      }

      this.g.F(true);
      List var144 = var47;
      if (var4 == null) {
         label994: {
            if (var47 == null) {
               var145 = this;
               if (var4 != null) {
                  break label994;
               }

               if (this.g.l < 8.0F) {
                  this.k = null;
               }
            }

            var145 = this;
         }

         var144 = var145.k;
      }

      label1122: {
         label1123: {
            if (var4 == null) {
               label984: {
                  if (var144 != null) {
                     float var49 = 0.0F;
                     this.A = B - 25.0F;
                     float var56 = 0.4F;

                     for (Module var71 : (List<Module>)this.k) {
                        var146 = ((com.elowen.utils.MouseUtils.E(var2, var3, y + 5.0F, t + 20.0F, 120.0F, B - 25.0F)) ? 1 : 0);
                        if (var4 != null) {
                           break label1123;
                        }

                        label977: {
                           label1127: {
                              if (var4 == null) {
                                 if (var146 == 0) {
                                    break label1127;
                                 }

                                 var146 = ((com.elowen.utils.MouseUtils.E(var2, var3, y + 5.0F, t + 20.0F + var49 + this.i.l, 120.0F, 25.0F)) ? 1 : 0);
                              }

                              label1128: {
                                 if (var4 == null) {
                                    if (var146 == 0) {
                                       break label1127;
                                    }

                                    var147 = this;
                                    if (var4 != null) {
                                       break label1128;
                                    }

                                    float var186;
                                    var146 = (byte)((var186 = this.g.l - 250.0F) == 0.0F ? 0 : (var186 < 0.0F ? -1 : 1));
                                 }

                                 if (var146 <= 0) {
                                    break label1127;
                                 }

                                 var147 = this;
                              }

                              if (var147.a == null) {
                                 var148 = true;
                                 break label977;
                              }
                           }

                           var148 = false;
                        }

                        boolean var77;
                        SmoothAnimationTimer var83;
                        label957: {
                           label956: {
                              var77 = var148;
                              var83 = (SmoothAnimationTimer)this.Bo.get(var71);
                              if (var4 == null) {
                                 if (!var71.w()) {
                                    break label956;
                                 }

                                 var83.J = this.g.l;
                              }

                              if (var4 == null) {
                                 break label957;
                              }
                           }

                           var83.J = 6.0F;
                        }

                        int var18;
                        float var21;
                        SmoothAnimationTimer var22;
                        label948: {
                           label947: {
                              var83.F(true);
                              var18 = (int)this.g.l;
                              int var19 = com.elowen.utils.Colors.z(54, 98, 236, (int)var83.l);
                              int var20 = com.elowen.utils.Colors.z(25, 25, 25, var18);
                              var21 = t + 20.0F + var49 + this.i.l;
                              this.v.d(y + 5.0F, var21, 120.0F, 25.0F, 5.0F, var20);
                              this.v.d(y + 5.0F, var21, 120.0F, 25.0F, 5.0F, var19);
                              var22 = (SmoothAnimationTimer)this.D.get(var71);
                              if (var4 == null) {
                                 if (!var77) {
                                    break label947;
                                 }

                                 var22.J = 150.0F;
                                 this.Z = var71;
                              }

                              if (var4 == null) {
                                 break label948;
                              }
                           }

                           var22.J = 5.0F;
                        }

                        var22.F(true);
                        int var23 = com.elowen.utils.Colors.z(255, 255, 255, (int)var22.l / 3);
                        this.v.d(y + 5.0F, var21, 120.0F, 25.0F, 5.0F, var23);
                        int var24 = var18 << 24 | 16777215;
                        this.c(var1, var71.i(), y + 13.0F, t + 25.0F + var49 + this.i.l, var56, var24);
                        var49 += 30.0F;
                        if (var4 != null) {
                           break;
                        }
                     }

                     this.B8 = var49 + 20.0F - B;
                     this.A -= var49 - 5.0F;
                     float var66 = this.B8 + B;
                     if (var4 != null) {
                        break label984;
                     }

                     if (var66 > B - 25.0F) {
                        label932: {
                           this.q.F(true);
                           ClickGUI var149 = this;
                           if (var4 == null) {
                              if (this.BK.e(1000.0)) {
                                 this.q.J = 0.0F;
                                 if (var4 == null) {
                                    break label932;
                                 }
                              }

                              var149 = this;
                           }

                           var149.q.J = 255.0F;
                        }

                        float var72 = B - 25.0F;
                        float var78 = (float)com.elowen.utils.MathUtils.Z(-this.i.l / -this.A, 0.0, 1.0);
                        float var84 = var72 / var66 * var72;
                        float var92 = Math.max(var84, 20.0F);
                        float var100 = var78 * (var72 - var92);
                        int var107 = com.elowen.utils.renderer.RenderUtils.f(3630060, this.q.l / 255.0F);
                        this.v.d(y + 127.0F, t + 20.0F + var100, 3.0F, var92, 1.5F, var107);
                     }
                  }

                  var1.restore();
               }

               var150 = this;
               if (var4 != null) {
                  if (var4 == null) {
                     if (this.S != null) {
                        float var52 = (var2 - y - 140.0F) / (BS - 160.0F);
                        float var60 = this.S.y$F() + (this.S.h$F() - this.S.y$F()) * var52;
                        float var187;
                        var133 = (((var187 = var60 - this.S.y$F()) == 0.0F ? 0 : (var187 < 0.0F ? -1 : 1)) != 0);
                        if (var4 == null) {
                           if (((var133) ? 1 : 0) < 0) {
                              var60 = this.S.y$F();
                           }

                           float var188;
                           var133 = (((var188 = var60 - this.S.h$F()) == 0.0F ? 0 : (var188 < 0.0F ? -1 : 1)) != 0);
                        }

                        if (var4 == null) {
                           if (((var133) ? 1 : 0) > 0) {
                              var60 = this.S.h$F();
                           }

                           var133 = ((Math.round(var60 / this.S.e())) != 0);
                        }

                        var60 = ((var133) ? 1 : 0) * this.S.e();
                        this.S.I(var60);
                     }

                     var150 = this;
                  }

                  if (var4 != null) {
                     break label1122;
                  }

                  var146 = ((var150.T) ? 1 : 0);
                  break label1123;
               }

               var144 = this.x;
            }

            if (var144 != null) {
               byte var50;
               float var57;
               label1133: {
                  var1.save();
                  var1.clipRect(Rect.makeXYWH(y, t + 20.0F, this.X.l, this.f.l - 25.0F));
                  var50 = (((byte)((com.elowen.utils.MouseUtils.E(var2, var3, y + 140.0F, t + 20.0F, BS - 155.0F, B - 25.0F)) ? 1 : 0)));
                  var57 = this.Bw.l;
                  float var189;
                  var133 = (((var189 = this.Bw.J - -this.B3) == 0.0F ? 0 : (var189 < 0.0F ? -1 : 1)) != 0);
                  if (var4 == null) {
                     if (((var133) ? 1 : 0) < 0) {
                        this.Bw.J = -this.B3;
                     }

                     var152 = this.Bw.J;
                     if (var4 != null) {
                        break label1133;
                     }

                     float var190;
                     var133 = (((var190 = this.Bw.J - 0.0F) == 0.0F ? 0 : (var190 < 0.0F ? -1 : 1)) != 0);
                  }

                  if (((var133) ? 1 : 0) > 0) {
                     this.Bw.J = 0.0F;
                  }

                  this.Bw.F(true);
                  var152 = 0.0F;
               }

               float var67 = var152;
               float var73 = 0.0F;
               this.BR = B - 25.0F;
               this.Bn = null;
               Iterator var79 = this.x.iterator();

               while (true) {
                  if (var79.hasNext()) {
                     label1136: {
                        Value var85 = (Value)var79.next();
                        Value var153 = var85;
                        if (var4 == null) {
                           var154 = var85.c$Z();
                           if (var4 != null) {
                              break;
                           }

                           if (!var154) {
                              break label1136;
                           }

                           var153 = var85;
                        }

                        if (var4 == null) {
                           if (var153.J$H() != com.elowen.values.ValueType.BOOLEAN) {
                              break label1136;
                           }

                           var153 = var85;
                        }

                        BooleanValue var93;
                        SmoothAnimationTimer var101;
                        label904: {
                           label903: {
                              var93 = var153.f$O();
                              var101 = (SmoothAnimationTimer)this.u.get(var93);
                              if (var4 == null) {
                                 if (!var93.w()) {
                                    break label903;
                                 }

                                 var101.J = 255.0F;
                              }

                              if (var4 == null) {
                                 break label904;
                              }
                           }

                           var101.J = 0.0F;
                        }

                        var101.F(true);
                        float var108 = 0.4F;
                        float var114 = this.P(var85.r(), var108) + 23.0F;
                        float var191;
                        var133 = (((var191 = var67 + var114 + 20.0F - (BS - 155.0F)) == 0.0F ? 0 : (var191 < 0.0F ? -1 : 1)) != 0);
                        if (var4 == null) {
                           if (((var133) ? 1 : 0) > 0) {
                              var67 = 0.0F;
                              var73 += 20.0F;
                           }

                           var133 = ((var50) != 0);
                        }

                        label894:
                        if (var4 == null) {
                           if (((var133) ? 1 : 0) != 0) {
                              var133 = com.elowen.utils.MouseUtils.E(var2, var3, y + 130.0F + var67, t + var73 + var57 + 20.0F, var114, 13.0F);
                              if (var4 != null) {
                                 break label894;
                              }

                              if (((var133) ? 1 : 0) != 0) {
                                 this.Bn = var93;
                              }
                           }

                           var133 = ((com.elowen.utils.Colors.z(54, 98, 236, (int)var101.l)) != 0);
                        }

                        int var120 = ((var133) ? 1 : 0);
                        this.v.d(y + 140.0F + var67, t + var73 + var57 + 20.0F, 12.0F, 12.0F, 2.0F, com.elowen.utils.Colors.z(0, 0, 0, 150));
                        this.v.d(y + 142.0F + var67, t + var73 + var57 + 22.0F, 8.0F, 8.0F, 2.0F, var120);
                        this.c(var1, var85.r(), y + 155.0F + var67, t + var73 + var57 + 19.0F, var108, -1);
                        var67 += var114;
                     }

                     if (var4 == null) {
                        continue;
                     }
                  }

                  var73 += 10.0F;
                  this.c = null;
                  var79 = this.x.iterator();
                  var154 = var79.hasNext();
                  break;
               }

               while (true) {
                  if (var154) {
                     label1139: {
                        Value var86 = (Value)var79.next();
                        Value var156 = var86;
                        if (var4 == null) {
                           var157 = var86.c$Z();
                           if (var4 != null) {
                              break;
                           }

                           if (((var157) ? 1 : 0) == 0) {
                              break label1139;
                           }

                           var156 = var86;
                        }

                        if (var4 == null) {
                           if (var156.J$H() != com.elowen.values.ValueType.FLOAT) {
                              break label1139;
                           }

                           var156 = var86;
                        }

                        FloatValue var94;
                        SmoothAnimationTimer var102;
                        var94 = var156.L();
                        var102 = (SmoothAnimationTimer)this.u.get(var94);
                        var133 = ((var50) != 0);
                        label870:
                        if (var4 == null) {
                           if (var50 != 0) {
                              var133 = com.elowen.utils.MouseUtils.E(var2, var3, y + 140.0F, t + var73 + var57 + 39.5F, BS - 155.0F, 10.0F);
                              if (var4 != null) {
                                 break label870;
                              }

                              if (((var133) ? 1 : 0) != 0) {
                                 this.c = var94;
                              }
                           }

                           this.c(var1, var86.r(), y + 140.0F, t + var73 + var57 + 25.0F, 0.4F, -1);
                           var133 = ((Math.round(var94.o$F() * 100.0F)) != 0);
                        }

                        float var159 = ((var133) ? 1 : 0) / 100.0F;
                        var10001 = var94.h$F();
                        String[] var129 = ab;
                        String var109 = var159 + " / " + var10001;
                        float var115 = this.P(var109, 0.4F);
                        this.c(var1, var109, y + BS - var115 - 15.0F, t + var73 + var57 + 25.0F, 0.4F, -1);
                        float var121 = (var94.o$F() - var94.y$F()) / (var94.h$F() - var94.y$F());
                        int var125 = com.elowen.utils.Colors.z(54, 98, 236, 255);
                        this.v.d(y + 140.0F, t + var73 + var57 + 42.0F, BS - 155.0F, 5.0F, 3.0F, com.elowen.utils.Colors.z(0, 0, 0, 150));
                        var102.J = (BS - 155.0F) * var121;
                        var102.F(true);
                        this.v.d(y + 140.0F, t + var73 + var57 + 42.0F, var102.l, 5.0F, 3.0F, var125);
                        this.v.d(y + 135.0F + var102.l, t + var73 + var57 + 39.5F, 10.0F, 10.0F, 5.0F, com.elowen.utils.Colors.z(255, 255, 255, 255));
                        var73 += 25.0F;
                     }

                     if (var4 == null) {
                        var154 = var79.hasNext();
                        continue;
                     }
                  }

                  this.E = null;
                  var79 = this.x.iterator();
                  var157 = var79.hasNext();
                  break;
               }

               label855: {
                  label854:
                  while (true) {
                     if (((var157) ? 1 : 0) != 0) {
                        label1143: {
                           Value var87 = (Value)var79.next();
                           Value var160 = var87;
                           if (var4 == null) {
                              boolean var161 = var87.c$Z();
                              if (var4 != null) {
                                 if (!var161) {
                                    break label855;
                                 }
                                 break;
                              }

                              if (!var161) {
                                 break label1143;
                              }

                              var160 = var87;
                           }

                           if (var4 == null) {
                              if (var160.J$H() != com.elowen.values.ValueType.MODE) {
                                 break label1143;
                              }

                              var160 = var87;
                           }

                           ModeValue var95 = var160.T$t();
                           SmoothAnimationTimer var103 = (SmoothAnimationTimer)this.u.get(var95);
                           var103.F(true);
                           this.c(var1, var87.r(), y + 140.0F, t + var73 + var57 + 25.0F, 0.4F, -1);
                           var67 = 0.0F;
                           var73 += 15.0F;
                           int var110 = 0;

                           while (var110 < var95.T$ArrString().length) {
                              String var116 = var95.T$ArrString()[var110];
                              float var122 = this.P(var116, 0.4F) + 20.0F;
                              float var192;
                              var157 = (((byte)((var192 = var67 + var122 + 20.0F - (BS - 155.0F)) == 0.0F ? 0 : (var192 < 0.0F ? -1 : 1))) != 0);
                              if (var4 != null) {
                                 continue label854;
                              }

                              if (var4 == null) {
                                 if (((var157) ? 1 : 0) > 0) {
                                    var67 = 0.0F;
                                    var73 += 20.0F;
                                 }

                                 var157 = ((var50) != 0);
                              }

                              byte var176;
                              short var180;
                              byte var181;
                              if (var4 == null) {
                                 if (((var157) ? 1 : 0) != 0) {
                                    var157 = com.elowen.utils.MouseUtils.E(var2, var3, y + 140.0F + var67, t + var73 + var57 + 25.0F, var122, 13.0F);
                                    if (var4 == null) {
                                       if (((var157) ? 1 : 0) != 0) {
                                          this.E = var95;
                                          this.p = var110;
                                       }

                                       var157 = ((54) != 0);
                                    }
                                 } else {
                                    var157 = ((54) != 0);
                                 }

                                 var176 = 98;
                                 var180 = 236;
                                 var181 = (((byte)((var95.t(var116)) ? 1 : 0)));
                                 if (var4 == null) {
                                    var181 = (byte)(var181 != 0 ? (int)var103.l : 10);
                                 }
                              } else {
                                 var176 = 98;
                                 var180 = 236;
                                 var181 = (((byte)((var95.t(var116)) ? 1 : 0)));
                                 if (var4 == null) {
                                    var181 = (byte)(var181 != 0 ? (int)var103.l : 10);
                                 }
                              }

                              int var126 = com.elowen.utils.Colors.z(((var157) ? 1 : 0), var176, var180, var181);
                              this.v.d(y + 140.0F + var67, t + var73 + var57 + 27.0F, 10.0F, 10.0F, 5.0F, com.elowen.utils.Colors.z(0, 0, 0, 150));
                              this.v.d(y + 141.0F + var67, t + var73 + var57 + 28.0F, 8.0F, 8.0F, 5.0F, var126);
                              this.c(var1, var116, y + 152.0F + var67, t + var73 + var57 + 25.0F, 0.4F, -1);
                              var67 += var122;
                              var110++;
                              if (var4 != null) {
                                 var73 += 20.0F;
                                 break label1143;
                              }
                           }

                           var73 += 20.0F;
                        }

                        if (var4 == null) {
                           var157 = var79.hasNext();
                           continue;
                        }

                        this.Bj = null;
                        var79 = this.x.iterator();
                     } else {
                        this.Bj = null;
                        var79 = this.x.iterator();
                     }

                     if (!var79.hasNext()) {
                        break label855;
                     }
                     break;
                  }

                  while (true) {
                     Value var88 = (Value)var79.next();
                     Value var162 = var88;
                     if (var4 == null) {
                        var146 = ((var88.c$Z()) ? 1 : 0);
                        if (var4 != null) {
                           break label1123;
                        }

                        if (var146 == 0) {
                           if (!var79.hasNext()) {
                              break;
                           }
                           continue;
                        }

                        var162 = var88;
                     }

                     if (var4 == null) {
                        if (var162.J$H() != com.elowen.values.ValueType.STRING) {
                           if (var4 == null) {
                              if (!var79.hasNext()) {
                                 break;
                              }
                              continue;
                           }

                           var162 = var88;
                        } else {
                           var162 = var88;
                        }
                     }

                     StringValue var96 = var162.N();
                     String var104 = var4 == null ? (var96.R$String() == null ? "" : var96.R$String()) : var96.R$String();
                     int var111 = var96 == this.n ? 1 : 0;
                     float var117 = y + 140.0F;
                     float var123 = t + var73 + var57 + 40.0F;
                     float var127 = BS - 155.0F;
                     float var128 = var117 + 4.0F;
                     float var25 = com.elowen.utils.renderer.SkijaFonts.g(com.elowen.utils.renderer.Fonts.i(9.3991995F));
                     float var26 = var123 + (14.0F - var25) / 2.0F;
                     if (var4 == null) {
                        if (var50 != 0 && com.elowen.utils.MouseUtils.E(var2, var3, var117, var123, var127, 14.0F)) {
                           this.Bj = var96;
                        }

                        this.c(var1, var88.r(), y + 140.0F, t + var73 + var57 + 25.0F, 0.4F, -1);
                     }

                     SmoothAnimationTimer var27 = (SmoothAnimationTimer)this.G.get(var96);
                     SmoothAnimationTimer var163 = var27;
                     if (var4 == null) {
                        if (var27 != null) {
                           var27.J = var111 != 0 ? 255.0F : 0.0F;
                           var27.F(true);
                        }

                        var163 = var27;
                     }

                     float var28 = var4 == null
                        ? (var163 == null ? (var111 != 0 ? 1.0F : 0.0F) : (float)com.elowen.utils.MathUtils.Z(var27.l / 255.0F, 0.0, 1.0))
                        : (float)com.elowen.utils.MathUtils.Z(var163.l / 255.0F, 0.0, 1.0);
                     int var29 = Math.round(150.0F + 40.0F * var28);
                     this.v.d(var117, var123, var127, 14.0F, 3.0F, com.elowen.utils.Colors.z(0, 0, 0, var29));
                     var133 = ((var111) != 0);
                     if (var4 == null) {
                        var133 = ((var111 != 0 ? Math.max(0, Math.min(this.C, var104.length())) : var104.length()) != 0);
                     }

                     int var30 = ((var133) ? 1 : 0);
                     int[] var31 = this.X(var104, var30, var127 - 8.0F);
                     this.c(var1, var104.substring(var31[0], var31[1]), var128, var26, 0.4F, -1);
                     float var32 = var128 + this.P(var104.substring(var31[0], var30), 0.4F);
                     var133 = ((var111) != 0);
                     if (var4 == null) {
                        if (var111 != 0) {
                           I.textInputManager().setTextInputArea(Math.round(var32), Math.round(var26), Math.round(var32 + 1.0F), Math.round(var26 + var25));
                        }

                        var133 = ((var111) != 0);
                     }

                     if (var4 == null) {
                        if (((var133) ? 1 : 0) == 0) {
                           var73 += 32.0F;
                           if (var4 != null || !var79.hasNext()) {
                              break;
                           }
                           continue;
                        }

                        long var193;
                        var133 = (((var193 = System.currentTimeMillis() / 500L % 2L - 0L) == 0L ? 0 : (var193 < 0L ? -1 : 1)) != 0);
                     }

                     if (((var133) ? 1 : 0) == 0) {
                        float var33 = var25 * 0.6F;
                        this.v.d(var32, var26 + (var25 - var33) / 2.0F, 0.5F, var33, 0.0F, com.elowen.utils.Colors.z(255, 255, 255, 255));
                     }

                     var73 += 32.0F;
                     if (var4 != null || !var79.hasNext()) {
                        break;
                     }
                  }
               }

               label738: {
                  label737: {
                     label1150: {
                        this.B3 = var73 - B + 25.0F;
                        this.BR -= var73;
                        var80 = ((int)(this.B3 + B));
                        float var194;
                        var133 = (((var194 = var80 - (B - 25.0F)) == 0.0F ? 0 : (var194 < 0.0F ? -1 : 1)) != 0);
                        if (var4 == null) {
                           if (((var133) ? 1 : 0) <= 0) {
                              break label738;
                           }

                           this.Bg.F(true);
                           var167 = this;
                           if (var4 != null) {
                              break label1150;
                           }

                           var133 = this.F.e(1000.0);
                        }

                        if (((var133) ? 1 : 0) != 0) {
                           this.Bg.J = 0.0F;
                           if (var4 == null) {
                              break label737;
                           }
                        }

                        var167 = this;
                     }

                     var167.Bg.J = 255.0F;
                  }

                  float var89 = B - 25.0F;
                  float var97 = (float)com.elowen.utils.MathUtils.Z(-this.Bw.l / -this.BR, 0.0, 1.0);
                  float var105 = var89 / var80 * var89;
                  float var112 = Math.max(var105, 20.0F);
                  float var118 = var97 * (var89 - var112);
                  int var124 = com.elowen.utils.renderer.RenderUtils.f(3630060, this.Bg.l / 255.0F);
                  this.v.d(y + BS - 8.0F, t + 20.0F + var118, 3.0F, var112, 1.5F, var124);
               }

               var1.restore();
            }

            var150 = this;
            if (var4 == null) {
               if (this.S != null) {
                  float var51 = (var2 - y - 140.0F) / (BS - 160.0F);
                  float var58 = this.S.y$F() + (this.S.h$F() - this.S.y$F()) * var51;
                  float var195;
                  var133 = (((var195 = var58 - this.S.y$F()) == 0.0F ? 0 : (var195 < 0.0F ? -1 : 1)) != 0);
                  if (var4 == null) {
                     if (((var133) ? 1 : 0) < 0) {
                        var58 = this.S.y$F();
                     }

                     float var196;
                     var133 = (((var196 = var58 - this.S.h$F()) == 0.0F ? 0 : (var196 < 0.0F ? -1 : 1)) != 0);
                  }

                  if (var4 == null) {
                     if (((var133) ? 1 : 0) > 0) {
                        var58 = this.S.h$F();
                     }

                     var133 = ((Math.round(var58 / this.S.e())) != 0);
                  }

                  var58 = ((var133) ? 1 : 0) * this.S.e();
                  this.S.I(var58);
               }

               var150 = this;
            }

            if (var4 != null) {
               break label1122;
            }

            var146 = ((var150.T) ? 1 : 0);
         }

         if (var146 != 0) {
            var150 = this;
            if (var4 != null) {
               break label1122;
            }

            if (this.P) {
               y = y + (var2 - this.o[0]);
               t = t + (var3 - this.o[1]);
               this.F(var2, var3);
            }
         }

         var150 = this;
      }

      label680:
      if (var4 == null) {
         if (var150.k != null) {
            var150 = this;
            if (var4 != null) {
               break label680;
            }

            if (!this.d) {
               var150 = this;
               if (var4 != null) {
                  break label680;
               }

               if (this.B_) {
                  var150 = this;
                  if (var4 != null) {
                     break label680;
                  }

                  if (this.P) {
                     label670: {
                        label1153: {
                           BS = BS + (var2 - this.o[0]);
                           B = B + (var3 - this.o[1]);
                           float var197;
                           var133 = (((var197 = BS - 500.0F) == 0.0F ? 0 : (var197 < 0.0F ? -1 : 1)) != 0);
                           if (var4 == null) {
                              if (((var133) ? 1 : 0) < 0) {
                                 BS = 500.0F;
                              }

                              var171 = B;
                              if (var4 != null) {
                                 break label1153;
                              }

                              float var198;
                              var133 = (((var198 = B - 300.0F) == 0.0F ? 0 : (var198 < 0.0F ? -1 : 1)) != 0);
                           }

                           if (((var133) ? 1 : 0) >= 0) {
                              break label670;
                           }

                           var171 = 300.0F;
                        }

                        B = var171;
                     }

                     this.F(var2, var3);
                  }
               }
            }
         }

         var150 = this;
      }

      label655: {
         if (var4 == null) {
            if (var150.a != null) {
               this.h.J = 250.0F;
               this.BC = this.a.i();
               if (var4 == null) {
                  break label655;
               }
            }

            var150 = this;
         }

         var150.h.J = 5.0F;
      }

      this.h.F(true);
      ClickGUI var172 = this;
      if (var4 == null) {
         if (this.O != null) {
            float var53 = 9.6F;
            Typeface var62 = com.elowen.utils.renderer.Fonts.n(var53).getTypeface();
            float var69 = com.elowen.utils.renderer.Fonts.n(var53).measureTextWidth(com.elowen.utils.FontIcons.b);
            float var74 = y + this.X.l - var69;
            float var81 = t + this.f.l - 10.0F;
            float var90 = 0.5F;
            int var98 = (int)(var90 * 255.0F) << 24 | 16777215;
            this.G(var1, com.elowen.utils.FontIcons.b, var74, var81, var62, var53, var98);
         }

         var172 = this;
      }

      float var173 = var172.h.l;
      var10001 = 6.0F;
      if (var4 == null) {
         if (!(var172.h.l > 6.0F)) {
            return;
         }

         this.v.d(y, t, this.X.l, this.f.l, 5.0F, com.elowen.utils.Colors.z(0, 0, 0, (int)(this.h.l / 2.0F)));
         var173 = this.h.l;
         var10001 = 255.0F;
      }

      float var54 = var173 / var10001;
      int var63 = (int)(var54 * 255.0F) << 24 | 16777215;
      String[] var130 = ab;
      String var70 = "Press a key to bind " + this.BC;
      String var75 = "(Press ESC to remove/cancel key bind)";
      float var82 = 14.0988F;
      float var91 = 9.3991995F;
      float var99 = com.elowen.utils.renderer.Fonts.i(var82).getMetrics().getHeight();
      float var106 = com.elowen.utils.renderer.Fonts.i(var91).getMetrics().getHeight();
      float var113 = var99 + var106 + 5.0F;
      float var119 = t + (this.f.l - var113) / 2.0F;
      this.c(var1, var70, y + this.X.l / 2.0F - this.P(var70, 0.6F) / 2.0F, var119, 0.6F, var63);
      this.c(var1, var75, y + this.X.l / 2.0F - this.P(var75, 0.4F) / 2.0F, var119 + var99 + 5.0F, 0.4F, var63);
   }

   public void K(double var1, double var3) {
      this.o[0] = (int)var1;
      this.o[1] = (int)var3;
   }

   public void F(int var1, int var2) {
      this.o[0] = var1;
      this.o[1] = var2;
   }

   private static void deobfLambda$init$0(Value var0, SmoothAnimationTimer var1) {
      String var2 = I();
      if (var0.J$H() == com.elowen.values.ValueType.MODE) {
         var1.l = 0.0F;
         var1.J = 255.0F;
      }
   }

   static {
      z(null);
      I = Minecraft.getInstance();
      y = 100.0F;
      t = 100.0F;
      BS = 400.0F;
      B = 250.0F;
   }

   public static void z(String var0) {
      Bv = var0;
   }

   public static String I() {
      return Bv;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
