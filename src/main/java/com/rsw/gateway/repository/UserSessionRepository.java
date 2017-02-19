package com.rsw.gateway.repository;

import com.rsw.gateway.domain.UserSession;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by DAlms on 2/12/17.
 */
public interface UserSessionRepository extends CrudRepository<UserSession, String> {

}
