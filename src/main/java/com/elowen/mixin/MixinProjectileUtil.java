package com.elowen.mixin;

import com.elowen.utils.BlinkingPlayer;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ProjectileUtil.class)
public class MixinProjectileUtil {
   @Redirect(
      method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
      )
   )
   private static List hook(Level var0, Entity var1, AABB var2, Predicate var3) {
      List var4 = var0.getEntities(var1, var2, var3);
      var4.removeIf(var5 -> deobfLambda$hook$0((Entity)var5));
      return var4;
   }

   private static boolean deobfLambda$hook$0(Entity var0) {
      return var0 instanceof BlinkingPlayer;
   }
}
