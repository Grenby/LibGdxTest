package com.mygdx.projects.perlinNoise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.projects.Utils.FPSCounter;
import com.mygdx.projects.Utils.Fonts;
import com.mygdx.projects.Utils.MyMath;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PerlinShaderMode extends InputAdapter implements Screen {

    static final float CELL_SIZE = 100;
    static final int OCTAVES = 8;

    static final int WIDTH = Gdx.graphics.getWidth();
    static final int HEIGHT = Gdx.graphics.getHeight();

    private final Set<Integer> keysForUpdate = new HashSet<>(Arrays.asList(
            Input.Keys.W, Input.Keys.D, Input.Keys.S, Input.Keys.A, Input.Keys.UP, Input.Keys.DOWN
    ));

    private final float[] windowCoords = new float[3 * WIDTH * HEIGHT];

    private final RandomXS128 random = new RandomXS128();

    private final FPSCounter fpsCounter = new FPSCounter(10);
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = Fonts.getFonts();

    private final OrthographicCamera camera = new OrthographicCamera(WIDTH, HEIGHT);

    private final Set<Integer> keyPress = new HashSet<>();
    private int scroll = 0;

    private final IntBuffer transposeBuffer;
    private final FloatBuffer directBuffer;
    private final FloatBuffer cameraBuffer;
    private final FrameBuffer frameBuffer;

    private ShaderProgram shader;

    private int u_octaves;
    private int u_cellSize;
    private int u_transitions;
    private int u_directions;
    private int u_projection;

    private Mesh mesh;
    private boolean screenShot = false;

    public PerlinShaderMode() {
        Vector2 tmp = new Vector2();
        int NUM = 256;
        int[] transitions = new int[NUM * 2];
        float[] directions = new float[NUM * 2];

        for (int i = 0; i < NUM; i++) {
            transitions[i] = i;
            MyMath.angleToVector(tmp, getAngle());
            directions[2 * i] = tmp.x;
            directions[2 * i + 1] = tmp.y;
        }
        for (int i = 0; i < NUM; i++) {
            int j = transitions[i];
            transitions[i] = transitions[random.nextInt(NUM)];
            transitions[transitions[i]] = j;
        }
        System.arraycopy(transitions, 0, transitions, NUM, NUM);

        transposeBuffer = ByteBuffer.allocateDirect(transitions.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        transposeBuffer.put(transitions).position(0);

        directBuffer = ByteBuffer.allocateDirect(directions.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        directBuffer.put(directions).position(0);

        cameraBuffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cameraBuffer.put(camera.invProjectionView.getValues()).position(0);

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,WIDTH,HEIGHT,false,false);
    }

    private float getAngle() {
        return MathUtils.PI2 * random.nextFloat();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keysForUpdate.contains(keycode)) {
            keyPress.add(keycode);
            return true;
        }
        switch (keycode) {
            case Input.Keys.ESCAPE:
                dispose();
                Gdx.app.exit();
                break;
            case Input.Keys.F:
                screenShot = true;
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keysForUpdate.contains(keycode)) {
            keyPress.remove(keycode);
            return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scroll = amount;
        return true;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);
        //no need for depth...
        Gdx.gl.glDepthMask(false);

        //enable blending, for alpha
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shader = new ShaderProgram(
                Gdx.files.internal("Sh/perlinNoise/vertex.glsl").readString(),
                Gdx.files.internal("Sh/perlinNoise/fragment.glsl").readString());

        if (!shader.isCompiled())
            throw new GdxRuntimeException(shader.getLog());

        u_octaves = shader.getUniformLocation("u_octaves");
        u_cellSize = shader.getUniformLocation("u_cellSize");
        u_transitions = shader.getUniformLocation("u_transitions[0]");
        u_directions = shader.getUniformLocation("u_directions[0]");

        u_projection = shader.getUniformLocation("u_projection");

        mesh = new Mesh(true, WIDTH * HEIGHT, 0, VertexAttribute.Position());

        updateMesh();
    }

    void updateMesh() {
        int id_vertex = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                windowCoords[id_vertex++] = 2f * i / WIDTH - 1;
                windowCoords[id_vertex++] = 2f * j / HEIGHT - 1;
                windowCoords[id_vertex++] = 0;
            }
        }
        mesh.setVertices(windowCoords);
    }

    void update(float delta) {
        fpsCounter.update(delta);

        final float DELTA_ZOOM = 1.1f * delta;
        final float DELTA_CAMERA = 1000f * delta;
        final float minZoom = 0.1f;
        final float maxZoom = 10;
        boolean cameraUpdate = false;
        if (scroll != 0) {
            camera.zoom += scroll * DELTA_ZOOM;
            scroll = 0;
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.DOWN) && camera.zoom < maxZoom) {
            camera.zoom += DELTA_ZOOM;
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.UP) && camera.zoom > minZoom) {
            camera.zoom -= DELTA_ZOOM;
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.W)) {
            camera.position.add(0, DELTA_CAMERA, 0);
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.D)) {
            camera.position.add(DELTA_CAMERA, 0, 0);
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.S)) {
            camera.position.add(0, -DELTA_CAMERA, 0);
            cameraUpdate = true;
        }
        if (keyPress.contains(Input.Keys.A)) {
            camera.position.add(-DELTA_CAMERA, 0, 0);
            cameraUpdate = true;
        }
        if (cameraUpdate) {
            camera.update();
            cameraBuffer.put(camera.invProjectionView.getValues()).position(0);
        }
    }
    Texture texture = null;
    @Override
    public void render(float delta) {

        update(delta);

        final GL20 gl = Gdx.gl20;

        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (screenShot)
            frameBuffer.begin();
        shader.begin();

        gl.glUniformMatrix4fv(u_projection, 1, false, cameraBuffer);

        gl.glUniform1i(u_octaves, OCTAVES);
        gl.glUniform1f(u_cellSize, CELL_SIZE);
        gl.glUniform1iv(u_transitions, 1, transposeBuffer);
        gl.glUniform2fv(u_directions, 1, directBuffer);

        mesh.render(shader, GL20.GL_POINTS);

        shader.end();
        if (screenShot) {
            screenShot = false;
            frameBuffer.end();
            texture = frameBuffer.getColorBufferTexture();

        }
        batch.begin();
        font.draw(batch, Float.toString(fpsCounter.getFps()), 0, Gdx.graphics.getHeight());
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
