package com.elowen.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FriendManager {
   private static final List o = new CopyOnWriteArrayList();

   public static boolean A(Entity var0) {
      String var1 = Vector2f.e();
      return !(var0 instanceof Player) ? false : o.contains(var0.getName().getString());
   }

   public static boolean u$Z(String var0) {
      return o.contains(var0);
   }

   public static void I(Player var0) {
      o.add(var0.getName().getString());
   }

   public static void V(String var0) {
      o.add(var0);
   }

   public static void z(Player var0) {
      o.remove(var0.getName().getString());
   }

   public static void u$V(String var0) {
      o.remove(var0);
   }

   public static List Q() {
      return o;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
