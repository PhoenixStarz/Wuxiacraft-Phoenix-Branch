package com.lazydragonstudios.wuxiacraft.cultivation;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.UUID;

public class MasterDiscipleContainer {

	public MasterDiscipleContainer() {
		this.master = null;
		this.disciples = new LinkedList<>();
	}

	@Nullable
	private PlayerReference master;

	private final LinkedList<PlayerReference> disciples;

	public PlayerReference getMaster() {
		return master;
	}

	public void setMaster(PlayerReference master) {
		this.master = master;
	}

	public LinkedList<PlayerReference> getDisciples() {
		return disciples;
	}

	public void addDisciple(PlayerReference ref) {
		this.disciples.add(ref);
	}

	public CompoundTag serialize() {
		var tag = new CompoundTag();
		if (this.master != null) {
			tag.put("master", this.master.serialize());
		} else if (tag.contains("master")) {
			tag.remove("master");
		}
		var disciplesTag = new ListTag();
		for (var discipleRef : this.disciples) {
			disciplesTag.add(discipleRef.serialize());
		}
		tag.put("disciples", disciplesTag);
		return tag;
	}

	public void deserialize(CompoundTag tag) {
		if (tag.contains("master")) {
			this.master = new PlayerReference(tag.getCompound("master"));
		} else {
			this.master = null;
		}
		if (tag.contains("disciples")) {
			var disciplesTag = (ListTag) tag.get("disciples");
			if (disciplesTag == null) {
				disciplesTag = new ListTag();
			}
			this.disciples.clear();
			for (Tag value : disciplesTag) {
				this.addDisciple(new PlayerReference((CompoundTag) value));
			}
		}
	}

	public static class PlayerReference {

		private UUID playerId;
		private String playerName;

		public PlayerReference(UUID playerId, String playerName) {
			this.playerName = playerName;
			this.playerId = playerId;
		}

		public PlayerReference(CompoundTag tag) {
			this.deserialize(tag);
		}

		public UUID getPlayerId() {
			return playerId;
		}

		public String getPlayerName() {
			return playerName;
		}

		public CompoundTag serialize() {
			var tag = new CompoundTag();
			tag.putUUID("uuid", this.playerId);
			tag.putString("name", this.playerName);
			return tag;
		}

		public void deserialize(CompoundTag tag) {
			if (tag.contains("uuid")) {
				this.playerId = tag.getUUID("uuid");
				this.playerName = tag.getString("name");
			}
		}
	}
}


