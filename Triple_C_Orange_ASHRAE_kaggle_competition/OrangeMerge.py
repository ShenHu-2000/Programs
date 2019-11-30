import pandas as pd
import numpy as np
import Methods as mt
from scipy.stats import ks_2samp
from sklearn.model_selection import StratifiedShuffleSplit

%load_ext autoreload
%autoreload 2

train = pd.read_csv("data/train.csv")
train.head()

weather_train = pd.read_csv("data/weather_train.csv")
weather_train.head()

building_meta = pd.read_csv("data/building_metadata.csv")
building_meta.head()

'''We take 70% of the dataset as our train dataset, and 30% of it as our test dataset.'''
split = StratifiedShuffleSplit(n_splits = 1, test_size = 0.3)
for train_index, test_index in split.split(train, train['timestamp']):
    small_train = train.loc[train_index]
    test_train = train.loc[test_index]
display(small_train.head())
display(test_train.head())

small_train = small_train.reset_index(drop = True)
test_train = test_train.reset_index(drop = True)
display(small_train.head())
display(test_train.head())

print(len(small_train)/len(train))
print(len(test_train)/len(train))

combined = small_train.merge(building_meta, how = 'left', on = ['building_id'])
combined.head()

combined['timestamp'] = pd.to_datetime(combined['timestamp'], format = "%Y-%m-%d %H:%M:%S")
combined.head()

building_meta.nunique()

train.nunique()

weather_train.nunique()

gp_time = train.groupby('timestamp').count()
gp_time.head()

first_stamp = train.loc[train['timestamp'] == '2016-01-01 00:00:00']
first_stamp

find_duplicates = first_stamp.loc[first_stamp['building_id'].duplicated().tolist()]
find_duplicates

combined = small_train.merge(building_meta, how = 'left', on = ['building_id'])
combined.head()

combined['timestamp'] = pd.to_datetime(combined['timestamp'], format = "%Y-%m-%d %H:%M:%S")
combined.head()

np.mean(combined.isnull())

combined['floor_count'].value_counts()

combined['floor_count'].plot(kind = 'hist', density = True, bins = 30)

combined['floor_count'].value_counts()

mt.check_dependency(combined, 'floor_count', 'site_id')

ser = combined.groupby('site_id')['floor_count'].count()
print(ser)

ser = combined.groupby('site_id')['floor_count'].mean()
dict = ser.to_dict()
print(dict)

total_mean = combined['floor_count'].mean()
total_mean

dict.update(dict.fromkeys([0, 2, 3, 6, 9, 11, 13, 14, 15], total_mean))
dict

combined['floor_count'] = combined.apply(mt.fill_floor_count, axis = 1, args = (dict, ))
np.mean(combined.isnull())

gpA = combined.loc[combined['year_built'].isnull(), 'site_id']
gpB = combined.loc[combined['year_built'].notnull(), 'site_id']

obs = ks_2samp(gpA, gpB).statistic
copy = combined.copy()
perm_results = []
for i in range(100):
    copy['year_built'] = combined['year_built'].sample(frac = 1, replace = False).reset_index(drop = True)
    gpA = copy.loc[copy['year_built'].isnull(), 'site_id']
    gpB = copy.loc[copy['year_built'].notnull(), 'site_id']
    perm_results.append(ks_2samp(gpA, gpB).statistic)
pval = np.mean(np.array(perm_results) >= obs)
pval


ser = combined.groupby('site_id')['year_built'].agg(pd.Series.mode)
dict = ser.to_dict()
dict

mean_7 = combined.loc[combined['site_id'] == 7]['year_built'].mean()
mean_7

mean_all = combined['year_built'].mean()
mean_all

dict[7] = 1961
dict.update(dict.fromkeys([6, 8, 9, 10, 11, 12, 13, 14], 1968))
dict

filled = combined.apply(mt.fill_year_built, axis = 1, args = (dict, ))
combined['year_built'] = filled
combined.head()

weather_building = weather_train.merge(building_meta, on = 'site_id', how = 'left')
weather_building

small_weather = small_train.merge(weather_building, on = ['building_id', 'timestamp'], how = 'left')
small_weather

small_weather.isnull().sum() 

corr_matrix = small_weather.corr()
corr_matrix["meter_reading"].sort_values(ascending=False)


