package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRenderTabOverlay;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.ModeValue;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

@ModuleInfo(R = "NameProtect", a = "Protect your name", M = Category.RENDER)
public class NameProtect extends Module {
   private static final String R;
   private final ModeValue K;
   private final com.elowen.values.impl.StringValue E;
   private static final String[] b = new String[]{"§dHidden§r", "Hidden", "Random", "Random", "妖猫", "Mode", "§k", "Custom", "§dHidden§r", "Name", "Custom", "§r"};
   public NameProtect() {
      String[] var1 = b;
      this.K = ValueBuilder.m(this, "Mode").m(0).W(new String[]{"Hidden", "Random", "Custom"}).f$K().T$t();
      this.E = ValueBuilder.m(this, "Name").J("妖猫").l(this::deobfLambda$new$0).f$K().N();
   }

   private static String F(NameProtect var0) {
      HasValue[] var1 = Theme.s$ArrQ();
      if (var0.K.t("Custom")) {
         String var2 = var0.E.R$String();
         if (var2 != null && !var2.isEmpty()) {
            return var2;
         }
      }

      return "§dHidden§r";
   }

   public static String u$String(String var0) {
      HasValue[] var1 = Theme.s$ArrQ();
      if (G.player == null) {
         return var0;
      }

      try {
         NameProtect var2 = (NameProtect)Elowen.S$Elowen().q$ModuleManager().A(NameProtect.class);
         if (var2 == null || !var2.w()) {
            return var0;
         }

         String var3 = G.player.getName().getString();
         if (var0.contains(var3)) {
            if (var2.K.t("Random")) {
               String[] var4 = b;
               return var0.replace(var3, "§k" + var3 + "§r");
            }

            return var0.replace(var3, F(var2));
         }
      } catch (Exception var5) {
      }

      return var0;
   }

   public static Component s(Component var0) {
      HasValue[] var1 = Theme.s$ArrQ();
      if (var0 != null && G.player != null) {
         try {
            NameProtect var2 = (NameProtect)Elowen.S$Elowen().q$ModuleManager().A(NameProtect.class);
            if (var2 == null || !var2.w()) {
               return var0;
            }
         } catch (Exception var4) {
            return var0;
         }

         String var5 = G.player.getName().getString();
         if (!var0.getString().contains(var5)) {
            return var0;
         }

         MutableComponent var3 = Component.empty();
         var0.visit((style, text) -> deobfLambda$protectComponent$0(var3, style, text), Style.EMPTY);
         return var3;
      } else {
         return var0;
      }
   }

   @com.elowen.events.api.EventTarget
   public void j(EventRenderTabOverlay var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.N() == EventType.NAME) {
         var1.O(s(var1.t()));
      }
   }

   private static Optional deobfLambda$protectComponent$0(MutableComponent var0, Style var1, String var2) {
      String var3 = u$String(var2);
      if (!var3.isEmpty()) {
         var0.append(Component.literal(var3).withStyle(var1));
      }

      return Optional.empty();
   }

   private Boolean deobfLambda$new$0() {
      return this.K.t("Custom");
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      R = "§dHidden§r";
   }
}
