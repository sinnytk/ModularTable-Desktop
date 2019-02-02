/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sinny
 */
import java.util.*;

public class CellList {
    private int rownum;
    private int cellnum;
    private int sheetnum;
    public CellList(int sheet, int row, int cell)
    {
        sheetnum = sheet;
        rownum = row;
        cellnum = cell;
    }
    public int getSheet()
    {
        return sheetnum;
    }
    public int getRow()
    {
        return rownum;
    }
    public int getCol()
    {
        return cellnum;
    }
    
    
    
}

