import org.grails.plugins.events.reactor.api.EventsApi
import org.grails.plugins.events.reactor.promise.ReactorPromise
import reactor.core.configuration.DispatcherType

doWithReactor = {
        environment {
                defaultDispatcher = "grailsPromiseDispatcher"

                
                /*
                dispatcher('grailsDispatcher') {
                        type = DispatcherType.RING_BUFFER
                        backlog = 512
                }*/

                if (grailsApplication.metadata['app.grails.version'].startsWith('2.3')) {
                        
                        dispatcher(ReactorPromise.PROMISE_DISPATCHER) {
                                type = DispatcherType.EVENT_LOOP
                                backlog = 512
                        }

                }
        }

        /*
        reactor(EventsApi.GRAILS_REACTOR) {
        }
        */
}