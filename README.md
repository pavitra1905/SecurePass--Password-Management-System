# SecurePass

## An Offline and Encrypted Password Manager

In today's times where Cybersecurity is a major issue, password entropy is extremely
essential. Password entropy is a measure of the effectiveness of a password against guessing
or brute-force attacks. Creating a strong password and remembering it is a challenge.

**SecurePass** is an application that will help users generate a strong password as well as store
them to protect themselves online. The app is easy to use and explicitly coded so that the
users can look through the project to ensure that the storage of their sensitive data can be
trusted. No external API's are being used for this project, hence, it is a completely offline
password manager.

The target users for this app can be anybody who wants to generate their passwords efficiently
and keep a record of them for their personal use.

I like to implement technology to benefit humanity. My father used to use CASIO's Digital diaries
to store his confidential information and always wondered if it could be digitally encoded. I read
some research papers for the algorithms that can be used to encrypt data  and discovered the 
significant change that occurred in 1997 when the NIST attempted to switch to a more secure 
encryption algorithm and contacted partners. This is especially true now, when post-quantum 
cryptography is being used to recreate essentially the same scenario.

## User Stories

- As a user, I want to be able to create entries with a name, web address, password, notes and add
multiple entries to the file
- As a user, I want to be able to generate a random password
- As a user, I want to be able to generate a random passphrase
- As a user, I want to be able to enter a *custom* password
- As a user, I want to be able to return entropy score for the password in an entry
- As a user, I want to be able to view a list of all entries
- As a user, I want to be able to save file on exit
- As a user, I want to be able to load previously saved file on start

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by creating
a password entry in the Password Management System
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by using the
filter passwords by score option after creating an entry
- You can locate my visual component by running Main as it's a GIF which functions as my app's starter
logo
- You can save the state of my application by clicking on the Save button after creating an entry
- You can reload the state of my application by clicking on the Load button after creating an entry and saving it
to load it from the file

## Phase 4: Task 2

Representative sample of the events that occur when the program runs:

```dtd
Tue Nov 28 22:50:45 PST 2023
Loaded entries from workroom.json.
Tue Nov 28 22:52:04 PST 2023
Added entry #2 with name Google.
Tue Nov 28 22:52:09 PST 2023
Saved entries to workroom.json.`
```

It loads any existing entries stored in the file. The user then adds an entry
with their Google account details and then save the entry onto the file.

## Phase 4: Task 3

Reflecting on the existing design as presented in my UML diagram, I would like
to make certain changes. 

I had independent functions for saving and loading data from the console and the 
GUI while the code was predominantly the same. I could refactor the code to make 
them abstract save and load functions so that the console and GUI can make a call 
to them with differing boolean values. This would put the duplicate code together 
and call the relevant section of the code which actually differs between the 
console and the GUI.

I also used numerous `final static String`fields in the `PasswordSystem.java` and
`GUI.java`. For the former, I had to store some keywords that the 
user would use and so that they can be called easily. For the latter, I had 
to store the various action commands for the different buttons I had in my GUI.
Instead of doing so, I could have used two enumeration classes that store the
names of these variables for the `PasswordSystem` and `GUI` classes. 
I could then import the enum class into these respective classes and use
the enums in place of the `final static String` fields. I implemented this to an
extent having one enumeration class called `Input` for the `PasswordSytem` class.

Apart from these changes, I feel my project meets design requirements by reducing
coupling and increasing cohesion wherever possible.