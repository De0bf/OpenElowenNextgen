package com.elowen.modules.impl.misc;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.ModeValue;
import java.util.Objects;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;

@ModuleInfo(R = "Teams", a = "Prevent attack teammates", M = Category.MISC)
public class Teams extends Module {
   public static Teams b;
   public ModeValue v;
   private static String[] T;
   private static final String[] d = new String[]{"Color", "Mode", "Scoreboard", "Color"};
   public Teams() {
      String[] var1 = d;
      this.v = ValueBuilder.m(this, "Mode").m(0).W(new String[]{"Scoreboard", "Color"}).f$K().T$t();
      b = this;
   }

   public static boolean T(Entity var0) {
      String[] var1 = a$ArrString();
      if (!Elowen.S$Elowen().q$ModuleManager().A(Teams.class).w()) {
         return false;
      }

      if (var0 instanceof Player) {
         if (b.v.t("Color")) {
            Integer var4 = var0.getTeamColor();
            Integer var5 = AntiStaff$PayloadDecoder.player.getTeamColor();
            return var4.equals(var5);
         } else {
            String var2 = b(var0);
            String var3 = b(AntiStaff$PayloadDecoder.player);
            return Objects.equals(var2, var3);
         }
      } else {
         return false;
      }
   }

   public static String b(Entity var0) {
      String[] var10000 = a$ArrString();
      PlayerInfo var2 = AntiStaff$PayloadDecoder.getConnection().getPlayerInfo(var0.getUUID());
      String[] var1 = var10000;
      PlayerInfo var3 = var2;
      if (var1 != null) {
         if (var2 == null) {
            return null;
         }

         var3 = var2;
      }

      PlayerTeam var4 = var3.getTeam();
      if (var1 != null) {
         if (var4 == null) {
            return null;
         }

         var4 = var2.getTeam();
      }

      return var4.getName();
   }

   public static void p(String[] var0) {
      T = var0;
   }

   public static String[] a$ArrString() {
      return T;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      String[] var10000 = new String[3];
      p(var10000);
   }
}
