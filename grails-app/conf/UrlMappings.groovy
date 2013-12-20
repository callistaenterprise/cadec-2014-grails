class UrlMappings {

	static mappings = {
        //Define our own ErrorController that catches all HTTP 500 errors
        "500"(controller: "error")

        "/tweets"(resources: 'tweet', excludes: ['index', 'update', 'create', 'edit']) {
        	//Nested resource
            "/retweet"(resource: 'retweet', includes: ['save'])
        }
	}
}
