package grails.cadec
import grails.rest.*

@Resource(uri="/tweets", formats=['json', 'xml'])
class Tweet {

	String status
	Long retweetCount = 0
	Boolean retweeted = false //Default value
	Account user
	
    static constraints = {
    	retweetCount nullable: true
    }
}
