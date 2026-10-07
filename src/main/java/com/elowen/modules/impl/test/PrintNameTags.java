package com.elowen.modules.impl.test;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.ChatUtils;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;

@ModuleInfo(R = "PrintNameTags", M = Category.TEST, a = "Print all entity name tags")
public class PrintNameTags extends Module {
   private static final String[] b = new String[]{"): ", "]:", "NameTags [", " (id="};
   @Override
   public void h$V() {
      String var1 = NoInterpolation.U$String();
      if (G.level != null) {
         ArrayList var2 = new ArrayList();

         for (Entity var4 : G.level.entitiesForRendering()) {
            if (var4.getDisplayName() != null) {
               String var10001 = var4.getType().getDescriptionId();
               int var10002 = var4.getId();
               String var10003 = var4.getDisplayName().getString();
               String[] var5 = b;
               var2.add(var10001 + " (id=" + var10002 + "): " + var10003);
               break;
            }
         }

         int var10000 = var2.size();
         String[] var8 = b;
         ChatUtils.b("NameTags [" + var10000 + "]:");
         Iterator var6 = var2.iterator();
         while (var6.hasNext()) {
            String var7 = (String)var6.next();
            ChatUtils.G(false, var7);
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
