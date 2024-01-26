package io.github.mrstickypiston.buildersjetpack;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;

@Config(id = BuildersJetpack.MOD_ID)
public class ModConfig {

    public enum fuelHudType {
        NONE,
        BAR,
        PERCENTAGE,
        NUMBER
    }

    // Client
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
    @Configurable.Comment("How the amount of fuel will be displayed. (NONE, BAR, PERCENTAGE, NUMBER)")
    public fuelHudType FUEL_HUD_TYPE = fuelHudType.BAR;

    @Configurable
    @Configurable.Comment("Fuel icon used for the fuel bar")
    public String FUEL_BAR_ICON = "▊";

    @Configurable
    @Configurable.Comment("Color code for the remaining fuel in the fuel bar")
    public String FUEL_BAR_REMAINING_COLOR = "§f";

    @Configurable
    @Configurable.Comment("Color code for the used fuel in the fuel bar")
    public String FUEL_BAR_USED_COLOR = "§8";

    // Server
    @Configurable
    @Configurable.Comment("The maximum amount of fuel a jetpack can have")
    @Configurable.Range(min=0)
    public int MAX_FUEL = 1000;

    @Configurable
    @Configurable.Comment("The base fuel cost per tick while flying")
    @Configurable.DecimalRange(min=0)
    @Configurable.Gui.NumberFormat("0.000#")
    public float FLY_BASE_FUEL_COST = 0.002F;

    @Configurable
    @Configurable.Comment("The cost per speed of flying with formula SPEED/COST")
    @Configurable.DecimalRange(min=0.00001)
    @Configurable.Gui.NumberFormat("0.000#")
    public float FLY_MOVEMENT_FUEL_COST = 40F;

    @Configurable
    @Configurable.Comment("Show warnings in chat when fuel reaches a certain amount")
    public boolean FUEL_WARNING = true;

    @Configurable
    @Configurable.Comment("The milestones a warning is sent if fuel warning is true")
    @Configurable.Range(min=0)
    public int[] FUEL_WARNINGS = {100, 50, 25, 10, 5, 4, 3, 2, 1};

    @Configurable
    @Configurable.Comment("The amount of fuel the fuel item adds to the jetpack")
    @Configurable.Range(min=0)
    public int FUEL_ITEM_AMOUNT = 100;

    @Configurable
    @Configurable.Comment("The amount of speed the fuel cost will be capped")
    @Configurable.DecimalRange(min=0)
    public float FUEL_SPEED_CAP = 1.4F;
}
