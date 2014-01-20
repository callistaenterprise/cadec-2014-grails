package grails.cadec

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.*

@Transactional(readOnly = true)
class RetweetController {

	static responseFormats = ['json', 'xml']

	@Transactional
    def save(Long tweetId) {
    	def retweet, tweet = Tweet.get(tweetId)
    	request.withFormat {
    		json {
    			retweet = new Retweet(retweetOf: tweet, retweeter: Account.get(request.JSON.retweeterId))
    		}
    	}
    	if(retweet.hasErrors()) {
    		respond retweet.errors
    	} else {
    		retweet.save flush: true, failOnError: true

    		tweet.retweetCount ++
    		if(!tweet.retweeted) tweet.retweeted = true
    		withFormat {
    			'*' {render status: CREATED}
    		}
    	}
    }
}
