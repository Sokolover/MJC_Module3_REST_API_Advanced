package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User userToSave = userConverter.convert(userDTO);
        User saved = userRepository.save(userToSave);
        return userConverter.convert(saved);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> userConverter.convert(user))
                .collect(Collectors.toList());
    }
}
