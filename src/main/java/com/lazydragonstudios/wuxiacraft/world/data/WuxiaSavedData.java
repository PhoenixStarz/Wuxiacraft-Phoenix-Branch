package com.lazydragonstudios.wuxiacraft.world.data;

import com.lazydragonstudios.wuxiacraft.sect.Sect;
import com.lazydragonstudios.wuxiacraft.world.dimension.DimensionManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class WuxiaSavedData extends SavedData {
    public static WuxiaSavedData INSTANCE;
/* 	
	@Nonnull
	public static WuxiaSavedData get(ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(WuxiaSavedData::load, WuxiaSavedData::create, "wuxiacraft_data");
	}
	*/
	public static void init(DimensionDataStorage dimensionDataStorage) {
        if (dimensionDataStorage != null) {
            WuxiaSavedData.INSTANCE = dimensionDataStorage.computeIfAbsent(
                    WuxiaSavedData::load,
                    WuxiaSavedData::new,
                    "wuxiacraft_data");
        }
    }

	public final HashMap<UUID, Sect> sects = new HashMap<>();

	@Override
    public @Nonnull CompoundTag save(@Nonnull CompoundTag pCompoundTag) {
		var tag = new CompoundTag();
	/*	var sectList = new ListTag();
		for (var sectEntry : this.sects.entrySet()) {
			sectList.add(sectEntry.getValue().saveData());
		}
		tag.put("sects", sectList); */
        tag.put("DimensionIdManager", DimensionManager.INSTANCE.serializeNBT());
        return tag;
	}

    public static WuxiaSavedData load(CompoundTag tag) {
	/* 	INSTANCE.sects.clear();
		if (!tag.contains("sects")) return INSTANCE;
		var sectsList = (ListTag) tag.get("sects");
		if (sectsList == null) return INSTANCE;
		for (var rawSectTag : sectsList) {
			if (!(rawSectTag instanceof CompoundTag sectTag)) continue;
			var sect = new Sect();
			sect.loadData(sectTag);
			INSTANCE.sects.put(sect.getSectId(), sect);
		}*/
		if (tag.contains("DimensionIdManager", Tag.TAG_COMPOUND)) {
            DimensionManager.INSTANCE.deserializeNBT(tag.getCompound("DimensionIdManager"));
        }
		return new WuxiaSavedData();
	}
/* 
	public static WuxiaSavedData create() {
		return new WuxiaSavedData();
	}
		*/
}