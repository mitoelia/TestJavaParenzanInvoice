<html>
<head>
  <title>Simple Invoicing - Built with AngularJS</title>
  <meta name="description" content="AngularJS and Angular Code Example for creating Invoices and Invoicing Application">
  <script src="http://code.jquery.com/jquery-1.7.1.js" type="text/javascript"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
  <script type="text/javascript" src="js/edit.js"></script>
</head>
<body ng:app="jqanim" ng:controller="InvoiceController" >
    <input type="hidden" name="invoiceHeaderId" id="invoiceHeaderId" value="<%= request.getParameter("invoiceHeaderId") %>">
  <div id="css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/css/bootstrap.min.css">
  </div>
<div class="container" width="800px" id="invoice" >
  <a ng-hide="printMode" href="https://github.com/metaware/angular-invoicing"><img ng-hide="printMode" style="position: absolute; top: 0; left: 0; border: 0;" src="https://s3.amazonaws.com/github/ribbons/forkme_left_red_aa0000.png" alt="Fork me on GitHub"></a>
  <table class="table">
    <tr>
      <td align="center" style="background-color:#357EBD;color:#FFF" colspan="2">
        INVOICE
      </td>
    </tr>
    <tr>
      <td>
        <h4>Invoice #<input type="text" id="invoice_number" ng-model="invoice.invoice_number" /></h4>
      </td>
      <td align="right">
        <input type='file' id="imgInp" />
        <img ng-hide="logoRemoved" id="company_logo" src="images/metaware_logo.png" alt="your image" width="300" />
        <br/>
        <div class="noPrint" ng-hide="printMode">
          <a ng-click="editLogo()" href >Edit Logo</a>
          <a ng-click="removeLogo()" id="remove_logo" href >Remove logo</a>
        </div>
      </td>
    </tr>
  </table>
  <table class="table">
    <tr>
      <td>
        <div class="infos">
          <div><strong><input type="text" ng-model="invoice.customer_info.name"/></strong></div>
          <div><input type="text" ng-model="invoice.customer_info.web_link"/></div>
          <div><input type="text" ng-model="invoice.customer_info.address1"/></div>
          <div><input type="text" ng-model="invoice.customer_info.address2"/></div>
          <div><input type="text" ng-model="invoice.customer_info.postal"/></div>
        </div>
      </td>
      <td align="right">
        <div class="align-right">
          <div><strong><input type="text" ng-model="invoice.company_info.name"/></strong></div>
          <div><input type="text" ng-model="invoice.company_info.web_link"/></div>
          <div><input type="text" ng-model="invoice.company_info.address1"/></div>
          <div><input type="text" ng-model="invoice.company_info.address2"/></div>
          <div><input type="text" ng-model="invoice.company_info.postal"/></div>
        </div>
      </td>
    </tr>
  </table>
  <table class="table table-striped" align="center" >
      <tr>
          <th></th>
          <th>Description</th>
          <th>Qty</th>
          <th>Cost</th>
          <th style="text-align:right;">Total</th>
          
      </tr>
      <tr ng:repeat="item in invoice.items" style="display: none" jq:animate="dropdown;250">
          <td><a href ng-hide="printMode" ng-click="removeItem(item)" class="btn btn-danger">[X]</a></td>
          <td><input ng:model="item.description" placeholder="Description"></td>
          <td><input ng:model="item.qty" value="1" size="4" ng:required ng:validate="integer" placeholder="qty"></td>
          <td><input ng:model="item.cost" value="0.00" ng:required ng:validate="number" size="6" placeholder="cost"></td>
          <td align="right">{{item.cost * item.qty | currency}}</td>
          
      </tr>
      <tr ng-hide="printMode">
          <td colspan="5"><a class="btn btn-primary" href ng:click="addItem()" >Add Item</a></td>
      </tr>
      <tr>
          <td colspan="4" align="right">
            Sub Total
          </td>
          <td align="right">
            {{invoice_sub_total() | currency}}
          </td>
      </tr>
      <tr>
          <td colspan="4" align="right">
            Tax(%): <input ng:model="invoice.tax" ng:validate="number" style="width:43px">
          </td>
          <td align="right">
            {{calculate_tax() | currency}}
          </td>
      </tr>
      <tr>
          <td colspan="4" align="right">
            Grand Total:
          </td>
          <td align="right">
            {{calculate_grand_total() | currency}}
          </td>
      </tr>
  </table>
  <div class="noPrint">
    <a href="#" class="btn btn-primary" ng-hide="printMode" ng-click="send()">Send</a>
    <a href="#" class="btn btn-primary" ng-show="printMode" ng-click="printInfo()">Print</a>
    <a href="#" class="btn btn-primary" ng-click="clearLocalStorage()">Reset</a>
    <a href="#" class="btn btn-primary" ng-hide="printMode" ng-click="printMode = true;">Turn On Print Mode</a>
    <a href="#" class="btn btn-primary" ng-show="printMode" ng-click="printMode = false;">Turn Off Print Mode</a>
    <a class="btn btn-primary" href ng:click="carica()" >load</a>
  </div>
</div>

<div ng-hide="printMode" class="copy noPrint">
  <a href="http://jasdeep.ca/?utm_source=angular_invoicing">Jasdeep Singh</a> & 
  <a href="http://github.com/manpreetrules">Manpreet Singh</a>
  Made with 
  <span class="love">&#9829;</span> in Toronto by 
  <a href="http://metawarelabs.com/?utm_source=angular_invoicing">Metaware Labs Inc.</a>
</div>
</body>
<!--<script src="js/jquery-1.10.2.js"></script>
<script src="js/fattura.js"></script>-->
</html>
