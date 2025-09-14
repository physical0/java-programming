package com.java.pertemuan3.tugas.model;

public abstract class AbstractTableModels {

    abstract String getColumnName(int index);

    abstract int getRowCount();

    abstract int getColumnCount();

    abstract Object getValueAt(int rowIndex, int columnIndex);

}
