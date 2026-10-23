plugins {
    id("java")
    id("net.neoforged.moddev") version "2.0.147"
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

// Enables [neoforge."x"] property lookup.
stonecutter {
    val (version, loader) = current.project.split('-', limit = 2)
    properties.tags(version, loader)
}

repositories {
    mavenCentral()
    maven("https://maven.neoforged.net/releases")
    maven("https://jitpack.io")
}

// The game's own Java, not the toolchain's.
val javaVersion = property("java_version").toString().toInt()

base.archivesName = "${property("mod_id")}-neoforge-mc${property("minecraft_version")}"
version = property("mod_version").toString()

val atFile = "easywhitelist.cfg"
// Copy for configuration time: NeoForge reads the generated AT before stonecutterGenerate runs.
val atSource = file("build/generated/stonecutter/main/resources/accesstransformer/$atFile")
if (!atSource.exists()) {
    atSource.parentFile.mkdirs()
    rootProject.file("src/main/resources/accesstransformer/$atFile").copyTo(atSource, overwrite = true)
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(25)) }
    withSourcesJar()
}

neoForge {
    version = property("neoforge_version").toString()
    accessTransformers.from(atSource)

    runs {
        create("server") {
            server()
            gameDirectory.set(file("run"))
        }
    }
    mods {
        create(property("mod_id").toString()) {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(javaVersion)
    dependsOn("stonecutterGenerate")
}

val modExpansions = mapOf(
    "version" to project.version.toString(),
    "supported_minecraft_version" to property("supported_minecraft_version").toString(),
    "neoforge_version" to property("neoforge_version").toString(),
    "mod_id" to property("mod_id").toString(),
    "mod_name" to property("mod_name").toString(),
    "java_version" to javaVersion.toString()
)

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    // Only the access transformer, at the path neoforge.mods.toml references.
    exclude("accesstransformer/**", "accesswidener/**", "fabric.mod.json")
    from(atSource) {
        into("META-INF")
        rename { "accesstransformer.cfg" }
    }

    inputs.properties(modExpansions)
    filesMatching("META-INF/neoforge.mods.toml") { expand(modExpansions) }
}

tasks.named("createMinecraftArtifacts") {
    dependsOn(tasks.named("stonecutterGenerate"))
}

tasks.named("processResources") {
    dependsOn("stonecutterGenerate")
}

tasks.jar {
    from("LICENSE")
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
    modLoaders.add("neoforge")

    val targets = property("supported_versions").toString().split(",")
    modrinth {
        projectId = "LODybUe5"
        accessToken = modrinthToken
        targets.forEach(minecraftVersions::add)
    }
    curseforge {
        projectId = "543767"
        accessToken = curseforgeToken
        targets.forEach(minecraftVersions::add)
        server.set(true)
    }
    // Uploads into the root publishGithub release.
    github {
        accessToken = githubToken
        parent(rootProject.tasks.named("publishGithub"))
    }
}
