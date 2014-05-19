/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function(){
   
    $.getJSON(  
            'Invoices'
            , function(result){
                var tabella_fatture = $("#fatture > tbody");
                $(result).each(function(i, item){
                    var tr_html =
                            "<tr data-fattura-id='"+ item.id +"'>"
                            + "<td>" + item.number + "</td>"
                            + "<td>" + item.date + "</td>"
                            + "<td>" + item.customerName + "</td>"
                            + "<td>" + item.totalPrice + "</td>"
                            + "<td>" + item.itemsCount + "</td>"
                            + "<td>" + "<a class='btn btn-default' href='edit.jsp?invoiceHeaderId="+ item.id +"'>edit</a>" + "</td>"
                            + "</tr>";
                    tabella_fatture.append(tr_html);
                });
                    
            }
                    
    );
    

});