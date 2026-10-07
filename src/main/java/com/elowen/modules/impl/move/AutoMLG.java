package com.elowen.modules.impl.move;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventTick;
import com.elowen.mixin.accessors.KeyMappingAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.ui.notification.NotificationLevel;
import com.elowen.ui.notification.Notification;
import com.elowen.utils.FallingPlayer;
import com.elowen.utils.Vector2f;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.utils.rotation.RotationManager;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult.Success;
import net.minecraft.world.InteractionResult.SwingSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "AutoMLG", a = "Automatically places water when falling", M = Category.MOVEMENT)
public class AutoMLG extends Module {
   private static final float h = 80.0F;
   private static final float f = 2.0F;
   private static final double D = 4.5;
   private static final double z = 5.0;
   private static final int F = 2;
   private static final int Y = 24;
   private static final double m = 0.15;
   private static final double J = 0.25;
   private static final int t = 10;
   private static final int p = 30;
   private static final int Q = 20;
   private static final double R = 0.3;
   private final FloatValue V;
   private final FloatValue i;
   private AutoMLG$State c;
   private Vector2f M;
   private InteractionHand b;
   private BlockPos S;
   private BlockPos x;
   private int T;
   private int U;
   private int Z;
   private int j;
   private int P;
   private int K;
   private boolean o;
   private boolean B;
   private boolean E;
   private static final String[] d = new String[]{"Failed to place water!", "No water bucket!", "Rotation Speed", "Failed to recycle the water due to moving!", "Fall Distance", "Failed to place water!", "No water bucket!"};
   public AutoMLG() {
      String[] var1 = d;
      this.V = com.elowen.values.ValueBuilder.m(this, "Fall Distance").d(3.0F).V(0.1F).w(3.0F).M(15.0F).f$K().L();
      this.i = com.elowen.values.ValueBuilder.m(this, "Rotation Speed").d(50.0F).V(1.0F).w(5.0F).M(180.0F).f$K().L();
      this.c = com.elowen.modules.impl.move.AutoMLG$State.IDLE;
      this.b = InteractionHand.MAIN_HAND;
      this.T = -1;
      this.U = -1;
      this.P = -1;
   }

   public Vector2f i$H() {
      return this.M;
   }

   private static Module e(Class var0) {
      try {
         return Elowen.S$Elowen().q$ModuleManager().A(var0);
      } catch (RuntimeException var2) {
         return null;
      }
   }

   @Override
   public void h$V() {
      this.P(true);
      this.o = false;
   }

   @Override
   public void q$V() {
      this.P(true);
   }

   @EventTarget
   public void f(EventTick param1) {
      boolean var2 = Scaffold.S$Z();
      if (param1.s$f() == com.elowen.events.api.types.EventType.PRE && G.player != null && G.level != null) {
         this.Z++;
         if (this.K > 0) {
            if (--this.K == 0) {
               this.P(true);
            }
         } else {
            if (this.o && (G.player.onGround() || G.player.fallDistance <= 0.0)) {
               this.o = false;
            }

            boolean var3 = this.d$Z();
            if (this.c == com.elowen.modules.impl.move.AutoMLG$State.PREPARE || this.c == com.elowen.modules.impl.move.AutoMLG$State.AIM) {
               if (G.player.onGround() && G.player.fallDistance <= 0.0 && !G.player.isInWater()) {
                  this.g("Failed to place water!");
                  return;
               }

               if (G.player.isInWater()) {
                  this.P(true);
                  return;
               }
            }

            switch (this.c.ordinal()) {
               case 0:
                  this.k(var3);
                  break;
               case 1:
                  this.u(var3);
                  break;
               case 2:
                  this.p(var3);
                  break;
               case 3:
                  this.V(var3);
                  break;
            }
         }
      }
   }

   private void k(boolean var1) {
      boolean var2 = Scaffold.S$Z();
      if (!var1 && !this.o && this.Z$h()) {
         if (!(G.player.fallDistance <= this.V.o$F())) {
            if (!this.U$Z()) {
               this.o = true;
               this.T(NotificationLevel.WARNING, "No water bucket!");
            } else {
               this.T = G.player.getInventory().getSelectedSlot();
               if (!this.S(Items.WATER_BUCKET)) {
                  this.o = true;
                  this.T(NotificationLevel.WARNING, "No water bucket!");
               } else {
                  this.p(com.elowen.modules.impl.move.AutoMLG$State.PREPARE);
               }
            }
         }
      }
   }

   private void u(boolean var1) {
      boolean var2;
      AutoMLG var6;
      label62: {
         boolean var10000 = Scaffold.k();
         this.j++;
         var2 = var10000;
         boolean var5 = var1;
         if (!var2) {
            if (var1) {
               this.M = null;
               return;
            }

            var6 = this;
            if (var2) {
               break label62;
            }

            var5 = this.S(Items.WATER_BUCKET);
         }

         if (!var5) {
            this.g("No water bucket!");
            return;
         }

         var6 = this;
      }

      Vector2f var7 = var6.M;
      if (!var2) {
         if (var6.M == null) {
            Vector2f var3 = com.elowen.utils.rotation.RotationManager.a$h();
            this.M = new Vector2f(var3.H, var3.E);
         }

         var7 = new Vector2f(this.M.H, 80.0F);
      }

      Vector2f var4 = var7;
      this.M = this.u(com.elowen.utils.rotation.RotationManager.a$h(), var4);
      var6 = this;
      label50:
      if (!var2) {
         if (!(Math.abs(this.M.E - 80.0F) <= 2.0F)) {
            var6 = this;
            if (var2) {
               break label50;
            }

            if (this.j < 10) {
               return;
            }
         }

         var6 = this;
      }

      var6.p(com.elowen.modules.impl.move.AutoMLG$State.AIM);
   }

   private void p(boolean var1) {
      boolean var2;
      AutoMLG var11;
      label100: {
         boolean var10000 = Scaffold.k();
         this.j++;
         var2 = var10000;
         boolean var10 = var1;
         if (!var2) {
            if (var1) {
               this.M = null;
               return;
            }

            var11 = this;
            if (var2) {
               break label100;
            }

            var10 = this.S(Items.WATER_BUCKET);
         }

         if (!var10) {
            this.g("No water bucket!");
            return;
         }

         var11 = this;
      }

      var11.B = false;
      Vec3 var3 = G.player.getEyePosition();
      List<AutoMLG$LandingTarget> var4 = this.a(this.Q(2));
      AutoMLG$LandingTarget var5 = null;
      Vec3 var6 = null;

      for (AutoMLG$LandingTarget var8 : var4) {
         Vec3 var9 = this.P(var8, var3);
         if (var9 != null) {
            var5 = var8;
            var6 = var9;
            break;
         }
      }

      AutoMLG$LandingTarget var12 = var5;
      if (!var2) {
         if (var5 != null) {
            this.S = var5.E;
            this.x = var5.X;
            this.M = this.u(com.elowen.utils.rotation.RotationManager.a$h(), com.elowen.utils.rotation.RotationUtils.X(var3, var6).M());
         }

         var12 = var5;
      }

      label103: {
         if (!var2) {
            if (var12 != null) {
               var11 = this;
               if (var2) {
                  break label103;
               }

               if (this.K(this.S, Direction.UP, 4.5, Fluid.NONE)) {
                  var11 = this;
                  break label103;
               }
            }

            var12 = var5;
         }

         if (var12 == null) {
            var11 = this;
            if (!var2) {
               if (this.j < 30) {
                  return;
               }

               var11 = this;
            }

            var11.g(this.B ? null : "Failed to place water!");
         }

         return;
      }

      var11.L();
   }

   private void V(boolean var1) {
      boolean var7 = false;
      int var9 = 0;
      boolean var2;
      AutoMLG var6;
      label161: {
         boolean var10000 = Scaffold.S$Z();
         this.j++;
         var2 = var10000;
         int var5 = this.j;
         int var10001 = 20;
         if (var2) {
            if (this.j >= 20) {
               this.g("Failed to recycle the water due to moving!");
               return;
            }

            var6 = this;
            if (!var2) {
               break label161;
            }

            var5 = this.Z;
            var10001 = this.P;
         }

         if (var5 <= var10001) {
            return;
         }

         var6 = this;
      }

      if (var2) {
         if (var6.x == null) {
            return;
         }

         var6 = this;
      }

      var7 = var6.E;
      label143:
      if (var2) {
         if (var6.E) {
            this.E = false;
            var7 = this.y(this.x);
            if (!var2) {
               break label143;
            }

            if (!var7) {
               this.g("Failed to place water!");
               return;
            }
         }

         var7 = var1;
      }

      label163: {
         if (var2) {
            if (!var7) {
               Vec3 var3 = G.player.getEyePosition();
               this.M = this.u(com.elowen.utils.rotation.RotationManager.a$h(), com.elowen.utils.rotation.RotationUtils.X(var3, Vec3.atCenterOf(this.x)).M());
            }

            var6 = this;
            if (!var2) {
               break label163;
            }

            var7 = this.y(this.x);
         }

         if (var7) {
            double var12;
            var9 = (var12 = G.player.getDeltaMovement().y - -0.5) == 0.0 ? 0 : (var12 < 0.0 ? -1 : 1);
            label127:
            if (var2) {
               if (var9 <= 0) {
                  var9 = ((G.player.onGround()) ? 1 : 0);
                  if (!var2) {
                     break label127;
                  }

                  if (var9 == 0) {
                     var9 = 0;
                     break label127;
                  }
               }

               var9 = 1;
            }

            int var4 = var9;
            int var10 = ((var1) ? 1 : 0);
            if (var2) {
               if (((var1) ? 1 : 0) != 0) {
                  return;
               }

               var10 = ((G.player.isInWater()) ? 1 : 0);
            }

            if (var2) {
               if (var10 == 0) {
                  return;
               }

               var10 = var4;
            }

            if (var2) {
               if (var10 == 0) {
                  return;
               }

               var10 = ((this.S(Items.BUCKET)) ? 1 : 0);
            }

            label167: {
               if (var2) {
                  if (var10 == 0) {
                     return;
                  }

                  var6 = this;
                  if (!var2) {
                     break label167;
                  }

                  var10 = ((this.K(this.x, null, 5.0, Fluid.SOURCE_ONLY)) ? 1 : 0);
               }

               if (var10 == 0) {
                  return;
               }

               var6 = this;
            }

            var6.J$V();
            return;
         }

         var6 = this;
      }

      var6.K = 2;
   }

   private boolean Z$h() {
      boolean var1 = Scaffold.S$Z();
      return G.gui.screen() == null
         && !G.player.isInWater()
         && !G.player.isInLava()
         && !G.player.onClimbable()
         && !G.player.isFallFlying()
         && !G.player.getAbilities().flying
         && !G.player.isPassenger()
         && G.player.getDeltaMovement().y < 0.0;
   }

   private boolean d$Z() {
      boolean var10000 = Scaffold.S$Z();
      Aura var2 = (Aura)e(Aura.class);
      boolean var1 = var10000;
      Aura var3 = var2;
      if (var1) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      var10000 = var3.w();
      if (var1) {
         if (!var10000) {
            return false;
         }

         var10000 = Aura.r;
      }

      return !var1 ? var10000 : var10000;
   }

   private boolean U$Z() {
      boolean var3 = false;
      boolean var1 = Scaffold.k();
      int var10000 = ((G.player.getOffhandItem().is(Items.WATER_BUCKET)) ? 1 : 0);
      if (!var1) {
         if (var10000 != 0) {
            this.b = InteractionHand.OFF_HAND;
            this.U = -1;
            return true;
         }

         var10000 = 0;
      }

      int var2 = var10000;

      while (true) {
         if (var2 < 9) {
            var3 = G.player.getInventory().getItem(var2).is(Items.WATER_BUCKET);
            if (var1) {
               break;
            }

            if (var1) {
               return var3;
            }

            if (var3) {
               this.b = InteractionHand.MAIN_HAND;
               this.U = var2;
               return true;
            }

            var2++;
            if (!var1) {
               continue;
            }
         }

         var3 = false;
         break;
      }

      return var3;
   }

   private boolean S(Item var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.b == InteractionHand.OFF_HAND) {
         return G.player.getOffhandItem().is(var1);
      }

      if (this.U >= 0 && this.U <= 8) {
         if (!G.player.getInventory().getItem(this.U).is(var1)) {
            return false;
         }

         G.player.getInventory().setSelectedSlot(this.U);
         return true;
      } else {
         return false;
      }
   }

   private boolean y(BlockPos var1) {
      boolean var2 = Scaffold.k();
      if (var1 != null) {
         ClientLevel var10000 = G.level;
         if (!var2) {
            if (G.level == null) {
               return false;
            }

            var10000 = G.level;
         }

         boolean var3 = var10000.getFluidState(var1).isSource();
         if (var2) {
            return var3;
         }

         if (var3) {
            return true;
         }
      }

      return false;
   }

   private void v$V() {
      boolean var1 = Scaffold.k();
      if (G.player != null) {
         AutoMLG var10000 = this;
         if (!var1) {
            if (this.b != InteractionHand.MAIN_HAND) {
               return;
            }

            var10000 = this;
         }

         int var2 = var10000.T;
         if (!var1) {
            if (var10000.T < 0) {
               return;
            }

            var2 = this.T;
         }

         if (var2 <= 8) {
            G.player.getInventory().setSelectedSlot(this.T);
         }
      }
   }

   private void L() {
      AutoMLG var2;
      label28: {
         boolean var1 = Scaffold.k();
         boolean var10000 = this.S(Items.WATER_BUCKET);
         if (!var1) {
            if (!var10000) {
               this.g("Failed to place water!");
               return;
            }

            var2 = this;
            if (var1) {
               break label28;
            }

            var10000 = this.F();
         }

         if (!var10000) {
            this.g("Failed to place water!");
            return;
         }

         this.P = this.Z;
         this.E = true;
         var2 = this;
      }

      var2.p(com.elowen.modules.impl.move.AutoMLG$State.RECYCLE);
   }

   private void J$V() {
      this.F();
   }

   private boolean F() {
      boolean var1 = Scaffold.S$Z();
      if (this.b == InteractionHand.MAIN_HAND) {
         return this.m();
      } else if (G.gameMode.useItem(G.player, this.b) instanceof Success var2) {
         this.q(this.b, var2);
         return true;
      } else {
         return false;
      }
   }

   private boolean m() {
      boolean var10000 = Scaffold.S$Z();
      KeyMapping var2 = G.options.keyUse;
      boolean var1 = var10000;
      var10000 = var2.isUnbound();
      if (var1) {
         label27:
         if (!var10000) {
            Gui var4 = G.gui;
            if (var1) {
               if (G.gui.screen() != null) {
                  break label27;
               }

               var4 = G.gui;
            }

            if (var4.overlay() == null) {
               KeyMapping.click(((KeyMappingAccessor)var2).elowen$getKey());
               return true;
            }
         }

         var10000 = false;
      }

      return var10000;
   }

   private void q(InteractionHand var1, Success var2) {
      boolean var3 = Scaffold.k();
      if (!var3) {
         if (var2.swingSource() == SwingSource.PREDICTED) {
            G.player.swing(var1, G.player.getItemInHand(var1).getInteractAnimation(), false);
         }

         G.player.itemUsed(var1);
      }
   }

   private boolean K(BlockPos var1, Direction var2, double var3, Fluid var5) {
      boolean var6 = Scaffold.S$Z();
      if (this.M != null && com.elowen.utils.rotation.RotationManager.L) {
         Vector2f var7 = com.elowen.utils.rotation.RotationManager.a$h();
         Vec3 var8 = G.player.getEyePosition();
         Vec3 var9 = var8.add(com.elowen.utils.RayTraceUtils.c(var7.H, var7.E).scale(5.0));
         HitResult var10 = com.elowen.utils.RayTraceUtils.c(var8, var9, Block.OUTLINE, var5, G.player);
         if (!(var10 instanceof BlockHitResult var11 && var10.getType() == Type.BLOCK)) {
            return false;
         } else if (!var11.getBlockPos().equals(var1)) {
            return false;
         } else {
            return var2 != null && var11.getDirection() != var2 ? false : var8.distanceTo(var11.getLocation()) <= var3;
         }
      } else {
         return false;
      }
   }

   private Vec3 Q(int var1) {
      FallingPlayer var2 = new FallingPlayer(G.player);
      var2.u(var1);
      return var2.g$Vec3();
   }

   private List a(Vec3 var1) {
      ArrayList<AutoMLG$LandingTarget> var3 = new ArrayList<>();
      boolean var10000 = Scaffold.S$Z();
      int var4 = Mth.floor(G.player.getY());
      boolean var2 = var10000;
      int var5 = Math.max(G.level.getMinY(), var4 - 24);
      int var6 = Mth.floor(var1.x);
      int var7 = Mth.floor(var1.z);
      int var8 = Mth.floor(var1.x - 0.3);

      while (true) {
         if (var8 <= Mth.floor(var1.x + 0.3)) {
            if (!var2) {
               break;
            }

            int var9 = Mth.floor(var1.z - 0.3);

            label67: {
               while (var9 <= Mth.floor(var1.z + 0.3)) {
                  AutoMLG$LandingTarget var10 = this.Y(var8, var9, var4, var5);
                  if (!var2) {
                     break label67;
                  }

                  if (var2) {
                     if (var10 != null) {
                        int var10001;
                        label59: {
                           label58: {
                              var10001 = var8;
                              int var10002 = var6;
                              if (var2) {
                                 if (var8 != var6) {
                                    break label58;
                                 }

                                 var10001 = var9;
                                 if (!var2) {
                                    break label59;
                                 }

                                 var10002 = var7;
                              }

                              if (var10001 == var10002) {
                                 var10001 = 1;
                                 break label59;
                              }
                           }

                           var10001 = 0;
                        }

                        var10.u = (boolean)((var10001) != 0);
                        var3.add(var10);
                     }

                     var9++;
                  }

                  if (!var2) {
                     break;
                  }
               }

               var8++;
            }

            if (var2) {
               continue;
            }
         }

         var3.sort((var0, var1x) -> deobfLambda$findLandings$0(var1, var0, var1x));
         break;
      }

      return var3;
   }

   private AutoMLG$LandingTarget Y(int var1, int var2, int var3, int var4) {
      boolean var10000 = Scaffold.S$Z();
      Vec3 var6 = new Vec3(var1 + 0.5, var3 + 0.05, var2 + 0.5);
      boolean var5 = var10000;
      Vec3 var7 = new Vec3(var1 + 0.5, var4, var2 + 0.5);
      BlockHitResult var8 = G.level.clip(new ClipContext(var6, var7, Block.COLLIDER, Fluid.NONE, G.player));
      BlockHitResult var14 = var8;
      if (var5) {
         if (!(var8 instanceof BlockHitResult)) {
            return null;
         }

         var14 = var8;
      }

      BlockHitResult var9 = var14;
      BlockHitResult var15 = var8;
      if (var5) {
         if (var8.getType() != Type.BLOCK) {
            return null;
         }

         var15 = var9;
      }

      if (var5) {
         if (var15.getDirection() != Direction.UP) {
            return null;
         }

         var15 = var9;
      }

      BlockPos var10 = var15.getBlockPos();
      BlockPos var11 = var10.above();
      BlockState var12 = G.level.getBlockState(var11);
      FluidState var13 = var12.getFluidState();
      var10000 = var13.isEmpty();
      if (var5) {
         if (!var10000) {
            this.B = true;
            return null;
         }

         var10000 = var12.isAir();
      }

      if (var5) {
         if (var10000) {
            return new AutoMLG$LandingTarget(var10, var11, var9.getLocation().y, var9.getLocation());
         }

         var10000 = var12.canBeReplaced(Fluids.WATER);
      }

      return !var10000 ? null : new AutoMLG$LandingTarget(var10, var11, var9.getLocation().y, var9.getLocation());
   }

   private List w(AutoMLG$LandingTarget var1, Vec3 var2) {
      ArrayList var4;
      double var5;
      double var7;
      double var9;
      int var10000 = ((Scaffold.k()) ? 1 : 0);
      var4 = new ArrayList();
      var5 = var1.s;
      var7 = var1.d.x;
      boolean var3 = (boolean)((var10000) != 0);
      var9 = var1.d.z;
      double var14;
      var10000 = (var14 = Math.abs(var2.x - var7) - 0.15) == 0.0 ? 0 : (var14 < 0.0 ? -1 : 1);
      label27:
      if (!var3) {
         if (var10000 < 0) {
            double var15;
            var10000 = (var15 = Math.abs(var2.z - var9) - 0.15) == 0.0 ? 0 : (var15 < 0.0 ? -1 : 1);
            if (var3) {
               break label27;
            }

            if (var10000 < 0) {
               Vec3 var11 = com.elowen.utils.RayTraceUtils.c(com.elowen.utils.rotation.RotationManager.a$h().H, 0.0F);
               var4.add(new Vec3(Mth.clamp(var7 + var11.x * 0.25, var7 - 0.25, var7 + 0.25), var5, Mth.clamp(var9 + var11.z * 0.25, var9 - 0.25, var9 + 0.25)));
               if (!var3) {
                  break label27;
               }
            }
         }

         var4.add(new Vec3(var7, var5, var9));
      }

      var4.add(new Vec3(Mth.clamp(var2.x, var7 - 0.25, var7 + 0.25), var5, Mth.clamp(var2.z, var9 - 0.25, var9 + 0.25)));
      var4.add(new Vec3(var7 + 0.25, var5, var9));
      var4.add(new Vec3(var7 - 0.25, var5, var9));
      var4.add(new Vec3(var7, var5, var9 + 0.25));
      var4.add(new Vec3(var7, var5, var9 - 0.25));
      return var4;
   }

   private Vec3 P(AutoMLG$LandingTarget var1, Vec3 var2) {
      Vec3 var9 = null;
      Vec3 var4 = null;
      float var5 = Float.MAX_VALUE;
      boolean var10000 = Scaffold.S$Z();
      Iterator var6 = this.w(var1, var2).iterator();
      boolean var3 = var10000;

      while (true) {
         if (var6.hasNext()) {
            var9 = (Vec3)var6.next();
            if (!var3) {
               break;
            }

            Vec3 var7 = var9;
            AutoMLG var10 = this;
            Vec3 var10001 = var2;
            Vec3 var10002 = var7;
            if (var3) {
               if (!this.p(var2, var7, var1.E, Direction.UP) && var3) {
                  continue;
               }

               var10 = this;
               var10001 = var2;
               var10002 = var7;
            }

            label37: {
               float var8 = var10.s(com.elowen.utils.rotation.RotationUtils.X(var10001, var10002).M());
               float var11 = var8;
               if (var3) {
                  if (!(var8 < var5)) {
                     break label37;
                  }

                  var11 = var8;
               }

               var5 = var11;
               var4 = var7;
            }

            if (var3) {
               continue;
            }
         }

         var9 = var4;
         break;
      }

      return var9;
   }

   private boolean p(Vec3 var1, Vec3 var2, BlockPos var3, Direction var4) {
      int var10000 = ((Scaffold.S$Z()) ? 1 : 0);
      Vec3 var6 = var2.subtract(var1);
      boolean var5 = (boolean)((var10000) != 0);
      double var7 = var6.length();
      double var16;
      var10000 = (var16 = var7 - 1.0E-4) == 0.0 ? 0 : (var16 < 0.0 ? -1 : 1);
      if (var5) {
         if (var10000 >= 0) {
            Vec3 var9 = var1.add(var6.scale((var7 + 0.05) / var7));
            HitResult var10 = com.elowen.utils.RayTraceUtils.c(var1, var9, Block.OUTLINE, Fluid.NONE, G.player);
            boolean var13 = var10 instanceof BlockHitResult;
            if (var5) {
               if (var13) {
                  BlockHitResult var11 = (BlockHitResult)var10;
                  if (var10.getType() == Type.BLOCK) {
                     BlockHitResult var14 = var11;
                     if (var5) {
                        if (!var11.getBlockPos().equals(var3)) {
                           return false;
                        }

                        var14 = var11;
                     }

                     if (var14.getDirection() == var4) {
                        return true;
                     }

                     return false;
                  }
               }

               var13 = false;
            }

            return var13;
         }

         var10000 = 0;
      }

      return (boolean)((var10000) != 0);
   }

   private float s(Vector2f var1) {
      Vector2f var2 = com.elowen.utils.rotation.RotationManager.a$h();
      return Math.abs(com.elowen.utils.rotation.RotationUtils.e(var1.H, var2.H)) + 2.0F * Math.abs(com.elowen.utils.rotation.RotationUtils.e(var1.E, var2.E));
   }

   private Vector2f u(Vector2f var1, Vector2f var2) {
      float var3 = this.i.o$F();
      float var4 = com.elowen.utils.rotation.RotationUtils.i(var3, var1.H, var2.H);
      float var5 = com.elowen.utils.rotation.RotationUtils.q(var1.E, var2.E, var3);
      return new Vector2f(com.elowen.utils.rotation.RotationManager.o(var4), com.elowen.utils.rotation.RotationManager.w(var5));
   }

   private void p(AutoMLG$State var1) {
      this.c = var1;
      this.j = 0;
   }

   private void g(String var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1 != null) {
         this.T(NotificationLevel.WARNING, var1);
      }

      this.o = true;
      this.P(true);
   }

   private void P(boolean var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1) {
         this.v$V();
      }

      this.c = com.elowen.modules.impl.move.AutoMLG$State.IDLE;
      this.j = 0;
      this.P = -1;
      this.K = 0;
      this.M = null;
      this.S = null;
      this.x = null;
      this.T = -1;
      this.U = -1;
      this.b = InteractionHand.MAIN_HAND;
      this.B = false;
      this.E = false;
   }

   private void T(NotificationLevel var1, String var2) {
      Elowen.S$Elowen().Q().m(new Notification(var1, var2, 3000L));
   }

   private static int deobfLambda$findLandings$0(Vec3 var0, AutoMLG$LandingTarget var1, AutoMLG$LandingTarget var2) {
      int var10000 = ((Scaffold.k()) ? 1 : 0);
      int var4 = Double.compare(var2.s, var1.s);
      boolean var3 = (boolean)((var10000) != 0);
      var10000 = var4;
      if (!var3) {
         if (var4 != 0) {
            return var4;
         }

         var10000 = ((var1.u) ? 1 : 0);
      }

      if (!var3) {
         if (var10000 != ((var2.u) ? 1 : 0)) {
            var10000 = ((var1.u) ? 1 : 0);
            if (!var3) {
               var10000 = var1.u ? -1 : 1;
            }

            return var10000;
         }

         var10000 = Double.compare(var1.d.distanceToSqr(var0), var2.d.distanceToSqr(var0));
      }

      return var10000;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
