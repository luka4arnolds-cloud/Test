# Heat Drift Craft — development prototype

Intended mashup: drive real Need for Speed Heat vehicles in Minecraft Java single-player,
with drift-focused keyboard/controller handling. Cars and cosmetic parts must be crafted.
Customization includes paint, wheels, spoilers, front/rear bumpers, lips and side skirts.
Heat content must be read from each player's own installation, never bundled.

## Current state

This is **not a playable Minecraft mod or publishable release**. Only an independent Java
physics core and design sheets exist. No Heat model has been imported, no Minecraft
entity or recipe registered, and no physical controller tested. Handling values are
initial tuning choices; they have not been evaluated for game feel in Minecraft.

## Verified development checks

From `/workspace/Test`, with Python 3 and JDK 21:

```sh
python tools/generate.py
java tests/CoreChecks.java
python tools/preflight.py --release
```

Generation validates sheet structure before producing `src/core/Tuning.java`.
The Java command compiles the simulation and runs six headless behavioral checks.
Release preflight currently **fails**, reporting 17 unverified integration rows.
Do not package or publish this prototype as a mod.

`design/*.json` is the source of truth. Update handling there, then regenerate.
The controls sheet specifies proposed bindings; it does not implement input capture.
Customization and integration rows remain explicitly unverified.

## Requirements still blocking the requested game

- Access to a licensed Heat installation to inspect actual vehicle/part data and implement
  a compatible local importer; the toolkit scan found no installed games in this cloud.
- Melty access to check game_info, existing mashups and supported loader/recipe requirements.
  The initial request was denied by the egress proxy (403). A melty.gg allowlist addition
  is saved in the environment draft; this does not activate it.
- Minecraft integration, actual crafting recipes, compatible-part UI and persistence,
  world collision, controller input, and in-game play tests.
- A real gameplay screenshot and one-click installation validation before publication.
- Content license, author credits and remix permissions must be established before listing.

No game assets, credentials, release archives or Melty listing have been created.
