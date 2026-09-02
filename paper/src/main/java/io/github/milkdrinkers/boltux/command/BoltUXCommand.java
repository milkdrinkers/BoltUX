package io.github.milkdrinkers.boltux.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.milkdrinkers.boltux.api.BoltUXAPI;
import io.github.milkdrinkers.boltux.data.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class BoltUXCommand extends Command {
    @Override
    public CommandAPICommand command() {
        return new CommandAPICommand("boltux")
            .withFullDescription("BoltUX commands.")
            .withShortDescription("BoltUX commands.")
            .withPermission(Permissions.ADMIN_PERMISSION)
            .withSubcommands(
                getLockCommand(),
                new DumpCommand().command(),
                new TranslationCommand().command()
            )
            .executes(this::helpMenu);
    }

    private void helpMenu(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Component.translatable("boltux.commands.help"));
    }

    private CommandAPICommand getLockCommand() {
        return new CommandAPICommand("getlock")
            .withFullDescription("Gives yourself the lock item.")
            .withShortDescription("Gives lock item.")
            .withPermission(Permissions.ADMIN_PERMISSION)
            .withOptionalArguments(
                new IntegerArgument("amount")
                    .replaceSuggestions(ArgumentSuggestions.strings("64", "32", "16"))
            )
            .executesPlayer((Player sender, CommandArguments args) -> {
                Integer amount = (Integer) args.get("amount");
                if (amount == null) {
                    sender.getInventory().addItem(BoltUXAPI.getInstance().getLockItem(1));
                    return;
                }
                sender.getInventory().addItem(BoltUXAPI.getInstance().getLockItem(amount));
            });
    }
}
