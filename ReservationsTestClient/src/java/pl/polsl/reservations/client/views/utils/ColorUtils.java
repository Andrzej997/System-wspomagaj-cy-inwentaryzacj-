package pl.polsl.reservations.client.views.utils;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matis
 */
public class ColorUtils {

    private static final String[] COLORS = {"BLACK", "WHITE", "RED", "BLUE", "GREEN", "YELLOW",
        "CYAN", "GRAY", "MAGENTA", "ORANGE", "PINK", "DARK_GRAY"};

    public static Color getColor() {
        Color color = null;
        for (String colorString : COLORS) {
            try {
                Field field = Color.class.getField(colorString);
                color = (Color) field.get(null);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                color = null;
            }
        }
        return color;
    }

    public static Color getColor(String colorName) {
        Color color = null;
        try {
            Field field = Color.class.getField(colorName);
            color = (Color) field.get(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            color = null;
        }
        return color;
    }
    
    public static String[] getColorList(){
        return COLORS;
    }
    
    public static List<Color> getColors(){
        List<Color> result = new ArrayList<>();
        for(String colorString : COLORS){
            Color color = getColor(colorString);
            if(color != null){
                result.add(color);
            }
        }
        return result;
    }
}
