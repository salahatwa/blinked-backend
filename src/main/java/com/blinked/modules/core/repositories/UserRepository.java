//package com.blinked.modules.core.repositories;
//
//import java.util.Optional;
//
//import org.springframework.lang.NonNull;
//
//import com.blinked.modules.core.repositories.base.BaseRepository;
//import com.blinked.modules.user.entities.User;
//
///**
// * User repository.
// *
// * @author ssatwa
// */
//public interface UserRepository extends BaseRepository<User, Integer> {
//
//	/**
//	 * Gets user by username.
//	 *
//	 * @param username username must not be blank
//	 * @return an optional user
//	 */
//	@NonNull
//	Optional<User> findByUsername(@NonNull String username);
//
//	/**
//	 * Gets user by email.
//	 *
//	 * @param email email must not be blank
//	 * @return an optional user
//	 */
//	@NonNull
//	Optional<User> findByEmail(@NonNull String email);
//}
