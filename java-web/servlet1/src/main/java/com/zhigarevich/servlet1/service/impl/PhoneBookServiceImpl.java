package com.zhigarevich.servlet1.service.impl;

import com.zhigarevich.servlet1.exception.BusinessException;
import com.zhigarevich.servlet1.model.PhoneBook;
import com.zhigarevich.servlet1.repository.PhoneBookDao;
import com.zhigarevich.servlet1.repository.impl.PhoneBookDaoImpl;
import com.zhigarevich.servlet1.service.PhoneBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class PhoneBookServiceImpl implements PhoneBookService {

    private static final Logger log = LogManager.getLogger();
    public static final String PHONE_BOOK_WITH_ID_WAS_NOT_FOUND_TEMPLATE = "PhoneBook with id: '%d' was not found";

    private final PhoneBookDao phoneBookRepository = new PhoneBookDaoImpl();

    @Override
    public List<PhoneBook> findAll() {
        log.debug("Attempting find all phonebooks");

        var phoneBooks = this.phoneBookRepository.findAll();

        log.info("Found phoneBooks: {}", phoneBooks.size());
        return phoneBooks;
    }

    @Override
    public PhoneBook findById(Long id) throws BusinessException {
        log.debug("Attempting find phoneBook by id: {}", id);

        var phoneBook = this.phoneBookRepository.findById(id).orElseThrow(
                () -> {
                    var message = String.format(PHONE_BOOK_WITH_ID_WAS_NOT_FOUND_TEMPLATE, id);
                    log.error(message);
                    return new BusinessException(message);
                }
        );

        log.info("Found phoneBook: {}", phoneBook);
        return phoneBook;
    }

    @Override
    public void delete(Long id) {
        log.debug("Attempting delete phoneBook by id: {}", id);

        this.phoneBookRepository.delete(id);

        log.info("PhoneBook deleted");
    }

    @Override
    public List<PhoneBook> findAllByUserId(int userId) {
        log.debug("Attempting find all phoneBooks by userId: {}", userId);

        var phoneBooks = this.phoneBookRepository.findAllByUserId(userId);

        log.info("Found phoneBooks: {}", phoneBooks.size());
        return phoneBooks;
    }

    @Override
    public boolean update(PhoneBook entry) {
        log.debug("Attempting update phoneBook with id: {}", entry.getId());

        var isUpdated = this.phoneBookRepository.update(entry);

        if (isUpdated) {
            log.info("PhoneBook updated: {}", entry.getId());
        }
        else {
            log.error("PhoneBook not updated: {}", entry.getId());
        }
        return isUpdated;
    }
}
