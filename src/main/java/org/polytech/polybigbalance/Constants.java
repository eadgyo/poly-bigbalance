package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.manager.TextureManager;
import org.polytech.polybigbalance.base.InterfaceEvent;

public interface Constants
{
    String WINDOW_TITLE = "Poly Big Balance";
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;

    String[] MAIN_MENU_BUTTONS = { "Play", "High scores", "How to play", "Credits", "Exit" };
    InterfaceEvent[] MAIN_MENU_EVENT = { InterfaceEvent.PLAY, InterfaceEvent.SCORE, InterfaceEvent.HOW_TO, InterfaceEvent.CREDIT, InterfaceEvent.EXIT };

    String RESOURCES_PATH = "/";

    Graphics g = new Graphics(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, true, true, Constants.class);

    Surface TEXT_FONT_SURFACE = TextureManager.getInstance().loadTexture(RESOURCES_PATH + "font.bmp");
}
