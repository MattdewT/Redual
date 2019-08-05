package de.matze.Blocks.main;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.GameObjectRenderer;
import de.matze.Blocks.entities.GameObjectLoader;
import de.matze.Blocks.entities.components.*;
import de.matze.Blocks.entities.components.shipComponents.ShipMovement;
import de.matze.Blocks.graphics.Fbo;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.Texture;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MouseButtons;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.input.MouseWheel;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.gamemaster.GameStateMaster;
import de.matze.Blocks.mechanics.gui.GUIObject;
import de.matze.Blocks.mechanics.gui.GUIRenderer;
import de.matze.Blocks.mechanics.gui.colorpicking.ColorPickingRenderer;
import de.matze.Blocks.mechanics.gui.font.Fontloader;
import de.matze.Blocks.mechanics.gui.guicomponents.*;
import de.matze.Blocks.mechanics.gui.guiloader.GuiLoader;
import de.matze.Blocks.mechanics.postProcessing.PPRenderer;
import de.matze.Blocks.mechanics.postProcessing.PostProcessingUtils;
import de.matze.Blocks.mechanics.postProcessing.gaussianblur.HBlur;
import de.matze.Blocks.mechanics.postProcessing.gaussianblur.VBlur;
import de.matze.Blocks.mechanics.skybox.Skybox;
import de.matze.Blocks.utils.WindowUtils;
import de.matze.Blocks.mechanics.gui.GUIMousePicker;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @autor matze tiroch
 * @date 16.02.2017
 * @version 1.5
 */

public class GuiTester implements Runnable {

    private Thread thread;
    private boolean running;

    private Keyboard keyboard;
    private MouseButtons mouseButtons;
    private MousePos mousePos;
    private MouseWheel mouseWheel;

    private Loader loader;

    private Skybox skybox;

    private Matrix4f gui_pr_matrix;
    private GUIRenderer guiRenderer;
    private List<GUIObject> guis;
    private GuiLoader guiLoader;

    private Matrix4f pr_matrix;
    private GameObjectRenderer gameObjectRenderer;
    private List<GameObject> gameObjects;
    private GameObjectLoader gameObjectLoader;

    private GUIMousePicker guiMousePicker;

    GameObject Player;
    PPRenderer ppRenderer;

    Fbo fbo;
    VBlur vblur;
    HBlur hblur;

    Fbo game_fbo;


    public void start() {
        running = true;
        thread = new Thread(this, "GuiTester");
        thread.start();
    }

    public void run() {
        init();
        while(running) {
            render();
            update();
            if(glfwWindowShouldClose(WindowUtils.getWindow()) == GL_TRUE)
                running = false;
        }
        loader.cleanUp();
        WindowUtils.cleanUp();
    }

    private void init(){
        if(glfwInit() == GL_FALSE) {
            throw new IllegalStateException("GLFW failed to init");
        }

        keyboard = new Keyboard();
        mouseButtons = new MouseButtons();
        mousePos = new MousePos();
        mouseWheel = new MouseWheel();

        //startup picture init
        WindowUtils.init(keyboard, mousePos, mouseButtons, mouseWheel);
        loader = new Loader();
        PostProcessingUtils.init(loader);
        ppRenderer = new PPRenderer();
        Texture startup_picture = loader.loadTexture("res/textures/startup_fliped.png");

        //startup picture render
        PostProcessingUtils.start();
        glBindTexture(GL_TEXTURE_2D, startup_picture.getTexID());
        ppRenderer.render();
        PostProcessingUtils.stop();
        glfwSwapBuffers(WindowUtils.getWindow());

        float aspect = (float)WindowUtils.getWidth() / (float)WindowUtils.getHeight();
        pr_matrix = Matrix4f.gluPerspective(68f, aspect, 0.3f, 1200.0f);
        skybox = new Skybox(loader, pr_matrix);

        fbo = new Fbo(800, 600, 1);
        game_fbo = new Fbo(800, 600, 1);

        vblur = new VBlur(800 / 4, 600 / 3);
        hblur = new HBlur(800 / 4, 600 / 3);

        Player = new GameObject(0);
        Player.addComponent(new TransformComponent(new Vector3f(0, 20, 0), new Vector3f()));
        Player.addComponent(new ViewComponent());
        Player.addComponent(new CameraComponent(Player));
        Player.addComponent(new PlayerComponent(Player));

        GameStateMaster.init();

        gameObjectRenderer = new GameObjectRenderer(pr_matrix, loader);
        gameObjects = new ArrayList<>();
        gameObjectLoader = new GameObjectLoader(loader);

        gameObjects.add(gameObjectLoader.loadGameObject("res/GameObjects/001"));

        gui_pr_matrix = Matrix4f.orthographic(-100.0f, 100.0f, -100.0f / aspect, 100.0f / aspect, -1.0f, 1.0f);
        guiRenderer = new GUIRenderer(gui_pr_matrix);

        guiLoader = new GuiLoader(loader, Fontloader.LoadFont(loader, "res/fonts/DTM_Mono.fnt", "res/fonts/DTM_Mono.png"), hblur.getOutputTexture());

        guis = new ArrayList<>();

        GUIObject gui = guiLoader.loadGui("res/Guis/000");
        guis.add(gui);
        gui = guiLoader.loadGui("res/Guis/001");
        guis.add(gui);
        gui = guiLoader.loadGui("res/Guis/002");
        guis.add(gui);
        gui = guiLoader.loadGui("res/Guis/003");
        guis.add(gui);
        gui = guiLoader.loadGui("res/Guis/010");
        gui.setEnable(false);
        guis.add(gui);

        GameStateMaster.changeState(GameStateMaster.GameStates.Menu);
        WindowUtils.resetFrameTimeSeconds();

        guiMousePicker = new GUIMousePicker(gui_pr_matrix);
    }

    private void render() {
        fbo.bindFrameBuffer();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        skybox.render(((CameraComponent)Player.getComponent(Component.ComponentTypes.Camera)).getViewPoint());

        fbo.unbindFrameBuffer();

        game_fbo.bindFrameBuffer();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gameObjectRenderer.renderScene(gameObjects);

        game_fbo.unbindFrameBuffer();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        PostProcessingUtils.start();
        glBindTexture(GL_TEXTURE_2D, fbo.getColourTexture());
        vblur.render();                                                          //vertical blur
        glBindTexture(GL_TEXTURE_2D, vblur.getOutputTexture());
        hblur.render();                                                          //horizental blur

        if(Keyboard.keys[GLFW_KEY_1]) {
            glBindTexture(GL_TEXTURE_2D, ColorPickingRenderer.getTexture());
        } else {
            if(GameStateMaster.getCurrentState() == GameStateMaster.GameStates.Match) {
                glBindTexture(GL_TEXTURE_2D, game_fbo.getColourTexture());
            } else {
                glBindTexture(GL_TEXTURE_2D, fbo.getColourTexture());
            }
        }
        ppRenderer.render();                                                     //standard picture

        PostProcessingUtils.stop();

        if(!Keyboard.keys[GLFW_KEY_2])
            guiRenderer.renderGUI(guis);

        glfwSwapBuffers(WindowUtils.getWindow());
    }

    private void update() {
        glfwPollEvents();

        if(Keyboard.keys[GLFW_KEY_ENTER]) {
            guis.clear();
            GUIObject gui = guiLoader.loadGui("res/Guis/000");
            guis.add(gui);
            gui = guiLoader.loadGui("res/Guis/001");
            guis.add(gui);
            gui = guiLoader.loadGui("res/Guis/002");
            guis.add(gui);
            gui = guiLoader.loadGui("res/Guis/003");
            guis.add(gui);
            gui = guiLoader.loadGui("res/Guis/010");
            gui.setEnable(false);
            guis.add(gui);
        }

        int id = ColorPickingRenderer.getID();

        guiMousePicker.update();

        for(GUIObject gui : guis) {
                List<GUIComponent> buttons = gui.getComponentList(GUIComponent.GUIComponentTypes.Button);
                for(GUIComponent c : buttons) {
                    Button b = (Button) c;
                    b.update(id, MouseButtons.buttons[GLFW_MOUSE_BUTTON_1]);
                }
                List<GUIComponent> animations = gui.getComponentList(GUIComponent.GUIComponentTypes.Animation);
                for(GUIComponent c : animations) {
                    Animation a = (Animation) c;
                    a.update();
                }
                List<GUIComponent> colorselector = gui.getComponentList(GUIComponent.GUIComponentTypes.ColorSelector);
                for(GUIComponent c : colorselector) {
                    ColorSelector cs = (ColorSelector) c;
                    cs.getColorWheel().update(guiMousePicker.getCurrentRay());
                }
        }

        for (GameObject go : gameObjects) {
            ((ShipMovement) go.getComponent(Component.ComponentTypes.ShipMovement)).update();
        }

        GameStateMaster.update();
        if(GameStateMaster.isStateChanged()) {
            update_states(GameStateMaster.getCurrentState());
            GameStateMaster.setStateChanged(false);
        }
        if(Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
            GameStateMaster.changeState(GameStateMaster.GameStates.Menu);
        }

        // slow down animation
        if(Keyboard.isKeyDown(GLFW_KEY_K)) {
            WindowUtils.set_animation_multiplier(1000);
        }
        if(Keyboard.isKeyDown(GLFW_KEY_L)) {
            WindowUtils.set_animation_multiplier(100);
        }

        ((PlayerComponent) Player.getComponent(Component.ComponentTypes.Player)).Update();
        WindowUtils.update();
        glfwSetWindowTitle(WindowUtils.getWindow(), "FTS: " + WindowUtils.getFrameTimeSeconds());
    }

    private void update_states(GameStateMaster.GameStates state) {
        switch(state) {
            case Play:
                guis.get(0).setEnable(false);
                guis.get(1).setEnable(false);
                guis.get(2).setEnable(false);
                guis.get(3).setEnable(false);
                guis.get(4).setEnable(false);
                break;
            case Credits:
                guis.get(0).setEnable(false);
                guis.get(1).setEnable(false);
                guis.get(2).setEnable(false);
                guis.get(3).setEnable(false);
                break;
            case Options:
                guis.get(0).setEnable(false);
                guis.get(1).setEnable(false);
                guis.get(2).setEnable(false);
                guis.get(3).setEnable(false);
                guis.get(4).setEnable(true);
                break;
            case Tutorial:
                guis.get(0).setEnable(false);
                guis.get(1).setEnable(false);
                guis.get(2).setEnable(false);
                guis.get(3).setEnable(false);
                break;
            case Menu:
                guis.get(0).setEnable(true);
                guis.get(1).setEnable(true);
                guis.get(2).setEnable(true);
                guis.get(3).setEnable(true);
                guis.get(4).setEnable(false);
                break;
            case Match:
                guis.get(0).setEnable(false);
                guis.get(1).setEnable(false);
                guis.get(2).setEnable(false);
                guis.get(3).setEnable(false);
                guis.get(4).setEnable(false);
                break;
        }
    }

    public static void main(String[] args) {
        new GuiTester().start();
    }

}
