package sg.edu.nus.iss.app.vttpworkshop14.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.vttpworkshop14.model.Contact;

@Repository // required to connect to databases
public interface RedisRepo {
    public void save(final Contact ctc);

    public Contact findById(final String contactId);

    public List<Contact> findAll(int startIndex);
}
