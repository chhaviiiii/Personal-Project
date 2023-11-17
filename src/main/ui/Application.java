package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static ui.OrderTracker.JSON_STORE;

public class Application extends JFrame {
    private javax.swing.JFrame jframe;
    private JTable productTable;
    private final OrderTableModel orderTableModel;
    private final OrderManager orderManager;
    private final OrderCreationPanel orderCreationPanel;
    private final OrderSearchPanel orderSearchPanel;
    private final OrderStatusUpdatePanel orderStatusUpdatePanel;

    public Application() {
        // Initialization
        this.orderTableModel = new OrderTableModel(new ArrayList<>());
        this.orderManager = new OrderManager(JSON_STORE, orderTableModel, jframe);
        this.orderCreationPanel = new OrderCreationPanel(orderManager);
        this.orderSearchPanel = new OrderSearchPanel(orderManager);
        this.orderStatusUpdatePanel = new OrderStatusUpdatePanel(orderManager);
        orderCreationPanel.setBackground(Color.BLACK);
        orderStatusUpdatePanel.setBackground(Color.BLACK);
        orderSearchPanel.setBackground(Color.BLACK);


        initializeUI();
        setTitle("Order Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
    }

    private void showSplashScreen() {
        JWindow splashScreen = new JWindow();
        ImageIcon imageIcon = new ImageIcon("./data/image.png"); // Load the image
        JLabel label = new JLabel(imageIcon);
        splashScreen.getContentPane().add(label);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null); // Center on screen

        // Set a timer to dispose of the splash screen
        new Timer(3500, e -> {
            splashScreen.dispose();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSED)); // Dispatch window closed event
        }).start();

        splashScreen.setVisible(true);
    }


    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("./data/image3.png"); // Load the image
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        imagePanel.setPreferredSize(new Dimension(200, 100)); // Set preferred size for the image panel
        return imagePanel;
    }

    private void initializeUI() {

        JPanel imagePanel = createImagePanel();
        add(imagePanel, BorderLayout.NORTH);
        // Layout setup
        setJMenuBar(new MenuBuilder(orderManager).createMenuBar());

        // Create the main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add the product table to the center
        this.productTable = new JTable(orderTableModel);
        mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Add the order creation panel to the north
        mainPanel.add(orderCreationPanel, BorderLayout.EAST);

        // Add the order search panel to the west
        mainPanel.add(orderSearchPanel, BorderLayout.NORTH);

        // Add the order status update panel to the east
        mainPanel.add(orderStatusUpdatePanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application app = new Application(); // Create the main application instance
            app.showSplashScreen(); // Show the splash screen

            // Listener to make the main window visible after the splash screen is closed
            app.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    app.setVisible(true); // Make the main application window visible
                }
            });
        });
    }

}
