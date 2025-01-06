package com.airesnor.wuxiacraft.handlers;

import com.airesnor.wuxiacraft.WuxiaCraft;
import com.airesnor.wuxiacraft.blocks.SpiritStoneStackBlock;
import com.airesnor.wuxiacraft.blocks.WuxiaBlocks;
import com.airesnor.wuxiacraft.config.WuxiaCraftConfig;
import com.airesnor.wuxiacraft.cultivation.Cultivation;
import com.airesnor.wuxiacraft.cultivation.IBarrier;
import com.airesnor.wuxiacraft.cultivation.ICultivation;
import com.airesnor.wuxiacraft.cultivation.ISealing;
import com.airesnor.wuxiacraft.cultivation.elements.Element;
import com.airesnor.wuxiacraft.cultivation.skills.ISkillCap;
import com.airesnor.wuxiacraft.cultivation.skills.Skill;
import com.airesnor.wuxiacraft.cultivation.skills.Skills;
import com.airesnor.wuxiacraft.cultivation.techniques.ICultTech;
import com.airesnor.wuxiacraft.cultivation.techniques.Techniques;
import com.airesnor.wuxiacraft.entities.mobs.WanderingCultivator;
import com.airesnor.wuxiacraft.entities.tileentity.SpiritStoneStackTileEntity;
import com.airesnor.wuxiacraft.items.ItemDagger;
import com.airesnor.wuxiacraft.items.ItemRecipe;
import com.airesnor.wuxiacraft.items.ItemSpiritStone;
import com.airesnor.wuxiacraft.items.WuxiaItems;
import com.airesnor.wuxiacraft.networking.*;
import com.airesnor.wuxiacraft.utils.CultivationUtils;
import com.airesnor.wuxiacraft.utils.MathUtils;
import com.airesnor.wuxiacraft.utils.TeleportationUtil;
import com.airesnor.wuxiacraft.world.Sect;
import com.airesnor.wuxiacraft.world.data.WorldSectData;
import com.airesnor.wuxiacraft.world.dimensions.WuxiaDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EventHandler {

	private static final String strength_mod_name = "wuxiacraft.attack_damage";
	private static final String speed_mod_name = "wuxiacraft.movement_speed";
	private static final String armor_mod_name = "wuxiacraft.armor";
	private static final String attack_speed_mod_name = "wuxiacraft.attack_speed";
	private static final String health_mod_name = "wuxiacraft.health";
	private static final String swim_mod_name = "wuxiacraft.swim";

	private static final Field foodStats = ReflectionHelper.findField(FoodStats.class, "foodSaturationLevel", "field_75125_b");

	static {
		foodStats.setAccessible(true);
	}

	/**
	 * It gives the client side information about cultivation which i stored server side
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		if (!player.world.isRemote) {
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);
			ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(player);
			NetworkWrapper.INSTANCE.sendTo(new UnifiedCapabilitySyncMessage(cultivation, cultTech, skillCap, true), (EntityPlayerMP) player);
			IBarrier barrier = CultivationUtils.getBarrierFromEntity(player);
			NetworkWrapper.INSTANCE.sendTo(new BarrierMessage(barrier, player.getUniqueID()), (EntityPlayerMP) player);
		}
		TextComponentString text = new TextComponentString("For a quick tutorial on the mod. \nPlease use the /culthelp command");
		text.getStyle().setColor(TextFormatting.GOLD);
		player.sendMessage(text);
	}

	/**
	 * Handles what happens when the play chats.
	 *
	 * @param event the event when the player chats, server-side
	 */
	@SubscribeEvent
	public void onPlayerChat(ServerChatEvent event) {
		EntityPlayerMP playerMP = event.getPlayer();
		if (playerMP != null) {
			WorldSectData sectData = WorldSectData.get(playerMP.getEntityWorld());
			Sect sect = Sect.getSectByPlayer(playerMP, sectData);
			if (sect != null) {
				String prefix = "[" + sect.getSectTag().toUpperCase() + "]";
				ITextComponent component = event.getComponent();
				ITextComponent newComponent = new TextComponentString(sect.getColour() + prefix);
				newComponent.appendSibling(component);
				event.setComponent(newComponent);
			}
		}
	}

	/**
	 * The big great logic behind cultivation happens here
	 * This is the DanTian dark matter
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);
			ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(player);

			cultivation.advTimer();
			cultivation.lessenPillCooldown();
			//each 100 ticks will sync the cultivation
			if (cultivation.getUpdateTimer() == 100) {
				if (!player.world.isRemote) {
					NetworkWrapper.INSTANCE.sendTo(new UnifiedCapabilitySyncMessage(cultivation, cultTech, skillCap, false), (EntityPlayerMP) player);
				}
				cultivation.resetTimer();
			}
			//each 20 ticks to apply modifiers, i guess that every tick is too cpu consuming
			// let's say you have 100 players on a server
			if (cultivation.getUpdateTimer() % 20 == 0) {
				if (!player.world.isRemote) {
					applyModifiers(player);
				}
			}
			if (player.world.isRemote) {
				boolean canFly = cultivation.getEssenceLevel().getModifierBySubLevel(cultivation.getEssenceSubLevel()) > 1100;
				if (canFly && player.capabilities.isFlying && cultivation.getEnergy() <= 0) {
					player.capabilities.isFlying = false;
				}
				if (canFly) {
					player.capabilities.allowFlying = true;
					player.capabilities.setFlySpeed((float) player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				}
				player.stepHeight = 0.6f;
				if (!player.isSneaking() && WuxiaCraftConfig.disableStepAssist) {
					double agilityModifier = CultivationUtils.getAgilityFromEntity(player);
					double dexterityModifier = CultivationUtils.getDexterityFromEntity(player);
					double strengthModifier = CultivationUtils.getStrengthFromEntity(player);
					player.stepHeight = Math.min(cultivation.getStepAssistLimit(), 0.6f * (1 + 0.15f * (float) (agilityModifier + dexterityModifier + strengthModifier)));
				}
				player.sendPlayerAbilities();
			}


			double energy = CultivationUtils.getMaxEnergy(player) * 0.00025;
			//add a little of soul modifier to help out since soul affects perception
			energy += cultivation.getDivineModifier() * 0.003;
			cultivation.addEnergy(energy);
			if (cultivation.getEnergy() > CultivationUtils.getMaxEnergy(player)) {
				cultivation.setEnergy(CultivationUtils.getMaxEnergy(player));
			}

		}
	}

	/**
	 * A toggle for not spamming messages
	 */
	private boolean toggleSneaking = false;

	/**
	 * Handles whenever a player sneak it will get nearby players cultivation
	 *
	 * @param event Description of whats happening
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerRequestLevels(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if (event.player.isSneaking()) {
				if (!toggleSneaking) {
					toggleSneaking = true;
					EntityPlayer player = event.player;
					ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
					NetworkWrapper.INSTANCE.sendToServer(new AskCultivationLevelMessage(cultivation.getEssenceLevel(), cultivation.getEssenceSubLevel(), player.getUniqueID()));
				}
			} else {
				toggleSneaking = false;
			}
		}
	}


	//So that formations doesn't overwork too
	private static long LastPlayerTickTime = 0;

	/**
	 * Handles the skills logic, cooldown and casting
	 * New casting logic: client handles cooldown and cast progress, client/server activates skills
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerProcessSkills(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);
			ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(player);
			float dexterityModifier = (float) CultivationUtils.getDexterityFromEntity(player);
			if (player.world.isRemote) {
				long timeDiff = System.currentTimeMillis() - LastPlayerTickTime;
				if (timeDiff >= 50) { //20 per seconds
					skillCap.setFormationActivated(false); //allow next tick formations to do something here
					LastPlayerTickTime = System.currentTimeMillis();
				}
			} else {
				skillCap.setFormationActivated(false); //server won't have speed hack i hope
			}
			if (skillCap.getCooldown() > 0) {
				skillCap.stepCooldown(-1 - dexterityModifier * 0.3f);
			} else {
				if (player.world.isRemote) {
					if (skillCap.getActiveSkill() >= 0 && !skillCap.getSelectedSkills().isEmpty()) {
						Skill selectedSkill = skillCap.getSelectedSkill(cultTech);
						if (selectedSkill != null) {
							if (skillCap.isCasting()) {
								if (cultivation.hasEnergy(selectedSkill.getCost())) {
									if (skillCap.getCastProgress() < selectedSkill.getCastTime() && selectedSkill.castingEffect(player)) {
										if (selectedSkill.castNotSpeedable) skillCap.stepCastProgress(1);
										else
											skillCap.stepCastProgress(Math.min(dexterityModifier * 0.4f, selectedSkill.getCastTime() / 10));
									}
									if (skillCap.getCastProgress() >= selectedSkill.getCastTime()) {
										if (selectedSkill.activate(player)) {
											skillCap.resetCastProgress();
											if (!player.isCreative())
												cultivation.remEnergy(selectedSkill.getCost());
											NetworkWrapper.INSTANCE.sendToServer(new ActivateSkillMessage(skillCap.getActiveSkill(), player.getUniqueID()));
											CultivationUtils.cultivatorAddProgress(player, Cultivation.System.ESSENCE, selectedSkill.getProgress(), true, false);
										}
									}
								}
							}
							if (skillCap.isDoneCasting()) {
								skillCap.setDoneCasting(false);
								skillCap.resetCastProgress();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Used client side knowing the server side limiter
	 */
	public static double maxServerSpeed = 10.0;

	/**
	 * Desperate tentative of not increasing the FOV, reduced a lot
	 *
	 * @param e Description of whats happening
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public void fovChange(FOVUpdateEvent e) {
		float f = 1.0F;
		EntityPlayer entity = e.getEntity();
		if (entity.capabilities.isFlying) {
			f *= 1.1F;
		}

		IAttributeInstance iattributeinstance = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		double spd = CultivationUtils.getAgilityFromEntity(entity);
		if (maxServerSpeed >= 0) {
			spd = Math.min(spd, maxServerSpeed);
		}

		spd *= entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();

		f = (float) ((double) f * (((iattributeinstance.getAttributeValue() - spd) / (double) entity.capabilities.getWalkSpeed() + 1.0D) / 2.0D));


		if (entity.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
			f = 1.0F;
		}

		if (entity.isHandActive() && entity.getActiveItemStack().getItem() == net.minecraft.init.Items.BOW) {
			int i = entity.getItemInUseMaxCount();
			float f1 = (float) i / 20.0F;

			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
		}
		e.setNewfov(f);
	}

	/**
	 * A toggle for not spamming messages
	 */
	private boolean toggleHasInGameFocus = false;

	/**
	 * Accumulates the fly cost to send to the server later
	 * This is an attempt to reduce the energy messages when someone's flying
	 */
	private float accumulatedFlyCost = 0f;

	/**
	 * Client side logic on every tick update
	 * It seems flight is only calculated client side
	 * Lame
	 *
	 * @param event Description of whats happening
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			EntityPlayer player = event.player;
			if (!player.world.isRemote) return;
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);

			if (!Minecraft.getMinecraft().inGameHasFocus) {
				if (!toggleHasInGameFocus) {
					toggleHasInGameFocus = true;
					ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(Minecraft.getMinecraft().player);
					skillCap.setCasting(false);
					skillCap.setDoneCasting(true);
				}
			} else if (toggleHasInGameFocus) {
				toggleHasInGameFocus = false;
			}

			float distance = (float) Math.sqrt(Math.pow(player.lastTickPosX - player.posX, 2) + Math.pow(player.lastTickPosY - player.posY, 2) + Math.pow(player.lastTickPosZ - player.posZ, 2));

			if (player.capabilities.isFlying) {
				float totalRem = 0f;
				float fly_cost = 2500f;
				float dist_cost = 1320f;
				if (cultivation.getEssenceLevel().getModifierBySubLevel(cultivation.getEssenceSubLevel()) < 160000) { // cannot fly freely
					totalRem += fly_cost;
				}
				if (distance > 0) {
					totalRem += distance * dist_cost;
				}
				if (!player.isCreative()) {
					cultivation.remEnergy(totalRem);
					accumulatedFlyCost += totalRem;
					if (cultivation.getUpdateTimer() % 10 == 0) {
						NetworkWrapper.INSTANCE.sendToServer(new EnergyMessage(1, accumulatedFlyCost, player.getUniqueID()));
						accumulatedFlyCost = 0;
					}
				}
			} /*else { //flying is not an exercise
			//CultivationUtils.cultivatorAddProgress(player, cultivation, distance * 0.1f);
			//NetworkWrapper.INSTANCE.sendToServer(new ProgressMessage(0, distance * 0.1f));
		}*/
		}
	}

	/**
	 * Whe a player jump, the get jump bonus base on its cultivation speed bonus
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityLivingBase player = event.getEntityLiving();
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(event.getEntityLiving());
			double baseJumpSpeed = event.getEntity().motionY;
			double agilityModifier = CultivationUtils.getAgilityFromEntity(player);
			double strengthModifier = CultivationUtils.getStrengthFromEntity(player);
			double jumpSpeed = 0.08f * (agilityModifier + strengthModifier);
			if (cultivation.getJumpLimit() >= 0) {
				jumpSpeed = Math.min(jumpSpeed, cultivation.getJumpLimit());
			}
			jumpSpeed *= baseJumpSpeed;
			event.getEntity().motionY += jumpSpeed;
		}
	}

	/**
	 * When a cultivator fall they'll have some fall height removed before damage is calculated
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onCultivatorFall(LivingFallEvent event) {
		EntityLivingBase player = event.getEntityLiving();
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(event.getEntityLiving());
		if (event.getEntityLiving().world.isRemote || event.getDistance() < 3) return;
		if ((cultivation.getBodyModifier() + cultivation.getEssenceModifier() * 0.8 + cultivation.getDivineModifier() * 0.2) > 100000) {
			event.setDistance(0);
		} else {
			double agilityModifier = CultivationUtils.getAgilityFromEntity(player);
			double constitutionModifier = CultivationUtils.getConstitutionFromEntity(player);
			double strengthModifier = CultivationUtils.getStrengthFromEntity(player);
			event.setDistance(event.getDistance() - 0.9f * (float) (agilityModifier + strengthModifier + constitutionModifier));
		}
	}

	/**
	 * When the player hits an entity, gain a little progress, but i'm regretting
	 * Also applies 1 damage to entity if wearing dagger
	 * <p>
	 * Extra from techniques if attacker kills using buddha soul technique, he loses sub levels and progress and foundation
	 * Extra from techniques, if attacker has nine springs, he can deal extra damage against living
	 *								^ or yin body art
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerHitEntity1(LivingDamageEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (!player.world.isRemote) {
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
				CultivationUtils.cultivatorAddProgress(player, Cultivation.System.BODY, 0.25f, false, false);
				ICultTech cultTech = CultivationUtils.getCultTechFromEntity(event.getEntityLiving());
				if (cultTech.getDivineTechnique() != null) {
					if (event.getEntityLiving().getHealth() <= 0) {
						if (cultTech.getDivineTechnique().getTechnique().equals(Techniques.BUDDHA_S_HEAVENLY_WAY)) {
							cultivation.setDivineSubLevel(0);
							cultivation.setDivineProgress(0);
							cultivation.setDivineFoundation(cultivation.getDivineFoundation() * 0.3);
						}
					} else if (cultTech.getDivineTechnique().getTechnique().equals(Techniques.NINE_SPRINGS_SOUL)) {
						double modifier = cultivation.getDivineModifier() * (cultTech.getDivineTechnique().getProficiency()
								/ cultTech.getDivineTechnique().getTechnique().getMaxProficiency()) * 0.5;
						event.setAmount(event.getAmount() * (1 + (float) modifier));
					}
					if (cultTech.getBodyTechnique() != null) {
						if (cultTech.getBodyTechnique().getTechnique().equals(Techniques.YIN_BODY_ART)) {
							double modifier = cultivation.getBodyModifier() * (cultTech.getBodyTechnique().getProficiency()
									/ cultTech.getBodyTechnique().getTechnique().getMaxProficiency()) * 0.5;
							event.setAmount(event.getAmount() * (1 + (float) modifier));
						}
					}
				}
				NetworkWrapper.INSTANCE.sendTo(new CultivationMessage(cultivation), (EntityPlayerMP) player);
			}
			ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
			if (stack.getItem() instanceof ItemDagger) {
				event.setAmount(1);
			}
		}
	}

	/**
	 * When they break some block, they gain a little progress based on the blocks hardness
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerDigBlock(BlockEvent.HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player != null && !player.world.isRemote) {
			IBlockState block = event.getState();
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
			CultivationUtils.cultivatorAddProgress(player, Cultivation.System.BODY, 0.1f * block.getBlockHardness(event.getWorld(), event.getPos()), false, false);
			NetworkWrapper.INSTANCE.sendTo(new CultivationMessage(cultivation), (EntityPlayerMP) player);
		}
	}

	/**
	 * Increases a little the players break speed based on its strength
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(event.getEntityPlayer());
		float baseSpeed = event.getOriginalSpeed();
		double dexterityModifier = CultivationUtils.getDexterityFromEntity(event.getEntityLiving());
		double strengthModifier = CultivationUtils.getStrengthFromEntity(event.getEntityLiving());
		float hasteModifier = (float) (0.1f * baseSpeed * (strengthModifier * 0.7 + dexterityModifier * 0.3));
		if (cultivation.getHasteLimit() >= 0) {
			hasteModifier = Math.min(hasteModifier, cultivation.getHasteLimit() * baseSpeed);
		}
		event.setNewSpeed(baseSpeed + hasteModifier); //so we don't remove vanilla modifier, just add over it
	}

	/**
	 * When the player die, he gets punished
	 * Loose 20% points of every foundation and their progress
	 *
	 * @param event Description of what happened, and what will come to be
	 */
	@SubscribeEvent
	public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		EntityPlayer original = event.getOriginal();
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
		ICultivation oldCultivation = CultivationUtils.getCultivationFromEntity(original);
		ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);
		ICultTech oldCultTech = CultivationUtils.getCultTechFromEntity(original);
		ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(player);
		ISkillCap oldSkillCap = CultivationUtils.getSkillCapFromEntity(original);
		ISealing sealing = CultivationUtils.getSealingFromEntity(player);
		ISealing oldSealing = CultivationUtils.getSealingFromEntity(original);
		IBarrier barrier = CultivationUtils.getBarrierFromEntity(player);
		IBarrier oldBarrier = CultivationUtils.getBarrierFromEntity(original);
		if (event.isWasDeath()) {
			cultivation.setBodyLevel(oldCultivation.getBodyLevel());
			cultivation.setDivineLevel(oldCultivation.getDivineLevel());
			cultivation.setEssenceLevel(oldCultivation.getEssenceLevel());
			cultivation.setBodySubLevel(Math.max(3 * (oldCultivation.getBodySubLevel() / 3), 0));
			cultivation.setDivineSubLevel(Math.max(3 * (oldCultivation.getDivineSubLevel() / 3), 0));
			cultivation.setEssenceSubLevel(Math.max(3 * (oldCultivation.getEssenceSubLevel() / 3), 0));
			double bodyModifier = cultivation.getBodyLevel().getProgressBySubLevel(cultivation.getBodySubLevel())
					/ oldCultivation.getBodyLevel().getProgressBySubLevel(oldCultivation.getBodySubLevel());
			double divineModifier = cultivation.getDivineLevel().getProgressBySubLevel(cultivation.getDivineSubLevel())
					/ oldCultivation.getDivineLevel().getProgressBySubLevel(oldCultivation.getDivineSubLevel());
			double essenceModifier = cultivation.getEssenceLevel().getProgressBySubLevel(cultivation.getEssenceSubLevel())
					/ oldCultivation.getEssenceLevel().getProgressBySubLevel(oldCultivation.getEssenceSubLevel());
			cultivation.setBodyFoundation(bodyModifier * Math.max(oldCultivation.getBodyFoundation() * 0.8,
					oldCultivation.getBodyFoundation() - 3 * oldCultivation.getBodyLevel().getProgressBySubLevel(oldCultivation.getBodySubLevel())));
			cultivation.setDivineFoundation(divineModifier * Math.max(oldCultivation.getDivineFoundation() * 0.8,
					oldCultivation.getDivineFoundation() - 3 * oldCultivation.getDivineLevel().getProgressBySubLevel(oldCultivation.getDivineSubLevel())));
			cultivation.setEssenceFoundation(essenceModifier * Math.max(oldCultivation.getEssenceFoundation() * 0.8,
					oldCultivation.getEssenceFoundation() - 3 * oldCultivation.getEssenceLevel().getProgressBySubLevel(oldCultivation.getEssenceSubLevel())));
		} else {
			cultivation.copyFrom(oldCultivation);
		}

		WuxiaCraft.logger.info("Restoring " + player.getDisplayNameString() + " cultivation.");
		cultTech.copyFrom(oldCultTech);
		skillCap.copyFrom(oldSkillCap, true);
		sealing.copyFrom(oldSealing);
		barrier.copyFrom(oldBarrier);
		barrier.setBarrierMaxAmount((float) Math.max(0, (cultivation.getEssenceModifier() - 3.0) * 0.5));
		barrier.setBarrierRegenRate(barrier.getBarrierMaxAmount() * 0.001f);
	}

	/**
	 * Restores the players cultivation when they respawn
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (event.player.world.isRemote) return;
		EntityPlayer player = event.player;
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
		ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);
		ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(player);
		WuxiaCraft.logger.info("Applying " + player.getDisplayNameString() + " cultivation.");
		NetworkWrapper.INSTANCE.sendTo(new UnifiedCapabilitySyncMessage(cultivation, cultTech, skillCap, true), (EntityPlayerMP) player);
		applyModifiers(player);
	}

	/**
	 * When a mob dies and we get it's loot table and add another loot
	 * Also add a recipe for blank recipes
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onMobDrop(LivingDropsEvent event) {
		if (event.getEntity() instanceof WanderingCultivator) {
			//scrolls
			Random rnd = event.getEntity().world.rand;
			ItemStack drop = new ItemStack(WuxiaItems.TECHNIQUES_SCROLL.get(rnd.nextInt(WuxiaItems.TECHNIQUES_SCROLL.size())), 1);
			if (rnd.nextInt(50) == 1) {
				event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, drop));
			}
			for (EntityItem item : event.getDrops()) {
				ItemStack stack = item.getItem();
				if (stack.getItem() == WuxiaItems.RECIPE_SCROLL) {
					ItemRecipe.setRecipeAtRandom(stack);
				}
				item.setItem(stack);
			}
		}
	}

	/**
	 * Makes players that fly too high find their respective dimension, or random
	 * And come back to over world again
	 *
	 * @param event A description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerFlyToAnotherDimension(TickEvent.PlayerTickEvent event) {
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(event.player);
	if (cultivation.getUpdateTimer() == 99) {
		if (event.side == Side.SERVER) {
			if (event.phase == TickEvent.Phase.END) {
				if (event.player.posY >= 3072) {
					if (event.player.world.provider.getDimension() == 0) {
						if (CultivationUtils.getMaxEnergy(event.player) > 100000) { //if energy may work as a parameter for levels
							double playerPosX = event.player.posX;
							double playerPosZ = event.player.posZ;
							ICultTech cultTech = CultivationUtils.getCultTechFromEntity(event.player);
							boolean found = false;
							int targetDimension = WuxiaDimensions.FIRE.getId();
							if (cultTech.hasElement(Element.FIRE) || cultTech.hasElement(Element.EARTH) || cultTech.hasElement(Element.METAL) || cultTech.hasElement(Element.WATER) || cultTech.hasElement(Element.WOOD)) {
								found = true;
								if (cultTech.hasElement(Element.EARTH)) {
									targetDimension = WuxiaDimensions.EARTH.getId();
								} else if (cultTech.hasElement(Element.METAL)) {
									targetDimension = WuxiaDimensions.METAL.getId();
								} else if (cultTech.hasElement(Element.WATER)) {
									targetDimension = WuxiaDimensions.WATER.getId();
								} else if (cultTech.hasElement(Element.WOOD)) {
									targetDimension = WuxiaDimensions.WOOD.getId();
								}
							}
							if (!found) { //couldn't find one element
								int target = event.player.getRNG().nextInt(5); //0 to 4
								switch (target) {
									case 0:
										targetDimension = WuxiaDimensions.FIRE.getId();
										break;
									case 1:
										targetDimension = WuxiaDimensions.EARTH.getId();
										break;
									case 2:
										targetDimension = WuxiaDimensions.METAL.getId();
										break;
									case 3:
										targetDimension = WuxiaDimensions.WATER.getId();
										break;
									case 4:
										targetDimension = WuxiaDimensions.WOOD.getId();
										break;
								}
							}
							TeleportationUtil.teleportPlayerToDimension((EntityPlayerMP) event.player, targetDimension, playerPosX, 1512, playerPosZ, event.player.rotationYaw, event.player.rotationPitch);
							double resistance = cultivation.getBodyModifier() * 0.4 + cultivation.getEssenceModifier() * 0.8 + cultivation.getEssenceModifier() + 0.3;
							event.player.attackEntityFrom(DamageSource.OUT_OF_WORLD, (float) Math.max(1, 3200 - resistance));
						}
					} else if (MathUtils.inGroup(event.player.world.provider.getDimension(),
							WuxiaDimensions.EARTH.getId(),
							WuxiaDimensions.WOOD.getId(),
							WuxiaDimensions.METAL.getId(),
							WuxiaDimensions.WATER.getId(),
							WuxiaDimensions.FIRE.getId(),
							WuxiaDimensions.MINING.getId())) {
						double playerPosX = event.player.posX;
						double playerPosZ = event.player.posZ;
						//back to over world
						TeleportationUtil.teleportPlayerToDimension((EntityPlayerMP) event.player, 0, playerPosX + 0.5, 1512, playerPosZ + 0.5, event.player.rotationYaw, event.player.rotationPitch);
						}
					}
				}
			}
		}
	}

	/**
	 * Destroys scheduled blocks from players skills, only 5 blocks per tick
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onBreakScheduledBlocks(TickEvent.PlayerTickEvent event) {
		ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(event.player);
		int i = 5;
		while (!skillCap.isScheduledEmpty() && i > 0) {
			i--;
			event.player.world.destroyBlock(skillCap.popScheduledBlockBreaks(), true);
		}
	}

	/**
	 * When a mob spawn, it also searches for wandering cultivators
	 *
	 * @param event Description of whats happening
	 */
	@SubscribeEvent
	public void onSpawnLiving(LivingSpawnEvent event) {
		if (event.getEntityLiving() instanceof EntityMob && !(event.getEntityLiving() instanceof EntityCreeper)
		&& !(event.getEntityLiving() instanceof EntityPigZombie) && !(event.getEntityLiving() instanceof EntityEnderman)) {
			((EntityMob) event.getEntityLiving()).targetTasks.addTask(2, new EntityAINearestAttackableTarget<>((EntityCreature) event.getEntityLiving(), WanderingCultivator.class, true));
		}
	}

	/**
	 * This will get EntityItems that are from spirit stones and turn them into a stock pile;
	 *
	 * @param event Description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onSpiritStoneStackFloats(TickEvent.WorldTickEvent event) {
		if (event.side == Side.SERVER && event.phase == TickEvent.Phase.END) {
			List<EntityItem> items = event.world.getEntities(EntityItem.class, input -> {
				boolean isSpiritStone = false;
				if (input != null) {
					if (input.getItem().getItem() instanceof ItemSpiritStone)
						isSpiritStone = true;
				}
				return isSpiritStone && input.ticksExisted >= 40 && input.getItem().getItemDamage() == 0;
			}); // 2 seconds existing right
			for (EntityItem item : items) {
				ItemStack stack = item.getItem();
				BlockPos pos = item.getPosition();
				Block spiritStones = WuxiaBlocks.SPIRIT_STONE_STACK_BLOCK;
				if (event.world.mayPlace(spiritStones, pos, true, EnumFacing.NORTH, item)) {
					event.world.setBlockState(pos, spiritStones.getDefaultState());
					SpiritStoneStackTileEntity te = (SpiritStoneStackTileEntity) event.world.getTileEntity(pos);
					if (te != null) {
						te.stack = stack;
					}
					item.setDead();
				} else if (event.world.getBlockState(pos).getBlock() == WuxiaBlocks.SPIRIT_STONE_STACK_BLOCK) {
					SpiritStoneStackTileEntity te = (SpiritStoneStackTileEntity) event.world.getTileEntity(pos);
					if (te != null) {
						if (te.stack.getItem() == stack.getItem()) { //meaning they're the same
							int remaining = te.stack.getMaxStackSize() - te.stack.getCount();
							int having = stack.getCount();
							int applying = Math.min(remaining, having);
							int left = having - applying;
							int right = te.stack.getCount() + applying;
							te.stack.setCount(right);
							stack.setCount(left);
							if (stack.isEmpty()) item.setDead();
							event.world.markAndNotifyBlock(pos, null, event.world.getBlockState(pos), event.world.getBlockState(pos), 3);
						}
					}
				}
			}
		}
	}

	/**
	 * Block#onBlockActivated wouldn't be called when sneaking, so i had to hack
	 *
	 * @param event A description of whats happening
	 */
	@SubscribeEvent
	public void onPlayerInteractSneaking(PlayerInteractEvent.RightClickBlock event) {
		if (event.getEntityPlayer().isSneaking() && event.getHand() == EnumHand.MAIN_HAND) {
			IBlockState blockState = event.getWorld().getBlockState(event.getPos());
			if (blockState.getBlock() instanceof SpiritStoneStackBlock) {
				Vec3d hit = event.getHitVec();
				if (blockState.getBlock().onBlockActivated(event.getWorld(), event.getPos(), blockState, event.getEntityPlayer(), event.getHand(), Objects.requireNonNull(event.getFace()), (float) hit.x, (float) hit.y, (float) hit.z)) {
					event.setUseBlock(Event.Result.ALLOW);
					event.setCanceled(true);
				}
			}
		}
	}

	/**
	 * When player has a cultivation that allows
	 *
	 * @param event A description of whats happening
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerHunger(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
		double cost = 9000;
		//first of all, only essence can be used as food
		if (player.ticksExisted % 100 == 0) {
			if (player.getFoodStats().getFoodLevel() < 20 && cultivation.getEssenceModifier() > 130) {
				if (cultivation.getEssenceModifier() > 16000) { // free food
					player.getFoodStats().setFoodLevel(20);
					try {
						foodStats.setFloat(player.getFoodStats(), 20f);
					} catch (Exception e) {
						WuxiaCraft.logger.error("Couldn't help with food, sorry!");
						e.printStackTrace();
					}
				} else if (cultivation.hasEnergy(cost)) {
					player.getFoodStats().setFoodLevel(20);
					try {
						foodStats.setFloat(player.getFoodStats(), 20f);
						cultivation.remEnergy(cost);
					} catch (Exception e) {
						WuxiaCraft.logger.error("Couldn't help with food, sorry!");
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Applies the modifiers to the corresponding player based on its cultivation
	 *
	 * @param player Player to be applied
	 */
	public static void applyModifiers(EntityPlayer player) {

		ICultTech cultTech = CultivationUtils.getCultTechFromEntity(player);

		//Adds the potions effects from cult tech
		for (PotionEffect effect : cultTech.getTechniquesEffects()) {
			player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() + 19, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
		}

		double spd = CultivationUtils.getAgilityFromEntity(player);
		if (WuxiaCraftConfig.maxServerSpeed >= 0) {
			spd = Math.min(spd, WuxiaCraftConfig.maxServerSpeed);
		}
		spd *= player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();

		double str = CultivationUtils.getStrengthFromEntity(player);
		double hp = CultivationUtils.getConstitutionFromEntity(player);
		double armor = CultivationUtils.getResistanceFromEntity(player);
		double atk_sp = CultivationUtils.getDexterityFromEntity(player);

		AttributeModifier strength_mod = new AttributeModifier(strength_mod_name, str, 0);
		AttributeModifier health_mod = new AttributeModifier(health_mod_name, hp, 0);
		//since armor base is 0, it'll add 2*strength as armor
		//I'll use for now strength for increase every other stat, since it's almost the same after all
		AttributeModifier armor_mod = new AttributeModifier(armor_mod_name, armor, 0);
		AttributeModifier speed_mod = new AttributeModifier(speed_mod_name, spd, 0);
		AttributeModifier attack_speed_mod = new AttributeModifier(attack_speed_mod_name, atk_sp, 0);
		AttributeModifier swim_speed_mod = new AttributeModifier(swim_mod_name, spd * (cultTech.hasElement(Element.WATER) ? 1 : 0.05), 0);

		//remove any previous strength modifiers
		for (AttributeModifier mod : player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifiers()) {
			if (mod.getName().equals(strength_mod_name)) {
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(mod);
			}
		}

		//remove any previous speed modifiers
		for (AttributeModifier mod : player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifiers()) {
			if (mod.getName().equals(speed_mod_name)) {
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(mod);
			}
		}

		//remove any previous attack speed modifiers
		for (AttributeModifier mod : player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifiers()) {
			if (mod.getName().equals(attack_speed_mod_name)) {
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(mod);
			}
		}

		//remove any previous armor modifiers
		for (AttributeModifier mod : player.getEntityAttribute(SharedMonsterAttributes.ARMOR).getModifiers()) {
			if (mod.getName().equals(armor_mod_name)) {
				player.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(mod);
			}
		}

		//remove any previous max health modifiers
		for (AttributeModifier mod : player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifiers()) {
			if (mod.getName().equals(health_mod_name)) {
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(mod);
			}
		}

		//remove any previous swim speed modifiers
		for (AttributeModifier mod : player.getEntityAttribute(EntityPlayer.SWIM_SPEED).getModifiers()) {
			if (mod.getName().equals(swim_mod_name)) {
				player.getEntityAttribute(EntityPlayer.SWIM_SPEED).removeModifier(mod);
			}
		}

		//apply current modifiers
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strength_mod);
		player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speed_mod);
		player.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(armor_mod);
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(attack_speed_mod);
		player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(health_mod);
		player.getEntityAttribute(EntityPlayer.SWIM_SPEED).applyModifier(swim_speed_mod);

		//WuxiaCraft.logger.info(String.format("Applying %s modifiers from %s.", player.getDisplayNameString(), cultivation.getCurrentLevel().getLevelName(cultivation.getCurrentSubLevel())));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onDefendingAgainstTheRageSaint(LivingAttackEvent event) {
		if (event.isCanceled()) return;
		if (event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource() instanceof Skills.HealingDamageSource)) {
			ICultTech cultTech = CultivationUtils.getCultTechFromEntity((EntityLivingBase) event.getSource().getTrueSource());
			if (cultTech.getDivineTechnique() != null) {
				if (cultTech.getDivineTechnique().getTechnique().equals(Techniques.RAGEFUL_ABNEGATION_SAINT_ARTS)) {
					event.getEntityLiving().heal(event.getAmount() * 0.1f);
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerDefense(LivingAttackEvent event) {
		if (event.isCanceled()) return;
		if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
		if (event.getAmount() <= 0.0f) return;

		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if (!player.world.isRemote) {
			IBarrier barrier = CultivationUtils.getBarrierFromEntity(player);
			if (barrier.isBarrierActive() && !barrier.isBarrierBroken()) {
				handleBarrier(event, barrier, player);
				TextComponentString message = new TextComponentString("Barrier Amount: " + barrier.getBarrierAmount());
				message.getStyle().setColor(TextFormatting.AQUA);
				player.sendMessage(message);
			}//the way it was before was canceling the other cancels, bring it back to execute
		}
	}

	// TODO - Handle the damage cancelling selectively

	/**
	 * Handles the barrier, cancels all damage at the moment
	 *
	 * @param event   LivingAttackEvent
	 * @param barrier Barrier instance of the player
	 * @param player  EntityPlayer instance of player
	 */
	public void handleBarrier(LivingAttackEvent event, IBarrier barrier, EntityPlayer player) {
		if (event.isCanceled()) return;
		if (event.getSource().isDamageAbsolute()) {
			if (barrier.getBarrierAmount() > 0.0f && event.getAmount() <= barrier.getBarrierAmount()) {
				event.setCanceled(true);
				barrier.removeBarrierAmount(event.getAmount());
			} else if (barrier.getBarrierAmount() > 0.0f && event.getAmount() > barrier.getBarrierAmount()) {
				float remainingDamage = event.getAmount() - barrier.getBarrierAmount();
				barrier.removeBarrierAmount(barrier.getBarrierAmount());
				barrier.setBarrierCooldown(Math.min(barrier.getBarrierMaxCooldown(), 3000 + (100 * barrier.getBarrierHits())));
				barrier.setBarrierBroken(true);
				barrier.setBarrierActive(false);
				event.setCanceled(true);
				if (remainingDamage > 0) {
					player.attackEntityFrom(event.getSource(), remainingDamage);
				}
			} else if (barrier.getBarrierAmount() <= 0.0f) {
				barrier.setBarrierCooldown(Math.min(barrier.getBarrierMaxCooldown(), 3000 + (100 * barrier.getBarrierHits())));
				barrier.setBarrierBroken(true);
				barrier.setBarrierActive(false);
			}
		} else {
			ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
			float armour = (float) Math.max(1, cultivation.getEssenceModifier() * 0.6);
			if (barrier.getBarrierAmount() > 0.0f && (event.getAmount() - armour) < barrier.getBarrierAmount()) {
				event.setCanceled(true);
				barrier.removeBarrierAmount(event.getAmount() - armour);
			} else if (barrier.getBarrierAmount() > 0.0f && (event.getAmount() - armour) > barrier.getBarrierAmount()) {
				float remainingDamage = (event.getAmount() - armour) - barrier.getBarrierAmount();
				barrier.removeBarrierAmount(barrier.getBarrierAmount());
				barrier.setBarrierCooldown(Math.min(barrier.getBarrierMaxCooldown(), 3000 + (100 * barrier.getBarrierHits())));
				barrier.setBarrierBroken(true);
				barrier.setBarrierActive(false);
				event.setCanceled(true);
				if (remainingDamage > 0) {
					player.attackEntityFrom(event.getSource(), Math.max(0, remainingDamage));
				}
			} else if (barrier.getBarrierAmount() <= 0.0f) {
				barrier.setBarrierCooldown(Math.min(barrier.getBarrierMaxCooldown(), 3000 + (100 * barrier.getBarrierHits())));
				barrier.setBarrierBroken(true);
				barrier.setBarrierActive(false);
			}
		}
		NetworkWrapper.INSTANCE.sendTo(new BarrierMessage(barrier, player.getUniqueID()), (EntityPlayerMP) player);
	}

	/**
	 * Handles the player barrier update every 20 ticks / 1 second
	 *
	 * @param event Player Update Event
	 */
	@SubscribeEvent
	public void onPlayerBarrierUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (!player.world.isRemote && player.ticksExisted % 20 == 0) {
				IBarrier barrier = CultivationUtils.getBarrierFromEntity(player);
				ICultivation cultivation = CultivationUtils.getCultivationFromEntity(player);
				//Aires: I vote to remEnergy only when barrier regen

				// Energy Drain while the barrier is active
				if (barrier.isBarrierActive() && !barrier.isBarrierBroken()) {
					cultivation.remEnergy(CultivationUtils.getMaxEnergy(player) * 0.00005F);
				}
				// Barrier regen when the barrier is not broken
				if (!barrier.isBarrierBroken() && barrier.isBarrierRegenActive() && barrier.getBarrierAmount() + barrier.getBarrierRegenRate() < barrier.getBarrierMaxAmount()) {
					barrier.addBarrierAmount(barrier.getBarrierRegenRate());
					cultivation.remEnergy(CultivationUtils.getMaxEnergy(player) * 0.00005F);
				} else if (barrier.getBarrierAmount() + barrier.getBarrierRegenRate() >= barrier.getBarrierMaxAmount()) {
					barrier.setBarrierAmount(barrier.getBarrierMaxAmount());
				}
				// Cooldown
				if (barrier.getBarrierCooldown() <= 0) {
					barrier.setBarrierBroken(false);
				} else {
					barrier.removeBarrierCooldown(20);
				}
				NetworkWrapper.INSTANCE.sendTo(new BarrierMessage(barrier, player.getUniqueID()), (EntityPlayerMP) player);
			}
		}
	}

}