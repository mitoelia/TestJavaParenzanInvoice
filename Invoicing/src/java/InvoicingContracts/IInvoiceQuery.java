/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InvoicingContracts;

import InvoicingDTOs.InvoiceDTO;

/**
 *
 * @author Elia
 */
public interface IInvoiceQuery {
    InvoiceDTO[] GetInvoices() throws QueryException;
    
}
