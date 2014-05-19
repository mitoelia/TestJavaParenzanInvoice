/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InvoicingContracts;

import InvoicingViewModels.InvoiceViewModel;

/**
 *
 * @author Elia
 */
public interface IInvoiceRepository {
    String InsertInvoice(InvoiceViewModel viewModel) throws RepositoryException;
    void UpdateInvoice(String id, InvoiceViewModel viewModel) throws RepositoryException;
    void DeleteInvoice(String id) throws RepositoryException;
    InvoiceViewModel GetInvoice(String id) throws RepositoryException;
}
