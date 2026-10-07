package com.elowen.modules.impl.move;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMouseClick;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Typeface;
import java.awt.Color;
import net.minecraft.util.Mth;

@ModuleInfo(R = "Timer", M = Category.MOVEMENT, a = "Timer module with mouse trigger and cooldown")
public class Timer extends Module {
   public FloatValue D;
   public FloatValue B;
   public ModeValue Q;
   private static final int M;
   private static final int l;
   private static final int o;
   private static final int q = 16777215;
   private boolean v;
   private int x;
   private long j;
   private static final long r = 10000L;
   private final SmoothAnimationTimer T;
   private final SkijaRenderer S;
   private static final String[] b = new String[]{"Cooldown: %.1fs", "Timer Ticks", "Cooldown: %.1fs", "%.1fx | %d/%d", "Ready", "Paused", "Trigger Button", "Button 5", "Timer: %.1fx | %d/%d ticks", "Middle", "Button 4", "Middle", "Button 5", "Right", "[Timer] HUD render failed: ", "Ready - Click to activate", "Button 4", "Left", "Paused (LongJump active)", "Right", "Button 3", "Speed", "Left", "Button 3"};
   public Timer() {
      String[] var1 = b;
      this.D = ValueBuilder.m(this, "Speed").d(2.0F).w(0.1F).M(10.0F).V(0.1F).f$K().L();
      this.B = ValueBuilder.m(this, "Timer Ticks").d(20.0F).w(1.0F).M(200.0F).V(1.0F).f$K().L();
      this.Q = ValueBuilder.m(this, "Trigger Button").W(new String[]{"Button 4", "Button 3", "Button 5", "Left", "Right", "Middle"}).m(0).f$K().T$t();
      this.v = false;
      this.x = 0;
      this.j = 0L;
      this.T = new SmoothAnimationTimer(0.0F);
      this.S = SkiaRenderManager.X$m();
   }

   private boolean t() {
      boolean var10000 = Scaffold.k();
      Module var2 = Elowen.S$Elowen().q$ModuleManager().A(LongJump.class);
      boolean var1 = var10000;
      Module var3 = var2;
      if (!var1) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      var10000 = var3.w();
      return var1 ? var10000 : var10000;
   }

   @Override
   public void h$V() {
      this.b$V();
   }

   @Override
   public void q$V() {
      this.b$V();
      Elowen.i = 1.0F;
   }

   private void b$V() {
      this.v = false;
      this.x = 0;
      Elowen.i = 1.0F;
      this.T.l = 0.0F;
      this.T.J = 0.0F;
   }

   private boolean s$Z() {
      boolean var1 = Scaffold.S$Z();
      return System.currentTimeMillis() - this.j >= 10000L;
   }

   private int S$I() {
      boolean var1;
      int var5;
      boolean var10000 = Scaffold.k();
      String var2 = this.Q.C();
      var1 = var10000;
      String var3 = var2;
      byte var4 = -1;
      var5 = var3.hashCode();
      label98:
      if (!var1) {
         switch (var5) {
            case -1043363098:
               var5 = ((var3.equals("Button 4")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 0;
               if (!var1) {
                  break;
               }
            case -1043363099:
               var5 = ((var3.equals("Button 3")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 1;
               if (!var1) {
                  break;
               }
            case -1043363097:
               var5 = ((var3.equals("Button 5")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 2;
               if (!var1) {
                  break;
               }
            case 2364455:
               var5 = ((var3.equals("Left")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 3;
               if (!var1) {
                  break;
               }
            case 78959100:
               var5 = ((var3.equals("Right")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 4;
               if (!var1) {
                  break;
               }
            case -1990474315:
               var5 = ((var3.equals("Middle")) ? 1 : 0);
               if (var1) {
                  break label98;
               }

               if (var5 != 0) {
                  var4 = 5;
               }
         }

         var5 = var4;
      }

      if (!var1) {
         switch (var5) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 5;
            case 3:
               return 0;
            case 4:
               return 1;
            case 5:
               return 2;
            default:
               var5 = 4;
         }
      }

      return var5;
   }

   @EventTarget
   public void N(EventMouseClick var1) {
      Timer var6 = null;
      boolean var2 = Scaffold.k();
      int var10000 = ((this.t()) ? 1 : 0);
      if (!var2) {
         if (var10000 != 0) {
            return;
         }

         var10000 = ((var1.Z()) ? 1 : 0);
      }

      if (!var2) {
         if (var10000 == 0) {
            return;
         }

         var10000 = var1.v$I();
      }

      int var3 = var10000;
      int var4 = this.S$I();
      var10000 = var3;
      if (!var2) {
         if (var3 != var4) {
            return;
         }

         var10000 = ((this.s$Z()) ? 1 : 0);
      }

      label63: {
         if (!var2) {
            if (var10000 == 0) {
               return;
            }

            var6 = this;
            if (var2) {
               break label63;
            }

            var10000 = ((this.v) ? 1 : 0);
         }

         if (var10000 != 0) {
            return;
         }

         this.v = true;
         var6 = this;
      }

      var6.x = 0;
   }

   @EventTarget
   public void W(EventTick var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1.s$f() == EventType.PRE) {
         if (this.t()) {
            if (this.v) {
               this.v = false;
               this.x = 0;
            }

            Elowen.i = 1.0F;
            this.T.J = 0.0F;
         } else {
            if (this.v) {
               this.x++;
               Elowen.i = this.D.o$F();
               float var3 = this.x / this.B.o$F() * 100.0F;
               this.T.J = Mth.clamp(var3, 0.0F, 100.0F);
               if (this.x >= this.B.o$F()) {
                  this.v = false;
                  this.x = 0;
                  this.j = System.currentTimeMillis();
                  Elowen.i = 1.0F;
               }
            }

            Elowen.i = 1.0F;
            long var6 = 10000L - (System.currentTimeMillis() - this.j);
            if (var6 > 0L) {
               float var5 = (float)(10000L - var6) / 10000.0F * 100.0F;
               this.T.J = Mth.clamp(var5, 0.0F, 100.0F);
            }

            this.T.J = 0.0F;
         }
      }
   }

   @EventTarget
   public void c(EventRender2D var1) {
      Timer var3;
      label39: {
         boolean var2 = Scaffold.k();
         int var10000 = ((this.w()) ? 1 : 0);
         if (!var2) {
            if (var10000 == 0) {
               return;
            }

            this.T.F(true);
            var3 = this;
            if (var2) {
               break label39;
            }

            float var4;
            var10000 = (byte)((var4 = this.T.l - 0.0F) == 0.0F ? 0 : (var4 < 0.0F ? -1 : 1));
         }

         if (var10000 <= 0) {
            var3 = this;
            if (var2) {
               break label39;
            }

            if (!(this.T.J > 0.0F)) {
               return;
            }
         }

         var3 = this;
      }

      var3.m();
   }

   private void m() {
      int var2 = G.getWindow().getGuiScaledWidth();
      int var10000 = ((Scaffold.k()) ? 1 : 0);
      int var3 = G.getWindow().getGuiScaledHeight();
      short var4 = 150;
      byte var5 = 6;
      int var6 = (var2 - var4) / 2;
      int var7 = var3 - 200;
      boolean var1 = (boolean)((var10000) != 0);
      var10000 = ((this.v) ? 1 : 0);
      if (!var1) {
         var10000 = this.v ? M : l;
      }

      int var8 = var10000;
      float var9 = this.T.l / 100.0F * var4;
      String var10 = this.g$String();

      try {
         Canvas var11 = this.S.G$Canvas();
         if (!var1) {
            if (var11 == null) {
               return;
            }

            this.S.d(var6, var7, var4, var5, 3.0F, o);
         }

         float var18 = var9;
         if (!var1) {
            if (var9 > 0.0F) {
               this.S.d(var6, var7, var9, var5, 3.0F, var8);
            }

            var18 = 11.0F;
         }

         float var12 = var18;
         Typeface var13 = com.elowen.utils.renderer.Fonts.i(var12).getTypeface();
         float var14 = this.S.W(var10, var13, var12);
         float var15 = var6 + (var4 - var14) / 2.0F;
         this.S.Y(var10, var15, var7 + var5 + 5, var13, var12, 16777215);
         this.S.p();
         this.S.G$V();
      } catch (RuntimeException var16) {
         System.err.println("[Timer] HUD render failed: " + var16);
      }
   }

   private String g$String() {
      boolean var1 = Scaffold.S$Z();
      if (this.t()) {
         return "Paused (LongJump active)";
      }

      if (this.v) {
         return String.format("Timer: %.1fx | %d/%d ticks", this.D.o$F(), this.x, (int)this.B.o$F());
      }

      long var2 = 10000L - (System.currentTimeMillis() - this.j);
      return var2 > 0L ? String.format("Cooldown: %.1fs", var2 / 1000.0) : "Ready - Click to activate";
   }

   @Override
   public String W$String() {
      boolean var1 = Scaffold.S$Z();
      if (this.t()) {
         return "Paused";
      }

      if (this.v) {
         return String.format("%.1fx | %d/%d", this.D.o$F(), this.x, (int)this.B.o$F());
      }

      long var2 = 10000L - (System.currentTimeMillis() - this.j);
      return var2 > 0L ? String.format("Cooldown: %.1fs", var2 / 1000.0) : "Ready";
   }

   static {
      M = new Color(76, 175, 80, 255).getRGB();
      l = new Color(255, 87, 34, 255).getRGB();
      o = new Color(0, 0, 0, 120).getRGB();
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
