plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.2"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
}

group = "ru.mnsvn"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.compileJava {
    options.release = 21
    options.encoding = Charsets.UTF_8.name()
}

tasks.test {
    useJUnitPlatform()
}

bukkitPluginYaml {
    main = "ru.mnsvn.goldenheads.GoldenHeads"
    author = "mnsvn"
    apiVersion = "1.21"
    foliaSupported = true
}