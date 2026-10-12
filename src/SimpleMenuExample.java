import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class SimpleMenuExample {
    private static final Color NAVY = new Color(30, 45, 61);
    private static final Color GREEN = new Color(43, 122, 91);
    private static final Color BACKGROUND = new Color(245, 247, 248);
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.US);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            System.err.println("Using default Swing look and feel: " + exception.getMessage());
        }

        javax.swing.SwingUtilities.invokeLater(SimpleMenuExample::createWindow);
    }

    private static void createWindow() {
        JFrame frame = new JFrame("TheMenuDB | Menu Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760, 520));
        frame.setSize(900, 620);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(createMenuBar(frame));

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(22, 28, 24, 28));
        content.add(createHeader(), BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Item", "Category", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(232, 238, 235));
        table.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 224)));

        JPanel workspace = new JPanel(new BorderLayout(18, 0));
        workspace.setOpaque(false);
        workspace.add(createForm(model), BorderLayout.WEST);
        workspace.add(createMenuPanel(tableScroll), BorderLayout.CENTER);
        content.add(workspace, BorderLayout.CENTER);

        frame.setContentPane(content);
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(event -> frame.dispose());
        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.addActionListener(event -> JOptionPane.showMessageDialog(
                frame, "TheMenuDB Menu Manager\nAdd and review menu items.",
                "About TheMenuDB", JOptionPane.INFORMATION_MESSAGE));

        fileMenu.add(exitMenu);
        helpMenu.add(aboutMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    private static JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("TheMenuDB");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(NAVY);
        JLabel subtitle = new JLabel("Manage your restaurant menu");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(103, 115, 123));

        JPanel heading = new JPanel(new BorderLayout(0, 4));
        heading.setOpaque(false);
        heading.add(title, BorderLayout.NORTH);
        heading.add(subtitle, BorderLayout.CENTER);
        header.add(heading, BorderLayout.WEST);
        return header;
    }

    private static JPanel createForm(DefaultTableModel model) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 228)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        card.setPreferredSize(new Dimension(260, 0));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);

        JLabel title = new JLabel("Add menu item");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(NAVY);
        addRow(card, constraints, title);

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField priceField = new JTextField();
        addField(card, constraints, "Item name", nameField);
        addField(card, constraints, "Category", categoryField);
        addField(card, constraints, "Price (e.g. 8.50)", priceField);

        JButton addButton = new JButton("Add item");
        addButton.setBackground(GREEN);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(event -> {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            try {
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                if (name.isEmpty() || price.signum() <= 0) {
                    throw new IllegalArgumentException();
                }
                model.addRow(new Object[]{name, category.isEmpty() ? "Uncategorized" : category,
                        CURRENCY.format(price)});
                nameField.setText("");
                categoryField.setText("");
                priceField.setText("");
                nameField.requestFocusInWindow();
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(card,
                        "Enter an item name and a price greater than zero.",
                        "Check menu item", JOptionPane.WARNING_MESSAGE);
            }
        });
        addRow(card, constraints, addButton);

        JButton clearButton = new JButton("Clear fields");
        clearButton.addActionListener(event -> {
            nameField.setText("");
            categoryField.setText("");
            priceField.setText("");
        });
        addRow(card, constraints, clearButton);

        constraints.weighty = 1;
        card.add(new JLabel(), constraints);
        return card;
    }

    private static void addField(JPanel panel, GridBagConstraints constraints,
                                 String label, JTextField field) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addRow(panel, constraints, fieldLabel);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(210, 34));
        addRow(panel, constraints, field);
    }

    private static void addRow(JPanel panel, GridBagConstraints constraints,
                               Component component) {
        panel.add(component, constraints);
        constraints.gridy++;
    }

    private static JPanel createMenuPanel(JScrollPane tableScroll) {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        JLabel title = new JLabel("Menu items");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(NAVY);
        panel.add(title, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }
}
