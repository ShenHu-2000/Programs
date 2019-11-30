import Orange
import numpy as np
import pandas as pd
import os
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import StratifiedShuffleSplit

fp_building_metadata = os.path.join("data", "building_metadata.csv")
fp_sample_submission = os.path.join("data", "sample_submission.csv")
fp_test              = os.path.join("data", "test.csv")
fp_train             = os.path.join("data", "train.csv")
fp_weather_test      = os.path.join("data", "weather_test.csv")
fp_weather_train     = os.path.join("data", "weather_train.csv")

'''Reading and cutting the csv'''

_metadata = pd.read_csv(fp_building_metadata)
print(_metadata.shape)
_metadata.head()

_wtest = pd.read_csv(fp_weather_test)
print(_wtest.shape)
_wtest.head()

_wtrain = pd.read_csv(fp_weather_train)
print(_wtrain.shape)
_wtrain.head()

_CHUNKSIZE = 100000
'''Using TestFileReader to read the whole chunk(1 chunk so next() returns the whole column of 'meter_reading')'''
_submission = next(pd.read_csv(fp_sample_submission, chunksize=_CHUNKSIZE, usecols=['meter_reading']))
print(_submission.shape)
_submission.head()

_test = next(pd.read_csv(fp_test, chunksize=_CHUNKSIZE, usecols=["building_id", "meter", "timestamp"]))
print(_test.shape)
_test.head()

_train = next(pd.read_csv(fp_train, chunksize=_CHUNKSIZE))
print(_train.shape)
_train.head()

'''Optimizing memory usage'''
'''Show the infos of each data type'''

np.iinfo(np.int8)
np.iinfo(np.int16)
np.iinfo(np.int32)
np.iinfo(np.int64)

'''Use numbers to represent each category of primary_use'''
primary_use_conversion = {}
for i, pu in enumerate(_metadata.primary_use.value_counts().index):
    primary_use_conversion[pu] = i
print(primary_use_conversion)

'''Changing elements in _metadata's primary_use column to corresponding numbers'''
_metadata['primary_use'] = _metadata.primary_use.apply(lambda pu: primary_use_conversion[pu])
print(_metadata)

_metadata = \
_metadata.fillna({"year_built": 2050,
                  "floor_count": 0})\
         .astype({"site_id": np.int8, 
                  "building_id": np.int16,
                  "primary_use": np.int8,
                  "year_built": np.int16,
                  "floor_count": np.int8})
print(_metadata)

'''Modify the "wind_direction" column by converting directions to numbers 0-8 and convert "timestamp" column into date and time'''

_wtest['wind_direction'] = _wtest['wind_direction'].apply(Orange.wind_direction_conversion)
_wtest['timestamp'] = pd.to_datetime(_wtest['timestamp'], format="%Y-%m-%d %H:%M:%S")
print(_wtest)


'''Fill some NAs of _wtest and _wtrain and change data types'''
_wtest = \
_wtest.fillna({"cloud_coverage": -1,
              }) \
      .astype({"air_temperature": np.float16, 
               "cloud_coverage": np.int8,
               "dew_temperature": np.float16,
               "precip_depth_1_hr": np.float16, # np.int16 after fillna
               "sea_level_pressure": np.float16,
               "wind_direction": np.float16, # np.int8 after fillna
               "wind_speed": np.float16})
print(_wtest)

'''Changing the _wtrain just as before to _wtest'''
_wtrain['wind_direction'] = _wtrain['wind_direction'].apply(Orange.wind_direction_conversion)
_wtrain['timestamp'] = pd.to_datetime(_wtrain['timestamp'], format="%Y-%m-%d %H:%M:%S")
print(_wtrain)

_wtrain = \
_wtrain.fillna({"cloud_coverage": -1,
              }) \
       .astype({"air_temperature": np.float16, 
                "cloud_coverage": np.int8,
                "dew_temperature": np.float16,
                "precip_depth_1_hr": np.float16, # np.int16 after fillna
                "sea_level_pressure": np.float16,
                "wind_direction": np.float16, # np.int8 after fillna
                "wind_speed": np.float16})
print(_wtrain)


'''Change "timestamp" column to data time and convert data types
'''

_test['timestamp'] = pd.to_datetime(_test['timestamp'], format="%Y-%m-%d %H:%M:%S")
_test = _test.astype({"building_id": np.int16,
                      "meter": np.int8})
print(_test) 

_train['timestamp'] = pd.to_datetime(_test['timestamp'], format="%Y-%m-%d %H:%M:%S")
_train = _train.astype({"building_id": np.int16,
                        "meter": np.int8})
print(_train)

'''Print out the linear plots of distribution of year_built and bar diagrams of distribution of primary_use'''
print(_metadata)
_metadata.describe()

print(_metadata.groupby('year_built')['building_id'].count().plot())

print(_metadata.groupby('primary_use')['building_id'].count().plot(kind='bar'))

print(_metadata['square_feet'].hist())




help(Orange.plot_wtrain)


Orange.plot_wtrain(_wtrain, site_id=5, start_month=10, start_day=1, end_month=10, end_day=30)
plt.show()
Orange.plot_wtrain_bar_diagram(_wtrain, site_id=5, start_month=10, start_day=1, end_month=10, end_day=30)
plt.show()
Orange.plot_wtrain_histogram(_wtrain, site_id=5, start_month=10, start_day=1, end_month=10, end_day=30)
plt.show()





'''Count the number of nan in columns precip_depth_1_hr and sea_level_pressure'''
print(_wtrain["precip_depth_1_hr"].isna().sum())
print(_wtrain["precip_depth_1_hr"].size)
print(_wtrain["sea_level_pressure"].isna().sum())
print(_wtrain["sea_level_pressure"].size)


