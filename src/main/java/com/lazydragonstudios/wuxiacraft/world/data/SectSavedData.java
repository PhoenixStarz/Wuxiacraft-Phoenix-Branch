package com.lazydragonstudios.wuxiacraft.world.data;

import com.lazydragonstudios.wuxiacraft.sect.Sect;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.UUID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SectSavedData extends SavedData {

	@Nonnull
	public static SectSavedData get(ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(SectSavedData::load, SectSavedData::create, "sect_data");
	}

	public final HashMap<UUID, Sect> sects = new HashMap<>();

	@Override
	public CompoundTag save(CompoundTag tag) {
		var sectList = new ListTag();
		for (var sectEntry : this.sects.entrySet()) {
			sectList.add(sectEntry.getValue().saveData());
		}
		tag.put("sects", sectList);
		return tag;
	}

	public static SectSavedData load(CompoundTag tag) {
		var instance = new SectSavedData();
		instance.sects.clear();
		if (!tag.contains("sects")) return instance;
		var sectsList = (ListTag) tag.get("sects");
		if (sectsList == null) return instance;
		for (var rawSectTag : sectsList) {
			if (!(rawSectTag instanceof CompoundTag sectTag)) continue;
			var sect = new Sect();
			sect.lodData(sectTag);
			instance.sects.put(sect.getSectId(), sect);
		}
		return instance;
	}

	public static SectSavedData create() {
		return new SectSavedData();
	}
}
