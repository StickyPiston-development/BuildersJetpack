package io.github.mrstickypiston.buildersjetpack.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class BuildersJetpackCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        final LiteralCommandNode<ServerCommandSource> buildersJetpackNode = dispatcher.register(literal("builders_jetpack")
                .executes(context -> {
                    context.getSource().sendFeedback(Text.of(String.format("[§c§lBuilders jetpack§r] version %s made by %s", BuildersJetpack.VERSION, BuildersJetpack.AUTHORS)), false);
                    return 0;
                })
        );

        dispatcher.register(literal("bj")
                .executes(context -> buildersJetpackNode.getCommand().run(context)));
    }
}
