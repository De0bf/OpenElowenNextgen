package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.files.ClientFile;
import com.elowen.values.ValueManager;
import com.elowen.values.HasValue;
import com.elowen.values.impl.ModeValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValueFile extends ClientFile {
   private static final Logger o;
   private static final String[] a = new String[]{"Unknown module '{}' in values.cfg, skipping.", "Failed to read line {}!", "Failed to read mode value {}!", "Unknown value type of {}!", "values.cfg", "Failed to read value {}!"};
   public ValueFile() {
      super("values.cfg");
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public void N(BufferedReader var1) throws IOException {
      boolean var46 = false;
      byte var13;
      String var10000 = InvSlotsFile.B$String();
      com.elowen.values.HasValueManager var3 = Elowen.S$Elowen().E$C();
      String var2 = var10000;
      ValueManager var4 = Elowen.S$Elowen().n$E();

      String var5;
      while ((var5 = var1.readLine()) != null) {
         String[] var6;
         label273: {
            label281: {
               try {
                  var6 = var5.split(":", 4);
                  if (var2 == null) {
                     break label273;
                  }

                  if (var6.length == 4) {
                     break label281;
                  }
               } catch (Exception var44) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }

               try {
                  String[] var16 = a;
                  o.error("Failed to read line {}!", var5);
               } catch (Exception var27) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }

               if (var2 != null) {
                  continue;
               }
            }

            try {
            } catch (Exception var26) {
               o.error("Failed to read value {}!", var5);
               if (var2 == null) {
                  break;
               }
               continue;
            }
         }

         String var7;
         String var8;
         String var9;
         String var10;
         HasValue var11;
         label264: {
            label263: {
               try {
                  var7 = var6[0];
                  var8 = var6[1];
                  var9 = var6[2];
                  var10 = var6[3];
                  var11 = var4.T(var8);
                  if (var2 == null) {
                     break label263;
                  }

                  if (var11 != null) {
                     break label264;
                  }
               } catch (Exception var43) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }

               try {
                  o.warn("Unknown module '{}' in values.cfg, skipping.", var8);
               } catch (Exception var25) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }
            }

            if (var2 != null) {
               continue;
            }
         }

         label255: {
            label282: {
               String var12;
               label283: {
                  label284: {
                     label285: {
                        try {
                           var12 = var7;
                           var13 = -1;
                           var46 = ((var12.hashCode()) != 0);
                           if (var2 == null) {
                              break label255;
                           }

                           switch (var12.hashCode()) {
                              case 66:
                                 break;
                              case 70:
                                 break label285;
                              case 77:
                                 break label283;
                              case 83:
                                 break label284;
                              default:
                                 break label282;
                           }
                        } catch (Exception var42) {
                           o.error("Failed to read value {}!", var5);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           var46 = var12.equals("B");
                           if (var2 == null) {
                              break label255;
                           }
                        } catch (Exception var34) {
                           o.error("Failed to read value {}!", var5);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           if (((var46) ? 1 : 0) == 0) {
                              break label282;
                           }
                        } catch (Exception var41) {
                           o.error("Failed to read value {}!", var5);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           var13 = 0;
                           if (var2 != null) {
                              break label282;
                           }
                        } catch (Exception var40) {
                           o.error("Failed to read value {}!", var5);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }
                     }

                     try {
                        var46 = var12.equals("F");
                        if (var2 == null) {
                           break label255;
                        }
                     } catch (Exception var33) {
                        o.error("Failed to read value {}!", var5);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        if (((var46) ? 1 : 0) == 0) {
                           break label282;
                        }
                     } catch (Exception var39) {
                        o.error("Failed to read value {}!", var5);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        var13 = 1;
                        if (var2 != null) {
                           break label282;
                        }
                     } catch (Exception var38) {
                        o.error("Failed to read value {}!", var5);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }
                  }

                  try {
                     var46 = var12.equals("S");
                     if (var2 == null) {
                        break label255;
                     }
                  } catch (Exception var32) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }

                  try {
                     if (((var46) ? 1 : 0) == 0) {
                        break label282;
                     }
                  } catch (Exception var37) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }

                  try {
                     var13 = 2;
                     if (var2 != null) {
                        break label282;
                     }
                  } catch (Exception var36) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               try {
                  var46 = var12.equals("M");
                  if (var2 == null) {
                     break label255;
                  }
               } catch (Exception var31) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }

               try {
                  if (((var46) ? 1 : 0) == 0) {
                     break label282;
                  }
               } catch (Exception var35) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }

               try {
                  var13 = 3;
               } catch (Exception var24) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }
            }

            try {
               var46 = ((var13) != 0);
            } catch (Exception var23) {
               o.error("Failed to read value {}!", var5);
               if (var2 == null) {
                  break;
               }
               continue;
            }
         }

         label286: {
            label202: {
               label201: {
                  try {
                     switch (var13) {
                        case 0:
                           var3.z(var11, var9).f$O().P(Boolean.parseBoolean(var10));
                           if (var2 != null) {
                              continue;
                           }
                        case 1:
                           break;
                        case 2:
                           break label201;
                        case 3:
                           break label202;
                        default:
                           break label286;
                     }
                  } catch (Exception var30) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }

                  try {
                     var3.z(var11, var9).L().I(Float.parseFloat(var10));
                     if (var2 != null) {
                        continue;
                     }
                  } catch (Exception var22) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               try {
                  var3.z(var11, var9).N().j(var10);
                  if (var2 != null) {
                     continue;
                  }
               } catch (Exception var21) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }
            }

            label189: {
               label287: {
                  int var14;
                  ModeValue var15;
                  try {
                     var14 = Integer.parseInt(var10);
                     var15 = var3.z(var11, var9).T$t();
                     if (var2 == null) {
                        break label189;
                     }

                     if (var14 < 0) {
                        break label287;
                     }
                  } catch (Exception var29) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }

                  try {
                     if (var14 >= var15.T$ArrString().length) {
                        break label287;
                     }
                  } catch (Exception var28) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }

                  try {
                     var15.y(var14);
                     if (var2 != null) {
                        continue;
                     }
                  } catch (Exception var20) {
                     o.error("Failed to read value {}!", var5);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               try {
                  o.error("Failed to read mode value {}!", var5);
               } catch (Exception var19) {
                  o.error("Failed to read value {}!", var5);
                  if (var2 == null) {
                     break;
                  }
                  continue;
               }
            }

            try {
               if (var2 != null) {
                  continue;
               }
            } catch (Exception var18) {
               o.error("Failed to read value {}!", var5);
               if (var2 == null) {
                  break;
               }
               continue;
            }
         }

         try {
            o.error("Unknown value type of {}!", var8);
         } catch (Exception var17) {
            o.error("Failed to read value {}!", var5);
            if (var2 == null) {
               break;
            }
         }
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
   }

   static {
      o = LogManager.getLogger(ValueFile.class);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
