package io.github.mrstickypiston.buildersjetpack;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Utils {
    public static ParticleEffect parseParticle(String s){
        return (ParticleEffect) Registry.PARTICLE_TYPE.get(new Identifier(s));
    }

    public static SoundEvent parseSoundEvent(String soundString) {
        Identifier soundId = new Identifier(soundString);
        return Registry.SOUND_EVENT.get(soundId);
    }
}
