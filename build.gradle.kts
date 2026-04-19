plugins {
    id("net.weavemc.gradle") version "1.1.0"
}

group = "me.yanjobs.mmutils"
version = "2.0.0"

weave {
    configure {
        name = "MMUtils"
        modId = "me.yanjobs.mmutils"
        entryPoints = listOf("me.yanjobs.mmutils.MMUtils")
        mcpMappings()
    }
    version("1.8.9")
}
repositories {
    maven("https://repo.spongepowered.org/maven/")
    maven("https://gitlab.com/api/v4/projects/80566527/packages/maven")
}

dependencies {
    implementation("net.weavemc:loader:1.1.0")
    implementation("net.weavemc:internals:1.1.0")
    implementation("net.weavemc.api:api:1.1.0")
    implementation("net.weavemc.api:api-v1_8:1.1.0")

    compileOnly("org.spongepowered:mixin:0.8.5")
}


java {
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
