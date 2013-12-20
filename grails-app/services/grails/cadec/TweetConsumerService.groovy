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

    @Selector ('saveTweet')
    @ReplyTo
    Tweet save(Event<Tweet> event){
       log.debug 'this save will act on event: ' + event
       Tweet tweet = event.data
        Promise promise = Tweet.async.task {
            Tweet.withTransaction {
                tweet.save flush: true
            }
        }.onComplete {
            //Here are stuff we can do when our long running GORM task has ended
            if(it) {
                log.info "Save of Tweet: $it went ok"  
            } else {
                log.info "Save was not ok"
            }
        }.onError {Throwable t->
            //Something really bad has happened"
            log.error "Error: ${t.getMessage()}"

            //We could of course throw some kind of event here as well
        }

        return (Tweet) promise.get()
    }
}
