package com.example.ex4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * parameters:
 *newMessage- is used as an indicator for if a new message was added.
 *userCounter- is a user id to match him a profile picture.
 *userList- is used to store all the connected users.
 *repository- is for the CRUD functionality.
 *dataSessionScope- holds info to track the different users.
 */
@Controller
public class UserController {

    private AtomicLong newMessage = new AtomicLong(0);
    private int userCounter = 0;
    @Autowired
    private UserList userList;
    @Autowired
    private UserRepository repository;

    private UserRepository getRepo() {
        return repository;
    }

    @Autowired
    private DataSessionScope dataSessionScope;

    public DataSessionScope getSession() {
        return dataSessionScope;
    }

    /**
     * this url is the home page the handler will check
     * if the user had already logged in.
     * if not, will display the login page.
     * else will send the user directly to the chat room.
     *
     * @param message
     * @param model
     * @return the proper view.
     */
    @GetMapping("/")
    public String login(Message message, Model model) {


        userList.getActiveUserList();

        if (getSession().isLoggedIn()) {
            getSession().setNewMessageId(newMessage.get() - 1);

            model.addAttribute("ProfileIndex", getSession().getProfileIndex());

            model.addAttribute("name", getSession().getName());

            return "chatRoom";
        }

        return "login";
    }

    /**
     * this handler deals with pre chat room operations and initializations.
     * such as:
     * check if the user name already joined chat.
     * check if the user name is blank.
     * set the users new message counter.
     * initialize session attributes.
     * if the name already exist will notify the user.
     * else send him to chat room.
     *
     * @param message
     * @param result
     * @param name user name.
     * @param model
     * @return chat room.
     */
    @PostMapping("/saveMessage")
    public String showSignUpForm(@Valid @ModelAttribute Message message, BindingResult result, @RequestParam(value = "name") String name, Model model) {

        if (result.hasErrors()) {
            return "login";
        }
        //set last message id to know if a new message was added
        getSession().setNewMessageId(newMessage.get() - 1);

        if (!userList.setTime(name)) {//give the user first time stamp
            model.addAttribute("error", "the name u provided is already logged in pleas try another name ");
            return "error";
        }
        getSession().setLoggedIn(true);//turn on users logged in flag
        getSession().setName(name);//set session name

        getSession().setProfileIndex(userCounter++);
        model.addAttribute("ProfileIndex", getSession().getProfileIndex());

        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "chatRoom";

    }

    /**
     * this handler will return the search page.
     *
     * @return search options.
     */
    @GetMapping("/get")
    public String search() {
        return "search";
    }

    /**
     * this handler returns info for query
     * asking for all messages of a specific user.
     *
     * @param name for query.
     * @return the list of messages.
     */
    @GetMapping(value = "/findUserMessages")
    public @ResponseBody
    List<Message> userMessages(@RequestParam(value = "name") String name) {

        return getRepo().findByName(name);

    }

    /**
     * this handler returns info for query
     * asking for all messages of which contain the given content.
     *
     * @param chat for query.
     * @return the list of messages.
     */
    @GetMapping(value = "/findUserByContent")
    public @ResponseBody
    List<Message> Messages(@RequestParam(value = "chat") String chat) {

        return getRepo().findAllByChatContains(chat);

    }

    /**
     * this handler returns data for a fetch request
     * requesting the list of the last 5 messages.
     * it only preforms the query if a new message was added,
     * if no new messages were added it returns an empty list.
     *
     * @return the list of last 5 messages.
     */
    @RequestMapping(value = "/getMessages")
    public @ResponseBody
    List<Message> LastMessage() {

        if (getSession().getNewMessageId() == (Long) newMessage.get()) //i.e.- NO new message!
            return new ArrayList<>();

        getSession().setNewMessageId((Long) newMessage.get());//reset to new message
        return getRepo().findFirst5ByOrderByCreatedAtDesc();

    }

    /**
     * this handler returns data for a fetch request,
     * requesting the list of connected users.
     *
     * @param name to update the current users time stamp.
     * @return all connected users.
     */
    @RequestMapping(value = "/getConnectedUsers")
    public @ResponseBody
    List<String> getAll(@RequestParam(value = "name") String name) {

        //this will update the time of last fetch indicating that the user is still logged in
        userList.setLastRequest(name);

        List<String> users = userList.getActiveUserList();

        return users;
    }

    /**
     * this handler is called when the user loges out.
     * it removes the user from the active user list,
     * and sets the session flag "is logged in" to false.
     *
     * @param name users name to remove from list.
     * @return login page.
     */
    @RequestMapping("/logOut")
    public String LogOut(@RequestParam(value = "name") String name) {

        userList.updateUserList(name);//remove user from active user list

        getSession().setLoggedIn(false);//flag the session for leaving
        return "redirect:/";
    }

    /**
     * this handler is called when the user sends a new message.
     * first it checks if the session is alive,
     * if not, will display a message.
     * if the session is still active then will save the new message in sql DB.
     *
     * @param message the new message being stored.
     * @param model
     * @return go back to the chat room.
     */
    @PostMapping("/addMessage")
    public String addMessage(@ModelAttribute("message") Message message, Model model) {

        //for if session ended
        if (!getSession().isLoggedIn()) {
            userList.updateUserList(message.getName());//delete from user list
            userList.deleteUser(message.getName());//delete from map
            model.addAttribute("error", "your session expired! please press the link below to login again.");
            return "error";
        }

        //set the flag to know if a new message was added
        newMessage.set(newMessage.get() + 1);
        getRepo().save(message);

        model.addAttribute("name", message.getName());
        return "chatRoom";
    }
//==================================================================
    /**
     * the following boxed in handlers take care of unauthorised
     * url Get requests.
     *
     * @return message.
     */
    @GetMapping("/saveMessage")
    public String notAuthorised(Model model) {
        model.addAttribute("error", "Pleas login first.");
        return "error";
    }
    //------------------------------------------------------------------
    @GetMapping("/chatRoom")
    public String loginFirst(Model model) {

        if (!getSession().isLoggedIn()){
            model.addAttribute("error", "Pleas login first to join the chat.");
            return "error";   }

        return "redirect:/";
    }
    //------------------------------------------------------------------
    @GetMapping("/search")
    public String canSearch(Model model) {

        if (!getSession().isLoggedIn()){
            model.addAttribute("error", "Pleas login first in order to search.");
            return "error";   }

        return "search";
    }
    //------------------------------------------------------------------
    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }
//==================================================================
}


