package io.github.mrstickypiston.buildersjetpack.client.config;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import net.minecraft.util.Formatting;

@Config(group = BuildersJetpack.MOD_ID, id = BuildersJetpack.MOD_ID + "_client", filename = BuildersJetpack.MOD_ID + "/client")
public class ClientConfig {

    public enum fuelHudType {
        DISABLED,
        BAR,
        PERCENTAGE,
        NUMBER
    }

    public enum fuelWarningType {
        DISABLED,
        // Chat is used for !disabled and !actionbar
        @SuppressWarnings("unused")
        CHAT,
        ACTIONBAR
    }

    @Configurable
    @Configurable.Comment("The particle that will be displayed under the player while flying")
    public String PARTICLE = "minecraft:cloud";

    @Configurable
    @Configurable.Comment("The sound that will be played when the player uses the jetpack fuel item")
    public String FUEL_ITEM_SUCCESS = "minecraft:item.bucket.fill_lava";

    @Configurable
    @Configurable.Comment("Hide the fuel hud if chat is open to avoid overlapping")
    public boolean FUEL_HUD_CHAT_FIX = true;

    @Configurable
    @Configurable.Comment("X location of the fuel hud")
    public float FUEL_HUD_X = 10;

    @Configurable
    @Configurable.Comment("Y location of the fuel hud")
    public float FUEL_HUD_Y = 10;

    @Configurable
    @Configurable.Comment("How the amount of fuel will be displayed. (DISABLED, BAR, PERCENTAGE, NUMBER)")
    public io.github.mrstickypiston.buildersjetpack.client.config.ClientConfig.fuelHudType FUEL_HUD_TYPE = io.github.mrstickypiston.buildersjetpack.client.config.ClientConfig.fuelHudType.BAR;

    @Configurable
    @Configurable.Comment("Fuel icon used for the fuel bar")
    public String FUEL_BAR_ICON = "▊";

    @Configurable
    @Configurable.Comment("Color for the remaining fuel in the fuel bar. RESET = no icon")
    public Formatting FUEL_BAR_REMAINING_COLOR = Formatting.WHITE;

    @Configurable
    @Configurable.Comment("Color for the used fuel in the fuel bar. RESET = no icon")
    public Formatting FUEL_BAR_USED_COLOR = Formatting.GRAY;

    @Configurable
    @Configurable.Comment("Show warnings in when fuel reaches a certain amount")
    public fuelWarningType FUEL_WARNING_TYPE = fuelWarningType.ACTIONBAR;

    @Configurable
    @Configurable.Comment("The milestones a warning is sent if fuel warning is true")
    @Configurable.Range(min=0)
    public int[] FUEL_WARNINGS = {100, 50, 25, 10, 5, 4, 3, 2, 1};
}
