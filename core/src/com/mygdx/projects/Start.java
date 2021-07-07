package com.mygdx.projects;

import com.badlogic.gdx.Game;
import com.mygdx.projects.perlinNoise.PerlinMode;
import com.mygdx.projects.perlinNoise.PerlinShaderMode;

public class Start extends Game {

    @Override
    public void create() {
        setScreen(new PerlinShaderMode());
    }
}
