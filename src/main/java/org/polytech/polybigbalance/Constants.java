package org.polytech.polybigbalance;

import org.cora.graphics.base.Image;
import org.cora.graphics.font.Font;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.manager.FontManager;
import org.cora.graphics.manager.TextureManager;
import org.polytech.polybigbalance.base.InterfaceEvent;

public interface Constants
{
    // ----- GENERAL CONTENT ----- //
    String WINDOW_TITLE = "Poly Big Balance";
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;
    myColor BACKGROUND_COLOR = new myColor(0.47f, 0.71f, 1.0f);

    // ----- MAIN MENU ----- //
    String[] MAIN_MENU_BUTTONS = { "Play", "How to play", "Credits", "Exit" };
    InterfaceEvent[] MAIN_MENU_EVENT = { InterfaceEvent.PLAY, InterfaceEvent.HOW_TO, InterfaceEvent.CREDIT, InterfaceEvent.EXIT };
    myColor MAIN_MENU_HIGHLIGHT_COLOR = new myColor(-0.3f, -0.3f, -0.3f, 1.0f);

    // ----- OPTION MENU ----- //
    String[] OPTION_MENU_BUTTONS = { "Resume", "Menu", "Exit" };
    InterfaceEvent[] OPTION_MENU_EVENT = { InterfaceEvent.POP, InterfaceEvent.MENU, InterfaceEvent.EXIT };

    // ----- GAME FINISHED MENU ----- //
    String[] GAME_FINISHED_BUTTONS = { "Play again", "Main menu", "Exit" };
    InterfaceEvent[] GAME_FINISHED_EVENT = { InterfaceEvent.NEW_GAME, InterfaceEvent.POP, InterfaceEvent.EXIT };

    // ----- LEVEL SELECTOR ----- //
    myColor SELECTOR_SELECTED_COLOR = myColor.RED(1.0f);
    myColor SELECTOR_NOT_SELECTED_COLOR = myColor.WHITE(1.0f);

    // ----- GAME ----- //
    int MAX_PLAYER = 4;

    // ----- RESOURCES ----- //

    String RESOURCES_PATH = "/";

    Graphics g = new Graphics(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, true, true, Constants.class);

    Font FONT = FontManager.getInstance().loadFont(Constants.RESOURCES_PATH + "font.bmp", 32);

    Image MAIN_MENU_BACKGROUND = new Image(TextureManager.getInstance().loadTexture(Constants.RESOURCES_PATH + "mainMenu.png"));
}
