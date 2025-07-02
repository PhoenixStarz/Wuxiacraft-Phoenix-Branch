package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.cultivation.CultivationRealm;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.Element;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPart;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPartType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.SkillParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class WuxiaRegistries {

	public static Supplier<IForgeRegistry<CultivationRealm>> CULTIVATION_REALMS;

	public static Supplier<IForgeRegistry<CultivationStage>> CULTIVATION_STAGES;

	public static Supplier<IForgeRegistry<Element>> ELEMENTS;

	public static Supplier<IForgeRegistry<TechniqueAspect>> TECHNIQUE_ASPECT;

	public static Supplier<IForgeRegistry<SkillAspectType>> SKILL_ASPECT;

	public static Supplier<IForgeRegistry<BodyPart>> BODY_PART;

	public static Supplier<IForgeRegistry<BodyPartType>> BODY_PART_TYPE;
}
