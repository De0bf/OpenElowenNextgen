package com.elowen.modules.impl.combat;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventAttack;
import com.elowen.events.impl.EventSprint;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.critical.Test;
import com.elowen.modules.impl.combat.critical.CriticalMode;
import com.elowen.modules.impl.combat.critical.DoubleDamage;
import com.elowen.modules.impl.combat.critical.Vanilla;
import com.elowen.modules.impl.combat.critical.Normal;
import com.elowen.utils.ChatUtils;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "Critical", a = "Critical Hit Target", M = Category.COMBAT)
public class Critical extends Module {
   public final ModeValue c;
   public final FloatValue J;
   public final BooleanValue d;
   public final BooleanValue I;
   public final FloatValue C;
   public final BooleanValue f;
   public final BooleanValue X;
   public final BooleanValue E;
   public final FloatValue b;
   public final FloatValue K;
   public final FloatValue h;
   private final Normal e;
   private final DoubleDamage o;
   private final Test y;
   private final Vanilla M;
   public CriticalMode U;
   private static final String[] i = new String[]{"Mode", "Logging", "Critical Range", "SkipTicks", "Normal", "Only KillAura", "Detection Range (blocks)", "Double Damage", "Vanilla", "Normal", "Double Damage", "Test", "With stop", "Vanilla", "Display Interval (ticks)", "Test", "Hurt Time", "Auto Jump", "Target Ticks"};
   public Critical() {
      String[] var1 = i;
      this.c = ValueBuilder.m(this, "Mode").m(0).W(new String[]{"Normal", "Double Damage", "Test", "Vanilla"}).f$K().T$t();
      this.J = ValueBuilder.m(this, "Critical Range").d(3.0F).V(0.1F).w(1.0F).M(3.2F).l(this::deobfLambda$new$0).f$K().L();
      this.d = ValueBuilder.m(this, "Auto Jump").h(true).l(this::deobfLambda$new$1).f$K().f$O();
      this.I = ValueBuilder.m(this, "SkipTicks").h(true).l(this::deobfLambda$new$2).f$K().f$O();
      this.C = ValueBuilder.m(this, "Target Ticks").d(2.0F).w(0.1F).M(3.0F).V(1.0F).l(this::deobfLambda$new$3).f$K().L();
      this.f = ValueBuilder.m(this, "Logging").h(true).l(this::deobfLambda$new$4).f$K().f$O();
      this.X = ValueBuilder.m(this, "With stop").h(false).l(this::deobfLambda$new$5).f$K().f$O();
      this.E = ValueBuilder.m(this, "Only KillAura").h(false).l(this::deobfLambda$new$6).f$K().f$O();
      this.b = ValueBuilder.m(this, "Display Interval (ticks)").d(10.0F).w(1.0F).M(20.0F).V(1.0F).l(this::deobfLambda$new$7).f$K().L();
      this.K = ValueBuilder.m(this, "Detection Range (blocks)").d(3.2F).w(1.0F).M(5.0F).V(0.01F).l(this::deobfLambda$new$8).f$K().L();
      this.h = ValueBuilder.m(this, "Hurt Time").d(2.0F).w(0.0F).M(10.0F).V(1.0F).l(this::deobfLambda$new$9).f$K().L();
      this.e = new Normal();
      this.o = new DoubleDamage();
      this.y = new Test();
      this.M = new Vanilla();
      this.e.O(this);
      this.o.O(this);
      this.y.O(this);
      this.M.O(this);
      this.U = this.e;
   }

   private void a$V() {
      boolean var1;
      int var5;
      boolean var10000 = Velocity.o$Z();
      String var3 = this.c.C();
      var1 = var10000;
      byte var4 = -1;
      var5 = var3.hashCode();
      label84:
      if (var1) {
         switch (var5) {
            case -527968610:
               var5 = ((var3.equals("Double Damage")) ? 1 : 0);
               if (!var1) {
                  break label84;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 0;
               if (var1) {
                  break;
               }
            case 2603186:
               var5 = ((var3.equals("Test")) ? 1 : 0);
               if (!var1) {
                  break label84;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 1;
               if (var1) {
                  break;
               }
            case 1897755483:
               var5 = ((var3.equals("Vanilla")) ? 1 : 0);
               if (!var1) {
                  break label84;
               }

               if (var5 == 0) {
                  break;
               }

               var4 = 2;
               if (var1) {
                  break;
               }
            case -1955878649:
               var5 = ((var3.equals("Normal")) ? 1 : 0);
               if (!var1) {
                  break label84;
               }

               if (var5 != 0) {
                  var4 = 3;
               }
         }

         var5 = var4;
      }

      CriticalMode var2;
      switch (var5) {
         case 0:
            var2 = this.o;
            if (var1) {
               break;
            }
         case 1:
            var2 = this.y;
            if (var1) {
               break;
            }
         case 2:
            var2 = this.M;
            if (var1) {
               break;
            }
         case 3:
         default:
            var2 = this.e;
      }

      CriticalMode var6 = this.U;
      if (var1) {
         if (this.U == var2) {
            return;
         }

         this.U.w();
         this.U = var2;
         var6 = this.U;
      }

      var6.F();
   }

   @Override
   public void h$V() {
      this.a$V();
      this.U.F();
   }

   @Override
   public void q$V() {
      this.U.w();
   }

   @EventTarget
   public void r(com.elowen.events.impl.EventRespawn var1) {
      boolean var2 = Velocity.o$Z();
      if (this.w()) {
         this.a$V();
         this.U.x();
      }
   }

   @EventTarget(3)
   public void p(EventMoveInput var1) {
      boolean var2 = Velocity.p();
      if (this.w()) {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var10000 = G;
         }

         if (var10000.level != null) {
            this.a$V();
            this.U.m(var1);
            return;
         }
      }
   }

   @EventTarget(3)
   public void Z(EventMoveInput var1) {
      boolean var2 = Velocity.o$Z();
      if (this.w() && G.player != null && G.level != null) {
         this.a$V();
         this.U.G(var1);
      }
   }

   @EventTarget(3)
   public void Y(EventSprint var1) {
      boolean var2 = Velocity.o$Z();
      if (this.w() && G.player != null && G.level != null) {
         this.a$V();
         this.U.P(var1);
      }
   }

   @EventTarget(1)
   public void L(EventMotion var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE) {
         if (this.w() && G.player != null && G.level != null) {
            this.a$V();
            this.U.K(var1);
         }
      }
   }

   @EventTarget
   public void z(EventAttack var1) {
      boolean var2 = Velocity.o$Z();
      if (this.w() && G.player != null && G.level != null) {
         this.a$V();
         this.U.T(var1);
      }
   }

   @EventTarget
   public void d(EventTick var1) {
      this.a$V();
      this.U.f(var1);
   }

   public Minecraft d$Minecraft() {
      return G;
   }

   public boolean T(Entity var1) {
      boolean var4 = false;
      boolean var2 = Velocity.p();
      Object var10000 = G.player;
      if (!var2) {
         if (G.player == null) {
            return true;
         }

         var10000 = var1;
      }

      var10000 = var10000 instanceof LivingEntity;
      if (!var2) {
         if (var4) {
            LivingEntity var3 = (LivingEntity)var1;
            if (!var2) {
               boolean var5 = G.player.onClimbable();
               if (!var2) {
                  if (!var5) {
                     boolean var7 = G.player.isInWater();
                     if (var2) {
                        return var7;
                     }

                     if (!var7) {
                        boolean var8 = G.player.isInLava();
                        if (var2) {
                           return var8;
                        }

                        if (!var8) {
                           boolean var9 = G.player.isPassenger();
                           if (var2) {
                              return var9;
                           }

                           if (!var9) {
                              boolean var10 = this.q$Z();
                              if (var2) {
                                 return var10;
                              }

                              if (!var10) {
                                 if (var2) {
                                    return (boolean)((var3.hurtTime) != 0);
                                 }

                                 if (var3.hurtTime <= 10) {
                                    float var6;
                                    int var11 = (var6 = var3.getHealth() - 0.0F) == 0.0F ? 0 : (var6 < 0.0F ? -1 : 1);
                                    if (var2) {
                                       return (boolean)((var11) != 0);
                                    }

                                    if (var11 > 0) {
                                       return false;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  var5 = true;
               }

               return var5;
            }
         }

         var4 = true;
      }

      return var4;
   }

   public boolean q$Z() {
      boolean var1 = Velocity.p();
      Minecraft var10000 = G;
      if (!var1) {
         if (G.player == null) {
            return false;
         }

         var10000 = G;
      }

      if (!var1) {
         if (var10000.level == null) {
            return false;
         }

         var10000 = G;
      }

      Vec3 var2 = var10000.player.getEyePosition();
      Vec3 var3 = var2.add(0.0, 0.5, 0.0);
      BlockHitResult var4 = G.level.clip(new ClipContext(var2, var3, Block.COLLIDER, Fluid.NONE, G.player));
      return var4.getType() == Type.BLOCK;
   }

   public void e() {
      KeyMapping var3 = null;
      boolean var1 = Velocity.p();
      Minecraft var10000 = G;
      if (!var1) {
         if (G.player == null) {
            return;
         }

         var10000 = G;
      }

      if (!var1) {
         if (var10000.options == null) {
            return;
         }

         var10000 = G;
      }

      LocalPlayer var2 = var10000.player;
      if (!var1) {
         label38: {
            if (!var10000.player.isSprinting()) {
               var3 = G.options.keySprint;
               if (var1) {
                  break label38;
               }

               if (!G.options.keySprint.isDown()) {
                  return;
               }
            }

            var3 = G.options.keySprint;
         }

         var3.setDown(false);
         var2 = G.player;
      }

      var2.setSprinting(false);
   }

   public void L(String var1) {
      com.elowen.utils.ChatUtils.b(var1);
   }

   public Entity u$Entity() {
      boolean var10000 = Velocity.p();
      Aura var2 = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
      boolean var1 = var10000;
      Aura var3 = var2;
      if (!var1) {
         if (var2 == null) {
            return null;
         }

         var3 = var2;
      }

      return var3.w() ? Aura.cj : null;
   }

   public boolean y$Z() {
      boolean var1 = Velocity.p();
      Minecraft var10000 = G;
      if (!var1) {
         if (G.player == null) {
            return true;
         }

         var10000 = G;
      }

      return var10000.level == null;
   }

   private Boolean deobfLambda$new$9() {
      return this.c.C().equals("Vanilla");
   }

   private Boolean deobfLambda$new$8() {
      return this.c.C().equals("Vanilla");
   }

   private Boolean deobfLambda$new$7() {
      boolean var1 = Velocity.p();
      boolean var10000 = this.c.C().equals("Vanilla");
      if (!var1) {
         if (!var10000) {
            return false;
         }

         var10000 = this.f.w();
      }

      if (var1) {
         return var10000;
      } else {
         return var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$6() {
      return this.c.C().equals("Vanilla");
   }

   private Boolean deobfLambda$new$5() {
      return this.c.C().equals("Vanilla");
   }

   private Boolean deobfLambda$new$4() {
      return this.c.C().equals("Vanilla");
   }

   private Boolean deobfLambda$new$3() {
      return this.c.C().equals("Double Damage");
   }

   private Boolean deobfLambda$new$2() {
      boolean var1 = Velocity.p();
      boolean var10000 = this.c.C().equals("Vanilla");
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$1() {
      boolean var1 = Velocity.p();
      boolean var10000 = this.c.C().equals("Vanilla");
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$0() {
      boolean var1 = Velocity.p();
      boolean var10000 = this.c.C().equals("Vanilla");
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
