package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.manager.TextureManager;

public interface Constants
{
    final String WINDOW_TITLE = "Poly Big Balance";
    final int WINDOW_WIDTH = 800;
    final int WINDOW_HEIGHT = 600;

    final String[] MAIN_MENU_BUTTONS = { "New Game", "High scores", "How to play", "Credits", "Exit" };

    final String RESOURCES_PATH = "src/main/resources/";

    final Graphics g = new Graphics(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, true, true);

    final Surface TEXT_FONT_SURFACE = TextureManager.getInstance().loadTexture(RESOURCES_PATH + "font.bmp");
}
