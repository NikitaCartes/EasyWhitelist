## Easy Whitelist Mod

Simple mod that changes whitelist (also banlist and op-list) behaviour from uuid-based to name-based, allowing it to
be use at offline mode.

### Note

1) Mod adds following offline version of commands:
    - Vanilla `whitelist` in comparison adds lowercase nicknames
    - `easywhitelist add <nickname>`
    - `easywhitelist remove <nickname>`
    - `easyban <nickname>`
    - `easypardon <nickname>`
    - `easyop <nickname>`
    - `easydeop <nickname>`
3) All commands are case-**sensitive**
4) Elements in `whitelist.json`, `ops.json`, `banned-players.json` must still contain both name and uuid
    - uuids must be in correct format, but may be random

```
  {
    "uuid": "01234567-89ab-def0-1234-56789abcdef0",
    "name": "NikitaCartes"
  }
```

[Discord](https://discord.gg/UY4nhvUzaK)

<del>[CurseForge](https://www.curseforge.com/minecraft/mc-mods/easyauth), [Modrinth](https://modrinth.com/mod/easyauth)

[My authentication mod](https://github.com/NikitaCartes/EasyAuth), for Fabric servers.
 