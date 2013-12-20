package grails.cadec
import org.grails.databinding.BindUsing

class TweetCommand {
    String status

    //When we are using the BindUsing we can do stuff like pre-fetching other domains
    //Of course we don't really need a Command object for this trivial case...but
    //it is nice to show it anyway
    @BindUsing({ obj, src ->
        Account.get(src['userId']) })
    Account userId
}