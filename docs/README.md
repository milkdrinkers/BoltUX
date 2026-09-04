<h1 style="text-align:center;">BoltUX - An Unofficial Bolt Expansion</h1>
<p style="text-align:center;">
    <img alt="GitHub License" src="https://img.shields.io/github/license/milkdrinkers/BoltUX?style=for-the-badge&color=blue&labelColor=141417">
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/milkdrinkers/BoltUX/total?style=for-the-badge&labelColor=141417">
    <img alt="GitHub Release" src="https://img.shields.io/github/v/release/milkdrinkers/BoltUX?include_prereleases&sort=semver&style=for-the-badge&label=LATEST%20VERSION&labelColor=141417">
    <img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/milkdrinkers/BoltUX/ci.yml?style=for-the-badge&labelColor=141417">
    <img alt="GitHub Issues or Pull Requests" src="https://img.shields.io/github/issues/milkdrinkers/BoltUX?style=for-the-badge&labelColor=141417">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/milkdrinkers/BoltUX?style=for-the-badge&labelColor=141417">
</p>

---

## Description

BoltUX (_Bolt User Experience_) primarily seeks to provide a GUI interfaces for [Bolt](https://github.com/pop4959/Bolt) commands and interactions, as well as additonal user feedback for certain actions. At this time it is only supported on Paper servers for Minecraft 1.21.X+ and above. By providing user interfaces BoltUX make it easier for most players to use Bolt by abstracting away commands and granular functionality. Since Bolt's commands are retained, more experienced players (or those who prefer commands) can still use commands in conjuction with, or in the place of the GUI menus.

### Features
#### Custom Lock Item
BoltUX adds support for a custom lock item that can be required to create protections instead of using commands. An [example resourcepack](https://github.com/milkdrinkers/BoltUX/blob/main/BoltUX-Resourcepack-1.0.zip) is included to provide a custom texture and model for the lock item. If this feature is enabled, protections are created by **shift right clicking with the lock item**. If using this feature, it is highly recommended that you disable the **bolt.command.lock** permission node and set ``autoProtect: false`` for all parts in the Bolt config so that users cannot lock a protection without using the lock item. This feature also requires users to be given the permission node **boltux.lock**.

![Lock Item Usage](https://github.com/milkdrinkers/BoltUX/blob/main/docs/assets/lock_user.gif "Lock Usage")

There is a default crafting recipe for the lock item.
<br>
<br>
![Lock Recipe](https://github.com/milkdrinkers/BoltUX/blob/main/docs/assets/lock_recipe.PNG?raw=true "Lock Crafting Recipe")

#### Protection Owner GUI
A GUI for interfacing protections can be accessed by any protection owner by **shift right clicking** on their owned protection with their hand. The interface facilitates most Bolt actions including but not limited to:
* Adding player/group access
* Removing player/group access
* Adding trusted players/groups
* Removing trusted players/groups
* Transferring ownership of the protection
* Unlocking (deleting) the proteciton

![Protection Owner Gui](https://github.com/milkdrinkers/BoltUX/blob/main/docs/assets/protection_owner_gui.gif "Protection Owner GUI")

#### Glowing Effect
BoltUX uses packets in order to display a red glowing effect to the client when a player tries to interact with a protection they do not have permissions to interact with.

![Protection Owner Gui](https://github.com/milkdrinkers/BoltUX/blob/main/docs/assets/red_glow.gif "Protection Owner GUI")

---

## Dependencies/Hooks
### Dependencies
* #### [Bolt](https://github.com/pop4959/Bolt)
### Optional Hooks
* #### [Towny](https://github.com/TownyAdvanced/Towny), [BoltTowny](https://github.com/pop4959/BoltTowny/tree/master)
    If Towny is used, BoltUX will automatically use TownyAPI in order to provide Town and Nation members as suggestions in the Add Access Menu. If both Towny and BoltTowny are used, Town sources will be supported in the menus.
* #### [ItemsAdder](https://itemsadder.devs.beer/), [Nexo](https://docs.nexomc.com/), [Oraxen](https://oraxen.com/)
    Custom item plugins. To use an item from one of them as the lock, set `lock-item.item` to the items id prefixed with the plugin that owns it:

    ```yaml
    lock-item:
      item: "nexo:my_lock" # or oraxen:my_lock, itemsadder:my_lock, ia:my_lock
    ```

    A bare id or a `minecraft:` prefix is treated as a vanilla material. Leave `item` blank to use the built in fallback item instead.

---

## Permissions
BoltUX contains the following permission nodes:
* ``boltux.admin``
Grants the user the ability to open and use the BoltUX GUI for any protection, regardless of whether the user is the protection owner. It also grants access to the **/boltux** commands.
* ``boltux.lock``
Grants the user the ability to use the custom lock items to lock protections, if the feature is enabled.
* ``boltux.craft``
Grants the user the ability to craft locks using the default crafting recipe.
---

## Commands
BoltUX adds the following commands:
* ``/boltux getlock <amount>``
Spawns the specified amount of lock items in the player's inventory, defaults to 1.
* ``/boltux translation reload``
Re-reads the language files from ``plugins/BoltUX/lang/``.
* ``/boltux translation test <key>``
Renders a single translation entry, for checking formatting.
* ``/boltux dump``
Uploads server and plugin configs plus the latest log to [mclo.gs](https://mclo.gs) and returns a link. Attach this when reporting bugs.

---

## Configuration

BoltUX creates `plugins/BoltUX/config.yml` on first start and reads it once when the plugin loads.

Translations live in `plugins/BoltUX/lang/`, and `/boltux translation reload` reloads them without a restart.

### Lock item

```yaml
lock-item:
  enabled: true

  # nexo:my_lock | oraxen:my_lock | itemsadder:my_lock | ia:my_lock
  # A bare id or minecraft:<id> is a vanilla material.
  # Blank means "always use the fallback below".
  item: ""

  # Used when "item" is blank, or when it cannot be resolved because the owning plugin is missing or the id is unknown.
  fallback:
    enabled: true # off means locks are disabled rather than falling back
    material: IRON_INGOT
    item-model: "" # 1.21.4+ component, recommended. Blank to skip
    custom-model-data: 8792 # legacy, 0 to skip. The bundled resourcepack uses 8792
    display-name: "<gray>Iron Lock</gray>"
    lore:
      - "<yellow>Shift-Right Click to Use</yellow>"
```

The fallback needs at least one of `item-model`, `custom-model-data` or `display-name`. Without any of them it would be an ordinary material and every one of them on the server would count as a lock, so BoltUX refuses it and turns locks off with an error in the console.

---
