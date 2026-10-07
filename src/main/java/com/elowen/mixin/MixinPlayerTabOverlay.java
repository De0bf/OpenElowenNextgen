package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRenderTabOverlay;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerTabOverlay.class)
public abstract class MixinPlayerTabOverlay {
   @Shadow
   public abstract Component getNameForDisplay(PlayerInfo var1);

   @Redirect(
      method = "extractRenderState",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;split(Lnet/minecraft/network/chat/FormattedText;I)Ljava/util/List;", ordinal = 0)
   )
   public List hookHeader(Font var1, FormattedText var2, int var3) {
      Component var4 = (Component)var2;
      EventRenderTabOverlay var5 = new EventRenderTabOverlay(EventType.HEADER, var4);
      Elowen.S$Elowen().e().B(var5);
      return var1.split(var5.t(), var3);
   }

   @Redirect(
      method = "extractRenderState",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;split(Lnet/minecraft/network/chat/FormattedText;I)Ljava/util/List;", ordinal = 1)
   )
   public List hookFooter(Font var1, FormattedText var2, int var3) {
      Component var4 = (Component)var2;
      EventRenderTabOverlay var5 = new EventRenderTabOverlay(EventType.FOOTER, var4);
      Elowen.S$Elowen().e().B(var5);
      return var1.split(var5.t(), var3);
   }

   @Redirect(
      method = "extractRenderState",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;getNameForDisplay(Lnet/minecraft/client/multiplayer/PlayerInfo;)Lnet/minecraft/network/chat/Component;"
      )
   )
   public Component hookName(PlayerTabOverlay var1, PlayerInfo var2) {
      Component var3 = this.getNameForDisplay(var2);
      EventRenderTabOverlay var4 = new EventRenderTabOverlay(EventType.NAME, var3);
      Elowen.S$Elowen().e().B(var4);
      return var4.t();
   }
}
