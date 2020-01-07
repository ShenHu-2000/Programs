import numpy as np
# Create arrays of different dimensions with a's data type for each element
# specified.
a = np.array([1,2,3],dtype='int16')
print(a)
b = np.array([[9,0,8,0,7,0],[6,0,5,0,4,0]])
print(b)
c = np.array([[1,2,3,4,5,6,7],[8,9,10,11,12,13,14]])
print(c)
# Print out the arrays, 
# the size of the arrays(the number of elements in the array)
# the number of dimensions of arrays, 
# the exact number of elements in each dimension of arrays,
# and the number of bytes the data type of each element. 
# dtype represents the data type of each element, 
# total size = itemsize * size(or nbytes)
print(a.ndim)
print("\n")
print(b.ndim)
print("\n")
print(a.size)
print("\n")
print(b.size)
print("\n")
print(b.shape)
print("\n")
print(a.itemsize)
print("\n")
print(a.dtype)
print("\n")
print(b.itemsize)
print("\n")
print(b.dtype)
print("\n")
print(b.nbytes)
print("\n")
print(a.nbytes)
print("\n")

#Get a specific element in c with indices(negative indices imply the reverse
# order)
print(c[1,2])
print("\n")
print(c[1,-1])
print("\n")

#Get a row.
print(c[1])
print("\n")
print(c[1,:])
print("\n")

#Get a column.
print(c[:,2])
print("\n")

#Get a section of the matrix. [startindex: endindex: step size]
print(c[0,1:-1:2])
print("\n")
print(c[0,1:-1])
print("\n")

#Value assignment for elements in the array.
c[1,5] = 20
print(c)
print("\n")

#3d Examples
d = np.array([[[1,2],[3,4]],[[5,6],[7,8]]])
print(d)
print("\n")
print(d[0,1,1])
print("\n")
print(d[:,1,:])
print("\n")
d[:,1,:] = [[9,9],[8,8]]
print("\n")
print(d)
print("\n")

#Initializing zero arrays.
e = np.zeros((2,3),dtype='int16')
print(e)
print("\n")

#Initializing arrays with all entries 1.
f = np.ones((4,2,2),dtype='int32')
print(f)
print("\n")

#Initializing arrays with all entries k(k can be 99)
g = np.full((2,2),99,dtype = 'int16')
print(g)
print("\n")

#Fill the array with the same shape with any number k
h = np.full_like(b, 4)
print(h)
print("\n")
print(np.full(b.shape, 4,dtype = 'int16'))
print("\n")

#Random decimal numbers
i = np.random.rand(4,2)
print(i)
print("\n")
j = np.random.random_sample(b.shape)
print(j)
print("\n")

#Random integer numbers
k = np.random.randint(7,size=(3,3))
print(k)
print("\n")
l = np.random.randint(-4,8,size=(3,3))
print(l)
print("\n")

#Identity matrix
m = np.identity(5)
print(m)

#Repeat an array
n = np.array([[1,2,3]])
o = np.repeat(n,3,axis=0)
print(o)
print("\n")

#print a specific array
output = np.ones((5,5))
p = np.zeros((3,3))
p[1,1]=9
output[1:-1,1:-1]=p
print(output)

#Be careful when copying arrays
q = np.array([1,2,3])
r = q
r[0] = 100
print(r)
print("\n")
print(q)
print("\n")

#Use np.copy() when copying arrays to avoid changing the original array.
s = np.array([1,2,3])
t = s.copy()
t[0] = 100
print(s)
print("\n")
print(t)
print("\n")

#+,-,*,/,** applicable to arrays.
u = np.array([1.0,2.0,3.0,4.0])
print(u+2)
print("\n")
print(u-2)
print("\n")
print(u*2)
print("\n")
print(u/2)
print("\n")
print(u**3)
print("\n")

#Addition/subtraction of two arrays of the same shapes.
v = np.array([1,2,1,2])
print(u+v)
print("\n")

#Take sine and cosine of each entry in the array. (radian)
print(np.sin(v))
print("\n")
print(np.cos(v))
print("\n")

#For more(https://docs.scipy.org/doc/numpy/reference/routines.math.html)

#Matrix multiplication
w = np.ones((2,3))
x = np.full((3,2),2)
print(np.dot(w,x))
print("\n")

#Find the determinant of a matrix
y = np.identity(3)
print(np.linalg.det(y))
print("\n")

#For more about python's linear algebra operations, http://docs.scipy.org/doc/numpy/reference/routines.linalg.html

#Statistics in np

stats = np.array([[1,2,3],[4,5,6]])
print(np.min(stats))
print("\n")
print(np.max(stats))
print("\n")
print(np.min(stats,axis=0))
print("\n")
print(np.min(stats,axis=1))
print("\n")
print(np.max(stats,axis=0))
print("\n")
print(np.max(stats,axis=1))
print("\n")
print(np.sum(stats))
print("\n")
print(np.sum(stats,axis=0))
print("\n")
print(np.sum(stats,axis=1))
print("\n")
