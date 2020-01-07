
from dice import Die
d = Die()
d.roll_die()
for number in range(10):
	d.roll_die()
print("\n")
d2 = Die(10)
d3 = Die(12)
for number in range(0,10):
	d2.roll_die()
print("\n")
for number in range(0,10):
	d3.roll_die()
