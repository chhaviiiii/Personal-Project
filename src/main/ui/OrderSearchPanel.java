package ui;

import javax.swing.*;
import java.awt.*;

public class OrderSearchPanel extends JPanel {
    private final OrderManager orderManager;

    public OrderSearchPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createSearchField(), BorderLayout.CENTER);
    }

    private JTextField createSearchField() {
        JTextField searchField = new JTextField();
        // Add action listener to search for an order by ID
        return searchField;
    }
}
