package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.manager.TextureManager;

public interface Constants
{
    String WINDOW_TITLE = "Poly Big Balance";
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;

    String[] MAIN_MENU_BUTTONS = { "New Game", "High scores", "How to play", "Credits", "Exit" };

    String RESOURCES_PATH = "src/main/resources/";

    Graphics g = new Graphics(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, true, true);

    Surface TEXT_FONT_SURFACE = TextureManager.getInstance().loadTexture(RESOURCES_PATH + "font.bmp");
}
