package com.elowen.utils;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EntityWatcher {
   private static final Minecraft H;
   private static final Map f;
   private static final Map M;
   private static final String[] a = new String[]{"End Crystal", " is holding KB Ball!", "Power Bow", "KB Stick", "God Axe", "Punch Bow", "KB Ball", " is holding end crystal!", " is holding Punch Bow!", "Power Bow", "KB Ball", " is holding enchanted golden apple!", "End Crystal", " is holding KB Stick!", "God Axe", " is holding Power Bow!", "effect.minecraft.", "Enchanted Golden Apple", "Punch Bow", " is holding god axe!", "Enchanted Golden Apple", "KB Stick"};
   public static Set n(AbstractClientPlayer var0) {
      String var10000 = Vector2f.e();
      LinkedHashSet var2 = new LinkedHashSet();
      String var1 = var10000;
      if (var1 == null && f.containsKey(var0)) {
         var2.addAll((Collection)f.get(var0));
      }

      for (MobEffectInstance var4 : var0.getActiveEffects()) {
         Holder var7 = var4.getEffect();
         if (var1 == null) {
            if (var7.is(MobEffects.ABSORPTION) && var1 == null) {
               continue;
            }

            var7 = var4.getEffect();
         }

         Holder var5 = var7;
         Identifier var6 = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)var5.value());
         if (var6 != null) {
            var2.add("effect.minecraft." + var6.getPath());
         }

         if (var1 != null) {
            break;
         }
      }

      return var2;
   }

   @EventTarget
   public void O(com.elowen.events.impl.EventRespawn var1) {
      f.clear();
      M.clear();
   }

   @EventTarget
   public void d(com.elowen.events.impl.EventMotion var1) {
      String var2 = Vector2f.e();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE && H.level != null) {
         f$Map().forEach((a, b) -> deobfLambda$onMotion$0((String)a, (SharedESPData)b));
         Iterator var3 = new ArrayList(H.level.players()).iterator();
         while (var3.hasNext()) {
            AbstractClientPlayer var4 = (AbstractClientPlayer)var3.next();
            if (var4 != H.player) {
               if (!f.containsKey(var4)) {
                  f.put(var4, new HashSet());
               }

               Set var5 = (Set)f.get(var4);
               if ((InventoryUtils.X(var4.getMainHandItem()) || InventoryUtils.X(var4.getOffhandItem())) && !var5.contains("God Axe")) {
                  com.elowen.ui.notification.NotificationLevel var10002 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var10003 = var4.getName().getString();
                  String[] var7 = a;
                  com.elowen.ui.notification.Notification var6 = new com.elowen.ui.notification.Notification(var10002, var10003 + " is holding god axe!", 3000L);
                  Elowen.S$Elowen().Q().m(var6);
                  var5.add("God Axe");
               }

               if ((InventoryUtils.Q(var4.getMainHandItem()) || InventoryUtils.Q(var4.getOffhandItem())) && !var5.contains("Enchanted Golden Apple")) {
                  com.elowen.ui.notification.NotificationLevel var20 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var26 = var4.getName().getString();
                  String[] var14 = a;
                  com.elowen.ui.notification.Notification var8 = new com.elowen.ui.notification.Notification(var20, var26 + " is holding enchanted golden apple!", 3000L);
                  Elowen.S$Elowen().Q().m(var8);
                  var5.add("Enchanted Golden Apple");
               }

               if ((InventoryUtils.Z(var4.getMainHandItem()) || InventoryUtils.Z(var4.getOffhandItem())) && !var5.contains("End Crystal")) {
                  com.elowen.ui.notification.NotificationLevel var21 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var27 = var4.getName().getString();
                  String[] var15 = a;
                  com.elowen.ui.notification.Notification var9 = new com.elowen.ui.notification.Notification(var21, var27 + " is holding end crystal!", 3000L);
                  Elowen.S$Elowen().Q().m(var9);
                  var5.add("End Crystal");
               }

               if ((InventoryUtils.M(var4.getMainHandItem()) || InventoryUtils.M(var4.getOffhandItem())) && !var5.contains("KB Ball")) {
                  com.elowen.ui.notification.NotificationLevel var22 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var28 = var4.getName().getString();
                  String[] var16 = a;
                  com.elowen.ui.notification.Notification var10 = new com.elowen.ui.notification.Notification(var22, var28 + " is holding KB Ball!", 3000L);
                  Elowen.S$Elowen().Q().m(var10);
                  var5.add("KB Ball");
               }

               if ((InventoryUtils.B(var4.getMainHandItem()) || InventoryUtils.B(var4.getOffhandItem())) && !var5.contains("KB Stick")) {
                  com.elowen.ui.notification.NotificationLevel var23 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var29 = var4.getName().getString();
                  String[] var17 = a;
                  com.elowen.ui.notification.Notification var11 = new com.elowen.ui.notification.Notification(var23, var29 + " is holding KB Stick!", 3000L);
                  Elowen.S$Elowen().Q().m(var11);
                  var5.add("KB Stick");
               }

               if ((InventoryUtils.C$I(var4.getMainHandItem()) > 2 || InventoryUtils.C$I(var4.getOffhandItem()) > 2) && !var5.contains("Punch Bow")) {
                  com.elowen.ui.notification.NotificationLevel var24 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var30 = var4.getName().getString();
                  String[] var18 = a;
                  com.elowen.ui.notification.Notification var12 = new com.elowen.ui.notification.Notification(var24, var30 + " is holding Punch Bow!", 3000L);
                  Elowen.S$Elowen().Q().m(var12);
                  var5.add("Punch Bow");
               }

               if ((InventoryUtils.g$I(var4.getMainHandItem()) > 3 || InventoryUtils.g$I(var4.getOffhandItem()) > 3) && !var5.contains("Power Bow")) {
                  com.elowen.ui.notification.NotificationLevel var25 = com.elowen.ui.notification.NotificationLevel.WARNING;
                  String var31 = var4.getName().getString();
                  String[] var19 = a;
                  com.elowen.ui.notification.Notification var13 = new com.elowen.ui.notification.Notification(var25, var31 + " is holding Power Bow!", 3000L);
                  Elowen.S$Elowen().Q().m(var13);
                  var5.add("Power Bow");
               }
            }
         }
      }
   }

   public static Map f$Map() {
      return M;
   }

   private static void deobfLambda$onMotion$0(String var0, SharedESPData var1) {
      if (System.currentTimeMillis() - var1.X$J() > 500L) {
         f$Map().remove(var0);
      }
   }

   static {
      H = Minecraft.getInstance();
      f = new ConcurrentHashMap();
      M = new ConcurrentHashMap();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
