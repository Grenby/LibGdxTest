package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.RayM.dimension3.RayMarchingScreen;
import com.mygdx.game.RayM3.Shaders.TestShader;
import com.mygdx.game.light2.RoomScreen1;

public class Start extends Game {
    @Override
    public void create() {
        setScreen(new TestShader());
    }
}
