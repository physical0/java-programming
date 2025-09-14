package com.java.pertemuan3.tugas;
import com.java.pertemuan3.tugas.model.ProductModel;
import com.java.pertemuan3.tugas.model.ProductTableModel;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class InventoryApp extends JFrame {

    ArrayList<ProductModel> products = new ArrayList<>();
    ProductTableModel productTableModel = new ProductTableModel(products);
    JPanel mainPanel = new JPanel();
    JLabel[] labels = new JLabel[productTableModel.getColumnCount()];
    JTextField[] textFields = new JTextField[productTableModel.getColumnCount()];
    JPanel topPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JTable bottomPanel = new JTable(productTableModel);
    JScrollPane scrollPane = new JScrollPane(bottomPanel);

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Hey there! This is a test sout.");
        new InventoryApp();
    }

    public InventoryApp() {

        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.setOpaque(true);
        mainPanel.setVisible(true);

        Border mainBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "Main Control Panel");

        initComponents();

        setTitle("Product Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        add(mainPanel);

    }

    private void initComponents() {
        
        scrollPane.setPreferredSize(new Dimension(780, 50));

        int i = 0;
        for (JLabel label : labels) {
            label = new JLabel(productTableModel.getColumnName(i) + ": ");
            label.setBounds(50, 20 + i * 50, 200, 20);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            topPanel.add(label);
            i++;
        }

        for (int j = 0; j < textFields.length; j++) {
            textFields[j] = new JTextField();
            textFields[j].setBounds(250, 20 + j * 50, 500, 20);
            topPanel.add(textFields[j]);
        }

        Button addButton = new Button("Add Product");
        Button removeButton = new Button("Remove Product from the List");

        addButton.setBounds(50, 400, 100, 30);
        addButton.addActionListener(e -> addButtonHelper());
        removeButton.setBounds(200, 400, 200, 30);
        removeButton.addActionListener(e -> removeButtonHelper());

        centerPanel.add(addButton);
        centerPanel.add(removeButton);


        // Panel controls
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        topPanel.setLayout(null);
        
        centerPanel.setBackground(Color.WHITE);

        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel sizes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.425; 
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(topPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.011;  
        mainPanel.add(centerPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.564; 
        mainPanel.add(scrollPane, gbc);
    }

    private void addButtonHelper() {
        try {
            String id = textFields[0].getText();
            String name = textFields[1].getText();
            int price = Integer.parseInt(textFields[2].getText());
            int stock = Integer.parseInt(textFields[3].getText());

            ProductModel product = new ProductModel(id, name, price, stock);
            products.add(product);
            productTableModel.fireTableDataChanged();
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "There is a field not filled!");
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid price and quantity!");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
        }
    }

    private void removeButtonHelper() {
        int selectedRow = bottomPanel.getSelectedRow();
        if (selectedRow >= 0) {
            productTableModel.removeProduct(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to remove!");
        }
    }

}