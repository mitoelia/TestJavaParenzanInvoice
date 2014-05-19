/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WindowsInvoicing;

import InvoicingContracts.IInvoiceRepository;
import InvoicingContracts.RepositoryException;
import InvoicingViewModels.InvoiceDetailViewModel;
import InvoicingViewModels.InvoiceViewModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author marco.parenzan
 */
public class SqlServerInvoiceRepository 
    implements IInvoiceRepository
{

    
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
    public String InsertInvoice(InvoiceViewModel viewModel) 
        throws RepositoryException
    {

        try{
            String new_id = "";
            Connection conn = getConnection();
            
            String headerSql = 
                    "INSERT INTO invoiceHeaders ("
                    + "Number, Date, CustomerName"
                    + ")"
                    + " VALUES ("
                    + viewModel.invoice_number
                    + ", " + "'2014-05-07'"
                    + ", '" + viewModel.customer_info.name + "'"
                    + ");"
                    + "SELECT SCOPE_IDENTITY()"
            ;
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(headerSql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            new_id = Integer.toString(id);
            rs.close();
            
            int i;
            for (i = 0; i<viewModel.items.length; i++)
            {
                InvoiceDetailViewModel detail = 
                        viewModel.items[i];
                
                String detailSql = "INSERT INTO invoiceItems ("
                        + "InvoiceHeaderId"
                        + ", Quantity"
                        + ", Description"
                        + ", UnitPrice"
                        + ", Price"
                        + ") VALUES ("
                        + new_id
                        + ", " + Integer.toString(detail.qty)
                        + ", '" + detail.description + "'"
                        + ", " + Float.toString(detail.cost)
                        + ", " + Float.toString(detail.cost * detail.qty)
                        + ")";
                
                Statement stmtDetail = conn.createStatement();
                stmtDetail.execute(detailSql);
            }
            
            conn.close();
            return new_id;
        }
        catch(SQLException ex)
        {
            throw new RepositoryException("Errore SQL");
        }
    }

    @Override
    public void UpdateInvoice(String id, InvoiceViewModel viewModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteInvoice(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceViewModel GetInvoice(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
