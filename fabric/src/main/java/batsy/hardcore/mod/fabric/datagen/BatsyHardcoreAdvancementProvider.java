package batsy.hardcore.mod.fabric.datagen;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import batsy.hardcore.mod.fabric.item.BatsyHardcoreModItemsFabric;
import batsy.hardcore.mod.util.BatsyHardcoreIdentifierProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BatsyHardcoreAdvancementProvider extends FabricAdvancementProvider {
    public BatsyHardcoreAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        createMainRootAdventure(consumer);
        Advancement recipesRoot = createRecipesRootAdvancement(consumer);
        createReviveAltarAdvancement(consumer, recipesRoot);
        createReviveTotemAdvancement(consumer, recipesRoot);
    }

    private Advancement createAdvancement(String displayName, String description, String id, Advancement parent, Consumer<Advancement> consumer, @NotNull Map<String, CriterionConditions> criteria, boolean hasRewards) {
        Advancement.Builder advancementBuilder = Advancement.Builder.createUntelemetered()
                .display(new AdvancementDisplay(new ItemStack(BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK),
                        Text.literal(displayName),
                        Text.literal(description),
                        BatsyHardcoreIdentifierProvider.create("textures/block/revive_altar_side.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        true));

        for (Map.Entry<String, CriterionConditions> entry : criteria.entrySet()) {
            advancementBuilder.criterion(entry.getKey(), entry.getValue());
        }
        advancementBuilder.criteriaMerger(CriterionMerger.OR);

        if (parent != null) {
            advancementBuilder.parent(parent);
        }

        if (hasRewards) {
            AdvancementRewards.Builder awardsBuilder = new AdvancementRewards.Builder();
            List.of(
                    BatsyHardcoreIdentifierProvider.create(BatsyHardcoreConfiguration.REVIVE_TOTEM),
                    BatsyHardcoreIdentifierProvider.create(BatsyHardcoreConfiguration.REVIVE_ALTAR)
            ).forEach(awardsBuilder::addRecipe);
            advancementBuilder.rewards(awardsBuilder.build());
        }

        return advancementBuilder.build(consumer, BatsyHardcoreMod.MOD_ID + ":" + id);
    }

    private @NotNull Map<String, CriterionConditions> createImpossibleCondition() {
        Map<String, CriterionConditions> map = new HashMap<>(1);
        map.put("impossible", new ImpossibleCriterion.Conditions());
        return map;
    }

    private @NotNull Map<String, CriterionConditions> createLocationCondition() {
        Map<String, CriterionConditions> map = new HashMap<>(1);
        map.put("location", TickCriterion.Conditions.createLocation(LocationPredicate.ANY));
        return map;
    }

    private Advancement createRecipesRootAdvancement(Consumer<Advancement> consumer) {
        return createAdvancement("Batsy Hardcore Recipe Advancements", "The Batsy Hardcore Recipes Advancement", "recipes/root", null, consumer, createImpossibleCondition(), false);
    }

    private void createMainRootAdventure(Consumer<Advancement> consumer) {
        createAdvancement("Batsy Hardcore", "Batsy Hardcore Mode", "root", null, consumer, createLocationCondition(), false);
    }

    private void createReviveTotemAdvancement(Consumer<Advancement> consumer, Advancement parent) {
        Map<String, CriterionConditions> criterionConditionsMap = new HashMap<>(2);
        criterionConditionsMap.put("has_diamond", InventoryChangedCriterion.Conditions.items(Items.DIAMOND));
        criterionConditionsMap.put("has_undying", InventoryChangedCriterion.Conditions.items(Items.TOTEM_OF_UNDYING));
        createAdvancement("Revive Totem", "Revive Totem Recipe", "recipes/" + BatsyHardcoreConfiguration.REVIVE_TOTEM, parent, consumer, criterionConditionsMap, true);
    }

    private void createReviveAltarAdvancement(Consumer<Advancement> consumer, Advancement parent) {
        Map<String, CriterionConditions> criterionConditionsMap = new HashMap<>(2);
        criterionConditionsMap.put("has_diamond", InventoryChangedCriterion.Conditions.items(Items.DIAMOND));
        criterionConditionsMap.put("has_respawn_anchor", InventoryChangedCriterion.Conditions.items(Blocks.RESPAWN_ANCHOR));
        createAdvancement("Revive Altar", "Revive Altar Recipe", "recipes/" + BatsyHardcoreConfiguration.REVIVE_ALTAR, parent, consumer, criterionConditionsMap, true);
    }
}
