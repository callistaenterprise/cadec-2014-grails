class UrlMappings {

	static mappings = {
        //Define our own ErrorController that catches all HTTP 500 errors
        "500"(controller: "error")

       	"/tweets"(resources: 'tweet', includes: ['show', 'save', 'delete']) {
       		"/retweet"(resource: 'retweet', includes: ['save'])
       	}

	}
}
