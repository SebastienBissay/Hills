import processing.core.PApplet;
import processing.core.PVector;
import shape.Pyramid;
import shape.Square;

import java.util.ArrayList;
import java.util.List;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Hills extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Hills.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        blendMode(BLEND_MODE);
        noLoop();

        Square.setPApplet(this);
        Pyramid.setPApplet(this);
    }

    @Override
    public void draw() {
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(new PVector(MARGIN, MARGIN), max(width, height) - 2 * MARGIN));
        int i = 0;
        while (i < squares.size()) {
            Square square = squares.get(i);
            if (map(pow(square.size(), SIZE_EXPONENT), pow(width, WIDTH_EXPONENT), 0, 1, 0)
                    * noise((square.corner().x + square.size() / 2) * NOISE_SCALE,
                    (square.corner().y + square.size() / 2) * NOISE_SCALE)
                    > 1 - CHANCE_OF_NEW_SQUARE) {
                squares.addAll(square.subdivide());
                squares.remove(i);
            } else {
                i++;
            }
        }

        squares.stream()
                .map(s -> new Pyramid(s.corner().x, s.corner().y, s.size()))
                .forEach(Pyramid::render);
        saveSketch(this);
    }
}
