import grails.cadec.Tweet
import grails.rest.render.json.*

beans = {
	tweetRenderer(JsonRenderer, Tweet) {
		excludes = ['retweetCount']
	}	
}
