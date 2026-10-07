package com.elowen.modules.impl.misc;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventEntityJoinWorld;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.ChatUtils;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.ModeValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Entry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

@ModuleInfo(R = "AntiStaff", M = Category.MISC, a = "CheckStaff")
public class AntiStaff extends Module {
   private final BooleanValue t;
   private final ModeValue B;
   private static final List F;
   private static final String J;
   private final List x;
   private static final String[] b = new String[]{"绿豆乃SAMA", "体贴的炼金术雀", "三国杀", "Stellarcat", "圣上荣光233", "Skyfoy", "艾米丽", "mc加载", "data", "nightbary", "BACs", "呱太", "name", "管理员-3", "Andrewkrist", "妖猫", "CuteXiaoYY", "小军君丶天使之翼", "§aAuto Hub → ", "Fia9", "霜月月OO", "管理员-10", "中二少年DL", "无量域雪", "Fruit_Candy", "可比不来嗯忑", "Henglie", "彩笔qwq", "彩笔", "/lobby", "管理员-6", "Hub Command", "枕上书丶塑望月", "流影只会嘤嘤嘤", "枕上书丶中二少年DL", "七鹤さん", "布吉岛打工仔", "管理员-8", "小匪", "/spawn", "抑郁的元宵", "name", "CuteGirlQlQl", "元宵的测试号", "data", "CandyApostle", "咩太", "鸡你太美", "WS故", "getIdentifier", "KiKiAman", "LOL乄菠萝", "xPir4te_", "Kiana__24", "巧克布丁", "枕上书丶塑塑月", "小H修bug", "妖猫的PC号", "天使", "heypixel:s2cevent", "元宵", "抖音丶小匪", "马哥乐", "heypixel:s2cevent", "§cStaff Detected! (", "Jay__Chou", "枕上书丶傲寒", "斗战胜佛", "虚空", "管理员-5", "小妖猫", "getChannel", "管理员-2", "圣上荣耀233", "LamMolincen_", "元宵睡不醒", "枕上书丶雪夜", "/hub", "Auto Hub", "MnamLeo_", "CuteGirlQiQi", "管理员-4", "Davidsun", "Magician", "神坑之逗", "倘若两散不如两难", "神伦子", "qxtmlc99", "StarNO1", "枫萧林然", "Qiiiiiii", "管理员-7", "identifier", "getData", "叕口仙气就飘飘", "小H", "管理员-9", "管理员-1", "广告君A", "绿豆奶", "Kuri_Turgut", "东海林皇", "players", "抖音_awa马原", "TempMono", "叼口仙气就飘飘", "mengchen3884", "chunyi1", "xiaotufei", "练书法的苦力怕"};
   public AntiStaff() {
      String[] var1 = b;
      this.t = ValueBuilder.m(this, "Auto Hub").h(true).f$K().f$O();
      this.B = ValueBuilder.m(this, "Hub Command").W(new String[]{"/hub", "/lobby", "/spawn"}).m(0).f$K().T$t();
      this.x = Arrays.<AntiStaff$PayloadDecoder>asList(this::X, this::n, this::R, this::w, this::S);
   }

   @Override
   public void h$V() {
   }

   @Override
   public void q$V() {
   }

   @EventTarget
   public void V(EventEntityJoinWorld var1) {
      String[] var10000 = Teams.a$ArrString();
      Entity var3 = var1.I();
      String[] var2 = var10000;
      Entity var6 = var3;
      if (var2 != null) {
         if (var3 == null) {
            return;
         }

         var6 = var3;
      }

      Component var4 = var6.getDisplayName();
      Component var7 = var4;
      if (var2 != null) {
         if (var4 == null) {
            return;
         }

         var7 = var4;
      }

      String var5 = var7.getString();
      if (F.contains(var5)) {
         this.B(var5);
      }
   }

   @EventTarget
   public void x(com.elowen.events.impl.EventPacket var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.M() == EventType.RECEIVE) {
         if (var1.R$Packet() instanceof ClientboundPlayerInfoUpdatePacket var3) {
            for (Entry var6 : var3.entries()) {
               Component var7 = var6.displayName();
               if (var7 != null) {
                  String var8 = var7.getString();
                  if (F.contains(var8)) {
                     this.B(var8);
                  }
                  break;
               }
            }
         } else if (var1.R$Packet() instanceof ClientboundCustomPayloadPacket var4) {
            this.q(var4);
         }
      }
   }

   private void q(ClientboundCustomPayloadPacket var1) {
      boolean var12 = false;
      String[] var10000 = Teams.a$ArrString();
      Identifier var3 = this.A(var1);
      String[] var2 = var10000;
      if (var3 != null && "heypixel:s2cevent".equals(var3.toString())) {
         byte[] var4 = this.j(var1);
         byte[] var11 = var4;
         if (var2 != null) {
            if (var4 == null) {
               return;
            }

            var11 = var4;
         }

         if (var11.length != 0) {
            String var5 = null;
            Iterator var6 = this.x.iterator();

            while (true) {
               if (var6.hasNext()) {
                  AntiStaff$PayloadDecoder var7 = (AntiStaff$PayloadDecoder)var6.next();
                  var5 = var7.c(var4);
                  var12 = this.J(var5);
                  if (var2 == null) {
                     break;
                  }

                  if (!var12 && var2 != null) {
                     continue;
                  }
               }

               var12 = this.J(var5);
               break;
            }

            if (var12) {
               try {
                  JsonObject var10 = JsonParser.parseString(var5).getAsJsonObject();
                  String[] var8 = b;
                  this.G(var10, "players");
                  this.G(var10, "data");
               } catch (Exception var9) {
               }
            }
         }
      }
   }

   private void G(JsonObject var1, String var2) {
      String[] var3 = Teams.a$ArrString();
      if (var1.has(var2) && var1.get(var2).isJsonObject()) {
         JsonObject var4 = var1.getAsJsonObject(var2);
         Iterator var5 = var4.entrySet().iterator();
         while (var5.hasNext()) {
            java.util.Map.Entry var6 = (java.util.Map.Entry)var5.next();
            JsonObject var7 = ((JsonElement)var6.getValue()).getAsJsonObject();
            String var8 = var7.has("name") ? var7.get("name").getAsString() : null;
            if (var8 != null && F.contains(var8)) {
               this.B(var8);
            }
         }
      }
   }

   private void B(String var1) {
      String[] var10000 = Teams.a$ArrString();
      String[] var6 = b;
      String var3 = "§cStaff Detected! (" + var1 + ")";
      ChatUtils.b(var3);
      ChatUtils.b(var3);
      String[] var2 = var10000;
      ChatUtils.b(var3);
      if (this.t.w()) {
         Minecraft var4 = Minecraft.getInstance();
         Minecraft var7 = var4;
         if (var2 != null) {
            if (var4.player == null) {
               return;
            }

            var7 = var4;
         }

         if (var7.getConnection() != null) {
            String var5;
            label40: {
               label50: {
                  var5 = this.B.C().trim();
                  boolean var8 = var5.isEmpty();
                  if (var2 != null) {
                     if (var8) {
                        return;
                     }

                     ChatUtils.b("§aAuto Hub → " + var5);
                     if (var2 == null) {
                        break label50;
                     }

                     var8 = var5.startsWith("/");
                  }

                  if (!var8) {
                     break label40;
                  }

                  var4.player.connection.sendCommand(var5.substring(1));
               }

               if (var2 != null) {
                  return;
               }
            }

            var4.player.connection.sendChat(var5);
         }
      }
   }

   private Identifier A(ClientboundCustomPayloadPacket var1) {
      String[] var10000 = Teams.a$ArrString();
      String[] var10003 = new String[3];
      String[] var4 = b;
      var10003[0] = "getIdentifier";
      var10003[1] = "getChannel";
      var10003[2] = "identifier";
      Object var3 = this.G(var1, var10003);
      String[] var2 = var10000;
      Object var5 = var3;
      if (var2 != null) {
         if (!(var3 instanceof Identifier)) {
            return null;
         }

         var5 = var3;
      }

      return (Identifier)var5;
   }

   private byte[] j(ClientboundCustomPayloadPacket var1) {
      String[] var10000 = Teams.a$ArrString();
      String[] var10003 = new String[2];
      String[] var6 = b;
      var10003[0] = "getData";
      var10003[1] = "data";
      Object var3 = this.G(var1, var10003);
      String[] var2 = var10000;
      Object var7 = var3;
      if (var2 != null) {
         if (!(var3 instanceof FriendlyByteBuf)) {
            return null;
         }

         var7 = var3;
      }

      FriendlyByteBuf var4 = (FriendlyByteBuf)var7;
      byte[] var5 = new byte[var4.readableBytes()];
      var4.getBytes(var4.readerIndex(), var5);
      return var5;
   }

   private Object G(Object var1, String[] var2) {
      String[] var10000 = Teams.a$ArrString();
      String[] var4 = var2;
      String[] var3 = var10000;

      for (String var7 : var4) {
         try {
            Method var8 = var1.getClass().getMethod(var7);
            var8.setAccessible(true);
            return var8.invoke(var1);
         } catch (Exception var9) {
            if (var3 != null) {
               continue;
            }
            break;
         }
      }

      return null;
   }

   private boolean J(String var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1 != null && !var1.isEmpty()) {
         var1 = var1.trim();
         return var1.startsWith("{") || var1.startsWith("[");
      } else {
         return false;
      }
   }

   private String X(byte[] var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.length < 6) {
         return null;
      } else if ((var1[0] & 255) != 250) {
         return null;
      } else {
         return !this.h$Z(var1, 5) ? null : this.h$String(var1, 5);
      }
   }

   private String n(byte[] var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.length < 2) {
         return null;
      } else {
         return (var1[0] & 255) != 233 ? null : this.Z(var1, 1);
      }
   }

   private String R(byte[] var1) {
      int var2 = this.Y(var1);
      return var2 >= 0 ? this.h$String(var1, var2) : null;
   }

   private String w(byte[] var1) {
      int var2 = this.A(var1);
      return var2 >= 0 ? this.Z(var1, var2) : null;
   }

   private String S(byte[] var1) {
      return this.Z(var1, 0);
   }

   private boolean h$Z(byte[] var1, int var2) {
      String[] var3 = Teams.a$ArrString();
      if (var2 >= var1.length - 1) {
         return false;
      }

      int var4 = var1[var2] & 255;
      int var5 = var1[var2 + 1] & 255;
      return var4 == 120 && (var5 == 1 || var5 == 156 || var5 == 218);
   }

   private int Y(byte[] var1) {
      int var4 = 0;
      String[] var10000 = Teams.a$ArrString();
      int var3 = 0;
      String[] var2 = var10000;

      while (true) {
         if (var3 < var1.length - 1) {
            var4 = ((this.h$Z(var1, var3)) ? 1 : 0);
            if (var2 == null) {
               break;
            }

            if (var2 == null) {
               return var4;
            }

            if (var4 != 0) {
               return var3;
            }

            var3++;
            if (var2 != null) {
               continue;
            }
         }

         var4 = -1;
         break;
      }

      return var4;
   }

   private int A(byte[] var1) {
      String[] var10000 = Teams.a$ArrString();
      int var3 = 0;
      String[] var2 = var10000;

      while (true) {
         if (var3 < var1.length) {
            byte var4 = var1[var3];
            if (var2 == null) {
               return var4;
            }

            if (var2 == null) {
               return var4;
            }

            if (var4 == 123) {
               break;
            }

            byte var5 = var1[var3];
            if (var2 == null) {
               return var5;
            }

            if (var5 == 91) {
               break;
            }

            var3++;
            if (var2 != null) {
               continue;
            }
         }

         return -1;
      }

      return var3;
   }

   private String h$String(byte[] var1, int var2) {
      String[] var10000 = Teams.a$ArrString();
      Inflater var4 = new Inflater();
      String[] var3 = var10000;
      var4.setInput(var1, var2, var1.length - var2);
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      byte[] var6 = new byte[8192];

      try {
         while (!var4.finished()) {
            int var7 = var4.inflate(var6);
            if (var3 != null) {
               if (var7 == 0) {
                  break;
               }

               var5.write(var6, 0, var7);
            }

            if (var3 == null) {
               break;
            }
         }

         return new String(var5.toByteArray(), StandardCharsets.UTF_8);
      } catch (DataFormatException var12) {
         return null;
      } finally {
         var4.end();
      }
   }

   private String Z(byte[] var1, int var2) {
      if (var2 >= var1.length) {
         return null;
      }

      try {
         return new String(var1, var2, var1.length - var2, StandardCharsets.UTF_8);
      } catch (Exception var4) {
         return null;
      }
   }

   static {
      J = "heypixel:s2cevent";
      F = new ArrayList();
      String[] var9 = b;
      F.add("妖猫");
      F.add("Andrewkrist");
      F.add("BACs");
      F.add("CandyApostle");
      F.add("chunyi1");
      F.add("CuteXiaoYY");
      F.add("Davidsun");
      F.add("叕口仙气就飘飘");
      F.add("东海林皇");
      F.add("抖音_awa马原");
      F.add("斗战胜佛");
      F.add("枫萧林然");
      F.add("Fia9");
      F.add("Fruit_Candy");
      F.add("广告君A");
      F.add("咩太");
      F.add("Henglie");
      F.add("LamMolincen_");
      F.add("Jay__Chou");
      F.add("Kiana__24");
      F.add("枕上书丶塑望月");
      F.add("流影只会嘤嘤嘤");
      F.add("绿豆奶");
      F.add("Magician");
      F.add("mc加载");
      F.add("MnamLeo_");
      F.add("Kuri_Turgut");
      F.add("七鹤さん");
      F.add("Qiiiiiii");
      F.add("qxtmlc99");
      F.add("圣上荣光233");
      F.add("神坑之逗");
      F.add("霜月月OO");
      F.add("Skyfoy");
      F.add("Stellarcat");
      F.add("倘若两散不如两难");
      F.add("TempMono");
      F.add("无量域雪");
      F.add("小匪");
      F.add("小H");
      F.add("小军君丶天使之翼");
      F.add("xPir4te_");
      F.add("虚空");
      F.add("枕上书丶傲寒");
      F.add("枕上书丶雪夜");
      F.add("枕上书丶中二少年DL");
      F.add("绿豆乃SAMA");
      F.add("nightbary");
      F.add("体贴的炼金术雀");
      F.add("StarNO1");
      F.add("小妖猫");
      F.add("妖猫的PC号");
      F.add("小H修bug");
      F.add("xiaotufei");
      F.add("元宵");
      F.add("CuteGirlQlQl");
      F.add("CuteGirlQiQi");
      F.add("彩笔");
      F.add("布吉岛打工仔");
      F.add("元宵的测试号");
      F.add("抑郁的元宵");
      F.add("元宵睡不醒");
      F.add("抖音丶小匪");
      F.add("练书法的苦力怕");
      F.add("KiKiAman");
      F.add("WS故");
      F.add("彩笔qwq");
      F.add("管理员-1");
      F.add("管理员-2");
      F.add("管理员-3");
      F.add("管理员-4");
      F.add("管理员-5");
      F.add("管理员-6");
      F.add("管理员-7");
      F.add("管理员-8");
      F.add("管理员-9");
      F.add("管理员-10");
      F.add("天使");
      F.add("艾米丽");
      F.add("可比不来嗯忑");
      F.add("鸡你太美");
      F.add("神伦子");
      F.add("马哥乐");
      F.add("圣上荣耀233");
      F.add("呱太");
      F.add("巧克布丁");
      F.add("叼口仙气就飘飘");
      F.add("三国杀");
      F.add("枕上书丶塑塑月");
      F.add("中二少年DL");
      F.add("mengchen3884");
      F.add("LOL乄菠萝");
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
