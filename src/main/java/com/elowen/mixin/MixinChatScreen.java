package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventClientChat;
import com.elowen.exceptions.NoSuchModuleException;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
   private final ThreadLocal eventHolder = new ThreadLocal();

   @Inject(method = "handleChatInput", at = @At("HEAD"), cancellable = true)
   private void onHandleChatInput(String var1, boolean var2, CallbackInfo var3) {
      EventClientChat var4 = new EventClientChat(var1);
      Elowen.S$Elowen().e().B(var4);
      this.eventHolder.set(var4);
      if (var4.c$Z()) {
         var3.cancel();
      }
   }

   @ModifyVariable(method = "handleChatInput", at = @At("HEAD"), argsOnly = true, index = 1)
   private String modifyMessage(String var1) {
      EventClientChat var2 = (EventClientChat)this.eventHolder.get();
      return var2 != null && !var2.c$Z() ? var2.n$String() : var1;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
