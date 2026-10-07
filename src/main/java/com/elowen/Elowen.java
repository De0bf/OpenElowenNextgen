package com.elowen;

import com.elowen.commands.CommandManager;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.EventManager;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.files.FileManager;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.render.ClickGUIModule;
import com.elowen.ui.notification.NotificationManager;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.EntityWatcher;
import com.elowen.utils.LogUtils;
import com.elowen.utils.AttackTargetHelper;
import com.elowen.utils.TickTimeHelper;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaFonts;
import com.elowen.utils.renderer.GpuScreenCapture;
import com.elowen.utils.rotation.RotationManager;
import com.elowen.values.HasValueManager;
import com.elowen.values.ValueManager;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elowen implements ClientModInitializer {
   public static final String Q;
   public static final Logger q;
   private static Elowen X;
   public static float i;
   public static Queue o;
   public static int U;
   private EventManager O;
   private HasValueManager T;
   private ValueManager D;
   private ModuleManager h;
   private CommandManager u;
   private FileManager b;
   private NotificationManager I;
   private PacketUtils E;
   private AttackTargetHelper g;
   private EntityWatcher m;
   public void onInitializeClient() {
      X = this;
      q.info("Elowen client initializing...");
      this.T = new HasValueManager();
      this.D = new ValueManager();
      this.O = new EventManager();
      this.I = new NotificationManager();
      this.h = new ModuleManager();
      this.b = new FileManager();
      this.u = new CommandManager();
      this.O.I(this);
      this.O.I(this.I);
      this.O.I(this.u);
      this.O.I(new RotationManager());

      try {
         com.elowen.utils.renderer.SkijaFonts.M();
         q.info("Skija fonts loaded successfully.");
      } catch (Exception var4) {
         q.error("Failed to load Skija fonts!", var4);
      }

      this.E = new PacketUtils();
      this.g = new AttackTargetHelper();
      this.m = new EntityWatcher();
      this.O.I(this.E);
      this.O.I(this.g);
      this.O.I(this.m);
      this.b.U$V();
      Module var2 = this.h.A(ClickGUIModule.class);
      if (var2 != null) {
         var2.M(false);
      }

      q.info("Elowen client initialized.");
   }

   @EventTarget
   public void Z(com.elowen.events.impl.EventShutdown var1) {
      this.b.X$V();
      GpuScreenCapture.H();
      SkiaRenderManager.l$V();
      com.elowen.utils.LogUtils.W$V();
   }

   @EventTarget(0)
   public void D(EventTick var1) {
      if (var1.s$f() == EventType.PRE) {
         TickTimeHelper.S$V();
      }
   }

   public static Elowen S$Elowen() {
      return X;
   }

   public Minecraft I() {
      return Minecraft.getInstance();
   }

   public EventManager e() {
      return this.O;
   }

   public HasValueManager E$C() {
      return this.T;
   }

   public ValueManager n$E() {
      return this.D;
   }

   public ModuleManager q$ModuleManager() {
      return this.h;
   }

   public CommandManager U$R() {
      return this.u;
   }

   public FileManager q$S() {
      return this.b;
   }

   public NotificationManager Q() {
      return this.I;
   }

   public PacketUtils K() {
      return this.E;
   }

   public AttackTargetHelper d$X() {
      return this.g;
   }

   public EntityWatcher j$H() {
      return this.m;
   }

   static {
      Q = "elowen";
      q = LoggerFactory.getLogger("elowen");
      i = 1.0F;
      o = new ConcurrentLinkedQueue();
      U = 0;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
