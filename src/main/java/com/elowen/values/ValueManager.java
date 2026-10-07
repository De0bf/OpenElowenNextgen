package com.elowen.values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueManager {
   private final List A = new ArrayList();
   private final Map g = new HashMap();

   public void K(HasValue var1) {
      this.A.add(var1);
      this.g.put(var1.i().toLowerCase(), var1);
   }

   public HasValue T(String var1) {
      return (HasValue)this.g.get(var1.toLowerCase());
   }

   public List S$List() {
      return this.A;
   }
}
