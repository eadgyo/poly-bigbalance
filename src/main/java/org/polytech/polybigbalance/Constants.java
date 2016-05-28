package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Surface;
import org.cora.graphics.manager.TextureManager;

public interface Constants
{
    String WINDOW_TITLE = "Poly Big Balance";
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 600;

    String RESOURCES_PATH = "src/main/resources/";
    
    Surface TEXT_FONT_SURFACE = TextureManager.createTexture(RESOURCES_PATH + "font.bmp");
}
