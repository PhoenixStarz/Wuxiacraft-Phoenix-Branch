package com.airesnor.wuxiacraft.commands;

import com.airesnor.wuxiacraft.world.event.Events;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class EventCommand extends CommandBase {

	@Override
	@Nonnull
	public String getName() {
		return "wuxiaEvent";
	}

	@Override
	@Nonnull
	public String getUsage(ICommandSender sender) {
		return "/wuxiaEvent <start/end> <event>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) sender;
                if (!playerMP.world.isRemote) {
                    boolean wrongUsage = false;
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("start")) {
							if (args[1].equalsIgnoreCase("thick")) {
							Events.setWorldEvent1(true);
							Events.setWorldEvent2(false);
								TextComponentString startText = new TextComponentString("The air thickens.");
								startText.getStyle().setColor(TextFormatting.BLUE);
								server.getPlayerList().sendMessage(startText);
							} else if (args[1].equalsIgnoreCase("thin")) {
							Events.setWorldEvent1(false);
							Events.setWorldEvent2(true);
								TextComponentString startText = new TextComponentString("The air thins.");
								startText.getStyle().setColor(TextFormatting.GRAY);
								server.getPlayerList().sendMessage(startText);
							} else if (args[1].equalsIgnoreCase("broil")) {
							Events.setWorldEvent3(true);
								TextComponentString startText = new TextComponentString("The air broils with rage.");
								startText.getStyle().setColor(TextFormatting.RED);
								server.getPlayerList().sendMessage(startText);
							}
                        } else if (args[0].equalsIgnoreCase("end")) {
							Events.setWorldEvent1(false);
							Events.setWorldEvent2(false);
							Events.setWorldEvent3(false);
								TextComponentString endText = new TextComponentString("The air normalizes.");
								endText.getStyle().setColor(TextFormatting.WHITE);
								server.getPlayerList().sendMessage(endText);
						}
                    } else {
                        wrongUsage = true;
                    }
                    if (wrongUsage) {
                        TextComponentString text = new TextComponentString("Invalid arguments, use /wuxiaEvent <start/end> <event>");
                        text.getStyle().setColor(TextFormatting.RED);
                        sender.sendMessage(text);
                    }
                }
        }
		else throw new CommandException("Not used correctly!");
    }

	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		List<String> completions = new ArrayList<>();
			if(args.length == 1) {
				if (args[0].toLowerCase().startsWith(args[0]))
					completions.add("start");
				if (args[0].toLowerCase().startsWith(args[0]))
					completions.add("end");
			} 
			else if(args.length == 2) {
				if (args[1].toLowerCase().startsWith(args[1]))
					completions.add("thick");
				if (args[1].toLowerCase().startsWith(args[1]))
					completions.add("thin");
				if (args[1].toLowerCase().startsWith(args[1]))
					completions.add("broil");
			}
		return completions;
	}
}
