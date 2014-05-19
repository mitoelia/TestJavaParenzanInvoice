/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package WindowsInvoicing;

import InvoicingContracts.IInvoiceRepository;
import InvoicingContracts.RepositoryException;
import InvoicingViewModels.InvoiceViewModel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author marco.parenzan
 */
public class FileSystemInvoiceRepository 
    implements IInvoiceRepository {
     
    private Gson _gson;
    
    protected Gson getGson()
    {
        if (_gson == null)
        {
            _gson = new Gson();
        }
        return _gson;
    }
    
    private String _rootFolder;
    
    public FileSystemInvoiceRepository(String rootFolder)
    {
        this._rootFolder = rootFolder;
    }

    @Override
    public String InsertInvoice(InvoiceViewModel viewModel) 
            throws RepositoryException 
    {
        try {
            String id = UUID.randomUUID().toString();
            FileWriter fileWriter = new FileWriter(
                    this._rootFolder
                            + "\\invoices\\"
                            + id
                            + ".json"
            );
            JsonWriter gsonWriter = new JsonWriter(fileWriter);
            getGson().toJson(viewModel, InvoiceViewModel.class, gsonWriter);
            gsonWriter.close();
            
            return id;
        } catch (IOException ex) {
            throw new  RepositoryException("Cannot insert invoice");
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
