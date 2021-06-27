package com.example.ex4;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * this component is used to track down the active users
 * and store them in a list.
 */
@Component
public class UserList {

   private ArrayList<String> userList= new ArrayList<>();

   private HashMap<String, Long> LastRequest = new HashMap<>();// hold user name and time of last request
//---------------------------------------------------------
    /**
     * when a user logs out he will be removed
     * @param name the key for map value
     */
    public synchronized void updateUserList(String name){

        LastRequest.remove(name);

    }
//---------------------------------------------------------
    /**
     * this function checks if the new user name already exist
     * if it dose return false.
     * else- will add the user to active user list along with request time stamp.
     * @param name users name.
     * @return T/F for existing user.
     */
    public synchronized boolean setTime(String name) {

        if(LastRequest.containsKey(name))
            return false;
        this.LastRequest.put(name, System.nanoTime());
        return true;
    }
//---------------------------------------------------------

    /**
     * this function sets the users time stamp
     * to indicate if he is still connected,
     * according to his latest request from the server.
     * @param name users name.
     */
    public synchronized void setLastRequest(String name) {

        this.LastRequest.put(name, System.nanoTime());

    }
//------------------------------------------------------------------------------------

    /**
     * this function will calculate if the user is still connected
     * by checking the elapsed time from his last request if it is greater then 10 seconds
     * he will be removed from the list.
     * @return the list of active users.
     */
    public synchronized ArrayList<String> getActiveUserList() {

        userList.clear();
        if(!LastRequest.isEmpty()) {
            for (Map.Entry<String, Long> entry : LastRequest.entrySet()) {

                Long start = (Long) entry.getValue();//last request time
                Long end = System.nanoTime();//current time

                Long elapsedTime = end - start;
                Long convert = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);

                if (convert <= 10 && (!entry.getKey().isEmpty()))
                    userList.add(entry.getKey());

            }
            return userList;
        }
        return new ArrayList<>();
    }

//------------------------------------------
public synchronized void deleteUser(String name) {

    LastRequest.remove(name);
}

}

