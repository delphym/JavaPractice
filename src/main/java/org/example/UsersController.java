package org.example;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	/* This is an example of the solution the candidate might write.
	 * Uncommenting this section will pass the test cases.
	 */


	private final UserService service;

	public UsersController(UserService service) {
		this.service = service;
	}

	@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
	public @ResponseBody List<User> all() {
		return this.service.all();
	}

}

@Service
class UserService {
	public List<User> all() {
		List<User> users = new ArrayList<User>();
		users.add(new User("Bob"));
		users.add(new User("Christina"));
		users.add(new User("Steve"));
		return users;
	}
}

class User {
	private final String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
