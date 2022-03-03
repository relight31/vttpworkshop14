package sg.edu.nus.iss.app.vttpworkshop14.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.vttpworkshop14.model.Contact;
import sg.edu.nus.iss.app.vttpworkshop14.service.ContactsRepo;

@Controller
public class AddressBookController {
    private Logger logger = Logger.getLogger(AddressBookController.class.getName());

    @Autowired
    ContactsRepo service;

    @GetMapping("/")
    public String contactForm(Model model){
        logger.log(Level.INFO, "Show the contact form");
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @GetMapping("/getContact/{contactId}")
    public String getContact(Model model, @PathVariable(value = "contactId") String contactId){
        logger.log(Level.INFO, "contactId "+contactId);
        Contact ctc = service.findById(contactId);
        model.addAttribute("contact", ctc);
        return "showContact";
    }

    @GetMapping("/contact")
    public String getAllContact(Model model, @RequestParam(name = "startIndex") String startIndex){
        List<Contact> resultFromService =  service.findAll(Integer.parseInt(startIndex));
        return "listContact";
    }

    @PostMapping("/contact")//name for what the page defines
    public String contactSubmit(@ModelAttribute Contact contact, Model model, HttpServletResponse httpResponse){
        logger.log(Level.INFO, "ID " + contact.getId());
        logger.log(Level.INFO, "Name " + contact.getName());
        logger.log(Level.INFO, "Phone " + contact.getPhone());
        logger.log(Level.INFO, "Email " + contact.getEmail());
        service.save(contact);

        return "showContact";
    }
}
