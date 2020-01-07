import numpy as np
import matplotlib.pyplot as plt
import re
l=[1,2,3,4,5]
np_arr=np.array(l)
type(np_arr)
np_arr2=np.mat('1 2; 3 4; 5 6')
np_arr3=np.array([1,2,3,4])

'''Type Precedence: Bool, Int, Float, String
Available data types:   Descriptions:

bool_                    Boolean(True or False) stored as a byte
int_                     Default integer type(same as C "long": normally either int32 or int 64)
intc                     Identical to C "int"(normally int32 or int64)
intp                     Integer used for indexing(Same as C "ssize_t", normalling int32 or int64)
int8                     Byte(-128 to 127)
int16                    Integer(-32768 to 32767)
int32                    Integer(-2147483648 to 2147483647)
int64                    Integer(-9223372036854775808 to 9223372036854775807)
uint8                    Unsigned integer(0 to 255)
uint16                   Unsigned integer(0 to 65535)
uint32                   Unsigned integer(0 to 4294967295)
uint64                   Unsigned integer(0 to 18446744073709551615)
float_                   Shorthand for float64 .
float16                  Half precision float: sign bit. 5 bits exponent, 10 bits mantissa
float32                  Single precision float: sign bit, 8 bits exponent, 23 bits mantissa
float64                  Double precision float: sign bit, 11 bits exponent, 52 bits mantissa
complex_                 Shorthand for complex128
complex64                Complex number. Represented by 32-bit floats(real and imaginary parts)
complex128               Complex number. Represented by 64-bit floats(real and imaginary parts)
'''

np_arr4=np.array([1,2,3.0])
print(np.array([1,2,3],dtype=float))
print(np.array([1,2,3],dtype='U'))
print(np.array([12354,2,3],dtype='<U2'))
print(np.array([1,2,3],dtype=complex))

x = np.array([('ram',26,10000.0),('shyam',31,50000)],dtype=[('name','<U11'),('age','int32'),('salary','<f4')])
print('Row 1', x[0])
print('Row 2', x[1])

x = np.array([[[('ram',26,10000.0),('shyam',31,50000)]],[[('ratheesh',26,10000.0),('ratun',31,50000)]]],
             dtype=[('name','<U11'),('age','int32'),('salary','<f4')])

two_arr = np.array([(1,2,3),(4,5,6),(7,8,9)])
print(two_arr)
print("Shape of 2D array is", two_arr.shape)
print("Number of dimensions for 2D array is", two_arr.ndim)
print("Data Type for 2D array is", two_arr.dtype)
print("Size of 2D array is", two_arr.size)

'''Important functions:'''
np_arr5=np.zeros((3,4))
np_arr6=np.ones((2,3,4),dtype=np.int16)
np.empty((2,3)) #Uninitialized, output may vary
i=np.eye(6)
identity=np.identity(6)

arr=np.array([[1,2],[3,4],[4,5]])
print("Diagonals of arr is",np.diag(arr))

print(np.arange(10,30,5)) # The same as range(x,y,z) from x(inclusive) to y(exclusive) 
# by step of z but in the form of an array(1-dimensional matrix)
print(np.arange(1,100,2.5))
print(np.linspace(0,100,10)) #Return an array of 10 evenly spaced numbers over the interval 0-100
#(both inclusive))

#Plotting a sin function using the numpy.linspace()
print(np.linspace(0,2,9))
x = np.linspace(0,2*np.pi,100)
f = np.sin(x)
print(f)
plt.plot(f)
plt.show()

arr=np.array([[1,2,3],[4,5,6],[7,8,9]])
print(np.tril(arr))
print(np.triu(arr))
print(np.tril(arr,-1))
print(np.triu(arr,1))

a=np.arange(9)
print(a)
print(np.arange(12).reshape(2,2,3))
print(np.arange(10000))
np.set_printoptions(threshold=10000) #Change the number of items printed
print(np.arange(10000))

print("Random number generation: Uniform Distribution")
print(np.random.rand(2,3))
print("Numbers from normal distribution with zero mean and standard deviation 1 i.e, standard normal")
print(np.random.randn(4,3))
n = np.random.randn(4,3)
print(n)
print(n.std()) #Standard deviation


plt.plot(np.random.randn(100))
plt.show()
plt.hist(np.random.randn(100))
plt.show()
plt.hist(np.random.rand(100))
plt.show()

r=np.random.randint(1,100)
print(r)
r=np.random.randint(1,100,10) '''randint(low, high, number of samples to be drawn)'''
print(r)
r=np.random.randint(1,100,(3,3)) '''randint(low, high, shape of the array)'''
print(r)

# Element-wsie arithmetic operations
a=np.array([20,30,40,50])
b=np.arange(1,5)
print(a)
print(b)
add=a+b
sub=a-b
div=a/b
mul=a*b
print('Addition',add)
print('Subtraction',sub)
print('Division',div)
print('Multiplication',mul)

#Arithmetic operations with scalar values
print(b**2)
print(b*10)
print(a<35)

'''Matrix Operations'''
A=np.array([[1,1],[0,1]])
B=np.array([[2,0],[3,4]])
print(A+B)
print(A-B)
print(A*B)
print(A*10)
print(A/10)
print(A+10)
print(A-10)
print(A.dot(B))
print(np.dot(A,B))

C=np.array([[0,-1,0],[1,0,0],[0,1,1]])
D=np.array([[0,1,0],[-1,0,0],[1,0,1]])
print(C.dot(D))


help(str.split)
s = "a,b,c,d,e"
res = s.split(',')
print(res)

a = np.array([[1,2,3,4,5],[6,7,8,9,10]])
print(a[0:1]) #截取第一行,返回 [[1 2 3 4 5]]
print(a[1,2:5]) #截取第二行，第三、四、五列，返回 [8 9 10]
print(a[1,:]) #截取第二行,返回 [ 6  7  8  9 10]

a = np.array([[1,2,3,4,5],[6,7,8,9,10]])
b = a[a>6] # 截取矩阵a中大于6的元素，返回的是一维数组
print(b) # 返回 [ 7  8  9 10]
# 其实布尔语句首先生成一个布尔矩阵，将布尔矩阵传入[]（方括号）实现截取
print(a>6) 
# 返回[[False False False False False][False  True  True  True  True]]


a = np.array([[1,2,3,4,5],[6,7,8,9,10]])
print(a)
#开始矩阵为[[ 1  2  3  4  5][ 6  7  8  9 10]]
a[a>6] = 0
print(a)
#大于6清零后矩阵为[[1 2 3 4 5] [6 0 0 0 0]]

a = np.floor(10*np.random.random((2,2))) #np.floor(a) (a is an array) takes the maximum integer smaller
# or equal to each entry in the array.
print(a)
b = np.floor(10*np.random.random((2,2))) 
print(b)
print(np.vstack((a,b)))
print(np.hstack((a,b)))

'''Understanding of stack(a,b, axis=), adding a dimension according to the corresponding axis and determine the positions of entries in the returned array according to the original positions of entries in a and b: https://blog.csdn.net/wgx571859177/article/details/80987459
Understanding of vstack(a,b), if the two arrays are 1-dimensional, stack them up. If they are multi-dimensional, stack the arrays(rows) in the order of their first axis(axis=1) up in the order of first a, then b vertically: https://blog.csdn.net/csdn15698845876/article/details/73380803
Understanding of hstack(a.b), if the two arrays are 1-dimensional, connect them directly in a horizontal way and obtain a 1-dimensional array. If they are multi-dimensional, notice the numbers or arrays in the same columns(axis=0) of each array and put the columns in the order of first a, then b horizontally: https://blog.csdn.net/ygys1234/article/details/79872694
'''



a = np.arange(12).reshape(3,4)
print(a)
print("Sum of elements:", a.sum())
print("Min of elements:", a.min())
print("Max of elements:", a.max())
print("Mean of elements:", a.mean())
print("Standard deviation of elements:",a.std())
print(a.sum(axis=0))
print(a.sum(axis=1))
print(a.min(axis=1))
print(a.cumsum(axis=1))
print(np.exp(a))
print(np.sqrt(a))


a=np.random.rand(4,3)
print(a)
print(np.argmax(a)) #Returns the indices of the maximum values along an axis.
print(np.max(a))

a = np.linspace(1,10,15)
print(a)
print(a[2])
print(a[2:5])
print(a[1:6:2])
print(a[:6:2])
print(a[1::2])


A = np.arange(20).reshape(5,4)
print(A)
print(A[2:4,1:3])
print(A[2,3])
print(A[2,...])

B = np.arange(45).reshape(3,3,5)

for axis1 in B:
    for axis2 in axis1:
        for val in axis2:
            print(val)
            print(' ')

            
for axis1 in B:
    print(axis1)
    print('*'*10)
    
print(B.flat) #flatten the array into a one dimensional array rowwise.

for element in A.flat:
    print(element)

print(B.ravel()) #Similar to flatten

print(A.T) #Transpose of A

'''reshape() function returns a modified shape of the array, resize() modify the array itself
If one of the dimension is given as -1 in reshape(a,b,...), this dimension is automatically calculated
'''
print(a)
a.resize(3,5)
print(a)
print(a.reshape(5,-1))


