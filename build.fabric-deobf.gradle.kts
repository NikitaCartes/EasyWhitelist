plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version "1.17.20"
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

// Enables [fabric."26.1"] property lookup.
stonecutter {
    val (version, loader) = current.project.split('-', limit = 2)
    properties.tags(version, loader)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

// The game's own Java, not the toolchain's.
val javaVersion = property("java_version").toString().toInt()

base.archivesName = "${property("mod_id")}-fabric-mc${property("minecraft_version")}"
version = property("mod_version").toString()

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(25)) }
    withSourcesJar()
}

// Copy for configuration time: loom reads the generated AW before stonecutterGenerate runs.
val awFile = "easywhitelist.official.accesswidener"
val generatedAw = file("build/generated/stonecutter/main/resources/accesswidener/$awFile")
if (!generatedAw.exists()) {
    generatedAw.parentFile.mkdirs()
    rootProject.file("src/main/resources/accesswidener/$awFile").copyTo(generatedAw, overwrite = true)
}

loom {
    accessWidenerPath = generatedAw
}

// Kept for Gradle 9 validation if a future Loom version adds the task.
tasks.matching { it.name == "validateAccessWidener" }.configureEach {
    dependsOn("stonecutterGenerate")
}

dependencies {
    // 26.1+ ships Mojang-mapped, so there is no `mappings(...)` line.
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    implementation(fabricApi.module("fabric-command-api-v2", property("fabric_version").toString()))
    implementation(fabricApi.module("fabric-api-base", property("fabric_version").toString()))

    implementation("me.lucko:fabric-permissions-api:${property("fabric_permissions_version")}")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(javaVersion)
    dependsOn("stonecutterGenerate")
}

tasks.jar {
    from("LICENSE")
}

val modExpansions = mapOf(
    "version" to project.version.toString(),
    "supported_minecraft_version" to property("supported_minecraft_version").toString(),
    "accessWidener" to awFile,
    "java_version" to javaVersion.toString()
)

tasks.processResources {
    // Not for a Fabric jar.
    exclude("META-INF/neoforge.mods.toml", "accesstransformer/**", "accesswidener/easywhitelist.named.accesswidener")

    inputs.properties(modExpansions)
    filesMatching("fabric.mod.json") { expand(modExpansions) }
    dependsOn("stonecutterGenerate")
}

tasks.register<Copy>("collectJars") {
    group = "build"
    from(tasks.jar.map { it.archiveFile })
    into(rootProject.layout.buildDirectory.dir("libs"))
    dependsOn("build", rootProject.tasks.named("cleanCollectedJars"))
}

publishMods {
    val modrinthToken = System.getenv("MODRINTH_TOKEN") ?: ""
    val curseforgeToken = System.getenv("CURSEFORGE_TOKEN") ?: ""
    val githubToken = System.getenv("GITHUB_TOKEN") ?: ""

    file = tasks.jar.get().archiveFile
    dryRun = modrinthToken.isEmpty() || curseforgeToken.isEmpty() || githubToken.isEmpty()
    displayName = "${property("display_name")} ${project.version}"
    version = project.version.toString()
    changelog = rootProject.file("RELEASE_NOTE.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    val targets = property("supported_versions").toString().split(",")
    modrinth {
        projectId = "LODybUe5"
        accessToken = modrinthToken
        targets.forEach(minecraftVersions::add)
        requires("fabric-api")
    }
    curseforge {
        projectId = "543767"
        accessToken = curseforgeToken
        targets.forEach(minecraftVersions::add)
        requires("fabric-api")
        server.set(true)
    }
    // Uploads into the root publishGithub release.
    github {
        accessToken = githubToken
        parent(rootProject.tasks.named("publishGithub"))
    }
}
