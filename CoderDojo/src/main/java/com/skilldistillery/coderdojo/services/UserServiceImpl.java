package com.skilldistillery.coderdojo.services;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	// TODO: Investigate the guide's implementation of "UserRepositoryImpl implements UserRepository".  
	//       This requires implementing all of the JpaRepository methods unless the impl is abstract...??
	//   	 But this service/serviceimpl variation doesn't use the repo at all
	
	@Autowired
	private UserRepository repo;
	
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public User getUserByUsername(String username) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("FROM User u where u.username=:username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }	

}
