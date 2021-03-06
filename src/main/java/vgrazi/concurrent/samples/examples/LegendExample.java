package vgrazi.concurrent.samples.examples;

import vgrazi.concurrent.samples.ConcurrentExampleConstants;
import vgrazi.concurrent.samples.ExampleType;
import vgrazi.concurrent.samples.sprites.ConcurrentSprite;
import vgrazi.concurrent.samples.sprites.ConcurrentTextSprite;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @user vgrazi.
 * Time: 12:26:11 AM
 */
public class LegendExample extends ConcurrentExample {
  int position = 0;
  private final JButton startButton = new JButton("Start Demo");
  private final ConcurrentSprite[] sprites = new ConcurrentSprite[30];

  private boolean initialized = false;

  public LegendExample(String label, Container frame, int slideNumber) {
    super(label, frame, ExampleType.BLOCKING, 500, false, slideNumber);
  }

  protected void initializeComponents() {
    if (!initialized) {
      initializeStartButton();
      initialized = true;
    }
  }

  @Override
  protected void setDefaultState() {
  }

  public String getDescriptionHtml() {
    return "";
  }

  private void initializeStartButton() {
    initializeButton(startButton, new Runnable() {
      public void run() {
        setAnimationCanvasVisible(true);
        startDemo();
        setState(1);
      }
    });
  }

  private void startDemo() {
    switch (position) {
      case 0:
        startButton.setText("Continue Demo");
        // reset just in case we are coming around again
        resetExample();
        // todo: add justification - left, right, center.
        sprites[0] = createTextSprite("Gray is   ");
        sprites[6] = createTextSprite("a block  ");
        sprites[7] = createTextSprite("of some  ");
        sprites[8] = createTextSprite("sort.   ");
        sprites[0].setAcquired();
        sprites[6].setAcquired();
        sprites[7].setAcquired();
        sprites[8].setAcquired();
        position++;
        break;
      case 1:
        sprites[1] = createTextSprite("Threads are ");
        sprites[2] = createTextSprite("represented ");
        sprites[3] = createTextSprite(" as arrows ");
        sprites[4] = createAcquiringSprite();
        position++;
        break;
      case 2:
        sprites[4].setAcquired();
        ((ConcurrentTextSprite) sprites[1]).setText("");
        ((ConcurrentTextSprite) sprites[2]).setText("Acquired");
        ((ConcurrentTextSprite) sprites[3]).setText("the lock  ");
        sprites[1].setAcquired();
        sprites[2].setAcquired();
        sprites[3].setAcquired();
        position++;
        break;
      case 3:
        ((ConcurrentTextSprite) sprites[2]).setText("Released");
        sprites[1].setReleased();
        sprites[2].setReleased();
        sprites[3].setReleased();
        sprites[4].setReleased();
        position++;
        break;
      case 4:
        resetExample();
        sprites[11] = createTextSprite("A Runnable ");
        sprites[12] = createAcquiringSprite(ConcurrentSprite.SpriteType.RUNNABLE);
        position++;
        break;
      case 5:
        sprites[13] = createTextSprite("Working ");
        sprites[12].setAcquired();
        position++;
        break;
      case 6:
        ((ConcurrentTextSprite) sprites[13]).setText("Released");
        sprites[11].setReleased();
        sprites[12].setReleased();
        sprites[13].setReleased();
        position++;
        break;
      case 7:
        resetExample();
        sprites[15] = createTextSprite("Any object");
        sprites[16] = createAcquiringSprite();
        sprites[16].setType(ConcurrentSprite.SpriteType.OVAL);
        position++;
        break;
      case 8:
        sprites[17] = createTextSprite("Queued");
        sprites[16].setAcquired();
        position++;
        break;
      case 9:
        ((ConcurrentTextSprite) sprites[17]).setText("Released");
        sprites[15].setReleased();
        sprites[16].setReleased();
        sprites[17].setReleased();
        position++;
        break;
      case 10:
        startButton.setText("Restart Demo");
        sprites[18] = createTextSprite("Page Down ");
        sprites[19] = createTextSprite("to start    ");
        sprites[20] = createTextSprite("presentation");
        sprites[18].setAcquiring();
        sprites[19].setAcquiring();
        sprites[20].setAcquiring();
        Executors.newScheduledThreadPool(1).schedule(new Runnable() {
          public void run() {
            sprites[18].setReleased();
            sprites[19].setReleased();
            sprites[20].setReleased();
          }
        }, 2, TimeUnit.SECONDS);
        position = 0;
        break;
    }
  }

  @Override
  public void reset() {
    resetExample();
    position = 0;
  }

  private void resetExample() {
    super.reset();
    message1("  ", ConcurrentExampleConstants.DEFAULT_BACKGROUND);
    message2("  ", ConcurrentExampleConstants.DEFAULT_BACKGROUND);
  }

  // todo: add snippet explaining how the snippets work
  protected String getSnippet() {
    String snippet;
    snippet = "";
    return snippet;
  }
}