import org.grails.plugins.events.reactor.api.EventsApi
import org.grails.plugins.events.reactor.promise.ReactorPromise
import reactor.core.configuration.DispatcherType
import reactor.event.dispatch.SynchronousDispatcher

includes = ['default']
doWithReactor = {
        environment {
            reactor('someOtherReactor') {
            	dispatcher = new SynchronousDispatcher()
            	ext 'gorm', true
            }
        }     
}