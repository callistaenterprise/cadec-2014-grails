package grails.cadec
import groovy.transform.CompileStatic
import reactor.event.Event
import reactor.spring.annotation.ReplyTo
import reactor.spring.annotation.Selector
import reactor.spring.annotation.SelectorType
import static grails.async.Promises.*
import grails.async.Promise
import grails.transaction.*

@CompileStatic
class TweetConsumerService {
   
   @Selector('saveTweet')
   @ReplyTo
   Tweet save(Event<Tweet> event) {
        Tweet tweet = event.data
        Promise promise = Tweet.async.task {
            Tweet.withTransaction { status ->
                tweet.save(flush: true)
            }
        }.onComplete {
            log.info "Completed ok"
        }.onError {Throwable t ->
            log.error "Error: ${t.getMessage()}"
        }
        return (Tweet) promise.get() 
   }
}
