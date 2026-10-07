package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventRender2D;
import com.elowen.files.FileManager;
import com.elowen.mixin.accessors.KeyMappingAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.MouseUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

@ModuleInfo(R = "RePlay", M = Category.MOVEMENT, a = "Record camera & key states to a file, then replay them automatically")
public class RePlay extends Module {
   private static final int p = 4;
   private static final int r = 3;
   private static final File c;
   private static final String V;
   private static final int m = 12;
   private boolean Q = false;
   private boolean l = false;
   private boolean h = false;
   private boolean j = false;
   private int M = 0;
   private RePlay$TickFrame D = null;
   private final List R = new ArrayList();
   private List Y = new ArrayList();
   private static final String[] b = new String[]{"§aRePlay enabled! Mouse5: Record | Mouse4: Replay", " ticks from ", "§cFailed to load replay: ", "§aReplaying ", "§aRecording started! Mouse5: Stop & save -> ", "#elowen-replay v2", "§aRecorded ", "§aReplay finished!", "§cFailed to save replay: ", " ticks | Mouse5: Stop", "#elowen-replay v2", "§cReplay file is empty or missing: ", "replay.txt", "§aRecording... ", " | Mouse4: Stop", "§bRePlay | Mouse5: Record | Mouse4: Replay", " ticks -> ", "§eReplaying... ", " | Mouse4: Stop"};
   @Override
   public void h$V() {
      this.Q = false;
      this.l = false;
      this.h = false;
      this.j = false;
      this.M = 0;
      this.D = null;
      this.R.clear();
      this.Y.clear();
      com.elowen.utils.ChatUtils.b("§aRePlay enabled! Mouse5: Record | Mouse4: Replay");
   }

   @Override
   public void q$V() {
      boolean var1 = Scaffold.S$Z();
      if (this.Q) {
         this.G$V();
      }

      this.W$V();
      this.h = false;
      this.j = false;
      super.q$V();
   }

   @EventTarget(0)
   public void l(EventTick var1) {
      boolean var2 = Scaffold.k();
      if (var1.s$f() == EventType.PRE) {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var10000 = G;
         }

         if (var10000.level != null) {
            this.K();
            RePlay var3 = this;
            if (!var2) {
               if (!this.Q) {
                  return;
               }

               this.D = new RePlay$TickFrame();
               var3 = this;
            }

            var3.D.B = this.R(true);
            return;
         }
      }
   }

   @EventTarget(0)
   public void p(EventTick var1) {
      boolean var2 = Scaffold.k();
      if (var1.s$f() == EventType.POST) {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var10000 = G;
         }

         if (var10000.level != null) {
            RePlay var3 = this;
            if (!var2) {
               if (!this.Q) {
                  return;
               }

               var3 = this;
            }

            RePlay$TickFrame var4 = var3.D;
            if (!var2) {
               if (var3.D == null) {
                  return;
               }

               var4 = this.D;
            }

            var4.i = this.R(false);
            this.R.add(this.D);
            this.D = null;
            return;
         }
      }
   }

   @EventTarget(4)
   public void k(EventTick var1) {
      Object var5 = null;
      boolean var2 = Scaffold.k();
      if (var1.s$f() == EventType.PRE) {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var10000 = G;
         }

         if (var10000.level != null) {
            label61: {
               int var4 = ((this.l) ? 1 : 0);
               if (!var2) {
                  if (!this.l) {
                     return;
                  }

                  var5 = this;
                  if (var2) {
                     break label61;
                  }

                  var4 = this.M;
               }

               if (var4 >= this.Y.size()) {
                  return;
               }

               var5 = this.Y.get(this.M);
            }

            RePlay$Frame var3 = ((RePlay$TickFrame)var5).B;
            RePlay$Frame var6 = var3;
            if (!var2) {
               if (var3 == null) {
                  return;
               }

               var6 = var3;
            }

            var6.n$V();
            this.D(var3);
            return;
         }
      }
   }

   @EventTarget(4)
   public void W(EventTick var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1.s$f() == EventType.POST && G.player != null && G.level != null) {
         if (this.l && this.M < this.Y.size()) {
            RePlay$Frame var3 = ((RePlay$TickFrame)this.Y.get(this.M)).i;
            if (var3 != null) {
               var3.n$V();
               this.D(var3);
            }

            this.M++;
            if (this.M >= this.Y.size()) {
               this.W$V();
               com.elowen.utils.ChatUtils.b("§aReplay finished!");
            }
         }
      }
   }

   @EventTarget
   public void Y(EventRender2D var1) {
      boolean var2 = Scaffold.k();
      int var10000 = ((this.w()) ? 1 : 0);
      if (!var2) {
         if (var10000 == 0) {
            return;
         }

         var10000 = ((this.Q) ? 1 : 0);
      }

      String var3;
      label46: {
         if (!var2) {
            if (var10000 != 0) {
               var10000 = this.R.size();
               String[] var8 = b;
               var3 = "§aRecording... " + var10000 + " ticks | Mouse5: Stop";
               if (!var2) {
                  break label46;
               }
            }

            var10000 = ((this.l) ? 1 : 0);
         }

         label35: {
            if (!var2) {
               if (var10000 == 0) {
                  break label35;
               }

               var10000 = Math.min(this.M, this.Y.size());
            }

            int var10001 = this.Y.size();
            String[] var9 = b;
            var3 = "§eReplaying... " + var10000 + "/" + var10001 + " | Mouse4: Stop";
            if (!var2) {
               break label46;
            }
         }

         var3 = "§bRePlay | Mouse5: Record | Mouse4: Replay";
      }

      int var4 = G.getWindow().getGuiScaledWidth();
      int var5 = G.getWindow().getGuiScaledHeight();
      float var6 = var4 / 2.0F - G.font.width(var3) / 2.0F;
      float var7 = var5 / 2.0F + 20.0F;
      var1.e().text(G.font, var3, (int)var6, (int)var7, -1);
   }

   private void K() {
      boolean var1;
      boolean var3;
      boolean var4;
      label99: {
         label102: {
            var4 = Scaffold.k();
            boolean var2 = com.elowen.utils.MouseUtils.Y(4);
            var1 = var4;
            var3 = com.elowen.utils.MouseUtils.Y(3);
            var4 = var2;
            label97:
            if (!var1) {
               if (var2) {
                  var4 = this.h;
                  if (var1) {
                     break label97;
                  }

                  if (!this.h) {
                     this.h = true;
                     RePlay var5 = this;
                     if (!var1) {
                        if (this.Q) {
                           this.G$V();
                           if (!var1) {
                              break label102;
                           }
                        }

                        this.W$V();
                        var5 = this;
                     }

                     var5.o$V();
                     if (!var1) {
                        break label102;
                     }
                  }
               }

               var4 = var2;
            }

            if (var1) {
               break label99;
            }

            if (!var4) {
               this.h = false;
            }
         }

         var4 = var3;
      }

      label78:
      if (!var1) {
         if (var4) {
            var4 = this.j;
            if (var1) {
               break label78;
            }

            if (!this.j) {
               this.j = true;
               RePlay var6 = this;
               if (!var1) {
                  if (this.l) {
                     this.W$V();
                     if (!var1) {
                        return;
                     }
                  }

                  this.G$V();
                  var6 = this;
               }

               var6.V();
               if (!var1) {
                  return;
               }
            }
         }

         var4 = var3;
      }

      if (!var4) {
         this.j = false;
      }
   }

   private void o$V() {
      this.R.clear();
      this.D = null;
      this.Q = true;
      com.elowen.utils.ChatUtils.b("§aRecording started! Mouse5: Stop & save -> " + c.getName());
   }

   private void G$V() {
      int var10000 = ((Scaffold.k()) ? 1 : 0);
      this.Q = false;
      boolean var1 = (boolean)((var10000) != 0);
      RePlay var3 = this;
      if (!var1) {
         if (this.D != null) {
            this.R.add(this.D);
            this.D = null;
         }

         this.o(this.R);
         var3 = this;
      }

      var10000 = var3.R.size();
      String var10001 = c.getName();
      String[] var2 = b;
      com.elowen.utils.ChatUtils.b("§aRecorded " + var10000 + " ticks -> " + var10001);
   }

   private void V() {
      int var10000 = ((Scaffold.S$Z()) ? 1 : 0);
      this.Y = this.a$List();
      boolean var1 = (boolean)((var10000) != 0);
      var10000 = ((this.Y.isEmpty()) ? 1 : 0);
      if (var1) {
         if (var10000 != 0) {
            com.elowen.utils.ChatUtils.b("§cReplay file is empty or missing: " + c.getAbsolutePath());
            return;
         }

         this.l = true;
         this.M = 0;
         var10000 = this.Y.size();
      }

      String var10001 = c.getName();
      String[] var2 = b;
      com.elowen.utils.ChatUtils.b("§aReplaying " + var10000 + " ticks from " + var10001 + " | Mouse4: Stop");
   }

   private void W$V() {
      this.l = false;
      this.M = 0;
      this.r();
   }

   private RePlay$Frame R(boolean var1) {
      return new RePlay$Frame(
         var1,
         G.player.getYRot(),
         G.player.getXRot(),
         G.options.keyUp.isDown(),
         G.options.keyDown.isDown(),
         G.options.keyLeft.isDown(),
         G.options.keyRight.isDown(),
         G.options.keyJump.isDown(),
         G.options.keyShift.isDown(),
         G.options.keySprint.isDown(),
         G.options.keyUse.isDown(),
         G.options.keyAttack.isDown()
      );
   }

   private void D(RePlay$Frame var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player != null) {
         G.player.setYRot(var1.j);
         G.player.setXRot(var1.D);
         G.player.setOldRot();
      }
   }

   private void r() {
      i(G.options.keyUp, false);
      i(G.options.keyDown, false);
      i(G.options.keyLeft, false);
      i(G.options.keyRight, false);
      i(G.options.keyJump, false);
      i(G.options.keyShift, false);
      i(G.options.keySprint, false);
      i(G.options.keyUse, false);
      i(G.options.keyAttack, false);
   }

   static void i(KeyMapping var0, boolean var1) {
      ((KeyMappingAccessor)var0).elowen$setIsDown(var1);
   }

   private void o(List var1) {
      boolean var2 = Scaffold.S$Z();

      try {
         File var3 = c.getParentFile();
         if (var3 != null && !var3.exists()) {
            var3.mkdirs();
         }

         BufferedWriter var4 = Files.newBufferedWriter(c.toPath(), StandardCharsets.UTF_8);

         try {
            String[] var8 = b;
            var4.write("#elowen-replay v2");
            var4.newLine();
            Iterator var5 = var1.iterator();
            while (var5.hasNext()) {
               RePlay$TickFrame var6 = (RePlay$TickFrame)var5.next();
               if (var6.B != null) {
                  var4.write(var6.B.s$String());
                  var4.newLine();
               }

               if (var6.i != null) {
                  var4.write(var6.i.s$String());
                  var4.newLine();
               }
            }

            var4.flush();
         } finally {
            var4.close();
         }
      } catch (IOException var12) {
         com.elowen.utils.ChatUtils.b("§cFailed to save replay: " + var12.getMessage());
      }
   }

   private List a$List() {
      List var2 = new ArrayList();
      File var3 = RePlay.c;
      if (!var3.exists()) {
         return var2;
      }

      try (BufferedReader var4 = Files.newBufferedReader(var3.toPath(), StandardCharsets.UTF_8)) {
         RePlay$TickFrame var6 = null;
         String var5;
         while ((var5 = var4.readLine()) != null) {
            if (!var5.isEmpty() && !var5.startsWith("#")) {
               RePlay$Frame var7 = RePlay$Frame.G(var5);
               if (var7 != null) {
                  if (var7.z) {
                     if (var6 != null) {
                        var2.add(var6);
                     }

                     var6 = new RePlay$TickFrame();
                     var6.B = var7;
                  } else {
                     if (var6 == null) {
                        var6 = new RePlay$TickFrame();
                     }

                     var6.i = var7;
                     var2.add(var6);
                     var6 = null;
                  }
               }
            }
         }

         if (var6 != null) {
            var2.add(var6);
         }
      } catch (IOException var9) {
         com.elowen.utils.ChatUtils.b("§cFailed to load replay: " + var9.getMessage());
      }

      return var2;
   }

   public static String y(boolean var0) {
      return var0 ? "1" : "0";
   }

   static Minecraft A$Minecraft() {
      return G;
   }

   static Minecraft u$Minecraft() {
      return G;
   }

   static Minecraft J$Minecraft() {
      return G;
   }

   static Minecraft a$Minecraft() {
      return G;
   }

   static Minecraft s$Minecraft() {
      return G;
   }

   static Minecraft I() {
      return G;
   }

   static Minecraft Q() {
      return G;
   }

   static Minecraft g$Minecraft() {
      return G;
   }

   static Minecraft f$Minecraft() {
      return G;
   }

   static {
      V = "#elowen-replay v2";
      c = new File(FileManager.u, "replay.txt");
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
