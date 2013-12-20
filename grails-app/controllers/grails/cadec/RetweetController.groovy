package grails.cadec

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.*

@Transactional(readOnly = true)
class RetweetController {

	static responseFormats = ['json', 'xml']

    //The tweetId comes from the url and Grails helps us with this /tweets/:tweetId/retweet
    //We can call an action with primitives from request parameters
	@Transactional
    def save(Long tweetId) {
    	def retweet, tweet = Tweet.get(tweetId)

    	request.withFormat {
    		json {
    			//Without this it doesn't work, we don't have anything in params so we need to snatch it straight from the request
    			//Could this have been done using a CommandObject?
    			retweet = new Retweet(retweetOf: tweet, retweeter: Account.get(request.JSON.retweeterId))
    		}
    	}
    	if(retweet.hasErrors()) {
    		respond retweet.errors
    	} else {
    		retweet.save flush: true, failOnError: true

    		//This isn't pretty, but hey its just a demo
    		tweet.retweetCount ++
    		if(!tweet.retweeted) tweet.retweeted = true
    		withFormat {
    			'*' {render status: CREATED}
    		}
    	}
    }
}
