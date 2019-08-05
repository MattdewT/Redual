package de.matze.Blocks.mechanics.logic;

/**
 * @autor matze
 * @date 13.04.2017
 */

public class LogicElement {

    private LogicElements logicElement;

    public enum LogicElements {
        trigger;
    }

    public LogicElement(LogicElements logicElement) {
        this.logicElement = logicElement;
    }

}
