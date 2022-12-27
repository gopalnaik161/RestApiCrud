package Tesqurelexam.app.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Tesqurelexam.app.Entity.User;
import Tesqurelexam.app.Exception.ResourceNotFoundException;
import Tesqurelexam.app.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User addUser(User user) {
		user.setIsActive("A");
		
		 
		
		  // code for date pattren change 
		
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		   String format = sdf.format(new Date());
		   
		   try {
			user.setDate(sdf.parse(format));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		     
	
        return userRepo.save(user);
	}



	@Override
	public List<User> getAllUser(User user) {
		
		return userRepo.findByIsActive("A");
	}

	@Override
	public User updateUser(long userId, User user) {
//		User existingUser = userRepo.findById(userId).orElse(null);
		
		User existingUser = userRepo.findByUserIdAndIsActive(userId, "A").orElse(null);
		
		if(existingUser!=null) {
			existingUser.setUserId(user.getUserId());
			existingUser.setUserName(user.getUserName());
			existingUser.setAddress(user.getAddress());
			existingUser.setContactNumber(user.getContactNumber());
			existingUser.setAdminId(user.getAdminId());
			existingUser.setCreatedBy(existingUser.getCreatedBy());
			existingUser.setDate(existingUser.getDate());	
		}
		
		return userRepo.save(existingUser);
	}

	@Override
	public Map<String, Boolean> deleteUser(Long userId)throws ResourceNotFoundException {
		  User user2 = userRepo.findByUserIdAndIsActive(userId, "A").orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		if (user2!=null) {
//			User user = findById.get();
			user2.setIsActive("D");
//			userRepo.delete(user);
			userRepo.save(user2);
			
		} 
		
		Map<String, Boolean> response = new HashMap<>();
	       response.put("deleted", Boolean.TRUE);
	       return response;
	}



	@Override
	public ResponseEntity<User> getoneUser(long userId) throws ResourceNotFoundException {
	 User user = userRepo.findByUserIdAndIsActive(userId,"A")
	.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	


	

	

}
