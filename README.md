Main controller:

UserController is the main controller operating all the handler functions, as follows:

The home page handler- "/" will first check if the user is logged in.

If not-

It will display a login page where the user will enter his name.

After the user enters his name he will be transferred to the chat room.

If the user is logged in-

then the home page will automatically redirect to the chat room.

The chat room displays as follows:

Greeting to user.
List of the connected users.
List of the last 5 messages.
Send button.
Search button.
Log out button.

-------------------------------------------------------------------------------------------
Connected users:

The user is considered "active" based on monitoring his fetch requests for the logged in users.

For a gap greater then 10 seconds between request, a user will be deemed as non active and removed

from the list (even though his session might still be active).

The "UserList" component is used to evaluate this, 

as it holds all the users in a hash map with a timestamp of there last request.

If the session is destroyed or deleted then upon submitting the next message the user will receive an

error notification and a link to login again.

In order to track down the different users, user info will be held in the component "DataSessionScope" which is scoped to the session scope.

The info is stored as follows:

User name.
Boolean for- is user logged in.
NewMessageId- to track new messages.
profileIndex- to apply a profile picture.

-------------------------------------------------------------------------------------------
Get messages:

the response to the request for the list of messages will return the list of the last 5 messages only if a new message was added

otherwise returns an empty list and no change is made on the client side.

The NewMessageId will determine whether or not there was a new message added.

It is evaluated as follows:

In the main controller an Atomic value is stored and is incremented for every new message. then when a

user requests the list of messages

his NewMessageId will be compared to the current message count, if they differ then the newest 5 

messages will be returned, otherwise the old list is kept.


-------------------------------------------------------------------------------------------
Session:

When a sessions time has expired or if the session had been terminated it is caught upon

the next time the user will try sending another message.

-------------------------------------------------------------------------------------------
Synchronized:

The joined resource which might be accessed simultaneously by multiple users is the "user list" component.

That is why all the components seter and geter functions are synchronized.

Also the "new message" indicator is incremented for each new message and

since this can happen by any which user, an AtomicLong is used for this indicator.

-------------------------------------------------------------------------------------------
Unauthorised get requests:

If the url for the search page is entered and the user never logged in 

a proper message is displayed.

The same is done for the chat room url,

unless the request comes from a logged in user then the page is provided.

-------------------------------------------------------------------------------------------
Preventing duplicate names:

When a new user tries to register before he is added to the user list

the list is scanned, if the list contains the users name

he will be notified and redirected to the login page.

-------------------------------------------------------------------------------------------
