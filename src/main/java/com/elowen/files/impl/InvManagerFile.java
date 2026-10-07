package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.files.ClientFile;
import com.elowen.modules.impl.player.InventoryCleaner;
import com.elowen.values.Value;
import com.elowen.values.impl.ModeValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvManagerFile extends ClientFile {
   private static final Logger M;
   private static final String[] a = new String[]{"Failed to parse invslots.cfg line: {}", "Unknown type '{}' for value '{}'", "Unknown value '{}' in invslots.cfg, ignoring.", "InventoryCleaner module not found, skipping invslots.cfg load.", "Invalid invslots config line: {}", "invslots.cfg", "Invalid mode index {} for value '{}'"};
   public InvManagerFile() {
      super("invslots.cfg");
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public void N(BufferedReader var1) throws IOException {
      boolean var45 = false;
      int var46 = 0;
      byte var12;
      String var10000 = InvSlotsFile.B$String();
      com.elowen.values.HasValueManager var3 = Elowen.S$Elowen().E$C();
      String var2 = var10000;
      InventoryCleaner var4 = (InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class);
      if (var2 != null) {
         if (var4 != null) {
            String var5;
            while ((var5 = var1.readLine()) != null) {
               String[] var6;
               label277: {
                  label287: {
                     try {
                        var6 = var5.split(":", 3);
                        if (var2 == null) {
                           break label277;
                        }

                        if (var6.length == 3) {
                           break label287;
                        }
                     } catch (Exception var43) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var43);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        String[] var15 = a;
                        M.error("Invalid invslots config line: {}", var5);
                     } catch (Exception var26) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var26);
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
                  } catch (Exception var25) {
                     M.error("Failed to parse invslots.cfg line: {}", var5, var25);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               String var7;
               String var8;
               String var9;
               Value var10;
               label268: {
                  label267: {
                     try {
                        var7 = var6[0];
                        var8 = var6[1];
                        var9 = var6[2];
                        var10 = var3.z(var4, var8);
                        if (var2 == null) {
                           break label267;
                        }

                        if (var10 != null) {
                           break label268;
                        }
                     } catch (Exception var42) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var42);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        M.warn("Unknown value '{}' in invslots.cfg, ignoring.", var8);
                     } catch (Exception var24) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var24);
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

               label259: {
                  label288: {
                     String var11;
                     label289: {
                        label290: {
                           label291: {
                              try {
                                 var11 = var7;
                                 var12 = -1;
                                 var45 = ((var11.hashCode()) != 0);
                                 if (var2 == null) {
                                    break label259;
                                 }

                                 switch (var11.hashCode()) {
                                    case 66:
                                       break;
                                    case 70:
                                       break label291;
                                    case 77:
                                       break label289;
                                    case 83:
                                       break label290;
                                    default:
                                       break label288;
                                 }
                              } catch (Exception var41) {
                                 M.error("Failed to parse invslots.cfg line: {}", var5, var41);
                                 if (var2 == null) {
                                    break;
                                 }
                                 continue;
                              }

                              try {
                                 var45 = var11.equals("B");
                                 if (var2 == null) {
                                    break label259;
                                 }
                              } catch (Exception var33) {
                                 M.error("Failed to parse invslots.cfg line: {}", var5, var33);
                                 if (var2 == null) {
                                    break;
                                 }
                                 continue;
                              }

                              try {
                                 if (((var45) ? 1 : 0) == 0) {
                                    break label288;
                                 }
                              } catch (Exception var40) {
                                 M.error("Failed to parse invslots.cfg line: {}", var5, var40);
                                 if (var2 == null) {
                                    break;
                                 }
                                 continue;
                              }

                              try {
                                 var12 = 0;
                                 if (var2 != null) {
                                    break label288;
                                 }
                              } catch (Exception var39) {
                                 M.error("Failed to parse invslots.cfg line: {}", var5, var39);
                                 if (var2 == null) {
                                    break;
                                 }
                                 continue;
                              }
                           }

                           try {
                              var45 = var11.equals("F");
                              if (var2 == null) {
                                 break label259;
                              }
                           } catch (Exception var32) {
                              M.error("Failed to parse invslots.cfg line: {}", var5, var32);
                              if (var2 == null) {
                                 break;
                              }
                              continue;
                           }

                           try {
                              if (((var45) ? 1 : 0) == 0) {
                                 break label288;
                              }
                           } catch (Exception var38) {
                              M.error("Failed to parse invslots.cfg line: {}", var5, var38);
                              if (var2 == null) {
                                 break;
                              }
                              continue;
                           }

                           try {
                              var12 = 1;
                              if (var2 != null) {
                                 break label288;
                              }
                           } catch (Exception var37) {
                              M.error("Failed to parse invslots.cfg line: {}", var5, var37);
                              if (var2 == null) {
                                 break;
                              }
                              continue;
                           }
                        }

                        try {
                           var45 = var11.equals("S");
                           if (var2 == null) {
                              break label259;
                           }
                        } catch (Exception var31) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var31);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           if (((var45) ? 1 : 0) == 0) {
                              break label288;
                           }
                        } catch (Exception var36) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var36);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           var12 = 2;
                           if (var2 != null) {
                              break label288;
                           }
                        } catch (Exception var35) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var35);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }
                     }

                     try {
                        var45 = var11.equals("M");
                        if (var2 == null) {
                           break label259;
                        }
                     } catch (Exception var30) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var30);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        if (((var45) ? 1 : 0) == 0) {
                           break label288;
                        }
                     } catch (Exception var34) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var34);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }

                     try {
                        var12 = 3;
                     } catch (Exception var23) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var23);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }
                  }

                  try {
                     var45 = ((var12) != 0);
                  } catch (Exception var22) {
                     M.error("Failed to parse invslots.cfg line: {}", var5, var22);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               label292: {
                  label206: {
                     label205: {
                        try {
                           switch (var12) {
                              case 0:
                                 var10.f$O().P(Boolean.parseBoolean(var9));
                                 if (var2 != null) {
                                    continue;
                                 }
                              case 1:
                                 break;
                              case 2:
                                 break label205;
                              case 3:
                                 break label206;
                              default:
                                 break label292;
                           }
                        } catch (Exception var29) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var29);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }

                        try {
                           var10.L().I(Float.parseFloat(var9));
                           if (var2 != null) {
                              continue;
                           }
                        } catch (Exception var21) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var21);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }
                     }

                     try {
                        var10.N().j(var9);
                        if (var2 != null) {
                           continue;
                        }
                     } catch (Exception var20) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var20);
                        if (var2 == null) {
                           break;
                        }
                        continue;
                     }
                  }

                  label193: {
                     int var13;
                     label192: {
                        ModeValue var14;
                        label191: {
                           try {
                              var13 = Integer.parseInt(var9);
                              var14 = var10.T$t();
                              var46 = var13;
                              if (var2 == null) {
                                 break label191;
                              }

                              if (var13 < 0) {
                                 break label192;
                              }
                           } catch (Exception var28) {
                              M.error("Failed to parse invslots.cfg line: {}", var5, var28);
                              if (var2 == null) {
                                 break;
                              }
                              continue;
                           }

                           try {
                              var46 = var13;
                           } catch (Exception var19) {
                              M.error("Failed to parse invslots.cfg line: {}", var5, var19);
                              if (var2 == null) {
                                 break;
                              }
                              continue;
                           }
                        }

                        try {
                           if (var46 < var14.T$ArrString().length) {
                              var14.y(var13);
                              if (var2 != null) {
                                 break label193;
                              }
                           }
                        } catch (Exception var27) {
                           M.error("Failed to parse invslots.cfg line: {}", var5, var27);
                           if (var2 == null) {
                              break;
                           }
                           continue;
                        }
                     }

                     try {
                        M.error("Invalid mode index {} for value '{}'", var13, var8);
                     } catch (Exception var18) {
                        M.error("Failed to parse invslots.cfg line: {}", var5, var18);
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
                  } catch (Exception var17) {
                     M.error("Failed to parse invslots.cfg line: {}", var5, var17);
                     if (var2 == null) {
                        break;
                     }
                     continue;
                  }
               }

               try {
                  M.error("Unknown type '{}' for value '{}'", var7, var8);
               } catch (Exception var16) {
                  M.error("Failed to parse invslots.cfg line: {}", var5, var16);
                  if (var2 == null) {
                     break;
                  }
               }
            }

            return;
         }

         M.warn("InventoryCleaner module not found, skipping invslots.cfg load.");
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
   }

   static {
      M = LogManager.getLogger(InvManagerFile.class);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
