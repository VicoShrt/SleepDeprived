package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMaps extends DataMapProvider {
    protected ModDataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.HEMP_SEEDS.getId(), new Compostable(0.25f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.TOBACCO_SEEDS.getId(), new Compostable(0.25f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.RAW_WEED_BUD.getId(), new Compostable(0.40f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.WET_WEED_BUD.getId(), new Compostable(0.40f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.WEED_BUD.getId(), new Compostable(0.40f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.TOBACCO_LEAF.getId(), new Compostable(0.40f, true), false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES).add(ModItems.TOBACCO_DRIED_LEAF.getId(), new Compostable(0.40f, true), false);
    }
}
