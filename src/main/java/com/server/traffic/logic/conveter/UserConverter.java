package com.server.traffic.logic.conveter;

import com.server.traffic.logic.dto.UserDTO;
import com.server.traffic.logic.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * UserConverter
 *
 * @author DatDV
 */
@Component
public class UserConverter extends MapperManager<UserDTO, UserEntity> {
}
