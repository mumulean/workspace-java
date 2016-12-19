This is a collection of projects made in Eclipse IDE for java programmers.

Currently, there are the following projects in this git repository:

CountdownApp - One of my go-to's for practicing programming, this app counts down 
to a specific date (at midnight) and then counts up from that date. The user can 
further "customize" their experience by choosing one of several formats that their 
counter can be displayed in, including one where they can see the total milliseconds 
to go/that have passed. (Version 1.0)

YGOCalc - My other go-to for practicing programming, this app keeps track of up to 8 
players in Yu-Gi-Oh!, assuming you can ever get 8 people to play it at the same time and 
all against one another. This version makes use of JDialogs to get user input. Previous
versions merely did all the computations and backend work in the same JFrame. The version 
directly preceding this one used a JTabbedPane. (Version 1.0)

TextLineGame - A spin on my C++ version, where I attempt to store the game data (enemy data, 
room data, item data, and so on) into an SQLite database that is then queried during game play
to get the given data. A new way to abstract it. Currently a work in progress. Trying to figure 
out the best way to approach certain problems. For instace, for items lying around in said room, you
should only be able to "take" the items once, not multiple times. Storing that value in the database would
create a situation where the db may not be able to revert to previous values; not doing so would be its own
problem all the same way. While I do plan to store item information in the database regardless, I need to figure out
the best way to move forward from the current version. (Version 0.01)