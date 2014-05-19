

package Fattura;

import InvoicingContracts.QueryException;
import InvoicingDTOs.InvoiceDTO;
import InvoicingViewModels.CompanyInfoViewModel;
import InvoicingViewModels.InvoiceDetailViewModel;
import InvoicingViewModels.InvoiceViewModel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "FatturaServlet", urlPatterns = {"/fattura"})
public class FatturaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FatturaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FatturaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try{
            Connection conn = getConnection();
            String id = request.getParameter("invoiceHeaderId");
            List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();
            Statement stmt = conn.createStatement();
            String command = "select number, date, customerName from Invoicing.dbo.InvoiceHeaders where invoiceHeaderId="+id;
            ResultSet rs = stmt.executeQuery(command);
            InvoiceViewModel viewModel = new InvoiceViewModel();
            
            
            rs.next(); // la prima riga
            viewModel.invoice_number = rs.getString(1);
            viewModel.customer_info = new CompanyInfoViewModel();
            viewModel.customer_info.name=rs.getString(3);
            
            List<InvoiceDetailViewModel> items = new ArrayList<InvoiceDetailViewModel>();

            rs = stmt.executeQuery("select quantity, description, unitPrice, Price from Invoicing.dbo.invoiceItems "
                    + "where invoiceHeaderId ="
                    + request.getParameter("invoiceHeaderId"));
            while(rs.next())
            {
                InvoiceDetailViewModel detail = new InvoiceDetailViewModel();
                detail.cost = Float.parseFloat(rs.getString(3));
                detail.description = rs.getString(2);
                detail.qty = Integer.parseInt(rs.getString(1));
                items.add(detail);
            }
            
            InvoiceDetailViewModel[] itemsB = new InvoiceDetailViewModel[]{};
            
            
            itemsB = items.toArray(itemsB);
            
            viewModel.items = itemsB;
            
            response.setContentType("application/json");
            JsonWriter gsonWriter = new JsonWriter(response.getWriter());
            getGson().toJson(viewModel, InvoiceViewModel.class, gsonWriter);
            
            rs.close();
            
            conn.close();
        }
        catch(SQLException ex)
        {
            try {
                throw new QueryException("Errore SQL");
            } catch (QueryException ex1) {
                Logger.getLogger(FatturaServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Gson _gson;
    
    protected Gson getGson()
    {
        if (_gson == null)
        {
            _gson = new Gson();
        }
        return _gson;
    }

}
