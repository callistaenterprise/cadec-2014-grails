package grails.cadec
import org.grails.databinding.BindUsing

class TweetCommand {
    String status
    
    @BindUsing({obj, src ->
    	Account.get(src['userId'])})
   	Account userId
}