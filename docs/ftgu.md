# From the Ground Up

Artisan Worktables allows restricting recipes based on a player's researched technologies.

## Import

```js
import mods.artisanintegrations.requirement.FTGU;
```

## Requirement Builder

If you want to require a player to have certain technologies unlocked to craft a recipe, call the following methods on the builder:

```java
FTGURequirementBuilder allOf(String[] technologies);
FTGURequirementBuilder anyOf(String[] technologies);
```

The player will be required to have researched all of the technologies defined in the method `allOf` and at least one of the technologies defined in the method `anyOf` to craft the recipe.

If you want to require a player to *not* have researched certain technologies, call the following method on the builder:

```java
FTGURequirementBuilder exclude(String[] technologies);
```

If a player has researched any of the technologies provided in the method `exclude`, the recipe can't be crafted.

## Examples

For example, the following recipe requires that the player has researched both technologies `ftgumod:survival/defense` and `ftgumod:survival/cooking` to craft the recipe.

```js
import mods.artisanworktables.builder.RecipeBuilder;
import mods.artisanintegrations.requirement.FTGU;

RecipeBuilder.get("basic")
  .setShapeless([<minecraft:dirt>])
  .addOutput(<minecraft:cobblestone>)
  .addRequirement(FTGU.allOf(["ftgumod:survival/defense", "ftgumod:survival/cooking"]))
  .create();
```

The following recipe requires that the player has researched either technology `ftgumod:survival/defense` or `ftgumod:survival/cooking`, but not `ftgumod:survival/boats` to craft the recipe:

```js
import mods.artisanworktables.builder.RecipeBuilder;
import mods.artisanintegrations.requirement.FTGU;

RecipeBuilder.get("basic")
  .setShapeless([<minecraft:dirt>])
  .addOutput(<minecraft:cobblestone>)
  .addRequirement(FTGU.anyOf(["ftgumod:survival/defense", "ftgumod:survival/cooking"]).exclude(["ftgumod:survival/boats"]))
  .create();
```
