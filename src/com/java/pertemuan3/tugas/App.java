package com.java.pertemuan3.tugas;
import com.java.pertemuan3.tugas.model.ProductModel;
import com.java.pertemuan3.tugas.model.ProductTableModel;
import java.util.ArrayList;
import javax.swing.JFrame;

public class App extends JFrame {


    ArrayList<ProductModel> products = new ArrayList<>();
    ProductTableModel productTableModel = new ProductTableModel(products);



    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public App() {

    }
}
