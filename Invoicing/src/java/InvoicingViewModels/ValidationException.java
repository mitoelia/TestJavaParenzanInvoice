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
public class ValidationException extends Exception {
    public String errorField;
    
    public ValidationException(String errorField, String message)
    {
        super(message);
        this.errorField = errorField;
    }
}
