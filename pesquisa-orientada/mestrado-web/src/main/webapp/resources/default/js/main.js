function handleLaddaLoadingIndicator(data, idButton) {
	var status = data.status;

	var button = Ladda.create(document.querySelector(idButton));

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