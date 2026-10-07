package com.elowen.modules.impl.combat;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.ChatUtils;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Entry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

@ModuleInfo(R = "AntiBots", M = Category.COMBAT, a = "Prevents bots from attacking you")
public class AntiBots extends Module {
   private static final Map Z;
   private static final Map R;
   private static final Map z;
   private static final Set q;
   private static final Map b;
   private final FloatValue I = ValueBuilder.m(this, "Respawn Time").d(2500.0F).V(100.0F).w(0.0F).M(10000.0F).f$K().L();
   private static final String[] c = new String[]{"Fake Staff Detected! (", "Respawn Time", "Bot Detected! (", "Bot Removed! ("};
   public static boolean O(Entity var0) {
      int var10000 = ((Velocity.p()) ? 1 : 0);
      AntiBots var2 = (AntiBots)Elowen.S$Elowen().q$ModuleManager().A(AntiBots.class);
      boolean var1 = (boolean)((var10000) != 0);
      float var4;
      var10000 = (var4 = var2.I.o$F() - 1.0F) == 0.0F ? 0 : (var4 < 0.0F ? -1 : 1);
      if (!var1) {
         if (var10000 < 0) {
            return false;
         }

         var10000 = ((b.containsKey(var0.getUUID())) ? 1 : 0);
      }

      if (!var1) {
         if (var10000 == 0) {
            return false;
         }

         float var5;
         var10000 = (var5 = (float)(System.currentTimeMillis() - (Long)b.get(var0.getUUID())) - var2.I.o$F()) == 0.0F ? 0 : (var5 < 0.0F ? -1 : 1);
      }

      if (!var1) {
         var10000 = var10000 < 0 ? 1 : 0;
      }

      return (boolean)((var10000) != 0);
   }

   public static boolean g(Entity var0) {
      return q.contains(var0.getId());
   }

   @EventTarget
   public void M(EventPacket var1) {
      Map var13 = null;
      boolean var2 = Velocity.p();
      if (var1.M() == EventType.RECEIVE && G.level != null) {
         Packet var11;
         label86: {
            Packet var5 = var1.R$Packet();
            boolean var10000 = var5 instanceof ClientboundPlayerInfoUpdatePacket;
            if (!var2) {
               if (var10000) {
                  ClientboundPlayerInfoUpdatePacket var3 = (ClientboundPlayerInfoUpdatePacket)var5;
                  ClientboundPlayerInfoUpdatePacket var14 = var3;
                  if (!var2) {
                     if (!var3.actions().contains(Action.ADD_PLAYER)) {
                        return;
                     }

                     var14 = var3;
                  }

                  for (Entry var6 : var14.entries()) {
                     GameProfile var7 = var6.profile();
                     UUID var8 = var7.id();
                     b.put(var8, System.currentTimeMillis());
                     if (var2 || var2) {
                        return;
                     }
                  }

                  return;
               }

               var5 = var1.R$Packet();
               var11 = var5;
               if (var2) {
                  break label86;
               }

               var10000 = var5 instanceof ClientboundAnimatePacket;
            }

            if (!var10000) {
               return;
            }

            var11 = var5;
         }

         ClientboundAnimatePacket var4 = (ClientboundAnimatePacket)var11;
         Entity var9 = G.level.getEntity(var4.getId());
         if (var9 != null) {
            UUID var10001;
            label90: {
               int var12 = var4.getAction();
               if (!var2) {
                  if (var12 != 0) {
                     return;
                  }

                  var13 = b;
                  var10001 = var9.getUUID();
                  if (var2) {
                     break label90;
                  }

                  var12 = (var13.containsKey(var10001) ? 1 : 0);
               }

               if (var12 == 0) {
                  return;
               }

               var13 = b;
               var10001 = var9.getUUID();
            }

            var13.remove(var10001);
         }
      }
   }

   @EventTarget
   public void G(EventRespawn var1) {
      Z.clear();
      R.clear();
      q.clear();
      z.clear();
   }

   @EventTarget
   public void H(EventMotion var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.Q() == EventType.PRE) {
         Iterator var3 = z.entrySet().iterator();
         while (var3.hasNext()) {
            java.util.Map.Entry var4 = (java.util.Map.Entry)var3.next();
            if (System.currentTimeMillis() - (Long)var4.getValue() > 500L) {
               ChatUtils.b("Fake Staff Detected! (" + (String)Z.get(var4.getKey()) + ")");
               z.remove(var4.getKey());
            }
         }
      }
   }

   @EventTarget
   public void Q(EventPacket var1) {
      Entry var22 = null;
      Packet var17 = null;
      boolean var2 = Velocity.p();
      EventPacket var10000 = var1;
      if (!var2) {
         if (var1.M() != EventType.RECEIVE) {
            return;
         }

         var10000 = var1;
      }

      Packet var6 = var10000.R$Packet();
      boolean var16 = var6 instanceof ClientboundPlayerInfoUpdatePacket;
      if (!var2) {
         if (var16) {
            ClientboundPlayerInfoUpdatePacket var3 = (ClientboundPlayerInfoUpdatePacket)var6;
            ClientboundPlayerInfoUpdatePacket var20 = var3;
            if (!var2) {
               if (!var3.actions().contains(Action.ADD_PLAYER)) {
                  return;
               }

               var20 = var3;
            }

            for (Entry var14 : var20.entries()) {
               if (var2) {
                  return;
               }

               label149: {
                  label150: {
                     Component var21 = var14.displayName();
                     if (!var2) {
                        if (var21 == null) {
                           break label149;
                        }

                        var22 = var14;
                        if (var2) {
                           break label150;
                        }

                        var21 = var14.displayName();
                     }

                     if (!var21.getSiblings().isEmpty()) {
                        break label149;
                     }

                     var22 = var14;
                  }

                  if (!var2) {
                     if (var22.gameMode() != GameType.SURVIVAL) {
                        break label149;
                     }

                     var22 = var14;
                  }

                  UUID var15 = var22.profile().id();
                  z.put(var15, System.currentTimeMillis());
                  Z.put(var15, var14.displayName().getString());
               }

               if (var2) {
                  return;
               }
            }

            return;
         }

         var6 = var1.R$Packet();
         var16 = var6 instanceof ClientboundAddEntityPacket;
      }

      label152: {
         if (!var2) {
            if (var16) {
               ClientboundAddEntityPacket var4 = (ClientboundAddEntityPacket)var6;
               UUID var11 = var4.getUUID();
               Map var19 = z;
               UUID var10001 = var11;
               if (!var2) {
                  if (!z.containsKey(var11)) {
                     return;
                  }

                  var19 = Z;
                  var10001 = var11;
               }

               String var13 = (String)var19.get(var10001);
               String[] var9 = c;
               ChatUtils.b("Bot Detected! (" + var13 + ")");
               R.put(var4.getId(), var13);
               z.remove(var11);
               q.add(var4.getId());
               return;
            }

            var6 = var1.R$Packet();
            var17 = var6;
            if (var2) {
               break label152;
            }

            var16 = var6 instanceof ClientboundRemoveEntitiesPacket;
         }

         if (!var16) {
            return;
         }

         var17 = var6;
      }

      ClientboundRemoveEntitiesPacket var5 = (ClientboundRemoveEntitiesPacket)var17;
      IntListIterator var10 = var5.entityIds().iterator();

      while (var10.hasNext()) {
         label83: {
            Integer var7 = var10.next();
            Object var18 = q;
            if (!var2) {
               if (!q.contains(var7)) {
                  break label83;
               }

               var18 = R.get(var7);
            }

            String var8 = (String)var18;
            ChatUtils.b("Bot Removed! (" + var8 + ")");
            q.remove(var7);
         }

         if (var2) {
            break;
         }
      }
   }

   static {
      Z = new ConcurrentHashMap();
      R = new ConcurrentHashMap();
      z = new ConcurrentHashMap();
      q = new HashSet();
      b = new ConcurrentHashMap();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
