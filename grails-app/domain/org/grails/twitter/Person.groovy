package org.grails.twitter

class Person {

	transient springSecurityService
	
	String realName
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	//static transients = ['springSecurityService']
	
	static hasMany = [followed:Person, status:Status]
	static searchable = [only:'realName']

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		id generator:'sequence', params:[sequence:'twitter_sequence']
		password column: '`password`'
	}

	Set<Authority> getAuthorities() {
		PersonAuthority.findAllByPerson(this).collect { it.authority }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
