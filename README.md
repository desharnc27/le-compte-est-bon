"Des chiffres et des lettres" (French: "numbers and letters") is a French television programme . One of the challenges of DCEDL is "Le compte est bon", a mathematical challenge in which you must find a way to combine 6 numbers with basic operations to reach a specific value.

See https://en.wikipedia.org/wiki/Des_chiffres_et_des_lettres for more detailed explanations.

This project is a simple solver that quickly finds a solution to any possible instance of "Le compte est bon".

How to run (for user): open terminal, cd your way to "<where you copied the folder>/target/classes" then enter this to execute:
```
java central.LeCompteEstBon [arguments]
```
	
Where the arguments are the numbers to combine (terms) and the number to reach (target value). You can type "help" as argument or open by yourself the help.txt file for more details on the argument format and the output.

Many examples of input:
```
java central.LeCompteEstBon help
java central.LeCompteEstBon 55,4,8,2,23,9
java central.LeCompteEstBon 97,3,5,3,0,11 44
```