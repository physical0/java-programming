package com.java.pertemuan3.tugas.model;

public abstract class AbstractTableModel {

    public abstract int getRowCount();

    public abstract int getColumnCount();

    public abstract Object getValueAt(int rowIndex, int columnIndex);

}
