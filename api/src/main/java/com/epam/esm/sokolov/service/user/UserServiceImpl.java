package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_PAGE_SIZE_MESSAGE;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDTO> findAll(Long pageSize, Long pageNumber) {
        if (isIncorrectArguments(pageSize, pageNumber)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());//fixme handle status-codes in controller tier, not in service
        }
        Long pageOffsetInQuery = pageNumber * pageSize;
        List<User> users = userRepository.findAll(pageSize, pageOffsetInQuery);
        if (CollectionUtils.isEmpty(users)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());
        }
        return users.stream()
                .map(user -> userConverter.convert(user))
                .collect(Collectors.toList());
    }

    private boolean isIncorrectArguments(Long pageSize, Long pageNumber) {
        return pageSize == null || pageNumber == null || pageSize < 0 || pageNumber < 0;
    }

    @Override
    public Long findUserAmount() {
        return userRepository.findUserAmount();
    }
}
