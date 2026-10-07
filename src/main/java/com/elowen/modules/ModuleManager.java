package com.elowen.modules;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventMouseClick;
import com.elowen.events.impl.EventKey;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.AntiBots;
import com.elowen.modules.impl.combat.AutoBlock;
import com.elowen.modules.impl.combat.AutoClicker;
import com.elowen.modules.impl.combat.AutoRod;
import com.elowen.modules.impl.combat.Critical;
import com.elowen.modules.impl.combat.AttackCrystal;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.fun.AutoEmptyAttack;
import com.elowen.modules.impl.fun.AutoCloseMC;
import com.elowen.modules.impl.fun.NoSwing;
import com.elowen.modules.impl.fun.FuckYou;
import com.elowen.modules.impl.misc.AntiStaff;
import com.elowen.modules.impl.misc.Disabler;
import com.elowen.modules.impl.misc.KillSay;
import com.elowen.modules.impl.misc.Spammer;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.modules.impl.move.AutoMLG;
import com.elowen.modules.impl.move.Blink;
import com.elowen.modules.impl.move.FastWeb;
import com.elowen.modules.impl.move.LongJump;
import com.elowen.modules.impl.move.NoFall;
import com.elowen.modules.impl.move.NoJumpDelay;
import com.elowen.modules.impl.move.NoSlow;
import com.elowen.modules.impl.move.RePlay;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.modules.impl.move.Sprint;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.modules.impl.move.Timer;
import com.elowen.modules.impl.player.AutoTools;
import com.elowen.modules.impl.player.ChestStealer;
import com.elowen.modules.impl.player.FastPlace;
import com.elowen.modules.impl.player.GhostHand;
import com.elowen.modules.impl.player.InventoryCleaner;
import com.elowen.modules.impl.render.Animations;
import com.elowen.modules.impl.render.AntiBlindness;
import com.elowen.modules.impl.render.AntiNausea;
import com.elowen.modules.impl.render.BetterNameTag;
import com.elowen.modules.impl.render.BoxESP;
import com.elowen.modules.impl.render.ChestESP;
import com.elowen.modules.impl.render.ClearLava;
import com.elowen.modules.impl.render.ClearWater;
import com.elowen.modules.impl.render.ClickGUIModule;
import com.elowen.modules.impl.render.Compass;
import com.elowen.modules.impl.render.FullBright;
import com.elowen.modules.impl.render.GhostESP;
import com.elowen.modules.impl.render.InterFace;
import com.elowen.modules.impl.render.ItemTags;
import com.elowen.modules.impl.render.LowFire;
import com.elowen.modules.impl.render.NameProtect;
import com.elowen.modules.impl.render.NameTags;
import com.elowen.modules.impl.render.NoHurtCam;
import com.elowen.modules.impl.render.NoRender;
import com.elowen.modules.impl.render.NotificationModule;
import com.elowen.modules.impl.render.OldHurtCam;
import com.elowen.modules.impl.render.PostProcess;
import com.elowen.modules.impl.render.Projectile;
import com.elowen.modules.impl.render.RotationCurve;
import com.elowen.modules.impl.render.Scoreboard;
import com.elowen.modules.impl.render.TargetHUD;
import com.elowen.modules.impl.render.Theme;
import com.elowen.modules.impl.render.TimeChanger;
import com.elowen.modules.impl.render.ViewClip;
import com.elowen.modules.impl.render.WaterMark;
import com.elowen.modules.impl.test.CheckS08;
import com.elowen.modules.impl.test.ECFullDisabler;
import com.elowen.modules.impl.test.NoInterpolation;
import com.elowen.modules.impl.test.PrintNameTags;
import com.elowen.modules.impl.test.PrintRot;
import com.elowen.values.HasValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleManager {
   private static final Logger B;
   private final List<Module> Y;
   private final Map p;
   private final Map k;
   private static int o;
   private static final String a;

   public ModuleManager() {
      boolean var10000 = Module.O();
      super();
      this.Y = new ArrayList();
      this.p = new HashMap();
      boolean var1 = var10000;
      this.k = new HashMap();

      try {
         this.C();
         this.Y.sort(ModuleManager::deobfLambda$new$0);
      } catch (Exception var3) {
         B.error(a, var3);
         throw new RuntimeException(var3);
      }

      Elowen.S$Elowen().e().I(this);
      if (!var1) {
         HasValue.d(HasValue.x());
      }
   }

   private void C() {
      this.F(
         new Module[]{
            new AntiBlindness(),
            new Animations(),
            new AntiBots(),
            new BetterNameTag(),
            new BoxESP(),
            new AntiNausea(),
            new AutoCloseMC(),
            new AutoEmptyAttack(),
            new AutoRod(),
            new AutoMLG(),
            new ClearLava(),
            new ClearWater(),
            new ClickGUIModule(),
            new FastPlace(),
            new FullBright(),
            new FuckYou(),
            new InterFace(),
            new Theme(),
            new WaterMark(),
            new com.elowen.modules.impl.render.ArrayListModule(),
            new NotificationModule(),
            new KillSay(),
            new LowFire(),
            new NameProtect(),
            new NameTags(),
            new NoHurtCam(),
            new NoRender(),
            new NoSwing(),
            new OldHurtCam(),
            new Scoreboard(),
            new Spammer(),
            new ViewClip(),
            new ChestESP(),
            new TargetHUD(),
            new GhostESP(),
            new NoInterpolation(),
            new PrintRot(),
            new PrintNameTags(),
            new CheckS08(),
            new PostProcess(),
            new AutoTools(),
            new AutoClicker(),
            new AutoBlock(),
            new Sprint(),
            new Timer(),
            new Compass(),
            new AttackCrystal(),
            new Aura(),
            new AntiStaff(),
            new Teams(),
            new Blink(),
            new Stuck(),
            new Velocity(),
            new Critical(),
            new LongJump(),
            new NoFall(),
            new NoJumpDelay(),
            new FastWeb(),
            new NoSlow(),
            new RePlay(),
            new Scaffold(),
            new Disabler(),
            new ChestStealer(),
            new GhostHand(),
            new InventoryCleaner(),
            new TimeChanger(),
            new RotationCurve(),
            new ECFullDisabler(),
            new Projectile(),
            new ItemTags()
         }
      );
   }

   private void F(Module[] var1) {
      boolean var10000 = Module.O();
      Module[] var3 = var1;
      boolean var2 = var10000;

      for (Module var6 : var3) {
         this.H(var6);
         if (!var2) {
            break;
         }
      }
   }

   private void H(Module var1) {
      var1.Z();
      this.Y.add(var1);
      this.p.put(var1.getClass(), var1);
      this.k.put(var1.i().toLowerCase(), var1);
   }

   public List O(Category var1) {
      ArrayList var3 = new ArrayList();
      boolean var10000 = Module.O();
      Iterator var4 = this.Y.iterator();
      boolean var2 = var10000;

      while (var4.hasNext()) {
         Module var5 = (Module)var4.next();
         if (var5.C() == var1) {
            var3.add(var5);
         }

         if (!var2) {
            break;
         }
      }

      return var3;
   }

   public Module A(Class var1) {
      boolean var10000 = Module.l$Z();
      Module var3 = (Module)this.p.get(var1);
      boolean var2 = var10000;
      Module var4 = var3;
      if (!var2) {
         if (var3 == null) {
            throw new NoSuchModuleException();
         }

         var4 = var3;
      }

      return var4;
   }

   public Module k(String var1) {
      boolean var10000 = Module.O();
      Module var3 = (Module)this.k.get(var1.toLowerCase());
      boolean var2 = var10000;
      Module var4 = var3;
      if (var2) {
         if (var3 == null) {
            throw new NoSuchModuleException();
         }

         var4 = var3;
      }

      return var4;
   }

   @EventTarget
   public void S(EventKey var1) {
      com.elowen.modules.Module var6 = null;
      boolean var2 = Module.l$Z();
      int var10000 = var1.n$I();
      if (!var2) {
         if (var10000 == 0) {
            return;
         }

         var10000 = ((var1.U$Z()) ? 1 : 0);
      }

      if (var10000 != 0 && Minecraft.getInstance().gui.screen() == null) {
         for (Module var4 : this.Y) {
            label48: {
               label59: {
                  var10000 = var4.U$I();
                  if (!var2) {
                     if (var10000 == 0) {
                        break label48;
                     }

                     var6 = var4;
                     if (var2) {
                        break label59;
                     }

                     var10000 = var4.U$I();
                  }

                  if (var10000 != var1.n$I()) {
                     break label48;
                  }

                  var6 = var4;
               }

               var6.R$V();
            }

            if (var2) {
               break;
            }
         }
      }
   }

   @EventTarget
   public void X(EventMouseClick var1) {
      boolean var2 = Module.O();
      if (!var1.Z() && (var1.v$I() == 3 || var1.v$I() == 4)) {
         Iterator var3 = this.Y.iterator();
         while (var3.hasNext()) {
            Module var4 = (Module)var3.next();
            if (var4.U$I() == -var1.v$I()) {
               var4.R$V();
            }
         }
      }
   }

   public List i() {
      return this.Y;
   }

   private static int deobfLambda$new$0(Module var0, Module var1) {
      return var0.i().compareToIgnoreCase(var1.i());
   }

   static {
      a = "Failed to initialize modules";
      B = LogManager.getLogger(ModuleManager.class);
   }

   public static void f(int var0) {
      o = var0;
   }

   public static int I() {
      return o;
   }

   public static int c$I() {
      int var0 = I();
      return var0 == 0 ? 96 : 0;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
