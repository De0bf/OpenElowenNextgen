package com.elowen.events.api;

import com.elowen.events.api.events.Event;
import com.elowen.events.api.events.EventStoppable;
import com.elowen.events.api.types.Priority;
import com.elowen.exceptions.NoSuchModuleException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EventManager {
   private static final Logger j;
   private final Map C = new ConcurrentHashMap();
   private static String[] I;
   private static final String[] b = new String[]{"sb {} {}", "{} {}"};
   public void I(Object var1) {
      String[] var10000 = j$ArrString();
      Method[] var3 = var1.getClass().getDeclaredMethods();
      int var4 = var3.length;
      String[] var2 = var10000;
      int var5 = 0;

      while (var5 < var4) {
         Method var6 = var3[var5];
         if (var2 == null) {
            if (!this.t(var6)) {
               this.I(var6, var1);
            }

            var5++;
         }

         if (var2 != null) {
            break;
         }
      }
   }

   public void d(Object var1, Class var2) {
      String[] var10000 = j$ArrString();
      Method[] var4 = var1.getClass().getDeclaredMethods();
      String[] var3 = var10000;
      int var5 = var4.length;
      int var6 = 0;

      while (var6 < var5) {
         Method var7 = var4[var6];
         if (var3 == null) {
            if (!this.U(var7, var2)) {
               this.I(var7, var1);
            }

            var6++;
         }

         if (var3 != null) {
            break;
         }
      }
   }

   public void R(Object var1) {
      String[] var10000 = j$ArrString();
      Iterator var3 = this.C.values().iterator();
      String[] var2 = var10000;

      label47:
      while (true) {
         boolean var7 = var3.hasNext();

         label44:
         while (var7) {
            List var4 = (List)var3.next();
            if (var2 != null) {
               return;
            }

            for (EventManager$MethodData var6 : (List<EventManager$MethodData>)var4) {
               var7 = var6.S$Object().equals(var1);
               if (var2 != null) {
                  continue label44;
               }

               if (var2 == null && var7) {
                  var4.remove(var6);
               }

               if (var2 != null) {
                  break;
               }
            }

            if (var2 == null) {
               continue label47;
            }
            break;
         }

         this.l(true);
         return;
      }
   }

   public void m(Object var1, Class var2) {
      String[] var3 = j$ArrString();
      if (this.C.containsKey(var2)) {
         Iterator var4 = ((List)this.C.get(var2)).iterator();
         while (var4.hasNext()) {
            EventManager$MethodData var5 = (EventManager$MethodData)var4.next();
            if (var5.S$Object().equals(var1)) {
               ((List)this.C.get(var2)).remove(var5);
            }
         }

         this.l(true);
      }
   }

   private void I(Method var1, Object var2) {
      EventManager$MethodData var5;
      Map var7;
      Class var10001;
      label44: {
         String[] var10000 = j$ArrString();
         Class var4 = var1.getParameterTypes()[0];
         var5 = new EventManager$MethodData(var2, var1, var1.getAnnotation(EventTarget.class).value());
         String[] var3 = var10000;
         boolean var6 = var5.O().isAccessible();
         if (var3 == null) {
            if (!var6) {
               var5.O().setAccessible(true);
            }

            var7 = this.C;
            var10001 = var4;
            if (var3 != null) {
               break label44;
            }

            var6 = this.C.containsKey(var4);
         }

         if (var6) {
            boolean var8 = ((List)this.C.get(var4)).contains(var5);
            if (var3 == null) {
               if (var8) {
                  return;
               }

               ((List)this.C.get(var4)).add(var5);
            }

            this.V(var4);
            if (var3 == null) {
               return;
            }
         }

         var7 = this.C;
         var10001 = var4;
      }

      var7.put(var10001, new EventManager$1(this, var5));
   }

   public void x(Class var1) {
      String[] var10000 = j$ArrString();
      Iterator var3 = this.C.entrySet().iterator();
      String[] var2 = var10000;

      while (var3.hasNext()) {
         Object var4 = ((Entry)var3.next()).getKey();

         while (((Class)var4).equals(var1)) {
            var4 = var3;
            if (var2 == null) {
               var3.remove();
               return;
            }
         }
      }
   }

   public void l(boolean var1) {
      java.util.Iterator var5 = null;
      String[] var10000 = j$ArrString();
      Iterator var3 = this.C.entrySet().iterator();
      String[] var2 = var10000;

      while (var3.hasNext()) {
         label31: {
            label30: {
               boolean var4 = var1;
               if (var2 == null) {
                  if (!var1) {
                     break label30;
                  }

                  var5 = var3;
                  if (var2 != null) {
                     break label31;
                  }

                  var4 = ((List)((Entry)var3.next()).getValue()).isEmpty();
               }

               if (!var4) {
                  continue;
               }
            }

            var5 = var3;
         }

         var5.remove();
         if (var2 != null) {
            break;
         }
      }
   }

   private void V(Class var1) {
      String[] var10000 = j$ArrString();
      CopyOnWriteArrayList var3 = new CopyOnWriteArrayList();
      byte[] var4 = Priority.c;
      int var5 = var4.length;
      String[] var2 = var10000;
      int var6 = 0;

      label47:
      while (true) {
         int var10 = var6;
         int var10001 = var5;

         label44:
         while (var10 < var10001) {
            byte var7 = var4[var6];
            Iterator var11 = ((List)this.C.get(var1)).iterator();
            if (var2 != null) {
               return;
            }

            Iterator var8 = var11;

            while (var8.hasNext()) {
               EventManager$MethodData var9 = (EventManager$MethodData)var8.next();
               var10 = var9.L();
               if (var2 == null) {
                  var10001 = var7;
                  if (var2 != null) {
                     continue label44;
                  }

                  if (var10 == var7) {
                     var3.add(var9);
                  }
               }

               if (var2 != null) {
                  break;
               }
            }

            var6++;
            if (var2 == null) {
               continue label47;
            }
            break;
         }

         this.C.put(var1, var3);
         return;
      }
   }

   private boolean t(Method var1) {
      String[] var2 = j$ArrString();
      return var1.getParameterTypes().length != 1 || !var1.isAnnotationPresent(EventTarget.class);
   }

   private boolean U(Method var1, Class var2) {
      String[] var3 = j$ArrString();
      return this.t(var1) || !var1.getParameterTypes()[0].equals(var2);
   }

   public Event B(Event var1) {
      String[] var10000 = j$ArrString();
      List var3 = (List)this.C.get(var1.getClass());
      String[] var2 = var10000;
      if (var3 != null) {
         label62: {
            Event var9 = var1;
            if (var2 == null) {
               if (!(var1 instanceof EventStoppable)) {
                  break label62;
               }

               var9 = var1;
            }

            EventStoppable var4 = (EventStoppable)var9;

            for (EventManager$MethodData var6 : (List<EventManager$MethodData>)var3) {
               this.g(var6, var1);
               if (var2 != null) {
                  return var4;
               }

               if (var4.d$Z() && var2 == null || var2 != null) {
                  break;
               }
            }

            if (var2 == null) {
               return var1;
            }
         }

         for (EventManager$MethodData var8 : (List<EventManager$MethodData>)var3) {
            this.g(var8, var1);
            if (var2 != null) {
               break;
            }
         }
      }

      return var1;
   }

   private void g(EventManager$MethodData var1, Event var2) {
      try {
         var1.O().invoke(var1.S$Object(), var2);
      } catch (InvocationTargetException var5) {
         String[] var4 = b;
         j.error("sb {} {}", var1.S, var1.A);
         var5.printStackTrace();
      } catch (Exception var6) {
         j.error("{} {}", var1.S, var1.A);
         var6.printStackTrace();
      }
   }

   static {
      E(null);
      j = LogManager.getLogger(EventManager.class);
   }

   public static void E(String[] var0) {
      I = var0;
   }

   public static String[] j$ArrString() {
      return I;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
