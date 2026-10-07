# Elowen-NextGen 1.1.1

Decompiled / deobfuscated sources for the **Elowen-NextGen** Minecraft client.

## Status

This is **not a fully deobfuscated, human-refactored tree**. Class and package
names, strings and a large part of the structure have been recovered, but some
method/field names are still obfuscated (the client used Zelix KlassMaster with
runtime string encryption), so full semantic readability is not guaranteed.
The remaining obfuscation does not affect behaviour — the sources still compile
and produce a working mod jar.

The tree started as a straight decompilation and carried a decompiler artifact
where an inherited Minecraft-instance field (`Module.G` / `Wrapper.q`) had been
resolved as a *class* reference — e.g. `NameTags$NameTagData.player` instead of
`G.player`. Those mis-resolved references (152 of them across 20 files) have
been corrected against the original class files, so `src/` now compiles as-is.

## Building

Requires **JDK 25** (the mod targets Minecraft 26.3 / Java 25) and an internet
connection for the first build (Gradle wrapper + Minecraft/Fabric dependencies).

Fabric Loom 1.18.2 needs the Gradle JVM itself to be Java 25, so point
`JAVA_HOME` at a JDK 25 before running the wrapper:

```bash
export JAVA_HOME=/path/to/jdk-25
./gradlew build
```

The build output is written to `build/libs/elowen-nextgen-1.1.1.jar`.

A plain `./gradlew` run downloads Gradle 9.7.0 automatically, and Fabric Loom
resolves Minecraft 26.3, the Fabric loader and the Fabric API.

### Why an "identity" mapping?

Minecraft 26.3 is shipped **unobfuscated** (official class/member names), so no
remapping is needed. `localrepo/` contains a tiny identity mapping artifact
(`com.elowen:identity:1.0`) that tells Loom to use the official names as-is.

## Project layout

```
build.gradle / settings.gradle / gradle.properties  build configuration
gradlew / gradlew.bat / gradle/                     Gradle 9.7.0 wrapper
localrepo/                                          identity mappings for Loom
libs/                                               bundled libraries (Skija, types)
src/main/java/com/elowen/                           mod sources
src/main/resources/                                 fabric.mod.json, mixins, assets
```

## Notes

- Targets **Minecraft 26.3**, built with **Fabric Loom 1.18.2**, Java **25**.
- The decompilation tooling that produced this tree is not included.
- Skija (Skia bindings) is bundled both in `libs/` (for compilation) and inside
  the mod jar under `META-INF/jars/`.
