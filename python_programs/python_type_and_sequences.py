def add_numbers(a,b,c=None):
    if(c!=None):
        print(str(a+b+c))
    else:
        print(str(a+b))

#Print the types of different data or functions
print(type("This is a string"))
print(type(1.0))
print(type(1))
print(add_numbers)

#Initializing tuples and lists.
x = (1,"a",2,"b")
y = [1,'a',2,'b']
#Lists can be created in this way : [expression of each entry in the array 
# with a parameter v for v in ...]
squares = [values**2 for values in range(1,11)]
print(squares)
#Lists operations
y.append(3.3)
y.append(4.4)
del y[-1]
print(y)
y.append(4.4)
print(y.pop(-1))
print(y)
y.append(4.4)
y.remove(4.4)
print(y)
y.insert(0,1.1)
print(y)
for item in y: 
    print(item)
print(y[1])
print(y[-1])
z = [1,2] + [3,4]
print(z)
a = len(z)
print(a)
b = z*3
print(b)
print(3.3 in y)
print(3.3 in z)
print(3.3 not in y)
print(3.3 not in z)

#String manipulation
s = "This is a string"
print(s[1:3])
print(s[-4:-2])
print(s[-4:])
print(s[:4])

firstname = 'Christopher'
lastname = 'Brooks'
print(firstname + ' ' + lastname)
print(firstname*3)
print('Chris' in firstname)
fnamespl = 'Christopher Arthur Hansen Brooks'.split(' ')
lnamespl = 'Christopher Arthur Hansen Brooks'.split(' ')
print(fnamespl)
print(lnamespl)

#Dictionary manipulations
j={'Christopher Brooks':'brooksch@umich.edu', 'Bill Gates':'billg@microsoft.com'}
for name in j:
    print(j[name])
for name in j.keys():
    print(name)
for email in j.values(): 
    print(email)
for name, email in j.items():
    print(name)
    print(email)
name1, name2 = j
print(name1)
print(name2)
email1, email2 = j.values()
print(email1)
print(email2)
