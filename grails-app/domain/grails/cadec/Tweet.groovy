package grails.cadec

class Tweet {

	String status
	Long retweetCount = 0
	Boolean retweeted = false //Default value
	Account user
	
    static constraints = {
    	retweetCount nullable: true
    }
}
