package ui;


import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static ui.OrderTracker.JSON_STORE;

// Main Graph User Interface Application
public class MainApplicationUI extends JFrame {
    private javax.swing.JFrame jframe;
    private JTable productTable;
    private final OrderTableModel orderTableModel;
    private final OrderManager orderManager;
    private final AddOrderPanel addOrderPanel;
    private final OrderSearchPanel orderSearchPanel;
    private final OrderStatusUpdatePanel orderStatusUpdatePanel;



    // EFFECTS: Initializes the main user interface for the application, including order management, order searching,
    // and order status update panels. Sets up the UI layout and appearance.
    // MODIFIES: Modifies this object (MainApplicationUI), creating and configuring various UI components and panels.
    public MainApplicationUI() {
        // Initialization
        this.orderTableModel = new OrderTableModel(new ArrayList<>());

        this.orderManager = new OrderManager(JSON_STORE, orderTableModel, jframe);
        this.addOrderPanel = new AddOrderPanel(orderManager);
        this.orderSearchPanel = new OrderSearchPanel(orderManager);
        this.orderStatusUpdatePanel = new OrderStatusUpdatePanel(orderManager);
        addOrderPanel.setBackground(Color.BLACK);
        orderStatusUpdatePanel.setBackground(Color.BLACK);
        orderStatusUpdatePanel.setForeground(Color.WHITE);
        orderSearchPanel.setBackground(Color.BLACK);


        initializeUI();
        setTitle("Order Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(705, 600);
        setLocationRelativeTo(null); // Center on screen
    }

    // EFFECTS: Displays a splash screen with an image for a set duration when the application starts.
    // The splash screen is centered on the screen.
    private void showSplashScreen() {
        JWindow splashScreen = new JWindow();
        ImageIcon imageIcon = new ImageIcon("./data/image.png");

        // Resize the image
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);

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

    // EFFECTS: Creates and returns a JPanel that displays an image. The image is resized to fit the panel's dimensions.
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

    // EFFECTS: Sets up the main user interface layout, including menu bar, image panel, main panel,
    // table for displaying orders, and top and bottom panels for order management functionalities.
    // Calls showSplashScreen method to display the splash screen.
    // MODIFIES: Modifies this object (MainApplicationUI), setting up and arranging its internal components.
    private void initializeUI() {
        createMenuBar();
        setupImagePanel();
        setupMainPanel();
        setupWindowListener();
        showSplashScreen();
    }

    // EFFECTS: Creates and sets the menu bar for the application window.
    // REQUIRES: orderManager must be initialized.
    // MODIFIES: this (MainApplicationUI object)
    private void createMenuBar() {
        setJMenuBar(new MenuBuilder(orderManager).createMenuBar());
    }

    // EFFECTS: Creates and adds the image panel to the main application window.
    // MODIFIES: this (MainApplicationUI object)
    private void setupImagePanel() {
        JPanel imagePanel = createImagePanel(); // Image
        add(imagePanel, BorderLayout.NORTH);
    }

    // EFFECTS: Initializes and configures the main panel, including setting up the table, top panel, and bottom panel.
    // REQUIRES: orderTableModel must be initialized.
    // MODIFIES: this (MainApplicationUI object)
    private void setupMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Layout setup
        mainPanel.setBackground(Color.BLACK);

        setupTable(mainPanel);
        setupTopPanel(mainPanel);
        setupBottomPanel(mainPanel);

        add(mainPanel);
    }

    // EFFECTS: Initializes the table for displaying data and adds it to the main panel.
    // REQUIRES: orderTableModel must be initialized and mainPanel must be provided as an argument.
    // MODIFIES: mainPanel (JPanel object)
    private void setupTable(JPanel mainPanel) {
        productTable = new JTable(orderTableModel); // Table setup
        customizeTable(productTable);
        mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

    // EFFECTS: Sets up the top panel with AddOrderPanel and OrderSearchPanel and adds it to the main panel.
    // REQUIRES: addOrderPanel and orderSearchPanel must be initialized and mainPanel must be provided as an argument.
    // MODIFIES: mainPanel (JPanel object)
    private void setupTopPanel(JPanel mainPanel) {
        JPanel topPanel = new JPanel(new BorderLayout()); // Top panel setup
        topPanel.setOpaque(false);
        topPanel.add(addOrderPanel, BorderLayout.WEST);
        topPanel.add(orderSearchPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    // EFFECTS: Sets up the bottom panel with OrderStatusUpdatePanel and adds it to the main panel.
    // REQUIRES: orderStatusUpdatePanel must be initialized and mainPanel must be provided as an argument.
    // MODIFIES: mainPanel (JPanel object)
    private void setupBottomPanel(JPanel mainPanel) {
        orderStatusUpdatePanel.setOpaque(false); // Bottom panel setup
        mainPanel.add(orderStatusUpdatePanel, BorderLayout.SOUTH);
    }

    // EFFECTS: Adds a window listener that, iterates over the EventLog and prints each event to the console.
    // REQUIRES: EventLog must be accessible.
    // MODIFIES: this (MainApplicationUI object
    private void setupWindowListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
            }
        });
    }


    // EFFECTS: Customizes the appearance of the provided JTable, setting its opacity, foreground,
    // and background colors, as well as the colors of its header.
    // MODIFIES: Modifies the provided JTable object, altering its visual properties.
    private void customizeTable(JTable table) {
        table.setOpaque(false);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.BLACK);
    }

    // EFFECTS: Entry point for the application. Initializes and displays the main application UI,
    // including a splash screen.
    // Sets up a window listener to make the main application window visible after the splash screen is closed.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplicationUI app = new MainApplicationUI(); // Create the main application instance
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

