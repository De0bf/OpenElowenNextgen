package com.elowen.modules;

import com.elowen.Elowen;
import com.elowen.modules.impl.render.ClickGUIModule;
import com.elowen.modules.impl.render.InterFace;
import com.elowen.ui.notification.NotificationLevel;
import com.elowen.ui.notification.Notification;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.values.HasValue;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;

public class Module extends HasValue {
   protected static final Minecraft G;
   public static boolean k;
   private final SmoothAnimationTimer w = new SmoothAnimationTimer(100.0F);
   private String H;
   private String A;
   private String s;
   private String W;
   private Category g;
   private boolean N;
   private int n = 0;
   private int a;
   private static boolean u;
   private static final String[] ab = new String[]{" Enabled!", " Disabled!"};
   public Module(String var1, String var2, Category var3) {
      boolean var10000 = l$Z();
      this.H = var1;
      this.s = var2;
      this.g = var3;
      super.j(var1);
      this.P();
      boolean var4 = var10000;
      if (!HasValue.x()) {
         H(!var4);
      }
   }

   public void X(String var1) {
      boolean var2 = O();
      if (var1 == null) {
         this.W = null;
         k = true;
      }

      if (!var1.equals(this.W)) {
         this.W = var1;
         k = true;
      }
   }

   private void P() {
      StringBuilder var2 = new StringBuilder();
      boolean var10000 = l$Z();
      char[] var3 = this.H.toCharArray();
      boolean var1 = var10000;
      int var4 = 0;

      while (true) {
         if (var4 < var3.length - 1) {
            if (var1) {
               break;
            }

            label35: {
               label34: {
                  var10000 = Character.isLowerCase(var3[var4]);
                  if (!var1) {
                     if (!var10000) {
                        break label34;
                     }

                     var10000 = Character.isUpperCase(var3[var4 + 1]);
                  }

                  if (var10000) {
                     var2.append(var3[var4]).append(" ");
                     if (!var1) {
                        break label35;
                     }
                  }
               }

               var2.append(var3[var4]);
            }

            var4++;
            if (!var1) {
               continue;
            }
         }

         var2.append(var3[var3.length - 1]);
         this.A = var2.toString();
         break;
      }
   }

   protected void Z() {
      boolean var1 = O();
      if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
         ModuleInfo var2 = this.getClass().getAnnotation(ModuleInfo.class);
         this.H = var2.R();
         this.s = var2.a();
         this.g = var2.M();
         super.j(this.H);
         this.P();
         Elowen.S$Elowen().n$E().K(this);
      }
   }

   public void h$V() {
   }

   public void q$V() {
   }

   public void M(boolean var1) {
      boolean var10000 = O();
      k = true;
      boolean var2 = var10000;

      try {
         Elowen var3 = Elowen.S$Elowen();
         var10000 = var1;
         if (var2) {
            if (var1) {
               this.N = true;
               var3.e().I(this);
               this.h$V();
               if (this instanceof ClickGUIModule) {
                  return;
               }

               label64: {
                  InterFace var4 = InterFace.q$InterFace();
                  InterFace var11 = var4;
                  if (var2) {
                     if (var4 == null) {
                        break label64;
                     }

                     var11 = var4;
                  }

                  if (var11.P.w()) {
                     G.player.playSound(SoundEvents.WOODEN_BUTTON_CLICK_ON, 0.5F, 1.3F);
                  }
               }

               String[] var6 = ab;
               Notification var5 = new Notification(NotificationLevel.SUCCESS, this.H + " Enabled!", 3000L);
               var3.Q().m(var5);
               if (var2) {
                  return;
               }
            }

            this.N = false;
            var3.e().R(this);
            this.q$V();
            var10000 = this instanceof ClickGUIModule;
         }

         if (!var10000) {
            label51: {
               InterFace var8 = InterFace.q$InterFace();
               InterFace var12 = var8;
               if (var2) {
                  if (var8 == null) {
                     break label51;
                  }

                  var12 = var8;
               }

               if (var12.P.w()) {
                  G.player.playSound(SoundEvents.WOODEN_BUTTON_CLICK_OFF, 0.5F, 0.8F);
               }
            }

            Notification var9 = new Notification(NotificationLevel.ERROR, this.H + " Disabled!", 3000L);
            var3.Q().m(var9);
         }
      } catch (Exception var7) {
      }
   }

   public void R$V() {
      boolean var1 = O();
      this.M(!this.N);
   }

   public SmoothAnimationTimer l$M() {
      return this.w;
   }

   @Override
   public String i() {
      return this.H;
   }

   public String o$String() {
      return this.A;
   }

   public String D() {
      return this.s;
   }

   public String W$String() {
      return this.W;
   }

   public Category C() {
      return this.g;
   }

   public boolean w() {
      return this.N;
   }

   public int A$I() {
      return this.n;
   }

   public int U$I() {
      return this.a;
   }

   public Module() {
   }

   public void k(int var1) {
      this.n = var1;
   }

   public void V(int var1) {
      this.a = var1;
   }

   static {
      H(true);
      G = Minecraft.getInstance();
      k = true;
   }

   public static void H(boolean var0) {
      u = var0;
   }

   public static boolean O() {
      return u;
   }

   public static boolean l$Z() {
      return !O();
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}
