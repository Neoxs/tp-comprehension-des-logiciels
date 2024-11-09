package clustering.visualization;

import java.awt.*;

public interface Paintable {

    void paint(Graphics2D g,
               int xDisplayOffset,
               int yDisplayOffset,
               double xDisplayFactor,
               double yDisplayFactor,
               boolean decorated
    );

}
