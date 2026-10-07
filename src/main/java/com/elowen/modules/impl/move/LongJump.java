package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.MouseUtils;
import com.elowen.utils.MoveUtils;
import com.elowen.utils.rotation.Rotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ModuleInfo(R = "LongJump", M = Category.MOVEMENT, a = "Allows you to use fireball longjump")
public class LongJump extends Module {
   public static Rotation r;
   private boolean m = false;
   private boolean j = false;
   private int d = 0;
   private int Y = -1;
   private boolean V = false;
   private boolean z = false;
   private boolean y = false;
   private boolean q = false;
   private boolean B = false;
   private long E = 0L;
   private int P = 0;
   private int C = 0;
   private int X = 0;
   private int p = 0;
   private final List U = new ArrayList();
   private final LinkedBlockingQueue I = new LinkedBlockingQueue();
   private static final String[] b = new String[]{"§cNo intercepted packets", " received", "§aReleasing ", "§bWaiting for input | Mouse4: Jump & use fireball | Mouse5: Release", "§aUsing fireball #", "§eFireball #", "§eIntercepting: %d packets | Time: %.1fs | Press Mouse5 (%d/%d)", " started, initial count: ", ", starting packet interception", "§eReceived #", ", waiting for next input", "§aAll released! Stopping LongJump.", "§aJumping for fireball #", " -> ", "§cNo FireBall!", "§aLongJump enabled! Press Mouse4 to jump & use fireball, Mouse5 to release each knockback", "§eFireball #", "§ePacket interception started, press Mouse5 to release each", "§cNo more fireballs available!", "§eStarting fireball usage #", "§e", " used! Count: ", "§cAll already released"};
   private void T$V() {
      boolean var1 = Scaffold.k();

      while (!this.I.isEmpty()) {
         try {
            Packet var2 = (Packet)this.I.poll();
            if (var2 != null && G.getConnection() != null) {
               var2.handle(G.getConnection());
            }
         } catch (Exception var3) {
            var3.printStackTrace();
            if (var1) {
               break;
            }
         }
      }
   }

   private void P(int var1) {
      boolean var2 = Scaffold.k();
      int var10000 = var1;
      if (!var2) {
         if (var1 >= this.U.size()) {
            return;
         }

         var10000 = (Integer)this.U.get(var1);
      }

      int var3 = var10000;
      int var4 = 0;

      int var10001;
      label66: {
         while (!this.I.isEmpty()) {
            var10000 = var4;
            var10001 = var3;
            if (var2 || var2) {
               break label66;
            }

            if (var4 > var3) {
               break;
            }

            try {
               Packet var5 = (Packet)this.I.poll();
               if (!var2) {
                  if (var5 != null && G.getConnection() != null) {
                     var5.handle(G.getConnection());
                  }

                  var4++;
               }
            } catch (Exception var6) {
               var6.printStackTrace();
               if (var2) {
                  break;
               }
            }
         }

         var10000 = var1;
         var10001 = 1;
      }

      int var7 = var10000 + var10001;

      while (var7 < this.U.size()) {
         this.U.set(var7, (Integer)this.U.get(var7) - (var3 + 1));
         var7++;
         if (var2) {
            break;
         }
      }
   }

   private int D$h() {
      int var10000 = ((Scaffold.S$Z()) ? 1 : 0);
      int var2 = 0;
      boolean var1 = (boolean)((var10000) != 0);

      while (true) {
         if (var2 < 9) {
            ItemStack var3 = G.player.getInventory().getItem(var2);
            if (var1) {
               var10000 = ((var3.isEmpty()) ? 1 : 0);
               if (!var1) {
                  break;
               }

               if (var10000 == 0 && var3.getItem() == Items.FIRE_CHARGE) {
                  return var2;
               }

               var2++;
            }

            if (var1) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   private int t() {
      boolean var10000 = Scaffold.k();
      int var2 = 0;
      boolean var1 = var10000;
      int var3 = 0;

      while (var3 < 9) {
         ItemStack var4 = G.player.getInventory().getItem(var3);
         if (!var1) {
            if (var4.getItem() == Items.FIRE_CHARGE) {
               var2 += var4.getCount();
            }

            var3++;
         }

         if (var1) {
            break;
         }
      }

      return var2;
   }

   private int j$I() {
      int var10000 = ((Scaffold.S$Z()) ? 1 : 0);
      int var2 = this.D$h();
      boolean var1 = (boolean)((var10000) != 0);
      var10000 = var2;
      if (var1) {
         if (var2 == -1) {
            com.elowen.utils.ChatUtils.b("§cNo FireBall!");
            this.M(false);
         }

         var10000 = var2;
      }

      return var10000;
   }

   @Override
   public void h$V() {
      this.T$V();
      this.d = 0;
      this.j = true;
      this.Y = -1;
      this.m = false;
      this.V = false;
      this.y = false;
      r = null;
      this.z = false;
      this.q = false;
      this.B = false;
      this.E = 0L;
      this.P = 0;
      this.C = 0;
      this.X = 0;
      this.p = 0;
      this.U.clear();
      com.elowen.utils.ChatUtils.b("§aLongJump enabled! Press Mouse4 to jump & use fireball, Mouse5 to release each knockback");
   }

   @Override
   public void q$V() {
      net.minecraft.client.Minecraft var3 = null;
      boolean var10000 = Scaffold.k();
      this.T$V();
      boolean var1 = var10000;
      LongJump var2 = this;
      if (!var1) {
         label24: {
            if (this.Y != -1) {
               var3 = G;
               if (var1) {
                  break label24;
               }

               if (G.player != null) {
                  G.player.getInventory().setSelectedSlot(this.Y);
               }
            }

            G.options.keyUse.setDown(false);
            var3 = G;
         }

         var3.options.keyJump.setDown(false);
         r = null;
         this.y = false;
         this.z = false;
         this.q = false;
         this.B = false;
         this.E = 0L;
         this.P = 0;
         this.C = 0;
         this.X = 0;
         this.p = 0;
         this.U.clear();
         var2 = this;
      }

      var2.q$V();
   }

   @EventTarget
   public void V(EventUpdate var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.w()) {
         if (this.z) {
            this.M(false);
         }

         if (this.j) {
            if (!com.elowen.utils.MoveUtils.V()) {
               this.m = true;
            }

            this.j = false;
         }

         label122: {
            boolean var3 = com.elowen.utils.MouseUtils.Y(3);
            if (var3 && !this.q) {
               this.q = true;
               if (this.y || this.d != 0) {
                  break label122;
               }

               int var4 = this.j$I();
               if (var4 != -1) {
                  this.Y = G.player.getInventory().getSelectedSlot();
                  G.player.getInventory().setSelectedSlot(var4);
                  this.d = 1;
                  int var10000 = this.P + 1;
                  String[] var5 = b;
                  com.elowen.utils.ChatUtils.b("§eStarting fireball usage #" + var10000);
               }
            }

            if (!var3) {
               this.q = false;
            }
         }

         boolean var6 = com.elowen.utils.MouseUtils.Y(4);
         if (var6 && !this.B) {
            this.B = true;
            if (this.V && this.p < this.C) {
               com.elowen.utils.ChatUtils.b("§aReleasing " + (this.p + 1) + "/" + this.C);
               this.P(this.p);
               this.p++;
               if (this.p < this.C) {
                  return;
               }

               com.elowen.utils.ChatUtils.b("§aAll released! Stopping LongJump.");
               this.V = false;
               this.M(false);
            }

            if (!this.V) {
               com.elowen.utils.ChatUtils.b("§cNo intercepted packets");
               this.M(false);
            }

            com.elowen.utils.ChatUtils.b("§cAll already released");
         }

         if (!var6) {
            this.B = false;
         }
      }
   }

   @EventTarget
   public void q(EventRender2D var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.w()) {
         int var3 = G.getWindow().getGuiScaledWidth();
         int var4 = G.getWindow().getGuiScaledHeight();
         if (this.V) {
            int var6 = this.I.size();
            long var7 = System.currentTimeMillis();
            long var9 = var7 - this.E;
            String[] var11 = b;
            String var5 = String.format("§eIntercepting: %d packets | Time: %.1fs | Press Mouse5 (%d/%d)", var6, (float)var9 / 1000.0F, this.p, this.C);
         }

         if (this.y) {
            String[] var16 = b;
            String var12 = "§aUsing fireball #" + this.P;
         }

         String var13 = "§bWaiting for input | Mouse4: Jump & use fireball | Mouse5: Release";
         float var14 = var3 / 2.0F - G.font.width(var13) / 2.0F;
         float var15 = var4 / 2.0F + 20.0F;
         var1.e().text(G.font, var13, (int)var14, (int)var15, -1);
      }
   }

   @EventTarget
   public void T(EventPacket var1) {
      EventPacket var7 = null;
      boolean var2 = Scaffold.k();
      int var10000 = ((this.w()) ? 1 : 0);
      if (!var2) {
         if (var10000 != 0 && G.level != null) {
            label142: {
               if (this.V) {
                  var7 = var1;
                  if (var2) {
                     break label142;
                  }

                  if (var1.M() == EventType.RECEIVE) {
                     EventPacket var8 = var1;
                     if (!var2) {
                        if (var1.c$Z()) {
                           return;
                        }

                        var8 = var1;
                     }

                     label151: {
                        Packet var3 = var8.R$Packet();
                        var10000 = ((var3 instanceof ClientboundPlayerPositionPacket) ? 1 : 0);
                        if (!var2) {
                           if (((var10000) != 0)) {
                              this.z = true;
                              var1.c(true);
                              if (!var2) {
                                 break label151;
                              }
                           }

                           var10000 = ((var3 instanceof ClientboundSetEntityMotionPacket) ? 1 : 0);
                        }

                        label134:
                        if (!var2) {
                           if (((var10000) != 0)) {
                              ClientboundSetEntityMotionPacket var4 = (ClientboundSetEntityMotionPacket)var3;
                              var10000 = var4.id();
                              if (var2) {
                                 break label134;
                              }

                              if (var10000 == com.elowen.utils.PlayerUtils.S$I()) {
                                 this.C++;
                                 this.U.add(this.I.size());
                                 G.execute(this::deobfLambda$onPacket$0);
                              }
                           }

                           var1.c(true);
                           this.I.add(var3);
                        }
                     }

                     if (!var2) {
                        return;
                     }
                  }
               }

               var7 = var1;
            }

            label120: {
               Packet var6 = var7.R$Packet();
               Packet var11 = var6;
               if (!var2) {
                  if (!(var6 instanceof ClientboundSetEntityMotionPacket)) {
                     break label120;
                  }

                  var11 = var6;
               }

               ClientboundSetEntityMotionPacket var5 = (ClientboundSetEntityMotionPacket)var11;
               label115:
               if (var1.M() == EventType.RECEIVE) {
                  var10000 = var5.id();
                  if (!var2) {
                     if (var10000 != com.elowen.utils.PlayerUtils.S$I()) {
                        break label115;
                     }

                     var10000 = this.P;
                  }

                  if (!var2) {
                     if (var10000 <= 0) {
                        break label115;
                     }

                     var10000 = ((this.V) ? 1 : 0);
                  }

                  if (!var2) {
                     if (var10000 != 0) {
                        break label115;
                     }

                     this.C++;
                     this.U.add(this.I.size());
                     G.execute(this::deobfLambda$onPacket$1);
                     var1.c(true);
                     this.I.add(var1.R$Packet());
                  }

                  this.V = true;
                  this.E = System.currentTimeMillis();
                  G.execute(LongJump::deobfLambda$onPacket$2);
               }
            }

            if (!var2) {
               return;
            }
         }

         var10000 = ((this.V) ? 1 : 0);
      }

      if (((var10000) != 0)) {
         G.execute(this::deobfLambda$onPacket$3);
      }
   }

   @EventTarget
   public void A(EventMotion var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.w()) {
         if (var1.Q() == EventType.PRE) {
            if (this.d <= 0) {
               return;
            }

            if (this.d == 1) {
               this.P++;
               com.elowen.utils.ChatUtils.b("§aJumping for fireball #" + this.P);
               G.options.keyJump.setDown(true);
               if (!this.m) {
                  float var3 = G.player.getYRot() - 180.0F;
                  float var4 = 88.0F;
               }

               float var8 = G.player.getYRot();
               float var11 = 90.0F;
               r = new Rotation(var8, var11);
            }

            if (this.d >= 2) {
               this.d = 0;
               int var9 = this.j$I();
               if (var9 != -1) {
                  G.player.getInventory().setSelectedSlot(var9);
                  this.X = this.t();
                  G.options.keyUse.setDown(true);
                  this.y = true;
                  com.elowen.utils.ChatUtils.b("§eFireball #" + this.P + " started, initial count: " + this.X);
               }

               this.M(false);
            }

            if (this.d == 0) {
               return;
            }

            this.d++;
         }

         if (this.y) {
            int var10 = this.t();
            if (var10 < this.X) {
               G.options.keyUse.setDown(false);
               G.options.keyJump.setDown(false);
               r = null;
               this.y = false;
               int var5 = var10;
               int var6 = this.X;
               int var7 = this.P;
               com.elowen.utils.ChatUtils.b("§eFireball #" + var7 + " used! Count: " + var6 + " -> " + var5 + ", waiting for next input");
            }

            if (this.D$h() == -1) {
               G.options.keyUse.setDown(false);
               G.options.keyJump.setDown(false);
               r = null;
               this.y = false;
               com.elowen.utils.ChatUtils.b("§cNo more fireballs available!");
            }
         }
      }
   }

   private void deobfLambda$onPacket$3() {
      this.T$V();
      this.V = false;
   }

   private static void deobfLambda$onPacket$2() {
      com.elowen.utils.ChatUtils.b("§ePacket interception started, press Mouse5 to release each");
   }

   private void deobfLambda$onPacket$1() {
      String[] var1 = b;
      com.elowen.utils.ChatUtils.b("§eReceived #" + this.C + ", starting packet interception");
   }

   private void deobfLambda$onPacket$0() {
      String[] var1 = b;
      com.elowen.utils.ChatUtils.b("§e" + this.C + " received");
   }

   static {
      r = null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
