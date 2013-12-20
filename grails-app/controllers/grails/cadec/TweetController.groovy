package grails.cadec

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.*
import static grails.async.Promises.*

@Transactional(readOnly = true)
class TweetController {

	static responseFormats = ['json', 'xml']

    def show(Tweet tweet) {
    	respond tweet
    }

    //Here we use Replyto for the event to gather info from the consumer
    @Transactional
    def save(TweetCommand tweetCommand) {

        //This part is not needed in the async task. Better to return fast if there is any problems.
        def tweet = new Tweet(status: tweetCommand.status, user: tweetCommand.userId)
        if(!tweet.validate()) {
            //If there are some errors we might just return right away anyway.
            log.error "It has errors"
            respond tweet.errors
        } 

        //Servlet 3.0 async task
        task {
            //There were no errors so lets run our long running task
            event('saveTweet', tweet) {
                log.info "Notifyin consumers of event with data: $tweet"
            }
            render status: ACCEPTED
        }
    }

    def delete(Tweet tweet) {
    	tweet.delete flush:true
		withFormat {
    		'*'{ render status: NO_CONTENT } 
		}
    }
}
