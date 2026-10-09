# Development journal

## Agreed scope
Minecraft Java host; real Heat vehicles and cosmetic parts from the player's installed
game. Single-player; craft cars and parts; drift-focused keyboard and controller control.
Customization: paint, wheels, spoiler, front/rear bumpers and lips, side skirts.

## Recon
Existing checkout `/workspace/Test` was empty with an unborn `work` branch.
Toolkit cloned to `/workspace/universal-modder`; read its mod-any-game skill and Minecraft
engine reference. Ran toolkit with `UV_CACHE_DIR=/tmp/heat-uv-cache` because the default
home cache is read-only. `um scan --list` found no Steam/Epic/Xbox installations.
Knowledge base has a general Frostbite asset note, but no verified Heat car importer.
Do not assume the note's other-game extractors work on Heat.

Provisional route: established Minecraft loader API, pending Melty game_info confirmation.
Do not patch either game's original files or ship extracted content.

## Completed work
JSON sheets cover handling, proposed controls, customization and integration requirements.
Handling generates one Java tuning record. Independent drift simulation includes smoothed
steering, analog deadzone, reduced lateral grip during drift, braking and speed limiting.
Six headless Java checks passed. These prove simulation properties only.
Release preflight fails on 17 unverified rows. No actual Minecraft launch performed.

## External blockers and next action
Cloud cannot access the user's PC installations. Work must continue in an execution
environment connected to those installations to inspect real Heat resources and test
the actual mod. No saves or game folders have been touched.
Melty request failed at proxy with 403, before authentication could be evaluated.
Saved draft adds custom allowed domain melty.gg, preserving package-manager presets.
User must review/save and publish that environment configuration to activate it;
then retry connection and fetch game_info/search_mashups/list_my_mods before integration.
No token copied into project files. No publication performed.
