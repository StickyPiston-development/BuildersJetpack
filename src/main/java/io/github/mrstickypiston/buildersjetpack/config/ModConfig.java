package io.github.mrstickypiston.buildersjetpack.config;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;

@Config(group = BuildersJetpack.MOD_ID, id = BuildersJetpack.MOD_ID + "_common", filename = BuildersJetpack.MOD_ID + "/common")
public class ModConfig {
    @Configurable
    @Configurable.Comment("The maximum amount of fuel a jetpack can have")
    @Configurable.Synchronized
    @Configurable.Range(min=0)
    public int MAX_FUEL = 1000;

    @Configurable
    @Configurable.Comment("The base fuel cost per tick while flying")
    @Configurable.Synchronized
    @Configurable.DecimalRange(min=0)
    @Configurable.Gui.NumberFormat("0.000#")
    public float FLY_BASE_FUEL_COST = 0.002F;

    @Configurable
    @Configurable.Comment("The cost per speed of flying with formula SPEED/COST")
    @Configurable.Synchronized
    @Configurable.DecimalRange(min=0.00001)
    @Configurable.Gui.NumberFormat("0.000#")
    public float FLY_MOVEMENT_FUEL_COST = 40F;

    @Configurable
    @Configurable.Comment("The amount of fuel the fuel item adds to the jetpack")
    @Configurable.Synchronized
    @Configurable.Range(min=0)
    public int FUEL_ITEM_AMOUNT = 100;

    @Configurable
    @Configurable.Comment("The amount of speed the fuel cost will be capped")
    @Configurable.Synchronized
    @Configurable.DecimalRange(min=0)
    public float FUEL_SPEED_CAP = 1.4F;
}
