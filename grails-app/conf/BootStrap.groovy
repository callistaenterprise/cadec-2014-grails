import grails.cadec.*
import grails.converters.*

class BootStrap {

    def init = { servletContext ->
      //initiate the application with at least one Account
    	new Account(firstName: 'a', lastName: 'tweeter').save flush: true, failOnError: true
 
      //This is showing one way of customizing a Response a little bit. There are other
      //ways for more advanced stuff like customized renderers.
		  JSON.registerObjectMarshaller(Tweet) {
  		  def map= [:]
        map['class'] = 'Tweet'
  			map['status'] = it.status
        if(it.retweetCount||it.retweetCount > 0) map['retweetCount'] = it.retweetCount
  			map['retweeted'] = it.retweeted
  			map['userId'] = it.user.id

  			return map 
		  }
    }
    def destroy = {
    }
}