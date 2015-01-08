package ac.neec.mio.group;

import java.util.Arrays;

import ac.neec.mio.user.User;

public class Group_bac {

	private String id;
	private String name;
	private String comment;
	private User[] users;

	public Group_bac(String id, String name, String comment, User[] users) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.users = Arrays.copyOf(users, users.length);
	}
	
	public void addUsers(User user){
		users[users.length] = user;
	}
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getComment(){
		return comment;
	}
	
	public User[] getUsers(){
		return users;
	}
}
