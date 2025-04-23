package com.zhigarevich.phonebook.service.impl;

import com.zhigarevich.phonebook.dao.PhoneBookDao;
import com.zhigarevich.phonebook.dao.impl.PhoneBookDaoImpl;
import com.zhigarevich.phonebook.exception.BusinessException;
import com.zhigarevich.phonebook.model.PhoneBookEntry;
import com.zhigarevich.phonebook.service.PhoneBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PhoneBookServiceImpl implements PhoneBookService {

    private final Logger log = LogManager.getLogger(PhoneBookServiceImpl.class);

    private final PhoneBookDao phoneBookDao = new PhoneBookDaoImpl();

    @Override
    public List<PhoneBookEntry> findAll() throws BusinessException {
        log.debug("Attempting find all phoneBook entries");

        List<PhoneBookEntry> phoneBookEntries = this.phoneBookDao.findAll();

        log.info("Found phoneBooks: {}", phoneBookEntries.size());
        return phoneBookEntries;
    }

    @Override
    public List<PhoneBookEntry> findAllByUser(int id) throws BusinessException {
        log.debug("Attempting find all phone book by user id: {}", id);

        List<PhoneBookEntry> entries = this.phoneBookDao.findAllByUser(id);

        log.info("Found books: {}", entries.size());
        return entries;
    }

    @Override
    public PhoneBookEntry findById(int id) throws BusinessException {
        log.debug("Attempting find phonebook by id: {}", id);
        PhoneBookEntry phoneBookEntry = this.phoneBookDao.findById(id).orElseThrow(
                () -> {
                    String message = "Phone book with id: '%d' was not found".formatted(id);

                    log.warn(message);
                    return new BusinessException(message);
                }
        );

        log.info("Phonebook found: {}", phoneBookEntry);
        return phoneBookEntry;
    }

    @Override
    public void save(PhoneBookEntry phoneBookEntry) throws BusinessException {
        log.debug("Attempting create new phonebook: {}", phoneBookEntry);

        this.phoneBookDao.save(phoneBookEntry);

        log.info("Phonebook created: {}", phoneBookEntry);
    }

    @Override
    public void delete(int id) throws BusinessException {
        log.debug("Attempting delete phonebook: {}", id);

        this.phoneBookDao.delete(id);

        log.info("Phonebook deleted: {}", id);
    }
}
