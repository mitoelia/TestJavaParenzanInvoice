/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InvoicingViewModels;

/**
 *
 * @author marco.parenzan
 */
public class InvoiceViewModel {
    
    public String invoice_number;
    public int tax;
    public CompanyInfoViewModel company_info;
    public CompanyInfoViewModel customer_info;
    public InvoiceDetailViewModel[] items;
        
}
