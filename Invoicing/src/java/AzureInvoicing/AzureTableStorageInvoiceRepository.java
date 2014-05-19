/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AzureInvoicing;

import InvoicingContracts.IInvoiceRepository;
import InvoicingViewModels.InvoiceViewModel;

/**
 *
 * @author marco.parenzan
 */
public class AzureTableStorageInvoiceRepository 
    implements IInvoiceRepository {

    @Override
    public String InsertInvoice(InvoiceViewModel viewModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
