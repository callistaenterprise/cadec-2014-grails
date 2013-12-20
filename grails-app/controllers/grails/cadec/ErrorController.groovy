package grails.cadec
import static org.springframework.http.HttpStatus.*

class ErrorController {

    def handleError() {
    	def exception = request.exception

    	//We can take a peek at the error in the log if we want to
    	log.debug exception

    	//pass something more delicate to the client
    	render status: INTERNAL_SERVER_ERROR
    }
}
