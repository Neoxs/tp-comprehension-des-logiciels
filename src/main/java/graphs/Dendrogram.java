package graphs;

import clustering.models.Cluster;
import clustering.visualization.DendrogramPanel;

import javax.swing.*;
import java.awt.*;


public class Dendrogram extends JFrame {

    public Dendrogram(Cluster cluster) {
        setSize(500, 400);
        setLocation(100, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.cyan);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        dp.setModel(cluster);
        setVisible(true);
    }
}
