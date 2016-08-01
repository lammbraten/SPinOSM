package de.spinosm.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.JButton;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	final static JXMapKit jXMapKit = new JXMapKit();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {


			        // Display the viewer in a JFrame
					new MainFrame();
					
					
			        JFrame frame = new JFrame("JXMapviewer2 Example 6");
			        frame.getContentPane().add(jXMapKit);
			        frame.setSize(800, 600);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        
        JXMapViewer map = jXMapKit.getMainMap();
        
		GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
		GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
		GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
		GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
		GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);

		// Create a track from the geo-positions
		List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);
        
		RoutePainter routePainter = new RoutePainter(track);

		// Set the focus
		map.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        jXMapKit.setZoom(11);


        jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) { 
                // ignore
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {



                // convert to world bitmap
        		


                
            }
        });
	}

}
