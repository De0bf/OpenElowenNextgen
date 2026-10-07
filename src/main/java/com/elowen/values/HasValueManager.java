package com.elowen.values;

import com.elowen.exceptions.NoSuchValueException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HasValueManager {
   private final List R = new ArrayList();

   public void p(Value var1) {
      this.R.add(var1);
   }

   public void J(Value var1) {
      this.R.remove(var1);
   }

   public List G(HasValue var1) {
      int var10000 = Value.x();
      ArrayList var3 = new ArrayList();
      int var2 = var10000;

      for (Value var5 : (List<Value>)this.R) {
         if (var5.A$Q() == var1) {
            var3.add(var5);
         }

         if (var2 != 0) {
            break;
         }
      }

      return var3;
   }

   public Value z(HasValue var1, String var2) {
      int var10000 = Value.a$I();
      Iterator var4 = this.R.iterator();
      int var3 = var10000;

      while (var4.hasNext()) {
         label51: {
            Value var5 = (Value)var4.next();
            Value var6 = var5;
            if (var3 != 0) {
               if (var5.A$Q() != var1) {
                  break label51;
               }

               var6 = var5;
            }

            if (var3 == 0) {
               return var6;
            }

            if (var6.r().equals(var2)) {
               return var5;
            }
         }

         if (var3 == 0) {
            HasValue.d(HasValue.X$Z());
            break;
         }
      }

      throw new NoSuchValueException();
   }

   public List A$List() {
      return this.R;
   }

   private static NoSuchValueException a(NoSuchValueException var0) {
      return var0;
   }
}
