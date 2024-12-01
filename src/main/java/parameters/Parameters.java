package parameters;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static processing.core.PConstants.ADD;

public final class Parameters {
    public static final long SEED = 1968539008;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;
    public static final int MARGIN = 200;
    public static final float CHANCE_OF_NEW_SQUARE = .95f;
    public static final float WIDTH_EXPONENT = .6f;
    public static final float SIZE_EXPONENT = .6f;
    public static final float NOISE_SCALE = 1 / 200f;
    public static final float NOISE_Z_OFFSET_SQUARE = .5f;
    public static final float NOISE_Z_OFFSET_PYRAMID = .25f;
    public static final int BLEND_MODE = ADD;
    public static final Color BACKGROUND_COLOR = new Color("232B36");
    public static final Color FILL_COLOR_1 = new Color(196);
    public static final Color FILL_COLOR_2 = new Color(128);
    public static final Color FILL_COLOR_3 = new Color(32);
    public static final Color FILL_COLOR_4 = new Color(32);

    /**
     * Helper method to extract the constants in order to save them to a json file
     *
     * @return a Map of the constants (name -> value)
     */
    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = Parameters.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(Parameters.class));
        }

        return Collections.singletonMap(Parameters.class.getSimpleName(), map);
    }

    public record Color(float red, float green, float blue, float alpha) {
        public Color(float red, float green, float blue) {
            this(red, green, blue, 255);
        }

        public Color(float grayscale, float alpha) {
            this(grayscale, grayscale, grayscale, alpha);
        }

        public Color(float grayscale) {
            this(grayscale, 255);
        }

        public Color(String hexCode) {
            this(decode(hexCode));
        }

        public Color(Color color) {
            this(color.red, color.green, color.blue, color.alpha);
        }

        public static Color decode(String hexCode) {
            return switch (hexCode.length()) {
                case 2 -> new Color(Integer.valueOf(hexCode, 16));
                case 4 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16));
                case 6 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16));
                case 8 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16),
                        Integer.valueOf(hexCode.substring(6, 8), 16));
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
