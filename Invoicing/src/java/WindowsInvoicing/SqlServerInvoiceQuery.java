/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WindowsInvoicing;

import InvoicingContracts.IInvoiceQuery;
import InvoicingContracts.QueryException;
import InvoicingDTOs.InvoiceDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elia
 */
public class SqlServerInvoiceQuery implements IInvoiceQuery {
    
    private Connection getConnection() throws SQLException
    {
        try {
            Class
                    .forName(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver")
                    .newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        Connection con = DriverManager.getConnection(
            "jdbc:sqlserver://127.0.0.1;"
            +"instanceName=ELIA_SQL;"
            +"databaseName=Invoicing;"
            +"user=invoicing;password=invoicing;");
        return con;
    }
    
    
    @Override
    public InvoiceDTO[] GetInvoices() throws QueryException {
        try{
            Connection conn = getConnection();
            
            List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT "
                            + "InvoiceHeaderId"
                            + ", Number"
                            + ", Date"
                            + ", CustomerName"
                            + ", TotalPrice"
                            + ", Items"
                            + " FROM dbo.invoice");
            while(rs.next()){
                InvoiceDTO dto = new InvoiceDTO();
                dto.id = Integer.toString(rs.getInt(1));
                dto.number = rs.getString(2);
                dto.date = rs.getString(3);
                dto.customerName = rs.getString(4);
                dto.totalPrice = rs.getFloat(5);
                dto.itemsCount = rs.getInt(6);
                
                invoices.add(dto);
            }
            rs.close();
            conn.close();
            InvoiceDTO[] dtos = new InvoiceDTO[]{};
            dtos = invoices.toArray(dtos);
            return dtos;
        } catch (SQLException ex){
            throw new QueryException("Errore SQL");
        }
    }
}
