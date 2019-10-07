# Programs
There are instructions of the rule of games(missions) in the files except for the BP algorithm. 
It is hard to translate all of them into English so please ask someone who can read Chinese to read it.
For BP algorithm, I failed at first in finding an equation to determine the types of octane with the parameters given due to the inability to make the 
total error lower than the upper bound. Later, I tried to use the bp network to verify different types of iris flower. 
Since I used java, I had to write out the whole network.The nodes are all elements in arrays and there are generally two steps: 
Backward propagation and forward propagation. Forward propagation outputs the total error, which can be partially differentiated
with respect to weights between different layers and offsets of different layers so that the weights and offsets can be
adjusted to reach a local minimum of the total error function. 
