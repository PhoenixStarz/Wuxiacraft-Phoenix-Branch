package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.command.AspectArgument;
import com.lazydragonstudios.wuxiacraft.command.ElementArgument;
import com.lazydragonstudios.wuxiacraft.command.StageArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WuxiaArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(
        ForgeRegistries.COMMAND_ARGUMENT_TYPES, WuxiaCraft.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<AspectArgument,
        SingletonArgumentInfo<AspectArgument>.Template>> ASPECT_RESLOCATION = register(AspectArgument.class,
        "aspect", SingletonArgumentInfo.contextFree(AspectArgument::id)
    );

	public static final RegistryObject<ArgumentTypeInfo<ElementArgument,
        SingletonArgumentInfo<ElementArgument>.Template>> ELEMENT_RESLOCATION = register(ElementArgument.class,
        "element", SingletonArgumentInfo.contextFree(ElementArgument::id)
    );

	public static final RegistryObject<ArgumentTypeInfo<StageArgument,
        SingletonArgumentInfo<StageArgument>.Template>> STAGE_RESLOCATION = register(StageArgument.class,
        "stage", SingletonArgumentInfo.contextFree(StageArgument::id)
    );

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<ArgumentTypeInfo<A, T>> register(
        Class<A> clazz, String name, ArgumentTypeInfo<A, T> ati) {
        var robj = ARGUMENT_TYPES.register(name, () -> ati);
        ArgumentTypeInfos.registerByClass(clazz, ati);
        return robj;
    }
}
