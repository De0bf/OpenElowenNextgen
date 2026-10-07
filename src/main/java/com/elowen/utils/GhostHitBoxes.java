package com.elowen.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.PositionAndRotation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GhostHitBoxes {
   private static final Map B;
   private static volatile boolean W;
   private static final String a;

   private GhostHitBoxes() {
      throw new UnsupportedOperationException(a);
   }

   public static void O(Minecraft var0) {
      boolean var7 = false;
      String var10000 = Vector2f.e();
      B.clear();
      String var1 = var10000;
      if (var1 == null) {
         label85:
         if (var0.player != null) {
            ClientLevel var4 = var0.level;
            if (var1 == null) {
               if (var0.level == null) {
                  break label85;
               }

               var4 = var0.level;
            }

            Iterator var2 = var4.players().iterator();

            label74: {
               while (true) {
                  if (var2.hasNext()) {
                     Player var3 = (Player)var2.next();
                     if (var1 != null) {
                        break;
                     }

                     Player var5 = var3;
                     if (var1 == null) {
                        if (var3 == var0.player) {
                           continue;
                        }

                        var5 = var3;
                     }

                     label94: {
                        boolean var6 = var5.isRemoved();
                        if (var1 == null) {
                           if (var6) {
                              continue;
                           }

                           if (var1 != null) {
                              break label94;
                           }

                           var6 = var3.isDeadOrDying();
                        }

                        if (var6) {
                           continue;
                        }

                        B.put(var3.getUUID(), R(var3));
                     }

                     if (var1 == null) {
                        continue;
                     }
                  }

                  var7 = B.isEmpty();
                  if (var1 != null) {
                     break label74;
                  }

                  if (!var7) {
                     var7 = true;
                     break label74;
                  }
                  break;
               }

               var7 = false;
            }

            W = var7;
            return;
         }

         W = false;
      }
   }

   public static AABB R(Player var0) {
      String var1 = Vector2f.e();
      if (var0 == null) {
         return null;
      }

      Vec3 var2 = k(var0);
      double var3 = var0.getBbWidth() * 0.5;
      double var5 = var0.getBbHeight();
      return new AABB(var2.x - var3, var2.y, var2.z - var3, var2.x + var3, var2.y + var5, var2.z + var3);
   }

   private static Vec3 k(Player var0) {
      String var1 = Vector2f.e();

      try {
         PositionAndRotation var2 = var0.getInterpolation().target();
         if (var2 != null && var2.position() != null) {
            return var2.position();
         }
      } catch (Exception var3) {
      }

      return new Vec3(var0.getX(), var0.getY(), var0.getZ());
   }

   public static void x() {
      B.clear();
      W = false;
   }

   public static boolean R$Z() {
      return W;
   }

   public static boolean n(UUID var0) {
      String var1 = Vector2f.e();
      return var0 != null && B.containsKey(var0);
   }

   public static AABB k(UUID var0) {
      return (AABB)B.get(var0);
   }

   public static Set Y() {
      return B.entrySet();
   }

   static {
      a = "Utility class";
      B = new ConcurrentHashMap();
      W = false;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
   }
