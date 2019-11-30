import numpy as np
import pandas as pd
%matplotlib inline
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import chi2
import Orange
import Methods as mt
from sklearn.model_selection import StratifiedShuffleSplit
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import chi2
from sklearn.ensemble import ExtraTreesRegressor


meta = pd.read_csv('data/building_metadata.csv')
df_train = pd.read_csv('data/train.csv', parse_dates=['timestamp'])
df_test = pd.read_csv('data/test.csv')
weather_train = pd.read_csv('data/weather_train.csv', parse_dates=['timestamp'])
weather_test = pd.read_csv('data/weather_test.csv')

def reduce_mem_usage(df, verbose=True):
    numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
    start_mem = df.memory_usage().sum() / 1024**2    
    for col in df.columns:
        col_type = df[col].dtypes
        if col_type in numerics:
            c_min = df[col].min()
            c_max = df[col].max()
            if str(col_type)[:3] == 'int':
                if c_min > np.iinfo(np.int8).min and c_max < np.iinfo(np.int8).max:
                    df[col] = df[col].astype(np.int8)
                elif c_min > np.iinfo(np.int16).min and c_max < np.iinfo(np.int16).max:
                    df[col] = df[col].astype(np.int16)
                elif c_min > np.iinfo(np.int32).min and c_max < np.iinfo(np.int32).max:
                    df[col] = df[col].astype(np.int32)
                elif c_min > np.iinfo(np.int64).min and c_max < np.iinfo(np.int64).max:
                    df[col] = df[col].astype(np.int64)  
            else:
                if c_min > np.finfo(np.float16).min and c_max < np.finfo(np.float16).max:
                    df[col] = df[col].astype(np.float16)
                elif c_min > np.finfo(np.float32).min and c_max < np.finfo(np.float32).max:
                    df[col] = df[col].astype(np.float32)
                else:
                    df[col] = df[col].astype(np.float64)    
    end_mem = df.memory_usage().sum() / 1024**2
    if verbose: print('Mem. usage decreased to {:5.2f} Mb ({:.1f}% reduction)'.format(end_mem, 100 * (start_mem - end_mem) / start_mem))
    return df

'''Use numbers to represent each category of primary_use. Modify meta according to the dictionary.'''
primary_use_conversion = {}
for i, pu in enumerate(meta.primary_use.value_counts().index):
    primary_use_conversion[pu] = i
print(primary_use_conversion)

meta['primary_use'] = meta.primary_use.apply(lambda pu: primary_use_conversion[pu])
print(meta)

'''Modify the "wind_direction" column by converting directions to numbers 0-8 and convert "timestamp" column into date and time'''

weather_test['wind_direction'] = weather_test['wind_direction'].apply(Orange.wind_direction_conversion)
weather_test['timestamp'] = pd.to_datetime(weather_test['timestamp'])
print(weather_test)

'''Fill some NAs of _wtest and _wtrain and change data types'''
weather_test = \
weather_test.fillna({"cloud_coverage": -1,
              }) \
      .astype({"air_temperature": np.float16, 
               "cloud_coverage": np.int8,
               "dew_temperature": np.float16,
               "precip_depth_1_hr": np.float16, # np.int16 after fillna
               "sea_level_pressure": np.float16,
               "wind_direction": np.float16, # np.int8 after fillna
               "wind_speed": np.float16})
print(weather_test)


'''Changing the _wtrain just as before to _wtest'''
weather_train['wind_direction'] = weather_train['wind_direction'].apply(Orange.wind_direction_conversion)
weather_train['timestamp'] = pd.to_datetime(weather_train['timestamp'])
print(weather_train)

weather_train = \
weather_train.fillna({"cloud_coverage": -1,
              }) \
       .astype({"air_temperature": np.float16, 
                "cloud_coverage": np.int8,
                "dew_temperature": np.float16,
                "precip_depth_1_hr": np.float16, # np.int16 after fillna
                "sea_level_pressure": np.float16,
                "wind_direction": np.float16, # np.int8 after fillna
                "wind_speed": np.float16})
print(weather_train)

'''Change "timestamp" column to data time and convert data types
'''

df_test['timestamp'] = pd.to_datetime(df_test['timestamp'])
df_test = df_test.astype({"building_id": np.int16,
                      "meter": np.int8})
print(df_test) 

df_train['timestamp'] = pd.to_datetime(df_test['timestamp'])
df_train = df_train.astype({"building_id": np.int16,
                        "meter": np.int8})
print(df_train)


'''We take 70% of the dataset as our train dataset, and 30% of it as our test dataset.'''
split = StratifiedShuffleSplit(n_splits = 1, test_size = 0.3)
for train_index, test_index in split.split(df_train, df_train['timestamp']):
    small_train = df_train.loc[train_index]
    test_train = df_train.loc[test_index]
display(small_train.head())
display(test_train.head())

small_train = small_train.reset_index(drop = True)
test_train = test_train.reset_index(drop = True)
display(small_train.head())
display(test_train.head())

print(len(small_train)/len(df_train))
print(len(test_train)/len(df_train))

combined = test_train.merge(meta, how = 'left', on = ['building_id'])
display(combined.head())






'''Change floor_count and year_built. 
Fill nans in floor count with modes in each site.1.For sites that have mean value, fill nans with mean.2.For sites with no mean values, fill nans with mean of the entire dataframe.
'''

ser = combined.groupby('site_id')['floor_count'].mean()
dict = ser.to_dict()
print(dict)

total_mean = combined['floor_count'].mean()
print(total_mean)

dict.update(dict.fromkeys([0, 2, 3, 6, 9, 11, 13, 14, 15], total_mean))
print(dict)

combined['floor_count'] = combined.apply(mt.fill_floor_count, axis = 1, args = (dict, ))
print(np.mean(combined.isnull()))


'''Fill year_built:  We can see that year_built in some sites are completely empty. 1.For sites with single modes, We fill nans in year_built with modes.2.For site7, we use the mean of built_year of all building in this site to fill nans.3.For other sites, we use the mean of the entire column to fill nans.'''

ser = combined.groupby('site_id')['year_built'].agg(pd.Series.mode)
dict = ser.to_dict()
print(dict)

mean_7 = combined.loc[combined['site_id'] == 7]['year_built'].mean()
print(mean_7)

mean_all = combined['year_built'].mean()
print(mean_all)

dict[7] = 1961
dict.update(dict.fromkeys([6, 8, 9, 10, 11, 12, 13, 14], 1968))
print(dict)

filled = combined.apply(mt.fill_year_built, axis = 1, args = (dict, ))
combined['year_built'] = filled
combined.head()


'''Reduce memory usage. Combine test_train with weather_train'''
df_train = reduce_mem_usage(df_train)
df_test = reduce_mem_usage(df_test)
test_train_df = combined.copy()

weather_train = reduce_mem_usage(weather_train)
weather_test = reduce_mem_usage(weather_test)
meta = reduce_mem_usage(meta)
test_train_df = reduce_mem_usage(test_train_df)

test_train_df = test_train_df.merge(weather_train, on=['site_id', 'timestamp'], how="left")

test_train_df.head()
display(test_train_df.shape)

corr_matrix = test_train_df.corr()
corr_matrix["meter_reading"].sort_values(ascending=False)

display(pd.plotting.scatter_matrix(weather_train[['air_temperature','cloud_coverage','dew_temperature','precip_depth_1_hr','sea_level_pressure','wind_direction','wind_speed']],figsize=(12,8)))


model = ExtraTreesRegressor(n_estimators=100,)




