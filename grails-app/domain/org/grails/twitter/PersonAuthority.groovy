package org.grails.twitter

import org.apache.commons.lang.builder.HashCodeBuilder

class PersonAuthority implements Serializable {

	private static final long serialVersionUID = 1

	Person person
	Authority authority

	boolean equals(other) {
		if (!(other instanceof PersonAuthority)) {
			return false
		}

		other.person?.id == person?.id &&
		other.authority?.id == authority?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (person) builder.append(person.id)
		if (authority) builder.append(authority.id)
		builder.toHashCode()
	}

	static PersonAuthority get(long personId, long authorityId) {
		PersonAuthority.where {
			person == Person.load(personId) &&
			authority == Authority.load(authorityId)
		}.get()
	}

	static boolean exists(long personId, long authorityId) {
		PersonAuthority.where {
			person == Person.load(personId) &&
			authority == Authority.load(authorityId)
		}.count() > 0
	}

	static PersonAuthority create(Person person, Authority authority, boolean flush = false) {
		def instance = new PersonAuthority(person: person, authority: authority)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Person u, Authority r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = PersonAuthority.where {
			person == Person.load(u.id) &&
			authority == Authority.load(r.id)
		}.deleteAll()

		if (flush) { PersonAuthority.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(Person u, boolean flush = false) {
		if (u == null) return

		PersonAuthority.where {
			person == Person.load(u.id)
		}.deleteAll()

		if (flush) { PersonAuthority.withSession { it.flush() } }
	}

	static void removeAll(Authority r, boolean flush = false) {
		if (r == null) return

		PersonAuthority.where {
			authority == Authority.load(r.id)
		}.deleteAll()

		if (flush) { PersonAuthority.withSession { it.flush() } }
	}

	static constraints = {
		authority validator: { Authority r, PersonAuthority ur ->
			if (ur.person == null) return
			boolean existing = false
			PersonAuthority.withNewSession {
				existing = PersonAuthority.exists(ur.person.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['authority', 'person']
		version false
	}
}
