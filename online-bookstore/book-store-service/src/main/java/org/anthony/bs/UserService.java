package org.anthony.bs;

import org.anthony.bs.domain.BsUser;
import org.anthony.bs.exception.BsException;

/**
 * @author Anthony
 */
public interface UserService {

    /**
     * save user info(add new user if bsUser.id is null)
     *
     * @param bsUser
     * @return id of user
     */
    Long save(BsUser bsUser) throws BsException;

    /**
     * delete user by id
     *
     * @param uid
     * @return id of deleted user
     */
    Long delete(Long uid) throws BsException;

    /**
     * find user by id.
     * <p>
     * throw exception if user is not exist or pwd is wrong
     *
     * @param uid
     * @return user detail
     */
    BsUser findUser(Long uid) throws BsException;

    /**
     * throw exception if user is not exist or pwd is wrong
     *
     * @param name
     * @param pwd
     * @throws BsException
     */
    Long login(String name, String pwd) throws BsException;

}
