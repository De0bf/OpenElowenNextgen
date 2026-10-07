package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.render.BetterNameTag;
import com.elowen.modules.impl.render.NameProtect;
import com.elowen.modules.impl.render.NameTags;
import java.util.Optional;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
   @Inject(method = "getNameTag(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true)
   private void elowen$onGetNameTag(Entity var1, CallbackInfoReturnable var2) {
      Component var3 = (Component)var2.getReturnValue();
      if (var3 != null) {
         String var4 = var3.getString();
         BetterNameTag var5 = (BetterNameTag)Elowen.S$Elowen().q$ModuleManager().A(BetterNameTag.class);
         if (var5 == null || !var5.w() || !var4.contains("\\n") && !var4.contains("@")) {
            NameProtect var8 = (NameProtect)Elowen.S$Elowen().q$ModuleManager().A(NameProtect.class);
            if (var8 != null && var8.w()) {
               String var7 = NameProtect.u$String(var4);
               if (!var7.equals(var4)) {
                  var2.setReturnValue(Component.literal(var7));
               }
            }
         } else {
            Component var6 = cleanName(var3);
            var2.setReturnValue(var6.getString().isEmpty() ? null : var6);
         }
      }
   }

   @Inject(
      method = "extractNameTags(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FDD)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void elowen$onExtractNameTags(Entity var1, EntityRenderState var2, float var3, double var4, double var6, CallbackInfo var8) {
      NameTags var9 = (NameTags)Elowen.S$Elowen().q$ModuleManager().A(NameTags.class);
      if (var9 != null && var9.w() && var1 instanceof Player) {
         var8.cancel();
      }
   }

   @Unique
   private static Component cleanName(Component var0) {
      MutableComponent var1 = Component.empty();
      var0.visit((style, text) -> cleanNamePart(var1, style, text), Style.EMPTY);
      return var1;
   }

   private static Optional cleanNamePart(MutableComponent var0, Style var1, String var2) {
      String var3 = var2.replace("\\n", "\n").replaceAll("@[^@]*@", "");
      if (!var3.isEmpty()) {
         var0.append(Component.literal(var3).withStyle(var1));
      }

      return Optional.empty();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
