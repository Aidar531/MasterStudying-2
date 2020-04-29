import math
from sympy import *

def Griewank(x,y):
    return (10-10/(0.05*(x**2+y**2)-math.cos(x)*math.cos(y/math.sqrt(2))+2))

def funcDer(): #i параметр по которому будем диффиренцировать 0-по х 1-по у 
    x,y=symbols('x y')
    f=10-10/(0.05*(x**2+y**2)-cos(x)*cos(y/sqrt(2))+2)
    grad_f = lambdify((x, y), derive_by_array(f, (x, y)))   
    return grad_f

print(funcDer()(0,1))
    
    
    