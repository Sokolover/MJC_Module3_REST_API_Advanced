package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.user.Role;
import com.epam.esm.sokolov.model.user.User;
import com.epam.esm.sokolov.repository.RoleRepository;
import com.epam.esm.sokolov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_PAGE_SIZE_MESSAGE;
import static com.epam.esm.sokolov.model.user.RoleName.ROLE_USER;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userConverter.convert(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoles(user);//todo find how to save roles more effectively
        User savedUser = userRepository.save(user);
        return userConverter.convert(savedUser);
    }

    private void setRoles(User user) {
        Role roleUser = roleRepository.findByName(ROLE_USER).orElseThrow(
                () -> new ServiceException("No such role exception"));
        user.setRoles(Collections.singleton(roleUser));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).<ServiceException>orElseThrow(() -> {
            String message = String.format("Resource not found (user id = %s)", id);
            throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
        });
    }

    @Override
    public List<UserDTO> findAll(Long pageSize, Long pageNumber) {
        if (isIncorrectArguments(pageSize, pageNumber)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());
        }
        Long pageOffsetInQuery = pageNumber * pageSize;
        List<User> users = userRepository.findAll(pageSize, pageOffsetInQuery);
        if (CollectionUtils.isEmpty(users)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());
        }
        return users.stream()
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }

    private boolean isIncorrectArguments(Long pageSize, Long pageNumber) {
        return pageSize == null || pageNumber == null || pageSize < 0 || pageNumber < 0;
    }

    @Override
    public Long findUserAmount() {
        return userRepository.count();
    }
}
