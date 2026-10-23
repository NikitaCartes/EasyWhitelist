plugins {
    id("dev.kikugie.stonecutter")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}
stonecutter active "26.1-fabric"

stonecutter parameters {
    constants.match(current.project.substringAfterLast('-'), "fabric", "neoforge")

    // <1.21.9: NameAndId -> com.mojang.authlib.GameProfile.
    replacements.string(current.parsed < "1.21.9", "!name_and_id") {
        replace("import net.minecraft.server.players.NameAndId;", "import com.mojang.authlib.GameProfile;")
        replace("net/minecraft/server/players/NameAndId;", "com/mojang/authlib/GameProfile;")
        replace("NameAndId ", "GameProfile ")
        replace("<NameAndId>", "<GameProfile>")
        replace(".name()", ".getName()")
        replace("new NameAndId(UUIDUtil.createOfflinePlayerUUID(name), name)", "new GameProfile(UUIDUtil.createOfflinePlayerUUID(name), name)")
    }
}

stonecutter.tasks {
    order("publishMods")
}

tasks.register<Delete>("cleanCollectedJars") {
    delete(layout.buildDirectory.dir("libs"))
}

// One GitHub release for the whole matrix: this task creates it, the nodes upload into it.
publishMods {
    val githubToken = System.getenv("GITHUB_TOKEN") ?: ""
    val modVersion = findProperty("mod_version")?.toString()
        ?: file("stonecutter.properties.toml").readLines()
            .first { it.trim().startsWith("mod_version") }
            .substringAfter('=').trim().trim('"')

    dryRun = githubToken.isEmpty()
    version = modVersion
    displayName = modVersion
    changelog = rootProject.file("RELEASE_NOTE.md").readText()
    type = STABLE

    github {
        accessToken = githubToken
        repository = "NikitaCartes/EasyWhitelist"
        commitish = "master"
        tagName = modVersion
        allowEmptyFiles = true
    }
}
