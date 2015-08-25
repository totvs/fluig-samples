function beforeSendNotification(notification) {
	if (notification.metadata && notification.metadata.get("priority")) {
		notification.priority = notification.metadata.get("priority");
	}
}