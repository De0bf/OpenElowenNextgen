package com.elowen.modules.impl.move;

final class RePlay$Frame {
   final boolean z;
   public final float j;
   public final float D;
   private final boolean G;
   private final boolean m;
   private final boolean s;
   private final boolean y;
   private final boolean x;
   private final boolean W;
   private final boolean E;
   private final boolean F;
   private final boolean K;
   private static final String[] a = new String[]{"PRE", "POST", "POST", "PRE"};
   public RePlay$Frame(
      boolean var1,
      float var2,
      float var3,
      boolean var4,
      boolean var5,
      boolean var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      boolean var12
   ) {
      this.z = var1;
      this.j = var2;
      this.D = var3;
      this.G = var4;
      this.m = var5;
      this.s = var6;
      this.y = var7;
      this.x = var8;
      this.W = var9;
      this.E = var10;
      this.F = var11;
      this.K = var12;
   }

   static RePlay$Frame G(String var0) {
      int var10000 = ((Scaffold.k()) ? 1 : 0);
      String[] var2 = var0.split(",");
      boolean var1 = (boolean)((var10000) != 0);
      var10000 = var2.length;
      if (!var1) {
         if (var10000 < 12) {
            return null;
         }

         String var8 = var2[0];
         String[] var5 = a;
         var10000 = ((var8.equals("PRE")) ? 1 : 0);
      }

      int var3;
      label52: {
         if (!var1) {
            if (var10000 != 0) {
               var3 = 1;
               if (!var1) {
                  break label52;
               }
            }

            var10000 = ((var2[0].equals("POST")) ? 1 : 0);
         }

         if (!var1) {
            if (var10000 == 0) {
               return null;
            }

            var10000 = 0;
         }

         var3 = var10000;
         if (var1) {
            return null;
         }
      }

      try {
         return new RePlay$Frame(
            (boolean)((var3) != 0),
            Float.parseFloat(var2[1]),
            Float.parseFloat(var2[2]),
            var2[3].equals("1"),
            var2[4].equals("1"),
            var2[5].equals("1"),
            var2[6].equals("1"),
            var2[7].equals("1"),
            var2[8].equals("1"),
            var2[9].equals("1"),
            var2[10].equals("1"),
            var2[11].equals("1")
         );
      } catch (NumberFormatException var6) {
         return null;
      }
   }

   public String s$String() {
      return (this.z ? "PRE" : "POST")
         + ","
         + this.j
         + ","
         + this.D
         + ","
         + RePlay.y(this.G)
         + ","
         + RePlay.y(this.m)
         + ","
         + RePlay.y(this.s)
         + ","
         + RePlay.y(this.y)
         + ","
         + RePlay.y(this.x)
         + ","
         + RePlay.y(this.W)
         + ","
         + RePlay.y(this.E)
         + ","
         + RePlay.y(this.F)
         + ","
         + RePlay.y(this.K);
   }

   public void n$V() {
      RePlay.i(RePlay.A$Minecraft().options.keyUp, this.G);
      RePlay.i(RePlay.u$Minecraft().options.keyDown, this.m);
      RePlay.i(RePlay.J$Minecraft().options.keyLeft, this.s);
      RePlay.i(RePlay.a$Minecraft().options.keyRight, this.y);
      RePlay.i(RePlay.s$Minecraft().options.keyJump, this.x);
      RePlay.i(RePlay.I().options.keyShift, this.W);
      RePlay.i(RePlay.Q().options.keySprint, this.E);
      RePlay.i(RePlay.g$Minecraft().options.keyUse, this.F);
      RePlay.i(RePlay.f$Minecraft().options.keyAttack, this.K);
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }

   static {
   }
}
