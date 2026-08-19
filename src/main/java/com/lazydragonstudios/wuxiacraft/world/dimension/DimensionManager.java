package com.lazydragonstudios.wuxiacraft.world.dimension;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.DivineCultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.DivineCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;
import com.lazydragonstudios.wuxiacraft.world.data.WuxiaSavedData;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

//inspired by Iron's Spells 'n Spellbooks Pocket Dim, DimensionalDoors Private Pocket, and inner world cultivation stuff.
public class DimensionManager implements INBTSerializable<CompoundTag> {
    public static final ResourceKey<Level> DIVINE_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(WuxiaCraft.MOD_ID, "divine_dimension"));
    public static final ResourceLocation DIVINE_ROOM_PLATFORM = new ResourceLocation(WuxiaCraft.MOD_ID, "divine_platform");
    public static final int DIVINE_SPACING = 1024;
    public static final int PLATFORM_Y_LEVEL = 64;

    private static final String UUID_KEY = "uuid";
    private static final String INT_ID_KEY = "divine_id";
    private static final String ID_MAP_KEY = "ids";
    private static final String NEXT_ID_KEY = "next_id";

    public static final DimensionManager INSTANCE = new DimensionManager();

    public void remove(UUID uuid) {
        ids.remove(uuid);
        WuxiaSavedData.INSTANCE.setDirty();
    }

    private int nextId;

    private final Object2IntMap<UUID> ids = new Object2IntOpenHashMap<>();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag entries = new ListTag();
        for (var entry : ids.object2IntEntrySet()) {
            CompoundTag tagEntry = new CompoundTag();
            tagEntry.putUUID(UUID_KEY, entry.getKey());
            tagEntry.putInt(INT_ID_KEY, entry.getIntValue());
            entries.add(tagEntry);
        }
        compoundTag.put(ID_MAP_KEY, entries);
        compoundTag.putInt(NEXT_ID_KEY, nextId);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag entries = nbt.getList(ID_MAP_KEY, 10);
        int nextId = nbt.getInt(NEXT_ID_KEY);
        for (Tag tag : entries) {
            try {
                CompoundTag compoundTag = (CompoundTag) tag;
                UUID uuid = compoundTag.getUUID(UUID_KEY);
                int divineId = compoundTag.getInt(INT_ID_KEY);
                ids.put(uuid, divineId);
            } catch (Exception e) {
                WuxiaCraft.LOGGER.error("Failed to parse DimensionManager id entry: {}: {}", tag, e.getMessage());
            }
        }
        this.nextId = nextId;
    }

    public int idFor(UUID uuid) {
        if (!ids.containsKey(uuid)) {
            ids.put(uuid, nextId);
            nextId++;
            WuxiaSavedData.INSTANCE.setDirty();
        }
        return ids.getInt(uuid);
    }

    public int idFor(Player player) {
        return idFor(player.getUUID());
    }

    public BlockPos structurePosForId(int divineDimensionId) {
        int[] iL = getPoints(divineDimensionId);
        return BlockPos.containing(DIVINE_SPACING * iL[0], PLATFORM_Y_LEVEL, DIVINE_SPACING * iL[1]);
    }

    public BlockPos structurePosForPlayer(Player player) {
        return structurePosForId(idFor(player));
    }

    // Source - https://stackoverflow.com/a/73824364
    // Posted by TerZer
    // Retrieved 2026-08-12, License - CC BY-SA 4.0
    private int[] getPoints(int n){
        int[] k = new int[2];
        if(n == 0){
            k[0] = 0;
            k[1] = 0;
            return k;
        }
        n--;
        int r = (int) (Math.floor((Math.sqrt(n + 1) -1) / 2) + 1);
        int p = (8 * r * (r - 1)) / 2;
        int a = (1 + n - p) % (r * 8);
        
        switch ((int) Math.floor(a / (r * 2))) {
        case 0:
            k[0] = a - r;
            k[1] = -r;
            return k;
        case 1:
            k[0] = r;
            k[1] = (a % (r * 2)) - r;
            return k;
        case 2:
            k[0] = r - (a % (r * 2));
            k[1] = r;
            return k;
        case 3:
            k[0] = -r;
            k[1] = r - (a % (r * 2));
            return k;
        }
        return null;       
    }

    public boolean maybeGenerateDivinePlatform(ServerPlayer player) {
        var serverLevel = player.serverLevel();
        var structurePos = structurePosForPlayer(player);
        var divineLevel = serverLevel.getServer().getLevel(DIVINE_DIMENSION);
        BlockState blockState = divineLevel.getBlockState(structurePos);
        if (blockState.isAir()) {
            BlockPos modifiedPos = structurePos.north().west();
            var structureTemplateManager = divineLevel.getStructureManager();
            var structureTemplate = structureTemplateManager.getOrCreate(DIVINE_ROOM_PLATFORM);
            var placementSettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true);
            structureTemplate.placeInWorld(divineLevel, modifiedPos, modifiedPos, placementSettings, divineLevel.getRandom(), 2);
            return true;
        }
        return false;
    }

    public void tick(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!serverLevel.dimension().equals(DimensionManager.DIVINE_DIMENSION)) {
            return;
        }
        if (serverLevel.getGameTime() % 20 == 0) {
            serverLevel.players().forEach(player -> {
                if (!player.isCreative() && !player.isSpectator()) {
                    double divineX = Math.abs(player.getX() % DimensionManager.DIVINE_SPACING);
                    double divineZ = Math.abs(player.getZ() % DimensionManager.DIVINE_SPACING);
                    if (Math.min(divineX, DimensionManager.DIVINE_SPACING - divineX) > 128
                            || Math.min(divineZ, DimensionManager.DIVINE_SPACING - divineZ) > 128 
                                    || player.getY() < -64 ) {
                        // teleport player back into bounds
                        ICultivation cultivation = Cultivation.get(player);
                        DivineCultivationContainer divineData = (DivineCultivationContainer) cultivation.getSystemData(System.DIVINE);
                        DivineCultivationStage stage = (DivineCultivationStage) divineData.getStage();
                        if (stage.getDimensionSize() > 0) {
                            var blockPos = structurePosForPlayer(player);
                            player.resetFallDistance();
                            player.stopRiding();
                            double x = blockPos.getX() + 0.5;
                            double y = blockPos.getY() + 1.0;
                            double z = blockPos.getZ() + 0.5;
                            player.teleportTo(x, y, z);
                        } else {
                            BlockPos dmPos = new BlockPos(0,64,0);
                            ServerLevel dimLevel = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation("minecraft:overworld")));
                            if (divineData.getStoredLocation() != null) {
                                dmPos = divineData.getStoredLocation().getValue();
                                dimLevel = player.getServer().getLevel(divineData.getStoredLocation().getKey());
                            }
                            double x = dmPos.getX() + 0.5;
                            double y = dmPos.getY() + 1.0;
                            double z = dmPos.getZ() + 0.5;
                            player.changeDimension(dimLevel, new ITeleporter() {
                                public void teleport(ServerLevel level, ServerPlayer serverPlayer) {
                                    serverPlayer.teleportTo(level, x, y, z, 1f, 1f);
                                }
                            });	
                            player.teleportTo(x, y, z);
                        }
                    }
                }
            });
        }
    }
}
