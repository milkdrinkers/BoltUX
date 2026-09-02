package io.github.milkdrinkers.boltux.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIPaper;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.milkdrinkers.boltux.data.Permissions;
import io.github.milkdrinkers.boltux.utility.Cfg;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.milkdrinkers.wordweaver.Translation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class containing the code for the translation commands.
 */
final class TranslationCommand extends Command {
    /**
     * Instantiates a new command tree.
     */
    @Override
    public CommandAPICommand command() {
        return new CommandAPICommand("translation")
            .withHelp("Translation related commands.", "Translation related commands.")
            .withPermission(Permissions.ADMIN_PERMISSION)
            .withSubcommands(
                commandReload(),
                commandTest(),
                new CommandAPICommand("help")
                    .executes(this::executorHelp)
            )
            .executes(this::executorHelp);
    }

    private CommandAPICommand commandReload() {
        return new CommandAPICommand("reload")
            .withHelp("Reload the translation files.", "Reload the translation files.")
            .withPermission(Permissions.ADMIN_PERMISSION)
            .executes(this::executorReload);
    }

    private CommandAPICommand commandTest() {
        return new CommandAPICommand("test")
            .withHelp("Test a translation entry.", "Test a translation entry.")
            .withPermission(Permissions.ADMIN_PERMISSION)
            .withArguments(
                new StringArgument("key").replaceSuggestions(ArgumentSuggestions.stringCollection(unused -> Translation.getKeys()))
            )
            .executes(this::executorTest);
    }

    private void executorHelp(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Component.translatable("boltux.commands.translation.help"));
    }

    private void executorReload(CommandSender sender, CommandArguments args) {
        Translation.setLocale(Cfg.get().language);
        Translation.reload();
        sender.sendMessage(Component.translatable("boltux.commands.translation.reloaded"));
    }

    private void executorTest(CommandSender sender, CommandArguments args) throws WrapperCommandSyntaxException {
        final String node = args.getByClassOrDefault("key", String.class, "");

        if (node == null)
            throw CommandAPIPaper.failWithAdventureComponent(Component.translatable("boltux.commands.translation.test.not-string"));

        if (node.isBlank())
            throw CommandAPIPaper.failWithAdventureComponent(Component.translatable("boltux.commands.translation.test.not-empty", Argument.string("node", node)));

        if (node.startsWith(".") || node.endsWith("."))
            throw CommandAPIPaper.failWithAdventureComponent(Component.translatable("boltux.commands.translation.test.illegal", Argument.string("node", node)));

        final String translation = Translation.of(node);

        if (translation == null)
            throw CommandAPIPaper.failWithAdventureComponent(Component.translatable("boltux.commands.translation.test.not-found", Argument.string("node", node)));

        if (translation.isBlank())
            throw CommandAPIPaper.failWithAdventureComponent(Component.translatable("boltux.commands.translation.test.not-empty2", Argument.string("node", node)));

        if (sender instanceof Player player) {
            sender.sendMessage(
                ColorParser.of(translation)
                    .papi(player)
                    .mini(player)
                    .build()
            );
        } else {
            sender.sendMessage(Translation.as(node));
        }
    }
}
