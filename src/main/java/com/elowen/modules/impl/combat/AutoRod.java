package com.elowen.modules.impl.combat;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventUpdateHeldItem;
import com.elowen.events.impl.EventSwing;
import com.elowen.events.impl.EventAttack;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.modules.impl.move.AutoMLG;
import com.elowen.modules.impl.move.Blink;
import com.elowen.modules.impl.move.LongJump;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.FriendManager;
import com.elowen.utils.TimeHelper;
import com.elowen.utils.Vector2f;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.BlinkingPlayer;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.utils.rotation.Rotation;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "AutoRod", a = "Automatically use fishing rod on enemies.", M = Category.COMBAT)
public class AutoRod extends Module {
   private final FloatValue F;
   private final FloatValue P;
   private final FloatValue e;
   private final FloatValue b;
   private final FloatValue T;
   private final FloatValue y;
   private final BooleanValue X;
   private final TimeHelper h;
   public Vector2f f;
   private int B;
   private boolean U;
   private InteractionHand x;
   private long l;
   private boolean I;
   private InteractionHand c;
   private int p;
   private boolean R;
   private static final String[] d = new String[]{"Delay", "FoV", "Max Distance", "Predict", "Rod Timeout", "Min Distance", "Only KillAura"};
   public AutoRod() {
      String[] var1 = d;
      this.F = ValueBuilder.m(this, "Min Distance").d(3.0F).V(0.1F).w(3.0F).M(10.0F).f$K().L();
      this.P = ValueBuilder.m(this, "Max Distance").d(8.0F).V(1.0F).w(3.0F).M(30.0F).f$K().L();
      this.e = ValueBuilder.m(this, "Delay").d(300.0F).V(1.0F).w(50.0F).M(2000.0F).f$K().L();
      this.b = ValueBuilder.m(this, "Predict").d(0.8F).V(0.1F).w(0.0F).M(2.0F).f$K().L();
      this.T = ValueBuilder.m(this, "Rod Timeout").d(500.0F).V(1.0F).w(50.0F).M(5000.0F).f$K().L();
      this.y = ValueBuilder.m(this, "FoV").d(360.0F).V(1.0F).w(10.0F).M(360.0F).f$K().L();
      this.X = ValueBuilder.m(this, "Only KillAura").h(false).f$K().f$O();
      this.h = new TimeHelper();
      this.B = -1;
      this.U = false;
      this.l = 0L;
      this.I = false;
      this.c = null;
      this.p = -1;
      this.R = false;
   }

   private static Module j(Class var0) {
      try {
         return Elowen.S$Elowen().q$ModuleManager().A(var0);
      } catch (RuntimeException var2) {
         return null;
      }
   }

   private boolean Y(AbstractClientPlayer var1) {
      boolean var2 = Velocity.o$Z();
      if (var1 == G.player) {
         return false;
      } else if (Teams.T(var1)) {
         return false;
      } else if (com.elowen.utils.FriendManager.A(var1)) {
         return false;
      } else if (this.c(var1)) {
         return false;
      } else if (this.B$Z(var1)) {
         return false;
      } else if (var1.isSpectator()) {
         return false;
      } else {
         return var1.isDeadOrDying() || var1.getHealth() <= 0.0F ? false : !(var1.getBbWidth() < 0.5) && !var1.isSleeping();
      }
   }

   private boolean H() {
      return this.h(G.player.getMainHandItem());
   }

   private boolean h(ItemStack var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.isEmpty()) {
         return false;
      } else if (var1.has(DataComponents.FOOD)) {
         return true;
      } else if (var1.getItem() == Items.BOW) {
         return true;
      } else if (var1.getItem() == Items.CROSSBOW) {
         return true;
      } else if (var1.getItem() == Items.SNOWBALL) {
         return true;
      } else if (var1.getItem() == Items.EGG) {
         return true;
      } else if (var1.getItem() == Items.ENDER_PEARL) {
         return true;
      } else if (var1.getItem() == Items.SPLASH_POTION) {
         return true;
      } else if (var1.getItem() == Items.LINGERING_POTION) {
         return true;
      } else {
         return var1.getItem() == Items.EXPERIENCE_BOTTLE ? true : var1.getItem() == Items.POTION;
      }
   }

   private boolean J(ItemStack var1) {
      return var1.getItem() == Items.FISHING_ROD;
   }

   private int w$h() {
      int var10000 = ((Velocity.p()) ? 1 : 0);
      int var2 = 0;
      boolean var1 = (boolean)((var10000) != 0);

      while (true) {
         if (var2 < 9) {
            var10000 = ((this.J(G.player.getInventory().getItem(var2))) ? 1 : 0);
            if (var1) {
               break;
            }

            if (var1) {
               return var10000;
            }

            if (var10000 != 0) {
               return var2;
            }

            var2++;
            if (!var1) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   private boolean G(Vector2f var1, double var2) {
      boolean var10000 = Velocity.p();
      HitResult var5 = com.elowen.utils.RayTraceUtils.h(new Rotation(var1.H, var1.E), var2, 0.0F, G.player, null, false);
      boolean var4 = var10000;
      HitResult var6 = var5;
      if (!var4) {
         if (var5 == null) {
            return false;
         }

         var6 = var5;
      }

      return var6.getType() == Type.BLOCK;
   }

   private Optional r() {
      return G.level
         .players()
         .stream()
         .filter(this::Y)
         .filter(this::deobfLambda$getTarget$0)
         .filter(AutoRod::deobfLambda$getTarget$1)
         .filter(AutoRod::deobfLambda$getTarget$2)
         .filter(this::deobfLambda$getTarget$3)
         .min(Comparator.comparingDouble(AutoRod::deobfLambda$getTarget$4));
   }

   private boolean c(Player var1) {
      boolean var10000 = Velocity.o$Z();
      AntiBots var3 = (AntiBots)j(AntiBots.class);
      boolean var2 = var10000;
      if (var3 == null) {
         return false;
      }

      var10000 = AntiBots.g(var1);
      if (var2) {
         if (!var10000) {
            var10000 = AntiBots.O(var1);
            if (!var2) {
               return var10000;
            }

            if (!var10000) {
               return false;
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private boolean B$Z(Entity var1) {
      boolean var2 = Velocity.o$Z();
      if (var1 instanceof BlinkingPlayer) {
         return true;
      } else {
         return var1 instanceof RemotePlayer ? var1.getId() < 0 : false;
      }
   }

   private Vector2f k(Entity var1) {
      if (this.B$Z(var1)) {
         return null;
      }

      double var2 = G.player.distanceTo(var1);
      double var4 = var2 * this.b.o$F();
      double var6 = var1.getX() + (var1.getX() - var1.xOld) * var4;
      double var8 = var1.getY() + (var1.getY() - var1.yOld) * var4;
      double var10 = var1.getZ() + (var1.getZ() - var1.zOld) * var4;
      double var12 = var6 - G.player.getX();
      double var14 = var8 + 1.2 - (G.player.getY() + G.player.getEyeHeight());
      double var16 = var10 - G.player.getZ();
      double var18 = Math.sqrt(var12 * var12 + var16 * var16);
      float var20 = (float)(Math.atan2(var16, var12) * 180.0 / Math.PI) - 90.0F;
      float var21 = -this.H((float)var18, (float)var14, 0.6F, 0.006F);
      var21 = Math.max(-90.0F, Math.min(90.0F, var21));
      return new Vector2f(var20, var21);
   }

   private float H(float var1, float var2, float var3, float var4) {
      boolean var10000 = Velocity.o$Z();
      float var6 = var3 * var3 * var3 * var3 - var4 * (var4 * var1 * var1 + 2.0F * var2 * var3 * var3);
      boolean var5 = var10000;
      float var7 = var6;
      float var10001 = 0.0F;
      if (var5) {
         if (var6 < 0.0F) {
            return 0.0F;
         }

         var7 = var3;
         var10001 = var3;
      }

      return (float)Math.toDegrees(Math.atan((var7 * var10001 - Math.sqrt(var6)) / (var4 * var1)));
   }

   private FishingHook q$FishingHook() {
      net.minecraft.world.entity.projectile.FishingHook var5 = null;
      boolean var1 = Velocity.p();
      ClientLevel var10000 = G.level;
      if (!var1) {
         if (G.level == null) {
            return null;
         }

         var10000 = G.level;
      }

      Iterator var2 = var10000.getEntitiesOfClass(FishingHook.class, G.player.getBoundingBox().inflate(30.0)).iterator();

      while (true) {
         if (var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            FishingHook var4 = (FishingHook)var3;
            var5 = var4;
            if (var1) {
               break;
            }

            if (var4.getPlayerOwner() == G.player) {
               var5 = var4;
               break;
            }

            if (!var1) {
               continue;
            }
         }

         return null;
      }

      return var5;
   }

   private void S$V() {
      boolean var1 = Velocity.p();
      AutoRod var10000 = this;
      if (!var1) {
         label24:
         if (this.B != -1) {
            LocalPlayer var2 = G.player;
            if (!var1) {
               if (G.player == null) {
                  break label24;
               }

               var2 = G.player;
            }

            var2.getInventory().setSelectedSlot(this.B);
         }

         this.U = false;
         var10000 = this;
      }

      var10000.B = -1;
   }

   private void u$V() {
      this.f = null;
      this.S$V();
      this.x = null;
      this.I = false;
      this.l = 0L;
      this.c = null;
      this.h.p();
   }

   private void v$V() {
      this.f = null;
      this.S$V();
      this.x = null;
      this.h.p();
      this.I = false;
      this.l = 0L;
      this.c = null;
   }

   private void R(boolean var1) {
      boolean var10000 = Velocity.o$Z();
      this.f = null;
      this.S$V();
      boolean var2 = var10000;
      AutoRod var3 = this;
      if (var2) {
         this.x = null;
         if (!var1) {
            return;
         }

         var3 = this;
      }

      var3.h.p();
   }

   private void c$V() {
      AutoRod var2;
      label41: {
         label40: {
            boolean var1 = Velocity.p();
            InteractionHand var10000 = this.c;
            InteractionHand var10001 = InteractionHand.MAIN_HAND;
            if (!var1) {
               if (this.c == InteractionHand.MAIN_HAND) {
                  var2 = this;
                  if (var1) {
                     break label41;
                  }

                  if (!this.J(G.player.getMainHandItem())) {
                     break label40;
                  }

                  G.gameMode.useItem(G.player, InteractionHand.MAIN_HAND);
                  if (!var1) {
                     break label40;
                  }
               }

               var2 = this;
               if (var1) {
                  break label41;
               }

               var10000 = this.c;
               var10001 = InteractionHand.OFF_HAND;
            }

            if (var10000 == var10001) {
               G.gameMode.useItem(G.player, InteractionHand.OFF_HAND);
            }
         }

         this.S$V();
         var2 = this;
      }

      var2.u$V();
   }

   @EventTarget
   public void b(EventAttack var1) {
      boolean var2 = Velocity.o$Z();
      if (G.player != null) {
         this.p = G.player.tickCount;
      }

      if (this.I || this.x != null || this.U) {
         this.R = true;
         var1.c(true);
      }
   }

   @EventTarget
   public void p(EventSwing var1) {
      boolean var2 = Velocity.p();
      AutoRod var10000 = this;
      if (!var2) {
         if (!this.R) {
            return;
         }

         var1.c(true);
         var10000 = this;
      }

      var10000.R = false;
   }

   @EventTarget
   public void v(EventUpdate var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.a$f() == com.elowen.events.api.types.EventType.PRE) {
         if (G.player != null && G.level != null) {
            if (G.gui.screen() == null) {
               boolean var3 = G.level.players().stream().filter(this::Y).anyMatch(this::deobfLambda$onUpdate$0);
               if (var3) {
                  if (this.I) {
                     this.c$V();
                  }

                  this.v$V();
               } else {
                  if (this.X.w()) {
                     Aura var4 = (Aura)j(Aura.class);
                     if (var4 == null || !var4.w()) {
                        if (this.I) {
                           this.c$V();
                        }

                        this.v$V();
                        return;
                     }
                  }

                  if (this.H()) {
                     this.v$V();
                  } else {
                     if (G.player.isUsingItem()) {
                        ItemStack var5 = G.player.getUseItem();
                        if (!this.J(var5)) {
                           if (this.I) {
                              this.c$V();
                           }

                           this.R(false);
                           return;
                        }
                     }

                     FishingHook var6 = this.q$FishingHook();
                     if (var6 != null && this.I) {
                        if (var6.getHookedIn() != null || (float)(System.currentTimeMillis() - this.l) >= this.T.o$F()) {
                           this.c$V();
                           return;
                        }
                     } else if (this.I && (float)(System.currentTimeMillis() - this.l) >= this.T.o$F()) {
                        this.u$V();
                        return;
                     }

                     if (!this.I) {
                        if (G.player.isUsingItem() && G.player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                           this.R(false);
                        } else if (this.i$h()) {
                           this.R(false);
                        } else if (this.I()) {
                           this.R(false);
                        } else if (this.J(G.player.getOffhandItem()) && G.player.isUsingItem() && G.player.getUsedItemHand() == InteractionHand.OFF_HAND) {
                           this.R(false);
                        } else {
                           if (this.x == null) {
                              this.k();
                           }

                           this.b$V();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean i$h() {
      boolean var1;
      Stuck var3;
      Blink var4;
      label71: {
         Scaffold var2 = (Scaffold)j(Scaffold.class);
         boolean var10000 = Velocity.o$Z();
         var3 = (Stuck)j(Stuck.class);
         var1 = var10000;
         var4 = (Blink)j(Blink.class);
         Scaffold var5 = var2;
         if (var1) {
            if (var2 == null) {
               break label71;
            }

            var5 = var2;
         }

         var10000 = var5.w();
         if (!var1) {
            return var10000;
         }

         if (var10000) {
            return true;
         }
      }

      label72: {
         Stuck var7 = var3;
         if (var1) {
            if (var3 == null) {
               break label72;
            }

            var7 = var3;
         }

         boolean var9 = var7.w();
         if (!var1) {
            return var9;
         }

         if (var9) {
            return true;
         }
      }

      Blink var8 = var4;
      if (var1) {
         if (var4 == null) {
            return false;
         }

         var8 = var4;
      }

      boolean var10 = var8.w();
      return !var1 ? var10 : var10;
   }

   private boolean I() {
      boolean var1;
      LongJump var3;
      AttackCrystal var4;
      label75: {
         boolean var10000 = Velocity.o$Z();
         AutoMLG var2 = (AutoMLG)j(AutoMLG.class);
         var1 = var10000;
         var3 = (LongJump)j(LongJump.class);
         var4 = (AttackCrystal)j(AttackCrystal.class);
         AutoMLG var5 = var2;
         if (var1) {
            if (var2 == null) {
               break label75;
            }

            var5 = var2;
         }

         if (var1) {
            if (!var5.w()) {
               break label75;
            }

            var5 = var2;
         }

         if (var5.i() != null) {
            return true;
         }
      }

      label59: {
         LongJump var6 = var3;
         if (var1) {
            if (var3 == null) {
               break label59;
            }

            var6 = var3;
         }

         if (var6.w() && LongJump.r != null) {
            return true;
         }
      }

      AttackCrystal var7 = var4;
      if (var1) {
         if (var4 == null) {
            return false;
         }

         var7 = var4;
      }

      return var7.w() && AttackCrystal.D != null;
   }

   private void k() {
      int var10000 = 0;
      boolean var17 = false;
      AutoRod var22 = null;
      AutoRod var20 = null;
      boolean var1;
      AutoRod var13;
      label182: {
         label183: {
            var1 = Velocity.p();
            if (G.player != null) {
               var10000 = this.p;
               if (var1) {
                  break label183;
               }

               if (this.p == G.player.tickCount) {
                  this.R(false);
                  return;
               }
            }

            var13 = this;
            if (var1) {
               break label182;
            }

            var10000 = (this.h.e(this.e.o$F()) ? 1 : 0);
         }

         if (var10000 == 0) {
            this.R(false);
            return;
         }

         var13 = this;
      }

      Optional var2 = var13.r();
      Object var14 = var2;
      if (!var1) {
         if (var2.isEmpty()) {
            this.R(false);
            return;
         }

         var14 = var2.get();
      }

      Entity var3 = (Entity)var14;
      Vector2f var4 = this.k(var3);
      Vector2f var15 = var4;
      if (!var1) {
         if (var4 == null) {
            this.R(false);
            return;
         }

         var15 = var4;
      }

      var15.E = Math.max(-90.0F, Math.min(90.0F, var4.E));
      double var5 = G.player.distanceTo(var3);
      AutoRod var16 = this;
      Vector2f var10001 = var4;
      if (!var1) {
         if (this.G(var4, Math.min(3.0, var5))) {
            this.R(false);
            return;
         }

         var16 = this;
         var10001 = var4;
      }

      int var9;
      label165: {
         label184: {
            var16.f = var10001;
            ItemStack var7 = G.player.getMainHandItem();
            ItemStack var8 = G.player.getOffhandItem();
            var9 = ((this.J(var7)) ? 1 : 0);
            int var10 = ((this.J(var8)) ? 1 : 0);
            var17 = ((var9) != 0);
            if (!var1) {
               if (var9 != 0) {
                  break label184;
               }

               var17 = ((var10) != 0);
            }

            if (var1) {
               break label165;
            }

            if (((var17) ? 1 : 0) != 0) {
               var17 = ((1) != 0);
               break label165;
            }
         }

         var17 = ((0) != 0);
      }

      int var11 = ((var17) ? 1 : 0);
      byte var18 = (byte)var11;
      if (!var1) {
         if (var11 != 0) {
            this.S$V();
            this.x = InteractionHand.OFF_HAND;
            return;
         }

         var18 = (byte)var9;
      }

      if (!var1) {
         var18 = (byte)(var18 != 0 ? G.player.getInventory().getSelectedSlot() : this.w$h());
      }

      byte var12 = var18;
      int var19 = var12;
      if (!var1) {
         if (var12 == -1) {
            this.R(false);
            return;
         }

         var19 = ((this.U) ? 1 : 0);
      }

      if (!var1) {
         if (var19 == 0) {
            label185: {
               int var21 = G.player.getInventory().getSelectedSlot();
               byte var23 = var12;
               if (!var1) {
                  if (var21 == var12) {
                     this.x = InteractionHand.MAIN_HAND;
                     return;
                  }

                  var22 = this;
                  if (var1) {
                     break label185;
                  }

                  var21 = this.B;
                  var23 = -1;
               }

               if (var21 == var23) {
                  this.B = G.player.getInventory().getSelectedSlot();
               }

               G.player.getInventory().setSelectedSlot(var12);
               var22 = this;
            }

            var22.U = true;
            return;
         }

         var19 = G.player.getInventory().getSelectedSlot();
      }

      label187: {
         if (!var1) {
            if (var19 != var12) {
               this.R(false);
               return;
            }

            var20 = this;
            if (var1) {
               break label187;
            }

            var19 = ((this.J(G.player.getMainHandItem())) ? 1 : 0);
         }

         if (var19 != 0) {
            this.x = InteractionHand.MAIN_HAND;
            return;
         }

         var20 = this;
      }

      var20.R(false);
   }

   private void b$V() {
      AutoRod var8 = null;
      boolean var1;
      int var10000;
      label117: {
         var1 = Velocity.p();
         if (G.player != null) {
            var10000 = this.p;
            if (var1) {
               break label117;
            }

            if (this.p == G.player.tickCount) {
               this.R(false);
               return;
            }
         }

         var10000 = ((this.H()) ? 1 : 0);
      }

      label120: {
         if (!var1) {
            if (var10000 != 0) {
               AutoRod var12 = this;
               if (!var1) {
                  if (this.I) {
                     this.c$V();
                     if (!var1) {
                        return;
                     }
                  }

                  var12 = this;
               }

               var12.R(false);
               return;
            }

            var8 = this;
            if (var1) {
               break label120;
            }

            var10000 = ((this.h.e(this.e.o$F())) ? 1 : 0);
         }

         if (var10000 == 0) {
            this.R(false);
            return;
         }

         var8 = this;
      }

      Optional var2 = var8.r();
      if (!var1) {
         label101:
         if (!var2.isEmpty()) {
            Object var9 = this.f;
            if (!var1) {
               if (this.f == null) {
                  break label101;
               }

               var9 = var2.get();
            }

            Entity var3 = (Entity)var9;
            double var4 = G.player.distanceTo(var3);
            AutoRod var10 = this;
            if (!var1) {
               if (this.G(this.f, Math.min(3.0, var4))) {
                  this.R(false);
                  return;
               }

               var10 = this;
            }

            InteractionHand var6 = var10.x;
            ItemStack var7 = var6 == InteractionHand.MAIN_HAND ? G.player.getMainHandItem() : G.player.getOffhandItem();
            boolean var11 = this.J(var7);
            if (!var1) {
               if (!var11) {
                  this.R(false);
                  return;
               }

               G.gameMode.useItem(G.player, var6);
               G.player.swing(var6, SwingAnimation.DEFAULT, false);
            }

            this.l = System.currentTimeMillis();
            this.I = true;
            this.c = var6;
            this.h.p();
            this.f = null;
            this.x = null;
            this.U = false;
            return;
         }

         this.R(false);
      }
   }

   @EventTarget
   public void N(EventUpdateHeldItem var1) {
      boolean var2 = Velocity.p();
      if (this.U) {
         EventUpdateHeldItem var10000 = var1;
         if (!var2) {
            if (var1.j$InteractionHand() != InteractionHand.MAIN_HAND) {
               return;
            }

            var10000 = var1;
         }

         var10000.w(G.player.getInventory().getItem(this.B));
      }
   }

   @Override
   public void h$V() {
      this.u$V();
   }

   @Override
   public void q$V() {
      this.v$V();
   }

   private boolean deobfLambda$onUpdate$0(AbstractClientPlayer var1) {
      boolean var2 = Velocity.p();
      float var3;
      int var10000 = (var3 = G.player.distanceTo(var1) - this.F.o$F()) == 0.0F ? 0 : (var3 < 0.0F ? -1 : 1);
      if (!var2) {
         var10000 = var10000 <= 0 ? 1 : 0;
      }

      return (boolean)((var10000) != 0);
   }

   private static double deobfLambda$getTarget$4(AbstractClientPlayer var0) {
      return G.player.distanceTo(var0);
   }

   private boolean deobfLambda$getTarget$3(AbstractClientPlayer var1) {
      return com.elowen.utils.rotation.RotationUtils.b(var1, this.y.o$F() / 2.0F);
   }

   private static boolean deobfLambda$getTarget$2(AbstractClientPlayer var0) {
      boolean var1 = Velocity.p();
      boolean var10000 = var0.isInvisible();
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private static boolean deobfLambda$getTarget$1(AbstractClientPlayer var0) {
      return G.player.hasLineOfSight(var0);
   }

   private boolean deobfLambda$getTarget$0(AbstractClientPlayer var1) {
      int var10000 = ((Velocity.o$Z()) ? 1 : 0);
      double var3 = G.player.distanceTo(var1);
      boolean var2 = (boolean)((var10000) != 0);
      double var6;
      var10000 = (var6 = var3 - this.P.o$F()) == 0.0 ? 0 : (var6 < 0.0 ? -1 : 1);
      if (var2) {
         if (var10000 > 0) {
            return false;
         }

         double var7;
         var10000 = (var7 = var3 - this.F.o$F()) == 0.0 ? 0 : (var7 < 0.0 ? -1 : 1);
      }

      return (boolean)(!var2 ? var10000 : var10000 >= 0);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
