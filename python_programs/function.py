def build_profile(first,last,**user_info):
	profile = {}
	profile['first name'] = first
	profile['last name'] = last
	for key, value in user_info.items():
		profile[key] = value
	return profile

user_profile = build_profile("Shen","Hu",location = "Guangdong", vocabulary = 40000)
print(user_profile)
print("\n")
print(user_profile.items())
print("\n")
print(user_profile.keys())
print("\n")
print(user_profile.values())
print("\n")
for eachkey in user_profile.keys():
	print(eachkey)
print("\n")
for eachvalue in user_profile.values():
	print(eachvalue)
print("\n")
favorite_languages = {'David' : 'java','Mark' : 'python','Anita' : 'python', 'Colin' : 'Matlab'}
for languages in set(favorite_languages.values()):
	print(languages)
print("\n")

	