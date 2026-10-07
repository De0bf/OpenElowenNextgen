package com.elowen.modules.impl.combat.critical;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventAttack;
import com.elowen.events.impl.EventSprint;
import com.elowen.modules.impl.combat.AntiBots;
import com.elowen.modules.impl.combat.Critical;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.combat.velocity.NoXZ;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.utils.FriendManager;
import com.elowen.utils.ChatUtils;
import com.elowen.utils.BlinkingPlayer;
import com.elowen.values.HasValue;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Vanilla implements CriticalMode {
   private Critical P;
   private int M;
   private boolean B;
   private boolean o;
   private double L;
   private static boolean K;
   private static final String a;

   public Vanilla() {
      boolean var10000 = K();
      this.M = 0;
      boolean var1 = var10000;
      this.B = false;
      this.o = false;
      this.L = 0.0;
      if (HasValue.X$Z()) {
         n(!var1);
      }
   }

   @Override
   public void O(Critical var1) {
      this.P = var1;
   }

   @Override
   public void f(EventTick var1) {
      this.P.X(a);
   }

   @Override
   public void F() {
      Vanilla var2;
      this.M = 0;
      boolean var10000 = p();
      this.B = false;
      boolean var1 = var10000;
      this.o = false;
      var2 = this;
      label38:
      if (!var1) {
         if (this.P != null) {
            var2 = this;
            if (var1) {
               break label38;
            }

            if (this.P.d$Minecraft().player != null) {
               this.L = this.P.d$Minecraft().player.getDeltaMovement().y;
               if (!var1) {
                  return;
               }

               HasValue.d(HasValue.X$Z());
            }
         }

         var2 = this;
      }

      var2.L = 0.0;
   }

   @Override
   public void w() {
      this.B = false;
      this.o = false;
   }

   @Override
   public void x() {
      this.F();
   }

   @Override
   public void m(EventMoveInput var1) {
      Vanilla var4 = null;
      boolean var2 = K();
      Critical var10000 = this.P;
      if (var2) {
         if (this.P == null) {
            return;
         }

         var10000 = this.P;
      }

      label46: {
         Minecraft var3 = var10000.d$Minecraft();
         if (var2) {
            if (var3.player == null) {
               return;
            }

            var4 = this;
            if (!var2) {
               break label46;
            }

            var3 = this.P.d$Minecraft();
         }

         if (var3.level == null) {
            return;
         }

         var4 = this;
      }

      if (var4.o) {
         var1.b(0.0F);
         var1.m(0.0F);
      }
   }

   @Override
   public void P(EventSprint var1) {
      Vanilla var4 = null;
      boolean var2 = K();
      Critical var10000 = this.P;
      if (var2) {
         if (this.P == null) {
            return;
         }

         var10000 = this.P;
      }

      label46: {
         Minecraft var3 = var10000.d$Minecraft();
         if (var2) {
            if (var3.player == null) {
               return;
            }

            var4 = this;
            if (!var2) {
               break label46;
            }

            var3 = this.P.d$Minecraft();
         }

         if (var3.level == null) {
            return;
         }

         var4 = this;
      }

      if (var4.B) {
         var1.W(-1);
      }
   }

   @Override
   public void K(com.elowen.events.impl.EventMotion var1) {
      boolean var2 = p();
      if (this.P != null && this.P.d$Minecraft().player != null && this.P.d$Minecraft().level != null) {
         if (this.P.E.w()) {
            Aura var3 = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
            if (var3 == null || !var3.w()) {
               this.B = false;
               this.o = false;
               this.L = this.P.d$Minecraft().player.getDeltaMovement().y;
               return;
            }
         }

         double var14 = this.P.d$Minecraft().player.getDeltaMovement().y;
         boolean var5 = this.P.d$Minecraft().player.onGround();
         boolean var6 = !var5 && this.L >= 0.0 && var14 < 0.0;
         boolean var7 = !var5 && var14 < 0.0;
         float var8 = this.P.K.o$F();
         LivingEntity var9 = this.W(var8);
         boolean var10 = false;
         boolean var11 = false;
         if ((var6 || var7) && var9 != null && !this.g$Z()) {
            int var12 = var9.hurtTime;
            float var13 = this.P.h.o$F();
            if (var12 <= var13) {
               var10 = true;
               if (this.P.X.w()) {
                  var11 = true;
               }
            }
         }

         this.B = var10;
         this.o = var11;
         this.L = var14;
         this.M++;
         if (!(this.M < this.P.b.o$F())) {
            this.M = 0;
            LivingEntity var15 = this.W(var8);
            if (var15 != null) {
               if (this.P.f.w()) {
                  ChatUtils.b(String.valueOf(var15.hurtTime));
               }
            }
         }
      }
   }

   @Override
   public void T(EventAttack var1) {
   }

   private boolean Y(LivingEntity var1) {
      boolean var2 = p();
      if (var1 == this.P.d$Minecraft().player) {
         return false;
      }

      if (var1 instanceof BlinkingPlayer) {
         return false;
      }

      AntiBots var3 = (AntiBots)Elowen.S$Elowen().q$ModuleManager().A(AntiBots.class);
      if (var3 == null || !var3.w() || !AntiBots.g(var1) && !AntiBots.O(var1)) {
         if (Teams.T(var1)) {
            return false;
         }

         if (FriendManager.A(var1)) {
            return false;
         }

         if (var1.isDeadOrDying() || var1.getHealth() <= 0.0F) {
            return false;
         }

         if (var1 instanceof ArmorStand) {
            return false;
         }

         if (var1 instanceof Player var4) {
            if (var4.getBbWidth() < 0.5 || var4.isSleeping()) {
               return false;
            }

            if (var4.isSpectator()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private LivingEntity W(double var1) {
      boolean var3 = p();
      if (this.P != null && this.P.d$Minecraft().player != null && this.P.d$Minecraft().level != null) {
         if (com.elowen.utils.rotation.RotationManager.L && com.elowen.utils.rotation.RotationManager.r != null) {
            float var8 = com.elowen.utils.rotation.RotationManager.r.H;
            float var9 = com.elowen.utils.rotation.RotationManager.r.E;
            double var4 = Math.toRadians(var8);
            double var6 = Math.toRadians(var9);
         }

         float var27 = this.P.d$Minecraft().player.getYRot();
         float var29 = this.P.d$Minecraft().player.getXRot();
         double var25 = Math.toRadians(var27);
         double var26 = Math.toRadians(var29);
         var27 = -Mth.sin((float)var25) * Mth.cos((float)var26);
         var29 = -Mth.sin((float)var26);
         float var10 = Mth.cos((float)var25) * Mth.cos((float)var26);
         Vec3 var11 = new Vec3(var27, var29, var10);
         Vec3 var12 = this.P.d$Minecraft().player.getEyePosition();
         Vec3 var13 = var12.add(var11.x * var1, var11.y * var1, var11.z * var1);
         AABB var14 = this.P.d$Minecraft().player.getBoundingBox().expandTowards(var11.scale(var1)).inflate(1.0);
         List var15 = this.P.d$Minecraft().level.getEntities(this.P.d$Minecraft().player, var14, this::deobfLambda$getTargetInRange$0);
         Entity var16 = null;
         double var17 = var1 + 1.0;
         Iterator var19 = var15.iterator();
         while (var19.hasNext()) {
            Entity var20 = (Entity)var19.next();
            AABB var21 = var20.getBoundingBox();
            Vec3 var22 = (Vec3)var21.clip(var12, var13).orElse(null);
            if (var22 != null) {
               double var23 = var12.distanceTo(var22);
               if (var23 < var17) {
                  var16 = var20;
               }
            }
         }

         return (LivingEntity)var16;
      } else {
         return null;
      }
   }

   private boolean g$Z() {
      boolean var1 = p();
      if (this.P == null) {
         return false;
      } else {
         Velocity var2 = (Velocity)Elowen.S$Elowen().q$ModuleManager().A(Velocity.class);
         if (var2 != null && var2.w()) {
            return !(var2.j instanceof NoXZ var3) ? false : var3.z$Z() || var3.X$Z();
         } else {
            return false;
         }
      }
   }

   private boolean deobfLambda$getTargetInRange$0(Entity var1) {
      boolean var2 = p();
      return var1 instanceof LivingEntity && this.Y((LivingEntity)var1);
   }

   public static void n(boolean var0) {
      K = var0;
   }

   public static boolean p() {
      return K;
   }

   public static boolean K() {
      return !p();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "Vanilla";
   }
}
