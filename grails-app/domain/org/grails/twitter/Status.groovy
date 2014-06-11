package org.grails.twitter

class Status {

    static constraints = {
    message size:2..140, blank:false
}
	
	static mapping = {
		id generator:'sequence', params:[sequence:'status_sequence']
	}
	
	String message
	Person author
	Date dateCreated
}
