package com.elowen.commands.impl;

import com.elowen.commands.Command;
import com.elowen.commands.CommandInfo;
import com.elowen.utils.FriendManager;
import com.elowen.utils.ChatUtils;
import com.elowen.values.HasValue;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;

@CommandInfo(V = "friend", Z = "Manage your friends list", C = "f")
public class CommandFriend extends Command {
   private static final String[] a = new String[]{"remove", "add", "Usage: .friend <playername> | .friend add/remove <playername>", "Added ", "add", "remove", " to your friends list.", " is not in your friends list.", "Usage: .friend <playername> | .friend add/remove <playername>", "Added ", "Removed ", " to your friends list.", " is already your friend.", " is already your friend.", " from your friends list."};
   @Override
   public void l(String[] var1) {
      int var11 = 0;
      label167: {
         boolean var2;
         Object var10;
         label155: {
            var2 = CommandBind.P();
            int var10000 = var1.length;
            byte var10001 = 1;
            if (!var2) {
               if (var10000 == 1) {
                  label145: {
                     String var3 = var1[0];
                     var10 = var3;
                     if (!var2) {
                        if (FriendManager.u$Z(var3)) {
                           ChatUtils.b(var3 + " is already your friend.");
                           if (!var2) {
                              break label145;
                           }

                           HasValue.d(HasValue.X$Z());
                        }

                        FriendManager.V(var3);
                        String[] var7 = a;
                        var10 = "Added " + var3 + " to your friends list.";
                     }

                     ChatUtils.b((String)var10);
                  }

                  if (!var2) {
                     return;
                  }
               }

               var10 = var1;
               if (var2) {
                  break label155;
               }

               var10000 = var1.length;
               var10001 = 2;
            }

            if (var10000 != var10001) {
               break label167;
            }

            var10 = var1;
         }

         String var4;
         String var8 = ((String[])var10)[0].toLowerCase();
         var4 = var1[1];
         String var5 = var8;
         byte var6 = -1;
         var11 = var5.hashCode();
         label120:
         if (!var2) {
            switch (var11) {
               case 96417:
                  var11 = var5.equals("add") ? 1 : 0;
                  if (var2) {
                     break label120;
                  }

                  if (var11 == 0) {
                     break;
                  }

                  var6 = 0;
                  if (!var2) {
                     break;
                  }
               case -934610812:
                  var11 = var5.equals("remove") ? 1 : 0;
                  if (var2) {
                     break label120;
                  }

                  if (var11 != 0) {
                     var6 = 1;
                  }
            }

            var11 = var6;
         }

         label114: {
            label161: {
               label112: {
                  label162: {
                     if (!var2) {
                        switch (var11) {
                           case 0:
                              var10 = var4;
                              if (var2) {
                                 break label162;
                              }

                              var11 = FriendManager.u$Z(var4) ? 1 : 0;
                              break;
                           case 1:
                              break label112;
                           default:
                              break label161;
                        }
                     }

                     if (var11 != 0) {
                        ChatUtils.b(var4 + " is already your friend.");
                        if (!var2) {
                           break label114;
                        }
                     }

                     FriendManager.V(var4);
                     var10 = "Added " + var4 + " to your friends list.";
                  }

                  ChatUtils.b((String)var10);
                  if (!var2) {
                     break label114;
                  }
               }

               var10 = var4;
               if (!var2) {
                  if (FriendManager.u$Z(var4)) {
                     FriendManager.u$V(var4);
                     ChatUtils.b("Removed " + var4 + " from your friends list.");
                     if (!var2) {
                        break label114;
                     }
                  }

                  var10 = var4 + " is not in your friends list.";
               }

               ChatUtils.b((String)var10);
               if (!var2) {
                  break label114;
               }
            }

            ChatUtils.b("Usage: .friend <playername> | .friend add/remove <playername>");
         }

         if (!var2) {
            return;
         }
      }

      ChatUtils.b("Usage: .friend <playername> | .friend add/remove <playername>");
   }

   @Override
   public String[] X(String[] var1) {
      boolean var2 = CommandBind.q$Z();
      if (var1.length == 1) {
         String[] var5 = a;
         ArrayList var6 = new ArrayList<>(List.of("add", "remove"));
         var6.addAll(B$List());
         return Y(var6, var1[0]);
      }

      if (var1.length == 2) {
         String var3 = var1[0].toLowerCase();
         if (var3.equals("add")) {
            return Y(B$List(), var1[1]);
         }

         if (var3.equals("remove")) {
            ArrayList var4 = new ArrayList(FriendManager.Q());
            var4.addAll(B$List());
            return Y(var4, var1[1]);
         }
      }

      return new String[0];
   }

   private static String[] Y(List<String> var0, String var1) {
      String var2 = var1.toLowerCase();
      return var0.stream().filter(s -> deobfLambda$matching$0(var2, s)).distinct().toArray(CommandFriend::deobfLambda$matching$1);
   }

   private static List B$List() {
      boolean var10000 = CommandBind.q$Z();
      ClientPacketListener var1 = Minecraft.getInstance().getConnection();
      boolean var0 = var10000;
      ClientPacketListener var2 = var1;
      if (var0) {
         if (var1 == null) {
            return List.of();
         }

         var2 = var1;
      }

      return var2.getOnlinePlayers().stream().map(CommandFriend::deobfLambda$onlinePlayers$0).filter(CommandFriend::deobfLambda$onlinePlayers$1).toList();
   }

   private static boolean deobfLambda$onlinePlayers$1(String var0) {
      boolean var1 = CommandBind.P();
      String var10000 = var0;
      if (!var1) {
         if (var0 == null) {
            return false;
         }

         var10000 = var0;
      }

      boolean var2 = var10000.isEmpty();
      return var1 ? var2 : !var2;
   }

   private static String deobfLambda$onlinePlayers$0(PlayerInfo var0) {
      return var0.getProfile().name();
   }

   private static String[] deobfLambda$matching$1(int var0) {
      return new String[var0];
   }

   private static boolean deobfLambda$matching$0(String var0, String var1) {
      boolean var2 = CommandBind.q$Z();
      return var1 != null && var1.toLowerCase().startsWith(var0);
   }

   private static com.elowen.exceptions.NoSuchModuleException b(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
