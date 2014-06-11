package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured

@Secured('IS_AUTHENTICATED_FULLY')

class StatusController {

   def springSecurityService

   def index() {
	   def msgs = currentUserTimeline()
	   return [messages: msgs] // this is a map. key=>value
   }

   def updateStatus = {
	   def status = new Status(message: params.message)
	   status.author = lookupPerson()
	   status.save(flush:true)  // setting flush:true ensures accuracy
	   def messages = currentUserTimeline()
	   
	   render template: 'messages', collection: messages, var: 'message'
   }

   private lookupPerson() {
	   Person.get(springSecurityService.principal.id)
   }

   private currentUserTimeline() {
	   def per = lookupPerson()
	   def query = Status.whereAny {
		   author { username == per.username }
	   }.order 'dateCreated', 'desc'
	   def messages = query.list(max: 20)

	   messages
   }

}