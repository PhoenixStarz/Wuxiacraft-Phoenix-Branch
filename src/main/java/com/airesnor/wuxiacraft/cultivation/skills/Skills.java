package com.airesnor.wuxiacraft.cultivation.skills;

import com.airesnor.wuxiacraft.cultivation.Cultivation;
import com.airesnor.wuxiacraft.cultivation.ICultivation;
import com.airesnor.wuxiacraft.cultivation.elements.Element;
import com.airesnor.wuxiacraft.cultivation.skills.threads.ThreadWoodenPrison;
import com.airesnor.wuxiacraft.cultivation.techniques.CultTech;
import com.airesnor.wuxiacraft.cultivation.techniques.ICultTech;
import com.airesnor.wuxiacraft.cultivation.techniques.Techniques;
import com.airesnor.wuxiacraft.entities.skills.*;
import com.airesnor.wuxiacraft.handlers.RendererHandler;
import com.airesnor.wuxiacraft.networking.ActivatePartialSkillMessage;
import com.airesnor.wuxiacraft.networking.EnergyMessage;
import com.airesnor.wuxiacraft.networking.NetworkWrapper;
import com.airesnor.wuxiacraft.networking.SpawnParticleMessage;
import com.airesnor.wuxiacraft.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Skills {

	// TODO Asura's despair (slows and blindness) from fruit
	// TODO Red Lotus Purgatory (Sets someone on fire) from fruit
	// TODO Elder Dragons Roar (paralyze someone and knock back) from fruit
	// TODO Buddha's palm (snare and dot) from fruit
	// TODO Hell's Pond (healing for undead) from fruit
	// TODO Thunder God's Punishment (strikes lightning at target while casting) from fruit
	// TODO Demon God's flash (tp at looking) from fruit
	// TODO Sword God's Mercy (Extra big sword beam) from fruit
	// TODO Green Lotus (A wood life steal skill) from Asura
	// TODO Overlay Swing (Sword marking skill that makes target vulnerable) from HuoYuhao
	// TODO Natural embodiment (mimicry) from HuoYuhao
	// TODO Eye of terror (gaze and slows target and fear) from HuoYuhao

	// TODO -- add a soul mark
	// TODO -- add a spiritual pressure

	//TODO -- add proficiency to each of the skills

	public static final List<Skill> SKILLS = new ArrayList<>();

	public static void init() {
		SKILLS.add(CULTIVATE_BODY);
		SKILLS.add(CULTIVATE_DIVINE);
		SKILLS.add(CULTIVATE_ESSENCE);
		SKILLS.add(GATHER_WOOD);
		SKILLS.add(WOOD_SUCTION);
		SKILLS.add(ACCELERATE_GROWTH);
		SKILLS.add(FLAMES);
		SKILLS.add(FIRE_BAll);
		SKILLS.add(METAL_DETECTION);
		SKILLS.add(ORE_SUCTION);
		SKILLS.add(EARTH_SUCTION);
		SKILLS.add(EARTHLY_WALL);
		SKILLS.add(WATER_NEEDLE);
		SKILLS.add(WATER_BLADE);
		SKILLS.add(SELF_HEALING);
		SKILLS.add(HEALING_HANDS);
		SKILLS.add(WALL_CROSSING);
		SKILLS.add(WEAK_SWORD_BEAM);
		SKILLS.add(SWORD_BEAM_BARRAGE);
		SKILLS.add(WEAK_SWORD_FLIGHT);
		SKILLS.add(LIGHT_FEET_LEAP);
		SKILLS.add(LIGHT_FEET_SKILL);
		SKILLS.add(MINOR_POWER_PUNCH);
		SKILLS.add(MINOR_BODY_REINFORCEMENT);
		SKILLS.add(ADEPT_SWORD_FLIGHT);
		SKILLS.add(SPIRIT_ARROW);
		SKILLS.add(SPIRIT_PRESSURE);
		SKILLS.add(DIVINE_AUTHORITY);
		SKILLS.add(STORM_AUTHORITY);
		SKILLS.add(WIND_BLADE);
		SKILLS.add(WOODEN_PRISON);
	//	SKILLS.add(GRAND_WOODEN_PRISON);
		SKILLS.add(WEAK_LIGHTNING_BOLT);
		SKILLS.add(STRONG_LIGHTNING_BOLT);
		SKILLS.add(LANDSLIDE);
	}

	public static final Potion ENLIGHTENMENT = new EnlightenmentPotion("enlightenment");

	public static final ISkillAction APPLY_SLOWNESS = actor -> {
		PotionEffect effect1 = new PotionEffect(MobEffects.SLOWNESS, 20, 3, false, false);
		PotionEffect effect2 = new PotionEffect(MobEffects.MINING_FATIGUE, 20, 2, false, false);
		actor.addPotionEffect(effect1);
		actor.addPotionEffect(effect2);
		return true;
	};

	public static final Skill CULTIVATE_BODY = new SkillCultivate("cultivate_body", Cultivation.System.BODY);

	public static final Skill CULTIVATE_DIVINE = new SkillCultivate("cultivate_divine", Cultivation.System.DIVINE);

	public static final Skill CULTIVATE_ESSENCE = new SkillCultivate("cultivate_essence", Cultivation.System.ESSENCE);

	public static final Skill GATHER_WOOD = new Skill("gather_wood", false, false, 40f, 3f, 7f, 0f)
			.setAction(actor -> {
				World worldIn = actor.world;
				boolean activated = false;
				BlockPos pos = SkillUtils.rayTraceBlock(actor, 5, 1f);
				if (pos != null) {
					IBlockState blockState = worldIn.getBlockState(pos);
					List<Block> logs = new ArrayList<>();
					logs.add(Blocks.LOG);
					logs.add(Blocks.LOG2);
					if (logs.contains(blockState.getBlock())) {
						Stack<BlockPos> tree = TreeUtils.findTree(worldIn, pos);
						if (!tree.isEmpty()) {
							//WuxiaCraft.logger.info("Added block to break");
							BlockPos pos2 = tree.pop();
							if (!actor.world.isRemote) {
								actor.world.destroyBlock(pos2, true);
								for (int i = 0; i < 10; i++) {
									double x = pos2.getX() + actor.getRNG().nextFloat();
									double y = pos2.getY() + actor.getRNG().nextFloat();
									double z = pos2.getZ() + actor.getRNG().nextFloat();
									double my = 0.05 + actor.getRNG().nextFloat() * 0.08;
									SpawnParticleMessage spm = new SpawnParticleMessage(EnumParticleTypes.VILLAGER_HAPPY, false, x, y, z, 0, my, 0, 0);
									SkillUtils.sendMessageWithinRange((WorldServer) actor.world, pos2, 64, spm);
								}
							}
							activated = true;
						}
					}
				}
				return activated;
			});

	
	public static final Skill WOOD_SUCTION = new Skill("wood_suction", true, false, 120f, 2.5f, 80f, 0f).setAction(actor -> {
		boolean activated = false;
		final int max_range = 24;
		float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
		int range = Math.min(max_range, 6 + (int) (Math.floor(0.01 * (strength))));
		List<BlockPos> positions = OreUtils.findLogs(actor.world, actor.getPosition(), range);
		IBlockState air = Blocks.AIR.getDefaultState();
		for (BlockPos pos : positions) {
			if (actor.world instanceof WorldServer) {
				IBlockState block = actor.world.getBlockState(pos);
				actor.world.setBlockState(pos, air);
				ItemStack item = new ItemStack(block.getBlock());
				EntityItem logItem = new EntityItem(actor.world, actor.posX, actor.posY, actor.posZ, item);
				logItem.setNoPickupDelay();
				actor.world.spawnEntity(logItem);
			}
			activated = true;
		}
		return activated;
	});

	public static final Skill ACCELERATE_GROWTH = new Skill("accelerate_growth", false, false, 60f, 1f, 15f, 0f)
			.setAction(actor -> {
				World worldIn = actor.world;
				BlockPos pos = SkillUtils.rayTraceBlock(actor, 6, 1f);
				boolean activated = false;
				if (pos != null) {
					List<BlockPos> neighbors = new ArrayList<>();
					neighbors.add(pos);
					neighbors.add(pos.north());
					neighbors.add(pos.east());
					neighbors.add(pos.west());
					neighbors.add(pos.south());
					neighbors.add(pos.north().east());
					neighbors.add(pos.north().west());
					neighbors.add(pos.south().east());
					neighbors.add(pos.south().west());
					for (BlockPos p : neighbors) {
						if (worldIn.getBlockState(p).getBlock() instanceof IGrowable) {
							IGrowable iGrowable = (IGrowable) worldIn.getBlockState(p).getBlock();
							if (iGrowable.canGrow(worldIn, p, worldIn.getBlockState(p), worldIn.isRemote)) {
								iGrowable.grow(worldIn, worldIn.rand, p, worldIn.getBlockState(p));
								if (!actor.world.isRemote) {
									for (int i = 0; i < 20; i++) {
										double x = pos.getX() + actor.getRNG().nextFloat();
										double y = pos.getY() + actor.getRNG().nextFloat();
										double z = pos.getZ() + actor.getRNG().nextFloat();
										double my = 0.05 + actor.getRNG().nextFloat() * 0.08;
										SpawnParticleMessage spm = new SpawnParticleMessage(EnumParticleTypes.VILLAGER_HAPPY, false, x, y, z, 0, my, 0, 0);
										NetworkWrapper.INSTANCE.sendToAll(spm);
									}
								}
								activated = true;
							}
						}
					}
				}
				return activated;
			});

	public static final Skill FLAMES = new Skill("flames", false, true, 30f, 1.5f, 10f, 0f)
			.setAction(actor -> {
				if (actor.world instanceof WorldServer) {
					float shootPitch = actor.rotationPitch;
					float shootYaw = actor.rotationYawHead;
					ICultTech cultTech = CultivationUtils.getCultTechFromEntity(actor);
					float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
					float damage = Math.min(80f, strength / 3f);
					if (cultTech.hasElement(Element.FIRE)) {
						damage *= 1.3f;
					}
					for (int i = 0; i < 3; i++) {
						FireThrowable ft = new FireThrowable(actor.world, actor, damage);
						ft.shoot(actor, shootPitch, shootYaw, 0.3f, 1.2f, 0.4f);
						WorldUtils.spawnEntity((WorldServer) actor.world, ft);
					}
				}
				return true;
			});

	public static final Skill FIRE_BAll = new Skill("fire_ball", false, true, 80f, 3f, 25f, 0f)
			.setAction(actor -> {
				if (actor.world instanceof WorldServer) {
					ICultTech cultTech = CultivationUtils.getCultTechFromEntity(actor);
					float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
					float speed = (float) CultivationUtils.getDexterityFromEntity(actor);
					float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getEssenceModifier();
					float damage = (float) Math.min(200f, 4 + strength * 0.8 + spirit * 0.01);
					if (cultTech.hasElement(Element.FIRE)) {
						damage *= 1.3f;
					}
					for (int i = 0; i < 5; i++) {
						FireThrowable ft = new FireThrowable(actor.world, actor, damage, 600, 60, 1.2f);
						ft.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(0.8f + speed * 0.4f, 1.8f), 0.4f);
						WorldUtils.spawnEntity((WorldServer) actor.world, ft);
					}
				}
				return true;
			});

	public static final Skill METAL_DETECTION = new Skill("metal_detection", false, false, 80f, 0.6f, 20f, 0f)
			.setAction(actor -> {
				if (actor.world.isRemote) {
					if (actor instanceof EntityPlayer) {
						final int max_range = 32;
						float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
						int range = Math.min(max_range, 16 + (int) (Math.floor(0.2 * strength)));
						OreUtils.findOres(actor.world, actor.getPosition(), range);
						float duration = 10 * 20; // 10 seconds
						RendererHandler.worldRenderQueue.add(duration, () -> {
							OreUtils.drawFoundOres((EntityPlayer) actor);
							return null;
						});
					}
				}
				return true;
			});

	public static final Skill ORE_SUCTION = new Skill("ore_suction", true, false, 120f, 2.5f, 80f, 0f).setAction(actor -> {
		boolean activated = false;
		final int max_range = 16;
		float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
		int range = Math.min(max_range, 4 + (int) (Math.floor(0.01 * (strength))));
		List<BlockPos> positions = OreUtils.findOres(actor.world, actor.getPosition(), range);
		IBlockState stone = Blocks.STONE.getDefaultState();
		for (BlockPos pos : positions) {
			if (actor.world instanceof WorldServer) {
				IBlockState block = actor.world.getBlockState(pos);
				actor.world.setBlockState(pos, stone);
				ItemStack item = new ItemStack(block.getBlock());
				EntityItem oreItem = new EntityItem(actor.world, actor.posX, actor.posY, actor.posZ, item);
				oreItem.setNoPickupDelay();
				//oreItem.setOwner(actor.getName());
				//i guess items have their own way to spawn in the client
				actor.world.spawnEntity(oreItem);
			}
			activated = true;
		}
		return activated;
	});

	public static final Skill EARTH_SUCTION = new Skill("earth_suction", false, false, 10f, 0.8f, 10f, 0f).setAction(actor -> {
		boolean activated = false;
		BlockPos pos = SkillUtils.rayTraceBlock(actor, 5f, 1f);
		if (pos != null) {
			if (OreUtils.earthTypes.contains(actor.world.getBlockState(pos).getBlock())) {
				if (actor.world instanceof WorldServer) {
					IBlockState block = actor.world.getBlockState(pos);
					actor.world.setBlockToAir(pos);
					ItemStack item = new ItemStack(block.getBlock().getItemDropped(block, actor.world.rand, 0));
					item.setCount(block.getBlock().quantityDropped(actor.world.rand));
					double dx = actor.posX - (pos.getX() + 0.5);
					double dy = (actor.posY + actor.getEyeHeight()) - (pos.getY() + 0.5);
					double dz = actor.posZ - (pos.getZ() + 0.5);
					double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
					EntityItem earthItem = new EntityItem(actor.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, item);
					earthItem.motionX = dx / dist * 0.6f;
					earthItem.motionY = dy / dist * 0.6f;
					earthItem.motionZ = dz / dist * 0.6f;
					earthItem.setNoPickupDelay();
					//earthItem.setOwner(actor.getName());
					//i guess items have their own way to spawn in the client
					actor.world.spawnEntity(earthItem);
				}
				activated = true;
			}
		}
		return activated;
	});

	public static final Skill EARTHLY_WALL = new Skill("earthly_wall", false, false, 80f, 0.8f, 10f, 0f).setAction(actor -> {
		boolean activated = false;
		BlockPos pos = SkillUtils.rayTraceBlock(actor, 5f, 1f);
		if (pos != null) {
			if (OreUtils.earthTypes.contains(actor.world.getBlockState(pos).getBlock())) {
				IBlockState block = actor.world.getBlockState(pos);
				IBlockState newBlock;
				if (block.getBlock() == Blocks.GRASS || block.getBlock() == Blocks.GRASS_PATH) {
					newBlock = Blocks.DIRT.getDefaultState();
				} else if (block.getBlock() == Blocks.GRASS) {
					newBlock = Blocks.COBBLESTONE.getDefaultState();
				} else {
					newBlock = block.getBlock().getDefaultState();
				}
				World world = actor.world;
				List<BlockPos> wall_position = new ArrayList<>();
				EnumFacing direction = EnumFacing.fromAngle(actor.rotationYaw);
				switch (direction) {
					case EAST:
					case WEST:
						for (int i = -3; i < 4; i++) {
							BlockPos wallPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + i);
							int height = 3;
							if (i == 0) height = 4;
							else if (Math.abs(i) == 3) height = 1;
							for (int j = 0; j < height; j++) {
								wall_position.add(wallPos.up(j + 1));
							}
						}
						break;
					case NORTH:
					case SOUTH:
						for (int i = -3; i < 4; i++) {
							BlockPos wallPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
							int height = 3;
							if (i == 0) height = 4;
							else if (Math.abs(i) == 3) height = 1;
							for (int j = 0; j < height; j++) {
								wall_position.add(wallPos.up(j + 1));
							}
						}
						break;
				}
				for (BlockPos toPlace : wall_position) {
					if (world.isAirBlock(toPlace) || world.getBlockState(toPlace).getBlock() == Blocks.TALLGRASS) {
						world.setBlockState(toPlace, newBlock);
						activated = true;
					}
				}
			}
		}
		return activated;
	});

	// Credits : My Girlfriend
	public static final Skill WATER_NEEDLE = new Skill("water_needle", false, true, 30f, 1.1f, 12f, 0f, "Lysian Prieto")
			.setAction(actor -> {
				if (actor.world instanceof WorldServer) {
					ICultTech cultTech = CultivationUtils.getCultTechFromEntity(actor);
					float strength = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
					float speed = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
					float damage = Math.min(60, 4 + (float) (strength * 0.3 + CultivationUtils.getMaxEnergy(actor) * 0.02));
					if (cultTech.hasElement(Element.WATER)) {
						damage *= 1.3;
					}
					WaterNeedleThrowable needle = new WaterNeedleThrowable(actor.world, actor, damage, 300);
					needle.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(1.8f, 0.8f + speed * 0.12f), 0.2f);
					WorldUtils.spawnEntity((WorldServer) actor.world, needle);
				}
				return true;
			});

	public static final Skill WATER_BLADE = new Skill("water_blade", false, true, 60f, 2.0f, 24f, 0f).setAction(actor -> {
		if (actor.world instanceof WorldServer) {
			ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(actor);
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity(actor);
			float strength = (float) CultivationUtils.getStrengthFromEntity(actor);
			float speed = (float) CultivationUtils.getDexterityFromEntity(actor);
			float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getEssenceModifier();
			float swordModifier = 1f;
			if (actor.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
				swordModifier = 1.5f;
				actor.swingArm(EnumHand.MAIN_HAND);
				skillCap.stepCastProgress(speed * 0.5f);
			}
			float damage = Math.min(200f, 8 + (strength * 0.7f + speed * 0.3f + spirit * 0.01f) * swordModifier);
			if (cultTech.hasElement(Element.WATER)) {
				damage *= 1.3;
			}
			WaterBladeThrowable blade = new WaterBladeThrowable(actor.world, actor, damage, 300);
			blade.shoot(actor, actor.rotationPitch, actor.rotationYaw, 0.3f, Math.min(2.8f, 0.7f + speed * 0.5f * swordModifier), 0.2f);
			WorldUtils.spawnEntity((WorldServer) actor.world, blade);
		}
		return true;
	});

	public static final Skill SELF_HEALING = new Skill("self_healing", false, false, 40f, 1f, 12f, 0f).setAction(actor -> {
		float strength = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		actor.heal(Math.max(15f, strength * 0.05f));
		return true;
	});

	public static final class HealingDamageSource extends EntityDamageSource {
		public HealingDamageSource(Entity damageSourceEntityIn) {
			super("healing", damageSourceEntityIn);
		}
	}

	public static final Skill HEALING_HANDS = new Skill("healing_hands", false, false, 70f, 1.4f, 15f, 0f).setAction(actor -> {
		boolean activated = false;
		Entity result = SkillUtils.rayTraceEntities(actor, 10f, 1f);
		if (result instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) result;
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity(actor);
			float strength = Math.min(300f, (float) CultivationUtils.getStrengthFromEntity(actor));
			if (cultTech.getDivineTechnique() != null) {
				if (cultTech.getDivineTechnique().getTechnique().equals(Techniques.RAGEFUL_ABNEGATION_SAINT_ARTS)) {
					strength = (float) Math.max(3, strength - CultivationUtils.getCultivationFromEntity(entity).getDivineModifier() * 0.3);
					entity.attackEntityFrom(new HealingDamageSource(actor).setDamageBypassesArmor(), strength);
					activated = true;
				}
			}
			if(!activated) {
				entity.heal(Math.max(18f, strength * 0.05f));
				activated = true;
			}
		}
		return activated;
	});

	//Credits: My Girlfriend
	public static final Skill WALL_CROSSING = new Skill("wall_crossing", false, false, 90f, 3.0f, 20f, 0f, "Lysian Prieto").setAction(actor -> {
		boolean activated = false;
		EnumFacing facing = EnumFacing.fromAngle(actor.rotationYaw);
		BlockPos pos = SkillUtils.rayTraceBlock(actor, 4f, 1f);
		if (pos != null) {
			int range = 15;
			int test = 0;
			while (!actor.world.isAirBlock(pos) && test < range) {
				pos = pos.offset(facing);
				test++;
			}
			if (actor.world.isAirBlock(pos) && actor.world.isAirBlock(pos.up())) {
				actor.setPositionAndUpdate(pos.getX() + 0.5d, pos.getY() + 0.1d, pos.getZ() + 0.5d);
				activated = true;
			}
		}
		return activated;
	});

	public static final Skill WEAK_SWORD_BEAM = new Skill("weak_sword_beam", false, true, 25f, 2.5f, 10f, 0f)
			.setAction(actor -> {
				boolean activated = false;
				if (actor.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
					activated = true;
					if (actor.world instanceof WorldServer) {
						float attack_strength = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
						float speed = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
						float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getMaxEnergy() * 0.1f;
						float strength = attack_strength * 1.1f + speed * 0.4f + spirit * 0.3f;
						float sword = ((ItemSword) actor.getHeldItem(EnumHand.MAIN_HAND).getItem()).getAttackDamage();
						int enchantment = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, actor.getHeldItem(EnumHand.MAIN_HAND));
						sword += enchantment > 0 ? 0.5 * (1 + enchantment) : 0;
						float damage = strength + sword;
						SwordBeamThrowable sbt = new SwordBeamThrowable(actor.world, actor, damage, 0xEF890A, 300);
						sbt.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 1.0f, 1.6f, 0f);
						WorldUtils.spawnEntity((WorldServer) actor.world, sbt);
					}
					actor.swingArm(EnumHand.MAIN_HAND);
				}
				return activated;
			});

	public static final ISkillAction BARRAGE_MINOR_BEAM = actor -> {
		if (!actor.world.isRemote) {
			float attack_strength = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
			float speed = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
			float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getMaxEnergy() * 0.1f;
			float strength = Math.min(80f, attack_strength * 1.1f + speed * 0.4f + spirit * 0.3f);
			float sword = ((ItemSword) actor.getHeldItem(EnumHand.MAIN_HAND).getItem()).getAttackDamage() * 2;
			int enchantment = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, actor.getHeldItem(EnumHand.MAIN_HAND));
			sword += enchantment > 0 ? 0.5 * (1 + enchantment) : 0;
			float damage = strength * 0.3f + sword * 0.3f;
			SwordBeamThrowable sbt = new SwordBeamThrowable(actor.world, actor, damage, 0x89EF0A, 300);
			sbt.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 1.0f, 1.2f, 0f);
			WorldUtils.spawnEntity((WorldServer) actor.world, sbt);
		}
		actor.swingArm(EnumHand.MAIN_HAND);
		return true;
	};

	public static final Skill SWORD_BEAM_BARRAGE = new Skill("sword_beam_barrage", false, true, 45f, 4f, 150f, 60f)
			.setAction(actor -> {
				boolean activated = false;
				ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(actor);
				if (actor.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
					activated = true;
					if (!actor.world.isRemote) {
						float attack_strength = (float) CultivationUtils.getStrengthFromEntity(actor);
						float speed = (float) CultivationUtils.getStrengthFromEntity(actor);
						float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getEssenceModifier();
						float strength = Math.min(150f, attack_strength * 1.1f + speed * 0.4f + spirit * 0.3f);
						float sword = ((ItemSword) actor.getHeldItem(EnumHand.MAIN_HAND).getItem()).getAttackDamage() * 2;
						int enchantment = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, actor.getHeldItem(EnumHand.MAIN_HAND));
						sword += enchantment > 0 ? 0.5 * (1 + enchantment) : 0;
						float damage = strength + sword;
						SwordBeamThrowable sbt = new SwordBeamThrowable(actor.world, actor, damage, 0xEF890A, 300);
						sbt.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 1.0f, 1.6f, 0f);
						WorldUtils.spawnEntity((WorldServer) actor.world, sbt);
					}
					actor.swingArm(EnumHand.MAIN_HAND);
					skillCap.resetBarrageCounter();
				}
				return activated;
			})
			.setWhenCasting(actor -> {
				boolean activated = false;
				ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(actor);
				if (actor.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
					activated = true;
					for (int i = 0; i < 4; i++) {
						if (skillCap.getBarrageReleased() <= i && skillCap.getCastProgress() >= (i + 1) * 150f / 5f) {
							skillCap.increaseBarrageToRelease();
						}
					}
					for (int i = 0; i < skillCap.getBarrageToRelease(); i++) {
						BARRAGE_MINOR_BEAM.activate(actor);
						CultivationUtils.getCultivationFromEntity(actor).remEnergy(9f);
						NetworkWrapper.INSTANCE.sendToServer(new ActivatePartialSkillMessage("barrageMinorBeam", 9f, actor.getUniqueID()));
						skillCap.increaseBarrageReleased();
					}
					skillCap.resetBarrageToRelease();
				}
				return activated;
			});

	public static final Skill LIGHT_FEET_SKILL = new SkillPotionEffectSelf("light_feet_skill", new PotionEffect(MobEffects.SPEED, 2400, 2, false, true), 18f, 3f, 12f, 20f, "Aires Adures");

	public static final Skill LIGHT_FEET_LEAP = new Skill("light_feet_leap", false, false, 19f, 1.2f, 15f, 20f)
			.setAction(actor -> {
				int bX = MathHelper.floor(actor.posX);
				int bY = MathHelper.floor(actor.posY - 1);
				int bZ = MathHelper.floor(actor.posZ);
				if (actor.getEntityWorld().getBlockState(new BlockPos(bX, bY, bZ)).getBlock() != Blocks.AIR) {
					float speed = Math.min((float) actor.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 6f, 12f);
					float yaw = actor.rotationYawHead;
					float pitch = actor.rotationPitch;
					float x = speed * -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
					float y = 2f;
					float z = speed * MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
					actor.motionX += x;
					actor.motionY += y;
					actor.motionZ += z;
					actor.fallDistance = -50;
				}
				return true;
			});

	public static final Skill MINOR_POWER_PUNCH = new Skill("minor_power_punch", false, true, 25f, 1.2f, 12f, 10f)
			.setAction(actor -> {
				boolean activated = false;
				Entity target = SkillUtils.rayTraceEntities(actor, 3f, 1f);
				if (target != null) {
					if (target instanceof EntityLivingBase) {
						activated = true;
						actor.swingArm(EnumHand.MAIN_HAND);
						float attack_strength = (float) actor.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.5f;
						float constitution = (float) actor.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() * 0.5f;
						float spirit = (float) CultivationUtils.getCultivationFromEntity(actor).getMaxEnergy() * 0.1f;
						float strength = attack_strength * 1.1f + constitution * 0.3f + spirit * 0.4f;
						for (PotionEffect effect : actor.getActivePotionEffects()) {
							if (effect.getPotion() == MobEffects.STRENGTH) {
								strength += (effect.getAmplifier() + 1) * 3;
							}
						}
						target.attackEntityFrom(DamageSource.causeMobDamage(actor), strength);
						float x = +MathHelper.sin(actor.rotationYawHead * 0.017453292F);
						float z = -MathHelper.cos(actor.rotationYawHead * 0.017453292F);
						((EntityLivingBase) target).knockBack(actor, Math.max(30f, strength * 0.3f), x, z); //not limit the damage but the knock back
					}
				}
				return activated;
			});

	public static final Skill SPIRIT_ARROW = new Skill("spirit_arrow", false, true, 15f, 1.2f, 15f, 2f)
			.setAction(actor -> {
				if (!actor.world.isRemote) {
					ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
					float strength = (float) cultivation.getDivineModifier() * 1.5f;
					SoulArrowThrowable soulArrowThrowable = new SoulArrowThrowable(actor.world, actor, strength, 300);
					soulArrowThrowable.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(1.8f, 0.8f + strength * 0.2f * 0.12f), 0.2f);
					WorldUtils.spawnEntity((WorldServer) actor.world, soulArrowThrowable);
				}
				return true;
			});

	public static final Skill WIND_BLADE = new Skill("wind_blade", false, true, 25f, 1.2f, 15f, 2f)
			.setAction(actor -> {
				if (!actor.world.isRemote) {
					ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
					float strength = (float) cultivation.getEssenceModifier() * 0.9f;
					if (actor.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
						strength *= 1.5f;
					}
					WindBladeThrowable windBladeThrowable = new WindBladeThrowable(actor.world, actor, strength, 300);
					windBladeThrowable.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(2.4f, 0.8f + strength * 0.4f * 0.12f), 0.2f);
					WorldUtils.spawnEntity((WorldServer) actor.world, windBladeThrowable);
				}
				actor.swingArm(EnumHand.MAIN_HAND);
				return true;
			});

	public static final ISkillAction PRESSURE_EVERYONE_NEAR = actor -> {
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
		boolean activated = false;
		if (cultivation.hasEnergy(1.2f)) {
			activated = true;
			AxisAlignedBB range = new AxisAlignedBB(new BlockPos(actor.posX, actor.posY, actor.posZ))
					.grow(Math.min(cultivation.getDivineModifier(), 128));
			List<EntityLivingBase> targets = actor.world.getEntitiesWithinAABB(EntityLivingBase.class, range, t -> t != actor);
			for (EntityLivingBase target : targets) {
				ICultivation targetCultivation = CultivationUtils.getCultivationFromEntity(target);
				if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.6) {
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 3, false, true));
					target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 3, false, true));
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.8) {
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 2, false, true));
					target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2, false, true));
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.9) {
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 1, false, true));
					target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 1, false, true));
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier()) {
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 0, false, true));
					target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 300, 0, false, true));
				}
			}
		}
		return activated;
	};

	public static final ISkillAction DAMAGE_EVERYONE_NEAR = actor -> {
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
		boolean activated = false;
		if (cultivation.hasEnergy(2.4f)) {
			activated = true;
			AxisAlignedBB range = new AxisAlignedBB(new BlockPos(actor.posX, actor.posY, actor.posZ))
					.grow(Math.min(cultivation.getDivineModifier(), 16));
			List<EntityLivingBase> targets = actor.world.getEntitiesWithinAABB(EntityLivingBase.class, range, t -> t != actor);
			for (EntityLivingBase target : targets) {
				ICultivation targetCultivation = CultivationUtils.getCultivationFromEntity(target);
				if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.5) {
					target.attackEntityFrom(DamageSource.GENERIC, 20);
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.7) {
					target.attackEntityFrom(DamageSource.GENERIC, 15);
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.9) {
					target.attackEntityFrom(DamageSource.GENERIC, 10);
				} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier()) {
					target.attackEntityFrom(DamageSource.GENERIC, 5);
				}
			}
		}
		return activated;
	};

	public static final ISkillAction FULMINATE_EVERYONE_NEAR = actor -> {
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
		boolean activated = false;
		if (cultivation.hasEnergy(4.8f)) {
			activated = true;
			AxisAlignedBB range = new AxisAlignedBB(new BlockPos(actor.posX, actor.posY, actor.posZ))
					.grow(Math.min(cultivation.getDivineModifier(), 12));
			if (actor.world instanceof WorldServer) {
				List<EntityLivingBase> targets = actor.world.getEntitiesWithinAABB(EntityLivingBase.class, range, t -> t != actor);
				for (EntityLivingBase target : targets) {
				WorldServer world = (WorldServer) actor.world;
				world.addScheduledTask(() -> {
					EntityLightningBolt lightningBolt = new EntityLightningBolt(world, target.posX, target.posY + 1.0, target.posZ, true); // effect only won't cause damage
					world.addWeatherEffect(lightningBolt);
					target.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setDamageIsAbsolute().setDamageBypassesArmor(), (float) 20);
						ICultivation targetCultivation = CultivationUtils.getCultivationFromEntity(target);
						ICultTech targetCultTech = CultivationUtils.getCultTechFromEntity(target);
						float strength = (float) cultivation.getDivineModifier();
						if (targetCultTech.getBodyTechnique().getTechnique().equals(Techniques.FORBIDDEN_LIGHTNING_A)
						 || targetCultTech.getDivineTechnique().getTechnique().equals(Techniques.FORBIDDEN_LIGHTNING_B)
						 || targetCultTech.getEssenceTechnique().getTechnique().equals(Techniques.FORBIDDEN_LIGHTNING_C)) {
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.4 && targetCultTech.hasElement(Element.LIGHTNING)) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) (strength));
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.6 && targetCultTech.hasElement(Element.LIGHTNING)) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.8));
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.8 && targetCultTech.hasElement(Element.LIGHTNING)) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.4));
						} else if (targetCultivation.getDivineModifier() >= cultivation.getDivineModifier() * 0.8 && targetCultTech.hasElement(Element.LIGHTNING)) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.2));
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.4) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.5));
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.6) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.4));
						} else if (targetCultivation.getDivineModifier() < cultivation.getDivineModifier() * 0.8) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.2));
						} else if (targetCultivation.getDivineModifier() >= cultivation.getDivineModifier() * 0.8) {
							target.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ((strength)*0.1));
						}				
					});
				}
			}
		}
		return activated;
	};

	public static final Skill SPIRIT_PRESSURE = new Skill("spirit_pressure", true, true, 12f, 0f, 500f, 1f)
			.setAction(actor -> true)
			.setWhenCasting(actor -> {
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
				if (cultivation.hasEnergy(1.2f)) {
					cultivation.remEnergy(1.2f);
					NetworkWrapper.INSTANCE.sendToServer(new ActivatePartialSkillMessage("pressureEveryoneNear", 1.2f, actor.getUniqueID()));
					return true;
				}
				return false;
			});

	public static final Skill DIVINE_AUTHORITY = new Skill("divine_authority", true, true, 24f, 0f, 500f, 1f)
			.setAction(actor -> true)
			.setWhenCasting(actor -> {
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
				if (cultivation.hasEnergy(2.4f)) {
					cultivation.remEnergy(2.4f);
					NetworkWrapper.INSTANCE.sendToServer(new ActivatePartialSkillMessage("damageEveryoneNear", 2.4f, actor.getUniqueID()));
					return true;
				}
				return false;
			});

	public static final Skill STORM_AUTHORITY = new Skill("storm_authority", true, true, 48f, 0f, 1f, 20f)
			.setAction(actor -> true)
			.setWhenCasting(actor -> {
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
				if (cultivation.hasEnergy(4.8f)) {
					cultivation.remEnergy(4.8f);
					NetworkWrapper.INSTANCE.sendToServer(new ActivatePartialSkillMessage("fulminateEveryoneNear", 4.8f, actor.getUniqueID()));
					return true;
				}
				return false;
			});

	public static final Skill WOODEN_PRISON = new Skill("wooden_prison", false, true, 64f, 3.2f, 33f, 1f)
			.setAction(actor -> {
				BlockPos target = actor.getPosition().down();
				Entity entity = SkillUtils.rayTraceEntities(actor, 30, 0f);
				if (entity != null) {
					target = entity.getPosition().down();
				} else {
					BlockPos position = SkillUtils.rayTraceBlock(actor, 30, 0f);
					target = position != null ? position : target;
				}
				if (actor.world instanceof WorldServer) {
					new ThreadWoodenPrison((WorldServer) actor.world, target).start();
				}
				return true;
			});

/* 
	public static final Skill GRAND_WOODEN_PRISON = new Skill("grand_wooden_prison", false, true, 128f, 4.2f, 43f, 1f)
			.setAction(actor -> {
				BlockPos target = actor.getPosition().down();
				Entity entity = SkillUtils.rayTraceEntities(actor, 30, 0f);
				if (entity != null) {
					target = entity.getPosition().down();
				} else {
					BlockPos position = SkillUtils.rayTraceBlock(actor, 30, 0f);
					target = position != null ? position : target;
				}
				if (actor.world instanceof WorldServer) {
					new ThreadWoodenPrison((WorldServer) actor.world, target).start();
				}
				return true;
			});
*/
	public static final Skill WEAK_LIGHTNING_BOLT = new Skill("weak_lightning_bolt", false, true, 25f, 1.2f, 14f, 0f)
			.setAction(actor -> {
				if (!actor.world.isRemote) {
					ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
					float strength = (float) cultivation.getEssenceModifier() * 1.1f;
					ThunderBoltThrowable thunderBoltThrowable = new ThunderBoltThrowable(actor.world, actor, strength, 300);
					thunderBoltThrowable.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(3.2f, 0.8f + strength * 0.4f * 0.12f), 0.2f);
					WorldUtils.spawnEntity((WorldServer) actor.world, thunderBoltThrowable);
				}
				actor.swingArm(EnumHand.MAIN_HAND);
				return true;
			});


	public static final Skill STRONG_LIGHTNING_BOLT = new Skill("strong_lightning_bolt", false, true, 75f, 4.4f, 42f, 0f)
			.setAction(actor -> {
				if (!actor.world.isRemote) {
					ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
					float strength = (float) cultivation.getEssenceModifier() * 3.3f;
					ThunderBoltThrowable thunderBoltThrowable = new ThunderBoltThrowable(actor.world, actor, strength, 400);
					thunderBoltThrowable.shoot(actor, actor.rotationPitch, actor.rotationYawHead, 0.3f, Math.min(3.2f, 0.8f + strength * 0.8f * 0.18f), 0.2f);
					WorldUtils.spawnEntity((WorldServer) actor.world, thunderBoltThrowable);
				}
				actor.swingArm(EnumHand.MAIN_HAND);
				return true;
			});

	public static final Skill LANDSLIDE = new Skill("landslide", true, false, 0f, 0f, 200f, 0f)
			.setAction(actor -> true)
			.setWhenCasting(actor -> {
				boolean activated = false;
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(actor);
				ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(actor);
				float cost = 12f;
				if (cultivation.hasEnergy(cost)) {
					if (((int) skillCap.getCastProgress()) % 5 == 4)
						NetworkWrapper.INSTANCE.sendToServer(new EnergyMessage(1, cost * 5, actor.getUniqueID()));
					cultivation.remEnergy(cost);
					if (OreUtils.earthTypes.contains(actor.world.getBlockState(actor.getPosition().down()).getBlock())
							|| OreUtils.earthTypes.contains(actor.world.getBlockState(actor.getPosition().down().offset(actor.getHorizontalFacing())).getBlock())
							|| OreUtils.earthTypes.contains(actor.world.getBlockState(actor.getPosition().down().offset(actor.getHorizontalFacing(),-1)).getBlock())) {
						float strength = (float) cultivation.getStrengthModifier();
						float speed = Math.max(0.4f, Math.min(1.3f, strength * 0.08f));
						float yaw = actor.rotationYaw;
						float pitch = 1.5f;
						float x = speed * -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
						float y = speed * -MathHelper.sin((pitch) * 0.017453292F);
						float z = speed * MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
						actor.motionX = x;
						actor.motionY = y;
						actor.motionZ = z;
						actor.swingingHand = EnumHand.MAIN_HAND;
						actor.swingProgressInt = 2;
						actor.isSwingInProgress = true;
						activated = true;
					}
					if (OreUtils.earthTypes.contains(actor.world.getBlockState(actor.getPosition().offset(actor.getHorizontalFacing())).getBlock())) {
						actor.setPositionAndUpdate(actor.posX, actor.posY + 1.1, actor.posZ);
						activated = true;
					}
				}
				return activated;
			});

	public static final Skill MINOR_BODY_REINFORCEMENT = new SkillPotionEffectSelf("minor_body_reinforcement", new PotionEffect(MobEffects.STRENGTH, 1800, 2, false, true), 12f, 1.2f, 18f, 20f, "Aires Adures");

	public static final Skill WEAK_SWORD_FLIGHT = new SkillSwordFlight("weak_sword_flight", 0.6f, 0.9f, 9f, 500f, 0f, "Aires Adures");

	public static final Skill ADEPT_SWORD_FLIGHT = new SkillSwordFlight("adept_sword_flight", 0.8f, 1.5f, 26f, 2000f, 0f, "Aires Adures");
}
