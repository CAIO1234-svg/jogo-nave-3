package br.mackenzie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.Iterator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture backgroundImage;
    private Texture obstacleTexture;
    private Array<Rectangle> obstacles;
    private float obstacleTimer;


    private float x, y;
    private float speed = 300f;


    private int shipWidth, shipHeight;
    private Sound collisionSound;
    private Music music;
    private Animation<Texture> animacaoNave;
    private float stateTimeNave;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("images.jpeg");
        backgroundImage = new Texture("background.png");
        obstacleTexture = new Texture("drop.png");
        obstacles = new Array<>();
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        Texture[] framesNave = new Texture[8];   // ADICIONE DAQUI
        for (int i = 0; i < 8; i++) {
            framesNave[i] = new Texture("Run" + (i+1) + ".png");
        }
        animacaoNave = new Animation<>(0.1f, framesNave);
        stateTimeNave = 0;                        // ATÉ AQUI

        shipWidth = image.getWidth();
        shipHeight = image.getHeight();

        x = Gdx.graphics.getWidth() / 2f - shipWidth / 2f;
        y = Gdx.graphics.getHeight() / 2f - shipHeight / 2f;

        shipHeight = image.getHeight();


        x = Gdx.graphics.getWidth() / 2f - shipWidth / 2f;
        y = Gdx.graphics.getHeight() / 2f - shipHeight / 2f;
    }

    private void createObstacle() {
        float obstacleWidth = 50;
        float obstacleHeight = 50;
        float x = MathUtils.random(0, Gdx.graphics.getWidth() - obstacleWidth);
        Rectangle obstacle = new Rectangle(x, Gdx.graphics.getHeight(), obstacleWidth, obstacleHeight);
        obstacles.add(obstacle);
    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime(); // tempo desde o último frame
        stateTimeNave += delta;
        Texture frameAtualNave = animacaoNave.getKeyFrame(stateTimeNave, true);
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
        obstacleTimer += delta;
        if (obstacleTimer > 1f) {
            obstacleTimer = 0;
            createObstacle();
        }

        Rectangle shipRect = new Rectangle(x, y, shipWidth, shipHeight);

        Iterator<Rectangle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Rectangle obstacle = iter.next();
            obstacle.y -= 200 * delta;

            if (obstacle.y + obstacle.height < 0) {
                iter.remove();
            } else if (obstacle.overlaps(shipRect)) {
                iter.remove();
            }
        }


        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(frameAtualNave, x, y, shipWidth, shipHeight);
        for (Rectangle obstacle : obstacles) {
            batch.draw(obstacleTexture, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
        batch.end();
        }


    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
