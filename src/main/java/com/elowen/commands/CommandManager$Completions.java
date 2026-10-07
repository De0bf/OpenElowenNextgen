package com.elowen.commands;

import java.util.List;

public record CommandManager$Completions(int e, List<String> c) {
   public static final CommandManager$Completions y = new CommandManager$Completions(0, List.of());

   public List C() {
      return this.c;
   }
}
