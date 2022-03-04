package sg.edu.nus.iss.app.vttpworkshop14.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.vttpworkshop14.model.Contact;

@Service // all business logic runs here (store data)
public class ContactsRepo implements RedisRepo {
    private Logger logger = Logger.getLogger(ContactsRepo.class.getName());
    private static final String CONTACT_ENTITY = "contactList";
    @Autowired
    RedisTemplate<String, Object> redisTemplate; // RedisTemplate allows setup and config of serialiser

    @Override
    public void save(final Contact ctc) {
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, ctc.getId());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", ctc.getId(), ctc);
        logger.log(Level.INFO, "Contact saved succesfully");
    }

    @Override
    /*
     * for code readability, don't need this actually. But REQUIRED when you
     * implement an interface cos you must override all methods in user-defined
     * interfaces
     */
    public Contact findById(final String contactId) {
        Contact result = (Contact) redisTemplate.opsForHash().get(CONTACT_ENTITY + "_Map", contactId);
        return result;
    }

    @Override
    public List<Contact> findAll(int startIndex) {
        List<Object> fromContactList = redisTemplate.opsForList()
                .range(CONTACT_ENTITY, startIndex, startIndex + 9); // paginate 10 records per page
        List<Contact> ctcs = (List<Contact>) redisTemplate.opsForHash()
                .multiGet(CONTACT_ENTITY + "_Map", fromContactList)
                .stream()
                .filter(Contact.class::isInstance)
                .map(Contact.class::cast)
                .toList();
        return ctcs;
    }
}
