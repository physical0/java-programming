package com.java.pertemuan3.tugas;
import com.java.pertemuan3.tugas.model.ProductModel;
import com.java.pertemuan3.tugas.model.ProductTableModel;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class App extends JFrame {

    ArrayList<ProductModel> products = new ArrayList<>();
    ProductTableModel productTableModel = new ProductTableModel(products);
    JPanel mainPanel = new JPanel();
    JLabel[] labels = new JLabel[productTableModel.getColumnCount()];
    JTextField[] textFields = new JTextField[productTableModel.getColumnCount()];

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Hey there!");
        new App();
    }

    public App() {

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
        add(mainPanel);

    }

    private void initComponents() {
        
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();


        int i = 0;
        for (JLabel label : labels) {
            label = new JLabel(productTableModel.getColumnName(i) + ": ");
            label.setBounds(50, 20 + i * 50, 200, 20);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            topPanel.add(label);
            i++;
        }

        int j = 0;
        for (JTextField textField : textFields) {
            textField = new JTextField();
            textField.setBounds(250, 20 + j * 50, 500, 20);
            topPanel.add(textField);
            j++;
        }

        Button addButton = new Button("Add Product");
        Button removeButton = new Button("Remove Product from the List");

        addButton.setBounds(50, 400, 100, 30);
        removeButton.setBounds(200, 400, 200, 30);
        centerPanel.add(addButton);
        centerPanel.add(removeButton);


        // Panel controls
        topPanel.setBounds(0, 0, 800, 300);
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
        gbc.weighty = 0.015;  
        mainPanel.add(centerPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.56; 
        mainPanel.add(bottomPanel, gbc);
    }
}
