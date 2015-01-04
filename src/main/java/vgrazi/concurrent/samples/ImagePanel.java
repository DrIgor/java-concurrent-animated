package vgrazi.concurrent.samples;

import vgrazi.concurrent.samples.examples.ConcurrentExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import java.util.logging.Level;

//import org.apache.log4j.Logger;

/*
 * User: vgrazi
 * Date: Sep 6, 2005
 * Time: 2:44:17 PM
 */
public class ImagePanel extends JPanel {
  private int yOverride = 0;
  private ConcurrentExample concurrentExample;
  private int xPos;
  private int yPos;
  private final static Logger logger = Logger.getLogger(ImagePanel.class.getName());

  public void setVisible(boolean aFlag) {
    super.setVisible(aFlag);
  }

  public ImagePanel(ConcurrentExample example) {
    setLayout(new BorderLayout());

    concurrentExample = example;
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(concurrentExample != null && concurrentExample.getAnimationCanvas().isVisible()) {
          xPos = e.getX();
          yPos = e.getY();
          logger.log(Level.INFO, "ConcurrentSpriteCanvas.mousePressed " + e);
        }
      }

      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() > 1) {
          toggleView();
          logger.log(Level.INFO, "ConcurrentSpriteCanvas.mouseDouble Clicked " + e);
        }
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        if (concurrentExample != null && concurrentExample.getAnimationCanvas().isVisible()) {
          int deltaX = e.getX() - xPos;
          int deltaY = e.getY() - yPos;
          xPos = e.getX();
          yPos = e.getY();
          Point location = concurrentExample.getAnimationCanvas().getLocation();
          concurrentExample.getAnimationCanvas().setLocation((int) location.getX() + deltaX, (int) (location.getY() + deltaY));
          logger.log(Level.INFO, "ConcurrentSpriteCanvas.mouseDragged " + e);
        }
      }
    });

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        if (clickCount > 1) {
          if (concurrentExample != null) {
            concurrentExample.setAnimationCanvasVisible(true);
          }
        }
      }
    });

    JPanel panel = new JPanel(new GridLayout(1, 9));
    panel.setOpaque(false);
    addSpacers(panel);
    add(panel, BorderLayout.SOUTH);
  }

  /**
   * If the example is displayed, changes to imageName view. If an imageName is displayed, changes to example view
   */
  public void toggleView() {
    if (concurrentExample != null) {
      if (concurrentExample.getAnimationCanvas().isVisible()) {
        setImageView();
      } else {
        setExampleView();
      }
    } else {
      setImageView();
    }
  }

  private void redoLayout() {
    getParent().doLayout();
    getParent().validate();
  }

  /**
   * Changes the display to show a concurrent example
   */
  public void setExampleView() {
    if (concurrentExample != null) {
      concurrentExample.setVisible(true);
      concurrentExample.setButtonsVisible(true);
    }
    setVisible(false);
  }

  /**
   * Changes the display to show this imageName label
   */
  public void setImageView() {
    if (concurrentExample != null) {
      concurrentExample.setVisible(false);
      concurrentExample.setButtonsVisible(false);
    }
    setVisible(true);
  }

  private void addSpacers(JPanel panel) {
    for (int i = 0; i < 4; i++) {
      addSpacer(panel);
    }
  }

  private void addSpacer(JPanel panel) {
    panel.add(new JLabel());
  }

  public void setBounds(int x, int y, int width, int height) {
    if (this.yOverride == 0 || yOverride > y) {
      super.setBounds(x, y, width, height);
      this.yOverride = y;
    } else {
      super.setBounds(x, this.yOverride, width, height);
    }
  }
}
