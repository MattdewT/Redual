package de.matze.Blocks.mechanics.gamemaster;

import de.matze.Blocks.mechanics.logic.LogicElement;
import de.matze.Blocks.mechanics.logic.Trigger;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor matze
 * @date 13.04.2017
 */

public class GameStateMaster {

    private static GameStates current_state;
    private static List<LogicElement> controller;
    private static List<GameStates> states;
    private static boolean state_changed;

    public enum GameStates {
        Invalid, Menu, Play, Credits, Options, Tutorial, Match
    }

    private GameStateMaster() {
    }

    public static void init() {
        controller = new ArrayList<>();
        states = new ArrayList<>();
        current_state = GameStates.Invalid;
    }

    public static void update() {
        for(int  i = 0; i < controller.size(); i++) {
            Trigger t = (Trigger) controller.get(i);
            if(t.value) {
                current_state = states.get(i);
                state_changed = true;
//                System.out.println(current_state);
            }
        }
    }

    public static void addController(LogicElement element, GameStates state) {
        controller.add(element);
        states.add(state);
    }

    public static void changeState(GameStates state) {
        if(current_state != state)
            state_changed = true;
        GameStateMaster.current_state = state;
    }

    public static GameStates getCurrentState() {
        return GameStateMaster.current_state;
    }

    public static boolean isStateChanged() {
        return state_changed;
    }

    public static void setStateChanged(boolean state_changed) {
        GameStateMaster.state_changed = state_changed;
    }
}
