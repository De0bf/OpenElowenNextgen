package com.elowen.modules.impl.misc;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.TimeHelper;
import com.elowen.values.HasValueManager;
import com.elowen.values.Value;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@ModuleInfo(R = "Spammer", a = "Spam chat!", M = Category.MISC)
public class Spammer extends Module {
   Random e = new Random();
   FloatValue P;
   ModeValue h;
   public static final String[] i = new String[]{"我是狂笑的蛇陈安健，我是花雨庭反作弊兼Hack Lunar端制作者！", "我是狂笑的蛇陈安健，我使用Hack Lunar不会Ban！因为我制作了花雨庭反作弊！", "你知道吗，我是狂笑的蛇陈安健！我制作了花雨庭反作弊，误封你们！", "不如我狂笑的蛇将写散文制作的Hack Lunar，我很稳定因为花雨庭反作弊就是出自本人之手！", "兄弟，你听我说！我不会被封，因为我是狂笑的蛇，我制作了花雨庭反作弊！", "我是狂笑的蛇陈安健，我疯狂巴结花雨庭的技术！", "陈安健制作的Hack Lunar依然稳定绕过花雨庭，因为我是狂笑的蛇！", "我是大名鼎鼎的狂笑的蛇将写散文本人陈安健，我能无视反作弊飘主播因为我制作了花雨庭反作弊！", "羡慕我不会被封吗？因为我狂笑的蛇本人陈安健制作了花雨庭的反作弊！", "B站搜索狂笑的蛇将写散文，看我陈安健精彩集锦！", "我狂笑的蛇陈安健能无视反作弊，因为花雨庭反作弊就是我做的！", "哈哈你知道你为什么会被误封吗？因为我狂笑的蛇陈安健制作了花雨庭反作弊！"};
   private final List r;
   private final TimeHelper j;
   private static final String[] b = new String[]{"我是大名鼎鼎的狂笑的蛇将写散文本人陈安健，我能无视反作弊飘主播因为我制作了花雨庭反作弊！", "None", "我是狂笑的蛇陈安健，我疯狂巴结花雨庭的技术！", "Delay", "兄弟，你听我说！我不会被封，因为我是狂笑的蛇，我制作了花雨庭反作弊！", "不如我狂笑的蛇将写散文制作的Hack Lunar，我很稳定因为花雨庭反作弊就是出自本人之手！", "我是狂笑的蛇陈安健，我使用Hack Lunar不会Ban！因为我制作了花雨庭反作弊！", "我狂笑的蛇陈安健能无视反作弊，因为花雨庭反作弊就是我做的！", "None", "B站搜索狂笑的蛇将写散文，看我陈安健精彩集锦！", "Prefix", "陈安健制作的Hack Lunar依然稳定绕过花雨庭，因为我是狂笑的蛇！", "你知道吗，我是狂笑的蛇陈安健！我制作了花雨庭反作弊，误封你们！", "哈哈你知道你为什么会被误封吗？因为我狂笑的蛇陈安健制作了花雨庭反作弊！", "羡慕我不会被封吗？因为我狂笑的蛇本人陈安健制作了花雨庭的反作弊！", "/shout ", "我是狂笑的蛇陈安健，我是花雨庭反作弊兼Hack Lunar端制作者！"};
   public Spammer() {
      String[] var1 = b;
      this.P = ValueBuilder.m(this, "Delay").d(6000.0F).V(100.0F).w(0.0F).M(15000.0F).f$K().L();
      this.h = ValueBuilder.m(this, "Prefix").m(0).W(new String[]{"None", "@", "/shout "}).f$K().T$t();
      this.r = new ArrayList();
      this.j = new TimeHelper();
   }

   @EventTarget
   public void W(EventTick var1) {
      String[] var2 = Teams.a$ArrString();
      if (var1.s$f() == EventType.POST && this.j.e(this.P.o$F())) {
         String var3 = this.h.t("None") ? "" : this.h.C();
         List var4 = ((List<BooleanValue>)this.r).stream().filter(BooleanValue::w).map(Value::r).toList();
         if (var4.isEmpty()) {
            return;
         }

         String var5 = (String)var4.get(this.e.nextInt(var4.size()));
         String var6 = var3 + var5;
         boolean var7 = AntiStaff$PayloadDecoder.player.isSprinting();
         if (var7) {
            AntiStaff$PayloadDecoder.player.setSprinting(false);
         }

         AntiStaff$PayloadDecoder.player.connection.sendChat(var6);
         if (var7) {
            AntiStaff$PayloadDecoder.player.setSprinting(true);
         }

         this.j.p();
      }
   }

   public List d$List() {
      return this.r;
   }

   public void A(HasValueManager var1) {
      String[] var10000 = Teams.a$ArrString();
      Iterator var3 = this.r.iterator();
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

         this.r.clear();
         break;
      }
   }

   public BooleanValue C(String var1, boolean var2) {
      BooleanValue var3 = ValueBuilder.m(this, var1).h(var2).f$K().f$O();
      this.r.add(var3);
      return var3;
   }

   public void J$V() {
      String[] var1 = Teams.a$ArrString();
      if (this.r.isEmpty()) {
         String[] var2 = i;
         int var3 = var2.length;
         int var4 = 0;
         while (var4 < var3) {
            String var5 = var2[var4];
            this.C(var5, false);
            var4++;
         }
      }
   }

   static {
      String[] var11 = new String[12];
      String[] var9 = b;
      var11[0] = "我是狂笑的蛇陈安健，我是花雨庭反作弊兼Hack Lunar端制作者！";
      var11[1] = "我是狂笑的蛇陈安健，我使用Hack Lunar不会Ban！因为我制作了花雨庭反作弊！";
      var11[2] = "你知道吗，我是狂笑的蛇陈安健！我制作了花雨庭反作弊，误封你们！";
      var11[3] = "不如我狂笑的蛇将写散文制作的Hack Lunar，我很稳定因为花雨庭反作弊就是出自本人之手！";
      var11[4] = "兄弟，你听我说！我不会被封，因为我是狂笑的蛇，我制作了花雨庭反作弊！";
      var11[5] = "我是狂笑的蛇陈安健，我疯狂巴结花雨庭的技术！";
      var11[6] = "陈安健制作的Hack Lunar依然稳定绕过花雨庭，因为我是狂笑的蛇！";
      var11[7] = "我是大名鼎鼎的狂笑的蛇将写散文本人陈安健，我能无视反作弊飘主播因为我制作了花雨庭反作弊！";
      var11[8] = "羡慕我不会被封吗？因为我狂笑的蛇本人陈安健制作了花雨庭的反作弊！";
      var11[9] = "B站搜索狂笑的蛇将写散文，看我陈安健精彩集锦！";
      var11[10] = "我狂笑的蛇陈安健能无视反作弊，因为花雨庭反作弊就是我做的！";
      var11[11] = "哈哈你知道你为什么会被误封吗？因为我狂笑的蛇陈安健制作了花雨庭反作弊！";
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
