package com.elowen.modules.impl.misc;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.TimeHelper;
import com.elowen.values.HasValueManager;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;

@ModuleInfo(R = "KillSay", a = "Automatic send message when you killed someone!", M = Category.MISC)
public class KillSay extends Module {
   Queue i;
   FloatValue E;
   ModeValue V;
   public static final String[] d = new String[]{"%s L", "%s fw", "%s 我喜欢你", "%s 我喜欢你♥", "%s 兄弟你好香", "%s 可以和我交往吗？", "%s 你好可爱", "%s 别急", "%s 你已被清朝杀手陈安健害死！快使用Hack Lunar！", "%s 你已被狂笑的蛇陈安健本人害死，快使用我编写的Hack Lunar端！"};
   private final List Z;
   public static Set c;
   private final TimeHelper B;
   Random U;
   private static final String[] b = new String[]{"%s fw", "%s 别急", "Delay", "%s 你已被清朝杀手陈安健害死！快使用Hack Lunar！", "None", "%s L", "/shout ", "None", "%s 我喜欢你", "%s 我喜欢你♥", "%s 兄弟你好香", "%s 可以和我交往吗？", "%s 你已被狂笑的蛇陈安健本人害死，快使用我编写的Hack Lunar端！", "Prefix", "%s 你好可爱"};
   public KillSay() {
      Teams.a$ArrString();
      this.i = new ConcurrentLinkedQueue();
      String[] var2 = b;
      this.E = com.elowen.values.ValueBuilder.m(this, "Delay").d(6000.0F).V(100.0F).w(0.0F).M(15000.0F).f$K().L();
      this.V = com.elowen.values.ValueBuilder.m(this, "Prefix").m(0).W(new String[]{"None", "@", "!", "/shout "}).f$K().T$t();
      this.Z = new ArrayList();
      this.B = new TimeHelper();
      this.U = new Random();
      if (!HasValue.x()) {
         Teams.p(new String[3]);
      }
   }

   @EventTarget
   public void G(EventRespawn var1) {
      c.clear();
   }

   @EventTarget
   public void R(com.elowen.events.impl.EventPacket var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.R$Packet() instanceof ClientboundPlayerInfoRemovePacket && var1.M() == EventType.RECEIVE && G.getConnection() != null) {
         ClientboundPlayerInfoRemovePacket var3 = (ClientboundPlayerInfoRemovePacket)var1.R$Packet();
         Iterator var4 = var3.profileIds().iterator();
         while (var4.hasNext()) {
            UUID var5 = (UUID)var4.next();
            PlayerInfo var6 = G.getConnection().getPlayerInfo(var5);
            if (var6 != null) {
               String var7 = var6.getProfile().name();
               if (c.contains(var7)) {
                  String var8 = this.V.t("None") ? "" : this.V.C();
                  List var9 = ((List<BooleanValue>)this.Z).stream().filter(BooleanValue::w).map(Value::r).toList();
                  if (!var9.isEmpty()) {
                     String var10 = (String)var9.get(this.U.nextInt(var9.size()));
                     String var11 = var8 + String.format(var10, var7);
                     this.i.offer(var11);
                     c.remove(var7);
                  }
               }
            }
         }
      }
   }

   @EventTarget
   public void C(com.elowen.events.impl.EventMotion var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.Q() == EventType.PRE) {
         if (G.player != null && G.player.tickCount < 10) {
            this.i.clear();
            c.clear();
            return;
         }

         if (this.B.e(this.E.o$F()) && !this.i.isEmpty()) {
            String var3 = (String)this.i.poll();
            boolean var4 = G.player.isSprinting();
            if (var4) {
               G.player.setSprinting(false);
            }

            G.player.connection.sendChat(var3);
            if (var4) {
               G.player.setSprinting(true);
            }

            this.B.p();
         }
      }
   }

   public List K() {
      return this.Z;
   }

   public void f(HasValueManager var1) {
      String[] var10000 = Teams.a$ArrString();
      Iterator var3 = this.Z.iterator();
      String[] var2 = var10000;

      while (true) {
         if (var3.hasNext()) {
            BooleanValue var4 = (BooleanValue)var3.next();
            var1.J(var4);
            if (var2 == null) {
               break;
            }

            if (var2 != null) {
               continue;
            }
         }

         this.Z.clear();
         break;
      }
   }

   public BooleanValue p(String var1, boolean var2) {
      BooleanValue var3 = com.elowen.values.ValueBuilder.m(this, var1).h(var2).f$K().f$O();
      this.Z.add(var3);
      return var3;
   }

   public void G$V() {
      String[] var1 = Teams.a$ArrString();
      if (this.Z.isEmpty()) {
         String[] var2 = d;
         int var3 = var2.length;
         int var4 = 0;
         while (var4 < var3) {
            String var5 = var2[var4];
            this.p(var5, false);
            var4++;
         }
      }
   }

   static {
      String[] var11 = new String[10];
      String[] var9 = b;
      var11[0] = "%s L";
      var11[1] = "%s fw";
      var11[2] = "%s 我喜欢你";
      var11[3] = "%s 我喜欢你♥";
      var11[4] = "%s 兄弟你好香";
      var11[5] = "%s 可以和我交往吗？";
      var11[6] = "%s 你好可爱";
      var11[7] = "%s 别急";
      var11[8] = "%s 你已被清朝杀手陈安健害死！快使用Hack Lunar！";
      var11[9] = "%s 你已被狂笑的蛇陈安健本人害死，快使用我编写的Hack Lunar端！";
      c = new CopyOnWriteArraySet();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
