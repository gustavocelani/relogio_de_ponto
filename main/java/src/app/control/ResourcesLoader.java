package app.control;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * ResourcesLoader
 */
public class ResourcesLoader {

    /**
     * loadIcon
     * @return Icon
     */
    public Image loadIcon() {
        return loadImage("/icons/icon.png");
    }

    /**
     * loadGreenIcon
     * @return Green Icon
     */
    public Image loadGreenIcon() {
        return loadImage("/icons/icon_green.png");
    }

    /**
     * loadImage
     * @param resourcesPath resourcesPath
     * @return Image
     */
    public Image loadImage(String resourcesPath) {
        try {
            return ImageIO.read(getClass().getResource(resourcesPath));

        } catch (IOException e) {
            System.out.println("Fail to load: " + resourcesPath + " " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
