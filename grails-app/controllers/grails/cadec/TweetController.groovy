package grails.cadec

import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.*
import static grails.async.Promises.*

@Transactional(readOnly = true)
class TweetController {

	static responseFormats = ['json', 'xml']

	def show() {
		def promise = Tweet.async.get( params.id )
		respond promise.get()
	}

	@Transactional
	def save(TweetCommand cmd) {
		def tweet = new Tweet(status: cmd.status, user: cmd.userId)

		if(tweet.hasErrors()) {
			respond tweet.errors
		} else {
			task {
				Tweet.async.task {
					withTransaction { status ->
						tweet.save flush: true
					}
				}
				withFormat {
					'*' {render status: CREATED}
				}
			}
		}
	}

	@Transactional
	def delete(Tweet tweet) {
		tweet.delete(flush: true)
		withFormat {
			'*' {render status: NO_CONTENT}
		}
	}
}
