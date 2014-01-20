import grails.cadec.*
import grails.converters.*

class BootStrap {

    def init = { servletContext ->
      //initiate the application with at least one Account
    	new Account(firstName: 'a', lastName: 'tweeter').save flush: true, failOnError: true
 
    }
    def destroy = {
    }
}