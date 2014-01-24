package grails.cadec

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.*
import grails.async.Promise
import static grails.async.Promises.*

@Transactional(readOnly = true)
class TweetController {

	static responseFormats = ['json', 'xml']

    def show(Tweet tweet) {
    	respond tweet	
    }

    @Transactional
    def save(TweetCommand cmd) {

        def tweet = new Tweet(status: cmd.status, user: cmd.userId)
        if(tweet.hasErrors()) {
            respond tweet.errors
        } else {
        	task {
            	event('saveTweet', tweet) {
            		log.info 'Reply was: $it'
            	}
            	render status: ACCEPTED
        	}
        }

    }

    def delete(Tweet tweet) {
    	tweet.delete flush:true
		withFormat {
    		'*'{ render status: NO_CONTENT } 
		}
    }
}
