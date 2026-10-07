package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.move.stuck.StuckMode;
import com.elowen.modules.impl.move.stuck.ElytraPacket;
import com.elowen.modules.impl.move.stuck.Normal;
import com.elowen.modules.impl.move.stuck.Delay;
import com.elowen.modules.impl.move.stuck.SkipTicks;
import com.elowen.modules.impl.move.stuck.PosPacket;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.ModeValue;
import net.minecraft.client.Minecraft;

@ModuleInfo(R = "Stuck", a = "Stuck in air!", M = Category.MOVEMENT)
public class Stuck extends Module {
   private final ModeValue m;
   private StuckMode l;
   private final Normal Z;
   private final Delay f;
   private final PosPacket Y;
   private final SkipTicks C;
   private final com.elowen.modules.impl.move.stuck.Freeze U;
   private final ElytraPacket b;
   private final com.elowen.modules.impl.move.stuck.Mix F;
   private static final String[] c = new String[]{"Delay", "Mix", "Pos Packet", "Skip Ticks", "Freeze", "Normal", "Pos Packet", "Elytra Packet", "Normal", "Delay", "Mix", "Elytra Packet", "Skip Ticks", "Mode", "Freeze"};
   public Stuck() {
      String[] var1 = c;
      this.m = ValueBuilder.m(this, "Mode").W(new String[]{"Normal", "Delay", "Skip Ticks", "Freeze", "Pos Packet", "Elytra Packet", "Mix"}).m(0).f$K().T$t();
      this.Z = new Normal();
      this.f = new Delay();
      this.Y = new PosPacket();
      this.C = new SkipTicks();
      this.U = new com.elowen.modules.impl.move.stuck.Freeze();
      this.b = new ElytraPacket();
      this.F = new com.elowen.modules.impl.move.stuck.Mix();
      this.Z.k(this);
      this.f.k(this);
      this.Y.k(this);
      this.C.k(this);
      this.U.k(this);
      this.b.k(this);
      this.F.k(this);
      this.l = this.Z;
   }

   public ModeValue S$t() {
      return this.m;
   }

   public Minecraft U$Minecraft() {
      return G;
   }

   private void v$V() {
      boolean var1;
      StuckMode var3;
      label105: {
         boolean var10000 = Scaffold.S$Z();
         String var2 = this.m.C();
         var1 = var10000;
         var10000 = "Normal".equals(var2);
         if (var1) {
            if (var10000) {
               var3 = this.Z;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Delay".equals(var2);
         }

         if (var1) {
            if (var10000) {
               var3 = this.f;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Pos Packet".equals(var2);
         }

         if (var1) {
            if (var10000) {
               var3 = this.Y;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Skip Ticks".equals(var2);
         }

         if (var1) {
            if (var10000) {
               var3 = this.C;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Freeze".equals(var2);
         }

         if (var1) {
            if (var10000) {
               var3 = this.U;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Elytra Packet".equals(var2);
         }

         if (var1) {
            if (var10000) {
               var3 = this.b;
               if (var1) {
                  break label105;
               }
            }

            var10000 = "Mix".equals(var2);
         }

         if (var10000) {
            var3 = this.F;
            if (var1) {
               break label105;
            }
         }

         var3 = this.Z;
      }

      StuckMode var5 = this.l;
      if (var1) {
         if (this.l == var3) {
            return;
         }

         this.l.Y();
         this.l = var3;
         var5 = this.l;
      }

      var5.G$V();
   }

   @Override
   public void h$V() {
      this.v$V();
      this.l.G$V();
   }

   @Override
   public void q$V() {
      this.l.Y();
   }

   @Override
   public void M(boolean var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player == null) {
         super.M(var1);
      } else {
         if (var1) {
            super.M(true);
         }

         if (this.l instanceof Normal var3) {
            if (var3.t()) {
               super.M(false);
            }

            var3.r(true);
         }

         if (this.l instanceof Delay var4) {
            if (var4.H()) {
               super.M(false);
            }

            var4.g(true);
         }

         super.M(false);
      }
   }

   @EventTarget
   public void e(EventTick var1) {
      this.v$V();
      this.l.B(var1);
   }

   @EventTarget
   public void S(com.elowen.events.impl.EventMotion var1) {
      this.v$V();
      this.l.b(var1);
   }

   @EventTarget
   public void X(com.elowen.events.impl.EventMoveInput var1) {
      this.v$V();
      this.l.z(var1);
   }

   @EventTarget
   public void Y(EventRespawn var1) {
      this.v$V();
      this.l.E(var1);
   }

   @EventTarget
   public void r(com.elowen.events.impl.EventPlayerTick var1) {
      this.v$V();
      this.l.a(var1);
   }

   @EventTarget(1)
   public void W(EventPacket var1) {
      this.v$V();
      this.l.V(var1);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
