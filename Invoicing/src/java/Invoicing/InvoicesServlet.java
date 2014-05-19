/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Invoicing;

import InvoicingContracts.IInvoiceQuery;
import InvoicingContracts.IInvoiceRepository;
import InvoicingContracts.QueryException;
import InvoicingContracts.RepositoryException;
import InvoicingDTOs.InvoiceDTO;
import InvoicingViewModels.InvoiceValidation;
import InvoicingViewModels.InvoiceViewModel;
import InvoicingViewModels.ResultViewModel;
import InvoicingViewModels.ValidationException;
import WindowsInvoicing.SqlServerInvoiceQuery;
import WindowsInvoicing.SqlServerInvoiceRepository;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Elia
 */
@WebServlet(name = "InvoicesServlet", urlPatterns = {"/Invoices"})
public class InvoicesServlet extends HttpServlet {
    
    private IInvoiceQuery _query;
    
    protected IInvoiceQuery getQuery() {
        if (_query == null){
            _query = new SqlServerInvoiceQuery();
        }
        return _query;
    }

    private IInvoiceRepository _repository;
    
    protected IInvoiceRepository getRepository()
    {
        if (_repository == null)
        {
            // _repository = new FileSystemInvoiceRepository("C:\\invoicing");
            _repository = new SqlServerInvoiceRepository();
        }
        return _repository;
    }
    
    private Gson _gson;
    
    protected Gson getGson()
    {
        if (_gson == null)
        {
            _gson = new Gson();
        }
        return _gson;
    }
    
    protected InvoiceViewModel deserializeInvoice(HttpServletRequest request) throws IOException
    {
        return deserializeInvoice(request.getReader());
    }
    
    protected InvoiceViewModel deserializeInvoice(BufferedReader reader)
    {
        InvoiceViewModel viewModel = 
                (InvoiceViewModel) getGson().fromJson(reader,  InvoiceViewModel.class);
        return viewModel;
    }
    
    protected void serializeInvoice(
            HttpServletResponse response
            , InvoiceViewModel viewModel) throws IOException
    {
        response.setContentType("application/json");
        serializeInvoice(response.getWriter(), viewModel);
    }
    
    protected void serializeInvoice(
            PrintWriter writer
            , InvoiceViewModel viewModel)
    {
        JsonWriter gsonWriter = new JsonWriter(writer);
        getGson().toJson(viewModel, InvoiceViewModel.class, gsonWriter);
    }
    
    protected void writeSuccess(HttpServletResponse response) 
            throws IOException
    {
        response.setContentType("application/json");
        ResultViewModel viewModel = new ResultViewModel();
        viewModel.success = true;
        JsonWriter gsonWriter = new JsonWriter(response.getWriter());
        getGson().toJson(viewModel, ResultViewModel.class, gsonWriter);
    }
    
    protected void writeFail(String message, HttpServletResponse response) 
            throws IOException
    {
        response.setContentType("application/json");
        ResultViewModel viewModel = new ResultViewModel();
        viewModel.success = false;
        viewModel.message = message;
        viewModel.errorField = "";
        JsonWriter gsonWriter = new JsonWriter(response.getWriter());
        getGson().toJson(viewModel, ResultViewModel.class, gsonWriter);
   }
        
    protected void writeValidationFail(String errorField, String message, HttpServletResponse response) 
            throws IOException
    {
        response.setContentType("application/json");
        ResultViewModel viewModel = new ResultViewModel();
        viewModel.success = false;
        viewModel.errorField = errorField;
        viewModel.message = message;
        JsonWriter gsonWriter = new JsonWriter(response.getWriter());
        getGson().toJson(viewModel, ResultViewModel.class, gsonWriter);
    }
        
    protected void writeRepositoryFail(String message, HttpServletResponse response) 
            throws IOException
    {
        response.setContentType("application/json");
        ResultViewModel viewModel = new ResultViewModel();
        viewModel.success = false;
        viewModel.message = message;
        JsonWriter gsonWriter = new JsonWriter(response.getWriter());
        getGson().toJson(viewModel, ResultViewModel.class, gsonWriter);
    }    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            InvoiceDTO[] dtos = getQuery().GetInvoices();
            response.setContentType("application/json");
            JsonWriter gsonWriter = new JsonWriter(response.getWriter());
            getGson().toJson(dtos, InvoiceDTO[].class, gsonWriter);
            
        }
        catch(QueryException ex){
            writeRepositoryFail(ex.getMessage(), response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            InvoiceViewModel viewModel = 
                    this.deserializeInvoice(request);
            InvoiceValidation.Validate(viewModel);
        
            getRepository().InsertInvoice(viewModel);
        
            writeSuccess(response);
        }
        catch(ValidationException ex)
        {
            writeValidationFail(ex.errorField, ex.getMessage(),  response);
        }
        catch(RepositoryException ex)
        {
            writeRepositoryFail(ex.getMessage(),  response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
