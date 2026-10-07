package com.elowen.commands.impl;

import com.elowen.Elowen;
import com.elowen.commands.Command;
import com.elowen.commands.CommandInfo;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Module;
import com.elowen.utils.ChatUtils;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;

@CommandInfo(V = "bind", Z = "Bind a command to a key", C = "b")
public class CommandBind extends Command {
   private static boolean E;
   private static final String[] a = new String[]{"Usage: .bind <module> [key]", " to.", "Invalid key.", " to ", "none", "Invalid module.", "Unbound ", "key.keyboard.", "Invalid module.", "Bound ", "Press a key to bind "};
   @Override
   public void l(String[] var1) {
      label121: {
         boolean var2;
         String[] var13;
         label122: {
            var2 = P();
            int var10000 = var1.length;
            byte var10001 = 1;
            if (!var2) {
               if (var10000 == 1) {
                  String var3 = var1[0];

                  try {
                     label111: {
                        label110: {
                           Module var4 = Elowen.S$Elowen().q$ModuleManager().k(var3);
                           if (!var2) {
                              if (var4 == null) {
                                 break label110;
                              }

                              com.elowen.utils.ChatUtils.b("Press a key to bind " + var3 + " to.");
                              Elowen.S$Elowen().e().I(new CommandBind$1(this, var4, var3));
                           }

                           if (!var2) {
                              break label111;
                           }
                        }

                        com.elowen.utils.ChatUtils.b("Invalid module.");
                     }
                  } catch (NoSuchModuleException var9) {
                     String[] var7 = a;
                     com.elowen.utils.ChatUtils.b("Invalid module.");
                  }

                  if (!var2) {
                     return;
                  }
               }

               var13 = var1;
               if (var2) {
                  break label122;
               }

               var10000 = var1.length;
               var10001 = 2;
            }

            if (var10000 != var10001) {
               break label121;
            }

            var13 = var1;
         }

         String var10 = var13[0];
         String var11 = var1[1];

         try {
            label91: {
               Module var5 = Elowen.S$Elowen().q$ModuleManager().k(var10);
               if (var5 != null) {
                  String var14 = var11;
                  if (!var2) {
                     if (var11.equalsIgnoreCase("none")) {
                        var5.V(InputConstants.UNKNOWN.getValue());
                        com.elowen.utils.ChatUtils.b("Unbound " + var10 + ".");
                        Elowen.S$Elowen().q$S().X$V();
                        if (!var2) {
                           break label91;
                        }
                     }

                     var14 = "key.keyboard." + var11.toLowerCase();
                  }

                  label87: {
                     label86: {
                        Key var6 = InputConstants.getKey(var14);
                        if (!var2) {
                           if (var6 == InputConstants.UNKNOWN) {
                              break label86;
                           }

                           var5.V(var6.getValue());
                           com.elowen.utils.ChatUtils.b("Bound " + var10 + " to " + var11.toUpperCase() + ".");
                           Elowen.S$Elowen().q$S().X$V();
                        }

                        if (!var2) {
                           break label87;
                        }
                     }

                     com.elowen.utils.ChatUtils.b("Invalid key.");
                  }

                  if (!var2) {
                     break label91;
                  }
               }

               com.elowen.utils.ChatUtils.b("Invalid module.");
            }
         } catch (NoSuchModuleException var8) {
            String[] var12 = a;
            com.elowen.utils.ChatUtils.b("Invalid module.");
         }

         if (!var2) {
            return;
         }
      }

      com.elowen.utils.ChatUtils.b("Usage: .bind <module> [key]");
   }

   @Override
   public String[] X(String[] var1) {
      boolean var2 = P();
      String[] var10000 = var1;
      if (!var2) {
         if (var1.length > 1) {
            return new String[0];
         }

         var10000 = ((java.util.List<Module>)Elowen.S$Elowen().q$ModuleManager().i()).stream().map(m -> ((Module)m).i()).filter(s -> deobfLambda$onTab$0(var1, s)).toArray(CommandBind::deobfLambda$onTab$1);
      }

      return var10000;
   }

   static Key I(int var0) {
      boolean var1 = q$Z();
      return var0 >= 0 && var0 <= 8 ? Type.MOUSE.getOrCreate(var0) : Type.KEYBOARD.getOrCreate(var0);
   }

   private static String[] deobfLambda$onTab$1(int var0) {
      return new String[var0];
   }

   private static boolean deobfLambda$onTab$0(String[] var0, String var1) {
      boolean var2 = P();
      String var10000 = var1.toLowerCase();
      String[] var10001 = var0;
      if (!var2) {
         if (var0.length == 0) {
            return var10000.startsWith("");
         }

         var10001 = var0;
      }

      return var10000.startsWith(var10001[0].toLowerCase());
   }

   public static void H(boolean var0) {
      E = var0;
   }

   public static boolean q$Z() {
      return E;
   }

   public static boolean P() {
      return !q$Z();
   }

   private static NoSuchModuleException b(NoSuchModuleException var0) {
      return var0;
   }

   static {
      H(true);
   }
}
