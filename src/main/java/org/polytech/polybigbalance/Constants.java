package org.polytech.polybigbalance;

import org.cora.graphics.font.Font;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.manager.FontManager;
import org.polytech.polybigbalance.base.InterfaceEvent;

public interface Constants
{
    // ----- GENERAL CONTENT ----- //
    String WINDOW_TITLE = "Poly Big Balance";
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;
    myColor BACKGROUND_COLOR = new myColor(0.47f, 0.71f, 1.0f);

    // ----- BUTTONS ----- //
    myColor BUTTON_HIGHLIGHT_COLOR = new myColor(0.7f, 0.7f, 0.7f);

    // ----- MAIN MENU ----- //
    String[] MENU_MAIN_BUTTON =
    { "Play", "How to play", "Credits", "Exit" };
    InterfaceEvent[] MENU_MAIN_EVENT =
    { InterfaceEvent.PLAY, InterfaceEvent.HOW_TO, InterfaceEvent.CREDIT, InterfaceEvent.EXIT };

    // ----- HELP MENU ----- //
    String MENU_HELP_TEXT = "Click and move the mouse while holding the left mouse button to draw a rectangle." + "The rectangle is created when you release the left mouse button.\n\n"
            + "Then wait 5 seconds, and if the rectangle doesn't touch the ground, your score is increased. A big rectangle means a big increase.\n\n" + "If the rectangle falls, you lose the game!";

    // ----- CREDITS MENU ----- //
    String MENU_CREDITS_TEXT = "Developers:\n\n" + "Ronan JAMET\n" + "Tudal LE BOT\n" + "Pierre PÉTILLON\n" + "Hugo PIGEON";

    // ----- PAUSE MENU ----- //
    String[] MENU_PAUSE_BUTTON =
    { "Resume", "Menu", "Exit" };
    InterfaceEvent[] MENU_PAUSE_EVENT =
    { InterfaceEvent.POP, InterfaceEvent.MENU, InterfaceEvent.EXIT };
    String MENU_PAUSE_TITLE = "Pause";

    // ----- GAME FINISHED MENU ----- //
    String[] GAME_FINISHED_BUTTON =
    { "Play again", "Main menu", "Exit" };
    InterfaceEvent[] GAME_FINISHED_EVENT =
    { InterfaceEvent.NEW_GAME, InterfaceEvent.POP, InterfaceEvent.EXIT };

    // ----- LEVEL SELECTOR ----- //
    myColor SELECTOR_SELECTED_COLOR = myColor.RED(1.0f);
    myColor SELECTOR_NOT_SELECTED_COLOR = myColor.WHITE(1.0f);

    // ----- GAME ----- //
    int MAX_PLAYER = 4;

    // ----- RESOURCES ----- //

    String RESOURCES_PATH = "/";

    Graphics g = new Graphics(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, true, true, Constants.class);

    Font FONT = FontManager.getInstance().loadFont(Constants.RESOURCES_PATH + "font.bmp", 32);
    Font FONT48 = FontManager.getInstance().loadFont(Constants.RESOURCES_PATH + "font48b.bmp", 48);
}
