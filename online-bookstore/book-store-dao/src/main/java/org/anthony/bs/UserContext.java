package org.anthony.bs;

import lombok.Data;
import org.anthony.bs.domain.BsUser;

@Data
public class UserContext {

    private static final ThreadLocal<UserContext> userContextLocalCache = new ThreadLocal<>();

    private UserContext() {
    }

    private BsUser bsUser;

    public static UserContext currentUser() {
        return userContextLocalCache.get();
    }

    public static void setCurrentUser(BsUser bsUser) {
        UserContext userContext = new UserContext();
        userContext.setBsUser(bsUser);
        userContextLocalCache.set(userContext);
    }

    public static void invalidate() {
        userContextLocalCache.remove();
    }

}
