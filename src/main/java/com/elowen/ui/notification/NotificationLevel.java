package com.elowen.ui.notification;

import java.awt.Color;

public enum NotificationLevel {
   SUCCESS(new Color(23, 150, 38, 255).getRGB()),
   INFO(new Color(23, 22, 38, 255).getRGB()),
   WARNING(new Color(138, 90, 92, 255).getRGB()),
   ERROR(new Color(148, 42, 43, 255).getRGB());

   private final int K;

   NotificationLevel(int var3) {
      this.K = var3;
   }

   public int y$I() {
      return this.K;
   }
}
