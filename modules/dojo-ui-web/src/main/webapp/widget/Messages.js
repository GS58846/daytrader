dojo.provide("dojotrader.widget.Messages");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojotrader.widget.BaseDaytraderPane");

dojo.widget.defineWidget(
	"dojotrader.widget.Messages", 
	[dojo.widget.HtmlWidget, dojotrader.widget.BaseDaytraderPane], {
		templatePath: dojo.uri.dojoUri("/dojotrader/widget/templates/HtmlMessages.html"),
		widgetType: "Messages",

		label: "Messages",
		historySize: 6,
			
		postCreate: function() {
			dojotrader.widget.Messages.superclass.postCreate.call(this);		
			
			dojo.event.topic.subscribe("/messages", this, "handleExternalEvents");
		},
		
		fillInTemplate: function(args, frag) {
			this._doClearMsgButton = dojo.widget.createWidget("Button", {}, this.clearMsgButtonNode);
			dojo.event.connect(this._doClearMsgButton, "onClick", this, "clearMessages");
		},
		
		handleExternalEvents: function (args) {
			if (args.event == "addMessage")
				this.addMessage(args.message);
			else if (args.event == "clearMessages")
				this.clearMessages();
		},
		
		addMessage: function(txtMessage) {		
			// add the new content
			var row = this.msgMessageTable.insertRow(1);
			cell = row.insertCell(0);
			cell.width = 100;
			this.appendTextNode(cell, this.createShortTimeStampStr());
			
			cell = row.insertCell(1);
			this.appendTextNode(cell, txtMessage);

			// handle the table style and history
			if (this.msgMessageTable.rows.length == 2) {
				row.className = "row-even";
			} else {
				var prev = this.msgMessageTable.rows[2];
				if (prev.className == "row-even")
					row.className = "row-odd";
				else
					row.className = "row-even";
			}

			if (this.msgMessageTable.rows.length > this.historySize + 1) {
				this.msgMessageTable.deleteRow(this.msgMessageTable.rows.length - 1);
			}			
		},

		clearMessages: function() {
			// this method is used to cleanup the messages pane when a user logs out
			numMsg = this.msgMessageTable.rows.length - 1;
			for (idx=0; idx < numMsg; idx++) {
				this.msgMessageTable.deleteRow(1);
			}
		}
	}
);
