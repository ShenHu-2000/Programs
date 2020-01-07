from random import randint
class Die(object):
	def __init__(self, sizes = 6):
		self.sizes = sizes
	
	def roll_die(self):
		print(randint(1,self.sizes))
	
	
		

