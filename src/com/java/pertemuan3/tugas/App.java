package com.java.pertemuan3.tugas;
import com.java.pertemuan3.tugas.model.ProductModel;
import com.java.pertemuan3.tugas.model.ProductTableModel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class App extends JFrame {

    ArrayList<ProductModel> products = new ArrayList<>();
    ProductTableModel productTableModel = new ProductTableModel(products);
    JPanel mainPanel = new JPanel();
    JLabel[] labels = {
        new JLabel("ID"),
        new JLabel("Name"),
        new JLabel("Price"),
        new JLabel("Quantity")
    };


    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Hey there!");
        new App();
    }

    public App() {
        setTitle("Product Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setLayout(new GridLayout(2, 1));

        Border mainBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "Main Control Panel");
        mainPanel.setBorder(mainBorder);

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);

        add(mainPanel);
        


    }
}
