package design_pattern.proxy;

public class UserServiceImpl implements UserService {
	
	@Override
	public void update() {
		System.out.println("user info updated");
	}
}