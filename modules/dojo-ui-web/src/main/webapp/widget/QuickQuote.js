dojo.provide("dojotrader.widget.QuickQuote");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.ValidationTextbox");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojotrader.widget.BaseDaytraderPane");


dojo.widget.defineWidget(
	"dojotrader.widget.QuickQuote",
	[dojo.widget.HtmlWidget, dojotrader.widget.BaseDaytraderPane], {
		templatePath: dojo.uri.dojoUri("/dojotrader/widget/templates/HtmlQuickQuote.html"),
		widgetType: "QuickQuote",

		label: "Quick Quote",
		historySize: 6,
		
		_symbolTextBox: null,
		
		fillInTemplate: function(args, frag) {
			var ref = dojo.widget.createWidget("Button", {caption: "Get Quote"}, this.buttonNode);
			dojo.event.connect(ref, "onClick", this, "getQuickQuote");
			
			this._symbolTextBox = dojo.widget.createWidget("ValidationTextBox", {type: "text", validation: "false", size: "5"}, this.textBoxNode);
		},
		
		postCreate: function(){
			dojotrader.widget.QuickQuote.superclass.postCreate.call(this);
		},
		
		getQuickQuote: function() {
			var symbol = this._symbolTextBox.getValue(); 
	
			// determine if the symbol is already in the list
			var table = this.qqQuotesTable;
			var inTable = table.innerHTML.indexOf(">" + symbol + "<"); 
	
			if (symbol != "" && inTable == -1) {		
				dojo.io.bind({
    				method:  "GET",
    				//url: "/proxy/SoapProxy/getQuote?p1=" + symbol.value + "&format=json",
       				url: "/daytraderProxy/doProxy/getQuote?p1=" + symbol,
       				mimetype: "text/json",
    				load: dojo.lang.hitch(this,this.handleQuickQuote),
    				error: dojo.lang.hitch(this,this.handleError),
    				useCache: false,
                	preventCache: true
  				});
			}
  		},
	
		handleQuickQuote: function(type, data, evt) {		
			// unhide the table
			if (this.qqQuotesDisplay.style.display == "none")
				this.qqQuotesDisplay.style.display = "";
	
			// add the information to the table
			var row = this.qqQuotesTable.insertRow(1);
			cell = row.insertCell(0);
			this.appendTextNode(cell, data.getQuoteReturn.symbol);

			cell = row.insertCell(1);
			this.appendTextNode(cell, "$" + data.getQuoteReturn.price);
			
			cell = row.insertCell(2);
			this.appendTextNode(cell, data.getQuoteReturn.change);
			
			cell = row.insertCell(3);
			this.appendTextNode(cell, data.getQuoteReturn.volume);
	
			// handle the table style and history
			if (this.qqQuotesTable.rows.length == 2) {
				row.className = "row-even";
			} else {
				var prev = this.qqQuotesTable.rows[2];
				if (prev.className == "row-even")
					row.className = "row-odd";
				else
					row.className = "row-even";
			}

			if (this.qqQuotesTable.rows.length > this.historySize + 1) {
				this.qqQuotesTable.deleteRow(this.qqQuotesTable.rows.length - 1);
			}
		}
	}
);
