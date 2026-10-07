package com.elowen.utils.renderer;

import com.elowen.exceptions.NoSuchModuleException;
import io.github.humbleui.skija.Typeface;
import java.util.Arrays;

public final class SkijaColoredText {
   private static final int[] t = new int[128];

   private SkijaColoredText() {
   }

   public static int b(char var0) {
      boolean var1 = SkijaRenderer.L();
      if (var0 >= 'A' && var0 <= 'F') {
         var0 = (char)(var0 - 'A' + 97);
      }

      return var0 < t.length ? t[var0] : -1;
   }

   public static String u$String(String var0) {
      char var8 = (char)0;
      boolean var1 = SkijaRenderer.x();
      String var10000 = var0;
      if (!var1) {
         if (var0 != null) {
            if (var1) {
               return var0;
            }

            if (!var0.isEmpty()) {
               StringBuilder var2 = new StringBuilder(var0.length());
               int var3 = 0;
               int var4 = var0.length();

               while (true) {
                  if (var3 < var4) {
                     var10000 = var0;
                     if (var1) {
                        break;
                     }

                     char var5;
                     label91: {
                        label92: {
                           int var10001;
                           var5 = var0.charAt(var3);
                           var8 = var5;
                           var10001 = 167;
                           label71:
                           if (!var1) {
                              if (var5 != 167) {
                                 var8 = var5;
                                 var10001 = 38;
                                 if (var1) {
                                    break label71;
                                 }

                                 if (var5 != '&') {
                                    break label91;
                                 }
                              }

                              var8 = ((char)(var3 + 1));
                              if (var1) {
                                 break label92;
                              }

                              var10001 = var4;
                           }

                           if (var8 >= var10001) {
                              break label91;
                           }

                           var8 = var0.charAt(var3 + 1);
                        }

                        int var6 = var8;
                        if (!var1) {
                           if (var6 == 120) {
                              var3 += 14;
                              if (!var1) {
                                 continue;
                              }
                           }

                           var3 += 2;
                        }

                        if (!var1) {
                           continue;
                        }
                     }

                     var2.append(var5);
                     var3++;
                     if (!var1) {
                        continue;
                     }
                  }

                  var10000 = var2.toString();
                  break;
               }

               return var10000;
            }
         }

         var10000 = "";
      }

      return var10000;
   }

   public static float V(String var0, Typeface var1, float var2) {
      boolean var3 = SkijaRenderer.x();
      if (var1 != null) {
         String var10000 = var0;
         if (!var3) {
            if (var0 == null) {
               return 0.0F;
            }

            var10000 = u$String(var0);
         }

         return SkijaFonts.K(var10000, var1, var2);
      } else {
         return 0.0F;
      }
   }

   public static int S(String var0, int var1) {
      boolean var2 = SkijaRenderer.x();
      int var10000 = var1 + 13;
      if (!var2) {
         if (var10000 >= var0.length()) {
            return -1;
         }

         var10000 = Q(var0.charAt(var1 + 3));
      }

      int var3 = var10000;
      int var4 = Q(var0.charAt(var1 + 5));
      int var5 = Q(var0.charAt(var1 + 7));
      int var6 = Q(var0.charAt(var1 + 9));
      int var7 = Q(var0.charAt(var1 + 11));
      int var8 = Q(var0.charAt(var1 + 13));
      var10000 = var3;
      if (!var2) {
         if (var3 >= 0) {
            if (var2) {
               return var4;
            }

            if (var4 >= 0) {
               if (var2) {
                  return var5;
               }

               if (var5 >= 0) {
                  if (var2) {
                     return var6;
                  }

                  if (var6 >= 0) {
                     if (var2) {
                        return var7;
                     }

                     label65:
                     if (var7 >= 0) {
                        var10000 = var8;
                        if (!var2) {
                           if (var8 < 0) {
                              break label65;
                           }

                           var10000 = (var3 << 4 | var4) << 16 | (var5 << 4 | var6) << 8 | var7 << 4 | var8;
                        }

                        return var10000;
                     }
                  }
               }
            }
         }

         var10000 = -1;
      }

      return var10000;
   }

   private static int Q(char var0) {
      boolean var1 = SkijaRenderer.L();
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else if (var0 >= 'a' && var0 <= 'f') {
         return var0 - 97 + 10;
      } else {
         return var0 >= 65 && var0 <= 70 ? var0 - 65 + 10 : -1;
      }
   }

   static {
      Arrays.fill(t, -1);
      t[48] = 0;
      t[49] = 170;
      t[50] = 43520;
      t[51] = 43690;
      t[52] = 11141120;
      t[53] = 11141290;
      t[54] = 16755200;
      t[55] = 11184810;
      t[56] = 5592405;
      t[57] = 5592575;
      t[97] = 5635925;
      t[98] = 5636095;
      t[99] = 16733525;
      t[100] = 16733695;
      t[101] = 16777045;
      t[102] = 16777215;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
