package shape;

import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static processing.core.PApplet.lerp;

public class Pyramid {
    private static PApplet pApplet;
    private final PVector[] vertices;

    public Pyramid(float x, float y, float s) {
        vertices = new PVector[5];
        vertices[0] = new PVector(x, y);
        vertices[1] = new PVector(x + s, y);
        vertices[2] = new PVector(x + s, y + s);
        vertices[3] = new PVector(x, y + s);
        float oX = pApplet.noise(x * NOISE_SCALE, y * NOISE_SCALE);
        float oY = pApplet.noise(x * NOISE_SCALE, y * NOISE_SCALE, NOISE_Z_OFFSET_PYRAMID);
        vertices[4] = new PVector(x + lerp(0, s, oX), y + lerp(0, s, oY));
    }

    public static void setPApplet(PApplet pApplet) {
        Pyramid.pApplet = pApplet;
    }

    public void render() {
        pApplet.noStroke();
        pApplet.fill(FILL_COLOR_1.red(), FILL_COLOR_1.green(), FILL_COLOR_1.blue(), FILL_COLOR_1.alpha());
        pApplet.triangle(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y, vertices[4].x, vertices[4].y);
        pApplet.fill(FILL_COLOR_2.red(), FILL_COLOR_2.green(), FILL_COLOR_2.blue(), FILL_COLOR_2.alpha());
        pApplet.triangle(vertices[2].x, vertices[2].y, vertices[1].x, vertices[1].y, vertices[4].x, vertices[4].y);
        if (pApplet.noise(vertices[4].x * NOISE_SCALE,
                vertices[4].y * NOISE_SCALE,
                NOISE_Z_OFFSET_SQUARE + NOISE_Z_OFFSET_PYRAMID)
                > 0.5) {
            pApplet.triangle(vertices[0].x, vertices[0].y, vertices[2].x, vertices[2].y, vertices[4].x, vertices[4].y);
            pApplet.fill(FILL_COLOR_3.red(), FILL_COLOR_3.green(), FILL_COLOR_3.blue(), FILL_COLOR_3.alpha());
            pApplet.triangle(vertices[3].x, vertices[3].y, vertices[1].x, vertices[1].y, vertices[4].x, vertices[4].y);
        } else {
            pApplet.triangle(vertices[1].x, vertices[1].y, vertices[3].x, vertices[3].y, vertices[4].x, vertices[4].y);
            pApplet.fill(FILL_COLOR_4.red(), FILL_COLOR_4.green(), FILL_COLOR_4.blue(), FILL_COLOR_4.alpha());
            pApplet.triangle(vertices[0].x, vertices[0].y, vertices[2].x, vertices[2].y, vertices[4].x, vertices[4].y);
        }
    }
}
