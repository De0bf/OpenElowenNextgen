package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.commands.CommandManager$Completions;
import com.elowen.exceptions.NoSuchModuleException;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandSuggestions.class)
public abstract class MixinCommandSuggestions {
   @Shadow
   @Final
   private EditBox input;
   @Shadow
   private CompletableFuture pendingSuggestions;
   @Shadow
   private boolean allowSuggestions;
   @Shadow
   private boolean keepSuggestions;
   @Shadow
   @Final
   private boolean commandsOnly;

   @Shadow
   public abstract void showSuggestions(boolean var1);

   @Inject(method = "updateCommandInfo", at = @At("TAIL"))
   private void onUpdateCommandInfo(CallbackInfo var1) {
      if (!this.keepSuggestions) {
         if (!this.commandsOnly) {
            String var2 = this.input.getValue();
            int var3 = this.input.getCursorPosition();
            if (var2.startsWith(".") && var3 >= ".".length() && var3 <= var2.length()) {
               CommandManager$Completions var4 = Elowen.S$Elowen().U$R().W(var2, var3);
               if (!var4.C().isEmpty()) {
                  SuggestionsBuilder var5 = new SuggestionsBuilder(var2.substring(0, var3), var4.e());

                  for (String var7 : (List<String>)var4.C()) {
                     var5.suggest(var7);
                  }

                  this.pendingSuggestions = var5.buildFuture();
                  if (this.allowSuggestions && (Boolean)Minecraft.getInstance().options.autoSuggestions().get()) {
                     this.showSuggestions(false);
                  }
               }
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
