package org.anthony.bs.impl;


import org.anthony.bs.UserService;
import org.anthony.bs.domain.BsUser;
import org.anthony.bs.enums.BooleanEnum;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.repository.UserRepo;
import org.anthony.bs.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepo userRepo;

    @Resource
    private RedisUtil redisUtil;

    private final static String USER_LOCK_KEY_PREFIX = "USER_LOCK_";

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Long save(BsUser bsUser) throws BsException {
        // check name
        checkUserName(bsUser.getName());
        //
        String lockKey = USER_LOCK_KEY_PREFIX + bsUser.getName();
        boolean lock = redisUtil.tryLock(lockKey);
        if (!lock) {
            throw BsException.of("01-006");
        }
        try {
            if (bsUser.getId() == null) {
                // new user check
                if (userRepo.findByName(bsUser.getName()) != null) {
                    throw BsException.of("01-003");
                }
                if (!StringUtils.hasText(bsUser.getPwd())) {
                    throw BsException.of("01-008");
                }
            } else {
                // old info check
                BsUser old = userRepo.findByName(bsUser.getName());
                if (old == null) {
                    throw BsException.of("01-004");
                }
                if (!StringUtils.hasText(bsUser.getPwd())) {
                    bsUser.setPwd(old.getPwd());
                }
            }
            bsUser.setIsActive(BooleanEnum.TRUE.getCode());
            // insert to DB
            bsUser = userRepo.save(bsUser);
            return bsUser.getId();
        } finally {
            redisUtil.releaseLock(lockKey);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Long delete(Long uid) throws BsException {
        BsUser bsUser = findUser(uid);
        bsUser.setIsActive(BooleanEnum.FALSE.getCode());
        userRepo.save(bsUser);
        return uid;
    }

    @Override
    public BsUser findUser(Long uid) throws BsException {
        Optional<BsUser> bsUserOptional = userRepo.findById(uid);
        if (!bsUserOptional.isPresent()) {
            throw BsException.of("01-005");
        }
        BsUser bsUser = bsUserOptional.get();
        bsUser.setPwd(null);
        return bsUser;
    }

    @Override
    public Long login(String name, String pwd) throws BsException {
        BsUser bsUser = userRepo.findByName(name);
        if (bsUser == null) {
            throw BsException.of("01-007");
        }
        if (!bsUser.getPwd().equals(pwd)) {
            throw BsException.of("01-008");
        }
        return bsUser.getId();
    }

    private void checkUserName(String name) throws BsException {
        if (!StringUtils.hasText(name)) {
            throw BsException.of("01-001");
        }
        for (char ch : name.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                throw BsException.of("01-002");
            }
        }
    }
}
