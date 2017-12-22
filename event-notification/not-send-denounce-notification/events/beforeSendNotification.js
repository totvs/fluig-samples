function beforeSendNotification(notification) {
	if (notification.eventType == "DENOUNCED_CONTENT") {
		notification.priority = "NONE";
	}
}