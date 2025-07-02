package com.lazydragonstudios.wuxiacraft.sect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Sect {

	private UUID sectId;

	private String sectName;

	private UUID sectMasterID;

	private Component sectMasterName;

	private final HashSet<UUID> members = new HashSet<>();

	private final HashMap<UUID, Component> membersName = new HashMap<>();

	public final HashSet<UUID> sectInvites = new HashSet<>();

	private boolean markedForRemoval;

	public Sect() {
		this.sectId = UUID.randomUUID();
	}

	public Sect(UUID sectId) {
		this.sectId = sectId;
	}

	public UUID getSectId() {
		return sectId;
	}

	public String getSectName() {
		return sectName;
	}

	public UUID getSectMasterID() {
		return sectMasterID;
	}

	public Component getSectMasterName() {
		return sectMasterName;
	}

	public HashSet<UUID> getMembers() {
		return members;
	}

	public void setSectName(String sectName) {
		this.sectName = sectName;
	}

	public HashMap<UUID, Component> getMembersName() {
		return membersName;
	}

	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}

	public CompoundTag saveData() {
		var tag = new CompoundTag();
		tag.putUUID("sectId", this.sectId);
		tag.putString("sectName", this.sectName);
		tag.putUUID("sectMasterId", this.sectMasterID);
		var memberList = new ListTag();
		for (var memberId : members) {
			var memberTag = new CompoundTag();
			memberTag.putUUID("memberId", memberId);
			memberList.add(memberTag);
		}
		tag.put("members", memberList);
		return tag;
	}

	public CompoundTag sharePublicInformation() {
		var tag = new CompoundTag();
		tag.putUUID("sectId", this.sectId);
		tag.putString("sectName", this.sectName);
		tag.putUUID("sectMasterId", this.sectMasterID);
		if (this.sectMasterName != null) {
			tag.putString("sectMasterName", this.sectMasterName.getString());
		}
		return tag;
	}

	public void lodData(CompoundTag tag) {
		this.sectId = tag.getUUID("sectId");
		this.sectName = tag.getString("sectName");
		this.sectMasterID = tag.getUUID("sectMasterId");
		this.sectMasterName = null;
		if (tag.contains("sectMasterName")) {
			this.sectMasterName = Component.literal(tag.getString("sectMasterName"));
		}
		this.members.clear();
		this.membersName.clear();
		if (tag.contains("members")) {
			var membersList = (ListTag) tag.get("members");
			for (var memberTag : membersList) {
				if (!(memberTag instanceof CompoundTag mTag)) continue;
				this.members.add(mTag.getUUID("memberId"));
			}
		}
	}

	public void onServerTick(MinecraftServer server) {
		if (this.sectMasterName == null && this.sectMasterID != null) {
			Player member = server.getPlayerList().getPlayer(this.sectMasterID);
			if (member != null) this.sectMasterName = member.getDisplayName();
		}
		if (this.membersName.isEmpty() && !this.members.isEmpty()) {
			for (UUID memberId : this.members) {
				Player member = server.getPlayerList().getPlayer(memberId);
				if (member != null) this.membersName.put(memberId, member.getDisplayName());
			}
		}
		if(this.members.isEmpty()) {
			this.markedForRemoval = true;
		}
	}

	public void setSectMaster(UUID uuid) {
		this.sectMasterID = uuid;
	}

	public void addMember(UUID uuid) {
		this.members.add(uuid);
	}
}
