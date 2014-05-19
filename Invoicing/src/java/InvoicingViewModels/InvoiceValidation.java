package InvoicingViewModels;

public class InvoiceValidation {
    
    public static void Validate(InvoiceViewModel viewModel) 
            throws ValidationException
    {
        if (viewModel.invoice_number == null)
           throw new ValidationException("invoice_number", "Invoice Number is missing");
        if (viewModel.invoice_number.equals(""))
           throw new ValidationException("invoice_number", "Invoice Number is missing");
    }
}
