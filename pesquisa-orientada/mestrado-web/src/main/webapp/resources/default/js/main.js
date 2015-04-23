function handleLaddaLoadingIndicator(data, idButton) {
	var status = data.status;

	var obj = document.querySelector(idButton);
	
	if (obj != null && obj != undefined) {
		var button = Ladda.create(obj);
	
		switch (status) {
		case "begin": // Before the ajax request is sent.
			button.start();
			break;
	
		case "complete": // After the ajax response is arrived.
			// ...
			break;
	
		case "success": // After update of HTML DOM based on ajax response..
			button.stop();
			break;
		}
	}
}