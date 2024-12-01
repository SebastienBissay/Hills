package shape;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static parameters.Parameters.NOISE_SCALE;
import static parameters.Parameters.NOISE_Z_OFFSET_SQUARE;

public record Square(PVector corner, float size) {
    private static PApplet pApplet;

    public static void setPApplet(PApplet pApplet) {
        Square.pApplet = pApplet;
    }

    public List<Square> subdivide() {
        ArrayList<Square> newSquares = new ArrayList<>();
        float t = pApplet.noise(corner.x * NOISE_SCALE, corner.y * NOISE_SCALE, NOISE_Z_OFFSET_SQUARE);
        newSquares.add(new Square(corner, size * t));
        newSquares.add(new Square(PVector.add(corner, new PVector(size * t, 0)), size * (1 - t)));
        newSquares.add(new Square(PVector.add(corner, new PVector(0, size * t)), size * t));
        newSquares.add(new Square(PVector.add(corner, new PVector(size * t, size * t)), size * (1 - t)));

        return newSquares;
    }
}
