package com.elowen.files.impl;

import com.elowen.files.ClientFile;
import com.elowen.ui.ClickGUI;
import com.elowen.values.HasValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CGuiFile extends ClientFile {
   private static final Logger r;
   private static final String[] a = new String[]{"clickgui.cfg", "Failed to read clickgui.cfg!"};
   public CGuiFile() {
      super("clickgui.cfg");
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      try {
         ClickGUI.y = Integer.parseInt(var1.readLine());
         ClickGUI.t = Integer.parseInt(var1.readLine());
         ClickGUI.BS = Integer.parseInt(var1.readLine());
         ClickGUI.B = Integer.parseInt(var1.readLine());
      } catch (Exception var3) {
         r.error("Failed to read clickgui.cfg!", var3);
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
      InvSlotsFile.B$String();
      var1.write((int)ClickGUI.y + "\n");
      var1.write((int)ClickGUI.t + "\n");
      var1.write((int)ClickGUI.BS + "\n");
      var1.write((int)ClickGUI.B + "\n");
      if (!HasValue.x()) {
         InvSlotsFile.G("tCOLab");
      }
   }

   static {
      r = LogManager.getLogger(CGuiFile.class);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
