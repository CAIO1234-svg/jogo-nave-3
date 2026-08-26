package br.mackenzie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture backgroundImage;


    private float x, y;
    private float speed = 300f;


    private int shipWidth, shipHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("images.jpeg");
        backgroundImage = new Texture("background.png");

        shipWidth = image.getWidth();
        shipHeight = image.getHeight();


        x = Gdx.graphics.getWidth() / 2f - shipWidth / 2f;
        y = Gdx.graphics.getHeight() / 2f - shipHeight / 2f;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime(); // tempo desde o último frame

        // --- movimento ---
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
        }
        if (Gdx.input.isTouched()) {
            x = Gdx.input.getX() - shipWidth / 2f;
            y = Gdx.graphics.getHeight() - Gdx.input.getY() - shipHeight / 2f; // inverte o Y (tela vs mundo)
        }


        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - shipWidth));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - shipHeight));


        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
