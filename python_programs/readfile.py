
filename = "Gulliver's Travel.pages"
with open(filename,'r') as f_obj:
	contents = f_obj.read()
	words = contents.split()
	print(words)
	num_words = len(words)
	print("The file " + filename + " has about " + str(num_words) + " words")

